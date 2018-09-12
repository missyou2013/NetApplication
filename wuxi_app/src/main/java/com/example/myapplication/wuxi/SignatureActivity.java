package com.example.myapplication.wuxi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查验员签字图片采集功能
 **/
public class SignatureActivity extends TakePhotoActivity {


    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;
    @BindView(R.id.ac_sign_btn_photo)
    Button acSignBtnPhoto;
    @BindView(R.id.ac_sign_img_photo)
    ImageView acSignImgPhoto;
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
    private SignatureActivity instance = null;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;


    Uri imageUri;
    String img_imageUri_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);

        instance = this;
//        setBackBtn();
//        setTopTitle("图片采集");

        titleTxt.setText("图片采集");
    }

    @OnClick({R.id.ac_sign_btn_photo, R.id.ac_sign_img_photo,
            R.id.title_btn_left, R.id.title_btn_left_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_sign_btn_photo:
                takePhoto = getTakePhoto();
//                    configCompress(takePhoto);
//                    File file = new File(Environment.getExternalStorageDirectory(), "/temp_my_app/" + System
//                            .currentTimeMillis() + ".jpg");
                File file = new File(Environment.getExternalStorageDirectory(), "/wuxi_app/" + System
                        .currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                imageUri = Uri.fromFile(file);
                Log.e("tag", "aaa=" + imageUri);
                img_imageUri_path = imageUri.getPath();
                Log.e("tag", "bbb=" + imageUri.getPath());
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            case R.id.ac_sign_img_photo:
                if (!TextUtils.isEmpty(photo_file_path)) {
                    Intent intent_preview = new Intent(instance, PhotoPreviewActivity.class);
                    intent_preview.putExtra("img_url_wuxi", photo_file_path);
                    startActivity(intent_preview);
                }
                break;
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_left_txt:
                break;
        }
    }

    String photo_file_path;
    String photo_file_path_OriginalPath;

    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
//            showImg(result.getImages());
            ArrayList<TImage> images = result.getImages();
            if (images != null && images.size() > 0) {
                //压缩后的图片路径，不压缩为null
                photo_file_path = images.get(0).getOriginalPath();
                Log.e("tag", "拍照成功--path==" + photo_file_path);
//                //原图片的路径
//                photo_file_path_OriginalPath = Environment.getExternalStorageDirectory() + images.get(0)
//                        .getOriginalPath().replace("my_images/", "");
//                Log.e("tag", "拍照成功--path=22=" + photo_file_path_OriginalPath);
                if (!TextUtils.isEmpty(photo_file_path)) {
                    Glide.with(this).load(new File(photo_file_path)).into(acSignImgPhoto);
                }
            }
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.e("tag", "拍照失败==" + msg);
    }

    @Override
    public void takeCancel() {
        Log.e("tag", "拍照==takeCancel");
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        getTakePhoto().onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        getTakePhoto().onSaveInstanceState(outState);
//        super.onSaveInstanceState(outState);
//    }

    private CropOptions getCropOptions() {
        int height = 400;
        int width = 800;
        boolean withWonCrop = true;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }



}
