package net.com.mvp.ac.commons;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static long order_time_record = 1;// 订单管理-当前时间记录
    public static int order_i_order = 1;// 订单管理-标题栏顺??
    private static final String IMAGE_MIME_TYPE = "image/png";
    private static final Uri STORAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
    public static Activity spact = null;// 用来启动在服务中下载的版本更新的apk
    private static String phoneReg = "^0{0,1}(13[0-9]|14[0-9]|15[0-9]|18[0-9])[0-9]{8}$";
    static ImageView spaceshipImage;
    private static Handler mHandler = new Handler();
    static int aminNum = 1;




    // public static VersionAlertDialog dialog;
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public void isDirExist(String dir) throws IOException {
        String SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        File file = new File(SDCardRoot + dir + File.separator);
        if (!file.exists()) {
            file.mkdirs();
            file.createNewFile();
        }
    }

    /**
     * 1、判断SD卡是否存??
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机IP地址
     *
     * @param
     * @return
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            UtilsLog.e("ifo", ex.toString());
        }
        return "";
    }

    /**
     * 获取手机mac地址
     *
     * @param context
     * @return
     */
    public String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * ????sd卡是否存??
     */
    public static boolean checkSDCard() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            return false;
        }
    }

    private static InputMethodManager imm;

    /**
     * 显示输入??
     */
    public static void showKeyboard(Context context, View focusView) {
        imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入??
     */
    public static void hideKeyboard(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 邮箱验证
     */
    public static boolean checkIsEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 用户密码验证
     */
    public static boolean checkIsPassword(String str) {
        return str != null && !str.equals("") && str.length() >= 6
                && str.length() <= 15;
    }

    /**
     * 用户验证码验??
     */
    public static boolean checkVerificationCode(String str) {
        return str != null && !str.equals("") && str.length() == 4;
    }

    /**
     * 用户名验??
     */
    public static boolean checkIsName(String str) {
        return str != null && !str.equals("");
    }

    /**
     * 非空\敏感词验??
     */
    public static boolean checkIsNotNull(String str) {
        return str == null || str.equals("") || str.equals("你妹");
    }

    private static ProgressDialog myDialog;

    static int count = 1;

    //
    // public static void showProgress(Context activity) {
    // System.err.println("进入showProgress---------" + count);
    // hideProgress();
    // showProgressDialog1(activity);
    // count++;
    // }
    //
    // public static void showProgress(Context activity, String str) {
    // // doShowProgress(activity, str);
    // // showProgressDialog(activity);
    // showProgressDialog1(activity);
    // }

    // public static void showProgress(Context activity, int id) {
    // // doShowProgress(activity, activity.getString(id));
    // // showProgressDialog(activity);
    // showProgressDialog1(activity);
    // }
    //
    // public static void hideProgress() {
    // if (myDialog != null) {
    // myDialog.dismiss();
    // // myDialog.cancel();
    // // myDialog=null;
    // }
    // hideProgressDialog();
    // }

    public static void doShowProgress(Context activity, String str) {
        myDialog = ProgressDialog.show(activity, "", str, true, true);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    // private static Dialog myProgressDialog;
    //
    // public static Dialog showProgressDialog(Context context) {
    // LayoutInflater inflater = LayoutInflater.from(context);
    // View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
    // LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);//
    // 加载布局
    // // main.xml中的ImageView
    // ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
    // TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);//
    // 提示文字
    // // 加载动画
    // Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
    // context, R.anim.loading_animation);
    // // 使用ImageView显示动画
    // spaceshipImage.startAnimation(hyperspaceJumpAnimation);
    // tipTextView.setText("加载信息...");// 设置加载信息
    //
    // myProgressDialog = new Dialog(context, R.style.loading_dialog);//
    // 创建自定义样式dialog
    //
    // myProgressDialog.setCancelable(true);// 可以用??返回键??取消
    // myProgressDialog.setContentView(layout, new LinearLayout.LayoutParams(
    // LinearLayout.LayoutParams.FILL_PARENT,
    // LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
    // myProgressDialog.setCanceledOnTouchOutside(false);
    // myProgressDialog.show();
    // return myProgressDialog;
    // }
    //
    // public static Dialog showProgressDialog1(Context context) {
    // LayoutInflater inflater = LayoutInflater.from(context);
    // View v = inflater.inflate(R.layout.loading_dialog1, null);
    // LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
    // spaceshipImage = (ImageView) v.findViewById(R.id.img);
    // if (mHandler != null) {
    // mHandler.removeCallbacks(mPollTask);
    // mHandler.removeCallbacksAndMessages(null);
    // }
    // mHandler.postDelayed(mPollTask, 30);
    // myProgressDialog = new Dialog(context, R.style.loading_dialog1);
    // myProgressDialog.setCancelable(true);// 可以用??返回键??取消
    // myProgressDialog.setContentView(layout, new LinearLayout.LayoutParams(
    // LinearLayout.LayoutParams.FILL_PARENT,
    // LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
    // myProgressDialog.setCanceledOnTouchOutside(false);
    // myProgressDialog.setOnCancelListener(new OnCancelListener() {
    //
    // @Override
    // public void onCancel(DialogInterface dialog) {
    // try {
    // if (mHandler != null) {
    // mHandler.removeCallbacks(mPollTask);
    // mHandler.removeCallbacksAndMessages(null);
    // }
    // } catch (Exception e) {
    // }
    // }
    // });
    // myProgressDialog.setOnDismissListener(new OnDismissListener() {
    //
    // @Override
    // public void onDismiss(DialogInterface dialog) {
    // try {
    // if (mHandler != null) {
    // mHandler.removeCallbacks(mPollTask);
    // mHandler.removeCallbacksAndMessages(null);
    // }
    // } catch (Exception e) {
    // }
    // }
    // });
    // myProgressDialog.show();
    // return myProgressDialog;
    // }

    // 加载数据动画
    private static void updateDisplay(int signalEMA) {
        // System.err.println("updateDisplay----");
        switch (signalEMA) {
            case 1:
                spaceshipImage.getDrawable().setLevel(1);
                break;
            case 2:
                spaceshipImage.getDrawable().setLevel(2);
                break;
            case 3:
                spaceshipImage.getDrawable().setLevel(3);
                break;
            case 4:
                spaceshipImage.getDrawable().setLevel(4);
                break;
            case 5:
                spaceshipImage.getDrawable().setLevel(5);
                break;
            case 6:
                spaceshipImage.getDrawable().setLevel(6);
                break;
            case 7:
                spaceshipImage.getDrawable().setLevel(7);
                break;
            case 8:
                spaceshipImage.getDrawable().setLevel(8);
                break;
            case 9:
                spaceshipImage.getDrawable().setLevel(9);
                break;
            case 10:
                spaceshipImage.getDrawable().setLevel(10);
                break;
            case 11:
                spaceshipImage.getDrawable().setLevel(11);
                break;
            case 12:
                spaceshipImage.getDrawable().setLevel(12);
                break;
            case 13:
                spaceshipImage.getDrawable().setLevel(13);
                break;
            case 14:
                spaceshipImage.getDrawable().setLevel(14);
                break;
            case 15:
                spaceshipImage.getDrawable().setLevel(15);
                break;
            case 16:
                spaceshipImage.getDrawable().setLevel(16);
                break;
            case 17:
                spaceshipImage.getDrawable().setLevel(17);
                break;
            case 18:
                spaceshipImage.getDrawable().setLevel(18);
                break;
            case 19:
                spaceshipImage.getDrawable().setLevel(19);
                break;
            case 20:
                spaceshipImage.getDrawable().setLevel(20);
            default:
                break;
        }
    }

    private static Runnable mPollTask = new Runnable() {
        public void run() {
            // System.err.println("aminNum=111111="+aminNum);
            aminNum++;
            if (aminNum > 20) {
                aminNum = 1;
            }
            updateDisplay(aminNum);
            mHandler.postDelayed(mPollTask, 30);// 定时任务，比??.3秒以后重新调用mPollTask
        }
    };

    // public static void hideProgressDialog() {
    // try {
    // if (myProgressDialog != null) {
    // myProgressDialog.dismiss();
    // }
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
    // }

    /**
     * ????按钮的对话框
     *
     * @param msg
     * @param title 按钮文本
     * @param click new DialogInterface.OnClickListener()
     */
    public static void showDialog(Activity activity, String msg, String title,
                                  String btnStr, DialogInterface.OnClickListener click) {
        showDialog(activity, msg, title, btnStr, click, null, null);
    }

    //	/**
//	 * 两个按钮的对话框
//	 *
//	 * @param activity
//	 * @param msg
//	 * @param title
//	 * @param btnStr1按钮1文本
//	 * @param click1按钮1事件
//	 *            new DialogInterface.OnClickListener()
//	 * @param btnStr2按钮2文本
//	 * @param click2按钮2事件
//	 *            new DialogInterface.OnClickListener()
//	 */
    public static void showDialog(Activity activity, String msg, String title,
                                  String btnStr1, DialogInterface.OnClickListener click1,
                                  String btnStr2, DialogInterface.OnClickListener click2) {
        final Builder builder = new Builder(activity);
        builder.setMessage("" + msg);
        builder.setTitle("" + title);
        // builder.setOnKeyListener(new OnKeyListener() {
        //
        // @Override
        // public boolean onKey(DialogInterface dialog, int keyCode,
        // KeyEvent event) {
        // // TODO Auto-generated method stub
        // if (keyCode == KeyEvent.KEYCODE_BACK
        // && event.getRepeatCount() == 0) {
        // builder.create().dismiss();
        // }
        // return true;
        // }
        // });
        if (click1 != null) {
            builder.setNegativeButton("" + btnStr1, click1);
        }
        if (click2 != null) {
            builder.setNegativeButton("" + btnStr2, click2);
        }
        builder.create().show();
    }

    public static String getMD5Str(String data) {
        byte[] md5 = null;
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            md5Digest.update(data.getBytes("UTF-8"));
            md5 = md5Digest.digest();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bytesToHexStr(md5);
    }

    public static String bytesToHexStr(byte b[]) {
        return bytesToHexStr(b, 0, b.length);
    }

    public static String bytesToHexStr(byte b[], int start, int len) {
        StringBuffer str = new StringBuffer();
        for (int i = start; i < start + len; i++) {
            str.append(String.format("%02x", b[i]));
        }
        return str.toString();
    }

    /**
     * 判断strJson是否是JSON数据
     *
     * @param strJson
     * @return 是：true ,不是：false
     */
    public static boolean isJson(String strJson) {
        if (strJson == null || strJson.trim().length() == 0) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            JSONObject joObject = new JSONObject(strJson);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static Uri insertPicToPhoto(Activity ac, String filename,
                                       String filepath) {
        ContentResolver contentResolver = ac.getContentResolver();
        ContentValues values = new ContentValues(7);

        // values.put(Images.Media.TITLE, title);
        values.put(Images.Media.DISPLAY_NAME, filename);
        // values.put(Images.Media.DATE_TAKEN, dateTaken);
        values.put(Images.Media.MIME_TYPE, IMAGE_MIME_TYPE);
        // values.put(Images.Media.ORIENTATION, degree[0]);
        values.put(Images.Media.DATA, filepath);
        // values.put(Images.Media.SIZE, size);

        return contentResolver.insert(STORAGE_URI, values);
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        // options.inSampleSize = calculateInSampleSize(options, 200, 250);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放??
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 判断是否是图??
     *
     * @param fileName
     * @return
     */
    public static boolean isPicOrNot(String fileName) {
        if (fileName.contains(".")) {
            fileName = fileName.substring(fileName.lastIndexOf(".") + 1);// "jpeg",
            // "gif",
            fileName = fileName.toLowerCase(); // "png"
            if (fileName.trim().compareTo("jpg") == 0
                    || fileName.trim().compareTo("jpeg") == 0
                    || fileName.trim().compareTo("gif") == 0
                    || fileName.trim().compareTo("png") == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否升级
     *
     * @param
     * @return
     */
    // 检测是否需要升级 local：本地版本号 service：服务端返回的版本号
    public static boolean checkUpdate(final String local, final String service) {
        boolean needUpdate = false;
        String[] localgroup = local.split("\\.");// 本地版本号分割
        String[] servicegroup = service.split("\\.");// 服务端版本号分割
        int local0 = Integer.parseInt(localgroup[0]);
        int local1 = Integer.parseInt(localgroup[1]);
        // int local2=Integer.parseInt(localgroup[2]);
        int service0 = Integer.parseInt(servicegroup[0]);
        int service1 = Integer.parseInt(servicegroup[1]);
        // int service2=Integer.parseInt(servicegroup[2]);
        // Log.d("checkUpdate","*************local-"+local);
        // Log.d("checkUpdate","*************service-"+service);
        if (local0 < service0) {
            needUpdate = true;
        } else // else if (local2<service2){
// needUpdate = true;
// }
            needUpdate = local1 < service1;

        return needUpdate;

    }

    // 获取当前版本号
    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            // Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 得到版本??
     *
     * @param paramContext
     * @return
     */
    public static int getVersion(Context paramContext) {
        PackageManager localPackageManager = paramContext.getPackageManager();
        String str1 = paramContext.getPackageName();
        PackageInfo localPackageInfo = null;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(str1, 64);
        } catch (NameNotFoundException e) {
            localPackageInfo = null;
        }
        if (localPackageInfo == null) {
            return -1;
        } else {
            return localPackageInfo.versionCode;

        }
    }

    /**
     * 非空判断??",null,"null"??
     *
     * @param value 要验证字符串
     * @return
     */
    public static boolean isNull(String value) {
        return value == null || value.trim().equals("")
                || value.trim().equals("null");
    }

    /**
     * @param format 时间格式
     * @return 当前时间
     */
    public static String getCurrenttime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time = sdf.format(new Date());
        return time;
    }

    /**
     * 得到本机电话号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();// 手机号码
        String imei = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();
        UtilsLog.d("====", tel + "=phone=" + imei + "==" + imsi);
        return tel;
    }

    // 相册中??取图??
    // protected void doPickPhotoFromGallery() {
    // try {
    // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    // intent.setType("image/*");
    // // intent.putExtra("crop", "true");
    // // intent.putExtra("aspectX", 1);
    // // intent.putExtra("aspectY", 1);
    // // intent.putExtra("outputX", 80);
    // // intent.putExtra("outputY", 80);
    // // intent.putExtra("return-data", true);
    // startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
    // } catch (ActivityNotFoundException e) {
    // Toast.makeText(this, R.string.photoPickerNotFoundText1,
    // Toast.LENGTH_LONG).show();
    // }
    // }

//	/**
//	 * @author Duke
//	 * @return 图片的uri地址
//	 * @exception 图片的地
//	 *                ????存至sp?? 。因为在调起三星S4的拍照程序时，会把name清空（其他手机没有出现该问题??
//	 */
    // private Uri getUri(){
    // Calendar c = Calendar.getInstance();
    // String date = c.get(Calendar.YEAR) + "" + (c.get(Calendar.MONTH) + 1) +
    // "" + c.get(Calendar.DAY_OF_MONTH) + "" + c.get(Calendar.HOUR_OF_DAY) + ""
    // + c.get(Calendar.MINUTE) + "" + c.get(Calendar.SECOND);
    // filePath= date + ".jpg";
    // AppUtil.saveString(this, "path", filePath);
    //
    // File photo = new File(camareFile, filePath);
    // return Uri.fromFile(photo);
    // }

    // 新浪微博

    // 跳转到网??
    public static void OpenUrl(Context c, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            c.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // ????验证身份??
    public static boolean isUserId(String id) {
        if (isNull(id)) {
            return false;
        } else if (id.length() != 15 && id.length() != 18) {
            return false;
        } else if (id.length() == 18 && !isNumeric(id.substring(0, 17))) {// 1-17位必须是数字（如果身份证长度??8位）
            return false;
        } else if (id.length() == 15 && !isNumeric(id)) {// 必须是数字（如果身份证长度是15位）
            return false;
        }
        return true;
    }

    /**
     * ======================================================================
     * 功能：判断字符串是否为数??
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
        /*
		 * 判断????字符时??为数??if(Character.isDigit(str.charAt(0))) { return true; }
		 * else { return false; }
		 */
    }

    /**
     * 校验银行卡卡??
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验??
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    // ????手机号是否填写正??
    public static boolean checkIsCellphone(String phonenumber) {
        Pattern p = Pattern.compile(phoneReg);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    // 比较两个时间戳相差天??
    public static int dateDiff(long fromDate, long toDate) {
        int days = 0;
        try {
            days = (int) Math.abs((fromDate - toDate) / (24 * 60 * 60 * 1000)) + 1;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return days;
    }

    public static void toast(Context paramContext, String paramString) {
        toast(paramContext, paramString, true);
    }

    public static void toast(Context paramContext, String paramString,
                             int paramInt) {
        toast(paramContext, paramString, true, paramInt);
    }

    public static void toast(Context paramContext, String paramString,
                             boolean paramBoolean) {
        toast(paramContext, paramString, paramBoolean, 1);
    }

    public static void toast(Context paramContext, String paramString,
                             boolean paramBoolean, int paramInt) {
        if (!paramBoolean)
            return;
        // BaseApplication.toastMgr.builder.display(paramString, paramInt);
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    /**
     * 弹出错误提示框
     *
     * @param context
     */
    public static void showErrorDialog(Context context, String msg) {
        Builder builder = new Builder(context);
        builder.setTitle(null);
        builder.setMessage(msg);
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        builder.show();
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    public static String changetime(long sss) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long javaTime = (sss * 10000 - 621355968000000000l) / 10000 + 8 * 3600;
        // - 8 * 3600* 1000;
        Date date = new Date(javaTime);

        return formatter.format(date) + "";
    }

    public static String changetimetofen(long sss) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long javaTime = (sss * 10000 - 621355968000000000l) / 10000 + 8 * 3600;
        Date date = new Date(javaTime);
        return formatter.format(date) + "";
    }

    public static String changetimetoday(long sss) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long javaTime = (sss * 10000 - 621355968000000000l) / 10000 + 8 * 3600;
        // - 8 * 3600* 1000;
        Date date = new Date(javaTime);

        return formatter.format(date) + "";
    }

    public static String changetimenew(long sss) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long javaTime = (sss - 621355968000000000l) / 10000 + 8 * 3600;
        // - 8 * 3600* 1000;
        Date date = new Date(javaTime);

        return formatter.format(date) + "";
    }

    public static long changetimetoc(long sss) {

        long cTime = sss * 10000 + 621355968000000000L;

        return cTime;
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static String shijiancha(long sss) {
        String times = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long javaTime = (sss * 10000 - 621355968000000000l) / 10000;
        Date d1 = new Date(javaTime);
        Date d2 = new Date(System.currentTimeMillis());// 你也可以获取当前时间
        long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别

        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);

        if (days > 7) {
            times = "一周前";
        } else if (days > 0) {
            times = days + "天前";
        } else if (hours > 0) {
            times = hours + "小时前";
        } else if (minutes > 0) {
            times = minutes + "分钟前";
        } else {
            times = "刚刚";
        }

        return times;
    }

    public static boolean isshijiannei(long sss) {
        String times = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long javaTime = (sss * 10000 - 621355968000000000l) / 10000;
        Date d1 = new Date(javaTime);
        Date d2 = new Date(System.currentTimeMillis());// 你也可以获取当前时间
        long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别

        return diff <= 0;
    }

//	// Mydialog
//	public static MyDialog mydialog;
//
//	//
//	public static void showDialog_edit(final Context context, String title,
//			String str, final TextView txt_content) {
//		if (mydialog != null) {
//			mydialog.dismiss();
//		}
//		// 引用自定义布局
//		View v = LayoutInflater.from(context).inflate(
//				R.layout.my_dialog_edittext2, null);
//
//		final EditText textView = (EditText) v.findViewById(R.id.textv_content);
//		// textView.setText(title);
//
//		mydialog = new MyDialog(context, title, v, new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				txt_content.setText(textView.getText().toString());
//				mydialog.dismiss();
//			}
//		}, 1, str);
//		// 显示dialog
//		mydialog.setCanceledOnTouchOutside(false);
//		mydialog.setCancelable(false);
//		mydialog.show();
//	}

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static String getCurrentTime3() {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = tempDate.format(new Date());
        return datetime;
    }

    // 小数点后面两位
    public static String FormatTwo(String number) {
        DecimalFormat format = new DecimalFormat("0.00");
        String a = format.format(new BigDecimal(number));
        return a;

    }
}
