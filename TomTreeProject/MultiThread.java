/**
 * Created with IntelliJ IDEA.
 * User: zhuang
 * Date: 4/24/13
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {

    public static void main(String[] args) {
        // We will store the threads so that we can check if they are done
        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i =1; i<100; i++){
            executor.execute(new MultiThread().new MyRunnable(i));
        }
        System.out.println("starting shutdown...");
        executor.shutdown();
        while(!executor.isTerminated()){
        }
    }

    private class MyRunnable implements Runnable {
        private final int countUntil;

        MyRunnable(int countUntil) {
            this.countUntil = countUntil;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = 1; i < countUntil; i++) {
                sum += i;
            }
            System.out.println(sum);
        }
    }
}