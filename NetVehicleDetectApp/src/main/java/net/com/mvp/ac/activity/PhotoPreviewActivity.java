package net.com.mvp.ac.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import net.com.mvp.ac.R;
import net.com.mvp.ac.application.BaseApplication;

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
        int key_photo = getIntent().getExtras().getInt("key_integer", 1);

        Glide.with(PhotoPreviewActivity.this).load(BaseApplication.WAIJIAN_PHOTO_PREVIEW_LISTS.get(key_photo)).
                into(acPhotoPreviewIv);
    }
}
