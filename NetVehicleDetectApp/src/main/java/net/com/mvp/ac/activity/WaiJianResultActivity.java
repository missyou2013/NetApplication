package net.com.mvp.ac.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;

import com.lzy.okgo.OkGo;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.WaiJianResultAdapter;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.City;
import net.com.mvp.ac.model.Group;
import net.com.mvp.ac.model.Item_1Model;
import net.com.mvp.ac.model.Item_2Model;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static net.com.mvp.ac.application.BaseApplication.myChoiceModelList;
import static net.com.mvp.ac.application.BaseApplication.mycar_checkItemlList;

//外检结果
public class WaiJianResultActivity extends BaseActivity {

    private ExpandableListView expandableListView;
    private List<Group> groupList = new ArrayList<Group>();
    private WaiJianResultAdapter adapter;
    private List<Object> gridList = new ArrayList<Object>();

    private WaiJianResultActivity instances = null;

    private String carNo = null;//车牌号码
    private String PlateType = null;//号牌种类类型
    private String cartype = null;//车辆类型

    CarsInforModel carsInforModel;
//    private List<CheckItemModel> mycar_checkItemlList = new ArrayList<>();//获取当前车辆需要的人工检验项目
    List<Item_2Model> Item_2Model_list = new ArrayList<>();//Code2去重后的所有数据
//    List<MyChoiceModel> myChoiceModelList = new ArrayList<>();//Code2选择的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wai_jian_result);
        ButterKnife.bind(this);

        setBackBtn();
        setTopTitle("检测项目");
        instances = this;
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("WaiJianPhotoActivity_CarsInforModel");


        //获取需要检测的人工检验项目接口
//        getData();
        //车牌号码
        if (!TextUtils.isEmpty(carsInforModel.getPlateRegion()) && !TextUtils.isEmpty(carsInforModel
                .getPlateNO())) {
            carNo = carsInforModel.getPlateRegion() + carsInforModel.getPlateNO();
        }
        //号牌类型
        if (!TextUtils.isEmpty(carsInforModel.getPlateTypeName())) {
            PlateType = String.valueOf(carsInforModel.getPlateTypeName());
        }
        //车辆类型
        if (!TextUtils.isEmpty(carsInforModel.getType())) {
            cartype = String.valueOf(carsInforModel.getType());
        }
        setUpViews();
        setUpListeners();

        init();
    }

    private void setUpViews() {
        expandableListView = (ExpandableListView) findViewById(R.id.ex);
    }

    private void setUpListeners() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                int i = groupList.get(groupPosition).getCityList().size();
                City city = groupList.get(groupPosition).getCityList().get(childPosition);
//                if (i<groupPosition) {
                city.changeChecked();
                adapter.notifyDataSetChanged();
                reFlashGridView();
//                } else {
//                    Toast.makeText(WaiJianResultActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
//                }
                UtilsLog.e("groupList==" + groupList.get(groupPosition).toString());
//                for (int i = 0; i < groupList.get(groupPosition).getCityList().size(); i++) {
//                    UtilsLog.e("groupList=groupList=(" + i + ")===" + groupList.get(i).toString());
//                    String Code2 = groupList.get(groupPosition).getCityList().get(i).getCode2();
//                    boolean isCheck = groupList.get(groupPosition).getCityList().get(i).isChecked();
//                    for (int j = 0; j < myChoiceModelList.size(); j++) {
//                        if (Code2.equals(myChoiceModelList.get(j).getCode2())) {
//                            myChoiceModelList.get(j).setChecked(isCheck);
//                            if (isCheck) {
//                                myChoiceModelList.get(j).setCode_values("1");
//                            } else {
//                                myChoiceModelList.get(j).setCode_values("2");
//                            }
//                        }
//                    }
//                }

                String Code2 =city.getCode2();
                boolean isCheck =city.isChecked();

                for (int i = 0; i < myChoiceModelList.size(); i++) {
                    if (Code2.equals(myChoiceModelList.get(i).getCode2())) {
                        myChoiceModelList.get(i).setChecked(isCheck);
                        if (isCheck) {
                            myChoiceModelList.get(i).setCode_values("1");
                        } else {
                            myChoiceModelList.get(i).setCode_values("2");
                        }
                    }
                    UtilsLog.e("myChoiceModelList=groupList=(" + i + ")===" + myChoiceModelList.get(i).toString());
                }
                return false;
            }
        });
    }

    private void init() {
        initData2();
        adapter = new WaiJianResultAdapter(this, groupList);
        expandableListView.setAdapter(adapter);
        reFlashGridView();
/*
全部展开
        int groupCount = expandableListView.getCount();
        for (int i=0; i<groupCount; i++) {
            expandableListView.expandGroup(i);
        };*/
    }

    public void reFlashGridView() {
        gridList.clear();
        for (Group group : groupList) {
            if (group.isChecked()) {
                gridList.add(group);
            } else {
                for (City city : group.getCityList()) {
                    if (city.isChecked()) {
                        gridList.add(city);
                    }
                }
            }
        }
    }

    void initData2() {
        List<Item_1Model> Item_1Model_list = new ArrayList<>();
        Item_1Model item_1 = null;
        for (int i = 0; i < mycar_checkItemlList.size(); i++) {
            item_1 = new Item_1Model();
            item_1.setID(mycar_checkItemlList.get(i).getID());
            item_1.setCode1(mycar_checkItemlList.get(i).getCode1());
            item_1.setName1(mycar_checkItemlList.get(i).getName1());
            Item_1Model_list.add(item_1);
        }
        for (int k = 0; k < Item_1Model_list.size() - 1; k++) {
            for (int j = Item_1Model_list.size() - 1; j > k; j--) {
                if (Item_1Model_list.get(j).getCode1().equals(Item_1Model_list.get(k).getCode1())) {
                    Item_1Model_list.remove(j);
                }
            }
        }

        Item_2Model item_2 = null;
        for (int i = 0; i < mycar_checkItemlList.size(); i++) {
            item_2 = new Item_2Model();
            item_2.setID(mycar_checkItemlList.get(i).getID());
            item_2.setCode1(mycar_checkItemlList.get(i).getCode1());
            item_2.setCode2(mycar_checkItemlList.get(i).getCode2());
            item_2.setName2(mycar_checkItemlList.get(i).getName2());
            Item_2Model_list.add(item_2);
        }
        for (int k = 0; k < Item_2Model_list.size() - 1; k++) {
            for (int j = Item_2Model_list.size() - 1; j > k; j--) {
                if (Item_2Model_list.get(j).getCode2().equals(Item_2Model_list.get(k).getCode2())) {
                    Item_2Model_list.remove(j);
                }
            }
        }
        for (int i = 0; i <myChoiceModelList.size() ; i++) {
            String Code2=myChoiceModelList.get(i).getCode2();
            for (int j = 0; j < Item_2Model_list.size(); j++) {
                if (Code2.equals(Item_2Model_list.get(j).getCode2())) {
                    myChoiceModelList.get(i).setCode_values("1");
                }
            }
        }
        for (int i = 0; i < Item_1Model_list.size(); i++) {
            Group group = new Group();
            group.setID(Item_1Model_list.get(i).getID());
            group.setCode1(Item_1Model_list.get(i).getCode1());
            group.setName(Item_1Model_list.get(i).getName1());
            group.setChecked(false);

            List<City> cityList = new ArrayList<City>();

            if (i < Item_2Model_list.size()) {
                for (int j = 0; j < Item_2Model_list.size(); j++) {

                    if (group.getCode1().equals(Item_2Model_list.get(j).getCode1())) {
                        City city = new City();
                        city.setID(Item_2Model_list.get(j).getID());
                        city.setCode1(Item_2Model_list.get(j).getCode1());
                        city.setCode2(Item_2Model_list.get(j).getCode2());
                        city.setName2(Item_2Model_list.get(j).getName2());
                        city.setName(Item_2Model_list.get(j).getName2());
                        city.setChecked(true);
                        cityList.add(city);
                        city.addObserver(group);
                        group.addObserver(city);
                    }
                }
            }
            if (cityList != null && cityList.size() > 0) {
                group.setCityList(cityList);
            }
            groupList.add(group);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

//    private void getData() {
//        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
//            if (CommonUtils.isOnline(instances)) {
//                getMyCarItems();
//            } else {
//                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
//                        .LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(instances, "请设置IP与端口号", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    //获取需要检测的人工检验项目接口
//    private void getMyCarItems() {
//        ItemsModel model = new ItemsModel();
//        model.setCarType(carsInforModel.getType());
//        model.setDetectCategroy(carsInforModel.getDetectionCategory());
//        model.setPassengerNb(carsInforModel.getApprovedLoad());
//        model.setPlateType(carsInforModel.getPlateType());
//        model.setProductionDate(carsInforModel.getProductionDate());
//        Gson gson = new Gson();
//        String json_str = gson.toJson(model);
//        UtilsLog.e("getMyCarItems---json_str==" + json_str);
//        UtilsLog.e("getMyCarItems---url==" + SharedPreferencesUtils.getIP(this) + GET_MY_ITEM);
//        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + GET_MY_ITEM).tag(this)
//                .upJson(json_str)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onStart(Request<String, ? extends Request> request) {
//                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
//                    }
//
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String result = response.body();
//                        UtilsLog.e("getMyCarItems-result==" + result);
//                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
//                        UtilsLog.e("getMyCarItems-newResult==" + newResult);
//                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
//                                (newResult) && !"[]".equals(result)) {
//                            mycar_checkItemlList = JsonUtil.stringToArray(newResult,
//                                    CheckItemModel[].class);
//                            init();
//                            for (int i = 0; i < mycar_checkItemlList.size(); i++) {
//                                UtilsLog.e("mycar_checkItemlList==(" + i + ")==" + mycar_checkItemlList.get
//                                        (i).toString());
//                            }
//
//                        } else {
////                            Toast.makeText(instances, getResources().getString(R.string.network_error),
//// Toast.LENGTH_LONG).show();
//                            UtilsLog.e("getGET_ALL_ITEMList-result==" + "没有数据");
//                        }
//                        CommonUtils.hideLoadingDialog(instances);
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                        UtilsLog.e("getMyCarItems-onError==" + response.body());
//                        CommonUtils.hideLoadingDialog(instances);
//                    }
//                });
//    }
}
