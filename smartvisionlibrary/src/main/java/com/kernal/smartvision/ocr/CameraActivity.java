package com.kernal.smartvision.ocr;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kernal.smartvision.adapter.CameraDocTypeAdapter;
import com.kernal.smartvision.adapter.RecogResultAdapter;
import com.kernal.smartvision.utils.CameraParametersUtils;
import com.kernal.smartvision.utils.CameraSetting;
import com.kernal.smartvision.utils.RecogOpera;
import com.kernal.smartvision.utils.VINRecogParameter;
import com.kernal.smartvision.utils.VINRecogResult;
import com.kernal.smartvision.view.HorizontalListView;
import com.kernal.smartvision.view.ViewfinderView;
import com.kernal.smartvisionocr.model.RecogResultModel;
import com.kernal.smartvisionocr.utils.KernalLSCXMLInformation;
import com.kernal.smartvisionocr.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：慧视OCR 类名称：相机界面 类描述： 创建人：黄震 创建时间：2016/02/03 修改人：${user} 修改时间：${date}
 * ${time} 修改备注：
 */
public class CameraActivity extends Activity implements SurfaceHolder.Callback,
        Camera.PreviewCallback {
    private int srcWidth, srcHeight, screenWidth, screenHeight;
    ;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private List<Integer> srcList = new ArrayList<Integer>();// 拍照分辨率集合
    private DisplayMetrics dm = new DisplayMetrics();
    private int selectedTemplateTypePosition = 0;
    private Vibrator mVibrator;
    private int tempUiRot = 0;
    private boolean isFinishActivity = false;
    Toast toast=null;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==101){
                if(toast!=null){
                    toast.setText("识别错误，错误码：" + mRecogOpera.error);
                }else{
                    toast=  Toast.makeText(getApplicationContext(),
                            "识别错误，错误码：" + mRecogOpera.error, Toast.LENGTH_LONG);

                }
                toast.show();
            }else {
                getPhoneSizeAndRotation();
                if (recogResultAdapter.isRecogSuccess) {
                    // 识别完成旋转时，先将当前识别字段的选中状态置为初始状态再消除布局View
                    ViewfinderView.fieldsPosition = 0;
                }
                RemoveView();
                vinRecogParameter.isFirstProgram = true;
                if (msg.what == 5) {
                    if (islandscape) {
                        LandscapeView();
                    } else {
                        PortraitView();
                    }

                    if (!recogResultAdapter.isRecogSuccess) {
                        AddView();
                    }
                } else {
                    SetScreenOritation();
                    changeData();
                    if (recogResultAdapter.isRecogSuccess) {

                    } else {
                        AddView();
                    }

                    if (camera != null) {
                        rotation = CameraSetting.getInstance(CameraActivity.this)
                                .setCameraDisplayOrientation(uiRot);
                        camera.setDisplayOrientation(rotation);
                    }

                }

            }
            }

    };
    private int rotation = 0; // 屏幕取景方向
    private CameraParametersUtils cameraParametersUtils;
    private RelativeLayout.LayoutParams layoutParams;
    private ListView type_template_listView;
    private HorizontalListView type_template_HorizontallistView;
    public ViewfinderView myViewfinderView;
    private ImageView iv_camera_back, iv_camera_flash, scanHorizontalLineImageView;
    private KernalLSCXMLInformation wlci_Landscape, wlci_Portrait, wlci;
    public static RelativeLayout re, bg_template_listView;
    private CameraDocTypeAdapter adapter;
    public static RecogResultAdapter recogResultAdapter;
    private int[] regionPos = new int[4];// 敏感区域
    private Camera.Size size;
    private int[] nCharCount = new int[2];// 返回结果中的字符数
    private byte[] data;
    private ImageButton imbtn_takepic;
    public static List<RecogResultModel> recogResultModelList = new ArrayList<RecogResultModel>();
    private boolean isOnClickRecogFields = false;// 点击识别结果分项，
    private int tempPosition = -1;// -1是初始化的值；-2是识别完成后再点击识别不对的项，隐藏确定按钮，识别被选择的项
    private ArrayList<String> list_recogSult;
    private int returnResult = -1;// 测试返回值
    private boolean isChangeType = false;// 是否切换过模板类型
    private int iTH_InitSmartVisionSDK = -1;
    private String SavePicPath;// 图片路径
    private ArrayList<String> savePath;// 图片路径的集合
    private String Imagepath;// 点击拍照按钮 保存的完整图像的路径
    private int[] LeftUpPoint = new int[2];
    private int[] RightDownPoint = new int[2];
    private int position;// 记录当前识别字段的下标
    private boolean isClick = false;// 是否点击了识别结果按钮
    private boolean isFirstIn = true;
    private boolean islandscape = false;// 是否为横向
    private RecogResultModel recogResultModel;
    private String[] httpContent;//上传服务的内容  包括图片路径  以及 识别结果
    public static byte[] Data;
    private boolean isSetZoom = false;
    private Animation verticalAnimation;
    private RecogOpera mRecogOpera;
    /**
     * 自动对焦
     */
    private Handler mAutoFocusHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                CameraSetting.getInstance(CameraActivity.this).autoFocus(camera);
                mAutoFocusHandler.sendEmptyMessageDelayed(
                        100, 2500);
            }

        }
    };
    private int uiRot;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 3,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1));
    private int sum = 0;
    private Runnable initCameraParams = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (camera != null) {
                if (islandscape) {
                    CameraSetting.getInstance(CameraActivity.this)
                            .setCameraParameters(CameraActivity.this,
                                    surfaceHolder, CameraActivity.this, camera,
                                    (float) srcWidth / srcHeight, srcList, false,
                                    rotation, isSetZoom);

                } else {
                    CameraSetting.getInstance(CameraActivity.this)
                            .setCameraParameters(CameraActivity.this,
                                    surfaceHolder, CameraActivity.this, camera,
                                    (float) srcHeight / srcWidth, srcList, false,
                                    rotation, isSetZoom);

                }
                size = camera.getParameters().getPreviewSize();
            }
            if (SharedPreferencesHelper.getBoolean(CameraActivity.this,
                    "isOpenFlash", false)) {
                iv_camera_flash.setImageResource(getResources().getIdentifier(
                        "flash_off", "drawable", getPackageName()));
                CameraSetting.getInstance(CameraActivity.this).openCameraFlash(camera);
            } else {
                iv_camera_flash.setImageResource(getResources().getIdentifier(
                        "flash_on", "drawable", getPackageName()));
                CameraSetting.getInstance(CameraActivity.this).closedCameraFlash(camera);
            }
        }

    };
    public Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            CloseCameraAndStopTimer(1);
            regionPos = mRecogOpera.regionPos;
            list_recogSult.clear();
            list_recogSult
                    .add(recogResultModelList.get(0).resultValue);
            String httpPath = mRecogOpera.savePicture(vinRecogParameter.data, 0, camera.getParameters().getPreviewSize(), rotation);
            Intent intent = new Intent(CameraActivity.this,
                    ShowResultActivity.class);
            intent.putStringArrayListExtra("recogResult",
                    list_recogSult);
            intent.putStringArrayListExtra("savePath", savePath);
            intent.putExtra(
                    "templateName",
                    wlci.template.get(selectedTemplateTypePosition).templateName);
            intent.putExtra("httpPath", httpPath);
            intent.putExtra("rotation", rotation);
            intent.putExtra("regionPos", regionPos);
            startActivity(intent);
            overridePendingTransition(
                    getResources().getIdentifier("zoom_enter",
                            "anim",
                            getApplication().getPackageName()),
                    getResources().getIdentifier("push_down_out",
                            "anim",
                            getApplication().getPackageName()));
            CameraActivity.this.finish();


        }
    };
    public Runnable finishActivity = new Runnable() {
        @Override
        public void run() {
            CloseCameraAndStopTimer(0);
            Intent intent = new Intent(CameraActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(
                    getResources().getIdentifier("zoom_enter", "anim",
                            getApplication().getPackageName()),
                    getResources().getIdentifier("push_down_out", "anim",
                            getApplication().getPackageName()));
            CameraActivity.this.finish();
        }
    };
    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getResources().getIdentifier("activity_camera",
                "layout", getPackageName()));
        // 已写入的情况下，根据version.txt中版本号判断是否需要更新，如不需要不执行写入操作
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        cameraParametersUtils = new CameraParametersUtils(this);
        uiRot = getWindowManager().getDefaultDisplay().getRotation();// 获取屏幕旋转的角度
        getPhoneSizeAndRotation();
        mRecogOpera = new RecogOpera(CameraActivity.this);
        mRecogOpera.initOcr();
        wlci_Landscape = mRecogOpera.getWlci_Landscape();
        wlci_Portrait = mRecogOpera.getWlci_Portrait();
        tempPosition = -1;
        findView();
        ClickEvent();
        SetScreenOritation();
        initData();
        AddView();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(CameraActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // 获取设备分辨率 不受虚拟按键影响
    public void getPhoneSizeAndRotation() {
        cameraParametersUtils.setScreenSize(CameraActivity.this);
        srcWidth = cameraParametersUtils.srcWidth;
        srcHeight = cameraParametersUtils.srcHeight;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    // 设置横竖屏状态和变换数据对象
    private void SetScreenOritation() {
        if (uiRot == 0 || uiRot == 2) // 竖屏状态下
        {
            islandscape = false;
            wlci = wlci_Portrait;
            PortraitView();
        } else { // 横屏状态下
            islandscape = true;
            wlci = wlci_Landscape;
            LandscapeView();
        }

    }

    /**
     * @param
     * @return void 返回类型
     * @throws
     * @Title: initData
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    private void initData() {
        // 初始化数据
        if (islandscape) {
            adapter = new CameraDocTypeAdapter(this, wlci.template,
                    screenWidth, screenWidth);
        } else {
            adapter = new CameraDocTypeAdapter(this, wlci.template,
                    screenWidth, screenHeight);
        }
        selectedTemplateTypePosition = ShowResultActivity.selectedTemplateTypePosition;
        adapter.selectedPosition = selectedTemplateTypePosition;
        recogResultModelList.clear();

        for (int i = 0; i < wlci.fieldType.get(
                wlci.template.get(selectedTemplateTypePosition).templateType)
                .size(); i++) {
            recogResultModel = new RecogResultModel();
            recogResultModel.resultValue = wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(i).name
                    + ":";
            recogResultModel.type = wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(i).type;

            recogResultModelList.add(recogResultModel);
        }

        // TODO Auto-generated method stub
        if (islandscape) {
            type_template_listView.setAdapter(adapter);
            recogResultAdapter = new RecogResultAdapter(this,
                    recogResultModelList, screenWidth, screenWidth);
        } else {
            type_template_HorizontallistView.setAdapter(adapter);
            recogResultAdapter = new RecogResultAdapter(this,
                    recogResultModelList, screenWidth, screenHeight);
        }
        savePath = new ArrayList<String>();
    }

    public void changeData() {
        if (islandscape) {
            adapter = new CameraDocTypeAdapter(this, wlci.template,
                    screenWidth, screenWidth);
            type_template_listView.setAdapter(adapter);
        } else {
            adapter = new CameraDocTypeAdapter(this, wlci.template,
                    screenWidth, screenHeight);
            type_template_HorizontallistView.setAdapter(adapter);
        }
        adapter.selectedPosition = selectedTemplateTypePosition;
    }

    @Override
    protected void onStart() {
        super.onStart();
        list_recogSult = new ArrayList<String>();
        vinRecogParameter.isFirstProgram = true;

    }

    /**
     * @return ${return_type} 返回类型
     * @throws
     * @Title: 相机界面
     * @Description: 动态布局相机界面的控件
     */

    private void findView() {
        surfaceView = (SurfaceView) this.findViewById(getResources()
                .getIdentifier("surfaceview_camera", "id", getPackageName()));
        bg_template_listView = (RelativeLayout) findViewById(getResources()
                .getIdentifier("bg_template_listView", "id", getPackageName()));
        type_template_listView = (ListView) this
                .findViewById(getResources().getIdentifier(
                        "type_template_listView", "id", getPackageName()));

        // 横向的Listview
        type_template_HorizontallistView = (HorizontalListView) findViewById(getResources()
                .getIdentifier("type_template_HorizontallistView", "id",
                        getPackageName()));
        re = (RelativeLayout) this.findViewById(getResources().getIdentifier(
                "re", "id", getPackageName()));
        imbtn_takepic = (ImageButton) this.findViewById(getResources()
                .getIdentifier("imbtn_takepic", "id", getPackageName()));
        iv_camera_back = (ImageView) this.findViewById(getResources()
                .getIdentifier("iv_camera_back", "id", getPackageName()));
        scanHorizontalLineImageView = (ImageView) this.findViewById(getResources()
                .getIdentifier("scanHorizontalLineImageView", "id", getPackageName()));
        iv_camera_flash = (ImageView) this.findViewById(getResources()
                .getIdentifier("iv_camera_flash", "id", getPackageName()));
        re.addOnLayoutChangeListener(new OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                if ((bottom != oldBottom && right == oldRight)
                        || (bottom == oldBottom && right != oldRight)) {
                    Message mesg = new Message();
                    mesg.what = 5;
                    handler.sendMessage(mesg);
                }

            }
        });

    }

    public void LandscapeView() {
        // 重新编辑扫描框
        if (srcWidth == screenWidth) {
            layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT, srcHeight);
            surfaceView.setLayoutParams(layoutParams);

            // 右侧菜单栏的背景布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (0.15 * srcWidth), srcHeight);
            layoutParams.leftMargin = (int) (0.85 * srcWidth);
            layoutParams.topMargin = 0;
            bg_template_listView.setLayoutParams(layoutParams);

            // 隐藏竖屏布局下的菜单栏
            type_template_HorizontallistView.setVisibility(View.GONE);
            // 右侧菜单栏的布局
            type_template_listView.setVisibility(View.VISIBLE);
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (0.15 * srcWidth), (int) (srcHeight * 0.95));
            layoutParams.leftMargin = (int) (0.85 * srcWidth);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            type_template_listView.setLayoutParams(layoutParams);

            // 闪光灯按钮UI布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.75);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_flash.setLayoutParams(layoutParams);

            // 返回按钮UI布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.05);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_back.setLayoutParams(layoutParams);

        } else if (srcWidth > screenWidth) {
            // 如果将虚拟硬件弹出则执行如下布局代码，相机预览分辨率不变压缩屏幕的高度
            int surfaceViewHeight = (screenWidth * srcHeight) / srcWidth;
            layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT, surfaceViewHeight);
            layoutParams.topMargin = (srcHeight - surfaceViewHeight) / 2;
            surfaceView.setLayoutParams(layoutParams);
            // 右侧菜单栏的背景布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (0.15 * srcWidth), srcHeight);
            layoutParams.leftMargin = (int) (0.85 * srcWidth)
                    - (srcWidth - screenWidth);
            ;
            layoutParams.topMargin = 0;
            bg_template_listView.setLayoutParams(layoutParams);

            // 隐藏竖屏布局下的菜单栏
            type_template_HorizontallistView.setVisibility(View.GONE);
            // 右侧菜单栏的布局
            type_template_listView.setVisibility(View.VISIBLE);
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (0.15 * srcWidth), srcHeight);
            layoutParams.leftMargin = (int) (0.85 * srcWidth)
                    - (srcWidth - screenWidth);
            layoutParams.topMargin = 0;
            type_template_listView.setLayoutParams(layoutParams);

            // 闪光灯按钮UI布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.75)
                    - (srcWidth - screenWidth);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_flash.setLayoutParams(layoutParams);

            // 返回按钮UI布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.05);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_back.setLayoutParams(layoutParams);

        }
        takepicButtonLandscapeView();
    }

    public void PortraitView() {

        if (srcHeight == screenHeight) {
            layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT, srcHeight);
            surfaceView.setLayoutParams(layoutParams);

            // 底侧菜单栏背景的竖屏布局
            layoutParams = new RelativeLayout.LayoutParams(srcWidth,
                    (int) (0.085 * srcHeight));
            layoutParams.topMargin = (int) (0.93 * srcHeight);
            bg_template_listView.setLayoutParams(layoutParams);

            // 底侧侧菜单栏的竖屏布局
            type_template_listView.setVisibility(View.GONE);
            layoutParams = new RelativeLayout.LayoutParams(srcWidth,
                    (int) (0.085 * srcHeight));
            layoutParams.topMargin = (int) (srcHeight * 0.95);
            type_template_HorizontallistView.setLayoutParams(layoutParams);
            type_template_HorizontallistView.setVisibility(View.GONE);

            // 闪光灯按钮UI竖屏布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.8);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_flash.setLayoutParams(layoutParams);

            // 返回按钮UI竖屏布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.1);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_back.setLayoutParams(layoutParams);

        } else if (srcHeight > screenHeight) {
            // 如果将虚拟硬件弹出则执行如下布局代码，相机预览分辨率不变压缩屏幕的宽度
            int surfaceViewWidth = (screenHeight * srcWidth) / srcHeight;
            layoutParams = new RelativeLayout.LayoutParams(surfaceViewWidth,
                    RelativeLayout.LayoutParams.FILL_PARENT);
            layoutParams.leftMargin = (srcWidth - surfaceViewWidth) / 2;
            surfaceView.setLayoutParams(layoutParams);

            // 底侧菜单栏的背景布局
            layoutParams = new RelativeLayout.LayoutParams(srcWidth,
                    (int) (srcHeight * 0.085));
            layoutParams.topMargin = (int) (0.93 * srcHeight)
                    - (srcHeight - screenHeight);
            ;
            layoutParams.leftMargin = (srcWidth - surfaceViewWidth) / 2;
            bg_template_listView.setLayoutParams(layoutParams);

            // 底侧菜单栏的布局
            type_template_listView.setVisibility(View.GONE);
            layoutParams = new RelativeLayout.LayoutParams(srcWidth,
                    (int) (0.085 * srcHeight));
            layoutParams.topMargin = (int) (0.95 * srcHeight)
                    - (srcHeight - screenHeight);

            layoutParams.leftMargin = (srcWidth - surfaceViewWidth) / 2;
            type_template_HorizontallistView.setLayoutParams(layoutParams);
            type_template_HorizontallistView.setVisibility(View.GONE);

            // 闪光灯按钮UI布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.8);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_flash.setLayoutParams(layoutParams);

            // 返回按钮UI竖屏布局
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.1);
            layoutParams.topMargin = (int) (srcHeight * 0.05);
            iv_camera_back.setLayoutParams(layoutParams);
        }
        takepicButtonPortraitView();

    }

    public void ClickEvent() {

        iv_camera_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                backLastActivtiy();
            }
        });
        iv_camera_flash.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (SharedPreferencesHelper.getBoolean(CameraActivity.this,
                        "isOpenFlash", false)) {
                    iv_camera_flash.setImageResource(getResources()
                            .getIdentifier("flash_on", "drawable",
                                    getPackageName()));
                    SharedPreferencesHelper.putBoolean(CameraActivity.this,
                            "isOpenFlash", false);
                    CameraSetting.getInstance(CameraActivity.this)
                            .closedCameraFlash(camera);
                } else {
                    SharedPreferencesHelper.putBoolean(CameraActivity.this,
                            "isOpenFlash", true);
                    iv_camera_flash.setImageResource(getResources()
                            .getIdentifier("flash_off", "drawable",
                                    getPackageName()));
                    CameraSetting.getInstance(CameraActivity.this)
                            .openCameraFlash(camera);
                }
            }
        });
        imbtn_takepic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                vinRecogParameter.isTakePic = true;
                vinRecogParameter.isFirstProgram = true;

            }
        });
        type_template_listView.setDividerHeight(0);
        // 右侧菜单栏点击触发事件
        type_template_listView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int i, long position) {

                        recogResultModelList.clear();
                        savePath.clear();
                        isClick = false;
                        selectedTemplateTypePosition = (int) position;
                        ShowResultActivity.selectedTemplateTypePosition = selectedTemplateTypePosition;
                        // 向识别结果字段数组中 添加识别结果字段条目
                        for (int x = 0; x < wlci.fieldType.get(
                                wlci.template.get((int) position).templateType)
                                .size(); x++) {
                            RecogResultModel recogResultModel = new RecogResultModel();
                            recogResultModel.resultValue = wlci.fieldType
                                    .get(wlci.template.get((int) position).templateType)
                                    .get(x).name
                                    + ":";
                            recogResultModel.type = wlci.fieldType
                                    .get(wlci.template.get((int) position).templateType)
                                    .get(x).type;

                            recogResultModelList.add(recogResultModel);

                        }
                        // 屏幕旋转后 重新添加布局
                        if (myViewfinderView == null) {
                            AddView();
                        }
                        // 正在使用的布局间的切换
                        if (myViewfinderView != null) {
                            re.removeView(myViewfinderView);
                            myViewfinderView = null;
                            myViewfinderView = new ViewfinderView(
                                    CameraActivity.this,
                                    wlci,
                                    wlci.template
                                            .get(selectedTemplateTypePosition).templateType,
                                    true);
                            ViewfinderView.fieldsPosition = 0;
                            re.addView(myViewfinderView);
                            vinRecogParameter.isFirstProgram = true;
                            isChangeType = true;
                            adapter.selectedPosition = (int) position;
                            adapter.notifyDataSetChanged();
                            recogResultAdapter = new RecogResultAdapter(
                                    CameraActivity.this, recogResultModelList,
                                    screenWidth, screenWidth);
                            recogResultAdapter.isRecogSuccess = false;

                        }
                        if (islandscape) {
                            takepicButtonLandscapeView();
                        } else {
                            takepicButtonPortraitView();
                        }
                        imbtn_takepic.setVisibility(View.VISIBLE);
                        if (wlci.fieldType
                                .get(wlci.template
                                        .get(selectedTemplateTypePosition).templateType)
                                .get(ViewfinderView.fieldsPosition).ocrId.equals("SV_ID_YYZZ_MOBILEPHONE")) {
                            if (camera != null) {
                                Camera.Parameters parameters = camera.getParameters();
                                if (parameters.isZoomSupported()) {
                                    parameters.setZoom((int) (parameters.getMaxZoom() * 0.4));  //因为近距离时有时不好对焦，现将焦距拉远，以便近距离时能清晰对焦   可自定义值
                                    camera.setParameters(parameters);
                                }
                            }
                        } else {
                            if (camera != null) {
                                Camera.Parameters parameters = camera.getParameters();
                                if (parameters.isZoomSupported()) {
                                    parameters.setZoom(0);  //因为近距离时有时不好对焦，现将焦距拉远，以便近距离时能清晰对焦   可自定义值
                                    camera.setParameters(parameters);
                                }
                            }

                        }
                    }


                });

        // 竖屏下 底侧菜单栏点击事件
        type_template_HorizontallistView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int i, long position) {
                        recogResultModelList.clear();
                        savePath.clear();
                        isClick = false;
                        selectedTemplateTypePosition = (int) position;
                        ShowResultActivity.selectedTemplateTypePosition = selectedTemplateTypePosition;
                        for (int x = 0; x < wlci.fieldType.get(
                                wlci.template.get((int) position).templateType)
                                .size(); x++) {
                            RecogResultModel recogResultModel = new RecogResultModel();
                            recogResultModel.resultValue = wlci.fieldType
                                    .get(wlci.template.get((int) position).templateType)
                                    .get(x).name
                                    + ":";
                            recogResultModel.type = wlci.fieldType
                                    .get(wlci.template.get((int) position).templateType)
                                    .get(x).type;

                            recogResultModelList.add(recogResultModel);

                        }
                        // 屏幕旋转后 重新添加布局
                        if (myViewfinderView == null) {
                            AddView();
                        }
                        // 正在使用的布局间的切换
                        if (myViewfinderView != null) {
                            re.removeView(myViewfinderView);
                            myViewfinderView = null;
                            myViewfinderView = new ViewfinderView(
                                    CameraActivity.this,
                                    wlci,
                                    wlci.template
                                            .get(selectedTemplateTypePosition).templateType,
                                    false);
                            ViewfinderView.fieldsPosition = 0;
                            re.addView(myViewfinderView);
                            vinRecogParameter.isFirstProgram = true;
                            isChangeType = true;
                            adapter.selectedPosition = (int) position;
                            adapter.notifyDataSetChanged();
                            recogResultAdapter = new RecogResultAdapter(
                                    CameraActivity.this, recogResultModelList,
                                    srcWidth, screenHeight);
                            recogResultAdapter.isRecogSuccess = false;
                        }

                        if (islandscape) {
                            takepicButtonLandscapeView();
                        } else {
                            takepicButtonPortraitView();
                        }
                        imbtn_takepic.setVisibility(View.VISIBLE);
                        if (wlci.fieldType
                                .get(wlci.template
                                        .get(selectedTemplateTypePosition).templateType)
                                .get(myViewfinderView.fieldsPosition).ocrId.equals("SV_ID_YYZZ_MOBILEPHONE")) {
                            if (camera != null) {
                                Camera.Parameters parameters = camera.getParameters();
                                if (parameters.isZoomSupported()) {
                                    parameters.setZoom((int) (parameters.getMaxZoom() * 0.4));  //因为近距离时有时不好对焦，现将焦距拉远，以便近距离时能清晰对焦   可自定义值
                                    camera.setParameters(parameters);
                                }
                            }
                        } else {
                            if (camera != null) {
                                Camera.Parameters parameters = camera.getParameters();
                                if (parameters.isZoomSupported()) {
                                    parameters.setZoom(0);  //因为近距离时有时不好对焦，现将焦距拉远，以便近距离时能清晰对焦   可自定义值
                                    camera.setParameters(parameters);
                                }
                            }

                        }
                    }
                });
    }

    public void RemoveView() {
        if (myViewfinderView != null) {
            myViewfinderView.destroyDrawingCache();
            re.removeView(myViewfinderView);
            myViewfinderView = null;
        }
    }

    public void AddView() {
        if (islandscape) {
            myViewfinderView = new ViewfinderView(
                    CameraActivity.this,
                    wlci,
                    wlci.template.get(selectedTemplateTypePosition).templateType,
                    true);

        } else {
            myViewfinderView = new ViewfinderView(
                    CameraActivity.this,
                    wlci,
                    wlci.template.get(selectedTemplateTypePosition).templateType,
                    false);
        }
        re.addView(myViewfinderView);

    }

    // 横屏下拍照按钮 在调出虚拟导航栏和收起导航栏时的布局
    public void takepicButtonLandscapeView() {

        if (srcWidth == screenWidth) {
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));

            layoutParams.leftMargin = (int) (srcWidth * 0.7);

            layoutParams.topMargin = (int) (srcHeight * 0.43);

            imbtn_takepic.setLayoutParams(layoutParams);

        } else if (srcWidth > screenWidth) {
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcWidth * 0.05), (int) (srcWidth * 0.05));

            layoutParams.leftMargin = (int) (srcWidth * 0.66);
            layoutParams.topMargin = (int) (srcHeight * 0.43);

            imbtn_takepic.setLayoutParams(layoutParams);

        }
        int leftPointX = (int) (wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointX * screenWidth);
        int leftPointY = (int) (wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointY * screenHeight);
        int rightPointX = (int) ((wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointX + wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).width) * screenWidth);
        int rightPointY = (int) ((wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointY + wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).height) * screenHeight);

        layoutParams = new RelativeLayout.LayoutParams(
                (rightPointX - leftPointX), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 0;
        scanHorizontalLineImageView.setLayoutParams(layoutParams);
        // 从上到下的平移动画
        // 从上到下的平移动画
        if (screenHeight >= 1080) {
            verticalAnimation = new TranslateAnimation(leftPointX, leftPointX, leftPointY - 30, rightPointY - 30);
        } else {
            verticalAnimation = new TranslateAnimation(leftPointX, leftPointX, leftPointY - 15, rightPointY - 15);
        }
        verticalAnimation.setDuration(1500);
        verticalAnimation.setRepeatCount(Animation.INFINITE); // 无限循环
        scanHorizontalLineImageView.startAnimation(verticalAnimation);
    }

    // 竖屏下拍照按钮 在调出虚拟导航栏和收起导航栏时的布局
    public void takepicButtonPortraitView() {

        if (srcHeight == screenHeight) {
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.86);
            layoutParams.topMargin = (int) (srcHeight * 0.415);
            imbtn_takepic.setLayoutParams(layoutParams);

        } else if (srcHeight > screenHeight) {
            layoutParams = new RelativeLayout.LayoutParams(
                    (int) (srcHeight * 0.05), (int) (srcHeight * 0.05));
            layoutParams.leftMargin = (int) (srcWidth * 0.86);
            layoutParams.topMargin = (int) (srcHeight * 0.39);
            imbtn_takepic.setLayoutParams(layoutParams);
        }
        int leftPointX = (int) (wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointX * screenWidth);
        int leftPointY = (int) (wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointY * screenHeight);
        int rightPointX = (int) ((wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointX + wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).width) * screenWidth);
        int rightPointY = (int) ((wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).leftPointY + wlci.fieldType
                .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).height) * screenHeight);
        layoutParams = new RelativeLayout.LayoutParams(
                (rightPointX - leftPointX), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 0;
        scanHorizontalLineImageView.setLayoutParams(layoutParams);
        // 从上到下的平移动画
        if (screenHeight >= 1080) {
            verticalAnimation = new TranslateAnimation(leftPointX, leftPointX, leftPointY - 30, rightPointY - 30);
        } else {
            verticalAnimation = new TranslateAnimation(leftPointX, leftPointX, leftPointY - 15, rightPointY - 15);
        }
        verticalAnimation.setDuration(1500);
        verticalAnimation.setRepeatCount(Animation.INFINITE); // 无限循环
        scanHorizontalLineImageView.startAnimation(verticalAnimation);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CameraActivity.this.runOnUiThread(initCameraParams);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        Message msg = new Message();
        msg.what = 100;
        mAutoFocusHandler.sendMessage(msg);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    // 小米PAD 解锁屏时执行surfaceChanged surfaceCreated，容易出现超时卡死现象，故在此处打开相机和设置参数
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (wlci.fieldType
                .get(wlci.template
                        .get(selectedTemplateTypePosition).templateType)
                .get(ViewfinderView.fieldsPosition).ocrId.equals("SV_ID_YYZZ_MOBILEPHONE")) {
            isSetZoom = true;
        } else {
            isSetZoom = false;
        }
        OpenCameraAndSetParameters();

    }

    @Override
    public void onPause() {
        super.onPause();
        CloseCameraAndStopTimer(0);
    }

    private VINRecogResult vinRecogResult;
    private VINRecogParameter vinRecogParameter = new VINRecogParameter();

    @Override
    public void onPreviewFrame(final byte[] bytes, Camera camera) {
        // 实时监听屏幕旋转角度
        uiRot = getWindowManager().getDefaultDisplay().getRotation();// 获取屏幕旋转的角度
        if (uiRot != tempUiRot) {
            Message mesg = new Message();
            mesg.what = uiRot;
            handler.sendMessage(mesg);
            tempUiRot = uiRot;
        }

        if (recogResultAdapter.isRecogSuccess) {
            return;
        }
        if (mRecogOpera.iTH_InitSmartVisionSDK == 0 && sum == 0) {

            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    sum = sum + 1;
                    vinRecogParameter.data = bytes;
                    vinRecogParameter.islandscape = islandscape;//是否横屏
                    vinRecogParameter.rotation = rotation;//屏幕旋转角度
                    vinRecogParameter.selectedTemplateTypePosition = selectedTemplateTypePosition;//模板位置
                    vinRecogParameter.wlci = wlci;//配置文件解析内容
                    vinRecogParameter.size = size;//相机的预览分辨率
                    vinRecogResult = mRecogOpera.startOcr(vinRecogParameter);
                    if (isFinishActivity) {
                        recogResultAdapter.isRecogSuccess = true;
                        CameraActivity.this.runOnUiThread(finishActivity);
                    }else{
                        if (vinRecogResult != null && vinRecogResult.result != null) {
                            String recogResult = vinRecogResult.result;//Vin识别结果
                            if (recogResult != null &&
                                    !"".equals(recogResult)) {
                                recogResultAdapter.isRecogSuccess = true;
                                mVibrator = (Vibrator) getApplication().getSystemService(
                                        Service.VIBRATOR_SERVICE);
                                mVibrator.vibrate(200);
                                savePath = vinRecogResult.savePath;
                                httpContent = vinRecogResult.httpContent;
                                CameraActivity.recogResultModelList.get(ViewfinderView.fieldsPosition).resultValue = recogResult;
                                CameraActivity.this.runOnUiThread(updateUI);
                            }

                        }else{
                            if(mRecogOpera.error!=-1){
                                Message message=new Message();
                                message.what=101;
                                handler.sendMessage(message);
                            }
                        }
                    }
                    sum = sum - 1;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
        mRecogOpera.freeKernalOpera(CameraActivity.this);
        scanHorizontalLineImageView.clearAnimation();
        mAutoFocusHandler.removeMessages(100);
        if (myViewfinderView != null) {
            re.removeView(myViewfinderView);
            myViewfinderView.destroyDrawingCache();
            ViewfinderView.fieldsPosition = 0;
            myViewfinderView = null;
        }
        super.onDestroy();

    }

    // 监听返回键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backLastActivtiy();
            return true;
        }
        return true;
    }

    public void backLastActivtiy() {
        isFinishActivity = true;
    }

    public void OpenCameraAndSetParameters() {
        try {
            if (null == camera) {
                camera = CameraSetting.getInstance(CameraActivity.this).open(0,
                        camera);

                rotation = CameraSetting.getInstance(CameraActivity.this)
                        .setCameraDisplayOrientation(uiRot);
                if (!isFirstIn) {
                    if (islandscape) {
                        CameraSetting.getInstance(CameraActivity.this)
                                .setCameraParameters(CameraActivity.this,
                                        surfaceHolder, CameraActivity.this,
                                        camera, (float) srcWidth / srcHeight,
                                        srcList, false, rotation, isSetZoom);
                    } else {
                        CameraSetting.getInstance(CameraActivity.this)
                                .setCameraParameters(CameraActivity.this,
                                        surfaceHolder, CameraActivity.this,
                                        camera, (float) srcHeight / srcWidth,
                                        srcList, false, rotation, isSetZoom);
                    }
                }
                isFirstIn = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CloseCameraAndStopTimer(int type) {
        if (camera != null) {
            if (type == 1) {
                if (camera != null) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                }
            } else {
                camera = CameraSetting.getInstance(CameraActivity.this)
                        .closeCamera(camera);
            }

        }
    }

}
