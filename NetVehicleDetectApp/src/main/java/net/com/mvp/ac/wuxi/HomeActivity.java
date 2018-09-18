package net.com.mvp.ac.wuxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;
import net.com.mvp.ac.activity.DaiJianCars2Activity;
import net.com.mvp.ac.activity.ScanQrCodeActivity;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.wuxi.finger.FingerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.ac_home_btn_obd)
    Button acHomeBtnObd;
    @BindView(R.id.ac_home_btn_vin)
    Button acHomeBtnVin;
    @BindView(R.id.ac_home_btn_zhiwen)
    Button acHomeBtnZhiwen;
    @BindView(R.id.ac_home_btn_check)
    Button acHomeBtnCheck;
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
    @BindView(R.id.ac_home_view_m)
    TextView acHomeViewM;
    @BindView(R.id.ac_home_view_m2)
    TextView acHomeViewM2;
    @BindView(R.id.ac_home_btn_chayan_qianzi)
    Button acHomeBtnChayanQianzi;
    @BindView(R.id.ac_home_view_m25)
    TextView acHomeViewM25;
    @BindView(R.id.ac_home_btn_other)
    Button acHomeBtnOther;

    private String check_mode, login_UserID, login_IDNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setBackBtn();

        Intent intent = getIntent();
        check_mode = intent.getExtras().getString("check_mode");
        login_UserID = intent.getExtras().getString("login_UserID");
        login_IDNumber = intent.getExtras().getString("login_IDNumber");

    }

    @OnClick({R.id.ac_home_btn_obd, R.id.ac_home_btn_vin,
            R.id.ac_home_btn_zhiwen, R.id.ac_home_btn_check,
            R.id.ac_home_btn_chayan_qianzi, R.id.ac_home_btn_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_home_btn_chayan_qianzi:
                //查验员签字识别
                Intent i2t = new Intent(this, SignatureActivity.class);
                startActivity(i2t);
                break;
            case R.id.ac_home_btn_other:
                //备用的
                break;
            case R.id.ac_home_btn_obd:
                break;
            case R.id.ac_home_btn_vin:
                Intent i2 = new Intent(this, ScanQrCodeActivity.class);
                startActivity(i2);
                break;
            case R.id.ac_home_btn_zhiwen:
                Intent i22 = new Intent(this, FingerActivity.class);
                startActivity(i22);
                break;
            case R.id.ac_home_btn_check:
                Intent i = new Intent(this, DaiJianCars2Activity.class);
                i.putExtra("check_mode", check_mode);
                i.putExtra("login_UserID", login_UserID);
                i.putExtra("login_IDNumber", login_IDNumber);
                startActivity(i);
                break;
        }
    }

    private long exitTime = 0;

    // 双击返回退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getSelf().exit(this);
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
