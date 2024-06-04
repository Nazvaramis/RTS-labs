package com.example.colocviu;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


public class main {
    public static void main(String args[]) throws BrokenBarrierException, InterruptedException {

        CyclicBarrier barrier = new CyclicBarrier(2);
        Semaphore semaphore = new Semaphore(3);
        Semaphore semaphore2 = new Semaphore(2);


        //Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500)

        ExecThread t1 = new ExecThread(barrier,semaphore,semaphore2,2,2,5,6,2,3,5);



        ExecThreadMonitor t2 = new ExecThreadMonitor(barrier,semaphore,semaphore2,3,6,5,4,3,2,4);
        //ExecThreadMonitor t2 = new ExecThreadMonitor(barrier, semaphore2,  3, 6, 3,2,5);

        t1.start();
        t2.start();
    }
}


class ExecThread extends Thread{
    Semaphore semaphore;

    Semaphore semaphore2;
    int activityMin, activityMax, sleepVal,activityMax2,activityMin2,sleep_min,sleep_max;

    CyclicBarrier barrier;


    public ExecThread(CyclicBarrier barrier, Semaphore semaphore,Semaphore semaphore2,int sleepVal,int activityMin,int activityMax,int activityMax2,int activityMin2,int sleep_min , int sleep_max){
        this.barrier = barrier;
        this.semaphore = semaphore;
        this.semaphore2 = semaphore2;
        this.activityMin = activityMin;
        this.activityMax = activityMax;
        this.sleepVal = sleepVal;
        this.activityMax2= activityMax2;
        this.activityMin2 = activityMin2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;

    }




    public void run() {

        //threadu din dreapta

        while(true) {
            System.out.println(this.getName() + " State 1");
            try {
                this.semaphore.acquire(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {

                Thread.sleep(500*3);

            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(this.getName() + " State 2");
            int k = (int) Math.round(Math.random() * (activityMax - activityMin) + activityMin);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }

            try{
                this.semaphore.acquire(2);
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            System.out.println(this.getName() + " State 3");

            int k2 = (int) Math.round(Math.random()*(activityMax2 - activityMin2) + activityMin2);
            for (int i = 0; i < k2*100000; i++){
                i++;
                i--;

            }

            this.semaphore2.release(2);
            this.semaphore.release(2);


                try {
                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min));
                }catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            System.out.println(this.getName() + " State 4");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }


            }
        }

    }



class ExecThreadMonitor extends Thread {
    Semaphore semaphore;

    Semaphore semaphore2;
    int activityMin, activityMax, sleepVal,sleep_min,sleep_max,activityMax2,activityMin2;

    CyclicBarrier barrier;

    public ExecThreadMonitor(CyclicBarrier barrier,Semaphore semaphore, Semaphore semaphore2, int activityMin, int activityMax, int activityMax2,int activityMin2,int sleepVal,int sleep_min, int sleep_max)
    {
        this.barrier =barrier;
        this.semaphore=semaphore;
        this.activityMin=activityMin;
        this.activityMax=activityMax;
        this.activityMax2 = activityMax2;
        this.activityMin2 = activityMin2;
        this.semaphore2 = semaphore2;
        this.sleepVal = sleepVal;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;


    }

    public void run() {

        while (true) {
            System.out.println(this.getName() + " - STATE 1");

            try {
                this.semaphore.acquire(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            try{Thread.sleep(sleepVal);}
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            System.out.println(this.getName() + " - STATE 2");

            int k = (int) Math.round(Math.random() * (activityMax - activityMin) + activityMin);

            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }


            try {
                this.semaphore2.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(this.getName() + " - STATE 3");


            this.semaphore.release(3);
            this.semaphore2.release(1);

            int k2 = (int) Math.round(Math.random() * (activityMax2 - activityMin2) + activityMin2);

            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }

            try {
                Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            System.out.println(this.getName() + " - STATE 4");

            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

        }
    }

}