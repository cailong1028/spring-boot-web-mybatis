/******************************************************************************
    File: ObjectPool.java

    Version 1.0
    Date            Author                Changes
    Jul.01,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.util;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * ?????????????????????????????????????????
 * ????????????§Ø????????????????????
 */
public abstract class ObjectPool {
	
	/**
	 * ????????????????????????
	 */
	private static int checkOut = 0;
	private static int checkIn = 0;
    /**
     * ???????????
     */
    private CleanUpThread cleaner;

    /**
     * ????????
     */
    // ?????§¹???(milliseconds)
    private long expirationTime = 0;
    // ?????????????????(milliseconds)
    private long checkOutTimeout = 0l;
    // ??????????(???????)
    private int maxObjNum = 0;
    // ????????§³????(???????)
    private int minObjNum = 0;
    // ????????
    private long lastCheckOut;

    /**
     * ????????
     */
    private Hashtable locked;
    private Hashtable unlocked;

    /**
     * ????????????????????§¹???? 30seconds
     */
    protected ObjectPool() {
        expirationTime = (1000 * 30); // 30 seconds
        init();
    }

    /**
     * ????????????????????§¹??????????(millseconds)
     */
    protected ObjectPool(long expireTime, long checkOutTimeout, int objMaxNum, int objMinNum){
        expirationTime = expireTime;
        this.checkOutTimeout = checkOutTimeout;
        this.maxObjNum = objMaxNum;
        this.minObjNum = objMinNum;
        init();
    }

    /**
     * ????????????íé?????????
     * @param obj ???????
     */
    protected synchronized void checkIn(Object obj) {
        if (obj != null) {
            locked.remove(obj);
            unlocked.put(obj, new Long(System.currentTimeMillis()));
          //CL 111115 ???
//            System.out.println(Thread.currentThread().getName()+"  free" + "@Count:" + checkIn++);
            System.out.println(Thread.currentThread().getName()+"  free" + "@Count:" + ++checkIn);
            notifyAll();
        }
    }

    /**
     * ?????????????§Ý????????????????????????????
     * ???????????????????????????????????????????????
     * ????????????§Ø??????????????????????????§Ý??????
     * ?????????????????
     * 
     * @return Object
     * @throws Exception
     */
    protected synchronized Object checkOut() throws Exception {
        long now = System.currentTimeMillis();
        lastCheckOut = now;
        Object obj = null;
        //System.out.println(Thread.currentThread().getName()+" Get In "+now);

        while(obj == null) {

            // ???????§Ý??????
            Enumeration objList = unlocked.keys();
            while (objList.hasMoreElements()) {
                obj = objList.nextElement();

                if (validate(obj)) {
                    unlocked.remove(obj);
                    locked.put(obj, new Long(now));
                    //System.out.println(Thread.currentThread().getName()+" Geted Pool "+now);
                    System.out.println(Thread.currentThread().getName()+" Geted "+now + "@Count:" +checkOut++);
                    return (obj);
                } else {
                    unlocked.remove(obj);
                    expire(obj);
                    obj = null;
                }
            }

            // ????????????§á?????????????????§³???????????????
            if(maxObjNum > 0){
                //System.out.println("Pool: unlock="+unlocked.size()+", locked="+locked.size());
                if(unlocked.size()+locked.size() < maxObjNum){
                    obj = create();
                }
            }else{
                obj = create();
            }

            if(obj==null && checkOutTimeout>0 &&
                System.currentTimeMillis()-now > checkOutTimeout){

                //System.out.println(Thread.currentThread().getName()+"  "+(System.currentTimeMillis()-now));
            	//CL 111115 ????timeout???
                throw new TimeoutException("Get object timeout in pool -->" + monitePool());
            }

            if(obj == null){
                long timeout = checkOutTimeout>0?checkOutTimeout:5000;
                try{
                    wait(timeout);
                }catch(InterruptedException ex){
                }
            }
        }//end while

        locked.put(obj, new Long(now));
        //CL 111115 ???
//        System.out.println(Thread.currentThread().getName()+" Geted "+now + "@Count:" + checkOut++);
        System.out.println(Thread.currentThread().getName()+" Geted "+now + "@Count:" + ++checkOut);
        return (obj);
    }

    /**
     * ?????????????
     */
    synchronized void cleanUp() {
        Object obj = null;
        long now = System.currentTimeMillis();

        Enumeration e = unlocked.keys();
        while (e.hasMoreElements()){
            if((unlocked.size()+locked.size()) <= minObjNum){
                break;
            }
            obj = e.nextElement();

            if (expirationTime > 0 &&
                (now-((Long) unlocked.get(obj)).longValue()) > expirationTime){

                unlocked.remove(obj);
                expire(obj);
                obj = null;
            }
        }

        System.gc();
        notifyAll();
    }

    public String monitePool(){
        return "Free:"+unlocked.size()+"    Locked:"+locked.size()+"   Max:"+maxObjNum;
    }

    private void init(){
        // ?????????????
        locked = new Hashtable();
        unlocked = new Hashtable();

        // ?????????
        lastCheckOut = System.currentTimeMillis();

        // ??????????
        cleaner = new CleanUpThread(this, expirationTime);
        cleaner.start();
    }

    /**
     * ??????§Ö???§Ø???
     */
    public void release(){
        Object obj = null;
        Enumeration e = unlocked.keys();
        while (e.hasMoreElements()){
            obj = e.nextElement();
            unlocked.remove(obj);
            expire(obj);
            obj = null;
        }
        e = locked.keys();
        while (e.hasMoreElements()){
            obj = e.nextElement();
            unlocked.remove(obj);
            expire(obj);
            obj = null;
        }
    }

    /**
     * ????????
     * @return Object
     * @throws Exception
     */
    protected abstract Object create() throws Exception;

    /**
     * ??????
     * @param o
     */
    protected abstract void expire(Object o);

    /**
     * ???????????§¹
     * @param obj ?????????
     * @return boolean ?????????§¹??true, validate, otherwise, invalidate
     */
    protected abstract boolean validate(Object obj);
}

/**
 * ????????????????????????????????????????¦Ä??????
 * ???????
 */
class CleanUpThread extends Thread {
    private ObjectPool pool;
    private long sleepTime;

    CleanUpThread(ObjectPool pool, long sleepTime) {
        this.pool = pool;
        this.sleepTime = sleepTime;
    }

    public void run() {
        while (true) {
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                // ignore it
            }
            pool.cleanUp();
        }
    }
}
