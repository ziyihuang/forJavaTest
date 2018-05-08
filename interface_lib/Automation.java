package interface_lib;

//import interface_junit.user.Login2Test;
//import interface_junit.user.Login2Test_supplement;

class Automation_Login2Test extends Thread {
	public void run() {
		do {
			try {
				//Login2Test.main(null);
				// 1分钟运行一次
				Thread.sleep(60 * 1000L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);
	}
}

class Automation_Login2Test_supplement extends Thread {
	public void run() {
		do {
			try {
				//Login2Test_supplement.main(null);
				// 20分钟运行一次
				Thread.sleep(20 * 60 * 1000L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);
	}
}

public class Automation {
	public static void main(String[] args) {
		Automation_Login2Test a1 = new Automation_Login2Test();
		a1.start();
		Automation_Login2Test_supplement a2 = new Automation_Login2Test_supplement();
		a2.start();
	}
}