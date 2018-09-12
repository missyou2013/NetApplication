package net.com.mvp.ac.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.BuildConfig;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.ContentAdapter2;
import net.com.mvp.ac.api.ApiConfig;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.OutPhotoItem;
import net.com.mvp.ac.model.RecheckCarsModel;
import net.com.mvp.ac.model.TypeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static net.com.mvp.ac.application.BaseApplication.WAIJIAN_PHOTO_PREVIEW_LISTS;

/**
 * 复检拍照
 */

public class ReCheckTakePhotoActivity extends TakePhotoActivity implements AdapterView.OnItemClickListener,
        ApiConfig {
    @BindView(R.id.ac_recheck_photo_listview)
    ListView acRecheckPhotoListview;
    @BindView(R.id.ac_recheck_photo_btn_wancheng2)
    Button acRecheckPhotoBtnWancheng2;
    @BindView(R.id.ac_recheck_photo_ll_bottom_layout)
    LinearLayout acRecheckPhotoLlBottomLayout;
    @BindView(R.id.ac_recheck_photo_scrollview)
    ScrollView acRecheckPhotoScrollview;
    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_left_txt)
    Button titleBtnLeftTxt;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.iv_headtip)
    ImageView ivHeadtip;
    @BindView(R.id.title_btn_right_txt)
    Button titleBtnRightTxt;
    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;

    private ReCheckTakePhotoActivity instances = null;
    private RecheckCarsModel recheckCarsModel;
    private List<TypeModel> typeModelList;
    private List<TypeModel> typeModelListAdapter;
    private ContentAdapter2 contentAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recheck);
        ButterKnife.bind(this);
        instances = this;
        setTopTitle("复检拍照");
        setBackBtn();
        init();
        WAIJIAN_PHOTO_PREVIEW_LISTS = new HashMap<>();
        recheckCarsModel = (RecheckCarsModel) getIntent().getSerializableExtra("re_model");

        takePhoto = getTakePhoto();
//        configCompress(takePhoto);

        getData(0);

//        getCropOptions();
    }

    /**
     * Back按钮退出 activity
     */

    public void setBackBtn() {
        View v = findViewById(R.id.title_btn_left);
        if (v == null)
            return;
        v.setVisibility(View.VISIBLE);
        ((ImageButton) v).setImageResource(R.mipmap.nav_backarrow);
        v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置标题(int：从配置里读取，String：直接赋值)
     */
    public void setTopTitle(Object str) {
        View v = findViewById(R.id.title_txt);
        if (v == null)
            return;
        if (str instanceof String) {
            ((TextView) v).setText(String.valueOf(str));
        } else if (str instanceof Integer) {
            ((TextView) v).setText(((Integer) str).intValue());
        }
    }

    @OnClick({R.id.ac_recheck_photo_btn_wancheng2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_recheck_photo_btn_wancheng2:
                //完成

                break;

        }
    }

    private void getData(int type) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(this))) {
            if (CommonUtils.isOnline(this)) {
                if (type == 0) {
                    getOutPhotoItem();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }


    private void init() {
        typeModelList = new ArrayList<>();
        typeModelListAdapter = new ArrayList<>();
    }

    //获取某辆车的外检拍照的项目
    // PlateType,CarType,Usage
    private void getOutPhotoItem() {
        OutPhotoItem model = new OutPhotoItem();
        model.setCarType(recheckCarsModel.getType());
        model.setPlateType(recheckCarsModel.getPlateType());
        model.setUsage(recheckCarsModel.getUsage());
        model.setPlatformSN(recheckCarsModel.getPlatformSN());
        Gson gson = new Gson();
        String json_str = gson.toJson(model);
        UtilsLog.e("getOutPhotoItem---json_str==" + json_str);
        UtilsLog.e("getOutPhotoItem---url==" + SharedPreferencesUtils.getIP(this) + WAI_JIAN_PHOTO_ITEM);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + WAI_JIAN_PHOTO_ITEM).tag(this)
                .upJson(json_str)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        BaseApplication.msg = result;
//                        UtilsLog.e("getOutPhotoItem-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getOutPhotoItem-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {
                            typeModelList = JsonUtil.stringToArray(newResult,
                                    TypeModel[].class);
                            if (typeModelList != null && typeModelList.size() > 0) {
                                TypeModel typeModel = null;
                                for (int i = 0; i < typeModelList.size(); i++) {
                                    typeModel = new TypeModel();
                                    typeModel.setName(typeModelList.get(i).getName());
                                    typeModel.setCode(typeModelList.get(i).getCode());
                                    typeModelListAdapter.add(typeModel);
                                }
                                contentAdapter2 = new ContentAdapter2(instances, typeModelListAdapter,
                                        mListener);
                                //实例化ContentAdapter2类，并传入实现类
                                acRecheckPhotoListview.setAdapter(contentAdapter2);
                                setListViewHeightBasedONChildren(acRecheckPhotoListview);
                                acRecheckPhotoListview.setOnItemClickListener(instances);
                            }
                        } else {
                            BaseApplication.msg = result;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        BaseApplication.msg = response.body() + "==" + response.message();
                        UtilsLog.e("getOutPhotoItem-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    /**
     * 实现类，响应按钮点击事件
     */
    TypeModel typeModel;
    int TypeModel_Position = 0;
    private String PHOTO_TYPE = null;//外检拍照的TYPE上传类型
    TakePhoto takePhoto;
    Uri imageUri;
    String tag = "tag";
    // 相册
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    private static String picFileFullName;
    private ContentAdapter2.MyClickListener mListener = new ContentAdapter2.MyClickListener() {
        @Override
        public void myOnClick(int position, View v, int flag) {
            PHOTO_TYPE = typeModelListAdapter.get(position).getCode();
            TypeModel_Position = position;
            if (flag == 0) {
                //跳转到图片预览界面
                if (WAIJIAN_PHOTO_PREVIEW_LISTS.get(Integer.parseInt(PHOTO_TYPE)) != null) {
                    Intent intent_preview = new Intent(instances, PhotoPreviewActivity.class);
                    intent_preview.putExtra("key_integer", Integer.parseInt(PHOTO_TYPE));
                    startActivity(intent_preview);
                }
            } else if (flag == 1) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    takePicture();
//                } else {

//                TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
//                builder.setCorrectImage(true);
//                takePhoto.setTakePhotoOptions(builder.create());
//                if (SharedPreferencesUtils.getPhotoClipping(instances)) {
//                    getCropOptions();
                    File file = new File(Environment.getExternalStorageDirectory(), "/temp_my_app/" + System
                            .currentTimeMillis() + ".jpg");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    imageUri = Uri.fromFile(file);
                    UtilsLog.e("photo", "WAIJIAN_PHOTO_imageUri=7777=" + imageUri);
                    takePhoto.onPickFromCapture(imageUri);
//                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
//                } else {
//                    File file = new File(Environment.getExternalStorageDirectory(), "/temp_my_app/" + System
//                            .currentTimeMillis() + ".jpg");
//                    if (!file.getParentFile().exists()) {
//                        file.getParentFile().mkdirs();
//                    }
//                    imageUri = Uri.fromFile(file);
//                    UtilsLog.e("photo", "WAIJIAN_PHOTO_imageUri=888=" + imageUri);
////                    takePhoto.onPickFromCapture(imageUri);
//                    takePhoto.onPickFromCapture(imageUri);
////                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
//                }


//                }
                typeModel = new TypeModel();
                typeModel.setName(typeModelListAdapter.get(position).getName());
                typeModel.setCode(typeModelListAdapter.get(position).getCode());
            }
        }
    };

    private CropOptions getCropOptions() {
        int height = 1200;
        int width = 1600;
        CropOptions.Builder builder = new CropOptions.Builder();
//        builder.setOutputX(width).setOutputY(height);
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.e("photo", "拍照失败==" + msg);
        ArrayList<TImage> images = result.getImages();
        if (images != null && images.size() > 0) {
            String photo_file_path = images.get(0).getCompressPath();
            String photo_file_path2 = images.get(0).getOriginalPath();
            Log.e("photo", "拍照失败--path==" + photo_file_path);
            Log.e("photo", "拍照失败--path=22=" + photo_file_path2);
        }
    }

    String photo_file_path_OriginalPath;

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        if (images != null && images.size() > 0) {
//            String photo_file_path = images.get(0).getCompressPath();
//            Log.e("photo", "拍照成功--path==" + photo_file_path);
//            File photo_flie = new File(photo_file_path);

            //拍照压缩后上传
//            getData(photo_flie, photo_file_path);
            photo_file_path_OriginalPath = Environment.getExternalStorageDirectory() + images.get(0)
                    .getOriginalPath().replace("my_images/", "");
            if (!TextUtils.isEmpty(photo_file_path_OriginalPath)) {
//                    Log.e("photo", "拍照成功--path=33=" + img_imageUri_path);
                if (SharedPreferencesUtils.getPhotoClipping(instances)) {
                    //拍照需要编辑的
                    Intent intent = new Intent(instances, WaiRotatePhotoActivity.class);
                    intent.putExtra("wa_rotate_img", photo_file_path_OriginalPath);
                    startActivityForResult(intent, 917);
                } else {
                    //拍照不需要编辑的
                    Luban.with(instances)
                            .load(new File(photo_file_path_OriginalPath))                     //传人要压缩的图片
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    Log.e("photo", "压缩成功后调用");
                                    //拍照压缩后上传
//                                    getData(file,picFileFullName);
                                    getData(file, photo_file_path_OriginalPath);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                                }
                            }).launch();    //启动压缩
                }
            }
        }
    }

    private void getData(File file, String file_name) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                getDataUploadPhoto(file, file_name);
            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }


    File file = null;

    public void takePicture() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory() + "/images/" + System
                    .currentTimeMillis() + ".jpg";
            File outputFile = new File(filePath);
            file = outputFile;
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri contentUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".fileprovider", outputFile);
            picFileFullName = outputFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(instances, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
            Log.e("tag", "请确认已经插入SD卡");
        }
    }

    public static void setListViewHeightBasedONChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

    private void configCompress(TakePhoto takePhoto) {
//        takePhoto.onEnableCompress(null, true);
        int maxSize = 102400;
        int width = 800;
        int height = 1024;
        boolean showProgressBar = false;
        boolean enableRawFile = false;
        CompressConfig config;
//        config = new CompressConfig.Builder()
////                .setMaxSize(maxSize)
////                .setMaxPixel(width >= height ? width : height)
//                .enableReserveRaw(enableRawFile)
//                .create();
        LubanOptions option = new LubanOptions.Builder()

//                .setMaxHeight(height)
//                .setMaxWidth(width)
                .setMaxSize(maxSize)
                .create();
        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config, showProgressBar);
    }
    String change01;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        Uri uri = data.getData();
                        if (!TextUtils.isEmpty(uri.getAuthority())) {
                            Cursor cursor = getContentResolver().query(uri,
                                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                            if (null == cursor) {
//                                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
//                                return;
                            }
                            cursor.moveToFirst();
                            picFileFullName = cursor.getString(cursor.getColumnIndex(MediaStore.Images
                                    .Media.DATA));
                            cursor.close();
                        } else {
                            picFileFullName = uri.getPath();
                        }
                    } else {
//                        Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
//                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e(tag, "获取图片成功，path=" + picFileFullName);
                // 照相时的确认
//                getData(new File(picFileFullName));

                Luban.with(instances)
                        .load(new File(picFileFullName))                     //传人要压缩的图片
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                CommonUtils.showLoadingDialog(instances, "上传中...");
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                //拍照压缩后上传
                                getData(file, picFileFullName);
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                            }
                        }).launch();    //启动压缩

            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了图像捕获
            } else {
                // 图像捕获失败，提示用户
                Log.e(tag, "拍照失败");
            }
        } else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String realPath = getRealPathFromURI(uri);
                    Log.e(tag, "获取图片成功22，path=" + realPath);
                } else {
                    Log.e(tag, "从相册获取图片失败");
                }
            }
        } else if (resultCode == 0) {
            Log.e(tag, "resultCode===0");
        }else if (resultCode == 917) {
//            旋转后返回，压缩图片，上传
            Log.e(tag, "resultCode===917");
            change01 = data.getStringExtra("change01_path");
            Log.e(tag, "change01===917==" + change01);
            Luban.with(instances)
                    .load(new File(change01))                     //传人要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            Log.e("photo", "rrrrr" );
                            //拍照压缩后上传
//                                    getData(file,picFileFullName);
                            getData(file, change01);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                        }
                    }).launch();    //启动压缩

        }
    }


    /**
     * This method is used to get real path of file from from uri<br/>
     * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-
     * captured-from-camera
     *
     * @param contentUri
     * @return String
     */
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this
            // method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    //上传照片
    private void getDataUploadPhoto(final File file, final String file_name) {
        UtilsLog.e("getDataUploadPhoto---url==" + SharedPreferencesUtils.getFileIP(instances) + UPLOAD_PHOTO);
        OkGo.<String>post(SharedPreferencesUtils.getFileIP(instances) + UPLOAD_PHOTO).tag(instances)
//                .params("PHOTO_TYPE", PHOTO_TYPE)
                //guid
                .params("QueueID", recheckCarsModel.getQueueID())
                //guid
                .params("GUID", recheckCarsModel.getGUID())
                //次数
                .params("Times", recheckCarsModel.getTimes())
                //PlatformSN
                .params("PlatformSN", recheckCarsModel.getPlatformSN())
                //SN
                .params("SN", recheckCarsModel.getSN())
                //拍摄时间
                .params("PhotoDate", DateUtil.currentTime2())
                //照片类型-拍的哪里的照片，位置
//                .params("PhotoType", getPlatformCode(PHOTO_TYPE))
                .params("PhotoType", PHOTO_TYPE)

                //车辆类型
                .params("Type", recheckCarsModel.getType())
                //车牌号码，两个拼起来的
                .params("CarNO", recheckCarsModel.getPlateRegion() + recheckCarsModel.getPlateNO())
                //号牌类型
                .params("PlateType", recheckCarsModel.getPlateType())
                //拍照code F1表示外检
                .params("ItemCode", "F1")
                //外检车道，检测线代号
                .params("Line", recheckCarsModel.getLine())
                //车辆识别代码
                .params("VIN", recheckCarsModel.getVIN())
                .params("FileType", "jpg")
//                .params("FileType", FileUtils.getExtensionName(file.getName()))
                //照片
                .params("zp", file)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataUploadPhoto-result==" + result);
//                        UtilsLog.e("response.headers()-result==" + response.headers());
//                        photo_result(PHOTO_TYPE);
//                        if (PHOTO_TYPE != null) {
//                            WAIJIAN_PHOTO_PREVIEW_LISTS.put(Integer.parseInt(PHOTO_TYPE), file);
//                        }
                        WAIJIAN_PHOTO_PREVIEW_LISTS.put(Integer.parseInt(PHOTO_TYPE), file);
                        if (!TextUtils.isEmpty(result) && !"[[]]".equals(result) && !"[{}]".equals(result)
                                && !"[]".equals(result)) {
                            String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\",
                                    "");
                            UtilsLog.e("getDataUploadPhoto-newResult==" + newResult);
                            if ("ok".equalsIgnoreCase(newResult)) {
                                try {
                                    if (!TextUtils.isEmpty(file_name)) {
                                        CommonUtils.DeleteImage(instances, file_name);
                                    } else {
                                        String logStr2 = "\n" + "重新拍照--外检上传照片--没有获取到即将删除的图片路径" + "\n";
                                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE,
                                                logStr2.getBytes());
                                        UtilsLog.e("getDataUploadPhoto==没有获取到即将删除的图片路径");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(instances, "上传成功", Toast.LENGTH_LONG).show();
                                typeModel.setColor(1);
                                typeModelListAdapter.set(TypeModel_Position, typeModel);
                                contentAdapter2.notifyDataSetChanged();
                            } else {
                                Toast.makeText(instances, "上传失败，请重新拍照", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            UtilsLog.e("getDataUploadPhoto-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataUploadPhoto-onError==" + response.body());
                        UtilsLog.e("getDataUploadPhoto-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
