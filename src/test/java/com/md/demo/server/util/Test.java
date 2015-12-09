package com.md.demo.server.util;

import java.util.concurrent.CountDownLatch;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        int n = 100;
        CountDownLatch end = new CountDownLatch(n);
        for(int i=0; i< n; i++) {
            new Thread(new ATask(latch, end)).start();;
        }
        latch.countDown();
        end.await();
        System.out.println("i am done");
    }
    
    
}

class ATask implements Runnable {
    
    private CountDownLatch latch;
    private CountDownLatch end;
    
    public ATask(CountDownLatch latch, CountDownLatch end) {
        this.latch = latch;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            latch.await();
            Thread.sleep(200 * 1000);
            System.out.println("====sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            end.countDown();
        }
    }
    
}
