package net.com.mvp.ac.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import net.com.mvp.ac.model.IPPortModel;
import net.com.mvp.ac.model.SettingModel;
import net.com.mvp.ac.model.UserModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class SharedPreferencesUtils {

	private static final String SharedPreferencesName = "com.mvd.app.activity";

	// 纬度坐标
	public static void setLatitude(Activity activity, float lat) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putFloat("latitude", lat);
		editor.commit();
	}

	public static float getLatitude(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		float flag = settings.getFloat("latitude", 0);
		return flag;
	}

	// 经度坐标
	public void setLongitude(Activity activity, float lat) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putFloat("longitude", lat);
		editor.commit();
	}

	public float getLongitude(Activity activity) {

		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		float flag = settings.getFloat("longitude", 0);
		return flag;
	}

	/**
	 * 设置是否记住登录状?? true：记??false：不记住
	 *
	 * @param activity
	 * @param
	 */
	public static void setLoadingStatement(Activity activity, Boolean flag) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putBoolean("is_loading", flag);
		editor.commit();
	}

	/**
	 * 获取是否记住登录状??
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static boolean getLoadingStatement(Activity activity) {
		boolean flag = false;
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getBoolean("is_loading", false);
		return flag;
	}
	/**
	 * 设置是否自动刷新
	 *
	 * @param activity
	 * @param flag
	 */
	public static void setAutoRefresh(Activity activity,  Boolean flag) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putBoolean("AutoRefresh_flag", flag);
		editor.commit();
	}

	/**
	 * 获取是否自动刷新
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static boolean getAutoRefresh(Context activity) {
		boolean flag = false;
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		flag = settings.getBoolean("AutoRefresh_flag", true);
		return flag;
	}


	/**
	 * 设置是否裁剪图片
	 *
	 * @param activity
	 * @param flag
	 */
	public static void setPhotoClipping(Activity activity,  Boolean flag) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putBoolean("PhotoClipping_flag", flag);
		editor.commit();
	}

	/**
	 * 获取是否裁剪图片
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static boolean getPhotoClipping(Context activity) {
		boolean flag = false;
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		flag = settings.getBoolean("PhotoClipping_flag", false);
		return flag;
	}

	/**
	 * 设置登录状?? true：登??false：未登录
	 *
	 * @param activity
	 * @param username
	 */
	public static void setLoading(Activity activity, String username, String pwd, Boolean flag) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putBoolean("loading", flag);
		editor.putString("username", username);
		editor.putString("pwd", username);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取登录状??
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static boolean getLoading(Context activity) {
		boolean flag = false;
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getBoolean("loading", false);
		return flag;
	}

	/**
	 * 设置用户id
	 *
	 * @param activity
	 * @param ID
	 * @param
	 */
	public static void setUID(Activity activity, String ID) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("uid", ID);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取用户id
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static String getUID(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getString("uid", "");
		return flag;
	}

	/**
	 * 获取用户??
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static String getLoadingUserName(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getString("username", null);
		return flag;
	}

	/**
	 * 设置授权码
	 *
	 * @param activity
	 * @param
	 */
	public static void setShouQuanMa(Activity activity,String shouquanma) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("ShouQuanMa", shouquanma);
		editor.commit();
	}

	/**
	 * 获取授权码
	 *
	 * @param activity
	 * @param
	 * @return
	 */
	public static String getShouQuanMa(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getString("ShouQuanMa", "");
		return flag;
	}
	/**
	 * 设置ip的实体类
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setIPModel(Activity activity, IPPortModel model) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
//		String ip,ip_port;//接口的ip
//		String file_ip,file_ip_port;//文件的ip
		editor.putString("m_ip", model.getIp());
		editor.putString("m_ip_port", model.getIp_port());
		editor.putString("m_file_ip", model.getFile_ip());
		editor.putString("m_file_ip_port", model.getFile_ip_port());
		editor.commit();
	}

	/**
	 * 获取ip实体类
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static IPPortModel getIPModel(Activity activity) {
		IPPortModel model = new IPPortModel();
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		model.setIp(settings.getString("m_ip", ""));
		model.setIp_port(settings.getString("m_ip_port", ""));
		model.setFile_ip(settings.getString("m_file_ip", ""));
		model.setFile_ip_port(settings.getString("m_file_ip_port", ""));
		return model;
	}

	public static void setWebServicesIP(Activity activity, String ip) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString("WebServices_ip", ip);
		editor.commit(); // ????要记得提??
	}
	public static String getWebServicesIP(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		flag = settings.getString("WebServices_ip", "");
		return flag;
	}
	public static void setWebServicesAddress(Activity activity, String ip) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString("WebServicesAddress_ip", ip);
		editor.commit(); // ????要记得提??
	}
	public static String getWebServicesAddress(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		flag = settings.getString("WebServicesAddress_ip", "");
		return flag;
	}
	/**
	 * 设置ip
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setIP(Activity activity, String ip) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString("ip", ip);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取ip
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static String getIP(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
		flag = settings.getString("ip", "");
		return flag;
	}
	/**
	 * 设置ip
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setFileIP(Activity activity, String ip) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("file_ip", ip);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取ip
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static String getFileIP(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getString("file_ip", null);
		return flag;
	}
	/**
	 * 设置用户是否第一次实用软??
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setFirstUseApp(Activity activity, Boolean flag) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putBoolean("first", flag);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取用户是否第一次实用软??
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static boolean getFirstUseApp(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		boolean flag = settings.getBoolean("first", false);
		return flag;
	}

	/**
	 * 设置用户token
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setUserToken(Activity activity, String token) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("userToken", token);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取用户token
	 *
	 * @param context
	 * @param
	 * @return
	 */

	public static String getUserToken(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		String flag = settings.getString("userToken", null);
		return flag;
	}

	/**
	 * 设置用户信息??昵称，??别，年龄，电话，头像URL
	 *
	 * @param
	 * @param
	 * @param
	 */
	public static void setUserAllinfor(Context context, String nickname, String gender, String age, String phone,
			String photoURL) {// 写入XML数据
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		// editor.putString("shake_user_id", id);
		editor.putString("shake_user_nickname", nickname);
		editor.putString("shake_user_gender", gender);
		editor.putString("shake_user_age", age);
		editor.putString("shake_user_phone", phone);
		editor.putString("shake_user_photo_url", photoURL);
		editor.commit(); // ????要记得提??
	}

	// /**
	// * 获取用户信息??id,昵称，??别，年龄，电话，头像URL
	// *
	// * @param context
	// * @return
	// */
	//
	// public static User getUserAllinfor(Context context) {
	// User user=new User();
	// SharedPreferences settings = context.getSharedPreferences(
	// SharedPreferencesName, 0);
	// // String id = settings.getString("shake_user_id", null);
	// String nickname = settings.getString("shake_user_nickname", null);
	// String gender = settings.getString("shake_user_gender", null);
	// String age = settings.getString("shake_user_age", null);
	// String phone = settings.getString("shake_user_phone", null);
	// String photoURL = settings.getString("shake_user_photo_url", null);
	//
	//
	// // user.setId(id);
	// user.setNickname(nickname);
	// user.setGender(gender);
	// user.setAge(age);
	// user.setPhone(phone);
	//
	//
	// return user;
	// }

	/**
	 * 设置用户余额
	 *
	 * @param
	 * @param
	 * @param
	 */
	public static void setUserBalance(Context context, String b) {// 写入XML数据
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("balance", b);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取用户余额
	 *
	 * @param context
	 * @param
	 * @return
	 */

	public static String getUserBalance(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		String flag = settings.getString("balance", "0");
		return flag;
	}

	//
	// /**
	// * 设置用户金钱余额
	// */
	// public static void setMyBalance(Context context,User user) {// 写入XML数据
	// SharedPreferences settings = context.getSharedPreferences(
	// SharedPreferencesName, 0);
	// SharedPreferences.Editor editor = settings.edit();
	// editor.putString("my_balance", user.getBalance());
	// editor.commit(); // ????要记得提??
	// }

	/**
	 * 获取用户金钱余额
	 *
	 */

	public static String getMyBalance(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		String flag = settings.getString("my_balance", "0");
		return flag;
	}

	/**
	 * 获取腾讯微博token
	 *
	 * @param context
	 * @param
	 * @return
	 */

	public static String getTXToken(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		String token = settings.getString("token", "");
		return token;
	}

	/**
	 * 获取腾讯微博token_secret
	 *
	 * @param context
	 * @param
	 * @return
	 */

	public static String getTXSecretToken(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SharedPreferencesName, 0);
		String token_secret = settings.getString("token_secret", "");
		return token_secret;
	}

	/**
	 * 设置用户是否第一次获取我的余??
	 *
	 * @param activity
	 * @param
	 * @param
	 */
	public static void setFirstGetMyBalance(Activity activity) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putBoolean("first_balance", true);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取用户是否第一次获取我的余??
	 *
	 * @param activity
	 * @param
	 * @return
	 */

	public static boolean getFirstGetMyBalance(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		boolean flag = settings.getBoolean("first_balance", false);
		return flag;
	}

	/**
	 * 保存头像图片
	 *
	 * @param activity
	 * @param
	 */
	public static void setUserPhoto(Activity activity, String photoURL) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("login_bean_picture", photoURL);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取头像图片
	 *
	 * @param activity
	 * @return
	 */
	public static String getUserPhoto(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String photo = settings.getString("login_bean_picture", "");
		return photo;
	}

	/**
	 * 保存上一次登录定位的城市
	 *
	 * @param activity
	 * @param
	 */
	public static void setUserCity(Activity activity, String city) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("city", city);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取上一次登录定位的城市
	 *
	 * @param activity
	 * @return
	 */
	public static String getUserCity(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String city = settings.getString("city", "");
		return city;
	}

	/**
	 * 清除pushTag
	 *
	 * @param activity
	 * @return
	 */
	public static void clearPushTag(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor et = settings.edit();
		et.remove("tags");
		et.commit();
	}

	/**
	 * 保存上一次登录使用的昵称
	 *
	 * @param activity
	 * @param
	 */
	public static void setUserNickName(Activity activity, String city) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("nickname", city);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取上一次登录使用的昵称
	 *
	 * @param activity
	 * @return
	 */
	public static String getUserNickName(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String city = settings.getString("nickname", "");
		return city;
	}

	/**
	 * 保存上一次登录使用的年龄
	 *
	 * @param activity
	 * @param
	 */
	public static void setUserAge(Activity activity, String age) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("age", age);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取上一次登录使用的年龄
	 *
	 * @param activity
	 * @return
	 */
	public static String getUserAge(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String age = settings.getString("age", "");
		return age;
	}

	/**
	 * 保存上一次登录使用的性别
	 *
	 * @param activity
	 * @param
	 */
	public static void setUserGender(Activity activity, String gender) {// 写入XML数据
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("gender", gender);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取上一次登录使用的性别
	 *
	 * @param activity
	 * @return
	 */
	public static String getUserGender(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String gender = settings.getString("gender", "");
		return gender;
	}

	/**
	 * 保存用户信息
	 *
	 * @return
	 */
	public static void setUserModelInfor(Activity activity, UserModel model) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("user_communityId", DESUtil.encrypt(model.getCommunityId()));
		editor.putString("user_status", DESUtil.encrypt(model.getStatus()));
		editor.putString("user_id", DESUtil.encrypt(model.getId()));
		editor.putString("user_passportid", DESUtil.encrypt(model.getPassportid()));
		editor.putString("user_pid", DESUtil.encrypt(model.getPid()));
		editor.putString("user_truename", DESUtil.encrypt(model.getTruename()));
		editor.putString("user_address", DESUtil.encrypt(model.getAddress()));
		editor.putString("user_linktel", DESUtil.encrypt(model.getLinktel()));
		editor.putString("user_idtype", DESUtil.encrypt(model.getIdtype()));
		editor.putString("user_idno", DESUtil.encrypt(model.getIdno()));
		editor.putString("user_birthday", DESUtil.encrypt(model.getBirthday()));
		editor.putString("user_photo", DESUtil.encrypt(model.getPhoto()));
		editor.putString("user_type", DESUtil.encrypt(model.getType()));
		editor.putString("user_workstatus", DESUtil.encrypt(model.getWorkstatus()));
		editor.putString("user_sex", DESUtil.encrypt(model.getSex()));
		editor.putString("user_remark", DESUtil.encrypt(model.getRemark()));
		editor.putString("user_grade", DESUtil.encrypt(model.getGrade()));
		editor.commit();
	}

	/**
	 * 获取用户信息
	 *
	 * @param activity
	 */
	public static UserModel getUserModelInfor(Context activity) {
		UserModel bean = new UserModel();
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		bean.setCommunityId(DESUtil.decrypt(settings.getString("user_communityId", "")));
		bean.setStatus(DESUtil.decrypt(settings.getString("user_status", "")));
		bean.setId(DESUtil.decrypt(settings.getString("user_id", "")));
		bean.setPassportid(DESUtil.decrypt(settings.getString("user_passportid", "")));
		bean.setPid(DESUtil.decrypt(settings.getString("user_pid", "")));
		bean.setTruename(DESUtil.decrypt(settings.getString("user_truename", "")));
		bean.setAddress(DESUtil.decrypt(settings.getString("user_address", "")));
		bean.setLinktel(DESUtil.decrypt(settings.getString("user_linktel", "")));
		bean.setIdtype(DESUtil.decrypt(settings.getString("user_idtype", "")));
		bean.setIdno(DESUtil.decrypt(settings.getString("user_idno", "")));
		bean.setBirthday(DESUtil.decrypt(settings.getString("user_birthday", "")));
		bean.setPhoto(DESUtil.decrypt(settings.getString("user_photo", "")));
		bean.setType(DESUtil.decrypt(settings.getString("user_type", "")));
		bean.setWorkstatus(DESUtil.decrypt(settings.getString("user_workstatus", "")));
		bean.setSex(DESUtil.decrypt(settings.getString("user_sex", "")));
		bean.setRemark(DESUtil.decrypt(settings.getString("user_remark", "")));
		bean.setGrade(DESUtil.decrypt(settings.getString("user_grade", "")));
		return bean;
	}
	/**
	 * 保存系统设置
	 *
	 * @return
	 */
	public static void setSettingModelInfor(Activity activity, SettingModel model) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("setting_ip", DESUtil.encrypt(model.getIP()));
		editor.putString("setting_port", DESUtil.encrypt(model.getPort()));
		editor.putString("setting_jiqima", DESUtil.encrypt(model.getJiqima()));
		editor.putString("setting_shouquanma", DESUtil.encrypt(model.getShouquanma()));
		editor.putString("setting_shuuku_tiaoshi_xinxi", DESUtil.encrypt(model.getShuuku_tiaoshi_xinxi()));
		editor.putString("setting_jiance_mode", DESUtil.encrypt(model.getJiance_mode()));
		editor.putString("setting_zhaopian_shuiyin", DESUtil.encrypt(model.getZhaopian_shuiyin()));
		editor.putString("setting_jiance_xiangmu_moren", DESUtil.encrypt(model.getJiance_xiangmu_moren()));
		editor.putString("setting_bianma", DESUtil.encrypt(model.getBianma()));
		editor.putString("setting_shijian_xianzhi", DESUtil.encrypt(model.getShijian_xianzhi()));
		editor.putString("setting_chepai_left", DESUtil.encrypt(model.getChepai_left()));
		editor.putString("setting_chepai_right", DESUtil.encrypt(model.getChepai_right()));
		editor.commit();
	}

	/**
	 * 获取系统设置
	 *
	 * @param activity
	 */
	public static SettingModel getSettingModelInfor(Context activity) {
		SettingModel bean = new SettingModel();
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		bean.setIP(DESUtil.decrypt(settings.getString("setting_ip", "")));
		bean.setPort(DESUtil.decrypt(settings.getString("setting_port", "")));
		bean.setJiqima(DESUtil.decrypt(settings.getString("setting_jiqima", "")));
		bean.setShouquanma(DESUtil.decrypt(settings.getString("setting_shouquanma", "")));
		bean.setShuuku_tiaoshi_xinxi(DESUtil.decrypt(settings.getString("setting_shuuku_tiaoshi_xinxi", "")));
		bean.setJiance_mode(DESUtil.decrypt(settings.getString("setting_jiance_mode", "")));
		bean.setZhaopian_shuiyin(DESUtil.decrypt(settings.getString("setting_zhaopian_shuiyin", "")));
		bean.setJiance_xiangmu_moren(DESUtil.decrypt(settings.getString("setting_jiance_xiangmu_moren", "")));
		bean.setBianma(DESUtil.decrypt(settings.getString("setting_bianma", "")));
		bean.setShijian_xianzhi(DESUtil.decrypt(settings.getString("setting_shijian_xianzhi", "")));
		bean.setChepai_left(DESUtil.decrypt(settings.getString("setting_chepai_left", "")));
		bean.setChepai_right(DESUtil.decrypt(settings.getString("setting_chepai_right", "")));
		return bean;
	}
	/**
	 * 获取Authorization
	 *
	 * @param activity
	 * @return
	 */
	public static String getLoadingAuthorization(Activity activity) {
		String flag = "";
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		flag = settings.getString("login_bean_authorization", null);
		return flag;
	}

	/**
	 * 保存push信息
	 *
	 * @param activity
	 * @param
	 */
	public static void setPushInfor(Context activity, String push_channelid, String push_userid) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		Editor editor = settings.edit();
		editor.putString("push_channelid", push_channelid);
		editor.putString("push_userid", push_userid);
		editor.commit(); // ????要记得提??
	}

	/**
	 * 获取push_channelid信息
	 *
	 * @param activity
	 * @return
	 */
	public static String getPushInfor_channelid(Context activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String gender = settings.getString("push_channelid", "");
		return gender;
	}

	/**
	 * 获取push_userid信息
	 *
	 * @param activity
	 * @return
	 */
	public static String getPushInfor_userid(Context activity) {
		SharedPreferences settings = activity.getSharedPreferences(SharedPreferencesName, 0);
		String gender = settings.getString("push_userid", "");
		return gender;
	}

	/**
	 * 移除某个key值已经对应的值
	 *
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 *
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 *
	 * @author zhy
	 *
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 *
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}
}
