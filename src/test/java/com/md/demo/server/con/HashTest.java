package com.md.demo.server.con;

import java.util.HashMap;
import java.util.Map;

public class HashTest {
    
    private int i = -10000;
    
    public static void main(String[] args) {
        Map<HashTest,String> map = new HashMap<HashTest, String>();
        HashTest test = new HashTest();
        System.out.println(test+","+test.hashCode());
        map.put(test, "a");
        String a = map.get(test);
        System.out.println(a);
        System.out.println(hash(test));
        System.out.println(indexFor(hash(test), 15));
        System.out.println(-10&15);
    }

    static int indexFor(int h, int length) {
        return h & (length-1);
    }
    
    final static int hash(Object k) {
        int h = 0;
        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    
    public int getI() {
        return i;
    }

    
    public void setI(int i) {
        this.i = i;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + i;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HashTest other = (HashTest) obj;
        if (i != other.i) return false;
        return true;
    }
    
    
}
