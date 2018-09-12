package net.com.mvp.ac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//校验码
public class VerifyCodeActivity extends BaseActivity {

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
    @BindView(R.id.ac_verify_code_jiqima)
    EditText acVerifyCodeJiqima;
    @BindView(R.id.ac_verify_code_shouquanma)
    EditText acVerifyCodeShouquanma;
    @BindView(R.id.ac_verify_code_wanchen)
    Button acVerifyCodeWanchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        ButterKnife.bind(this);

        setTopTitle(R.string.ac_verify_code_title);

        setHideLeftBtn();
    }


//    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    @Override
    protected void onResume() {
        super.onResume();
        acVerifyCodeJiqima.setText(CommonUtils.getIMEI(this));
    }

    @OnClick(R.id.ac_verify_code_wanchen)
    public void onViewClicked() {
        String imei = acVerifyCodeJiqima.getText().toString().trim();
        String shouquanma = acVerifyCodeShouquanma.getText().toString().trim();
        if (!TextUtils.isEmpty(shouquanma) && shouquanma.length() == 11) {
            boolean result = CommonUtils.checkAuthorizationCode(imei + "999", shouquanma);
            if (result) {
                SharedPreferencesUtils.setFirstUseApp(this, true);
                SharedPreferencesUtils.setShouQuanMa(this, shouquanma);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "授权码不正确或过期，请联系管理员授权", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtils.setFirstUseApp(this, false);
            }

        } else {
            Toast.makeText(this, "请输入正确的授权码", Toast.LENGTH_SHORT).show();
        }

    }

//    private long exitTime = 0;
//
//    // 双击返回退出程序
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                BaseApplication.getSelf().exit(this);
//                this.finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
