package net.com.mvp.ac.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import net.com.mvp.ac.R;
import net.com.mvp.ac.cache.FileUtils;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.UtilsLog;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 外检旋转照片
 */
public class WaiRotatePhotoActivity extends BaseActivity {

    @BindView(R.id.ac_wai_rotate_img)
    ImageView acWaiRotateImg;
    @BindView(R.id.ac_wai_rotate_btn_rotate)
    Button acWaiRotateBtnRotate;
    @BindView(R.id.ac_wai_rotate_btn_upload)
    Button acWaiRotateBtnUpload;
    private WaiRotatePhotoActivity instances = null;
    private String img_path, rotate_img_path;
    private File file_img, file_rotate_img;
    private int degrees = 0;
    private Handler handler = new Handler();
    WaiRotatePhotoActivity.MyRunnable myRunnable = new WaiRotatePhotoActivity.MyRunnable();//定义MyRunnable的对象；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wai_rotate_photo);
        ButterKnife.bind(this);

        instances = this;

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels;
        imageHeight = metrics.heightPixels;

        img_path = getIntent().getExtras().getString("wa_rotate_img", null);
        UtilsLog.e("photo", "WaiRotatePhotoActivity---img_path=" + img_path);
        if (!TextUtils.isEmpty(img_path)) {
            file_img = new File(img_path);
            if (!file_img.exists()) {
                return;
            } else {
//                Glide.with(instances).load(file_img).into(acWaiRotateImg);
//                Bitmap bmp = BitmapFactory.decodeFile(img_path);
//                acWaiRotateImg.setImageBitmap(mainBitmap);
                File outputFile = FileUtils.genEditFile();
//                EditImageActivity.start(this,img_path,outputFile.getAbsolutePath(),ACTION_REQUEST_EDITIMAGE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // System.out.println("RESULT_OK");
            switch (requestCode) {
//                case SELECT_GALLERY_IMAGE_CODE://
//                    handleSelectFromAblum(data);
//                    break;
//                case TAKE_PHOTO_CODE://拍照返回
//                    handleTakePhoto(data);
//                    break;
                case ACTION_REQUEST_EDITIMAGE://
                    handleEditorImage(data);
                    break;
            }// end switch
        }
    }

    private void handleEditorImage(Intent data) {
//        String newFilePath = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);
//        boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);
//
//        if (isImageEdit){
////            Toast.makeText(this, getString(R.string.save_path, newFilePath), Toast.LENGTH_LONG).show();
//        }else{//未编辑  还是用原来的图片
//            newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);;
//        }
//        rotate_img_path=newFilePath;
//        UtilsLog.e("photo", "WaiRotatePhotoActivity---newFilePath=" + newFilePath);
//        //System.out.println("newFilePath---->" + newFilePath);
//        //File file = new File(newFilePath);
//        //System.out.println("newFilePath size ---->" + (file.length() / 1024)+"KB");
//        Log.d("image is edit", isImageEdit + "");
//        LoadImageTask loadTask = new LoadImageTask();
//        loadTask.execute(newFilePath);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    @OnClick({R.id.ac_wai_rotate_img, R.id.ac_wai_rotate_btn_rotate,
            R.id.ac_wai_rotate_btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_wai_rotate_img:
                break;
            case R.id.ac_wai_rotate_btn_rotate:
//                if (file_img != null && file_img.exists()) {
//
//
//                    new Thread() {
//
//                        @Override
//                        public void run() {
////                                handler.postDelayed(myRunnable,600);
//                            handler.post(myRunnable);//调用Handler.post方法；
//                        }
//                    }.start();
//
//                }
                File outputFile = FileUtils.genEditFile();
//                EditImageActivity.start(instances,img_path,outputFile.getAbsolutePath(),ACTION_REQUEST_EDITIMAGE);
                break;
            case R.id.ac_wai_rotate_btn_upload:
                if (!TextUtils.isEmpty(rotate_img_path)) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra("change01_path", rotate_img_path);
//                    mIntent.putExtra("change02", "2000");
                    // 设置结果，并进行传送
                    this.setResult(917, mIntent);
                } else {
                    rotate_img_path = img_path;
                }
                this.finish();
                break;
        }
    }

    class MyRunnable implements Runnable {//内部类实现Runnable接口；

        @Override
        public void run() {//还是在Runnable重写的run()方法中更新界面；
//            text.setText("使用Handler更新了界面");
//            getData(0);
            if (degrees >= 360) {
                degrees = 90;
            }else{
                degrees = degrees + 90;
            }
            Bitmap bmp = BitmapFactory.decodeFile(img_path);
//            Bitmap bitmap = CommonUtils.rotateBitmap(bmp, degrees);
            Bitmap bitmap = CommonUtils.rotaingImageView(degrees, bmp);

            rotate_img_path = CommonUtils.saveBitmap(instances, bitmap);
            UtilsLog.e("photo", "WaiRotatePhotoActivity---rotate_img_path=" + rotate_img_path);
            if (!TextUtils.isEmpty(rotate_img_path)) {
                file_rotate_img = new File(rotate_img_path);
                if (!file_rotate_img.exists()) {
                    return;
                } else {
                    Glide.with(instances).load(file_rotate_img).into(acWaiRotateImg);
                }
            }
        }
    }

    private View editImage;//
    private Bitmap mainBitmap;
    private int imageWidth, imageHeight;//
    private String path;
    private View mTakenPhoto;//拍摄照片用于编辑
    private Uri photoURI = null;
    public static final int REQUEST_PERMISSON_SORAGE = 1;
    public static final int REQUEST_PERMISSON_CAMERA = 2;

    public static final int SELECT_GALLERY_IMAGE_CODE = 7;
    public static final int TAKE_PHOTO_CODE = 8;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    public static final int ACTION_STICKERS_IMAGE = 10;

    private final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return null;
//            return BitmapUtils.getSampledBitmap(params[0], imageWidth / 4, imageHeight / 4);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(Bitmap result) {
            super.onCancelled(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (mainBitmap != null) {
                mainBitmap.recycle();
                mainBitmap = null;
                System.gc();
            }
            mainBitmap = result;
            acWaiRotateImg.setImageBitmap(mainBitmap);
        }
    }// end inner class
}
