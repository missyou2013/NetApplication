package net.com.mvp.ac.wuxi;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.NiFanModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NiFanActivity extends BaseActivity {
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
    @BindView(R.id.ac_car_nifan_xishu)
    TextView acCarNifanXishu;
    @BindView(R.id.ac_car_nifan_guid)
    TextView acCarNifanGuid;
    @BindView(R.id.ac_car_nifan_times)
    TextView acCarNifanTimes;
    @BindView(R.id.ac_car_nifan_color)
    TextView acCarNifanColor;
    private NiFanActivity instance = null;
    private CarsInforModel carsInforModel;
    private List<NiFanModel> niFanModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ni_fan);
        ButterKnife.bind(this);

        setTopTitle("逆反系数");
        setBackBtn();
        instance = this;
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
    }

    //逆反系数查询
    private void getDataWaiKuo(int Detection_ID) {
        UtilsLog.e("逆反系数查询---url==" + SharedPreferencesUtils.getIP(this) + NIFAN_XISHU);
        UtilsLog.e("Detection_ID==" + "{\"Detection_ID\":" + Detection_ID + "}");

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + NIFAN_XISHU).tag(this)
                .upJson("{\"Detection_ID\":" + Detection_ID + "}")
//                .params("Detection_ID", Detection_ID)
//                .params("Detection_ID","{\"Detection_ID\":"+ Detection_ID +"}")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instance, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("逆反系数查询-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("逆反系数查询-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            niFanModelList = new ArrayList<NiFanModel>();
                            niFanModelList = JsonUtil.stringToArray(newResult, NiFanModel[].class);
                            acCarNifanXishu.setText(niFanModelList.get(0).getTestValue() + "");
                            acCarNifanGuid.setText(niFanModelList.get(0).getGUID() + "");
                            acCarNifanTimes.setText(niFanModelList.get(0).getTimes() + "");
                            acCarNifanColor.setText(niFanModelList.get(0).getReflectiveColor());
//                            if ("1".equals(niFanModelList.get(0).getM1_Verdict())) {
//                                acCarWaikuoPanding.setText("合格");
//                            } else if ("2".equals(niFanModelList.get(0).getM1_Verdict())) {
//                                acCarWaikuoPanding.setText("不合格");
//                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "没有获取到数据", Toast.LENGTH_SHORT).show();
                            UtilsLog.e("逆反系数查询-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instance);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("逆反系数寸查询-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instance);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataWaiKuo(carsInforModel.getID());
//        getDataWaiKuo(78);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instance = null;
    }
}
