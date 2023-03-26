package Extends;

public class ThreadExtends {

	public static void sleep(long mls) {
		
		mls = mls < 0 ? 0 : mls;
		
		try {
			Thread.sleep(mls);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
