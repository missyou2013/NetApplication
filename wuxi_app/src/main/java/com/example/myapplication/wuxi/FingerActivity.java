package com.example.myapplication.wuxi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.application.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FingerActivity extends BaseActivity {

    @BindView(R.id.f_btn_1)
    Button fBtn1;
    @BindView(R.id.f_btn_2)
    Button fBtn2;
    @BindView(R.id.f_tv_1)
    TextView fTv1;
    private Context mContext;
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private final static String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle("指纹识别");
        mContext = this;
        manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);

        fBtn1=(Button)findViewById(R.id.f_btn_1) ;
        fBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFinger()) {
                    Toast.makeText(mContext, "请进行指纹识别", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "keyi==============");
                    startListening(null);
                }else {
                    Log.e(TAG, "no--no==============");
                }
            }
        });

    }

    @OnClick({R.id.f_btn_1, R.id.f_btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.f_btn_1:
//                onFingerprintClick(fBtn1);
                if (isFinger()) {
                    Toast.makeText(this, "请进行指纹识别", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "keyi==============");
                    startListening(null);
                }else {
                    Log.e(TAG, "no--no==============");
                }
                break;
            case R.id.f_btn_2:
                break;
        }
    }


    public boolean isFinger() {

        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "完蛋了，居然没有指纹权限--xxxxxxxxxxxxxxxxx");
            Toast.makeText(mContext, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e(TAG, "有指纹权限");
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Log.e(TAG, "完蛋了，没有指纹识别模块--xxxxxxxxxxxxxxxxx");
            Toast.makeText(mContext, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e(TAG, "有指纹模块");
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            Log.e(TAG, "完蛋了，没有开启锁屏密码--xxxxxxxxxxxxxxxxx");
            Toast.makeText(mContext, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e(TAG, "已开启锁屏密码");
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Log.e(TAG, "完蛋了，没有录入指纹--xxxxxxxxxxxxxxxxx");
            Toast.makeText(mContext, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e(TAG, "已录入指纹");

        return true;
    }

    CancellationSignal mCancellationSignal = new CancellationSignal();
    //回调方法
    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            Toast.makeText(mContext, errString, Toast.LENGTH_SHORT).show();
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

            Toast.makeText( mContext, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

//            Toast.makeText( mContext, "指纹识别成功", Toast.LENGTH_SHORT).show();
            fTv1.setText("指纹识别成功");

        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText( mContext, "指纹识别失败", Toast.LENGTH_SHORT).show();
        }
    };


    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);


    }

    /**
     * 锁屏密码
     */
    private void showAuthenticationScreen() {

        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Log(String tag, String msg) {
        Log.e(tag, msg);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void onFingerprintClick(View v) {

        FingerprintUtil.callFingerPrint(new FingerprintUtil.OnCallBackListenr() {
            AlertDialog dialog;

            @Override
            public void onSupportFailed() {
                Log.e("tag","当前设备不支持指纹");
                showToast("当前设备不支持指纹");
            }

            @Override
            public void onInsecurity() {
                Log.e("tag","当前设备未处于安全保护中");
                showToast("当前设备未处于安全保护中");
            }

            @Override
            public void onEnrollFailed() {
                Log.e("tag","请到设置中设置指纹");
                showToast("请到设置中设置指纹");
            }

            @Override
            public void onAuthenticationStart() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_fingerprint, null);
                initView(view);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeMessages(0);
                        FingerprintUtil.cancel();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                showToast(errString.toString());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    handler.removeMessages(0);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                Log.e("tag","解锁失败");
                showToast("解锁失败");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                showToast(helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                showToast("解锁成功");
                Log.e("tag","解锁成功");
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    handler.removeMessages(0);
                }

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                int i = postion % 5;
                if (i == 0) {
                    tv[4].setBackground(null);
//                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
//                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tv[i - 1].setBackground(null);
                }
                postion++;
                handler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };
    TextView[] tv = new TextView[5];
    private int postion = 0;

    private void initView(View view) {
        postion = 0;
        tv[0] = (TextView) view.findViewById(R.id.tv_1);
        tv[1] = (TextView) view.findViewById(R.id.tv_2);
        tv[2] = (TextView) view.findViewById(R.id.tv_3);
        tv[3] = (TextView) view.findViewById(R.id.tv_4);
        tv[4] = (TextView) view.findViewById(R.id.tv_5);
        handler.sendEmptyMessageDelayed(0, 100);
    }


    public void showToast(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}
