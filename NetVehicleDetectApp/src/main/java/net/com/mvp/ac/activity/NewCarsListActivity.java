package net.com.mvp.ac.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.NewCarsListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.DownLineModel;
import net.com.mvp.ac.model.NewCarsModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新车调度
 */
public class NewCarsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.ac_new_cars_et_search2)
    EditText acNewCarsEtSearch2;
    @BindView(R.id.ac_new_cars_btn_search2)
    Button acNewCarsBtnSearch2;
    @BindView(R.id.ac_list_new_cars)
    ListView acListNewCars;
    @BindView(R.id.ac_new_cars_fab)
    Button acNewCarsFab;
    @BindView(R.id.ac_new_cars_btn_shangxian)
    Button acNewCarsBtnShangxian;

    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态 3:调度
    public NewCarsListActivity instances = null;
    private String Login_name = "", login_IDNumber;//当前登录的检测员的名字

    //查询出来的列表
    private List<NewCarsModel> carsInforModelList = new ArrayList<NewCarsModel>();
    //查询出来后过滤的列表
    private List<NewCarsModel> carsInforModelList_filter = new ArrayList<NewCarsModel>();
    private NewCarsListAdapter carsInforListAdapter;
    private String zhoushu = "0";//轴数
    private String carNO = null;
    private DownLineModel downLineModel;
    private List<NewCarsModel> searChCarsFilterList = new ArrayList<NewCarsModel>();//查询后过滤的列表
    private List<NewCarsModel> searChCarsList_one = new ArrayList<NewCarsModel>();//查询的列表
    private List<NewCarsModel> newCarsModelArrayList_intent = new ArrayList<NewCarsModel>();//传递数据用的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cars_list);
        ButterKnife.bind(this);

        setTopTitle("调度车辆列表");
        setBackBtn();
//        setHideLeftBtn();
        instances = this;
        Login_name = getIntent().getExtras().getString("login_UserID", "");
        login_IDNumber = getIntent().getExtras().getString("login_IDNumber", "");

        CHECK_MODE = BaseApplication.JIANCE_MODE;

        acNewCarsEtSearch2.setFocusable(true);
        acNewCarsEtSearch2.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        acNewCarsEtSearch2.setText("");
        zhoushu = "0";
        newCarsModelArrayList_intent = new ArrayList<NewCarsModel>();//传递数据用的
        UtilsLog.e("是否自动刷新==" + SharedPreferencesUtils.getAutoRefresh(this));
        if (SharedPreferencesUtils.getAutoRefresh(this)) {
            getData(0);
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    @OnClick({R.id.ac_new_cars_btn_search2, R.id.ac_new_cars_bt_type2,
            R.id.ac_new_cars_fab, R.id.ac_new_cars_btn_shangxian,
            R.id.ac_new_cars_et_search2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_new_cars_btn_shangxian:
                //上线
                int maxAccount = 0;
                for (int i = 0; i < NewCarsListAdapter.sSelected.size(); i++) {
                    if (NewCarsListAdapter.sSelected.get(i)) {
                        maxAccount = maxAccount + 1;
                    }
                }

                UtilsLog.e("选择的行数：maxAccount==" + maxAccount);
                if (maxAccount > 2) {
                    Toast.makeText(instances, "选择数目不能大于两辆", Toast.LENGTH_LONG).show();
                } else if (maxAccount == 2) {
                    //上线

                    for (int i = 0; i < NewCarsListAdapter.sSelected.size(); i++) {
                        if (NewCarsListAdapter.sSelected.get(i)) {
                            newCarsModelArrayList_intent.add(carsInforModelList_filter.get(i));
                        }
                    }
                    if (newCarsModelArrayList_intent != null && newCarsModelArrayList_intent.size() > 0) {
                        Intent intent = new Intent(instances, NewCarDetailsActivity.class);
                        intent.putExtra("new_car_model", (Serializable)newCarsModelArrayList_intent);
                        intent.putExtra("login_UserID", Login_name);
                        intent.putExtra("login_IDNumber", login_IDNumber);
                        intent.putExtra("zhoushu", zhoushu);
                        startActivity(intent);
                    } else {
                        UtilsLog.e("maxAccount == 2---没有数据");
                    }
                } else if (maxAccount == 1) {
                    int index = 0;
                    NewCarsModel model = null;
                    for (int i = 0; i < NewCarsListAdapter.sSelected.size(); i++) {
                        if (NewCarsListAdapter.sSelected.get(i)) {
                            index = i;
                        }
                    }
                    model = carsInforModelList_filter.get(index);
                    newCarsModelArrayList_intent.add(model);
                    if (model.getPlateType().equals("15") || (model.getType().substring(0, 1))
                            .equalsIgnoreCase("Q")) {
                        dialogList(newCarsModelArrayList_intent); // 轴数列表
                    } else {
                        //上线
                        Intent intent = new Intent(instances, NewCarDetailsActivity.class);
                        intent.putExtra("new_car_model", (Serializable)newCarsModelArrayList_intent);
                        intent.putExtra("login_UserID", Login_name);
                        intent.putExtra("login_IDNumber", login_IDNumber);
                        intent.putExtra("zhoushu", zhoushu);
                        startActivity(intent);
                    }
                } else if (maxAccount == 0) {
                    Toast.makeText(instances, "请先选择车辆", Toast.LENGTH_LONG).show();
                    UtilsLog.e("列表没有数据");
                }
                break;
            case R.id.ac_new_cars_btn_search2:
                //查询
                if (CommonUtils.isOnline(this)) {
                    carNO = acNewCarsEtSearch2.getText().toString().trim();
                    if (!TextUtils.isEmpty(carNO)) {
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                        downLineModel = new DownLineModel();
                        downLineModel.setType("0");
                        downLineModel.setStatus("0");
                        downLineModel.setPlateNO(carNO);
                        searChCarsFilterList = new ArrayList<NewCarsModel>();//查询后过滤的列表
                        getDataSearchCarsByNO(downLineModel);
//                        for (int i = 0; i < 2; i++) {
//                            if (i == 0) {
//                                downLineModel.setStatus("0");
//                                getDataSearchCarsByNO(downLineModel);
//                            } else if (i == 1) {
//                                downLineModel.setStatus("1");
//                                getDataSearchCarsByNO(downLineModel);
//                            }
//                        }
                    } else {
                        Toast.makeText(instances, "车牌号码不能为空", Toast
                                .LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                            .show();
                }

                break;
            case R.id.ac_new_cars_fab:
                //刷新
                if (SharedPreferencesUtils.getAutoRefresh(this)) {
                    getData(0);
                }
                break;
            case R.id.ac_new_cars_et_search2:
                //输入框

//                showSoftInputFromWindow(instances, acNewCarsEtSearch2);
                break;
        }
    }


    /**
     * 列表
     */
    private void dialogList(final List<NewCarsModel> model) {
        final String items[] = Constants.ZHOUSHOU_LIST;

        AlertDialog.Builder builder = new AlertDialog.Builder(instances, 3);
        builder.setTitle("请选择轴数");
        // builder.setMessage("是否确认退出?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zhoushu = items[which];
                //上线
                Intent intent = new Intent(instances, NewCarDetailsActivity.class);
                intent.putExtra("new_car_model", (Serializable)model);
                intent.putExtra("login_UserID", Login_name);
                intent.putExtra("login_IDNumber", login_IDNumber);
                intent.putExtra("zhoushu", zhoushu);
                startActivity(intent);

//                dialog.dismiss();
//                Toast.makeText(instances, items[which],
//                        Toast.LENGTH_SHORT).show();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                Toast.makeText(instances, "确定", Toast.LENGTH_SHORT)
//                        .show();
            }
        });
        builder.create().show();
    }


    private void getData(int type) {
        if (CommonUtils.isOnline(this)) {
            if (type == 0) {
                getDataNewCarsList();
            } else if (type == 1) {

            } else if (type == 2) {

            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    //获取车辆列表
    private void getDataNewCarsList() {
        if (carsInforModelList_filter != null) {
            carsInforModelList_filter.clear();
        }
        UtilsLog.e("getDataNewCarsList---url==" + SharedPreferencesUtils.getIP(this) + NEW_CARS_LIST);
        OkGo.<String>get(SharedPreferencesUtils.getIP(this) + NEW_CARS_LIST).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
//                        UtilsLog.e("getDataNewCarsList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataNewCarsList-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {

                            carsInforModelList = new ArrayList<NewCarsModel>();
                            carsInforModelList = JsonUtil.stringToArray(newResult, NewCarsModel[].class);

                            if (carsInforModelList != null && carsInforModelList.size() > 0) {
                                for (int i = 0; i < carsInforModelList.size(); i++) {
                                    if (carsInforModelList.get(i).getDetectionCategory().equals("00")) {
                                        carsInforModelList_filter.add(carsInforModelList.get(i));
                                    }
                                }
                                acListNewCars.setVisibility(View.VISIBLE);
                                if (carsInforModelList_filter != null && carsInforModelList_filter.size() >
                                        0) {
                                    carsInforListAdapter = new NewCarsListAdapter(instances,
                                            carsInforModelList_filter);
                                    acListNewCars.setAdapter(carsInforListAdapter);
                                }
//                                else {
//                                    carsInforListAdapter = new NewCarsListAdapter(instances,
//                                            carsInforModelList_filter);
//                                    acListNewCars.setAdapter(carsInforListAdapter);
//                                }
                            } else {
                                acListNewCars.setVisibility(View.GONE);
//                                carsInforListAdapter.notifyDataSetChanged();
                                UtilsLog.e("getDataCarsListByMode-result=22=" + "2222没有数据");
                            }
                        } else {
                            acListNewCars.setVisibility(View.GONE);
//                            carsInforListAdapter.notifyDataSetChanged();
                            UtilsLog.e("getDataCarsListByMode-result=22=" + "没有数据");
                        }


                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataNewCarsList-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //不自动刷新的查询
    //carNO 车牌号码
    private void getDataSearchCarsByNO(DownLineModel downLineModel) {
        Gson gson = new Gson();
        String json_str = gson.toJson(downLineModel);
        UtilsLog.e("getDataSearchCarsByNO---json_str==" + json_str);
        UtilsLog.e("getDataSearchCarsByNO---url==" + SharedPreferencesUtils.getIP(this) + CARS_DOWN_LINE);
        String logStr = "\n" + "待检车辆列表--查询----请求" + "\n" +
                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE
                + "\n" + "json" + json_str;
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + CARS_DOWN_LINE).tag(this)
                .upJson(json_str)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataSearchCarsByNO-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            searChCarsList_one = new ArrayList<NewCarsModel>();
                            searChCarsList_one = JsonUtil.stringToArray(newResult, NewCarsModel[]
                                    .class);
                            if (searChCarsList_one != null && searChCarsList_one.size() > 0) {
                                for (int i = 0; i < searChCarsList_one.size(); i++) {
                                    if (searChCarsList_one.get(i).getDetectionCategory().equals("00")) {
                                        searChCarsFilterList.add(searChCarsList_one.get(i));
                                    }
                                }

                                if (searChCarsFilterList != null && searChCarsFilterList.size() > 0) {
                                    carsInforListAdapter = new NewCarsListAdapter(instances,
                                            searChCarsFilterList);
                                    acListNewCars.setAdapter(carsInforListAdapter);
                                    acListNewCars.setVisibility(View.VISIBLE);
                                } else {
                                    acListNewCars.setVisibility(View.GONE);
                                    Toast.makeText(instances, "没有查询到相关车辆", Toast
                                            .LENGTH_LONG).show();
                                }
                            } else {
                                acListNewCars.setVisibility(View.GONE);
                                Toast.makeText(instances, "没有查询到相关车辆", Toast
                                        .LENGTH_LONG).show();
                            }
                        } else {
                            acListNewCars.setVisibility(View.GONE);
                            UtilsLog.e("getDataSearchCarsByNO-result==" + "没有数据");
                            Toast.makeText(instances, "没有查询到相关车辆", Toast
                                    .LENGTH_LONG).show();
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataSearchCarsByNO-onError==" + response.body());
                        String logStr = "\n" + "待检车辆列表--查询----请求失败--error" + "\n" +
                                "result::" + response.body() + "\n" +
                                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.cb_check);
        checkbox.setChecked(!checkbox.isChecked());
    }
}
