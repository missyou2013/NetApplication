package net.com.mvp.ac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.DownLineCarsListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.DownLineModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//重新下线车辆列表
public class DownLineCarsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.ac_down_line_listview)
    ListView acDownLineListview;
    @BindView(R.id.ac_down_line_listview_fab)
    FloatingActionButton acDownLineListviewFab;
    DownLineCarsListActivity instances;
    @BindView(R.id.ac_down_line_et_search22)
    EditText acDownLineEtSearch22;
    @BindView(R.id.ac_down_line_btn_search22)
    Button acDownLineBtnSearch22;

    private String CURRENT_CHECK_MODE = "";//当前检测模式
    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态


    private List<CarsInforModel> adapterCarsList = new ArrayList<CarsInforModel>();
    //    private List<CarsInforModel> carsInforModelList = new ArrayList<CarsInforModel>();
    private DownLineModel downLineModel;
    private String carNO = null;
    private DownLineCarsListAdapter downLineCarsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_line_cars_list);
        ButterKnife.bind(this);
        instances = this;
        setBackBtn();
        setTopTitle("重新下线车辆列表");

        acDownLineListview.setOnItemClickListener(this);
        CHECK_MODE = BaseApplication.JIANCE_MODE;
        UtilsLog.e(" BaseApplication.JIANCE_MODE-DaiJianCars2Activity==" + BaseApplication.JIANCE_MODE);
        switch (BaseApplication.JIANCE_MODE) {
            //检测模式 0:外检  F1 1：路试 R1 2：底盘动态 DC
            case 0:
                CURRENT_CHECK_MODE = "F1";
                break;
            case 1:
                CURRENT_CHECK_MODE = "R1";
                break;
            case 2:
                CURRENT_CHECK_MODE = "DC";
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
//        getData(0);
        carNO = acDownLineEtSearch22.getText().toString().trim();
        if (!TextUtils.isEmpty(carNO)) {
            downLineModel = new DownLineModel();
            downLineModel.setStatus("1");
            downLineModel.setType("0");
            downLineModel.setPlateNO(carNO);
            getData(0, carNO);
        }
    }

    @OnClick(R.id.ac_down_line_btn_search22)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_down_line_btn_search22:
                carNO = acDownLineEtSearch22.getText().toString().trim();
                if (!TextUtils.isEmpty(carNO)) {
                    downLineModel = new DownLineModel();
                    downLineModel.setStatus("1");
                    downLineModel.setType("0");
                    downLineModel.setPlateNO(carNO);
                    getData(0, carNO);
                } else {
                    Toast.makeText(instances, "车牌号码不能为空", Toast
                            .LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(DaiJianCars2Activity.this, CarDetailsActivity.class);
//        startActivity(intent);
        CarsInforModel carsInforModel = (CarsInforModel) parent.getAdapter().getItem(position);
        UtilsLog.e("carsInforModel=11=" + carsInforModel.toString());
        Intent intent2 = null;
        //检测模式 0:外检  1：路试 2：底盘动态
        intent2 = new Intent(DownLineCarsListActivity.this, DownLineCarDetailsActivity.class);
        intent2.putExtra("model_CarsInforModel_downline", carsInforModel);
        intent2.putExtra("check_mode_downline", CHECK_MODE);
        startActivity(intent2);
    }

    private void getData(int type, String carNO) {
        if (CommonUtils.isOnline(this)) {
            if (type == 0) {
                getDataSearchCarsByNO(carNO);
            } else {
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    //重新下线查询
    //carNO 车牌号码
    private void getDataSearchCarsByNO(String carNO) {
        Gson gson = new Gson();
        String json_str = gson.toJson(downLineModel);
        UtilsLog.e("getDataSearchCarsByNO---json_str==" + json_str);
        UtilsLog.e("getDataSearchCarsByNO---url==" + SharedPreferencesUtils.getIP(this) + CARS_DOWN_LINE);

        String logStr = "\n" + "重新下线查询----请求" + "\n"  +
                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE
                + "\n"  +"json"+json_str;
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + CARS_DOWN_LINE).tag(this)
                .upJson(json_str)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataSearchCarsByNO-newResult==" + newResult);

                        String logStr = "\n" + "重新下线查询----请求成功" + "\n" +
                                "result::" + response.body() + "\n" +
                                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            adapterCarsList = new ArrayList<CarsInforModel>();

                            adapterCarsList = JsonUtil.stringToArray(newResult, CarsInforModel[]
                                    .class);
                            if (adapterCarsList != null && adapterCarsList.size() > 0) {
                                downLineCarsListAdapter = new DownLineCarsListAdapter
                                        (DownLineCarsListActivity.this,
                                                adapterCarsList);
                                acDownLineListview.setAdapter(downLineCarsListAdapter);
                                acDownLineListview.setVisibility(View.VISIBLE);
                            } else {
                                acDownLineListview.setVisibility(View.GONE);
                                Toast.makeText(instances, "没有查询到相关车辆", Toast
                                        .LENGTH_LONG).show();
                            }
                        } else {
                            acDownLineListview.setVisibility(View.GONE);
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
                        String logStr = "\n" + "重新下线查询----请求失败--error" + "\n" +
                                "result::" + response.body() + "\n" +
                                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }


}
