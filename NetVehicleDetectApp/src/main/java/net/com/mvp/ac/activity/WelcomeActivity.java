package net.com.mvp.ac.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

//欢迎页
public class WelcomeActivity extends BaseActivity {

    public static String[] permissionArray = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CALENDAR,
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.READ_PHONE_STATE,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_welcome);

        PermissionUtils.checkPermissionArray(this, permissionArray, 2);
        PermissionUtils.checkPermissionArray(this, permissionArray, 5);

        if (checkMounted(WelcomeActivity.this, "/mnt/sdcard")) {
            Log.e("tag", "内置SD卡可用");
        } else {
            Log.e("tag", "内置SD卡cccc可用");
        }

        if (checkMounted(WelcomeActivity.this, "/mnt/extsd")) {
            Log.e("tag", "外置SD卡可用");
        } else {
            Log.e("tag", "外置SD卡bbbbbb可用");
        }

//        String sdFile = Environment.getExternalStorageDirectory().toString();
//        Log.e("tag", "sdFile==" + sdFile);
//        String state = Environment.getExternalStorageState();
//        Log.e("tag", "state==" + state);
//        Log.e("tag", "Environment.MEDIA_MOUNTED==" + Environment.MEDIA_MOUNTED);
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            Log.e("tag", "=========_==========");
//        } else {
//            Toast.makeText(this, "请确认已经插入SD卡111111", Toast.LENGTH_SHORT).show();
//            Log.e("tag", "请确认已经插入SD卡");
//        }
        phoneInfor();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                myhandler.sendMessage(msg);
            }
        };
        myhandler.postDelayed(runnable, 2000);// 打开定时器，执行操作
    }

    /**
     * 检查是否挂载
     */
    public static boolean checkMounted(Context context, String mountPoint) {
        if (mountPoint == null) {
            return false;
        }
        StorageManager storageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getVolumeState = storageManager.getClass().getMethod(
                    "getVolumeState", String.class);
            String state = (String) getVolumeState.invoke(storageManager,
                    mountPoint);
            return Environment.MEDIA_MOUNTED.equals(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Myhandler myhandler = new Myhandler(this);

    private static class Myhandler extends Handler {

        private final WeakReference<WelcomeActivity> weakReference;

        public Myhandler(WelcomeActivity welcomeActivity) {
            weakReference = new WeakReference<>(welcomeActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity welcomeActivity = weakReference.get();
            if (welcomeActivity != null) {

//
//                if (!SharedPreferencesUtils.getFirstUseApp(welcomeActivity)) {
//                    // 第一次执行则关闭定时执行操作
//                    Intent intent = new Intent(welcomeActivity, VerifyCodeActivity.class);
//                    welcomeActivity.startActivity(intent);
//                } else {
//                    String imei = CommonUtils.getIMEI(welcomeActivity);
//                    boolean result = CommonUtils.checkAuthorizationCode(imei + "999",
//                            SharedPreferencesUtils.getShouQuanMa(welcomeActivity));
//                    if (result) {
                Intent intent = new Intent(welcomeActivity, LoginActivity.class);
                welcomeActivity.startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(welcomeActivity, VerifyCodeActivity.class);
//                        welcomeActivity.startActivity(intent);
//                    }

//                }

//                Intent intent = new Intent(welcomeActivity, LoginActivity.class);
//                welcomeActivity.startActivity(intent);

                welcomeActivity.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myhandler.removeCallbacksAndMessages(null);
    }

    public   String phoneInfor() {
        String phoneInfo = "";
        TelephonyManager mTm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context
                .WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String imei = mTm.getDeviceId();//得到用户Id

        String imsi = mTm.getSubscriberId();

        String deviceid = mTm.getDeviceId();//获取智能设备唯一编号

        String te1 = mTm.getLine1Number();//获取本机号码

//        String imei = mTm.getSimSerialNumber();//获得SIM卡的序号

        String mtype = android.os.Build.MODEL; // 手机型号

        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得

        String result = wifiInfo.getMacAddress();//MAC地址

        String mtyb = android.os.Build.BRAND;//手机品牌

        phoneInfo = "Product: " + android.os.Build.PRODUCT;

        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;

        phoneInfo += ", TAGS: " + android.os.Build.TAGS;

        phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;

        phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;

        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;

        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;

        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;

        phoneInfo += ", BRAND: " + android.os.Build.BRAND;

        phoneInfo += ", BOARD: " + android.os.Build.BOARD;

        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;

        phoneInfo += ", ID: " + android.os.Build.ID;

        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;

        phoneInfo += ", USER: " + android.os.Build.USER;
        phoneInfo += ", mtype: " + mtype;

        Log.e("tag", "phoneInfor==" + phoneInfo);

        return phoneInfo;

    }


    //获取手机安装的应用信息（排除系统自带）

    private String getAllApp() {

        String result = "";

        List<PackageInfo> packages = this.getPackageManager().getInstalledPackages(0);

        for (PackageInfo i : packages) {

            if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                result += i.applicationInfo.loadLabel(this.getPackageManager()).toString() + ",";

            }

        }

        return result.substring(0, result.length() - 1);

    }


    /**
     * 获取手机是否root信息
     *
     * @return
     */

    private String isRoot() {

        String bool = "Root:false";

        try {

            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {

                bool = "Root:false";

            } else {

                bool = "Root:true";

            }

        } catch (Exception e) {

        }

        return bool;

    }
}
