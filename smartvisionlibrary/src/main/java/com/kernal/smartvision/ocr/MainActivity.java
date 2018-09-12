package com.kernal.smartvision.ocr;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kernal.smartvision.utils.CameraSetting;
import com.kernal.smartvision.utils.Utills;


/**
 * 项目名称：慧视OCR
 * 类名称：首界面
 * 类描述：
 * 创建人：黄震
 * 创建时间：2016/02/03
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class MainActivity extends Activity {
    private int width, height;
    private DisplayMetrics dm = new DisplayMetrics();
    private int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private ImageButton btn_set, btn_app_logo, btn_scanRecog, btn_import;
    private int index = 0;
    public static boolean moudle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        setContentView(getResources().getIdentifier("vin_activity_main", "layout", getPackageName()));
        ShowResultActivity.hiddenVirtualButtons(getWindow().getDecorView());
        findview();

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE, // 读取权限
            Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
            Manifest.permission.FLASHLIGHT};
    /**
     * @return ${return_type}    返回类型
     * @throws
     * @Title: 主界面UI布局
     * @Description: 主要动态布局界面上的控件
     */
    private final int SELECT_RESULT_CODE = 3;

    public void findview() {
        CameraSetting.getInstance(this).hiddenVirtualButtons(getWindow().getDecorView());

        btn_set = (ImageButton) this.findViewById(getResources().getIdentifier("btn_set", "id", getPackageName()));

        btn_app_logo = (ImageButton) this.findViewById(getResources().getIdentifier("btn_app_logo", "id", getPackageName()));
        btn_scanRecog = (ImageButton) this.findViewById(getResources().getIdentifier("btn_scanRecog", "id", getPackageName()));
        btn_import = (ImageButton) this.findViewById(getResources().getIdentifier("btn_import", "id", getPackageName()));
        btn_scanRecog.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                moudle = true;
                Intent cameraintent = new Intent(MainActivity.this, CameraActivity.class);
                if (Build.VERSION.SDK_INT >= 23) {
                    CheckPermission checkPermission = new CheckPermission(MainActivity.this);
                    if (checkPermission.permissionSet(PERMISSION)) {
                        PermissionActivity.startActivityForResult(MainActivity.this, 0, "CameraActivity", PERMISSION);
                        MainActivity.this.finish();
                    } else {
                        startActivity(cameraintent);
                        overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
                        MainActivity.this.finish();
                    }
                } else {
                    startActivity(cameraintent);
                    overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
                    MainActivity.this.finish();
                }
            }
        });
        btn_import.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moudle = false;
                Intent selectIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent wrapperIntent = Intent.createChooser(selectIntent, "请选择一张图片");
                if (Build.VERSION.SDK_INT >= 23) {
                    CheckPermission checkPermission = new CheckPermission(MainActivity.this);
                    if (checkPermission.permissionSet(PERMISSION)) {
                        PermissionActivity.startActivityForResult(MainActivity.this, 0, "choice", PERMISSION);
                    } else {
                        try {
                            startActivityForResult(wrapperIntent, SELECT_RESULT_CODE);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    try {
                        startActivityForResult(wrapperIntent, SELECT_RESULT_CODE);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_set.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                if (Build.VERSION.SDK_INT >= 23) {

                    CheckPermission checkPermission = new CheckPermission(MainActivity.this);
                    if (checkPermission.permissionSet(PERMISSION)) {
                        PermissionActivity.startActivityForResult(MainActivity.this, 0, "SettingActivity", PERMISSION);
                    } else {
                        startActivity(intent);
                        overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
//			                MainActivity.this.finish();			
                    }
                } else {
                    startActivity(intent);
                    overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));

                }
//				Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
//                overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
            }
        });
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (width * 0.2), (int) (width * 0.2));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = (int) (0.4 * height);
        btn_scanRecog.setLayoutParams(params);
        params = new RelativeLayout.LayoutParams((int) (width * 0.15), (int) (width * 0.15));
        params.leftMargin = (int) (width * 0.7);
        params.topMargin = (int) (0.85 * height);
        btn_import.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams((int) (width * 0.4), (int) (height * 0.18));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = (int) (0.2 * height);
        btn_app_logo.setLayoutParams(params);
        params = new RelativeLayout.LayoutParams((int) (width * 0.1), (int) (width * 0.1));
        params.leftMargin = (int) (width * 0.75);
        params.topMargin = (int) (0.02 * height);
        btn_set.setLayoutParams(params);
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title: CreatDDialog
     * @Description: TODO(这里用一句话描述这个方法的作用) 创建选择对话框，选择识别模式,只在进入视频识别时进行选择，拍照识别默认快速识别模式
     */
    private void CreatDialog() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("请选择识别模式");
        final String[] model = {"扫描识别", "导入识别"};

        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'扫描识别' 会被勾选上   1 表示导入识别
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(model, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
//                isTouch = true;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                isTouch = false;
                if (index == 0) {
                    //扫描识别
                    moudle = true;
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    Intent cameraintent = new Intent(MainActivity.this, CameraActivity.class);
                    if (Build.VERSION.SDK_INT >= 23) {
                        CheckPermission checkPermission = new CheckPermission(MainActivity.this);
                        if (checkPermission.permissionSet(PERMISSION)) {
                            PermissionActivity.startActivityForResult(MainActivity.this, 0, "CameraActivity", PERMISSION);
                            MainActivity.this.finish();
                        } else {

                            startActivity(cameraintent);
                            overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
                            MainActivity.this.finish();
                        }
                    } else {

                        startActivity(cameraintent);
                        overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
                        MainActivity.this.finish();
                    }
                } else {
                    //导入识别
                    moudle = false;
                    Intent selectIntent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent wrapperIntent = Intent.createChooser(selectIntent, "请选择一张图片");
                    if (Build.VERSION.SDK_INT >= 23) {
                        CheckPermission checkPermission = new CheckPermission(MainActivity.this);
                        if (checkPermission.permissionSet(PERMISSION)) {
                            PermissionActivity.startActivityForResult(MainActivity.this, 0, "choice", PERMISSION);
                        } else {
                            try {
                                startActivityForResult(wrapperIntent, SELECT_RESULT_CODE);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        try {
                            startActivityForResult(wrapperIntent, SELECT_RESULT_CODE);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            String picPathString = null;
            Uri uri = data.getData();
            picPathString = Utills.getPath(getApplicationContext(), uri);
            if (picPathString != null && !picPathString.equals("") && !picPathString.equals(" ") && !picPathString.equals("null")) {
                if (picPathString.endsWith(".jpg") || picPathString.endsWith(".JPG") || picPathString.endsWith(".png") || picPathString.endsWith(".PNG")) {
                    Intent intentResult = new Intent(getApplicationContext(), PicRecogActivity.class);
                    intentResult.putExtra("recogImagePath", picPathString);
                    startActivity(intentResult);
                    this.finish();
                } else {
                    Toast.makeText(this, "请选择一张正确的图片", Toast.LENGTH_SHORT).show();
                }

//                finish();
            }

        }
    }
}
