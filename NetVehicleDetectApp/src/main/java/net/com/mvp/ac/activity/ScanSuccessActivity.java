package net.com.mvp.ac.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.com.mvp.ac.R;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//扫描成功
public class ScanSuccessActivity extends BaseActivity   {
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
    @BindView(R.id.ac_scan_success_tv_chepai)
    TextView acScanSuccessTvChepai;
    @BindView(R.id.ac_scan_et_phone)
    EditText acScanEtPhone;
    @BindView(R.id.ac_scan_success_btn_call)
    Button acScanSuccessBtnCall;

    private ScanSuccessActivity instance;
    private String scan_success_result = "";

    String my_phone = "",QRCODE_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_success);
        ButterKnife.bind(this);


        instance = this;
        setBackBtn();
        setTopTitle("扫描结果");
        scan_success_result = getIntent().getStringExtra("qrcoding_result");
        Log.e("tag","ScanSuccessActivity-result==" + scan_success_result);
        if (!TextUtils.isEmpty(scan_success_result)) {

            if (scan_success_result.contains(QRCODE_URL)) {

                    if (!TextUtils.isEmpty(scan_success_result)) {
                        String str = scan_success_result.replace(QRCODE_URL + "&id=", "");
                        Log.e("tag","ScanSuccessActivity-str==" + str);
                        if (!TextUtils.isEmpty(str)) {
//                            get_UserInfor(str);
                        } else {
                            Toast.makeText(instance, "未扫描到相关信息", Toast
                                    .LENGTH_LONG).show();
//                            instance.finish();
                        }

                    }

            } else {
                Toast.makeText(instance, "未扫描到相关信息", Toast
                        .LENGTH_LONG).show();
//                instance.finish();
            }
        } else {
            Toast.makeText(instance, "未扫描到相关信息", Toast
                    .LENGTH_LONG).show();
//            instance.finish();
        }


    }






    @OnClick({R.id.ac_scan_et_phone, R.id.ac_scan_success_btn_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_scan_et_phone:
                break;
            case R.id.ac_scan_success_btn_call:
                break;
        }
    }

    class gotoPhone extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
//            UtilsLog.e("AXphone==" + "5555555555555");
//            AXphone(scanSuccessModel.getTelPhone(), my_phone);
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }




}
