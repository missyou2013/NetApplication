package net.com.mvp.ac.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.MyProvincesListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.commons.VehicleCheckUtils;
import net.com.mvp.ac.model.CarItemStartModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.ReportedModel;
import net.com.mvp.ac.model.UpdateCarStatusModel;
import net.com.mvp.ac.wuxi.NoticesActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待检测车辆详情
 * 外检
 */
public class CarDetailsActivity extends BaseActivity {

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
    CarsInforModel carsInforModel;

    private CarDetailsActivity instances = null;
    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private String Line = "1";//外检车道
    private String Item_Code = "";//F1外检,R1路试，DC底盘动态
    private String Login_name = "", login_IDNumber;//当前登录的检测员的名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle(R.string.ac_cardetails_title);
        pop_xuhao();
        instances = this;

        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
        login_IDNumber = getIntent().getExtras().getString("login_IDNumber", "");
        Login_name = getIntent().getExtras().getString("Login_name", "");
        UtilsLog.e("carsInforModel=44=" + carsInforModel.toString());

        CHECK_MODE = BaseApplication.JIANCE_MODE;
        UtilsLog.e("CarDetailsActivity---CHECK_MODE==" + CHECK_MODE);
        switch (CHECK_MODE) {
            case 0:
                Item_Code = "F1";
                break;
            case 1:
                Item_Code = "R1";
                break;
            case 2:
                Item_Code = "DC";
                break;
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
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
            case R.id.ac_car_details_waijianchedao:
//                pop_xuhao.showAtLocation(acCarDetailsWaijianchedao, Gravity.RIGHT, 0, 0);
                pop_xuhao.showAsDropDown(acCarDetailsWaijianchedao);
                listview_xuhao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acCarDetailsWaijianchedao.setText(xuhaoList.get(position));
//                        settingModel.setChepai_left(xuhaoList.get(position));
                        Line = xuhaoList.get(position);
                        pop_xuhao.dismiss();
                    }
                });
                break;
            case R.id.ac_car_details_chejianxianhao:
//                pop_xuhao.showAtLocation(acCarDetailsChejianxianhao, Gravity.RIGHT, 0, 0);
                pop_xuhao.showAsDropDown(acCarDetailsChejianxianhao);
                listview_xuhao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acCarDetailsChejianxianhao.setText(xuhaoList.get(position));
//                        settingModel.setChepai_left(xuhaoList.get(position));
                        pop_xuhao.dismiss();
                    }
                });
                break;
            case R.id.ac_car_details_cancle:
//                instances.finish();
                //跳转到查询公告

                Intent i = new Intent(instances, NoticesActivity.class);
                i.putExtra("model_CarsInforModel", carsInforModel);
                i.putExtra("check_mode", CHECK_MODE);
                i.putExtra("Login_name", Login_name);
                i.putExtra("login_IDNumber", login_IDNumber);
                startActivity(i);
                break;
            case R.id.ac_car_details_start:
                //项目开始
                if (!TextUtils.isEmpty(Item_Code)) {
                    getDataWaiJainStart("");
                    String logStr = "车牌号码：：" + carsInforModel.getPlateRegion() + carsInforModel
                            .getPlateNO() + "\n" + "动作：点击项目开始按钮";
                    PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

//                    if (carsInforModel.getAppearanceStatus() == 0) {
//                        getDataWaiJainStart("");
//                        String logStr = "车牌号码：：" + carsInforModel.getPlateRegion() + carsInforModel
//                                .getPlateNO() + "\n" + "动作：点击项目开始按钮";
//                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
//                    } else {
//                        updateCarStatusInfor(1);
//                        Intent intent = new Intent(CarDetailsActivity.this, WaiJianPhotoActivity.class);
//                        intent.putExtra("CarDetailsActivity_CarsInforModel", carsInforModel);
//                        intent.putExtra("CarDetailsActivity_acCarDetailsWaijianchedao",
//                                acCarDetailsWaijianchedao
//                                        .getText().toString().trim());
//                        intent.putExtra("CarDetailsActivity_Line", Line);
//                        startActivity(intent);
//                        finish();
//                    }

                }
                break;
        }
    }

    //popuwindow
    private View popu_xuhao_view;
    private LinearLayout parent22;
    private PopupWindow pop_xuhao = null;
    private ListView listview_xuhao;
    private MyProvincesListAdapter myProAdapter;
    private List<String> xuhaoList = new ArrayList<String>();

    private void pop_xuhao() {
        for (int i = 0; i < Constants.WAIJIAN_CHEDAO_CHEJIAN_XIANHAO.length; i++) {
            xuhaoList.add(i, Constants.WAIJIAN_CHEDAO_CHEJIAN_XIANHAO[i]);
        }
        pop_xuhao = new PopupWindow(this);
        popu_xuhao_view = getLayoutInflater().inflate(R.layout.item_setting_province_letter,
                null);
        pop_xuhao.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_xuhao.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_xuhao.setBackgroundDrawable(new BitmapDrawable());
        pop_xuhao.setFocusable(true);
        pop_xuhao.setOutsideTouchable(true);
        pop_xuhao.setContentView(popu_xuhao_view);
        parent22 = (LinearLayout) popu_xuhao_view.findViewById(R.id.setting_provinces_parent);
        listview_xuhao = (ListView) popu_xuhao_view.findViewById(R.id.setting_provinces_listview2);
        myProAdapter = new MyProvincesListAdapter(this, xuhaoList);
        listview_xuhao.setAdapter(myProAdapter);
        parent22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop_xuhao.dismiss();
            }
        });
    }

    //flag:0==start;1==end
    private void getData(int flag) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(this))) {
            if (CommonUtils.isOnline(this)) {
                getServiceTime(flag);
            } else {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    //外检检测开始
    private void getDataWaiJainStart(String currentTime) {
        CarItemStartModel carItemStartModel = new CarItemStartModel();
        carItemStartModel.setPlatformSN(carsInforModel.getPlatformSN());
        carItemStartModel.setPlateType(carsInforModel.getPlateType());
//        carItemStartModel.setPlateNO(carsInforModel.getPlateNO());
        carItemStartModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
//        data.get(position).getPlateRegion() + data.get(position).getPlateNO()
        carItemStartModel.setVIN(carsInforModel.getVIN());
        carItemStartModel.setDetectionID(String.valueOf(carsInforModel.getID()));
        carItemStartModel.setLine(Line);
        carItemStartModel.setTest_times(carsInforModel.getTimes());
        carItemStartModel.setDetectionDevID(CommonUtils.getIMEI(this));
        carItemStartModel.setItemCode(Item_Code);
        BaseApplication.W_ItemStart_time = "";
        BaseApplication.W_ItemStart_time = DateUtil.currentTime2();
//        carItemStartModel.setDetectionItemStartDate(DateUtil.currentTime2());
        carItemStartModel.setDetectionItemStartDate(BaseApplication.W_ItemStart_time);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);

//        String upjson_url = "{[" + jsonStr.substring(1, jsonStr.length() - 1) + "]}";
        String upjson_url = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";

        UtilsLog.e("getDataWaiJainStart---jsonStr==" + upjson_url);
        UtilsLog.e("getDataWaiJainStart---url==" + SharedPreferencesUtils.getIP(this) + ITEM_START);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + ITEM_START)
                .tag(this)
                .upJson(upjson_url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().trim();
                        UtilsLog.e("getDataWaiJainStart-result==" + result);
                        UtilsLog.e("getDataWaiJainStart-result=22=" + result.substring(1, result.length() -
                                1));
                        String logStr = "\n" + "外检项目开始----请求成功" + "\n" +
                                "result::" + response.body() + "\n" +
                                "URL::" + SharedPreferencesUtils.getIP(instances) + ITEM_START;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        String str = result.substring(1, result.length() - 1);
//                        if ("ok".equals(str)) {

//                            updateCarStatusInfor(1);
                        getDataReportData();
                        Intent intent = new Intent(CarDetailsActivity.this, WaiJianPhotoActivity.class);
                        intent.putExtra("CarDetailsActivity_CarsInforModel", carsInforModel);
                        intent.putExtra("CarDetailsActivity_acCarDetailsWaijianchedao",
                                acCarDetailsWaijianchedao.getText().toString().trim());
                        intent.putExtra("CarDetailsActivity_Line", Line);
                        intent.putExtra("login_w_name", Login_name);
                        intent.putExtra("login_IDNumber", login_IDNumber);

                        startActivity(intent);
                        finish();
//                        } else {
//                            Toast.makeText(CarDetailsActivity.this, "上线失败，请重试", Toast.LENGTH_LONG)
//                                    .show();
//                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        String logStr2 = "\n" + "外检项目开始请求--onError" + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr2.getBytes());
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    /**
     * 更新车辆状态
     *
     * @param flag flag:0 未检测
     * flag:1 项目开始
     * flag:2 项目结束
     * flag:99  中途退出的情况
     */
    String jsonStr;

    private void updateCarStatusInfor(int flag) {
        UpdateCarStatusModel model = new UpdateCarStatusModel();
        model.setType(0);
        model.setQueueID(String.valueOf(carsInforModel.getID()));
        switch (CHECK_MODE) {
            case 0:
                model.setAppearanceStatus(flag);
                break;
            case 1:
                model.setRoadStatus(flag);
                break;
            case 2:
                model.setDynamicStatus(flag);
                break;
        }
        Gson gson = new Gson();
        jsonStr = gson.toJson(model);
        UtilsLog.e("updateCarStatusInfor---jsonStr==" + jsonStr);
        UtilsLog.e("updateCarStatusInfor---url==" + SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS)
                .tag(this)
                .upJson(jsonStr)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().trim();
                        UtilsLog.e("updateCarStatusInfor-result==" + result);
                        UtilsLog.e("updateCarStatusInfor-result=22=" + result.substring(1, result.length()
                                - 1));
                        String logStr = "\n" + "外检-车辆详情--更新车辆状态请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "JSON::" + jsonStr + "\n" + "result::" +
                                response.body() + "\n";
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
//                        String str = result.substring(1, result.length() - 1);
//                        if ("ok".equals(str)) {

//                            Intent intent = new Intent(CarDetailsActivity.this, WaiJianPhotoActivity.class);
//                            intent.putExtra("CarDetailsActivity_CarsInforModel", carsInforModel);
//                            intent.putExtra("CarDetailsActivity_acCarDetailsWaijianchedao",
//                                    acCarDetailsWaijianchedao
//                                            .getText().toString().trim());
//                            intent.putExtra("CarDetailsActivity_Line", Line);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(CarDetailsActivity.this, "上线失败，请重试", Toast.LENGTH_LONG)
//                                    .show();
//                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        String logStr = "\n" + "外检-车辆详情--更新车辆状态请求失败--error" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //获取服务器时间接口
    //startORend:0==开始；1==结束
    private void getServiceTime(final int startORend) {
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + GET_SERVICE_TIME).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getServiceTime-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        if (!TextUtils.isEmpty(result) && !"[]".equals(result)) {

                            if (startORend == 0) {
                                //项目开始
                                getDataWaiJainStart("");
                            } else if (startORend == 1) {

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getServiceTime-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    //组装要上报的json
    private String reportedJsonStr() {
        String myjson = "";
        ReportedModel model;
        model = new ReportedModel();
        model.setDataType("F1");
        model.setDetection_ID(String.valueOf(carsInforModel.getID()));
        model.setGUID(carsInforModel.getGUID());
        model.setTest_times(carsInforModel.getTimes());
        model.setUnqualified_Code1("0");
        model.setUnqualified_Code2("0");
        model.setUnqualified_Code3("0");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model);
        myjson = "[{" + jsonStr.substring(1, jsonStr.length() - 1) + "}]";
        UtilsLog.e("getDataReportData---myjson==" + myjson);

        return myjson;
    }

    private void getDataReportData() {
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + REPORTED_DATA);
        String myjson = reportedJsonStr();
        String logStr = "\n" + "外检-车辆详情--上报数据请求成功--请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                REPORTED_DATA + "\n" + "JSON::" + myjson + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + REPORTED_DATA).tag(instances)
                .upJson(myjson)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataReportData-result==" + result);
                        String logStr = "\n" + "外检-车辆详情--上报数据请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "JSON::" + reportedJsonStr() +
                                "\n" + "result::" + result + "\n";
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        if ("\"ok\"".equals(result)) {
//                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
//                            instances.finish();

                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        String logStr = "\n" + "外检-车辆详情--上报数据请求失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "JSON::" + reportedJsonStr() +
                                "\n" + "result::" + response.body() + "\n";
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
                    }
                });
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
////            getDataWaiJainEnd();
//            updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
//            finish();
//            return false;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }
}
