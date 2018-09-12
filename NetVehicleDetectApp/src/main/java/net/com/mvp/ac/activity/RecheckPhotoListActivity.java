package net.com.mvp.ac.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.RecheckCarsListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.RecheckCarsModel;
import net.com.mvp.ac.model.RecheckJsonModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecheckPhotoListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.ac_rechreck_list)
    ListView acRechreckList;
    public RecheckPhotoListActivity instances = null;
    @BindView(R.id.ac_daijian_et_search22)
    EditText acDaijianEtSearch22;
    @BindView(R.id.ac_recheck_start_time)
    TextView acRecheckStartTime;
    @BindView(R.id.ac_recheck_end_time)
    TextView acRecheckEndTime;

    private String carNO = null;
    private String StartTime = null;
    private String EndTime = null;
    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private String CURRENT_CHECK_MODE = "";//当前检测模式

    final Calendar ca = Calendar.getInstance();
    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;
    final int DATE_DIALOG2 = 2;

    RecheckJsonModel recheckJsonModel;//复检查询上传json用到

    List<RecheckCarsModel> recheckCarsModelList = new ArrayList<>();//复检查询到的车辆列表
    List<RecheckCarsModel> filtering_recheckCarsModelList = new ArrayList<>();//复检查询到的车辆列表-过滤后的
    RecheckCarsListAdapter recheckCarsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_photo);
        ButterKnife.bind(this);
        acRechreckList.setOnItemClickListener(this);
        setTopTitle(R.string.activity_daijian_car_list);
        instances = this;
        setBackBtn();
        CHECK_MODE = BaseApplication.JIANCE_MODE;
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
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        acRecheckStartTime.setText(DateUtil.getTodayDate2());
        acRecheckEndTime.setText(DateUtil.getTodayDate2());
        recheckJsonModel = new RecheckJsonModel();
        acRechreckList.setVisibility(View.GONE);
    }

    private void getData(int type, String str) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(this))) {
            if (CommonUtils.isOnline(this)) {
                if (type == 0) {
                    getDataSearchCarsByNO(str);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecheckCarsModel model = (RecheckCarsModel) parent.getAdapter().getItem(position);
        Intent intent2 = null;
        intent2 = new Intent(instances, ReCheckTakePhotoActivity.class);
        intent2.putExtra("re_model", model);
        startActivity(intent2);
    }

    @OnClick({R.id.ac_daijian_btn_search22, R.id.ac_recheck_start_time, R.id.ac_recheck_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_daijian_btn_search22:
                //查询
                carNO = acDaijianEtSearch22.getText().toString().trim();
                StartTime = acRecheckStartTime.getText().toString().trim();
                EndTime = acRecheckEndTime.getText().toString().trim();
                if (!TextUtils.isEmpty(carNO)) {
                    recheckJsonModel.setPlateNO(carNO);
                    recheckJsonModel.setEndDate(EndTime);
                    recheckJsonModel.setStartDate(StartTime);
                    recheckJsonModel.setType("0");
                    getData(0, carNO);

                } else {
                    Toast.makeText(instances, "车牌号码不能为空", Toast
                            .LENGTH_LONG).show();
                }
                break;
            case R.id.ac_recheck_start_time:
                //开始时间
                showDialog(DATE_DIALOG);
                break;
            case R.id.ac_recheck_end_time:
                //结束时间
                showDialog(DATE_DIALOG2);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
            case DATE_DIALOG2:
                return new DatePickerDialog(this, mdateListener2, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display(TextView tv) {
        tv.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay)
                .append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display(acRecheckStartTime);
        }
    };
    private DatePickerDialog.OnDateSetListener mdateListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display(acRecheckEndTime);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }


    //复检查询
    //carNO 车牌号码
    private void getDataSearchCarsByNO(String carNO) {
        Gson gson = new Gson();
        String json_str = gson.toJson(recheckJsonModel);
        UtilsLog.e("getDataSearchCarsByNO---json_str==" + json_str);
        UtilsLog.e("getDataSearchCarsByNO---url==" + SharedPreferencesUtils.getIP(this) + RECHECK_CARS_LIST);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + RECHECK_CARS_LIST).tag(this)
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
//                        UtilsLog.e("getDataSearchCarsByNO-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataSearchCarsByNO-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            recheckCarsModelList=new ArrayList<RecheckCarsModel>();
                            filtering_recheckCarsModelList=new ArrayList<RecheckCarsModel>();
                            recheckCarsModelList = JsonUtil.stringToArray(newResult, RecheckCarsModel[]
                                    .class);
                            if (recheckCarsModelList != null && recheckCarsModelList.size() > 0) {
                                for (int i = 0; i <recheckCarsModelList.size()  ; i++) {
//                                    if(recheckCarsModelList.get(i).getDetectionStatus()==2||recheckCarsModelList.get(i).getDetectionStatus()==3){
                                        filtering_recheckCarsModelList.add(recheckCarsModelList.get(i));
//                                    }
                                }
                                if (filtering_recheckCarsModelList != null && filtering_recheckCarsModelList.size() > 0) {
                                    recheckCarsListAdapter = new RecheckCarsListAdapter(RecheckPhotoListActivity.this,
                                            filtering_recheckCarsModelList);
                                    acRechreckList.setAdapter(recheckCarsListAdapter);
                                }else{
                                    Toast.makeText(instances, "没有查询到相关车辆", Toast
                                            .LENGTH_LONG).show();
                                }
                                acRechreckList.setVisibility(View.VISIBLE);
                            } else {
                                acRechreckList.setVisibility(View.GONE);
                                Toast.makeText(instances, "没有查询到相关车辆", Toast
                                        .LENGTH_LONG).show();
                            }

                        } else {
                            acRechreckList.setVisibility(View.GONE);
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
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

}
