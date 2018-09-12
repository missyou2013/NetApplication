package net.com.mvp.ac.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.NewCarsModel;
import net.com.mvp.ac.model.UpLineModel;
import net.com.mvp.ac.model.UserAccountModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新车调度详情页
 **/
public class NewCarDetailsActivity extends BaseActivity {
    public NewCarDetailsActivity instances = null;

    @BindView(R.id.ac_new_car_details_chepai_haoma)
    TextView acNewCarDetailsChepaiHaoma;
    @BindView(R.id.ac_new_car_details_haopaileixing)
    TextView acNewCarDetailsHaopaileixing;
    @BindView(R.id.ac_new_car_details_cheliangleixing)
    TextView acNewCarDetailsCheliangleixing;
    @BindView(R.id.ac_new_car_details_vin)
    TextView acNewCarDetailsVin;
    @BindView(R.id.ac_new_car_details_chepai_haoma2)
    TextView acNewCarDetailsChepaiHaoma2;
    @BindView(R.id.ac_new_car_details_haopaileixing2)
    TextView acNewCarDetailsHaopaileixing2;
    @BindView(R.id.ac_new_car_details_cheliangleixing2)
    TextView acNewCarDetailsCheliangleixing2;
    @BindView(R.id.ac_new_car_details_vin2)
    TextView acNewCarDetailsVin2;
    @BindView(R.id.ac_new_car_details_layout_2)
    LinearLayout acNewCarDetailsLayout2;
    @BindView(R.id.ac_new_car_details_name)
    TextView acNewCarDetailsName;
    @BindView(R.id.lay_line_ac_new_details_username)
    LinearLayout layLineAcNewDetailsUsername;
    @BindView(R.id.ac_new_car_details_waijianchedao)
    TextView acNewCarDetailsWaijianchedao;
    @BindView(R.id.lay_line_ac_new_car_details_waijianchedao)
    LinearLayout layLineAcNewCarDetailsWaijianchedao;
    @BindView(R.id.ac_new_car_details_cancle)
    Button acNewCarDetailsCancle;
    @BindView(R.id.ac_new_set_view_mm)
    TextView acNewSetViewMm;
    @BindView(R.id.ac_new_car_details_start)
    Button acNewCarDetailsStart;

    private String Login_name = "", login_IDNumber;//当前登录的检测员的名字
    private List<NewCarsModel> newCarsModelArrayList_intent = new ArrayList<NewCarsModel>();//传递数据用的
    private String Line = "1";//检测线号
    private int amountNum = 0;//传递过来的对象的个数
    private List<UserAccountModel> dipan_users = new ArrayList<>();
    private String[] dipan_users_;
    private UpLineModel upLineModel;
    private String zhoushu = "0";
    private int dipan_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_details);
        ButterKnife.bind(this);

        setTopTitle("车辆详情");
        setBackBtn();
        instances = this;
        upLineModel = new UpLineModel();

        Login_name = getIntent().getExtras().getString("login_UserID", "");
        login_IDNumber = getIntent().getExtras().getString("login_IDNumber", "");
        newCarsModelArrayList_intent = (ArrayList<NewCarsModel>) getIntent().getSerializableExtra
                ("new_car_model");
        zhoushu = getIntent().getExtras().getString("zhoushu","0");
        UtilsLog.e("2222=JIANCE_MODE=" + BaseApplication.JIANCE_MODE);
        acNewCarDetailsName.setText(Login_name);

        if (newCarsModelArrayList_intent != null && newCarsModelArrayList_intent.size() > 0) {
            amountNum = newCarsModelArrayList_intent.size();
            UtilsLog.e("2222=amountNum=" + amountNum);
//            for (int i = 0; i < newCarsModelArrayList_intent.size(); i++) {
//                UtilsLog.e("2222==" + newCarsModelArrayList_intent.get(i).toString());
//            }
        }
        initViews();
        if (BaseApplication.userAccountModelList != null && BaseApplication.userAccountModelList.size() > 0) {
            for (int i = 0; i < BaseApplication.userAccountModelList.size(); i++) {
                if (BaseApplication.userAccountModelList.get(i).getUserRight
                        ().contains("5")) {
                    //底盘静态员--权限---5
                    dipan_users.add(BaseApplication.userAccountModelList.get(i));
                }
            }
            if (dipan_users != null && dipan_users.size() > 0) {
                dipan_users_ = new String[dipan_users.size()];
                for (int i = 0; i < dipan_users_.length; i++) {
                    dipan_users_[i] = dipan_users.get(i).getUserName();
                }
            }
        }
    }

    private void initViews() {
        switch (amountNum) {
            case 1:
                acNewCarDetailsLayout2.setVisibility(View.GONE);
                //车牌号码
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getPlateRegion()) && !TextUtils
                        .isEmpty(newCarsModelArrayList_intent.get(0).getPlateNO())) {
                    acNewCarDetailsChepaiHaoma.setText(newCarsModelArrayList_intent.get(0).getPlateRegion() +
                            newCarsModelArrayList_intent.get(0).getPlateNO
                                    ());
                }
                //号牌类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getPlateTypeName())) {
                    acNewCarDetailsHaopaileixing.setText(newCarsModelArrayList_intent.get(0)
                            .getPlateTypeName() + "");
                }
                //车辆类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getType())) {
                    acNewCarDetailsCheliangleixing.setText(newCarsModelArrayList_intent.get(0).getType() +
                            "");
                }
                //Vin
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getVIN())) {
                    acNewCarDetailsVin.setText(newCarsModelArrayList_intent.get(0).getVIN() + "");
                }
                break;
            case 2:
                acNewCarDetailsLayout2.setVisibility(View.VISIBLE);
                //车牌号码
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getPlateRegion()) && !TextUtils
                        .isEmpty(newCarsModelArrayList_intent.get(0).getPlateNO())) {
                    acNewCarDetailsChepaiHaoma.setText(newCarsModelArrayList_intent.get(0).getPlateRegion() +
                            newCarsModelArrayList_intent.get(0).getPlateNO
                                    ());
                }
                //号牌类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getPlateTypeName())) {
                    acNewCarDetailsHaopaileixing.setText(newCarsModelArrayList_intent.get(0)
                            .getPlateTypeName() + "");
                }
                //车辆类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getType())) {
                    acNewCarDetailsCheliangleixing.setText(newCarsModelArrayList_intent.get(0).getType() +
                            "");
                }
                //Vin
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(0).getVIN())) {
                    acNewCarDetailsVin.setText(newCarsModelArrayList_intent.get(0).getVIN() + "");
                }


                //车牌号码
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(1).getPlateRegion()) && !TextUtils
                        .isEmpty(newCarsModelArrayList_intent.get(1).getPlateNO())) {
                    acNewCarDetailsChepaiHaoma2.setText(newCarsModelArrayList_intent.get(1).getPlateRegion() +
                            newCarsModelArrayList_intent.get(1).getPlateNO
                                    ());
                }
                //号牌类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(1).getPlateTypeName())) {
                    acNewCarDetailsHaopaileixing2.setText(newCarsModelArrayList_intent.get(1)
                            .getPlateTypeName() + "");
                }
                //车辆类型
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(1).getType())) {
                    acNewCarDetailsCheliangleixing2.setText(newCarsModelArrayList_intent.get(1).getType() +
                            "");
                }
                //Vin
                if (!TextUtils.isEmpty(newCarsModelArrayList_intent.get(1).getVIN())) {
                    acNewCarDetailsVin2.setText(newCarsModelArrayList_intent.get(1).getVIN() + "");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    @OnClick({R.id.ac_new_car_details_name, R.id.ac_new_car_details_waijianchedao, R.id
            .ac_new_car_details_cancle, R.id.ac_new_car_details_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_new_car_details_name:
                //底盘静态员
                if (dipan_users_ != null && dipan_users_.length > 0) {
                    dipan_users_List();
                } else {
                    Toast.makeText(instances, "检测员没有权限",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ac_new_car_details_waijianchedao:
                //检测线号
                dialogList();
                break;
            case R.id.ac_new_car_details_cancle:
                //取消
                instances.finish();
                break;
            case R.id.ac_new_car_details_start:
                //上线
                if (CommonUtils.isOnline(this)) {
                    upLineModel.setLine(Line);
                    String para = "";
                    switch (amountNum) {
                        case 1:
                            para = "0," + Line + "," + newCarsModelArrayList_intent.get(0).getQueueID() +
                                    ",0," + zhoushu + ",0," + Login_name + "," +
                                    BaseApplication.userAccountModelList.get(dipan_index).getUserName() + "," +
                                    login_IDNumber +","+BaseApplication.userAccountModelList.get(dipan_index).getIDNumber();
                            break;
                        case 2:
                            para = "1," + Line + "," + newCarsModelArrayList_intent.get(0).getQueueID() +
                                    ",0," + newCarsModelArrayList_intent.get(1).getID() + ",0,"
                                    + Login_name + "," + BaseApplication.userAccountModelList.get(dipan_index).getUserName()
                                    + "," + login_IDNumber +","+
                                    BaseApplication.userAccountModelList.get(dipan_index).getIDNumber();
                            break;
                    }
                    upLineModel.setPara(para);
                    getDataReportData(JsonUtil.toJson(upLineModel));
                } else {
                    Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                            .show();
                }

                break;
        }
    }

    /**
     * 列表
     */
    public void dialogList() {
        final String items[] = Constants.ZHOUSHOU_LIST;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        builder.setTitle("请选检测线号");
        // builder.setMessage("是否确认退出?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Line = items[which];
                acNewCarDetailsWaijianchedao.setText(Line);
                dialog.dismiss();
//                Toast.makeText(instances, items[which],
//                        Toast.LENGTH_SHORT).show();

            }
        });
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(instances, "确定", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
        builder.create().show();
    }

    /**
     * 底盘静态员列表
     */

    public void dipan_users_List() {
        final String items[] = dipan_users_;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        builder.setTitle("请选择底盘静态员");
        // builder.setMessage("是否确认退出?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dipan_index = which;
                acNewCarDetailsName.setText(items[which]);
                dialog.dismiss();
//                Toast.makeText(instances, items[which],
//                        Toast.LENGTH_SHORT).show();

            }
        });
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(instances, "确定", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
        builder.create().show();
    }

    //上线请求
    private void getDataReportData(final String strJson) {
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + CARS_UP_LINE);
        UtilsLog.e("getDataReportData-strJson==" + strJson);
        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + CARS_UP_LINE).tag(instances)
                .upJson(strJson)
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
                        String logStr = "\n" + "调度车辆自动上线--上传自己后台数据--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                CARS_UP_LINE + "\n" + "JSON::" + strJson +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        if ("\"ok\"".equals(result)) {
                            Toast.makeText(instances, "车辆上线成功", Toast.LENGTH_LONG).show();
                            instances.finish();
                        } else {
//                            showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        String logStr = "\n" + "调度车辆自动上线--上传自己后台数据-onError-失败" +
                                "\n"  + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                    }
                });
    }

}
