package net.com.mvp.ac.cache;

import android.os.Environment;

import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.commons.FileUtils;
import java.io.File;
import java.io.IOException;

public class ConfigCache {
	private static final String TAG = ConfigCache.class.getName();

	public static final int CONFIG_CACHE_MOBILE_TIMEOUT = 28800000; // 8 hour
	public static final int CONFIG_CACHE_WIFI_TIMEOUT = 1800000; // 30 minute

	public static String PACKAGE_NAME = "com.degao.app.management";
	public static String SD_PATH = Environment.getExternalStorageDirectory()
			.toString();

	public static String FILE_PATH = SD_PATH + "/" + PACKAGE_NAME;

	public static String FILE_CACHE = FILE_PATH + "/dataCache";

	public static String IMAGE_CACHE = FILE_PATH + "/imgCache/";

	public static String getUrlCache() {
		String result = null;
		File file = new File(FILE_CACHE + "/num");
		if (file.exists() && file.isFile()) {
			try {
				result = FileUtils.readTextFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void setCache(String data) {

		File file = new File(FILE_CACHE);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(FILE_CACHE + "/num");
		try {
			// 创建缓存数据到磁盘，就是创建文件
			FileUtils.writeTextFile(file, data);
		} catch (IOException e) {
			UtilsLog.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
			e.printStackTrace();
		}
	}

	/**
	 * 删除缓存文件
	 */
	public static boolean deleteCache() {
		try {
			File file = new File(FILE_CACHE + "/num");
			if (file.exists()) {
				file.delete();
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除缓存文件
	 */
	public static void deleteAllCache() {
		File file = new File(FILE_CACHE);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		try {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
