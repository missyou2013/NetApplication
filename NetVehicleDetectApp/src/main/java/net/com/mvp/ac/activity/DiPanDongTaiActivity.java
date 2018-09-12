package net.com.mvp.ac.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.DiPanDongTaiAdapter;
import net.com.mvp.ac.adapter.MyProvincesListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarItemEndModel;
import net.com.mvp.ac.model.CarItemStartModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.CheckItemModel;
import net.com.mvp.ac.model.Item_2Model;
import net.com.mvp.ac.model.ItemsModel;
import net.com.mvp.ac.model.MyChoiceModel;
import net.com.mvp.ac.model.PictureModel;
import net.com.mvp.ac.model.ReportedModel;
import net.com.mvp.ac.model.UpdateCarStatusModel;
import net.com.mvp.ac.model.VideoModel;
import net.com.mvp.ac.model.WaiJainDataModel;
import net.com.mvp.ac.widget.ListViewForScrollView;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countuptime.CountupView;

import static net.com.mvp.ac.application.BaseApplication.IsFirst;
import static net.com.mvp.ac.application.BaseApplication.myChoiceModelList;
import static net.com.mvp.ac.application.BaseApplication.mycar_checkItemlList;


/**
 *
 */

public class DiPanDongTaiActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.ac_dipan_dongtai_jiance_time)
    TextView acDipanDongtaiJianceTime;
    @BindView(R.id.ac_dipan_dongtai_jiance_chepaihaoma)
    TextView acDipanDongtaiJianceChepaihaoma;
    @BindView(R.id.ac_dipan_dongtai_jiance_haopailiexing)
    TextView acDipanDongtaiJianceHaopailiexing;
    @BindView(R.id.ac_dipan_dongtai_chedaohao)
    TextView acDipanDongtaiChedaohao;
    @BindView(R.id.ac_dipan_dongtai_vin)
    TextView acDipanDongtaiVin;
    @BindView(R.id.ac_dipan_dongtai_jianyanliushui)
    TextView acDipanDongtaiJianyanliushui;
    @BindView(R.id.ac_dipan_dongtai_jianyancishu)
    TextView acDipanDongtaiJianyancishu;
    @BindView(R.id.ac_dipan_dongtai_beizhu)
    EditText acDipanDongtaiBeizhu;
    @BindView(R.id.ac_dipan_dongtai_shangci_daima)
    EditText acDipanDongtaiShangciDaima;
    @BindView(R.id.ac_dipan_dongtai_start)
    Button acDipanDongtaiStart;
    @BindView(R.id.ac_set_view_mm34)
    TextView acSetViewMm34;
    @BindView(R.id.ac_dipan_dongtai_wanchen)
    Button acDipanDongtaiWanchen;
    @BindView(R.id.ac_dipan_dongtai_start_listview)
    ListViewForScrollView acDipanDongtaiStartListview;
    @BindView(R.id.ac_dipan_dongtai_jiance_scrollview)
    ScrollView scrollView;
    @BindView(R.id.ac_dipandongtai_btn_buhege_submit)
    Button acDipandongtaiBtnBuhegeSubmit;

    @BindView(R.id.ac_dipan_dongtai_tv_paizhao_0_statement)
    TextView acDipanDongtaiTvPaizhao0Statement;
    @BindView(R.id.ac_dipan_dongtai_tv_paizhao_1_statement)
    TextView acDipanDongtaiTvPaizhao1Statement;

    @BindView(R.id.ac_dipan_dongtai_tv_jishiqi)
    TextView acDipanDongtaiTvJiShiQi;

    private DiPanDongTaiAdapter diPanDongTaiAdapter;
    private CarsInforModel carsInforModel;
    private String carNo = null;//车牌号码
    private String PlateType = null;//号牌种类类型
    private String cartype = null;//车辆类型
    private DiPanDongTaiActivity instances = null;
    private int CHECK_MODE = 2;//检测模式 0:外检  1：路试 2：底盘动态
    private String Line = "1";//外检车道
    private String Item_Code = "DC";//F1外检,R1路试，DC底盘动态
    private WaiJainDataModel waiJainDataModel = new WaiJainDataModel();//上传数据model
    private VideoModel model = new VideoModel();
    boolean START = true;
    CountupView mCvCountupViewTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_pan_dong_tai);
        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle(R.string.ac_dipan_dongtai_title);
        pop_xuhao();
        instances = this;
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
//        carsInforModel = getIntent().getExtras().getParcelable("model_CarsInforModel");
        initView();

        mCvCountupViewTest1 = (CountupView) findViewById(R.id.ac_dipan_dongtai_jishiqi_cv_CountupViewTest3);


//        scrollView.smoothScrollTo(0, 0);
        acDipanDongtaiStartListview.setOnItemClickListener(this);
        diPanDongTaiAdapter = new DiPanDongTaiAdapter(this);
        acDipanDongtaiStartListview.setAdapter(diPanDongTaiAdapter);
//        Utils.setListViewHeightBasedOnChildren(acDipanDongtaiStartListview);
        acDipanDongtaiStartListview.setItemsCanFocus(false);
        acDipanDongtaiStartListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        acDipanDongtaiStartListview.setVisibility(View.GONE);

        if (myChoiceModelList != null && myChoiceModelList.size() > 0) {
            myChoiceModelList.clear();
        }
        List<Item_2Model> Item_2Model_list = new ArrayList<>();//Code2去重后的所有数据
        Item_2Model item_2 = null;
        for (int i = 0; i < BaseApplication.checkItemModelList.size(); i++) {
            item_2 = new Item_2Model();
            item_2.setID(BaseApplication.checkItemModelList.get(i).getID());
            item_2.setCode1(BaseApplication.checkItemModelList.get(i).getCode1());
            item_2.setCode2(BaseApplication.checkItemModelList.get(i).getCode2());
            item_2.setName2(BaseApplication.checkItemModelList.get(i).getName2());
            Item_2Model_list.add(item_2);
        }
        for (int k = 0; k < Item_2Model_list.size() - 1; k++) {
            for (int j = Item_2Model_list.size() - 1; j > k; j--) {
                if (Item_2Model_list.get(j).getCode2().equals(Item_2Model_list.get(k).getCode2())) {
                    Item_2Model_list.remove(j);
                }
            }
        }
        if (IsFirst) {
            for (int i = 0; i < Item_2Model_list.size(); i++) {
//                UtilsLog.e("dipandongtai-Item_2Model_list=11=" + Item_2Model_list.get(i).toString());
                MyChoiceModel model = new MyChoiceModel();
                model.setCode2(Item_2Model_list.get(i).getCode2());
                model.setChecked(true);
                model.setName2(Item_2Model_list.get(i).getName2());
                model.setCode_key("Code2-" + Item_2Model_list.get(i).getCode2());
                model.setCode_values("0");
                myChoiceModelList.add(model);
            }
//            for (int i = 0; i < myChoiceModelList.size(); i++) {
//                UtilsLog.e("dipandongtai-myChoiceModelList=11=" + myChoiceModelList.get(i).toString());
//            }
            getMyCarItems();
        }

    }

    int recLen = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            if (recLen < 120) {
                acDipanDongtaiTvJiShiQi.setText("项目进行时间：" + recLen + "  秒");
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void initView() {
        super.initView();
        if (carsInforModel != null) {
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
            //检测时间
            if (!TextUtils.isEmpty(carsInforModel.getDetectDate())) {
                String str = carsInforModel.getDetectDate().substring(6, carsInforModel.getDetectDate()
                        .length() - 2).trim();
//                String DetectDate = DateUtil.getDateTimeFromMillisecond(Long.parseLong(str));
                acDipanDongtaiJianceTime.setText(carsInforModel.getDetectDate());
            }
            //车牌号码
            if (!TextUtils.isEmpty(carsInforModel.getPlateRegion()) && !TextUtils.isEmpty(carsInforModel
                    .getPlateNO())) {
                acDipanDongtaiJianceChepaihaoma.setText(carsInforModel.getPlateRegion() + carsInforModel
                        .getPlateNO());
            }
            //号牌类型
            if (!TextUtils.isEmpty(carsInforModel.getPlateTypeName())) {
                acDipanDongtaiJianceHaopailiexing.setText(carsInforModel.getPlateTypeName() + "");
            }
            //Vin
            if (!TextUtils.isEmpty(carsInforModel.getVIN())) {
                acDipanDongtaiVin.setText(carsInforModel.getVIN() + "");
            }
            //检验流水
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getPlatformSN()))) {
                acDipanDongtaiJianyanliushui.setText(carsInforModel.getPlatformSN() + "");
            }
            //检验次数
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getTimes()))) {
                acDipanDongtaiJianyancishu.setText(carsInforModel.getTimes() + "");
            }
        }
    }

    @OnClick({R.id.ac_dipan_dongtai_chedaohao, R.id.ac_dipan_dongtai_start, R.id.ac_dipan_dongtai_wanchen,
            R.id.ac_dipandongtai_btn_buhege_submit, R.id.title_btn_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                //左上角返回键
//                updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
                String logStr = "\n" + "底盘动态--点击左上角返回键" + "\n";
                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                finish();
                break;
            case R.id.ac_dipandongtai_btn_buhege_submit:
                //不合格项目录入
//                Intent intent = new Intent(instances, DisqualificationActivity.class);
//                intent.putExtra("buhege_flag", 1);//不合格项目跳转 0：外检 1：底盘动态
//                intent.putExtra("WaiJianPhotoActivity_CarsInforModel", carsInforModel);
//                startActivity(intent);
                String logStr2 = "\n" + "底盘动态--不合格项目录入" + "\n";
                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr2.getBytes());
                Intent intent_2 = new Intent(instances, WaiJianResultActivity.class);
                intent_2.putExtra("WaiJianPhotoActivity_CarsInforModel", carsInforModel);
                startActivity(intent_2);
                break;
            case R.id.ac_dipan_dongtai_chedaohao:
                pop_xuhao.showAsDropDown(acDipanDongtaiChedaohao);
                break;
            case R.id.ac_dipan_dongtai_start:
//                mCvCountdownView.start(4 * 1000); // 毫秒
//                acDipanDongtaiStart.setClickable(false);
                //底盘动态检验开始
                PHOTO_PlatformCode = "0344";
//                takePicture();
                String logStr3 = "\n" + "底盘动态项目开始--点击项目开始按钮--未请求" + "\n";
                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr3.getBytes());
                getData(0);
                getDataDCPicture(PHOTO_PlatformCode);
                mCvCountupViewTest1.setTag("test1");
                long time1 = (long) 1000;
                mCvCountupViewTest1.start(time1);
                break;
            case R.id.ac_dipan_dongtai_wanchen:
                //底盘动态检验结束
                acDipanDongtaiWanchen.setEnabled(false);
                String logStr4 = "\n" + "底盘动态项目结束--点击项目结束按钮--未请求" + "\n";
                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr4.getBytes());
                PHOTO_PlatformCode = "0342";
                getDataDCPicture(PHOTO_PlatformCode);
                waiJainDataModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
                waiJainDataModel.setGUID(carsInforModel.getGUID());
                waiJainDataModel.setDetectionDevID(CommonUtils.getIMEI(instances));
                waiJainDataModel.setBrakeForce(carsInforModel.getBrakeForce());
                waiJainDataModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
                waiJainDataModel.setAirAdmission(carsInforModel.getAirAdmission());
                try {
                    String name = URLDecoder.decode(BaseApplication.accountModelList.get(0).getUserName(),
                            "UTF-8");
                    waiJainDataModel.setDynamicInspector(name);//检测员名字，汉字
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                waiJainDataModel.setDynamicNo(BaseApplication.accountModelList.get(0).getIDNumber());

                waiJainDataModel.setApprovedLoad(carsInforModel.getApprovedLoad());
                waiJainDataModel.setArea(carsInforModel.getArea());
                waiJainDataModel.setBodyColor(carsInforModel.getBodyColor());
                waiJainDataModel.setBrand(carsInforModel.getBrand());
                waiJainDataModel.setCarBodyType(carsInforModel.getCarBodyType());
                waiJainDataModel.setCarID(carsInforModel.getCarID());
                waiJainDataModel.setCarModel(carsInforModel.getCarModel());
                waiJainDataModel.setCarTypeLevel(carsInforModel.getCarTypeLevel());
                waiJainDataModel.setCategory(carsInforModel.getCategory());
                waiJainDataModel.setCentralAxle(carsInforModel.getCentralAxle());
                waiJainDataModel.setCode(carsInforModel.getCode());
                waiJainDataModel.setCombCarNO(carsInforModel.getCombCarNO());
                waiJainDataModel.setCurbWeight(carsInforModel.getCurbWeight());
                waiJainDataModel.setDetectDate(carsInforModel.getDetectDate());
                waiJainDataModel.setDetectionCategory(carsInforModel.getDetectionCategory());
                waiJainDataModel.setDetectionStatus(carsInforModel.getDetectionStatus());
                waiJainDataModel.setDisplacement(carsInforModel.getDisplacement());
                waiJainDataModel.setDriveAxle(carsInforModel.getDriveAxle());
                waiJainDataModel.setDriverAxleWeight(carsInforModel.getDriverAxleWeight());
                waiJainDataModel.setDriverTypeName(carsInforModel.getDriverTypeName());
                waiJainDataModel.setDriverType(carsInforModel.getDriverType());
                waiJainDataModel.setPlateType(carsInforModel.getPlateType());
                waiJainDataModel.setItemcode(Item_Code);
                waiJainDataModel.setLine(Line);
                waiJainDataModel.setMainAxis(carsInforModel.getMainAxis());
                waiJainDataModel.setParkingAxle(carsInforModel.getParkingAxle());
                waiJainDataModel.setEngineNO(carsInforModel.getEngineNO());
                waiJainDataModel.setFuelType(carsInforModel.getFuelType());
                waiJainDataModel.setPlateTypeName(carsInforModel.getPlateTypeName());
                waiJainDataModel.setPlatformSN(carsInforModel.getPlatformSN());
                waiJainDataModel.setQueueID(carsInforModel.getQueueID());
                waiJainDataModel.setSN(carsInforModel.getSN());
                waiJainDataModel.setStatus(carsInforModel.getStatus());
                waiJainDataModel.setTest_times(carsInforModel.getTimes());
                waiJainDataModel.setType(carsInforModel.getType());
                waiJainDataModel.setVIN(carsInforModel.getVIN());
                if (CommonUtils.isOnline(instances)) {

                    model.setItemcode(Item_Code);
                    model.setLine(Line);
                    model.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
                    model.setPlateType(carsInforModel.getPlateType());
                    model.setPlatformSN(carsInforModel.getPlatformSN());
                    model.setTest_times(carsInforModel.getTimes());
                    model.setDetectionItemStartDate(BaseApplication.D_ItemStart_time);
                    model.setDetectionItemEndDate(BaseApplication.D_ItemEnd_time);

                    //上传平台的数据
                    waiJainDataModel.setDataType("DC");
                    uploadPlatformData_w(waiJainDataModel);

                    START = false;
                    //视频接口上传数据
                    uploadVideo(model);

                } else {
                    Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                            .LENGTH_LONG).show();
                }
//
//                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DiPanDongTaiAdapter.ViewHolder vHollder = (DiPanDongTaiAdapter.ViewHolder) view.getTag();
//在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。
        vHollder.cBox.toggle();
        DiPanDongTaiAdapter.isSelected.put(position, vHollder.cBox.isChecked());
    }

    //    View.OnClickListener bPop = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            for (int i = 0; i < acDipanDongtaiStartListview.getCount(); i++) {
//                if (DiPanDongTaiAdapter.isSelected.get(i)) {
//                    DiPanDongTaiAdapter.ViewHolder vHollder = (DiPanDongTaiAdapter.ViewHolder)
// acDipanDongtaiStartListview.getChildAt(i).getTag();
//                    UtilsLog.d("--onClick --" + vHollder.title.getText());
//                }
//            }
//        }
//    };
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
        listview_xuhao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                acDipanDongtaiChedaohao.setText(xuhaoList.get(position));
                Line = xuhaoList.get(position);
                pop_xuhao.dismiss();
            }
        });
        parent22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop_xuhao.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
//        Toast.makeText(instances, "检测完成", Toast.LENGTH_LONG).show();
        instances = null;
    }

    private void getData(int flag) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
//                getDataReportData();
                switch (flag) {
                    case 0:
                        getDataWaiJainStart();
                        break;
                    case 1:
                        getDataWaiJainEnd();
                        break;
                }
            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(instances, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    //动态拍照
    String jsonStr_getDataDCPicture;

    private void getDataDCPicture(String plateType) {

        PictureModel model = new PictureModel();
        model.setItemCode(Item_Code);
        model.setLine(Line);
        model.setPhotoDate(DateUtil.currentTime2());
        model.setPhotoType(plateType);
        model.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
        model.setPlatformSN(carsInforModel.getPlatformSN());
        model.setTest_times(carsInforModel.getTimes());
        model.setVIN(carsInforModel.getVIN());
        model.setPlateType(carsInforModel.getPlateType());
        model.setDetection_ID(String.valueOf(carsInforModel.getID()));
        Gson gson = new Gson();
        jsonStr_getDataDCPicture = gson.toJson(model);
        UtilsLog.e("getDataDCPicture---jsonStr==" + jsonStr_getDataDCPicture);

        UtilsLog.e("getDataDCPicture---url==" + SharedPreferencesUtils.getIP(instances) +
                DC_PICTURE);
        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + DC_PICTURE).tag(instances)
                .upJson(jsonStr_getDataDCPicture)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataDCPicture-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataDCPicture-newResult==" + newResult);

                        String logStr = "\n" + "底盘动态--拍照请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                DC_PICTURE +
                                "\n" + "JSON::" + jsonStr_getDataDCPicture + "\n" +
                                "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());

                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
//
                        } else {
                            UtilsLog.e("getDataDCPicture-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataDCPicture-onError==" + response.body());
                        String logStr = "\n" + "底盘动态--拍照请求失败" +
                                "\n" + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //底盘动态开始
    String upjson_url_start_item_dipan;

    private void getDataWaiJainStart() {
        CarItemStartModel carItemStartModel = new CarItemStartModel();
        carItemStartModel.setPlatformSN(carsInforModel.getPlatformSN());
        carItemStartModel.setPlateType(carsInforModel.getPlateType());
//        carItemStartModel.setPlateNO(carsInforModel.getPlateNO());
        carItemStartModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
        carItemStartModel.setVIN(carsInforModel.getVIN());
        carItemStartModel.setDetectionID(String.valueOf(carsInforModel.getID()));
        carItemStartModel.setLine(Line);
        carItemStartModel.setTest_times(carsInforModel.getTimes());
        carItemStartModel.setDetectionDevID(CommonUtils.getIMEI(this));
        carItemStartModel.setItemCode(Item_Code);
        BaseApplication.D_ItemStart_time = "";
        BaseApplication.D_ItemStart_time = DateUtil.currentTime2();
        carItemStartModel.setDetectionItemStartDate(BaseApplication.D_ItemStart_time);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);
        upjson_url_start_item_dipan = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        UtilsLog.e("getDataWaiJainStart---jsonStr==" + upjson_url_start_item_dipan);
        UtilsLog.e("getDataWaiJainStart---url==" + SharedPreferencesUtils.getIP(this) + ITEM_START);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + ITEM_START)
                .tag(this)
                .upJson(upjson_url_start_item_dipan)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataWaiJainStart-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String str = result.substring(1, result.length() - 1);
                        if ("ok".equals(str)) {
                            handler.postDelayed(runnable, 1000);
                            Toast.makeText(instances, "项目开始", Toast.LENGTH_SHORT)
                                    .show();

                            String logStr = "\n" + "底盘动态开始--请求成功" +
                                    "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                    ITEM_END + "\n" + "JSON::" + upjson_url_start_item_dipan +
                                    "\n" + "result::" + result;
                            PDALogUtils.createLogFile(BaseApplication
                                    .JIANCE_MODE, logStr.getBytes());

//                            updateCarStatusInfor(1);
                            getDataReportData();
                        } else {
                            String logStr = "\n" + "底盘动态开始--上线失败" +
                                    "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                    ITEM_END + "\n" + "JSON::" + upjson_url_start_item_dipan +
                                    "\n" + "result::" + result;
                            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                            Toast.makeText(instances, "上线失败，请重试", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        String logStr = "\n" + "底盘动态开始--失败" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
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
    String jsonStr_updateCarStatusInfor;

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
        jsonStr_updateCarStatusInfor = gson.toJson(model);
        UtilsLog.e("updateCarStatusInfor---jsonStr==" + jsonStr_updateCarStatusInfor);
        UtilsLog.e("updateCarStatusInfor---url==" + SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS)
                .tag(this)
                .upJson(jsonStr_updateCarStatusInfor)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().trim();
                        UtilsLog.e("updateCarStatusInfor-result==" + result);
                        UtilsLog.e("updateCarStatusInfor-result=22=" + result.substring(1, result.length() -
                                1));
                        String logStr = "\n" + "底盘动态--更新状态--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS +
                                "\n" + "JSON::" + jsonStr_updateCarStatusInfor +
                                "\n" + "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        String logStr = "\n" + "底盘动态--更新状态--失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS +
                                "\n" + "JSON::" + jsonStr_updateCarStatusInfor +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //外检检测结束
    String upjson_url_getDataWaiJainEnd;

    private void getDataWaiJainEnd() {
        CarItemEndModel carItemStartModel = new CarItemEndModel();
        carItemStartModel.setPlatformSN(carsInforModel.getPlatformSN());
        carItemStartModel.setPlateType(carsInforModel.getPlateType());
        carItemStartModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
        carItemStartModel.setVIN(carsInforModel.getVIN());
        carItemStartModel.setLine(Line);
        carItemStartModel.setTest_times(carsInforModel.getTimes());
        carItemStartModel.setDetectionDevID(CommonUtils.getIMEI(this));
        carItemStartModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
        carItemStartModel.setItemCode(Item_Code);
        BaseApplication.D_ItemEnd_time = "";
        BaseApplication.D_ItemEnd_time = DateUtil.currentTime2();
        carItemStartModel.setDetectionItemEndDate(BaseApplication.D_ItemEnd_time);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);
        upjson_url_getDataWaiJainEnd = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        UtilsLog.e("getDataWaiJainEnd---jsonStr==" + upjson_url_getDataWaiJainEnd);
        UtilsLog.e("getDataWaiJainEnd---url==" + SharedPreferencesUtils.getIP(this) + ITEM_END);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + ITEM_END).tag(this)
                .upJson(upjson_url_getDataWaiJainEnd)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataWaiJainEnd-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String str = result.substring(1, result.length() - 1);

                        String logStr = "\n" + "底盘动态结束--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END + "\n" + "JSON::" + upjson_url_getDataWaiJainEnd + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        Toast.makeText(instances, "项目完成", Toast.LENGTH_LONG).show();
                        //项目总结束
                        EndAllItem(String.valueOf(carsInforModel.getID()));
                        updateCarStatusInfor(1);

                        if ("ok".equals(str)) {
//                            Toast.makeText(instances, "项目结束", Toast.LENGTH_LONG)
//                                    .show();
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                }
//                            }, 300);//后执行Runnable中的run方法
                        } else {
//                            Toast.makeText(instances, "项目未结束，请重试", Toast.LENGTH_LONG)
//                                    .show();
                            showDialog("底盘动态平台下线失败，请尝试重新下线");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainEnd-onError==" + response.body());
                        String logStr = "\n" + "底盘动态结束--请求失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END + "\n" + "JSON::" + upjson_url_getDataWaiJainEnd + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }


    //上传平台数据
    String upjson_url_uploadPlatformData_w;

    private void uploadPlatformData_w(WaiJainDataModel waiJainDataModel) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(waiJainDataModel);
        upjson_url_uploadPlatformData_w = "[{" + jsonStr.substring(1, jsonStr.length() - 1) + "," +
                DateUtil.codeStr() +
                "}]";
        UtilsLog.e("dipandongtai-uploadPlatformData_w---jsonStr==" + upjson_url_uploadPlatformData_w);
        UtilsLog.e("dipandongtai-uploadPlatformData_w---url==" + SharedPreferencesUtils.getIP(this) +
                PLAT_FORM_DATA);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + PLAT_FORM_DATA).tag(this)
                .upJson(upjson_url_uploadPlatformData_w)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("dipandongtai-uploadPlatformData_w-result==" + result);

                        CommonUtils.hideLoadingDialog(instances);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("uploadPlatformData_w-newResult==" + newResult);
                        String logStr = "\n" + "底盘动态--上传平台数据-请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                PLAT_FORM_DATA + "\n" + "JSON::" + upjson_url_uploadPlatformData_w + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        if ("ok".equalsIgnoreCase(newResult)) {
                            getData(1);
//                            //项目结束
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    /**
//                                     *要执行的操作
//                                     */
//
//                                }
//                            }, 1000);//后执行Runnable中的run方法

                        } else {
                            showDialog("上传平台数据失败，请重试，如果还未成功请联系管理员");
//                            Toast.makeText(instances, "项目结束失败", Toast.LENGTH_LONG)
//                                    .show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("dipandongtai-uploadPlatformData_w-onError==" + response.body());
                        String logStr = "\n" + "底盘动态-onError-上传平台数据-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                PLAT_FORM_DATA + "\n" + "JSON::" + upjson_url_uploadPlatformData_w + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("上传平台数据失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    //组装要上报的json
    private String reportedJsonStr() {
        String myjson = "";
        ReportedModel model;
        model = new ReportedModel();
        model.setDataType("DC");
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

    String myjson_getDataReportData;

    private void getDataReportData() {
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + REPORTED_DATA);
        myjson_getDataReportData = reportedJsonStr();

        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + REPORTED_DATA).tag(instances)
                .upJson(myjson_getDataReportData)
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
                        String logStr = "\n" + "底盘动态开始--上传自家后台请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "JSON::" + myjson_getDataReportData + "\n" +
                                "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        if ("\"ok\"".equals(result)) {
                            acDipanDongtaiStart.setEnabled(false);
//                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
//                            instances.finish();
                        } else {
                            showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);

                        String logStr = "\n" + "底盘动态开始--上传自家后台--失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "JSON::" + myjson_getDataReportData + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();

                        showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    /**
     * 项目总结束接口,最后一个执行
     */
    private void EndAllItem(final String detectionID) {
        UtilsLog.e("EndAllItem---url==" + SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
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
                        UtilsLog.e("EndAllItem-result==" + result);
                        String logStr = "\n" + "底盘动态--总结束-成功" +
                                "\n" + "url::" + SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
                                detectionID + "/DetectionID" + "\n" +
                                "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        mCvCountupViewTest1.stop();
                        finish();
//                        if ("\"ok\"".equals(result)) {
//                            updateCarStatusInfor(1);
//                            Toast.makeText(instances, "项目完成", Toast.LENGTH_LONG).show();
//                            mCvCountupViewTest1.stop();
//                            finish();
//                        } else {
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtils.hideLoadingDialog(instances);
                        UtilsLog.e("EndAllItem-onError==" + response.body());
                        UtilsLog.e("EndAllItem-onError==" + response.getException());
                        String logStr = "\n" + "底盘动态--总结束-失败" + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                    }
                });
    }
//    //拼装json  --Code2
//    private String appendCode2AndValues() {
//        String Code2JsonStr = "";
//        String result = "";
//        if (myChoiceModelList != null && myChoiceModelList.size() > 0) {
//            for (int i = 0; i < myChoiceModelList.size(); i++) {
//                Code2JsonStr = Code2JsonStr + "\"" + myChoiceModelList.get(i).getCode_key() + "\""
//                        + ":" + "\"" + myChoiceModelList.get(i).getCode_values() + "\"" + ",";
//            }
////            UtilsLog.e("Code2JsonStr==" + Code2JsonStr);
////            UtilsLog.e("dipandongtai-Code2JsonStr=22=" + Code2JsonStr.substring(0, Code2JsonStr.length()
//// - 1));
//        }
//        if (Code2JsonStr.length() > 0) {
//            result = Code2JsonStr.substring(0, Code2JsonStr.length() - 1);
//        }
//
//        return result;
//    }

    //获取需要检测的人工检验项目接口
    private void getMyCarItems() {
        ItemsModel model = new ItemsModel();
        model.setCarType(carsInforModel.getType());
        model.setDetectCategroy(carsInforModel.getDetectionCategory());
        model.setPassengerNb(carsInforModel.getApprovedLoad());
        model.setPlateType(carsInforModel.getPlateType());

        String registerTime = carsInforModel.getRegisteDate();
        String str = registerTime.substring(6, registerTime.length() - 2).trim();
        String registerdate = DateUtil.getDateTimeFromMillisecond(Long.parseLong(str));
        model.setRegisteDate(registerdate);
//        model.setRegisteDate(carsInforModel.getRegisteDate());
        Gson gson = new Gson();
        String json_str = gson.toJson(model);
        UtilsLog.e("getMyCarItems---json_str==" + json_str);
        UtilsLog.e("getMyCarItems---url==" + SharedPreferencesUtils.getIP(this) + GET_MY_ITEM);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + GET_MY_ITEM).tag(this)
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
                        UtilsLog.e("getMyCarItems-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getMyCarItems-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {
                            mycar_checkItemlList = new ArrayList<CheckItemModel>();
                            mycar_checkItemlList = JsonUtil.stringToArray(newResult,
                                    CheckItemModel[].class);
                            List<Item_2Model> Item_2Model_list = new ArrayList<>();//Code2去重后的所有数据
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
                                    if (Item_2Model_list.get(j).getCode2().equals(Item_2Model_list.get(k)
                                            .getCode2())) {
                                        Item_2Model_list.remove(j);
                                    }
                                }
                            }
                            for (int i = 0; i < myChoiceModelList.size(); i++) {
                                String Code2 = myChoiceModelList.get(i).getCode2().toString().trim();
                                for (int j = 0; j < Item_2Model_list.size(); j++) {
                                    String code2_my = Item_2Model_list.get(j).getCode2().toString().trim();
                                    if (Code2.equals(code2_my)) {
                                        myChoiceModelList.get(i).setCode_values("1");
                                    }
                                }
                            }
                        } else {
                            UtilsLog.e("getGET_ALL_ITEMList-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getMyCarItems-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    //视频接口
    private void uploadVideo(VideoModel model) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model);
        UtilsLog.e("uploadVideo---jsonStr==" + jsonStr);
        UtilsLog.e("uploadVideo---url==" + SharedPreferencesUtils.getIP(this) + VIDEO_ITEM);
        String logStr = "\n" + "底盘动态--uploadVideo--请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                VIDEO_ITEM + "\n" + "JSON::" + jsonStr + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + VIDEO_ITEM).tag(this)
                .upJson(jsonStr)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("uploadVideo-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String logStrf = "\n" + "底盘动态--uploadVideo--请求成功" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("uploadPlatformData_w-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "底盘动态--uploadVideo-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                VIDEO_ITEM + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }


    String tag = "tag";
    String PHOTO_PlatformCode = "";//拍照对应平台code


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            String logStr = "\n" + "底盘动态--返回键-屏幕底部" +
                    "\n";
            PDALogUtils.createLogFile(BaseApplication
                    .JIANCE_MODE, logStr.getBytes());

//            updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
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
