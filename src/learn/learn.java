package learn;


import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 



//当将一个任务添加到线程池中的时候，线程池会为每个任务创建一个线程，该线程会在之后的某个时刻自动执行。
public class learn { 
        public static void main(String[] args) { 
//                ExecutorService executorService = Executors.newCachedThreadPool();
                ExecutorService executorService = Executors.newFixedThreadPool(5);
//         ExecutorService executorService = Executors.newSingleThreadExecutor();

                for (int i = 0; i < 5; i++) { 
                        executorService.execute(new TestRunnable()); 
                        System.out.println("************* a" + i + " *************"); 
                } 
                executorService.shutdown(); 
        } 
} 

class TestRunnable implements Runnable { 
        public void run() { 
                System.out.println(Thread.currentThread().getName() + "线程被调用了。"); 
                while (true) { 
                        try { 
                                Thread.sleep(5000); 
                                System.out.println(Thread.currentThread().getName()); 
                        } catch (InterruptedException e) { 
                                e.printStackTrace(); 
                        } 
                } 
        } 
}