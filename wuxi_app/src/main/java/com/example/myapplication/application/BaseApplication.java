package com.example.myapplication.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    public static String msg="";
    public static final String TAG = "tag";
    public static int JIANCE_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态  3:调度
//    public static List<UserAccountModel> accountModelList = new ArrayList<>();//登录成功后缓存登录返回的信息
//    public static List<CheckItemModel> checkItemModelList = new ArrayList<>();//获取所有人工检验项目
//    public static List<MyChoiceModel> myChoiceModelList = new ArrayList<>();//Code2选择的列表
//    public static List<CheckItemModel> mycar_checkItemlList = new ArrayList<>();
    public static boolean IsFirst = true;//是否第一次给myChoiceModelList赋值

    public static String W_ItemStart_time = "";//外检开始时间
    public static String W_ItemEnd_time = "";//外检结束时间
    public static String R_ItemStart_time = "";//路试开始时间
    public static String R_ItemEnd_time = "";//路试结束时间
    public static String D_ItemStart_time = "";//底盘动态开始时间
    public static String D_ItemEnd_time = "";//底盘动态结束始时间


    public static BaseApplication mApp = null;
    public List<Activity> mActivitys = new ArrayList();

    public static int screenWidth = 0;
    public long exitTime = 0;
    public Context context_aplication;
//    public static List<UserAccountModel> userAccountModelList = new ArrayList<>();

    public static BaseApplication getSelf() {
        return mApp;
    }

    public static Map<Integer, File> WAIJIAN_PHOTO_PREVIEW_LISTS;//外检拍照缓存照片-预览用


//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase db;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
    public static BaseApplication instances;
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = (BaseApplication) getApplicationContext();
        initError();

        BleManager.getInstance().init(mApp );
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);



//        CrashHandler.getInstance().init(this);

//        Logger.addLogAdapter(new AndroidLogAdapter());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.ALL);
//        builder.addInterceptor(new LogInterceptor());
//第三方的开源库，使用通知显示当前请求的log
//        builder.addInterceptor(new ChuckInterceptor(this));
//全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次
        // (一次原始请求，三次重连请求)，不需要可以设置为0

        instances = this;
//        setDatabase();
    }

    /**
     * 设置greenDao
     */
//    private void setDatabase() {
//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        mHelper = new DaoMaster.DevOpenHelper(this, "db_net_vd_app", null);
//        db = mHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }
    public void pull(Activity paramActivity) {
        for (int i = 0; i < mActivitys.size(); i++) {
            if (this.mActivitys.get(i) == paramActivity) {
                this.mActivitys.remove(i);
                return;
            }
        }
    }

    public void push(Activity paramActivity) {
        for (int i = 0; i < mActivitys.size(); i++) {
            if (this.mActivitys.get(i) == paramActivity) {
                this.mActivitys.add(paramActivity);
                return;
            }
        }
    }

    public void exit(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
        } else {
            for (Activity activity : mActivitys) {
                activity.finish();
            }
            // 退回到桌面，假退出
            // Intent i = new Intent(Intent.ACTION_MAIN);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // i.addCategory(Intent.CATEGORY_HOME);
            // context.startActivity(i);
        }
    }

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, String> errorMap = new HashMap<Integer, String>();

    private void initError() {
        errorMap.put(1, "查询成功");
        errorMap.put(-1, "未登录");
        errorMap.put(-2, "参数不全");
        errorMap.put(-3, "手机号或密码不正确");
        errorMap.put(-4, "短信验证码错误");
        errorMap.put(-5, "手机号码已存在");
    }

    /**
     * 根据status获取错误信息
     *
     * @param status
     * @return
     */
    public String getError(int status) {
        if (errorMap.containsKey(status))
            return errorMap.get(status);
        return "";
    }

}
