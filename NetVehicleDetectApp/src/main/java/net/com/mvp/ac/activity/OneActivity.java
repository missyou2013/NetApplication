package net.com.mvp.ac.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneActivity extends TakePhotoActivity {
    TakePhoto takePhoto;
    Uri imageUri;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa);
        ButterKnife.bind(this);

        takePhoto = getTakePhoto();
        initTakePhoto(takePhoto);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto.onPickFromCapture(imageUri);
                textView2.setText("imageUri==" + imageUri);
            }
        });
    }

    File file;
    CompressConfig config;
    String pic_name = "";

    private void initTakePhoto(TakePhoto takePhoto) {
        pic_name = "鲁B88888_外检_" + System.currentTimeMillis() + ".jpg";
        try {
            file = new File(Environment.getExternalStorageDirectory(), "/carImages/" + pic_name);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            imageUri = Uri.fromFile(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        takePhoto.onEnableCompress(null, true);
        LubanOptions option = new LubanOptions.Builder()
                .setMaxHeight(640)
                .setMaxWidth(480)
                .setMaxSize(102400)
                .create();
        config = CompressConfig.ofLuban(option);
//        config=new CompressConfig.Builder()
//                .setMaxSize(1024*2)
//                .setMaxPixel(800)
//                .create();
        config.enableReserveRaw(true);
        takePhoto.onEnableCompress(config, true);
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();

//        File fileImage = new File(images.get(0).getCompressPath());
//        String path = fileImage.getPath();
//        String AbsolutePath = fileImage.getAbsolutePath();
//        String name = fileImage.getName();
//        textView3.setText("path==" + images );

//
//        if (!TextUtils.isEmpty(images.get(0).getCompressPath())) {
//            try {
//                String pathsss = FileUtils.SAVE_REAL_PATH;
//                File myCaptureFile = new File(pathsss, "copy_" + System.currentTimeMillis() + ".jpg");
////                FileUtils.copyFile(new File(images.get(images.size() - 1).getCompressPath()), myCaptureFile);
//                FileUtils.saveFile(images.get(0).getCompressPath(), "A_" + System.currentTimeMillis() + ".jpg");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        for (int i = 0, j = images.size(); i < j - 1; i += 2) {
            if (!TextUtils.isEmpty(images.get(i).getCompressPath())) {
                try {
                    FileUtils.saveFile(images.get(i).getCompressPath(), "A_" + System.currentTimeMillis() + ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (images.size() % 2 == 1) {
            if (!TextUtils.isEmpty(images.get(images.size() - 1).getCompressPath())) {
                try {
                    FileUtils.saveFile(images.get(images.size() - 1).getCompressPath(), "A_" + System.currentTimeMillis() + ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
