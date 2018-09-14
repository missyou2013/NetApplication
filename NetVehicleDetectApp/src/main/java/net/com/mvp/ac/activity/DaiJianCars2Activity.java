package net.com.mvp.ac.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import net.com.mvp.ac.adapter.CarsInforListAdapter;
import net.com.mvp.ac.adapter.MyAccountModeListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarCheckItemsModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.DownLineModel;
import net.com.mvp.ac.wuxi.WaiKuoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 待检车辆列表
 */
public class DaiJianCars2Activity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.ac_daijian_et_search2)
    EditText acDaijianEtSearch2;
    @BindView(R.id.ac_daijian_btn_search2)
    Button acDaijianBtnSearch2;
    @BindView(R.id.ac_daijian_bt_type2)
    Button acDaijianBtType2;
    @BindView(R.id.list_shequgonggao_baoxiu)
    ListView listShequgonggaoBaoxiu;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private List<CarsInforModel> carsInforModelList = new ArrayList<CarsInforModel>();
    private List<CarsInforModel> carsInforModelList_2 = new ArrayList<CarsInforModel>();
    private List<CarsInforModel> searChCarsList_one = new ArrayList<CarsInforModel>();//查询的列表
    private List<CarsInforModel> searChCarsList_two = new ArrayList<CarsInforModel>();//查询的列表
    private List<CarsInforModel> searChCarsFilterList = new ArrayList<CarsInforModel>();//查询后过滤的列表

    //adapter的过滤后的数据列表
    private List<CarsInforModel> adapterCarsList = new ArrayList<CarsInforModel>();
    private CarsInforListAdapter carsInforListAdapter;
    public DaiJianCars2Activity instances = null;
    private String carNO = null;
    private List<CarCheckItemsModel> carCheckItemModelList = new ArrayList<CarCheckItemsModel>();
    //待检测车辆要检测的项目的列表
    private String CURRENT_CHECK_MODE = "";//当前检测模式
    private String Login_name = "",login_IDNumber;//当前登录的检测员的名字
    private DownLineModel downLineModel;
    private Handler handler   = new Handler();
    MyRunnable myRunnable = new MyRunnable();//定义MyRunnable的对象；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_jian_cars_2);
        ButterKnife.bind(this);
        setTopTitle(R.string.activity_daijian_car_list);
        setHideLeftBtn();
        instances = this;

//        setRightTxt("重新下线", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(DaiJianCars2Activity.this, DownLineCarsListActivity.class);
//                startActivity(i);
//            }
//        });

        Login_name=getIntent().getExtras().getString("login_UserID","");
        login_IDNumber=getIntent().getExtras().getString("login_IDNumber","");

        CHECK_MODE = BaseApplication.JIANCE_MODE;
        UtilsLog.e(" BaseApplication.JIANCE_MODE-DaiJianCars2Activity==" + BaseApplication.JIANCE_MODE);
        listShequgonggaoBaoxiu.setOnItemClickListener(this);
        pop_type();

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

    @OnClick({R.id.ac_daijian_btn_search2, R.id.ac_daijian_bt_type2, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_daijian_btn_search2:
                //查询
                carNO = acDaijianEtSearch2.getText().toString().trim();
                if (!TextUtils.isEmpty(carNO)) {
                    CommonUtils.showLoadingDialog(instances, "加载中...");
                    downLineModel = new DownLineModel();
                    downLineModel.setType("0");
                    downLineModel.setPlateNO(carNO);
                    searChCarsFilterList = new ArrayList<CarsInforModel>();//查询后过滤的列表
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            downLineModel.setStatus("0");
                            getDataSearchCarsByNO(downLineModel);
                        } else if (i == 1) {
                            downLineModel.setStatus("1");
                            getDataSearchCarsByNO(downLineModel);
                        }
                    }
                } else {
                    Toast.makeText(instances, "车牌号码不能为空", Toast
                            .LENGTH_LONG).show();
                }

//                if (adapterCarsList != null && adapterCarsList.size() > 0) {
//                    searChCarsList = new ArrayList<CarsInforModel>();
//                    for (int i = 0; i < adapterCarsList.size(); i++) {
//                        String cno = (adapterCarsList.get(i).getPlateRegion() + adapterCarsList.get
//                                (i).getPlateNO()).toLowerCase();
//                        if (cno.contains(carNO.toLowerCase())) {
//                            searChCarsList.add(adapterCarsList.get(i));
//                        }
//                    }
//                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
//                            searChCarsList, CURRENT_CHECK_MODE);
//                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
//                }

                break;
            case R.id.ac_daijian_bt_type2:
//                pop2.showAsDropDown(acDaijianBtType2);
                //跳转复检拍照界面
                Intent intent = new Intent(this, RecheckPhotoListActivity.class);
                startActivity(intent);
                break;
            case R.id.fab:
                if (SharedPreferencesUtils.getAutoRefresh(this)) {
                    getData(0);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(DaiJianCars2Activity.this, CarDetailsActivity.class);
//        startActivity(intent);
        CarsInforModel carsInforModel = (CarsInforModel) parent.getAdapter().getItem(position);
        UtilsLog.e("carsInforModel=33=" + carsInforModel.toString());
        Intent intent2 = null;
        //检测模式 0:外检  1：路试 2：底盘动态
        if (CHECK_MODE == 0) {
            //外检
            intent2 = new Intent(DaiJianCars2Activity.this, CarDetailsActivity.class);
//            intent2.putExtra("check_mode",BaseApplication.JIANCE_MODE);
        } else if (CHECK_MODE == 1) {
            //路试
            intent2 = new Intent(DaiJianCars2Activity.this, RoadTestActivity.class);
//            intent2.putExtra("check_mode",BaseApplication.JIANCE_MODE);
        } else if (CHECK_MODE == 2){
            //底盘动态
            intent2 = new Intent(DaiJianCars2Activity.this, DiPanDongTaiActivity.class);
//            intent2.putExtra("check_mode",BaseApplication.JIANCE_MODE);
        }
        else if (CHECK_MODE == 3){
            //外廓尺寸
            intent2 = new Intent(DaiJianCars2Activity.this, WaiKuoActivity.class);
        }
        else if (CHECK_MODE == 4){
            //逆反系数
            intent2 = new Intent(DaiJianCars2Activity.this, DiPanDongTaiActivity.class);
//            intent2.putExtra("check_mode",BaseApplication.JIANCE_MODE);
        }
        intent2.putExtra("model_CarsInforModel", carsInforModel);
        intent2.putExtra("check_mode", CHECK_MODE);
        intent2.putExtra("Login_name", Login_name);
        intent2.putExtra("login_IDNumber", login_IDNumber);

        startActivity(intent2);
    }


    private View view2;
    private LinearLayout parent22;
    private PopupWindow pop2 = null;
    private ListView listview_modes;
    private List<String> typeList = new ArrayList<String>();
    MyAccountModeListAdapter myTypeAdapter;

    private void pop_type() {
        for (int i = 0; i < Constants.JIANCE_TYPE.length; i++) {
            typeList.add(i, Constants.JIANCE_TYPE[i]);
        }
        pop2 = new PopupWindow(this);
        view2 = getLayoutInflater().inflate(R.layout.item_pop_type,
                null);
        pop2.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop2.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop2.setBackgroundDrawable(new BitmapDrawable());
        pop2.setFocusable(true);
        pop2.setOutsideTouchable(true);
        pop2.setContentView(view2);
        parent22 = (LinearLayout) view2.findViewById(R.id.ac_daijian_car2_parent);
        listview_modes = (ListView) view2.findViewById(R.id.ac_daijian_car2_listview);
        if (typeList != null && typeList.size() > 0) {
            myTypeAdapter = new MyAccountModeListAdapter(DaiJianCars2Activity.this, typeList);
            listview_modes.setAdapter(myTypeAdapter);
            listview_modes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
//                Toast.makeText(LoginActivity.this, "====" + strModes.get(position),
//                        Toast.LENGTH_SHORT).show();
                    acDaijianBtType2.setText(typeList.get(position));
                    pop2.dismiss();
                }
            });
        }


        parent22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop2.dismiss();
            }
        });
    }

    private void getData(int type) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(this))) {
            if (CommonUtils.isOnline(this)) {
                if (type == 0) {
                    getDataCarsListByMode();
                } else if (type == 1) {

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
    protected void onResume() {
        super.onResume();
        acDaijianEtSearch2.setText("");
        UtilsLog.e("是否自动刷新==" + SharedPreferencesUtils.getAutoRefresh(this));
        if (SharedPreferencesUtils.getAutoRefresh(this)) {

            new Thread() {

                @Override
                public void run() {
                    handler.postDelayed(myRunnable,600);
//                    handler.post(myRunnable);//调用Handler.post方法；
                }
            }.start();


//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    getData(0);
//                }
//            });
        } else {

        }
    }
    class MyRunnable implements Runnable {//内部类实现Runnable接口；

        @Override
        public void run() {//还是在Runnable重写的run()方法中更新界面；
//            text.setText("使用Handler更新了界面");
            getData(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

    //获取车辆列表
    private void getDataCarsList() {
        if (adapterCarsList != null) {
            adapterCarsList.clear();
        }
        UtilsLog.e("getDataCarsList---url==" + SharedPreferencesUtils.getIP(this) + CARS_LIST_DAI_JIAN);
        OkGo.<String>get(SharedPreferencesUtils.getIP(this) + CARS_LIST_DAI_JIAN).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
//                        UtilsLog.e("getDataCarsList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataCarsList-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            carsInforModelList = new ArrayList<CarsInforModel>();
                            carsInforModelList = JsonUtil.stringToArray(newResult, CarsInforModel[].class);
                            if (carsInforModelList != null && carsInforModelList.size() > 0) {
                                for (int i = 0; i < carsInforModelList.size(); i++) {
//                                    getDataCarItemsDetails(String.valueOf(carsInforModelList.get(i).getID()
//                                    ), i);
                                    //检测模式 0:外检  1：路试 2：底盘动态
                                    switch (CHECK_MODE) {
                                        case 0:
                                            if (carsInforModelList.get(i).getAppearanceStatus() == 0) {
                                                adapterCarsList.add(carsInforModelList.get(i));
                                            }
                                            break;
                                        case 1:
                                            if (carsInforModelList.get(i).getRoadStatus() == 0) {
                                                adapterCarsList.add(carsInforModelList.get(i));
                                            }
                                            break;
                                        case 2:
                                            if (carsInforModelList.get(i).getDynamicStatus() == 0) {
                                                adapterCarsList.add(carsInforModelList.get(i));
                                            }
                                            break;
                                    }
                                }

                                if (adapterCarsList != null && adapterCarsList.size() > 0) {
//                                    carsInforListAdapter.notifyDataSetChanged();
                                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                            adapterCarsList, CURRENT_CHECK_MODE);
                                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
//                                    for (int i = 0; i < adapterCarsList.size(); i++) {
//                                        UtilsLog.e("getDataCarsList-carsInforModelList==" +
// adapterCarsList.get(i).toString());
//                                    }
                                } else {
                                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                            adapterCarsList, CURRENT_CHECK_MODE);
                                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
//                                    carsInforListAdapter.notifyDataSetChanged();
                                }

                            } else {
                                UtilsLog.e("getDataCarsList-result==" + "2222没有数据");
                            }


                        } else {
                            UtilsLog.e("getDataCarsList-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataUsersList-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }


    //获取车辆列表根据选择的检测类型
    private void getDataCarsListByMode() {
        //检测模式 0:外检  F1 1：路试 R1 2：底盘动态 DC
        String car_url = "";
        switch (BaseApplication.JIANCE_MODE) {
            case 0:
                car_url = CAR_LIST_WAIJIAN;
                break;
            case 1:
                car_url = CAR_LIST_LUSHI;
                break;
            case 2:
                car_url = CAR_LIST_DIPAN;
                break;
            case 3:
                car_url = CAR_LIST_WAIJIAN;
                break;
            case 4:
                car_url = CAR_LIST_WAIJIAN;
                break;
        }
        if (adapterCarsList != null) {
            adapterCarsList.clear();
        }
        UtilsLog.e("getDataCarsListByMode---url==" + SharedPreferencesUtils.getIP(this) + car_url);
        OkGo.<String>get(SharedPreferencesUtils.getIP(this) + car_url).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
//                        UtilsLog.e("getDataCarsList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataCarsListByMode-newResult==" + newResult);
                        UtilsLog.showLongLogs("==11=="+newResult);
                        UtilsLog.showLogCompletion("==22=="+newResult,200);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            carsInforModelList = new ArrayList<CarsInforModel>();
                            carsInforModelList = JsonUtil.stringToArray(newResult, CarsInforModel[].class);
                            if (carsInforModelList != null && carsInforModelList.size() > 0) {

                                for (int i = 0; i < carsInforModelList.size(); i++) {
                                    adapterCarsList.add(carsInforModelList.get(i));
                                }
//                                if (adapterCarsList != null && adapterCarsList.size() > 0) {
//                                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity
// .this,
//                                            adapterCarsList, CURRENT_CHECK_MODE);
//                                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
//                                }else{
//                                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity
// .this,
//                                            adapterCarsList, CURRENT_CHECK_MODE);
//                                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
////                                    carsInforListAdapter.notifyDataSetChanged();
//                                }

                            } else {
//                                carsInforListAdapter.notifyDataSetChanged();
                                UtilsLog.e("getDataCarsListByMode-result==" + "2222没有数据");
                            }
                        } else {
//                            carsInforListAdapter.notifyDataSetChanged();
                            UtilsLog.e("getDataCarsListByMode-result==" + "没有数据");
                        }
                        getDataCarsListByMode_2();
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataCarsListByMode-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //获取车辆列表根据选择的检测类型2
    private void getDataCarsListByMode_2() {
        //检测模式 0:外检  F1 1：路试 R1 2：底盘动态 DC
        String car_url = "";
        switch (BaseApplication.JIANCE_MODE) {
            case 0:
                car_url = CAR_LIST_WAIJIAN_2;
                break;
            case 1:
                car_url = CAR_LIST_LUSHI_2;
                break;
            case 2:
                car_url = CAR_LIST_DIPAN_2;
                break;
            case 3:
                car_url = CAR_LIST_WAIJIAN;
                break;
            case 4:
                car_url = CAR_LIST_WAIJIAN;
        }
//        if(adapterCarsList!=null){
//            adapterCarsList.clear();
//        }
        UtilsLog.e("getDataCarsListByMode-22--url==" + SharedPreferencesUtils.getIP(this) + car_url);
        OkGo.<String>get(SharedPreferencesUtils.getIP(this) + car_url).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
//                        UtilsLog.e("getDataCarsList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataCarsListByMode-newResult=22=" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            carsInforModelList_2 = new ArrayList<CarsInforModel>();
                            carsInforModelList_2 = JsonUtil.stringToArray(newResult, CarsInforModel[].class);
                            if (carsInforModelList_2 != null && carsInforModelList_2.size() > 0) {
                                for (int i = 0; i < carsInforModelList_2.size(); i++) {
                                    adapterCarsList.add(carsInforModelList_2.get(i));
                                }
                            } else {
                                listShequgonggaoBaoxiu.setVisibility(View.GONE);
//                                carsInforListAdapter.notifyDataSetChanged();
                                UtilsLog.e("getDataCarsListByMode-result=22=" + "2222没有数据");
                            }
                        } else {
                            listShequgonggaoBaoxiu.setVisibility(View.GONE);
//                            carsInforListAdapter.notifyDataSetChanged();
                            UtilsLog.e("getDataCarsListByMode-result=22=" + "没有数据");
                        }
                        listShequgonggaoBaoxiu.setVisibility(View.VISIBLE);
                        if (adapterCarsList != null && adapterCarsList.size() > 0) {
                            carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                    adapterCarsList, CURRENT_CHECK_MODE);
                            listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
                        } else {
                            carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                    adapterCarsList, CURRENT_CHECK_MODE);
                            listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
//                                    carsInforListAdapter.notifyDataSetChanged();
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataCarsListByMode-onError=22=" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //查询
    //carNO 车牌号码
    private void getDataSearchCars(String carNO) {
        UtilsLog.e("getDataCarsList---url==" + SharedPreferencesUtils.getIP(this) + CARS_LIST_FRONT +
                CARS_LIST_STATUS + CARS_LIST_BEHIND);
        OkGo.<String>get(SharedPreferencesUtils.getIP(this) + CARS_LIST_FRONT + CARS_LIST_STATUS +
                CARS_LIST_BEHIND).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataCarsList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataCarsList-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            carsInforModelList = JsonUtil.stringToArray(newResult, CarsInforModel[].class);
                            carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                    carsInforModelList, CURRENT_CHECK_MODE);
                            listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
                        } else {
                            UtilsLog.e("getDataCarsList-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataUsersList-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //废弃--不用
    //获取对应车辆要检测的项目
    private void getDataCarItemsDetails(String DetectionID, final int i_position) {
        UtilsLog.e("getDataCarItemsDetails---url==" + SharedPreferencesUtils.getIP(instances) +
                CAR_ITEM_CHECK + DetectionID + "/DetectionID");
        OkGo.<String>get(SharedPreferencesUtils.getIP(instances) + CAR_ITEM_CHECK + DetectionID +
                "/DetectionID").tag(instances)

                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();

                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataCarItemsDetails-result==" + newResult);


                        UtilsLog.e("getDataCarItemsDetails-CURRENT_CHECK_MODE==" + CURRENT_CHECK_MODE);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            carCheckItemModelList = JsonUtil.stringToArray(newResult, CarCheckItemsModel[]
                                    .class);
                            if (carCheckItemModelList != null && carCheckItemModelList.size() > 0) {
                                for (int i = 0; i < carsInforModelList.size(); i++) {
                                    String itemCode = carsInforModelList.get(i).getItemCode();
                                    String itemStatus = carsInforModelList.get(i).getItemStatus();
                                    if (!TextUtils.isEmpty(itemCode) && !TextUtils.isEmpty(itemStatus)) {
                                        if (itemStatus.equals("0") && CURRENT_CHECK_MODE.equals(itemCode)) {
                                            UtilsLog.e("getDataCarItemsDetails-model=11=" +
                                                    carsInforModelList.get(i).toString());

                                        }
                                    }

                                }
                                for (int i = 0; i < carCheckItemModelList.size(); i++) {
                                    String itemCode = carCheckItemModelList.get(i).getItemCode();
                                    int itemStatus = carCheckItemModelList.get(i).getItemStatus();
                                    if (itemStatus == 0 && CURRENT_CHECK_MODE.equals(itemCode)) {
                                        CarsInforModel c = carsInforModelList.get(i_position);
                                        c.setItemCode(itemCode);
                                        c.setItemStatus(String.valueOf(itemStatus));
                                        carsInforModelList.set(i_position, c);
//
//                                       carsInforModelList.remove(i_position);
//                                       carsInforModelList.add(c);
                                    }
                                }
                                for (int i = 0; i < carsInforModelList.size(); i++) {
                                    String itemCode = carsInforModelList.get(i).getItemCode();
                                    String itemStatus = carsInforModelList.get(i).getItemStatus();
                                    if (!TextUtils.isEmpty(itemCode) && !TextUtils.isEmpty(itemStatus)) {
                                        if (itemStatus.equals("0") && CURRENT_CHECK_MODE.equals(itemCode)) {
                                            UtilsLog.e("getDataCarItemsDetails-model=22=" +
                                                    carsInforModelList.get(i).toString());

                                        }
                                    }

                                }
                                carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                        carsInforModelList, CURRENT_CHECK_MODE);
                                listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
                            }
                        } else {
                            UtilsLog.e("getDataCarsList-result==" + "没有数据");
                        }

                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataCarItemsDetails-onError==" + response.body());
                        UtilsLog.e("getDataCarItemsDetails-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
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

//                        String logStr = "\n" + "待检车辆列表--查询----请求成功" + "\n" +
//                                "result::" + response.body() + "\n" +
//                                "URL::" + SharedPreferencesUtils.getIP(instances) + CARS_DOWN_LINE;
//                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            searChCarsList_one = new ArrayList<CarsInforModel>();
                            searChCarsList_one = JsonUtil.stringToArray(newResult, CarsInforModel[]
                                    .class);
                            if (searChCarsList_one != null && searChCarsList_one.size() > 0) {

                                for (int i = 0; i < searChCarsList_one.size(); i++) {
                                    switch (BaseApplication.JIANCE_MODE) {
                                        case 0:
                                            if (searChCarsList_one.get(i).getAppearanceStatus() == 0 ||
                                                    searChCarsList_one.get(i).getAppearanceStatus() ==
                                                            ITEM_MIDDLE_EXIT_CODE) {
                                                searChCarsFilterList.add(searChCarsList_one.get(i));
                                            }
                                            break;
                                        case 1:
                                            if (searChCarsList_one.get(i).getRoadStatus() == 0 ||
                                                    searChCarsList_one.get(i).getRoadStatus() ==
                                                            ITEM_MIDDLE_EXIT_CODE) {
                                                searChCarsFilterList.add(searChCarsList_one.get(i));
                                            }
                                            break;
                                        case 2:
                                            if (searChCarsList_one.get(i).getDynamicStatus() == 0 ||
                                                    searChCarsList_one.get(i).getDynamicStatus() ==
                                                            ITEM_MIDDLE_EXIT_CODE) {
                                                searChCarsFilterList.add(searChCarsList_one.get(i));
                                            }
                                            break;
                                    }
                                }
                                if (searChCarsFilterList != null && searChCarsFilterList.size() > 0) {
                                    carsInforListAdapter = new CarsInforListAdapter(DaiJianCars2Activity.this,
                                            searChCarsFilterList, CURRENT_CHECK_MODE);
                                    listShequgonggaoBaoxiu.setAdapter(carsInforListAdapter);
                                    listShequgonggaoBaoxiu.setVisibility(View.VISIBLE);
                                }
                            } else {
                                listShequgonggaoBaoxiu.setVisibility(View.GONE);
                                Toast.makeText(instances, "没有查询到相关车辆", Toast
                                        .LENGTH_LONG).show();
                            }

                        } else {
                            listShequgonggaoBaoxiu.setVisibility(View.GONE);
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

//    private long exitTime = 0;
//
//    // 双击返回退出程序
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                BaseApplication.getSelf().exit(this);
//                this.finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}