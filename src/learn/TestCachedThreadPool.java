package learn;

/**
 * @author huangziyi@circle
 * @date 2017年3月14日 time 下午4:39:34
 * 
 */

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 


public class TestCachedThreadPool  { 
        public static void main(String[] args) { 
//                ExecutorService executorService = Executors.newCachedThreadPool();
                ExecutorService executorService = Executors.newFixedThreadPool(5);
//         ExecutorService executorService = Executors.newSingleThreadExecutor();

                for (int i = 0; i < 5; i++) { 
                        executorService.execute(new TestRunnable1()); 
                        System.out.println("************* a" + i + " *************"); 
                } 
                executorService.shutdown(); 
        } 
} 

class TestRunnable1 implements Runnable { 
        public void run() { 
                System.out.println(Thread.currentThread().getName() + "线程被调用了。"); 
           
                } 
        
}