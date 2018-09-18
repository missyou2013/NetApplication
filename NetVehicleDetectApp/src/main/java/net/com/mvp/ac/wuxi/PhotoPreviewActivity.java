package net.com.mvp.ac.wuxi;

import android.os.Bundle;

import com.bumptech.glide.Glide;


import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

//外检拍照照片预览
public class PhotoPreviewActivity extends BaseActivity {

    @BindView(R.id.ac_photo_preview_iv)
    com.github.chrisbanes.photoview.PhotoView acPhotoPreviewIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        ButterKnife.bind(this);
        String key_photo = getIntent().getExtras().getString("img_url_wuxi", "");

        Glide.with(PhotoPreviewActivity.this).load(new File(key_photo)).
                into(acPhotoPreviewIv);
    }
}
