package com.modules.prime.test.pattern;

public class PatternTest<K, V> implements PatternInterface<Integer> {

    K k;
    V v;

    PatternTest(K k, V v){
        this.k = k;
        this.v = v;
    }

    @Override
    public Integer getT() {
        return 2;
    }

    public K getK(){
        return this.k;
    }
    public V getV(){
        return this.v;
    }

    public <M> M getM(Class<M> clazz, String str) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        PatternTest p = new PatternTest<Integer, String>(1, "a");
        System.out.println(p.getT());
        System.out.println(p.getK());
        System.out.println(p.getV());


        System.out.println(p.getM(String.class, "aa"));

    }
}
