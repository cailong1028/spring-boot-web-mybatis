package com.modules.demo2.test.nio.netty.primary.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TZookeeper {

    ZooKeeper zk;

    final private CountDownLatch latch = new CountDownLatch(1);

    public ZooKeeper connect() throws IOException, InterruptedException {
        zk = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    latch.countDown();
                }
            }
        });
        latch.await();
        return zk;
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        TZookeeper tZookeeper = new TZookeeper();
        tZookeeper.connect();
        Stat stat = tZookeeper.zk.exists("/javaapi", true);
        if(stat != null){
            tZookeeper.zk.delete("/javaapi", stat.getVersion());
        }
        byte[] data = "abcde".getBytes();
        tZookeeper.zk.create("/javaapi", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        stat = tZookeeper.zk.exists("/javaapi", true);
        if(stat != null){
            System.out.println("\tznode exists and version is: " + stat.getVersion());
        }else{
            System.out.println("\tznode does not exists");
        }
        tZookeeper.zk.setData("/javaapi", "ABC".getBytes(), stat.getVersion());
        byte[] result = tZookeeper.zk.getData("/javaapi", true, null);
        System.out.println("\tznode data is: " + new String(result, "UTF-8"));

        //getChildren
        List<String> children = tZookeeper.zk.getChildren("/", false);
        for (String child:children){
            System.out.println("\troot child: " + child);
        }

        stat = tZookeeper.zk.exists("/javaapi", true);
        tZookeeper.zk.delete("/javaapi", stat.getVersion());
        tZookeeper.close();
    }
}
