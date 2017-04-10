package learn;

/**
 * @author huangziyi@circle
 * @date 2017年3月14日 time 下午4:39:34
 * 
 */

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 


public class TestCachedThreadPool2  { 
        public static void main(String[] args) { 
//                ExecutorService executorService = Executors.newCachedThreadPool();
                ExecutorService executorService = Executors.newFixedThreadPool(5);
//         ExecutorService executorService = Executors.newSingleThreadExecutor();

                for (int i = 0; i < 5; i++) { 
                        executorService.execute(new run1()); 
                        System.out.println("************* a" + i + " *************"); 
                } 
                executorService.shutdown(); 
        } 
} 



class run1 implements Runnable { 
        public void run() { 
        	int i = 0;
//        System.out.println("启用线程 "+i);
                System.out.println(Thread.currentThread().getName() + "线程被调用了。"); 
           
                } 
        
}