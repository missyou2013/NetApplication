package net.com.mvp.ac.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.CheDaoWidthAdapter;
import net.com.mvp.ac.adapter.MyAccountModeListAdapter;
import net.com.mvp.ac.adapter.MyProvincesListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.Utils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarItemEndModel;
import net.com.mvp.ac.model.CarItemStartModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.PictureModel;
import net.com.mvp.ac.model.RoadReportedModel;
import net.com.mvp.ac.model.RoadTestModel;
import net.com.mvp.ac.model.UpdateCarStatusModel;
import net.com.mvp.ac.model.VideoModel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//路试
public class RoadTestActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.ac_lushi_chepai_haoma)
    TextView acLushiChepaiHaoma;
    @BindView(R.id.ac_lushi_haopaileixing)
    TextView acLushiHaopaileixing;
    @BindView(R.id.ac_lushi_chedao)
    TextView acLushiChedao;
    @BindView(R.id.ac_lushi_vin)
    TextView acLushiVin;
    @BindView(R.id.ac_lushi_jianyanliushui)
    TextView acLushiJianyanliushui;
    @BindView(R.id.ac_lushi_jianyancishu)
    TextView acLushiJianyancishu;
    @BindView(R.id.ac_lushi_chusudu)
    EditText acLushiChusudu;
    @BindView(R.id.ac_lushi_zongshijian)
    EditText acLushiCheDaoWidth;
    @BindView(R.id.ac_lushi_zhidong_juli)
    EditText acLushiZhidongJuli;
    @BindView(R.id.ac_lushi_zhidong_juli_hege)
    TextView acLushiZhidongJuliHege;
    @BindView(R.id.ac_lushi_xietiao_shijian)
    EditText acLushiXietiaoShijian;
    @BindView(R.id.ac_lushi_xietiao_shijian_hege)
    TextView acLushiXietiaoShijianHege;
    @BindView(R.id.ac_lushi_mfdd)
    EditText acLushiMfdd;
    @BindView(R.id.ac_lushi_mfdd_hege)
    TextView acLushiMfddHege;
    @BindView(R.id.ac_lushi_max_jiansudu)
    EditText acLushiMaxJiansudu;
    @BindView(R.id.ac_lushi_zhidong_wendingxing)
    TextView acLushiZhidongWendingxing;
    @BindView(R.id.ac_lushi_btn_xiangmu_kaishi)
    Button acLushiBtnXiangmuKaishi;
    @BindView(R.id.ac_lushi_zhidong_kaishi)
    Button acLushiZhidongKaishi;
    @BindView(R.id.ac_lushi_btn_wanchen_1)
    Button acLushiBtnWanchen1;
    @BindView(R.id.ac_lushi_podao_podu)
    TextView acLushiPodaoPodu;
    @BindView(R.id.ac_lushi_podao_podu_hege)
    TextView acLushiPodaoPoduHege;
    @BindView(R.id.ac_lushi_zhuche_kaishi_btn)
    Button acLushiZhucheKaishiBtn;
    @BindView(R.id.ac_lushi_view_mm)
    TextView acLushiViewMm;
    @BindView(R.id.ac_lushi_btn_wanchen_2)
    Button acLushiBtnWanchen2;
    @BindView(R.id.ac_lushi_chesubiao_et)
    EditText acLushiChesubiaoEt;
    @BindView(R.id.ac_lushi_chesubiao_hege)
    TextView acLushiChesubiaoHege;
    @BindView(R.id.ac_lushi_chesu_kaishi_btn)
    Button acLushiChesuKaishiBtn;
    @BindView(R.id.ac_lushi_btn_wanchen_2_middle)
    TextView acLushiBtnWanchen2Middle;
    @BindView(R.id.ac_lushi_btn_wanchen_3)
    Button acLushiBtnWanchen3;
    @BindView(R.id.ac_lushi_btn_last_cancle)
    Button acLushiBtnLastCancle;
    @BindView(R.id.ac_lushi_last_middle)
    TextView acLushiLastMiddle;
    @BindView(R.id.ac_lushi_btn_last_end)
    Button acLushiBtnLastEnd;
    @BindView(R.id.ac_lushi_jianceyuan)
    TextView acLushiJianceyuan;

    //拍照的提示textview
    @BindView(R.id.ac_lushi_tv_paizhao_0_statement)
    TextView acLushiTvPaizhao0Statement;
    @BindView(R.id.ac_lushi_tv_paizhao_1_statement)
    TextView acLushiTvPaizhao1Statement;
    @BindView(R.id.ac_lushi_tv_paizhao_2_statement)
    TextView acLushiTvPaizhao2Statement;
    @BindView(R.id.ac_lushi_tv_paizhao_3_statement)
    TextView acLushiTvPaizhao3Statement;
//    @BindView(R.id.ac_road_tv_xingche_lushi_zhidong_panding)
//    TextView acRoadTvXingcheLushiZhidongPanding;
//    @BindView(R.id.ac_road_tv_yingji_caozongli_fangshi)
//    TextView acRoadTvYingjiCaozongliFangshi;
//    @BindView(R.id.ac_road_tv_yingji_lushi_zhidong_panding)
//    TextView acRoadTvYingjiLushiZhidongPanding;
//    @BindView(R.id.test_test_ll)
//    LinearLayout testTestLl;
//    @BindView(R.id.ac_road_tv_lushi_jieguo)
//    TextView acRoadTvLushiJieguo;
//    @BindView(R.id.ac_road_manzai_zhidong_juli)
//    EditText acRoadManzaiZhidongJuli;
//    @BindView(R.id.ac_road_manzai_mfdd)
//    EditText acRoadManzaiMfdd;

    //照片是否上传的标志List
    private List<Boolean> uploadPhotoList = new ArrayList<>();

    private PopupWindow pop = null;
    private View popu_view;
    private ListView listview_IsQualified;
    private List<String> yes_no_Modes = new ArrayList<String>();
    private int CHECK_MODE = 1;//检测模式 0:外检  1：路试 2：底盘动态
    CarsInforModel carsInforModel;
    int QualifiedType = 0;//合格类型，用来判断是哪个点击事件
    private RoadTestActivity instances = null;

    private RoadReportedModel roadReportedModel = new RoadReportedModel();
    BaseApplication baseApplication;
    String Item_code = "";
    String text_name = "";//检测员名字
    String Line = "1";//车道号
    String wen_ding_xing = "";//稳定性
    String po_du = "";//坡度
    String po_du_hege = "1";//坡度是否合格
    String che_su = "";//车速实测值
    String che_su_hege = "1";//车速实测值是否合格
    RoadTestModel roadTestModel = new RoadTestModel();//路试组装json实体类

    private VideoModel model = new VideoModel();
//    String xingche_lushi_zhidong_panding = "";
//    String yingji_caozongli_fangshi = "";
//    String yingji_lushi_zhidong_panding = "";
//    String lushi_jieguo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_test);
        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle(R.string.ac_lushi_title);
        baseApplication = BaseApplication.getSelf();
        instances = this;
        if (getIntent() != null) {
            carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
//            carsInforModel = getIntent().getExtras().getParcelable("model_CarsInforModel");
            initView();
        }

    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 4; i++) {
            uploadPhotoList.add(i, false);
        }
//        popWindow_yingji_caozongli_fangshi();
//        popWindow_yingji_lushi_zhidong_panding();
//        popWindow_xingche_lushi_zhidong_panding();
//        popWindow_lushi_jieguo();

        popWindow_IsQualified();
        pop_xuhao();
        pop_renyuan();
        pop_wendingxing();
        pop_podu();
        pop_chedao_width();

//        if (yes_no_Modes_xingche_lushi_zhidong_panding != null &&
//                yes_no_Modes_xingche_lushi_zhidong_panding.size() > 0) {
//            acRoadTvXingcheLushiZhidongPanding.setText(yes_no_Modes_xingche_lushi_zhidong_panding.get(1));
//        }
//        xingche_lushi_zhidong_panding = "1";
//
//        if (yes_no_Modes_yingji_lushi_zhidong_panding != null && yes_no_Modes_yingji_lushi_zhidong_panding
//                .size() > 0) {
//            acRoadTvYingjiLushiZhidongPanding.setText(yes_no_Modes_yingji_lushi_zhidong_panding.get(1));
//        }
//        yingji_lushi_zhidong_panding = "1";
//
//        if (yes_no_Modes_lushi_jieguo != null && yes_no_Modes_lushi_jieguo
//                .size() > 0) {
//            acRoadTvLushiJieguo.setText(yes_no_Modes_lushi_jieguo.get(1));
//        }
//        lushi_jieguo = "1";
//
//        if (yes_no_Modes_caozongli != null && yes_no_Modes_caozongli.size() > 0) {
//            acRoadTvYingjiCaozongliFangshi.setText(yes_no_Modes_caozongli.get(0));
//        }
//        yingji_caozongli_fangshi = "0";


        if (wendingxingList != null && wendingxingList.size() > 0) {
            acLushiZhidongWendingxing.setText(wendingxingList.get(0));
        }
        wen_ding_xing = "1";
        if (poduList != null && poduList.size() > 0) {
            acLushiPodaoPodu.setText(poduList.get(1));
        }

        po_du = "1";
        if (carsInforModel != null) {
            UtilsLog.e("carsInforModel==" + carsInforModel.toString());
            roadReportedModel.setGUID(carsInforModel.getGUID());
            roadReportedModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
            roadReportedModel.setTest_times(carsInforModel.getTimes());
            roadReportedModel.setDataType("ROAD");
            roadReportedModel.setTotalMass(carsInforModel.getTotalMass());
            roadReportedModel.setApprovedload(carsInforModel.getApprovedLoad());
            roadReportedModel.setBrakeForce(carsInforModel.getBrakeForce());
            roadReportedModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());

            //车牌号码
            if (!TextUtils.isEmpty(carsInforModel.getPlateRegion()) && !TextUtils.isEmpty(carsInforModel
                    .getPlateNO())) {
                acLushiChepaiHaoma.setText(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
            }
            //号牌类型
            if (!TextUtils.isEmpty(carsInforModel.getPlateTypeName())) {
                acLushiHaopaileixing.setText(carsInforModel.getPlateTypeName() + "");
            }
            //Vin
            if (!TextUtils.isEmpty(carsInforModel.getVIN())) {
                acLushiVin.setText(carsInforModel.getVIN() + "");
            }
            //检验流水
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getPlatformSN()))) {
                acLushiJianyanliushui.setText(carsInforModel.getPlatformSN() + "");
            }
            //检验次数
            if (!TextUtils.isEmpty(String.valueOf(carsInforModel.getTimes()))) {
                acLushiJianyancishu.setText(carsInforModel.getTimes() + "");
            }
        }

    }

    @OnClick({R.id.ac_lushi_zhidong_juli_hege, R.id.ac_lushi_xietiao_shijian_hege,
            R.id.ac_lushi_mfdd_hege, R.id.ac_lushi_zhidong_wendingxing,
            R.id.ac_lushi_btn_xiangmu_kaishi, R.id.ac_lushi_zhidong_kaishi,
            R.id.ac_lushi_btn_wanchen_1, R.id.ac_lushi_podao_podu,
            R.id.ac_lushi_podao_podu_hege, R.id.ac_lushi_zhuche_kaishi_btn,
            R.id.ac_lushi_btn_wanchen_2, R.id.ac_lushi_chesubiao_hege,
            R.id.ac_lushi_chesu_kaishi_btn, R.id.ac_lushi_btn_wanchen_3,
            R.id.ac_lushi_btn_last_cancle, R.id.ac_lushi_btn_last_end,
            R.id.ac_lushi_chedao, R.id.ac_lushi_jianceyuan, R.id.ac_lushi_zongshijian
            , R.id.title_btn_left
// , R.id.ac_road_tv_xingche_lushi_zhidong_panding,
//            R.id.ac_road_tv_yingji_caozongli_fangshi, R.id
//            .ac_road_tv_yingji_lushi_zhidong_panding, R.id.ac_road_tv_lushi_jieguo
    })
    public void onViewClicked(View view) {
        Utils.hideKeyboard(this);
        switch (view.getId()) {
            case R.id.title_btn_left:
                //左上角返回键
//                updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
                String logStr = "\n" + "路试--点击左上角返回键" +
                        "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr.getBytes());
                finish();
                break;
            case R.id.ac_lushi_zongshijian:
                //车道宽度
//                pop_chedao_width.showAtLocation(acLushiZhidongWendingxing, Gravity.BOTTOM, 0, 0);
////                pop_renyuan.showAtLocation(acLushiJianceyuan, Gravity.BOTTOM, 0, 0);
//                listview_chedao_width.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        acLushiCheDaoWidth.setText(chedao_widthList.get(position));
////                        text_name = renyuanList.get(position);
//                        pop_chedao_width.dismiss();
//                    }
//                });
                break;
            case R.id.ac_lushi_jianceyuan:
                //路试检测员
                QualifiedType = 5;
//                pop.showAtLocation(acLushiZhidongJuliHege, Gravity.BOTTOM, 0, 0);
                pop_renyuan.showAsDropDown(acLushiJianceyuan);
//                pop_renyuan.showAtLocation(acLushiJianceyuan, Gravity.BOTTOM, 0, 0);
                listview_renyuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acLushiJianceyuan.setText(renyuanList.get(position));
                        text_name = renyuanList.get(position);
                        pop_renyuan.dismiss();
                    }
                });
                break;
//            case R.id.ac_lushi_zhidong_juli_hege:
//                QualifiedType = 0;
//                pop.showAtLocation(acLushiZhidongJuliHege, Gravity.BOTTOM, 0, 0);
//                break;
//            case R.id.ac_lushi_xietiao_shijian_hege:
//                QualifiedType = 1;
//                pop.showAtLocation(acLushiXietiaoShijianHege, Gravity.BOTTOM, 0, 0);
//                break;
//            case R.id.ac_lushi_mfdd_hege:
//                QualifiedType = 2;
//                pop.showAtLocation(acLushiMfddHege, Gravity.BOTTOM, 0, 0);
//                break;
            case R.id.ac_lushi_chedao:
                //车道号
                pop_xuhao.showAsDropDown(acLushiChedao);
                listview_xuhao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acLushiChedao.setText(xuhaoList.get(position));
                        Line = xuhaoList.get(position);
                        pop_xuhao.dismiss();
                    }
                });
                break;
            case R.id.ac_lushi_zhidong_wendingxing:
                //制动稳定性
//                pop_wendingxing.showAsDropDown(acLushiZhidongWendingxing);
                pop_wendingxing.showAtLocation(acLushiZhidongWendingxing, Gravity.BOTTOM, 0, 0);
                listview_wendingxing.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acLushiZhidongWendingxing.setText(wendingxingList.get(position));
                        wen_ding_xing = String.valueOf(position + 1);
                        pop_wendingxing.dismiss();
                    }
                });
                break;
            case R.id.ac_lushi_podao_podu:
                //坡道坡度
//                pop_podu.showAsDropDown(acLushiPodaoPodu);
                pop_podu.showAtLocation(acLushiPodaoPodu, Gravity.BOTTOM, 0, 0);
                listview_podu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acLushiPodaoPodu.setText(poduList.get(position));
                        po_du = String.valueOf(position);
                        pop_podu.dismiss();
                    }
                });
                break;
            case R.id.ac_lushi_podao_podu_hege:
                //坡道坡度是否合格
                QualifiedType = 3;
                pop.showAtLocation(acLushiPodaoPoduHege, Gravity.BOTTOM, 0, 0);
                listview_IsQualified.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
//                        acLushiPodaoPodu.setText(poduList.get(position));
                        po_du_hege = String.valueOf(position);
                        pop.dismiss();
                    }
                });
                break;
            case R.id.ac_lushi_btn_xiangmu_kaishi:
                //项目开始
                String logStr3 = "路试--项目开始--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr3.getBytes());

                String Road_Name = acLushiJianceyuan.getText().toString().trim();//检测员
                if (TextUtils.isEmpty(Road_Name)) {
                    Toast.makeText(instances, "请选择检测员的名字", Toast.LENGTH_LONG).show();
                    return;
                }
                getItemStart("R");
                acLushiBtnXiangmuKaishi.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_zhidong_kaishi:
                //制动开始
                String logStr4 = "路试--制动开始--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr4.getBytes());
                String Road_Name1 = acLushiJianceyuan.getText().toString().trim();//检测员
                if (TextUtils.isEmpty(Road_Name1)) {
                    Toast.makeText(instances, "请选择检测员的名字", Toast.LENGTH_LONG).show();
                    return;
                }
                Item_code = "R1";
                PHOTO_PlatformCode = "0341";
                getItemStart(Item_code);
                getDataDCPicture(PHOTO_PlatformCode, Item_code);
                acLushiZhidongKaishi.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_zhuche_kaishi_btn:
                //驻车开始
                String logStr5 = "路试--驻车开始--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr5.getBytes());
                String Road_Name2 = acLushiJianceyuan.getText().toString().trim();//检测员
                if (TextUtils.isEmpty(Road_Name2)) {
                    Toast.makeText(instances, "请选择检测员的名字", Toast.LENGTH_LONG).show();
                    return;
                }
                Item_code = "R2";
                PHOTO_PlatformCode = "0345";
                getItemStart(Item_code);
                getDataDCPicture(PHOTO_PlatformCode, Item_code);
                acLushiZhucheKaishiBtn.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_chesu_kaishi_btn:
                //车速开始
                String logStr6 = "路试--车速开始--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr6.getBytes());
                Item_code = "R3";
                PHOTO_PlatformCode = "0347";
                getItemStart(Item_code);
//                getDataDCPicture(PHOTO_PlatformCode, Item_code);
                acLushiChesuKaishiBtn.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_btn_wanchen_1:
                //制动完成
                String logStr7 = "路试--制动完成--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr7.getBytes());
                Item_code = "R1";
                PHOTO_PlatformCode = "0343";
                getDataDCPicture(PHOTO_PlatformCode, Item_code);
                setUploadData(1);
                getItemEnd(Item_code, false);
                acLushiBtnWanchen1.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_btn_wanchen_2:
                //驻车完成
                String logStr8 = "路试--驻车完成--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr8.getBytes());
                Item_code = "R2";
                getItemEnd(Item_code, false);
                setUploadData(2);
                acLushiBtnWanchen2.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_btn_wanchen_3:
                //车速完成
                String logStr9 = "路试--车速完成--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr9.getBytes());
                Item_code = "R3";
                getItemEnd(Item_code, false);
//                setUploadData(3);
                acLushiBtnWanchen3.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.ac_lushi_chesubiao_hege:
                QualifiedType = 4;
                pop.showAtLocation(acLushiChesubiaoHege, Gravity.BOTTOM, 0, 0);
                listview_IsQualified.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
//                        acLushiPodaoPodu.setText(poduList.get(position));
                        che_su_hege = String.valueOf(position);
                        pop.dismiss();
                    }
                });
                break;
            case R.id.ac_lushi_btn_last_cancle:
                finish();
                break;
            case R.id.ac_lushi_btn_last_end:
                //项目结束
                String logStr10 = "路试--项目结束--按钮点击" + "\n" +
                        "车牌号码：：" + carsInforModel.getPlateRegion() +
                        carsInforModel.getPlateNO() + "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr10.getBytes());
                setUploadData(4);
                getItemEnd("R", true);
                acLushiBtnLastEnd.setTextColor(getResources().getColor(R.color.red));
                break;
//            case R.id.ac_road_tv_xingche_lushi_zhidong_panding:
//                //行车路试制动判定
//
//                pop_lushi_zhidong_panding.showAtLocation(acRoadTvXingcheLushiZhidongPanding, Gravity
//                        .BOTTOM, 0, 0);
//                listview_IsQualified_lushi_zhidong_panding.setOnItemClickListener(new AdapterView
//                        .OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        acRoadTvXingcheLushiZhidongPanding.setText
//                                (yes_no_Modes_xingche_lushi_zhidong_panding.get(position));
//                        xingche_lushi_zhidong_panding = String.valueOf(position + 1);
//                        pop_lushi_zhidong_panding.dismiss();
//                    }
//                });
//                break;
//            case R.id.ac_road_tv_yingji_caozongli_fangshi:
//                //应急操纵力方式
//                pop_yingji_caozongli_fangshi.showAtLocation(acRoadTvYingjiCaozongliFangshi, Gravity.BOTTOM,
//                        0, 0);
//                listview_IsQualified_yingji_caozongli_fangshi.setOnItemClickListener(new AdapterView
//                        .OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        acRoadTvYingjiCaozongliFangshi.setText(yes_no_Modes_caozongli.get(position));
//                        yingji_caozongli_fangshi = String.valueOf(position);
//                        pop_yingji_caozongli_fangshi.dismiss();
//                    }
//                });
//                break;
//            case R.id.ac_road_tv_yingji_lushi_zhidong_panding:
//                //应急路试制动判定
//                pop_yingji_lushi_zhidong_panding.showAtLocation(acRoadTvYingjiLushiZhidongPanding, Gravity
//                        .BOTTOM, 0, 0);
//                listview_IsQualified_yingji_lushi_zhidong_panding.setOnItemClickListener(new AdapterView
//                        .OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        acRoadTvYingjiLushiZhidongPanding.setText(yes_no_Modes_yingji_lushi_zhidong_panding
//                                .get(position));
//                        yingji_lushi_zhidong_panding = String.valueOf(position + 1);
//                        pop_yingji_lushi_zhidong_panding.dismiss();
//                    }
//                });
//                break;
//            case R.id.ac_road_tv_lushi_jieguo:
//                //路试结果
//                pop_lushi_jieguo.showAtLocation(acRoadTvLushiJieguo, Gravity
//                        .BOTTOM, 0, 0);
//                listview_IsQualified_lushi_jieguo.setOnItemClickListener(new AdapterView
//                        .OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1,
//                                            int position, long arg3) {
//                        acRoadTvLushiJieguo.setText(yes_no_Modes_lushi_jieguo
//                                .get(position));
//                        lushi_jieguo = String.valueOf(position + 1);
//                        pop_lushi_jieguo.dismiss();
//                    }
//                });
//                break;

        }
    }


    /**
     * 设置上传的数据,调用请求接口
     *
     * @param flag 1: 制动
     *             2：驻车
     *             3：车速
     *             4:项目结束，上报数据
     */
    private void setUploadData(int flag) {
        String Braking_Distance = acLushiZhidongJuli.getText().toString().trim();//制动距离
        String Initial_Velocity = acLushiChusudu.getText().toString().trim();//初速度
        String Coordination_Time = acLushiXietiaoShijian.getText().toString().trim();//协调时间
        String Mfdd = acLushiMfdd.getText().toString().trim();//mfdd

//        String manzai_Mfdd = acRoadManzaiMfdd.getText().toString().trim();//mfdd
//        String manzai_zhidongjuli = acRoadManzaiZhidongJuli.getText().toString().trim();//制动距离

        String Parking_Gradient = acLushiPodaoPodu.getText().toString().trim();//坡道坡度
        String Parking_Gradient_Verdict = acLushiPodaoPoduHege.getText().toString().trim();//坡道坡度是否合格
        String Road_LaneWidth = acLushiCheDaoWidth.getText().toString().trim();//车道宽度
        String Road_Name = acLushiJianceyuan.getText().toString().trim();//检测员
        String Speed = acLushiChesubiaoEt.getText().toString().trim();//车速实测值
        String Speed_Verdict = acLushiChesubiaoHege.getText().toString().trim();//车速实测值是否合格
        String Stability = acLushiZhidongWendingxing.getText().toString().trim();//制动稳定性

        if (!TextUtils.isEmpty(Initial_Velocity)
                && !TextUtils.isEmpty(Coordination_Time) &&
                !TextUtils.isEmpty(Parking_Gradient) && !TextUtils.isEmpty(Parking_Gradient_Verdict)
                && !TextUtils.isEmpty(Road_LaneWidth) &&
                !TextUtils.isEmpty(Road_Name)) {

            //项目结束用的
            roadTestModel.setPlatformSN(carsInforModel.getPlatformSN());
            roadTestModel.setLine(Line);
//            roadTestModel.setItemcode("R1,R2,R3");
            roadTestModel.setItem_Code("R1,R2");
            roadTestModel.setTest_times(carsInforModel.getTimes());
            roadTestModel.setPlateType(carsInforModel.getPlateType());
            roadTestModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
            roadTestModel.setVIN(carsInforModel.getVIN());
            try {
                roadTestModel.setRoad_Name(URLDecoder.decode(Road_Name, "UTF-8"));//检测员名字，汉字
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            roadTestModel.setInitial_Velocity(Initial_Velocity);
            roadTestModel.setCoordination_Time(Coordination_Time);
            roadTestModel.setStability(wen_ding_xing);

            //默认不填的情况下制动距离为0
            if (!TextUtils.isEmpty(Braking_Distance)) {
                roadTestModel.setBraking_Distance(Braking_Distance);
            }else {
//                if (TextUtils.isEmpty(manzai_zhidongjuli)) {
//                    manzai_zhidongjuli= "0";
//                    roadTestModel.setBraking_Distance(manzai_zhidongjuli);
//                }else{
//                    roadTestModel.setBraking_Distance(manzai_zhidongjuli);
//                 }
            }
            if (!TextUtils.isEmpty(Mfdd)) {
                roadTestModel.setMFDD(Mfdd);
            }else {
//                if (TextUtils.isEmpty(manzai_Mfdd)) {
//                    manzai_Mfdd= "0";
//                    roadTestModel.setMFDD(manzai_Mfdd);
//                }else{
//                    roadTestModel.setMFDD(manzai_Mfdd);
//                }
            }
//            if (wen_ding_xing == "1") {
            roadTestModel.setBrake_Verdict("1");
//            } else {
//                roadTestModel.setBrake_Verdict("2");
//            }
            roadTestModel.setParking_Gradient(po_du);
            roadTestModel.setParking_Gradient_Verdict(po_du_hege);

            roadTestModel.setSpeed_Verdict(che_su_hege);
//            if (wen_ding_xing == "1" && che_su_hege == "1" && po_du_hege == "1") {
            roadTestModel.setRoad_Verdict("1");
//            } else {
//                roadTestModel.setRoad_Verdict("2");
//            }
            roadTestModel.setRoad_LaneWidth(Road_LaneWidth);
            roadTestModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
//                    TotalMass, ApprovedLoad, BrakeForce, Type,GUID
            roadTestModel.setTotalMass(carsInforModel.getTotalMass());
            roadTestModel.setApprovedLoad(carsInforModel.getApprovedLoad());
            roadTestModel.setBrakeForce(carsInforModel.getBrakeForce());
            roadTestModel.setType(carsInforModel.getType());
            roadTestModel.setGUID(carsInforModel.getGUID());
            roadTestModel.setDataType("ROAD");

            if (flag == 1 || flag == 2 || flag == 3) {
                roadTestModel.setSpeed("");
                uploadPlatformData(PLAT_FORM_DATA, roadTestModel);
            }
            if (flag == 4) {
                roadTestModel.setSpeed("0");
                uploadPlatformData(REPORTED_DATA, roadTestModel);
                //上报数据的
//                roadReportedModel.setBraking_Distance(Braking_Distance);//制动距离

                //默认不填的情况下制动距离为0
                if (!TextUtils.isEmpty(Braking_Distance)) {
//                Braking_Distance = "0";
                    roadReportedModel.setBraking_Distance(Braking_Distance);
                }else {
//                    if (TextUtils.isEmpty(manzai_zhidongjuli)) {
//                        manzai_zhidongjuli= "0";
//                        roadReportedModel.setBraking_Distance(manzai_zhidongjuli);
//                    }else{
//                        roadReportedModel.setBraking_Distance(manzai_zhidongjuli);
//                    }
                }

                if (!TextUtils.isEmpty(Mfdd)) {
                    roadReportedModel.setMFDD(Mfdd);
                }else {
//                    if (TextUtils.isEmpty(manzai_Mfdd)) {
//                        manzai_Mfdd= "0";
//                        roadReportedModel.setMFDD(manzai_Mfdd);
//                    }else{
//                        roadReportedModel.setMFDD(manzai_Mfdd);
//                    }
                }



                roadReportedModel.setCoordination_Time(Coordination_Time);//协调时间
                roadReportedModel.setInitial_Velocity(Initial_Velocity);//初速度


                if ("15%".equals(Parking_Gradient)) {
                    roadReportedModel.setParking_Gradient("1");//坡度
                } else if ("20%".equals(Parking_Gradient)) {
                    roadReportedModel.setParking_Gradient("0");//坡度
                }
                if (Constants.HEGE_BUHEGE[0].equals(Parking_Gradient_Verdict)) {//坡度状态合格不合格未检测
                    roadReportedModel.setParking_Gradient_Verdict("0");//未检测
//                        Item_code="R1";
                } else if (Constants.HEGE_BUHEGE[1].equals(Parking_Gradient_Verdict)) {
                    roadReportedModel.setParking_Gradient_Verdict("1");//合格
                    Item_code = Item_code + ",R2";
                } else if (Constants.HEGE_BUHEGE[2].equals(Parking_Gradient_Verdict)) {
                    Item_code = Item_code + ",R2";
                    roadReportedModel.setParking_Gradient_Verdict("2");//不合格
                }
                if (Constants.STABILITY_TYPE[0].equals(Stability)) {//制动稳定性
                    roadReportedModel.setStability("0");//未跑偏
                } else if (Constants.STABILITY_TYPE[1].equals(Stability)) {
                    roadReportedModel.setStability("1");//左跑偏
                } else if (Constants.STABILITY_TYPE[2].equals(Stability)) {
                    roadReportedModel.setStability("2");//右跑偏
                }
                roadReportedModel.setRoad_LaneWidth(Road_LaneWidth);//车道宽度
                try {
//                        URLEncoder.encode(xing,"UTF-8");
                    roadReportedModel.setRoad_Name(URLEncoder.encode(Road_Name, "UTF-8"));//检测员名字，汉字
//                        roadReportedModel.setRoad_Name(URLDecoder.decode(Road_Name, "UTF-8"));//检测员名字，汉字
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                roadReportedModel.setSpeed(Speed);//速度
                if (Constants.HEGE_BUHEGE[0].equals(Speed_Verdict)) {//速度状态合格不合格未检测
//                        Item_code="R1";
                    roadReportedModel.setSpeed_Verdict("0");//未检测
                } else if (Constants.HEGE_BUHEGE[1].equals(Speed_Verdict)) {
                    Item_code = Item_code + ",R3";
                    roadReportedModel.setSpeed_Verdict("1");//合格
                } else if (Constants.HEGE_BUHEGE[2].equals(Speed_Verdict)) {
                    Item_code = Item_code + ",R3";
                    roadReportedModel.setSpeed_Verdict("2");//不合格
                }
                roadReportedModel.setItemcode(Item_code);
                Gson gson = new Gson();
                String myjson = gson.toJson(roadReportedModel);
                UtilsLog.e("roadReportedModel---myjson==" + roadReportedModel.toString());
                getData(myjson);
//                    getDataPlateData(myjson);

                model.setItemcode("R1,R2,R3");
                model.setLine(Line);
                model.setPlateNO(carsInforModel.getPlateNO());
                model.setPlateType(carsInforModel.getPlateType());
                model.setPlatformSN(carsInforModel.getPlatformSN());
                model.setTest_times(carsInforModel.getTimes());
                model.setDetectionItemStartDate(BaseApplication.R_ItemStart_time);
                model.setDetectionItemEndDate(BaseApplication.R_ItemEnd_time);
                //视频接口上传数据
                uploadVideo(model);

//                    uploadPlatformData(PLAT_FORM_DATA, roadReportedModel);

//                    getItemEnd();
                Toast.makeText(instances, "路试项目完成", Toast
                        .LENGTH_LONG).show();
            }
//                    instances.finish();
        } else {
            Toast.makeText(instances, "请输入全部正确有效的数据", Toast.LENGTH_LONG).show();
        }
    }

    //是否合格popwindow
    private void popWindow_IsQualified() {
        for (int i = 0; i < Constants.HEGE_BUHEGE.length; i++) {
            yes_no_Modes.add(i, Constants.HEGE_BUHEGE[i]);
        }
        pop = new PopupWindow(this);
        popu_view = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(popu_view);
        listview_IsQualified = (ListView) popu_view.findViewById(R.id.setting_listview);
        listview_IsQualified.setOnItemClickListener(this);
        MyAccountModeListAdapter MyAccountModeListAdapter = new MyAccountModeListAdapter(this, yes_no_Modes);
        listview_IsQualified.setAdapter(MyAccountModeListAdapter);
    }

   /* private PopupWindow pop_lushi_zhidong_panding = null;
    private View popu_view_lushi_zhidong_panding;
    private ListView listview_IsQualified_lushi_zhidong_panding;
    private List<String> yes_no_Modes_xingche_lushi_zhidong_panding = new ArrayList<String>();

    //是否合格popwindow
    private void popWindow_xingche_lushi_zhidong_panding() {
        for (int i = 0; i < Constants.HEGE_BUHEGE.length; i++) {
            yes_no_Modes_xingche_lushi_zhidong_panding.add(i, Constants.HEGE_BUHEGE[i]);
        }
        pop_lushi_zhidong_panding = new PopupWindow(this);
        popu_view_lushi_zhidong_panding = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_lushi_zhidong_panding.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_lushi_zhidong_panding.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_lushi_zhidong_panding.setBackgroundDrawable(new BitmapDrawable());
        pop_lushi_zhidong_panding.setFocusable(true);
        pop_lushi_zhidong_panding.setOutsideTouchable(true);
        pop_lushi_zhidong_panding.setContentView(popu_view_lushi_zhidong_panding);
        listview_IsQualified_lushi_zhidong_panding = (ListView) popu_view_lushi_zhidong_panding
                .findViewById(R.id.setting_listview);
        listview_IsQualified_lushi_zhidong_panding.setOnItemClickListener(this);
        MyAccountModeListAdapter MyAccountModeListAdapter = new MyAccountModeListAdapter(this,
                yes_no_Modes_xingche_lushi_zhidong_panding);
        listview_IsQualified_lushi_zhidong_panding.setAdapter(MyAccountModeListAdapter);
    }

    //是否合格popwindow
    private PopupWindow pop_yingji_lushi_zhidong_panding = null;
    private View popu_view_yingji_lushi_zhidong_panding;
    private ListView listview_IsQualified_yingji_lushi_zhidong_panding;
    private List<String> yes_no_Modes_yingji_lushi_zhidong_panding = new ArrayList<String>();

    private void popWindow_yingji_lushi_zhidong_panding() {
        for (int i = 0; i < Constants.HEGE_BUHEGE.length; i++) {
            yes_no_Modes_yingji_lushi_zhidong_panding.add(i, Constants.HEGE_BUHEGE[i]);
        }
        pop_yingji_lushi_zhidong_panding = new PopupWindow(this);
        popu_view_yingji_lushi_zhidong_panding = getLayoutInflater().inflate(R.layout
                        .item_setting_popupwindows,
                null);
        pop_yingji_lushi_zhidong_panding.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_yingji_lushi_zhidong_panding.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_yingji_lushi_zhidong_panding.setBackgroundDrawable(new BitmapDrawable());
        pop_yingji_lushi_zhidong_panding.setFocusable(true);
        pop_yingji_lushi_zhidong_panding.setOutsideTouchable(true);
        pop_yingji_lushi_zhidong_panding.setContentView(popu_view_yingji_lushi_zhidong_panding);
        listview_IsQualified_yingji_lushi_zhidong_panding = (ListView)
                popu_view_yingji_lushi_zhidong_panding.findViewById(R.id.setting_listview);
        listview_IsQualified_yingji_lushi_zhidong_panding.setOnItemClickListener(this);
        MyAccountModeListAdapter MyAccountModeListAdapter = new MyAccountModeListAdapter(this,
                yes_no_Modes_yingji_lushi_zhidong_panding);
        listview_IsQualified_yingji_lushi_zhidong_panding.setAdapter(MyAccountModeListAdapter);
    }

    //是否合格popwindow
    private PopupWindow pop_yingji_caozongli_fangshi = null;
    private View popu_view_yingji_caozongli_fangshi;
    private ListView listview_IsQualified_yingji_caozongli_fangshi;
    private List<String> yes_no_Modes_caozongli = new ArrayList<String>();

    private void popWindow_yingji_caozongli_fangshi() {
        for (int i = 0; i < Constants.CAOZONGLI.length; i++) {
            yes_no_Modes_caozongli.add(i, Constants.CAOZONGLI[i]);
        }
        pop_yingji_caozongli_fangshi = new PopupWindow(this);
        popu_view_yingji_caozongli_fangshi = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_yingji_caozongli_fangshi.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_yingji_caozongli_fangshi.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_yingji_caozongli_fangshi.setBackgroundDrawable(new BitmapDrawable());
        pop_yingji_caozongli_fangshi.setFocusable(true);
        pop_yingji_caozongli_fangshi.setOutsideTouchable(true);
        pop_yingji_caozongli_fangshi.setContentView(popu_view_yingji_caozongli_fangshi);
        listview_IsQualified_yingji_caozongli_fangshi = (ListView) popu_view_yingji_caozongli_fangshi
                .findViewById(R.id.setting_listview);
        listview_IsQualified_yingji_caozongli_fangshi.setOnItemClickListener(this);
        MyAccountModeListAdapter MyAccountModeListAdapter = new MyAccountModeListAdapter(this,
                yes_no_Modes_caozongli);
        listview_IsQualified_yingji_caozongli_fangshi.setAdapter(MyAccountModeListAdapter);
    }

    private PopupWindow pop_lushi_jieguo = null;
    private View popu_view_lushi_jieguo;
    private ListView listview_IsQualified_lushi_jieguo;
    private List<String> yes_no_Modes_lushi_jieguo = new ArrayList<String>();

    private void popWindow_lushi_jieguo() {
        for (int i = 0; i < Constants.HEGE_BUHEGE.length; i++) {
            yes_no_Modes_lushi_jieguo.add(i, Constants.HEGE_BUHEGE[i]);
        }
        pop_lushi_jieguo = new PopupWindow(this);
        popu_view_lushi_jieguo = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_lushi_jieguo.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_lushi_jieguo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_lushi_jieguo.setBackgroundDrawable(new BitmapDrawable());
        pop_lushi_jieguo.setFocusable(true);
        pop_lushi_jieguo.setOutsideTouchable(true);
        pop_lushi_jieguo.setContentView(popu_view_lushi_jieguo);
        listview_IsQualified_lushi_jieguo = (ListView) popu_view_lushi_jieguo
                .findViewById(R.id.setting_listview);
        listview_IsQualified_lushi_jieguo.setOnItemClickListener(this);
        MyAccountModeListAdapter MyAccountModeListAdapter = new MyAccountModeListAdapter(this,
                yes_no_Modes_lushi_jieguo);
        listview_IsQualified_lushi_jieguo.setAdapter(MyAccountModeListAdapter);
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (QualifiedType) {
//            case 0:
//                acLushiZhidongJuliHege.setText(yes_no_Modes.get(position));
//                break;
//            case 1:
//                acLushiXietiaoShijianHege.setText(yes_no_Modes.get(position));
//                break;
//            case 2:
//                acLushiMfddHege.setText(yes_no_Modes.get(position));
//                break;
            case 3:
                acLushiPodaoPoduHege.setText(yes_no_Modes.get(position));
                break;
            case 4:
                acLushiChesubiaoHege.setText(yes_no_Modes.get(position));
                break;
            case 5:
                acLushiJianceyuan.setText(renyuanList.get(position));
                break;
        }
        pop.dismiss();
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

    //popuwindow
    private View popu_wendingxing_view;
    //    private LinearLayout parent22_wendingxing;
    private PopupWindow pop_wendingxing = null;
    private ListView listview_wendingxing;
    private MyAccountModeListAdapter myProAdapter_w;
    private List<String> wendingxingList = new ArrayList<String>();

    private void pop_wendingxing() {
        for (int i = 0; i < Constants.STABILITY_TYPE.length; i++) {
            wendingxingList.add(i, Constants.STABILITY_TYPE[i]);
        }
        pop_wendingxing = new PopupWindow(this);
        popu_wendingxing_view = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_wendingxing.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_wendingxing.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_wendingxing.setBackgroundDrawable(new BitmapDrawable());
        pop_wendingxing.setFocusable(true);
        pop_wendingxing.setOutsideTouchable(true);
        pop_wendingxing.setContentView(popu_wendingxing_view);
        listview_wendingxing = (ListView) popu_wendingxing_view.findViewById(R.id.setting_listview);
        myProAdapter_w = new MyAccountModeListAdapter(this, wendingxingList);
        listview_wendingxing.setAdapter(myProAdapter_w);
    }

    //popuwindow
    private View popu_podu_view;
    private PopupWindow pop_podu = null;
    private ListView listview_podu;
    private MyAccountModeListAdapter myProAdapter_podu;
    private List<String> poduList = new ArrayList<String>();

    private void pop_podu() {
        for (int i = 0; i < Constants.GRADIENT_TYPE.length; i++) {
            poduList.add(i, Constants.GRADIENT_TYPE[i]);
        }
        pop_podu = new PopupWindow(this);
        popu_podu_view = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_podu.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_podu.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_podu.setBackgroundDrawable(new BitmapDrawable());
        pop_podu.setFocusable(true);
        pop_podu.setOutsideTouchable(true);
        pop_podu.setContentView(popu_podu_view);
        listview_podu = (ListView) popu_podu_view.findViewById(R.id.setting_listview);
        myProAdapter_podu = new MyAccountModeListAdapter(this, poduList);
        listview_podu.setAdapter(myProAdapter_podu);
    }

    //popuwindow车道宽度
    private View popu_chedao_width;
    private PopupWindow pop_chedao_width = null;
    private ListView listview_chedao_width;
    private CheDaoWidthAdapter cheDaoWidthAdapter;
    private List<String> chedao_widthList = new ArrayList<String>();

    //车道宽度
    private void pop_chedao_width() {
        for (int i = 0; i < Constants.CHEDAO_WIDTH.length; i++) {
            chedao_widthList.add(i, Constants.CHEDAO_WIDTH[i]);
        }
        pop_chedao_width = new PopupWindow(this);
        popu_chedao_width = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop_chedao_width.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop_chedao_width.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_chedao_width.setBackgroundDrawable(new BitmapDrawable());
        pop_chedao_width.setFocusable(true);
        pop_chedao_width.setOutsideTouchable(true);
        pop_chedao_width.setContentView(popu_chedao_width);
        listview_chedao_width = (ListView) popu_chedao_width.findViewById(R.id.setting_listview);
        cheDaoWidthAdapter = new CheDaoWidthAdapter(this, chedao_widthList);
        listview_chedao_width.setAdapter(cheDaoWidthAdapter);
    }

    //popuwindow
    private View popu_renyuan_view;
    private RelativeLayout parent_renyuan;
    private PopupWindow pop_renyuan = null;
    private ListView listview_renyuan;
    private MyAccountModeListAdapter myProAdapter_renyuan;
    private List<String> renyuanList = new ArrayList<String>();

    private void pop_renyuan() {
        for (int i = 0; i < BaseApplication.userAccountModelList.size(); i++) {
            if (BaseApplication.userAccountModelList.get(i).getUserRight().contains("0")
                    || BaseApplication.userAccountModelList.get(i).getUserRight().contains("6")) {
//                renyuanList.add(i, BaseApplication.userAccountModelList.get(i).getUserName());
                renyuanList.add(BaseApplication.userAccountModelList.get(i).getUserName());
            }
        }
        pop_renyuan = new PopupWindow(this);
        popu_renyuan_view = getLayoutInflater().inflate(R.layout.item_popupwindows_bottom,
                null);
        pop_renyuan.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_renyuan.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_renyuan.setBackgroundDrawable(new BitmapDrawable());
        pop_renyuan.setFocusable(true);
        pop_renyuan.setOutsideTouchable(true);
        pop_renyuan.setContentView(popu_renyuan_view);
        parent_renyuan = (RelativeLayout) popu_renyuan_view.findViewById(R.id.parent2);
        listview_renyuan = (ListView) popu_renyuan_view.findViewById(R.id.themeid_listview);
        if (renyuanList != null && renyuanList.size() > 0) {
            myProAdapter_renyuan = new MyAccountModeListAdapter(this, renyuanList);
            listview_renyuan.setAdapter(myProAdapter_renyuan);
            parent_renyuan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pop_renyuan.dismiss();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        if (pop_xuhao != null) {
            pop_xuhao.dismiss();
            pop_xuhao = null;
        }
        if (pop != null) {
            pop.dismiss();
            pop = null;
        }
        instances = null;
    }

    private void getData(String str) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                getDataReportData(str);
            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(instances, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    //检测开始
    private void getItemStart(final String itemCode) {
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
        carItemStartModel.setItemCode(itemCode);
        BaseApplication.R_ItemStart_time = "";
        BaseApplication.R_ItemStart_time = DateUtil.currentTime2();
        carItemStartModel.setDetectionItemStartDate(BaseApplication.R_ItemStart_time);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);
        String upjson_url = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        UtilsLog.e("getItemStart---jsonStr==" + upjson_url);
        UtilsLog.e("getItemStart---url==" + SharedPreferencesUtils.getIP(this) + ITEM_START);

        String logStr = "\n" + "路试项目开始--请求--ItemCode----" + itemCode +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                ITEM_START + "\n" + "JSON::" + upjson_url + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

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
                        String result = response.body();
                        UtilsLog.e("getItemStart-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String str = result.substring(1, result.length() - 1);

                        String logStr = "\n" + "路试项目开始--请求成功--ItemCode" + itemCode +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_START + "\n" + "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        if ("ok".equals(str)) {
                            Toast.makeText(instances, "开始", Toast.LENGTH_LONG)
                                    .show();
                            if (itemCode.equals("R")) {
//                                updateCarStatusInfor(1);
                            }
                        } else {
                            Toast.makeText(instances, "上线失败，请重试", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getItemStart-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试项目开始--请求失败--error--ItemCode" + itemCode +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }

    /**
     * 项目结束
     *
     * @param itemCode
     * @param b        是否是最后一次
     */
    //检测结束
    private void getItemEnd(String itemCode, final boolean b) {
        CarItemEndModel carItemStartModel = new CarItemEndModel();
        carItemStartModel.setPlatformSN(carsInforModel.getPlatformSN());
        carItemStartModel.setPlateType(carsInforModel.getPlateType());
        carItemStartModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
        carItemStartModel.setVIN(carsInforModel.getVIN());
        carItemStartModel.setLine(Line);
        carItemStartModel.setTest_times(carsInforModel.getTimes());
        carItemStartModel.setDetectionDevID(CommonUtils.getIMEI(this));
//        carItemStartModel.setItemCode(Item_code);
        carItemStartModel.setItemCode(itemCode);
        carItemStartModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
        BaseApplication.R_ItemEnd_time = "";
        BaseApplication.R_ItemEnd_time = DateUtil.currentTime2();
        carItemStartModel.setDetectionItemEndDate(BaseApplication.R_ItemEnd_time);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(carItemStartModel);
        String upjson_url = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        UtilsLog.e("getItemJainEnd---jsonStr==" + upjson_url);
        UtilsLog.e("getItemJainEnd---url==" + SharedPreferencesUtils.getIP(this) + ITEM_END);

        String logStr = "\n" + "路试项目结束--请求--ItemCode" + itemCode +
                "\n" + "是否是最后一次，最后一次要调用项目总结束---" + b + "\n" +
                "URL::" + SharedPreferencesUtils.getIP(instances) +
                ITEM_END + "\n" + "JSON::" + upjson_url + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + ITEM_END).tag(this)
                .upJson(upjson_url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getItemJainEnd-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String str = result.substring(1, result.length() - 1);
                        String logStr = "\n" + "路试项目结束--请求成功" +
                                "\n" + "是否是最后一次，最后一次要调用项目总结束---" + b + "\n" +
                                "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END + "\n" + "result::" + response.body() + "\n";
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        if (b) {
                            updateCarStatusInfor(1);
                            Toast.makeText(instances, "项目完成", Toast.LENGTH_LONG).show();

                            //项目总结束
                            EndAllItem(String.valueOf(carsInforModel.getID()));
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                }
//                            }, 300);//后执行Runnable中的run方法
                        } else {
                            Toast.makeText(instances, "结束", Toast.LENGTH_LONG)
                                    .show();
                        }


                        if ("ok".equals(str)) {
//                            //项目总结束
//                            if (b) {
//
//                                updateCarStatusInfor(1);
//                                Toast.makeText(instances, "项目完成", Toast.LENGTH_LONG).show();
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //项目总结束
//                                        EndAllItem(String.valueOf(carsInforModel.getID()));
//                                    }
//                                }, 300);//后执行Runnable中的run方法
//                            } else {
//                                Toast.makeText(instances, "结束", Toast.LENGTH_LONG)
//                                        .show();
//                            }
                        } else {
                            showDialog("路试平台下线失败，请尝试重新下线");
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                            Toast.makeText(instances, "下线失败，请重试", Toast.LENGTH_LONG)
//                                    .show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getItemJainEnd-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试项目结束--请求失败--error" +
                                "\n" + "是否是最后一次，最后一次要调用项目总结束---" + b + "\n"
                                + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }

    /**
     * 更新车辆状态
     *
     * @param flag flag:0 未检测
     *             flag:1 项目开始
     *             flag:2 项目结束
     *             flag:917  中途退出的情况
     */
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
        String jsonStr = gson.toJson(model);
        UtilsLog.e("updateCarStatusInfor---jsonStr==" + jsonStr);
        UtilsLog.e("updateCarStatusInfor---url==" + SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS);

        String logStr = "\n" + "路试--更新状态--请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                UPDATE_CAR_STATUS + "\n" + "JSON::" + jsonStr + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS)
                .tag(this)
                .upJson(jsonStr)
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
                        CommonUtils.hideLoadingDialog(instances);

                        String logStr = "\n" + "路试--更新状态--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "\n" + "result::" + result + "\n";
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试--更新状态--失败--error--" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }

    //平台上传数据--DetectionData上传数据
    private void uploadPlatformData(final String url, RoadTestModel model) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model);
        String upjson_url = "{" + jsonStr.substring(1, jsonStr.length() - 1) + "}";
        upjson_url = "[" + upjson_url + "]";

        UtilsLog.e("uploadPlatformData---jsonStr=路试=" + upjson_url);
        UtilsLog.e("uploadPlatformData---url=路试=" + SharedPreferencesUtils.getIP(this) + url);

        String logStr = "\n" + "路试--上传数据-请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                url + "\n" + "JSON::" + upjson_url + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + url)
                .tag(this)
                .upJson(upjson_url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("uploadPlatformData-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试--上传数据-请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                url + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("uploadPlatformData-onError==" + response.body());
                        String logStr = "\n" + "路试--上传数据-失败-onError" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                url + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }


    /**
     * 数据上报
     *
     * @param jsonstr 要上报的json字符串
     *
     **/
    private void getDataReportData(String jsonstr) {
        UtilsLog.e("getDataReportData-jsonstr==" + jsonstr);
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + REPORTED_DATA);

        String logStr = "\n" + "路试上传自己后台数据--请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                REPORTED_DATA + "\n" + "JSON::" + jsonstr + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + REPORTED_DATA).tag(instances)
                .upJson(jsonstr)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        CommonUtils.hideLoadingDialog(instances);
                        String result = response.body();
                        UtilsLog.e("getDataReportData-result==" + result);

                        String logStrf = "\n" + "路试---上传自己后台数据--请求成功" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());

                        if ("\"ok\"".equals(result)) {
//                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
//                            instances.finish();
                        } else {
//                            showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                        String logStr = "\n" + "路试--上传自己后台数据-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
//                        showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    private void getDataPlateData(String jsonstr) {
        UtilsLog.e("getDataReport111Data-jsonstr=1111=" + "[" + jsonstr + "]");
        UtilsLog.e("getDataReport111Data---url=11111=" + SharedPreferencesUtils.getIP(instances) +
                PLAT_FORM_DATA);
        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + PLAT_FORM_DATA).tag(instances)
                .upJson("[" + jsonstr + "]")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
//                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataReport111Data-result=11111=" + result);
                        if ("\"ok\"".equals(result)) {
//                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
//                            instances.finish();
                        }
//
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
                    }
                });
    }

    //视频接口
    private void uploadVideo(VideoModel model) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model);
        UtilsLog.e("uploadVideo---jsonStr==" + jsonStr);
        UtilsLog.e("uploadVideo---url==" + SharedPreferencesUtils.getIP(this) + VIDEO_ITEM);

        String logStr = "\n" + "路试--uploadVideo--请求" +
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

                        String logStrf = "\n" + "路试--uploadVideo--请求成功" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("uploadPlatformData_w-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试--uploadVideo-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                VIDEO_ITEM + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }


    String PHOTO_PlatformCode = "";//拍照对应平台code


    //动态拍照
    private void getDataDCPicture(String plateType, String Item_Code) {
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
        String jsonStr = gson.toJson(model);
        UtilsLog.e("getDataDCPicture---jsonStr==" + jsonStr);
        UtilsLog.e("getDataDCPicture---url==" + SharedPreferencesUtils.getIP(instances) +
                DC_PICTURE);

        String logStr = "\n" + "路试--拍照请求" +
                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                DC_PICTURE + "\n" + "JSON::" + jsonStr + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + DC_PICTURE).tag(instances)
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
                        UtilsLog.e("getDataDCPicture-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataDCPicture-newResult==" + newResult);

                        String logStr = "\n" + "路试--拍照请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                DC_PICTURE + "\n" + "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());


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
                        CommonUtils.hideLoadingDialog(instances);

                        String logStr = "\n" + "路试--拍照请求失败--error" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }

    /**
     * 项目总结束接口,最后一个执行
     */
    private void EndAllItem(String detectionID) {
        UtilsLog.e("EndAllItem---url==" + SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
                detectionID + "/DetectionID");
        String logStr = "\n" + "路试--总结束-请求" +
                "\n" + "url::" + SharedPreferencesUtils.getIP(instances) + ITEM_ALL_END +
                detectionID + "/DetectionID" + "\n";
        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
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
                        String logStr = "\n" + "路试--总结束-请求成功" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        instances.finish();

//                        if ("\"ok\"".equals(result)) {
//
//                            updateCarStatusInfor(1);
//                            Toast.makeText(instances, "项目完成", Toast.LENGTH_LONG).show();
//                            instances.finish();
//                        } else {
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("EndAllItem-onError==" + response.body());
                        UtilsLog.e("EndAllItem-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "路试--总结束-失败-error" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                    }
                });
    }

    AlertDialog.Builder builder = null;

    private void showDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder
                (instances);
        builder.setTitle("提示");
        builder.setMessage(content);
//        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

//            getDataWaiJainEnd();
//            updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
