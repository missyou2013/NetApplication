package net.com.mvp.ac.commons;

import android.util.Log;

/**
 * 调试打印信息??  正式上线?? 把isTest = true改成isTest = false
 *
 */
public class UtilsLog {

	public static boolean isTest = true;
	public static final String TAG="tag";
	
	public static void d(String paramString2) {
		if (isTest)
			Log.d(TAG, paramString2);
	}
	public static void e(String paramString2) {
		if (isTest)
			Log.e(TAG, paramString2);
	}
	public static void d(String paramString1, String paramString2) {
		if (isTest)
			Log.d(paramString1, paramString2);
	}

	public static void e(String paramString1, String paramString2) {
		if (isTest)
			Log.e(paramString1, paramString2);
	}

	public static void i(String paramString1, String paramString2) {
		if (isTest)
			Log.i(paramString1, paramString2);
	}

//	public static void log(String paramString1, String paramString2) {
//		StackTraceElement[] arrayOfStackTraceElement = new Throwable()
//				.getStackTrace();
//		if (isTest) {
//			StackTraceElement localStackTraceElement = arrayOfStackTraceElement[1];
//			Object[] arrayOfObject = new Object[4];
//			arrayOfObject[0] = localStackTraceElement.getClassName();
//			arrayOfObject[1] = Integer.valueOf(localStackTraceElement
//					.getLineNumber());
//			arrayOfObject[2] = localStackTraceElement.getMethodName();
//			arrayOfObject[3] = paramString2;
//			Log.e(paramString1,
//					String.format("======[%s][%s][%s]=====%s", arrayOfObject));
//		}
//	}

	public static void w(String paramString1, String paramString2) {
		if (isTest)
			Log.w(paramString1, paramString2);
	}

	public static void w(String paramString1, String paramString2,
			Throwable paramThrowable) {
		if (isTest)
			Log.w(paramString1, paramString2, paramThrowable);
	}

	public static void w(String paramString, Throwable paramThrowable) {
		if (isTest)
			Log.w(paramString, paramThrowable);
	}

	public static void showLongLogs( String msg) {  //信息太长,分段打印
		String tag="TAG";
		//因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
		//  把4*1024的MAX字节打印长度改为2001字符数
		int max_str_length = 2001 - tag.length();
		//大于4000时
		while (msg.length() > max_str_length) {
			Log.e(tag, msg.substring(0, max_str_length));
			msg = msg.substring(max_str_length);
		}
		//剩余部分
		Log.e(tag, msg);
	}
	/**
	 * 分段打印出较长log文本
	 * @param log        原log文本
	 * @param showCount  规定每段显示的长度（最好不要超过eclipse限制长度）
	 */
	public static void showLogCompletion(String log,int showCount){
		if(log.length() >showCount){
			String show = log.substring(0, showCount);
//			System.out.println(show);
			Log.e("TAG", show+"");
			if((log.length() - showCount)>showCount){//剩下的文本还是大于规定长度
				String partLog = log.substring(showCount,log.length());
				showLogCompletion(partLog, showCount);
			}else{
				String surplusLog = log.substring(showCount, log.length());
//				System.out.println(surplusLog);
				Log.e("TAG", surplusLog+"");
			}

		}else{
//			System.out.println(log);
			Log.e("TAG", log+"");
		}
	}

}
