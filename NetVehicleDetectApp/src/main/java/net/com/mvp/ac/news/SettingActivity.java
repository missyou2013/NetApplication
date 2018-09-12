package net.com.mvp.ac.news;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.news_ac_set_btn_save)
    Button newsAcSetBtnSave;
    @BindView(R.id.news_ac_setting_et_1)
    EditText newsAcSettingEt1;
    @BindView(R.id.news_ac_setting_et_2)
    EditText newsAcSettingEt2;
    @BindView(R.id.news_ac_setting_et_3)
    EditText newsAcSettingEt3;
    @BindView(R.id.news_ac_setting_et_4)
    EditText newsAcSettingEt4;
    @BindView(R.id.news_ac_setting_et_5)
    EditText newsAcSettingEt5;
    @BindView(R.id.news_ac_setting_et_6)
    EditText newsAcSettingEt6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_setting);
        ButterKnife.bind(this);

        setBackBtn();
        setTopTitle(R.string.title_activity_setting);

    }

    @OnClick(R.id.news_ac_set_btn_save)
    public void onViewClicked() {
    }
}
