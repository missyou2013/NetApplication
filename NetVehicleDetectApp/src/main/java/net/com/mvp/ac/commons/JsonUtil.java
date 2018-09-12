package net.com.mvp.ac.commons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: JsonParseUtil
 * @Description: json解析工具类
 * @author liuxj
 * @date 2014-10-15 上午11:38:34
 *
 */
public class JsonUtil {
	private JsonUtil() {
	}

	/**
	 * 对象转换成json字符串
	 *
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象
	 *
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * json字符串转成对象
	 *
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * 报异常： com.google.gson.internal.StringMap cannot be cast to com
	 *
	 * @param jsonstr
	 * @param class1
	 * @return
	 */
	public static <T> List<T> getJsonToModelList(String jsonstr, Class<T> class1) {

		Type listType = new TypeToken<List<T>>() {
		}.getType();
		Gson gson = new Gson();
		List<T> linkedList = gson.fromJson(jsonstr, listType);
		return linkedList;
	}

	/**
	 * 解析数组
	 * 
	 * @param s
	 * @param clazz
	 * @return
	 */ 
	public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
		T[] arr = new Gson().fromJson(s, clazz);
		return Arrays.asList(arr);
	}


}
