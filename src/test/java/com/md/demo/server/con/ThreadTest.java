package com.md.demo.server.con;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Descriptions of the class ThreadTest.java's implementation：TODO described the
 * implementation of class
 * 
 * @author yangzhen 2015年10月26日 下午9:04:29
 */
public class ThreadTest {

    public static void main(String[] args) {
        AtomicInteger atom = new AtomicInteger(1); 
        new Thread(new A(atom), "aaa1").start();
        new Thread(new B(atom), "aaa2").start();
        //new Thread(new C(atom), "aaa3").start();
    }
}

class A implements Runnable {
    AtomicInteger atom; 
    public A(AtomicInteger atom){
        this.atom = atom;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ThreadTest.class) {
                if (atom.intValue() % 2 == 1) {
                    System.out.println("===" +Thread.currentThread().getName() + "," + System.currentTimeMillis());
                    atom.incrementAndGet();
                    ThreadTest.class.notifyAll();
                } else {
                    try {
                        ThreadTest.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}

class B implements Runnable {
    AtomicInteger atom; 
    public B(AtomicInteger atom){
        this.atom = atom;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ThreadTest.class) {
                if (atom.intValue() % 2 == 0) {
                    System.out.println("===" +Thread.currentThread().getName() + "," + System.currentTimeMillis());
                    atom.incrementAndGet();
                    ThreadTest.class.notifyAll();
                } else {
                    try {
                        ThreadTest.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}

class C implements Runnable {
    AtomicInteger atom; 
    public C(AtomicInteger atom){
        this.atom = atom;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ThreadTest.class) {
                if (atom.intValue() % 3 == 3) {
                    System.out.println("===" + 1 + ","
                                       + Thread.currentThread().getName() + ","
                                       + Thread.currentThread().getId());
                    atom.incrementAndGet();
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}
