package net.com.mvp.ac.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.com.mvp.ac.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

//扫描二维码
public class ScanQrCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = "ff";
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    @BindView(R.id.ac_scan_zxingview)
    ZXingView acScanZxingview;
    @BindView(R.id.ac_scan_tv_middle)
    TextView acScanTvMiddle;
    @BindView(R.id.ac_scan_btn_ok)
    Button acScanBtnOk;
    @BindView(R.id.ac_scan_btn_no)
    Button acScanBtnNo;
    //    @InjectView(R.id.ac_scan_zxingview)
//    ZXingView acScanZxingview;
//    @InjectView(R.id.ac_scan_tv_middle)
//    TextView acScanTvMiddle;
//    @InjectView(R.id.ac_scan_btn_ok)
//    Button acScanBtnOk;
//    @InjectView(R.id.ac_scan_btn_no)
//    Button acScanBtnNo;
    private ScanQrCodeActivity instance;
    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_scan_qr_code);
        ButterKnife.bind(this);

        instance = this;
        mQRCodeView = (ZXingView) findViewById(R.id.ac_scan_zxingview);
        mQRCodeView.setDelegate(instance);
        setBackBtn();
        setTopTitle("扫描二维码");
        initView();
        initData();

        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent i2=new Intent(instance,ScanSuccessActivity.class);
                instance.startActivity(i2);
                instance.finish();
            }
        }, 4*1000);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
        instance = null;
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e(TAG, "result:" + result);
//        Intent intent = new Intent(instance, ScanSuccessActivity.class);
//        intent.putExtra("qrcoding_result", result);
//        startActivity(intent);
//        instance.finish();
//        call(result);
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mQRCodeView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(instance, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(instance, "手机号==="+result, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
    }




    @OnClick({R.id.ac_scan_btn_ok, R.id.ac_scan_btn_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_scan_btn_ok:
                mQRCodeView.openFlashlight();
                break;
            case R.id.ac_scan_btn_no:
                mQRCodeView.closeFlashlight();
                break;
        }
    }
}
