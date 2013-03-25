/**
 * @author Yanis Biziuk
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import space.SkeletonGame;

import java.util.LinkedList;

public class DesktopGame {

    public class SyncObj{

        LinkedList<Integer> list = new LinkedList<Integer>();
        private final Object sync = new Object();
        private final Object syncList = new Object();

        public SyncObj(){
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    SyncObj.this.prepareData();
                }
            });
            th.start();
        }

        public void prepareData(){

            while (true){

                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() + "@ data prepared!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }


                synchronized (sync){

                    System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() + "@ data added!");

                    synchronized (syncList){
                        list.add((int)(Math.random() * 100 % 10));
                        list.add((int)(Math.random() * 100 % 10));
                        list.add((int)(Math.random() * 100 % 10));
                    }

                    sync.notifyAll();

                    System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ notifyAll complete... and sleep");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // Ignored.
                    }
                    System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ notifyAll done");

                }
            }
        }
        public void getData(){


            while (true){

                Integer data = null;

                synchronized (sync){

                    synchronized (syncList){
                        if (!list.isEmpty()){
                            data = list.removeFirst();
                        }
                    }

                    if (data == null){
                        System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ no data");
                        try {
                            sync.wait();

                            System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ wake UP... wait 500");
                            Thread.sleep(500);
                            System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ complete wait 500");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }

                }

                if (data != null){

                    System.out.println(System.currentTimeMillis() + "/" + Thread.currentThread().getName() +  "@ got data: " + data);


                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        break;
                    }


                    break;
                } else {

                }


            }

        }

        public int test(){
            int test = 1;
            try {
                test = 1;
                test++;
                test--;
                test += 10;

                if (Math.random() * 10 > 5){
                    test++;
                    return test;
                } else {
                    test--;
                    return test;
                }

            } finally {
                System.out.println("WOW");
            }
        }

        public void test2(){

        }
    }

    public static void main (String[] args) throws InterruptedException {

        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } finally {
                    System.out.println("WOW");
                }
            }
        });
        t.setDaemon(true);
        t.start();

        if (1==1) return;

        final SyncObj obj = new DesktopGame().new SyncObj();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    obj.getData();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread th1 = new Thread(runnable);
        Thread th2 = new Thread(runnable);
        th1.start();
        th2.start();

        Thread.sleep(999999999);
        */

        new LwjglApplication(new SkeletonGame(), "Game", 800, 480, true);
    }
}