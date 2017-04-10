package learn;
/*
 * MyThread 继承 Thread，在run方法内编写线程真正需要实现的功能
 * 当同时调用Thread类中的start()方法执行线程，也就是调用了Thread的run()方法
 * 调用方法：
 * 1.创建一个Thread对象
 * MyThread h1 = new MyThread("A");
 * 2.启动线程
 *   h1.start()
 * 
 * */
class  MyThread extends Thread {

	private static String name;
//	private CyclicBarrier cb;

	public MyThread(String name) {
		this.name = name;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println(name + "运行     " + i);
		}

	}

	public static void main(String[] args) {
		int c = 2;
		MyThread h1 = new MyThread("A");
		h1.start();
//		for (int i = 0; i < 2; i++) {
//			MyThread h1 = new MyThread("A");
//			h1.start();
//		}
		// learnTest h1=new learnTest("A");
		// learnTest h2=new learnTest("B");
		// h1.run();
		// h2.run();
	}
}