package net.com.mvp.ac.wuxi;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;
import net.com.mvp.ac.api.ApiConfig;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.WaiKuoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 外廓尺寸
 **/
public class WaiKuoActivity extends BaseActivity implements ApiConfig {


    public WaiKuoActivity instances = null;
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
    @BindView(R.id.ac_car_waikuo_chang)
    TextView acCarWaikuoChang;
    @BindView(R.id.ac_car_waikuo_kuan)
    TextView acCarWaikuoKuan;
    @BindView(R.id.ac_car_waikuo_gao)
    TextView acCarWaikuoGao;
    @BindView(R.id.ac_car_waikuo_xianzhi)
    TextView acCarWaikuoXianzhi;
    @BindView(R.id.ac_car_waikuo_panding)
    TextView acCarWaikuoPanding;

    private CarsInforModel carsInforModel;
    private List<WaiKuoModel> waiKuoModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wai_kuo);
        ButterKnife.bind(this);
        setTopTitle("外廓尺寸");
        setBackBtn();
        instances = this;

        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
        UtilsLog.e("carsInforModel---getID==" + carsInforModel.getID());
    }

    //外廓尺寸查询
    private void getDataWaiKuo(int Detection_ID) {
        UtilsLog.e("外廓尺寸查询---url==" + SharedPreferencesUtils.getIP(this) + WAI_KUO_SIZE);
        UtilsLog.e("Detection_ID==" + "{\"Detection_ID\":" + Detection_ID + "}");

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + WAI_KUO_SIZE).tag(this)
                .upJson("{\"Detection_ID\":" + Detection_ID + "}")
//                .params("Detection_ID", Detection_ID)
//                .params("Detection_ID","{\"Detection_ID\":"+ Detection_ID +"}")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("外廓尺寸查询-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("外廓尺寸查询-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            waiKuoModelList = new ArrayList<WaiKuoModel>();
                            waiKuoModelList = JsonUtil.stringToArray(newResult, WaiKuoModel[].class);
                            acCarWaikuoChang.setText(waiKuoModelList.get(0).getM1_Length_Value()+"mm");
                            acCarWaikuoKuan.setText(waiKuoModelList.get(0).getM1_Width_Value()+"mm");
                            acCarWaikuoGao.setText(waiKuoModelList.get(0).getM1_Height_Value()+"mm");
                            acCarWaikuoXianzhi.setText(waiKuoModelList.get(0).getM1_standard());
                            if ("1".equals(waiKuoModelList.get(0).getM1_Verdict())) {
                                acCarWaikuoPanding.setText("合格");
                            } else if ("2".equals(waiKuoModelList.get(0).getM1_Verdict())) {
                                acCarWaikuoPanding.setText("不合格");
                            }
                        } else {
                            UtilsLog.e("外廓尺寸查询-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("外廓尺寸查询-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDataWaiKuo( carsInforModel.getID());
        getDataWaiKuo(78);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }
}
