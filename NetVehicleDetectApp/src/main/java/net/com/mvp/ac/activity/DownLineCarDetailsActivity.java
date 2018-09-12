package net.com.mvp.ac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.commons.VehicleCheckUtils;
import net.com.mvp.ac.model.CarItemEndModel;
import net.com.mvp.ac.model.CarsInforModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待检测车辆详情
 * 外检
 */
public class DownLineCarDetailsActivity extends BaseActivity {

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


    @BindView(R.id.ac_car_details_chepai_haoma)
    TextView acCarDetailsChepaiHaoma;
    @BindView(R.id.ac_car_details_haopaileixing)
    TextView acCarDetailsHaopaileixing;
    @BindView(R.id.ac_car_details_cheliangleixing)
    TextView acCarDetailsCheliangleixing;
    @BindView(R.id.ac_car_details_vin)
    TextView acCarDetailsVin;
    @BindView(R.id.ac_car_details_waijianchedao)
    TextView acCarDetailsWaijianchedao;
    @BindView(R.id.ac_car_details_chejianxianhao)
    TextView acCarDetailsChejianxianhao;
    @BindView(R.id.ac_car_details_shifouwaijian)
    TextView acCarDetailsShifouwaijian;
    @BindView(R.id.ac_car_details_zongzhiliang)
    TextView acCarDetailsZongzhiliang;
    @BindView(R.id.ac_car_details_zhengbeizhiliang)
    TextView acCarDetailsZhengbeizhiliang;
    @BindView(R.id.ac_car_details_chewaikuochang)
    TextView acCarDetailsChewaikuochang;
    @BindView(R.id.ac_car_details_chewaikuokuan)
    TextView acCarDetailsChewaikuokuan;
    @BindView(R.id.ac_car_details_chewaikuogao)
    TextView acCarDetailsChewaikuogao;
    @BindView(R.id.ac_car_details_jianyanliushui)
    TextView acCarDetailsJianyanliushui;
    @BindView(R.id.ac_car_details_jianyancishu)
    TextView acCarDetailsJianyancishu;
    @BindView(R.id.ac_car_details_shiyongxingzhi)
    TextView acCarDetailsShiyongxingzhi;
    @BindView(R.id.ac_car_details_cheshenyanse)
    TextView acCarDetailsCheshenyanse;
    @BindView(R.id.ac_car_details_huoxiangchangdu)
    TextView acCarDetailsHuoxiangchangdu;
    @BindView(R.id.ac_car_details_huoxiangkuandu)
    TextView acCarDetailsHuoxiangkuandu;
    @BindView(R.id.ac_car_details_huoxianggaodu)
    TextView acCarDetailsHuoxianggaodu;
    @BindView(R.id.ac_car_details_zhuangtai)
    TextView acCarDetailsZhuangtai;
    @BindView(R.id.ac_car_details_cancle)
    Button acCarDetailsCancle;
    @BindView(R.id.ac_set_view_mm)
    TextView acSetViewMm;
    @BindView(R.id.ac_car_details_start)
    Button acCarDetailsStart;
    @BindView(R.id.lay_line_ac_car_details_waijianchedao)
    LinearLayout acCarDetailslayLine;
    CarsInforModel carsInforModel;

    private DownLineCarDetailsActivity instances = null;
    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private String Line = "1";//外检车道
    private String Item_Code = "";//F1外检,R1路试，DC底盘动态
    private String checkModeStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle("车辆信息");
        instances = this;
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel_downline");
        UtilsLog.e("carsInforModel=22=" + carsInforModel.toString());
        CHECK_MODE = BaseApplication.JIANCE_MODE;
        UtilsLog.e("CarDetailsActivity---CHECK_MODE==" + CHECK_MODE);
        switch (CHECK_MODE) {
            case 0:
                Item_Code = "F1";
                checkModeStr = "外检";
                break;
            case 1:
                Item_Code = "R1";
                checkModeStr = "路试";
                break;
            case 2:
                Item_Code = "DC";
                checkModeStr = "底盘动态";
                break;
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        acCarDetailsStart.setText("下线");
        acCarDetailslayLine.setVisibility(View.GONE);
        if (carsInforModel != null) {
            //车牌号码
            if (!TextUtils.isEmpty(carsInforModel.getPlateRegion()) && !TextUtils.isEmpty(carsInforModel
                    .getPlateNO())) {
                acCarDetailsChepaiHaoma.setText(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO
                        ());
            }
            //号牌类型
            if (!TextUtils.isEmpty(carsInforModel.getPlateTypeName())) {
                acCarDetailsHaopaileixing.setText(carsInforModel.getPlateTypeName() + "");
            }
            //车辆类型
            if (!TextUtils.isEmpty(carsInforModel.getType())) {
                acCarDetailsCheliangleixing.setText(carsInforModel.getType() + "");
            }
            //Vin
            if (!TextUtils.isEmpty(carsInforModel.getVIN())) {
                acCarDetailsVin.setText(carsInforModel.getVIN() + "");
            }
            //总质量
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getTotalMass()))) {
                acCarDetailsZongzhiliang.setText(String.valueOf(carsInforModel.getTotalMass()));
            }
            //整备质量
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getCurbWeight()))) {
                acCarDetailsZhengbeizhiliang.setText(String.valueOf(carsInforModel.getCurbWeight()));
            }
            //车外廓长
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getOutlineLength()))) {
                acCarDetailsChewaikuochang.setText(String.valueOf(carsInforModel.getOutlineLength()));
            }
            //车外廓宽
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getOutlineWidth()))) {
                acCarDetailsChewaikuokuan.setText(String.valueOf(carsInforModel.getOutlineWidth()));
            }
            //车外廓高
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getOutlineHeight()))) {
                acCarDetailsChewaikuogao.setText(String.valueOf(carsInforModel.getOutlineHeight()));
            }
            //检验流水
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getPlatformSN()))) {
                acCarDetailsJianyanliushui.setText(carsInforModel.getPlatformSN() + "");
            }
            //检验次数
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getTimes()))) {
                acCarDetailsJianyancishu.setText(carsInforModel.getTimes() + "");
            }
            //使用性质
            if (!TextUtils.isEmpty(carsInforModel.getUseProperty())) {
                acCarDetailsShiyongxingzhi.setText(carsInforModel.getUseProperty());
            }
            //车身颜色
            if (!TextUtils.isEmpty(carsInforModel.getBodyColor())) {
                acCarDetailsCheshenyanse.setText(VehicleCheckUtils.CarColorsFilter(carsInforModel
                        .getBodyColor()));
            }
        }
    }

    @OnClick({R.id.ac_car_details_waijianchedao, R.id.ac_car_details_chejianxianhao, R.id
            .ac_car_details_cancle, R.id.ac_car_details_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_car_details_cancle:
                String logStr22 = checkModeStr + "重新下线--底部取消按钮：："  ;
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr22.getBytes());
                instances.finish();
                break;
            case R.id.ac_car_details_start:
                //下线
                String logStr = checkModeStr + "重新下线--车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel
                        .getPlateNO() + "\n" + "动作：点击下线按钮";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr.getBytes());
                if (!TextUtils.isEmpty(Item_Code)) {
//                    if (carsInforModel.getAppearanceStatus() == 1) {
//                        getData();
//                    } else if (carsInforModel.getDynamicStatus() == 1) {
//                        getData();
//                    } else if (carsInforModel.getRoadStatus() == 1) {
                        getData();
//                    }
                }
                break;
        }
    }


    private void getData() {
        if (CommonUtils.isOnline(this)) {
            getDataWaiJainEnd("");
        } else {
            Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 项目总结束接口,最后一个执行
     */
    private void EndAllItem(final String detectionID) {
        UtilsLog.e(checkModeStr +"EndAllItem---url==" + SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
                detectionID + "/DetectionID");
        OkGo.<String>get(SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
                detectionID + "/DetectionID").tag(instances)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        CommonUtils.hideLoadingDialog(instances);
                        String result = response.body();
                        String logStr = "\n" + checkModeStr + "重新下线项目总结束:总结束:总结束--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_ALL_END +detectionID + "/DetectionID" +
                                "\n"+"\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        UtilsLog.e(checkModeStr +"EndAllItem-result==" + result);

                        instances.finish();
//                        if ("\"ok\"".equals(result)) {
//                            Toast.makeText(instances, "车辆下线成功", Toast.LENGTH_LONG).show();
//                            instances.finish();
//                        }else{
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        String logStr = "\n" + checkModeStr + "重新下线总结束:总结束:总结束--失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_ALL_END +
                                detectionID + "/DetectionID" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        UtilsLog.e(checkModeStr +"EndAllItem-onError==" + response.body());
                        UtilsLog.e(checkModeStr +"EndAllItem-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                    }
                });
    }

    //外检检测结束
    String getDataWaiJainEnd_upjson_url;

    private void getDataWaiJainEnd(String endTime) {
        CarItemEndModel carItemStartModel = new CarItemEndModel();
        carItemStartModel.setPlatformSN(carsInforModel.getPlatformSN());
        carItemStartModel.setPlateType(carsInforModel.getPlateType());
//        carItemStartModel.setPlateNO(carsInforModel.getPlateNO());
        carItemStartModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
        carItemStartModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
        carItemStartModel.setVIN(carsInforModel.getVIN());
        carItemStartModel.setLine(Line);
        carItemStartModel.setTest_times(carsInforModel.getTimes());
        carItemStartModel.setDetectionDevID(CommonUtils.getIMEI(this));
        carItemStartModel.setItemCode(Item_Code);
        BaseApplication.W_ItemEnd_time = "";
        BaseApplication.W_ItemEnd_time = DateUtil.currentTime2();
        carItemStartModel.setDetectionItemEndDate(BaseApplication.W_ItemEnd_time);
//        carItemStartModel.setDetectionItemEndDate(endTime);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);
//        String upjson_url = "{[" + jsonStr.substring(1, jsonStr.length() - 1) + "]}";
        getDataWaiJainEnd_upjson_url = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        UtilsLog.e(checkModeStr +"getDataWaiJainEnd---jsonStr==" + getDataWaiJainEnd_upjson_url);
        UtilsLog.e(checkModeStr +"getDataWaiJainEnd---url==" + SharedPreferencesUtils.getIP(this) + ITEM_END);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + ITEM_END).tag(this)
                .upJson(getDataWaiJainEnd_upjson_url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e(checkModeStr +"getDataWaiJainEnd-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\",
                                "");
                        UtilsLog.e(checkModeStr +"getDataWaiJainEnd-newResult==" + newResult);
                        String logStr = "\n" + checkModeStr + "重新下线项目结束--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END +"\n" + "JSON::" + getDataWaiJainEnd_upjson_url+
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());


                        Toast.makeText(instances, "车辆重新下线完成", Toast.LENGTH_LONG).show();
                        //项目总结束
                        EndAllItem(String.valueOf(carsInforModel.getID()));
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //项目总结束
//                                EndAllItem(String.valueOf(carsInforModel.getID()));
//                            }
//                        }, 300);//后执行Runnable中的run方法

//                        if ("ok".equalsIgnoreCase(newResult)) {
////                            updateCarStatusInfor(2);
//                            Toast.makeText(instances, "车辆下线成功", Toast.LENGTH_LONG).show();
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //项目总结束
//                                    EndAllItem(String.valueOf(carsInforModel.getID()));
//                                }
//                            }, 300);//后执行Runnable中的run方法
//                        } else {
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
////                            Toast.makeText(instances, "下线失败，请重试", Toast.LENGTH_LONG)
////                                    .show();
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e(checkModeStr +"getDataWaiJainEnd-onError==" + response.body());
                        String logStr = "\n" + checkModeStr + "重新下线项目结束--失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances.finish();
        instances = null;
    }
    android.support.v7.app.AlertDialog.Builder builder = null;
    private void showDialog(String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder
                (instances);
        builder.setTitle("提示");
        builder.setMessage(content);
//        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

}
