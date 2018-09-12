package net.com.mvp.ac.activity;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.BuildConfig;
import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.ContentAdapter2;
import net.com.mvp.ac.api.ApiConfig;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.cache.FileUtils;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.DateUtil;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarItemEndModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.CheckItemModel;
import net.com.mvp.ac.model.Item_2Model;
import net.com.mvp.ac.model.ItemsModel;
import net.com.mvp.ac.model.MyChoiceModel;
import net.com.mvp.ac.model.OutPhotoItem;
import net.com.mvp.ac.model.PhotoTypeModel;
import net.com.mvp.ac.model.ReportedModel;
import net.com.mvp.ac.model.TypeModel;
import net.com.mvp.ac.model.UpdateCarStatusModel;
import net.com.mvp.ac.model.VideoModel;
import net.com.mvp.ac.model.WaiJainDataModel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countuptime.CountupView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static net.com.mvp.ac.application.BaseApplication.IsFirst;
import static net.com.mvp.ac.application.BaseApplication.WAIJIAN_PHOTO_PREVIEW_LISTS;
import static net.com.mvp.ac.application.BaseApplication.myChoiceModelList;
import static net.com.mvp.ac.application.BaseApplication.mycar_checkItemlList;

/**
 *
 */
//外检拍照
public class WaiJianPhotoActivity extends TakePhotoActivity implements AdapterView.OnItemClickListener,
        ApiConfig {

    @BindView(R.id.ac_waijian_txt_zuoqianfang_45)
    TextView acWaijianTxtZuoqianfang45;
    @BindView(R.id.ac_waijian_btn_zuoqianfang_45)
    Button acWaijianBtnZuoqianfang45;
    @BindView(R.id.ac_waijian_txt_youhoufang_45)
    TextView acWaijianTxtYouhoufang45;
    @BindView(R.id.ac_waijian_btn_youhoufang_45)
    Button acWaijianBtnYouhoufang45;
    @BindView(R.id.ac_waijian_txt_shibiedaima)
    TextView acWaijianTxtShibiedaima;
    @BindView(R.id.ac_waijian_btn_shibiedaima)
    Button acWaijianBtnShibiedaima;
    @BindView(R.id.ac_waijian_txt_anquandai)
    TextView acWaijianTxtAnquandai;
    @BindView(R.id.ac_waijian_btn_anquandai)
    Button acWaijianBtnAnquandai;
    @BindView(R.id.ac_waijian_lay_anquandai)
    LinearLayout acWaijianLayAnquandai;
    @BindView(R.id.ac_waijian_txt_xingshi_jilu)
    TextView acWaijianTxtXingshiJilu;
    @BindView(R.id.ac_waijian_btn_xingshi_jilu)
    Button acWaijianBtnXingshiJilu;
    @BindView(R.id.ac_waijian_xingshi_jilu)
    LinearLayout acWaijianXingshiJilu;
    @BindView(R.id.ac_waijian_txt_miehuoqi)
    TextView acWaijianTxtMiehuoqi;
    @BindView(R.id.ac_waijian_btn_miehuoqi)
    Button acWaijianBtnMiehuoqi;
    @BindView(R.id.ac_waijian_miehuoqi)
    LinearLayout acWaijianMiehuoqi;
    @BindView(R.id.ac_waijian_txt_chexiangneibu)
    TextView acWaijianTxtChexiangneibu;
    @BindView(R.id.ac_waijian_btn_chexiangneibu)
    Button acWaijianBtnChexiangneibu;
    @BindView(R.id.ac_waijian_chexiangneibu)
    LinearLayout acWaijianChexiangneibu;
    @BindView(R.id.ac_waijian_txt_cheliang_zhenghoufang)
    TextView acWaijianTxtCheliangZhenghoufang;
    @BindView(R.id.ac_waijian_btn_cheliang_zhenghoufang)
    Button acWaijianBtnCheliangZhenghoufang;
    @BindView(R.id.ac_waijian_cheliang_zhenghoufang)
    LinearLayout acWaijianCheliangZhenghoufang;
    @BindView(R.id.ac_waijian_txt_xiaochebiaopai)
    TextView acWaijianTxtXiaochebiaopai;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai)
    Button acWaijianBtnXiaochebiaopai;
    //    @BindView(R.id.ac_waijian_xiaochebiaopai)
//    LinearLayout acWaijianXiaochebiaopai;
    @BindView(R.id.ac_waijian_txt_xiaoche_biaozhideng)
    TextView acWaijianTxtXiaocheBiaozhideng;
    @BindView(R.id.ac_waijian_btn_xiaoche_biaozhideng)
    Button acWaijianBtnXiaocheBiaozhideng;
    @BindView(R.id.ac_waijian_xiaoche_biaozhideng)
    LinearLayout acWaijianXiaocheBiaozhideng;
    @BindView(R.id.ac_waijian_txt_xiaoche_tingche_zhishipai)
    TextView acWaijianTxtXiaocheTingcheZhishipai;
    @BindView(R.id.ac_waijian_btn_xiaoche_tingche_zhishipai)
    Button acWaijianBtnXiaocheTingcheZhishipai;
    @BindView(R.id.ac_waijian_xiaoche_tingche_zhishipai)
    LinearLayout acWaijianXiaocheTingcheZhishipai;
    @BindView(R.id.ac_waijian_btn_wancheng)
    Button acWaijianBtnWancheng;

    @BindView(R.id.ac_waijian_txt_caozong_fuzhu)
    TextView acWaijianTxtCaozongFuzhu;
    @BindView(R.id.ac_waijian_btn_caozong_fuzhu)
    Button acWaijianBtnCaozongFuzhu;
    @BindView(R.id.ac_waijian_caozong_fuzhu_fuzhu)
    LinearLayout acWaijianCaozongFuzhu;
    @BindView(R.id.ac_waijian_txt_fadongjihao)
    TextView acWaijianTxtFadongjihao;
    @BindView(R.id.ac_waijian_btn_fadongjihao)
    Button acWaijianBtnFadongjihao;
    @BindView(R.id.ac_waijian_fadongjihao)
    LinearLayout acWaijianFadongjihao;
    @BindView(R.id.ac_waijian_txt_fadongjicang)
    TextView acWaijianTxtFadongjicang;
    @BindView(R.id.ac_waijian_btn_fadongjicang)
    Button acWaijianBtnFadongjicang;
    @BindView(R.id.ac_waijian_fadongjicang)
    LinearLayout acWaijianFadongjicang;
    @BindView(R.id.ac_waijian_txt_yingjichui)
    TextView acWaijianTxtYingjichui;
    @BindView(R.id.ac_waijian_btn_yingjichui)
    Button acWaijianBtnYingjichui;
    @BindView(R.id.ac_waijian_yingjichui)
    LinearLayout acWaijianYingjichui;
    @BindView(R.id.ac_waijian_txt_jijiuxiang)
    TextView acWaijianTxtJijiuxiang;
    @BindView(R.id.ac_waijian_btn_jijiuxiang)
    Button acWaijianBtnJijiuxiang;
    @BindView(R.id.ac_waijian_jijiuxiang)
    LinearLayout acWaijianJijiuxiang;
    @BindView(R.id.ac_waijian_txt_fangbaosi)
    TextView acWaijianTxtFangbaosi;
    @BindView(R.id.ac_waijian_btn_fangbaosi)
    Button acWaijianBtnFangbaosi;
    @BindView(R.id.ac_waijian_fangbaosi)
    LinearLayout acWaijianFangbaosi;
    @BindView(R.id.ac_waijian_txt_fuzhuzhidong)
    TextView acWaijianTxtFuzhuzhidong;
    @BindView(R.id.ac_waijian_btn_fuzhuzhidong)
    Button acWaijianBtnFuzhuzhidong;
    @BindView(R.id.ac_waijian_fuzhuzhidong)
    LinearLayout acWaijianFuzhuzhidong;
    @BindView(R.id.ac_waijian_txt_jinji_qieduan)
    TextView acWaijianTxtJinjiQieduan;
    @BindView(R.id.ac_waijian_btn_jinji_qieduan)
    Button acWaijianBtnJinjiQieduan;
    @BindView(R.id.ac_waijian_jinji_qieduan)
    LinearLayout acWaijianJinjiQieduan;
    @BindView(R.id.ac_waijian_txt_shoudong_jixie)
    TextView acWaijianTxtShoudongJixie;
    @BindView(R.id.ac_waijian_btn_shoudong_jixie)
    Button acWaijianBtnShoudongJixie;
    @BindView(R.id.ac_waijian_shoudong_jixie)
    LinearLayout acWaijianShoudongJixie;
    @BindView(R.id.ac_waijian_txt_fu_zhidong_taban)
    TextView acWaijianTxtFuZhidongTaban;
    @BindView(R.id.ac_waijian_btn_fu_zhidong_taban)
    Button acWaijianBtnFuZhidongTaban;
    @BindView(R.id.ac_waijian_fu_zhidong_taban)
    LinearLayout acWaijianFuZhidongTaban;
    @BindView(R.id.ac_waijian_txt_weixian_huowu)
    TextView acWaijianTxtWeixianHuowu;
    @BindView(R.id.ac_waijian_btn_weixian_huowu)
    Button acWaijianBtnWeixianHuowu;
    @BindView(R.id.ac_waijian_weixian_huowu)
    LinearLayout acWaijianWeixianHuowu;

    @BindView(R.id.ac_waijian_luntai_guige)
    LinearLayout acWaijianLuntaiGuige;

    String tag = "tag";
    // 相册
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    private static String picFileFullName;

    CarsInforModel carsInforModel;

    @BindView(R.id.ac_waijian_txt_luntai_guige_l_f)
    TextView acWaijianTxtLuntaiGuigeLF;
    @BindView(R.id.ac_waijian_btn_luntai_guige_l_f)
    Button acWaijianBtnLuntaiGuigeLF;
    @BindView(R.id.ac_waijian_txt_luntai_guige_l_b)
    TextView acWaijianTxtLuntaiGuigeLB;
    @BindView(R.id.ac_waijian_btn_luntai_guige_l_b)
    Button acWaijianBtnLuntaiGuigeLB;
    @BindView(R.id.ac_waijian_txt_luntai_guige_r_f)
    TextView acWaijianTxtLuntaiGuigeRF;
    @BindView(R.id.ac_waijian_btn_luntai_guige_r_f)
    Button acWaijianBtnLuntaiGuigeRF;
    @BindView(R.id.ac_waijian_txt_luntai_guige_r_b)
    TextView acWaijianTxtLuntaiGuigeRB;
    @BindView(R.id.ac_waijian_btn_luntai_guige_r_b)
    Button acWaijianBtnLuntaiGuigeRB;

    @BindView(R.id.ac_waijian_btn_buhege)
    Button acWaijianBtnBuhegeXiangmu;
    @BindView(R.id.ac_waijian_btn_zuoqianfang_45_preview)
    Button acWaijianBtnZuoqianfang45Preview;
    @BindView(R.id.ac_waijian_btn_youhoufang_45_preview)
    Button acWaijianBtnYouhoufang45Preview;
    @BindView(R.id.ac_waijian_btn_shibiedaima_preview)
    Button acWaijianBtnShibiedaimaPreview;
    @BindView(R.id.ac_waijian_btn_anquandai_preview)
    Button acWaijianBtnAnquandaiPreview;
    @BindView(R.id.ac_waijian_btn_xingshi_jilu_preview)
    Button acWaijianBtnXingshiJiluPreview;
    @BindView(R.id.ac_waijian_btn_miehuoqi_preview)
    Button acWaijianBtnMiehuoqiPreview;
    @BindView(R.id.ac_waijian_btn_chexiangneibu_preview)
    Button acWaijianBtnChexiangneibuPreview;
    @BindView(R.id.ac_waijian_btn_cheliang_zhenghoufang_preview)
    Button acWaijianBtnCheliangZhenghoufangPreview;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai_preview)
    Button acWaijianBtnXiaochebiaopaiPreview;
    @BindView(R.id.ac_waijian_btn_xiaoche_biaozhideng_preview)
    Button acWaijianBtnXiaocheBiaozhidengPreview;
    @BindView(R.id.ac_waijian_btn_xiaoche_tingche_zhishipai_preview)
    Button acWaijianBtnXiaocheTingcheZhishipaiPreview;
    @BindView(R.id.ac_waijian_btn_caozong_fuzhu_preview)
    Button acWaijianBtnCaozongFuzhuPreview;
    @BindView(R.id.ac_waijian_btn_fadongjihao_preview)
    Button acWaijianBtnFadongjihaoPreview;
    @BindView(R.id.ac_waijian_btn_fadongjicang_preview)
    Button acWaijianBtnFadongjicangPreview;
    @BindView(R.id.ac_waijian_btn_yingjichui_preview)
    Button acWaijianBtnYingjichuiPreview;
    @BindView(R.id.ac_waijian_btn_jijiuxiang_preview)
    Button acWaijianBtnJijiuxiangPreview;
    @BindView(R.id.ac_waijian_btn_fangbaosi_preview)
    Button acWaijianBtnFangbaosiPreview;
    @BindView(R.id.ac_waijian_btn_fuzhuzhidong_preview)
    Button acWaijianBtnFuzhuzhidongPreview;
    @BindView(R.id.ac_waijian_btn_jinji_qieduan_preview)
    Button acWaijianBtnJinjiQieduanPreview;
    @BindView(R.id.ac_waijian_btn_shoudong_jixie_preview)
    Button acWaijianBtnShoudongJixiePreview;
    @BindView(R.id.ac_waijian_btn_fu_zhidong_taban_preview)
    Button acWaijianBtnFuZhidongTabanPreview;
    @BindView(R.id.ac_waijian_btn_weixian_huowu_preview)
    Button acWaijianBtnWeixianHuowuPreview;
    @BindView(R.id.ac_waijian_btn_luntai_guige_l_f_preview)
    Button acWaijianBtnLuntaiGuigeLFPreview;
    @BindView(R.id.ac_waijian_btn_luntai_guige_l_b_preview)
    Button acWaijianBtnLuntaiGuigeLBPreview;
    @BindView(R.id.ac_waijian_btn_luntai_guige_r_f_preview)
    Button acWaijianBtnLuntaiGuigeRFPreview;
    @BindView(R.id.ac_waijian_btn_luntai_guige_r_b_preview)
    Button acWaijianBtnLuntaiGuigeRBPreview;
    @BindView(R.id.ac_waijian_xiaochebiaopai_1)
    LinearLayout acWaijianXiaochebiaopai1;
    @BindView(R.id.ac_waijian_txt_xiaochebiaopai2)
    TextView acWaijianTxtXiaochebiaopai2;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai_preview2)
    Button acWaijianBtnXiaochebiaopaiPreview2;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai2)
    Button acWaijianBtnXiaochebiaopai2;
    @BindView(R.id.ac_waijian_xiaochebiaopai_2)
    LinearLayout acWaijianXiaochebiaopai2;
    @BindView(R.id.ac_waijian_txt_xiaochebiaopai3)
    TextView acWaijianTxtXiaochebiaopai3;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai_preview3)
    Button acWaijianBtnXiaochebiaopaiPreview3;
    @BindView(R.id.ac_waijian_btn_xiaochebiaopai3)
    Button acWaijianBtnXiaochebiaopai3;
    @BindView(R.id.ac_waijian_xiaochebiaopai_3)
    LinearLayout acWaijianXiaochebiaopai3;
    @BindView(R.id.ac_waijian_txt_chexiang_yeyabi)
    TextView acWaijianTxtChexiangYeyabi;
    @BindView(R.id.ac_waijian_btn_chexiang_yeyabi_preview)
    Button acWaijianBtnChexiangYeyabiPreview;
    @BindView(R.id.ac_waijian_btn_chexiang_yeyabi)
    Button acWaijianBtnChexiangYeyabi;
    @BindView(R.id.ac_waijian_chexiang_yeyabi)
    LinearLayout acWaijianChexiangYeyabi;
    @BindView(R.id.ac_waijian_photo_listview)
    ListView acWaijianPhotoListview;
    @BindView(R.id.ac_waijian_btn_buhege2)
    Button acWaijianBtnBuhege2;
    @BindView(R.id.ac_waijian_btn_wancheng2)
    Button acWaijianBtnWancheng2;

    @BindView(R.id.ac_waijian_photo_scrollview)
    ScrollView acWaijianScrollView;
    @BindView(R.id.ac_waijian_tv_jishiqi_time_time)
    TextView acWaijianTvJishiqi;
    @BindView(R.id.ac_waijian_photo_ll_bottom_layout)
    LinearLayout acWaijianPhotoLlBottomLayout;

    private WaiJianPhotoActivity instances = null;
    private String ID = null;//检测车辆ID
    private String carNo = null;//车牌号码
    private String PlateType = null;//号牌种类类型
    private String cartype = null;//车辆类型
    private String PHOTO_TYPE = null;//外检拍照的TYPE上传类型
    private String waijian_chedao = null;//外检车道，检测线代号
    private List<PhotoTypeModel> photoTypeModelList;//照片类型列表

    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private String Line = "1";//外检车道
    private String Item_Code = "";//F1外检,R1路试，DC底盘动态
    private WaiJainDataModel waiJainDataModel = new WaiJainDataModel();//外检上传数据model
    private String Login_name = "";//当前登录的检测员的名字
    //    BaseApplication.mycar_checkItemlList = new ArrayList<>();//获取当前车辆需要的人工检验项目

    private VideoModel model = new VideoModel();
    private List<Boolean> IsTakePhoto = new ArrayList<>();//当前车辆要检测的项目是否拍照的列表

    TakePhoto takePhoto;
    Uri imageUri;
    int recLen = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            if (recLen < 1200) {
                acWaijianTvJishiqi.setText("项目进行时间：" + recLen + "  秒");
            }
            handler.postDelayed(this, 1000);
        }
    };
    CountupView mCvCountupViewTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wai_jian_photo);
        ButterKnife.bind(this);

        mCvCountupViewTest1 = (CountupView) findViewById(R.id.ac_waijian_tv_jishiqi_cv_CountupViewTest3);
        mCvCountupViewTest1.setTag("test1");
        long time1 = (long) 1000;
        mCvCountupViewTest1.start(time1);


//        handler.postDelayed(runnable, 1000);
//        setBackBtn();

//        setTopTitle(R.string.ac_waijian_title);

        WAIJIAN_PHOTO_PREVIEW_LISTS = new HashMap<>();
        instances = this;
        waijian_chedao = getIntent().getExtras().getString("CarDetailsActivity_acCarDetailsWaijianchedao",
                "");
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra
                ("CarDetailsActivity_CarsInforModel");
        UtilsLog.e("carsInforModel=55=" + carsInforModel.toString());
//        carsInforModel = getIntent().getExtras().getParcelable("CarDetailsActivity_CarsInforModel");
        Login_name = getIntent().getExtras().getString("login_w_name", "");
        UtilsLog.e("Login_name=Login_name=" + Login_name);
        initView();
//        verifyStoragePermissions(instances);
        //检测车辆ID
        ID = String.valueOf(carsInforModel.getID());
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
        //获取去图片类型列表
//        getDataPhotoTypeList();


        CHECK_MODE = BaseApplication.JIANCE_MODE;
        UtilsLog.e("WaiJianPhotoActivity---CHECK_MODE==" + CHECK_MODE);
        Line = getIntent().getStringExtra("CarDetailsActivity_Line");
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
                // UtilsLog.e("Item_2Model_list=11=" + Item_2Model_list.get(i).toString());
                MyChoiceModel model = new MyChoiceModel();
                model.setCode2(Item_2Model_list.get(i).getCode2());
                model.setChecked(true);
                model.setName2(Item_2Model_list.get(i).getName2());
                model.setCode_key("Code2-" + Item_2Model_list.get(i).getCode2());
                model.setCode_values("0");
                myChoiceModelList.add(model);
            }
//            for (int i = 0; i < myChoiceModelList.size(); i++) {
//                UtilsLog.e("myChoiceModelList=11=" + myChoiceModelList.get(i).toString());
//            }
            getMyCarItems();
        }

        init();
        getOutPhotoItem();

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.e("photo", "拍照失败==" + msg);
        ArrayList<TImage> images = result.getImages();
        if (images != null && images.size() > 0) {
            String photo_file_path = images.get(0).getCompressPath();
            String photo_file_path2 = images.get(0).getOriginalPath();
            Log.e("photo", "拍照失败--path==" + photo_file_path);
            Log.e("photo", "拍照失败--path=22=" + photo_file_path2);
//            File photo_flie = new File(photo_file_path);
//
//            //拍照压缩后上传
//            getData(photo_flie);
        }
    }

    String photo_file_path;
    String photo_file_path_OriginalPath;

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Log.e("photo", "111111111111");
        if (result != null) {
//            showImg(result.getImages());
            ArrayList<TImage> images = result.getImages();
            if (images != null && images.size() > 0) {
                //压缩后的图片路径，不压缩为null
                photo_file_path = images.get(0).getCompressPath();
//                Log.e("photo", "拍照成功--path==" + photo_file_path);
                //原图片的路径
                photo_file_path_OriginalPath = Environment.getExternalStorageDirectory() + images.get(0)
                        .getOriginalPath().replace("my_images/", "");
                Log.e("photo", "拍照成功--path=22=" + photo_file_path_OriginalPath);
                if (!TextUtils.isEmpty(photo_file_path_OriginalPath)) {
//                    Log.e("photo", "拍照成功--path=33=" + img_imageUri_path);
                    if (SharedPreferencesUtils.getPhotoClipping(instances)) {
                        //拍照需要编辑的
                        Intent intent = new Intent(instances, WaiRotatePhotoActivity.class);
                        intent.putExtra("wa_rotate_img", photo_file_path_OriginalPath);
                        startActivityForResult(intent, 917);
                    } else {
                        //拍照不需要编辑的
                        Luban.with(instances)
                                .load(new File(photo_file_path_OriginalPath))                     //传人要压缩的图片
                                .setCompressListener(new OnCompressListener() { //设置回调
                                    @Override
                                    public void onStart() {
                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        // TODO 压缩成功后调用，返回压缩后的图片文件
                                        Log.e("photo", "压缩成功后调用");
                                        //拍照压缩后上传
//                                    getData(file,picFileFullName);
                                        getData(file, photo_file_path_OriginalPath);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用
                                        Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                                    }
                                }).launch();    //启动压缩
                    }
                }
            }
        }
    }

    private void showImg(ArrayList<TImage> images) {
//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtra("images", images);
//        startActivity(intent);
    }

    private void initView() {
        //操纵辅助装置  残疾人专用汽车
        acWaijianCaozongFuzhu.setVisibility(View.GONE);

//        String carType = carsInforModel.getType();//车辆类型
//        String RegisteDate = carsInforModel.getRegisteDate();//注册登记日期
//        int TotalMass = Integer.parseInt(carsInforModel.getTotalMass());//总质量
//        String UseProperty = carsInforModel.getUseProperty();//使用性质

//        //行驶记录装置
//        if (VehicleCheckUtils.IsBanGuaQianYinChe(carType)) {
//            acWaijianXingshiJilu.setVisibility(View.VISIBLE);
//        } else if (VehicleCheckUtils.checkDrivingRecordingMethod(UseProperty, RegisteDate, TotalMass)) {
//            acWaijianXingshiJilu.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianXingshiJilu.setVisibility(View.GONE);
//        }
//
//        //灭火器
//        if (VehicleCheckUtils.IsKeche(carType) || VehicleCheckUtils.IsWeiXianyunshuche(UseProperty)) {
//            acWaijianMiehuoqi.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianMiehuoqi.setVisibility(View.GONE);
//        }
//
//        //车厢内部拍照
//        if (VehicleCheckUtils.checkCarInterior(carType)) {
//            acWaijianChexiangneibu.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianChexiangneibu.setVisibility(View.GONE);
//        }
//
//        //车辆正后方
//        if (VehicleCheckUtils.checkBehindCar(carType, UseProperty)) {
//            acWaijianCheliangZhenghoufang.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianCheliangZhenghoufang.setVisibility(View.GONE);
//        }
//
//        //校车
//        if (VehicleCheckUtils.IsSchoolBus(UseProperty)) {
//            acWaijianXiaochebiaopai1.setVisibility(View.VISIBLE);
//            acWaijianXiaochebiaopai2.setVisibility(View.VISIBLE);
//            acWaijianXiaochebiaopai3.setVisibility(View.VISIBLE);
//            acWaijianXiaocheBiaozhideng.setVisibility(View.VISIBLE);
//            acWaijianXiaocheTingcheZhishipai.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianXiaochebiaopai1.setVisibility(View.GONE);
//            acWaijianXiaochebiaopai2.setVisibility(View.GONE);
//            acWaijianXiaochebiaopai3.setVisibility(View.GONE);
//            acWaijianXiaocheBiaozhideng.setVisibility(View.GONE);
//            acWaijianXiaocheTingcheZhishipai.setVisibility(View.GONE);
//        }
//
//        //发动机号码拍照
//        if (!VehicleCheckUtils.IsGuaChe(carType)) {
//            acWaijianFadongjihao.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianFadongjihao.setVisibility(View.GONE);
//        }
//
//        //发动机舱自灭火装置拍照
//        if (VehicleCheckUtils.IsKeche(carType) || VehicleCheckUtils.IsSchoolBus(UseProperty)) {
//            acWaijianFadongjicang.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianFadongjicang.setVisibility(View.GONE);
//        }
//
//        //应急锤拍照
//        if (VehicleCheckUtils.IsKeche(carType)) {
//            acWaijianYingjichui.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianYingjichui.setVisibility(View.GONE);
//        }
//
//        //急救箱拍照
//        if (VehicleCheckUtils.IsSchoolBus(UseProperty)) {
//            acWaijianJijiuxiang.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianJijiuxiang.setVisibility(View.GONE);
//        }
//
//        //防抱死
//        if (VehicleCheckUtils.IsSchoolBus(UseProperty) || VehicleCheckUtils.IsKeche(carType)
//                || VehicleCheckUtils.IsHuoche(carType) || VehicleCheckUtils.IsZhuanXiangzuoyeche(carType)) {
//            acWaijianFangbaosi.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianFangbaosi.setVisibility(View.GONE);
//        }
//
//        //辅助制动装置拍照
//        if (VehicleCheckUtils.IsHuoche(carType) || VehicleCheckUtils.IsZhuanXiangzuoyeche(carType)) {
//            acWaijianFuzhuzhidong.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianFuzhuzhidong.setVisibility(View.GONE);
//        }
//
//        //紧急切断装置
//        if (VehicleCheckUtils.IsWeiXianyunshuche(UseProperty)) {
//            acWaijianJinjiQieduan.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianJinjiQieduan.setVisibility(View.GONE);
//        }
//
//        //手动机械断电开关
//        if (VehicleCheckUtils.IsKeche(carType)) {
//            acWaijianShoudongJixie.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianShoudongJixie.setVisibility(View.GONE);
//        }
//
//        //副制动踏板拍照
//        if (VehicleCheckUtils.IsJiaoLianche(UseProperty)) {
//            acWaijianFuZhidongTaban.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianFuZhidongTaban.setVisibility(View.GONE);
//        }
//
//        //危险货物运输车标志
//        if (VehicleCheckUtils.IsWeiXianyunshuche(UseProperty)) {
//            acWaijianWeixianHuowu.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianWeixianHuowu.setVisibility(View.GONE);
//        }
//
//        //轮轮胎规格拍照
//        if (VehicleCheckUtils.IsSchoolBus(UseProperty) || VehicleCheckUtils.IsKeche(carType)
//                || VehicleCheckUtils.IsHuoche(carType) || VehicleCheckUtils.IsZhuanXiangzuoyeche(carType)
//                || VehicleCheckUtils.IsWeiXianyunshuche(UseProperty)) {
//            acWaijianLuntaiGuige.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianLuntaiGuige.setVisibility(View.GONE);
//        }
//
//        //车厢液压臂拍照
//        if (VehicleCheckUtils.checkCarHydraulicArm(carType)) {
//            acWaijianChexiangYeyabi.setVisibility(View.VISIBLE);
//        } else {
//            acWaijianChexiangYeyabi.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.ac_waijian_btn_caozong_fuzhu, R.id.ac_waijian_btn_fadongjihao,
            R.id.ac_waijian_btn_fadongjicang, R.id.ac_waijian_btn_yingjichui, R.id.ac_waijian_btn_jijiuxiang,
            R.id.ac_waijian_btn_fuzhuzhidong, R.id.ac_waijian_btn_jinji_qieduan,
            R.id.ac_waijian_btn_shoudong_jixie, R.id.ac_waijian_btn_fu_zhidong_taban,
            R.id.ac_waijian_btn_weixian_huowu, R.id.ac_waijian_btn_zuoqianfang_45,
            R.id.ac_waijian_btn_youhoufang_45, R.id.ac_waijian_btn_shibiedaima, R.id.ac_waijian_btn_anquandai,
            R.id.ac_waijian_btn_xingshi_jilu, R.id.ac_waijian_btn_miehuoqi, R.id.ac_waijian_btn_chexiangneibu,
            R.id.ac_waijian_btn_cheliang_zhenghoufang, R.id.ac_waijian_btn_xiaochebiaopai,
            R.id.ac_waijian_btn_xiaoche_biaozhideng, R.id.ac_waijian_btn_xiaoche_tingche_zhishipai,
            R.id.ac_waijian_btn_wancheng, R.id.ac_waijian_btn_fangbaosi,
            R.id.ac_waijian_btn_luntai_guige_l_f, R.id.ac_waijian_btn_luntai_guige_l_b,
            R.id.ac_waijian_btn_luntai_guige_r_f, R.id.ac_waijian_btn_luntai_guige_r_b,
            R.id.ac_waijian_btn_buhege, R.id.ac_waijian_btn_zuoqianfang_45_preview,
            R.id.ac_waijian_btn_youhoufang_45_preview,
            R.id.ac_waijian_btn_shibiedaima_preview, R.id.ac_waijian_btn_anquandai_preview,
            R.id.ac_waijian_btn_xingshi_jilu_preview, R.id.ac_waijian_btn_miehuoqi_preview,
            R.id.ac_waijian_btn_chexiangneibu_preview, R.id.ac_waijian_btn_xiaochebiaopai_preview,
            R.id.ac_waijian_btn_xiaoche_biaozhideng_preview, R.id
            .ac_waijian_btn_xiaoche_tingche_zhishipai_preview,
            R.id.ac_waijian_btn_caozong_fuzhu_preview, R.id.ac_waijian_btn_fadongjihao_preview,
            R.id.ac_waijian_btn_fadongjicang_preview, R.id.ac_waijian_btn_yingjichui_preview,
            R.id.ac_waijian_btn_jijiuxiang_preview, R.id.ac_waijian_btn_fangbaosi_preview,
            R.id.ac_waijian_btn_fuzhuzhidong_preview, R.id.ac_waijian_btn_jinji_qieduan_preview,
            R.id.ac_waijian_btn_shoudong_jixie_preview, R.id.ac_waijian_btn_fu_zhidong_taban_preview,
            R.id.ac_waijian_btn_weixian_huowu_preview, R.id.ac_waijian_btn_luntai_guige_l_f_preview,
            R.id.ac_waijian_btn_luntai_guige_l_b_preview, R.id.ac_waijian_btn_luntai_guige_r_f_preview,
            R.id.ac_waijian_btn_luntai_guige_r_b_preview, R.id.ac_waijian_btn_cheliang_zhenghoufang_preview,
            R.id.ac_waijian_btn_xiaochebiaopai_preview2, R.id.ac_waijian_btn_xiaochebiaopai2,
            R.id.ac_waijian_btn_xiaochebiaopai_preview3, R.id.ac_waijian_btn_xiaochebiaopai3,
            R.id.ac_waijian_btn_chexiang_yeyabi_preview, R.id.ac_waijian_btn_chexiang_yeyabi,
            R.id.ac_waijian_btn_buhege2, R.id.ac_waijian_btn_wancheng2
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.title_btn_left:
//                //左上角返回键
//                updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
//                finish();
//                break;
//            case R.id.ac_waijian_btn_zuoqianfang_45:
//                PHOTO_TYPE = "1";
//                //左前方斜视45度-全部
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_youhoufang_45:
//                //右后方斜视45度-全部
//                PHOTO_TYPE = "2";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_shibiedaima:
//                //车辆识别代码-全部
//                PHOTO_TYPE = "3";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_anquandai:
//                //安全带-全部
//                PHOTO_TYPE = "4";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xingshi_jilu:
//                //行驶记录装置
//                //对公路客车、旅游客车、危险货物运输车，校车，
//                // 2013年3月1日起注册登记的未设置乘客站立区的公共汽车、半挂牵引车和总质量大于等于12000kg的货车
//                PHOTO_TYPE = "5";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_miehuoqi:
//                //灭火器
//                //客车、危险货物运输车
//                PHOTO_TYPE = "6";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_chexiangneibu:
//                //车厢内部拍照
////                客车、 校车、厢式 、棚式货车 和挂车
//                PHOTO_TYPE = "7";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_cheliang_zhenghoufang:
//                //车辆正后方
//                // 货车、 挂车、 专项作业车 、校车
//                PHOTO_TYPE = "8";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xiaochebiaopai:
//                //校车标牌(前)正面照片
////                校车
//                PHOTO_TYPE = "9";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xiaochebiaopai_preview2:
//                if (WAIJIAN_PHOTO_PREVIEW_LISTS.get(40) != null) {
//                    Intent intent_preview = new Intent(instances, PhotoPreviewActivity.class);
//                    intent_preview.putExtra("key_integer", 40);
//                    startActivity(intent_preview);
//                }
//                break;
//            case R.id.ac_waijian_btn_xiaochebiaopai2:
//                //校车标牌(前)反面照片
//                PHOTO_TYPE = "40";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xiaochebiaopai_preview3:
//                if (WAIJIAN_PHOTO_PREVIEW_LISTS.get(41) != null) {
//                    Intent intent_preview = new Intent(instances, PhotoPreviewActivity.class);
//                    intent_preview.putExtra("key_integer", 41);
//                    startActivity(intent_preview);
//                }
//                break;
//            case R.id.ac_waijian_btn_xiaochebiaopai3:
//                //校车标牌(后)正面照片
//                PHOTO_TYPE = "41";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xiaoche_biaozhideng:
//                //校车标志灯
//                // 校车
//                PHOTO_TYPE = "10";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_caozong_fuzhu:
//                //操纵辅助装置  残疾人专用汽车
//                PHOTO_TYPE = "11";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_fadongjihao:
//                //发动机号码拍照   除挂车外的其他机动车
//                PHOTO_TYPE = "12";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_fadongjicang:
//                //发动机舱自灭火装置拍照    客车、校车
//                PHOTO_TYPE = "13";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_yingjichui:
//                //应急锤拍照    客车
//                PHOTO_TYPE = "14";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_jijiuxiang:
//                //急救箱拍照   校车
//                PHOTO_TYPE = "15";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_fangbaosi:
//                //防抱死   客车， 校车，货车，专项作业车
//                PHOTO_TYPE = "16";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_fuzhuzhidong:
//                //辅助制动装置拍照   货车 、专项作业货车
//                PHOTO_TYPE = "17";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_jinji_qieduan:
//                //紧急切断装置   危险货物运输车
//                PHOTO_TYPE = "18";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_shoudong_jixie:
//                //手动机械断电开关  客车
//                PHOTO_TYPE = "19";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_fu_zhidong_taban:
//                //副制动踏板拍照  教练车
//                PHOTO_TYPE = "20";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_weixian_huowu:
//                //危险货物运输车标志   危险货物运输车
//                PHOTO_TYPE = "21";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_luntai_guige_l_f:
//                //轮轮胎规格拍照  客车，校车，货车，专项作业车，危险货物运输车
//                //左前轮胎
//                PHOTO_TYPE = "22";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_luntai_guige_l_b:
//                //轮轮胎规格拍照  客车，校车，货车，专项作业车，危险货物运输车
//                //左后轮胎
//                PHOTO_TYPE = "23";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_luntai_guige_r_f:
//                //轮轮胎规格拍照  客车，校车，货车，专项作业车，危险货物运输车
//                //右前轮胎
//                PHOTO_TYPE = "24";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_luntai_guige_r_b:
//                //轮轮胎规格拍照  客车，校车，货车，专项作业车，危险货物运输车
//                //右后轮胎
//                PHOTO_TYPE = "25";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_xiaoche_tingche_zhishipai:
//                //校车停车指示标志牌
//                // 校车
//                PHOTO_TYPE = "26";
//                takePicture();
//                break;
//            case R.id.ac_waijian_btn_chexiang_yeyabi:
//                //车厢液压臂拍照
//                PHOTO_TYPE = "66";
//                takePicture();
//                break;
            case R.id.ac_waijian_btn_wancheng2:
                //外检完成
//                String logStr = "\n" + "点击外检完成按钮---还未发送请求";
//                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                waiJainDataModel.setDetection_ID(String.valueOf(carsInforModel.getID()));
                waiJainDataModel.setGUID(carsInforModel.getGUID());
                waiJainDataModel.setDetectionDevID(CommonUtils.getIMEI(this));
                waiJainDataModel.setBrakeForce(carsInforModel.getBrakeForce());
//                waiJainDataModel.setPlateNO(carsInforModel.getPlateNO());
                waiJainDataModel.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());

                waiJainDataModel.setAirAdmission(carsInforModel.getAirAdmission());

                try {
//                    String name = URLDecoder.decode(BaseApplication.accountModelList.get(0).getUserName(),
//                            "UTF-8");
                    String name = URLDecoder.decode(Login_name, "UTF-8");

                    waiJainDataModel.setAppearanceInspector(name);//检测员名字，汉字
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                waiJainDataModel.setAppearanceNo(BaseApplication.accountModelList.get(0).getIDNumber());

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
//                appendCode2AndValues();

                model.setItemcode(Item_Code);
                model.setLine(Line);
                model.setPlateNO(carsInforModel.getPlateRegion() + carsInforModel.getPlateNO());
                model.setPlateType(carsInforModel.getPlateType());
                model.setPlatformSN(carsInforModel.getPlatformSN());
                model.setTest_times(carsInforModel.getTimes());

                if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
                    if (CommonUtils.isOnline(instances)) {
                        //上传平台的数据
                        waiJainDataModel.setDataType("F1");
                        uploadPlatformData_w(waiJainDataModel);
                        getDataReportData();
                        //视频接口上传数据
                        model.setDetectionItemStartDate(BaseApplication.W_ItemStart_time);
                        model.setDetectionItemEndDate(BaseApplication.W_ItemEnd_time);
                        model.setDetectionID(String.valueOf(carsInforModel.getID()));
                        uploadVideo(model);
                    } else {
                        Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                                .LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
                }
//                finish();
                break;
            case R.id.ac_waijian_btn_buhege2:
                String logStr2 = "\n" + "外检不合格项目按钮点击" +
                        "\n";
                PDALogUtils.createLogFile(BaseApplication
                        .JIANCE_MODE, logStr2.getBytes());
                //不合格项目录入
//                Intent intent = new Intent(WaiJianPhotoActivity.this, DisqualificationActivity.class);
//                intent.putExtra("buhege_flag", 0);//不合格项目跳转 0：外检 1：底盘动态
//                intent.putExtra("WaiJianPhotoActivity_CarsInforModel", carsInforModel);
//                startActivity(intent);
                Intent intent_2 = new Intent(WaiJianPhotoActivity.this, WaiJianResultActivity.class);
                intent_2.putExtra("WaiJianPhotoActivity_CarsInforModel", carsInforModel);
                startActivity(intent_2);
                break;

        }
    }

    // 照相
//    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
// .READ_EXTERNAL_STORAGE})

    File file = null;

    public void takePicture() {
        Log.e("photo", "takePicture---takePicture");
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            String filePath = Environment.getExternalStorageDirectory() + "/images/" + System
//                    .currentTimeMillis() + ".jpg";
            String filePath = Environment.getExternalStorageDirectory() + "/images/" + System
                    .currentTimeMillis() + ".png";
            File outputFile = new File(filePath);
            file = outputFile;
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri contentUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".fileprovider", outputFile);
            picFileFullName = outputFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Toast.makeText(instances, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
            Log.e("tag", "请确认已经插入SD卡");
        }
    }

    String change01;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("photo", "2222222");
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.e(tag, "获取图片成功，path=" + picFileFullName);
                // 照相时的确认
//                getData(new File(picFileFullName));
//                Uri uri = data.getData();
//                picFileFullName = uri.getPath();
                Luban.with(instances)
                        .load(new File(picFileFullName))                     //传人要压缩的图片
//                        .putGear(1)//设置压缩级别
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                CommonUtils.showLoadingDialog(instances, "上传中...");
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                //拍照压缩后上传
                                getData(file, picFileFullName);
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                            }
                        }).launch();    //启动压缩

            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了图像捕获
            } else {
                // 图像捕获失败，提示用户
                Log.e(tag, "拍照失败");
            }
        } else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String realPath = getRealPathFromURI(uri);
                    Log.e(tag, "获取图片成功22，path=" + realPath);
                } else {
                    Log.e(tag, "从相册获取图片失败");
                }
            }
        } else if (resultCode == 0) {
            Log.e(tag, "resultCode===0");
        } else if (resultCode == 917) {
//            旋转后返回，压缩图片，上传
            Log.e(tag, "resultCode===917");
            change01 = data.getStringExtra("change01_path");
            Log.e(tag, "change01===917==" + change01);

            Luban.with(instances)
                    .load(new File(change01))                     //传人要压缩的图片
//                        .putGear(1)//设置压缩级别
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                CommonUtils.showLoadingDialog(instances, "上传中...");
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            Log.e("photo", "rrrrr");
                            //拍照压缩后上传
//                                    getData(file,picFileFullName);
                            getData(file, change01);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            Toast.makeText(instances, "图片压缩出现问题，请重试", Toast.LENGTH_LONG).show();
                        }
                    }).launch();    //启动压缩

        }

    }


    /**
     * This method is used to get real path of file from from uri<br/>
     * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-
     * captured-from-camera
     *
     * @param contentUri
     * @return String
     */
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this
            // method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    private void getData(File file, String file_name) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                getDataUploadPhoto(file, file_name);
            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    //上传照片
    private void getDataUploadPhoto(final File file, final String file_name) {
        Log.e("photo", "6666666");
        UtilsLog.e("getDataUploadPhoto---url==" + SharedPreferencesUtils.getFileIP(instances) + UPLOAD_PHOTO);
        OkGo.<String>post(SharedPreferencesUtils.getFileIP(instances) + UPLOAD_PHOTO).tag(instances)
//                .params("PHOTO_TYPE", PHOTO_TYPE)
                //guid
                .params("QueueID", carsInforModel.getQueueID())
                //guid
                .params("GUID", carsInforModel.getGUID())
                //次数
                .params("Times", carsInforModel.getTimes())
                //PlatformSN
                .params("PlatformSN", carsInforModel.getPlatformSN())
                //SN
                .params("SN", carsInforModel.getSN())
                //拍摄时间
                .params("PhotoDate", DateUtil.currentTime2())
                //照片类型-拍的哪里的照片，位置
//                .params("PhotoType", getPlatformCode(PHOTO_TYPE))
                .params("PhotoType", PHOTO_TYPE)

                //车辆类型
                .params("Type", carsInforModel.getType())
                //车牌号码，两个拼起来的
                .params("CarNO", carNo)
                //号牌类型
                .params("PlateType", carsInforModel.getPlateType())
                //拍照code F1表示外检
                .params("ItemCode", "F1")
                //外检车道，检测线代号
                .params("Line", waijian_chedao)
                //车辆识别代码
                .params("VIN", carsInforModel.getVIN())
                .params("FileType", "jpg")
//                .params("FileType", FileUtils.getExtensionName(file.getName()))
                //照片
                .params("zp", file)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataUploadPhoto-result==" + result);

                        String logStr = "\n" + "外检上传照片--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getFileIP(instances) +
                                UPLOAD_PHOTO + "\n" + "result:" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
//                        UtilsLog.e("response.headers()-result==" + response.headers());
//                        photo_result(PHOTO_TYPE);
//                        if (PHOTO_TYPE != null) {
//                            WAIJIAN_PHOTO_PREVIEW_LISTS.put(Integer.parseInt(PHOTO_TYPE), file);
//                        }
                        WAIJIAN_PHOTO_PREVIEW_LISTS.put(Integer.parseInt(PHOTO_TYPE), file);
                        if (!TextUtils.isEmpty(result) && !"[[]]".equals(result) && !"[{}]".equals(result)
                                && !"[]".equals(result)) {
                            String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\",
                                    "");
                            UtilsLog.e("getDataUploadPhoto-newResult==" + newResult);
                            if ("ok".equalsIgnoreCase(newResult)) {
                                try {
                                    if (!TextUtils.isEmpty(file_name)) {
                                        CommonUtils.DeleteImage(instances, file_name);
                                        String[] str_photo_path = file_name.split("Pictures");

                                        UtilsLog.e("str_photo_path[0]===" + str_photo_path[0]);
                                        UtilsLog.e("str_photo_path[1]===" + str_photo_path[1]);
                                        net.com.mvp.ac.commons.FileUtils.deleteDirectory(Environment
                                                .getExternalStorageDirectory() + "/Pictures" +
                                                str_photo_path[1]);
                                    } else {
                                        String logStr2 = "\n" + "外检上传照片--没有获取到即将删除的图片路径" + "\n";
                                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE,
                                                logStr2.getBytes());
                                        UtilsLog.e("getDataUploadPhoto==没有获取到即将删除的图片路径");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(instances, "上传成功", Toast.LENGTH_LONG).show();
                                typeModel.setColor(1);
                                typeModelListAdapter.set(TypeModel_Position, typeModel);
                                contentAdapter2.notifyDataSetChanged();
                            } else {
                                String logStr2 = "\n" + "外检上传照片--失败" +
                                        "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                        UPLOAD_PHOTO + "\n" + "result:" + response.body();
                                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr2.getBytes());
                                Toast.makeText(instances, "上传失败，请重新拍照", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            String logStr3 = "\n" + "外检上传照片--失败" +
                                    "\n" + "data::" + "没有数据" + "\n" + "result:" + response.body();
                            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr3.getBytes());
                            UtilsLog.e("getDataUploadPhoto-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataUploadPhoto-onError==" + response.body());
                        UtilsLog.e("getDataUploadPhoto-onError==" + response.getException());
                        String logStr = "\n" + "外检上传照片-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getFileIP(instances) +
                                UPLOAD_PHOTO + "\n" + "result:" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);

                        Toast.makeText(instances, "上传照片失败，请重试", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
        mCvCountupViewTest1.stop();
    }

    //拍照上传后，改变view状态--未拍照，已拍照
    private void photo_result(String state) {
        int photo_state = Integer.parseInt(state);
        switch (photo_state) {
            case 1:
                acWaijianTxtZuoqianfang45.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtZuoqianfang45.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 2:
                acWaijianTxtYouhoufang45.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtYouhoufang45.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 3:
                acWaijianTxtShibiedaima.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtShibiedaima.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 4:
                acWaijianTxtAnquandai.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtAnquandai.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 5:
                acWaijianTxtXingshiJilu.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtXingshiJilu.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 6:
                acWaijianTxtMiehuoqi.setText(getResources().getString(R.string.ac_waijian_paizhao_yipaizhao));
                acWaijianTxtMiehuoqi.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 7:
                acWaijianTxtChexiangneibu.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtChexiangneibu.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 8:
                acWaijianTxtCheliangZhenghoufang.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtCheliangZhenghoufang.setTextColor(getResources().getColor(R.color
                        .textcolor_green));
                break;
            case 9:
                acWaijianTxtXiaochebiaopai.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtXiaochebiaopai.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 10:
                acWaijianTxtXiaocheBiaozhideng.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtXiaocheBiaozhideng.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 11:
                acWaijianTxtCaozongFuzhu.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtCaozongFuzhu.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 12:
                acWaijianTxtFadongjihao.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtFadongjihao.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 13:
                acWaijianTxtFadongjicang.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtFadongjicang.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 14:
                acWaijianTxtYingjichui.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtYingjichui.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 15:
                acWaijianTxtJijiuxiang.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtJijiuxiang.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 16:
                acWaijianTxtFangbaosi.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtFangbaosi.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 17:
                acWaijianTxtFuzhuzhidong.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtFuzhuzhidong.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 18:
                acWaijianTxtJinjiQieduan.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtJinjiQieduan.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 19:
                acWaijianTxtShoudongJixie.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtShoudongJixie.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 20:
                acWaijianTxtFuZhidongTaban.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtFuZhidongTaban.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 21:
                acWaijianTxtWeixianHuowu.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtWeixianHuowu.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 22:
                acWaijianTxtLuntaiGuigeLF.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtLuntaiGuigeLF.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 23:
                acWaijianTxtLuntaiGuigeLB.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtLuntaiGuigeLB.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 24:
                acWaijianTxtLuntaiGuigeRF.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtLuntaiGuigeRF.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 25:
                acWaijianTxtLuntaiGuigeRB.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtLuntaiGuigeRB.setTextColor(getResources().getColor(R.color.textcolor_green));
                break;
            case 26:
                acWaijianTxtXiaocheTingcheZhishipai.setText(getResources().getString(R.string
                        .ac_waijian_paizhao_yipaizhao));
                acWaijianTxtXiaocheTingcheZhishipai.setTextColor(getResources().getColor(R.color
                        .textcolor_green));
                break;
        }
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
        UtilsLog.e("getDataWaiJainEnd---jsonStr==" + getDataWaiJainEnd_upjson_url);
        UtilsLog.e("getDataWaiJainEnd---url==" + SharedPreferencesUtils.getIP(this) + ITEM_END);
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
                        UtilsLog.e("getDataWaiJainEnd-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataWaiJainEnd-newResult==" + newResult);
                        String logStr = "\n" + "外检项目结束--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) + "" +
                                ITEM_END + "\n" + "JSON::" + getDataWaiJainEnd_upjson_url +
                                "\n" + "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        Toast.makeText(instances, "外检项目完成", Toast
                                .LENGTH_LONG).show();
                        EndAllItem(String.valueOf(carsInforModel.getID()));
                        updateCarStatusInfor(1);

//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //项目总结束
//                                updateCarStatusInfor(1);
//                                EndAllItem(String.valueOf(carsInforModel.getID()));
//                            }
//                        }, 300);//后执行Runnable中的run方法

                        if ("ok".equalsIgnoreCase(newResult)) {
//                            Toast.makeText(instances, "外检项目完成", Toast
//                                    .LENGTH_LONG).show();
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //项目总结束
//                                    updateCarStatusInfor(1);
//                                    EndAllItem(String.valueOf(carsInforModel.getID()));
//                                }
//                            }, 300);//后执行Runnable中的run方法
                        } else {
                            showDialog("外检平台下线失败，请尝试重新下线");
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainEnd-onError==" + response.body());
                        String logStr = "\n" + "外检项目结束-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_END + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    //外检上传平台数据
    String upjson_url;

    private void uploadPlatformData_w(WaiJainDataModel waiJainDataModel) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(waiJainDataModel);
        upjson_url = "[{" + jsonStr.substring(1, jsonStr.length() - 1) + "," + appendCode2AndValues
                () + "}]";
        UtilsLog.e("uploadPlatformData_w---jsonStr==" + upjson_url);
        UtilsLog.e("uploadPlatformData_w---url==" + SharedPreferencesUtils.getIP(this) + PLAT_FORM_DATA);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + PLAT_FORM_DATA).tag(this)
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
                        UtilsLog.e("uploadPlatformData_w-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("uploadPlatformData_w-newResult==" + newResult);
                        if ("ok".equalsIgnoreCase(newResult)) {
                            String logStr = "\n" + "外检上传平台数据--请求成功" +
                                    "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                    PLAT_FORM_DATA + "\n" + "JSON::" + upjson_url +
                                    "\n" + "result::" + result;
                            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /**
                                     *要执行的操作
                                     */
                                    getDataWaiJainEnd("");
                                }
                            }, 1000);//后执行Runnable中的run方法


                        } else {
                            String logStr = "\n" + "外检上传平台数据--项目结束失败" +
                                    "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                    PLAT_FORM_DATA + "\n" + "JSON::" + upjson_url +
                                    "\n" + "result::" + result;
                            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                            showDialog("上传平台数据失败，请重试，如果还未成功请联系管理员");
//                            Toast.makeText(WaiJianPhotoActivity.this, "项目结束失败", Toast.LENGTH_LONG)
//                                    .show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("uploadPlatformData_w-onError==" + response.body());
                        String logStr = "\n" + "外检上传平台数据-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                PLAT_FORM_DATA + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("上传平台数据失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    /***
     * 获取图片类型PlatformCode上传用
     * @param typeFlag PHOTO_TYPE
     * **/
    private String getPlatformCode(String typeFlag) {
        String code = "";
        if (photoTypeModelList != null && photoTypeModelList.size() > 0) {
            Iterator iterList = photoTypeModelList.iterator();//List接口实现了Iterable接口
            //循环list
            while (iterList.hasNext()) {
                PhotoTypeModel photoTypeModel = (PhotoTypeModel) iterList.next();
                if (typeFlag.equals(photoTypeModel.getCode())) {
                    UtilsLog.e(photoTypeModel.toString());
                    code = photoTypeModel.getPlatformCode();
                    return code;
                }
            }
        }
        return code;
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

    String myjson;

    private void getDataReportData() {
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + REPORTED_DATA);
        myjson = reportedJsonStr();
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
                        String logStr = "\n" + "外检上传自己后台数据--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "JSON::" + myjson +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                        if ("\"ok\"".equals(result)) {
//                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
//                            instances.finish();
                        } else {
                            showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        String logStr = "\n" + "外检上传自己后台数据-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                REPORTED_DATA + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
//                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
//                                .LENGTH_LONG).show();
                        showDialog("上传后台数据失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    //拼装json  --Code2
    private String appendCode2AndValues() {
        String Code2JsonStr = "";
        String result = "";
        if (myChoiceModelList != null && myChoiceModelList.size() > 0) {
            for (int i = 0; i < myChoiceModelList.size(); i++) {
                Code2JsonStr = Code2JsonStr + "\"" + myChoiceModelList.get(i).getCode_key() + "\""
                        + ":" + "\"" + myChoiceModelList.get(i).getCode_values() + "\"" + ",";
            }
//            UtilsLog.e("Code2JsonStr==" + Code2JsonStr);
            UtilsLog.e("Code2JsonStr=22=" + Code2JsonStr.substring(0, Code2JsonStr.length() - 1));
        }
        if (Code2JsonStr.length() > 0) {
            result = Code2JsonStr.substring(0, Code2JsonStr.length() - 1);
        }
        return result;
    }

    private void getData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                getMyCarItems();
            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(instances, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }


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

        Gson gson = new Gson();
        String json_str = gson.toJson(model);
        UtilsLog.e("getMyCarItems---json_str==" + json_str);
        UtilsLog.e("getMyCarItems---url==" + SharedPreferencesUtils.getIP(this) + GET_MY_ITEM);

//        String logStr = "\n" + "外检--获取需要检测的人工检验项目接口--请求" +
//                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
//                GET_MY_ITEM + "\n" + "JSON::" + json_str + "\n";
//        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
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
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getMyCarItems-newResult==" + newResult);
//                        String logStrf = "\n" + "外检--获取需要检测的人工检验项目接口--请求成功" +
//                                "\n" + "result::" + response.body();
//                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());

                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {
                            mycar_checkItemlList = new ArrayList<CheckItemModel>();
                            mycar_checkItemlList = JsonUtil.stringToArray(newResult,
                                    CheckItemModel[].class);
                            if (mycar_checkItemlList != null && mycar_checkItemlList.size() > 0) {

                                //给是否拍照列表赋值，初始化
                                for (int i = 0; i < mycar_checkItemlList.size(); i++) {
                                    IsTakePhoto.add(false);
                                }

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
//
                                for (int i = 0; i < myChoiceModelList.size(); i++) {
//
                                    String Code2 = myChoiceModelList.get(i).getCode2().toString().trim();
                                    for (int j = 0; j < Item_2Model_list.size(); j++) {
                                        String code2_my = Item_2Model_list.get(j).getCode2().toString()
                                                .trim();
                                        if (Code2.equals(code2_my)) {
                                            myChoiceModelList.get(i).setCode_values("1");
                                        }
                                    }
                                }
                            } else {
                                UtilsLog.e("getMyCarItems-Result==没有获取到当前车辆检测到的项目");
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
                        String logStrf = "\n" + "外检--获取需要检测的人工检验项目接口--请求失败--error" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());
                    }
                });
    }


    //视频接口
    private void uploadVideo(VideoModel model) {

//        BaseApplication.W_ItemStart_time = DateUtil.currentTime2();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(model);
        UtilsLog.e("uploadVideo---jsonStr==" + jsonStr);
        UtilsLog.e("uploadVideo---url==" + SharedPreferencesUtils.getIP(this) + VIDEO_ITEM);
        String logStr = "\n" + "外检--uploadVideo--请求" +
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
                        String logStrf = "\n" + "外检--uploadVideo--请求成功" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStrf.getBytes());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("uploadPlatformData_w-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                        String logStr = "\n" + "外检--uploadVideo-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                VIDEO_ITEM + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                    }
                });
    }

    //获取某辆车的外检拍照的项目
    // PlateType,CarType,Usage
    String json_str_getOutPhotoItem;

    private void getOutPhotoItem() {
        OutPhotoItem model = new OutPhotoItem();
        model.setCarType(carsInforModel.getType());
        model.setPlateType(carsInforModel.getPlateType());
        model.setUsage(carsInforModel.getUsage());
        model.setPlatformSN(carsInforModel.getPlatformSN());
        Gson gson = new Gson();
        json_str_getOutPhotoItem = gson.toJson(model);
        UtilsLog.e("getOutPhotoItem---json_str==" + json_str_getOutPhotoItem);
        UtilsLog.e("getOutPhotoItem---url==" + SharedPreferencesUtils.getIP(this) + WAI_JIAN_PHOTO_ITEM);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + WAI_JIAN_PHOTO_ITEM).tag(this)
                .upJson(json_str_getOutPhotoItem)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        BaseApplication.msg = result;
//                        UtilsLog.e("getOutPhotoItem-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getOutPhotoItem-newResult==" + newResult);

                        String logStr = "\n" + "外检--获取某辆车的外检拍照的项目--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                WAI_JIAN_PHOTO_ITEM + "\n" + "JSON::" + json_str_getOutPhotoItem;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {

                            typeModelList = JsonUtil.stringToArray(newResult,
                                    TypeModel[].class);
                            if (typeModelList != null && typeModelList.size() > 0) {
                                TypeModel typeModel = null;
                                for (int i = 0; i < typeModelList.size(); i++) {
                                    typeModel = new TypeModel();
                                    typeModel.setName(typeModelList.get(i).getName());
                                    typeModel.setCode(typeModelList.get(i).getCode());
                                    typeModelListAdapter.add(typeModel);
                                }
                                contentAdapter2 = new ContentAdapter2(instances, typeModelListAdapter,
                                        mListener);
                                //实例化ContentAdapter2类，并传入实现类
                                acWaijianPhotoListview.setAdapter(contentAdapter2);
                                setListViewHeightBasedONChildren(acWaijianPhotoListview);
                                acWaijianPhotoListview.setOnItemClickListener(instances);
                            }
                        } else {
                            String logStr2 = "\n" + "外检--获取某辆车的外检拍照的项目--失败" +
                                    "\n" + "result::" + result;
                            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr2.getBytes());

                            BaseApplication.msg = result;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        BaseApplication.msg = response.body() + "==" + response.message();
                        UtilsLog.e("getOutPhotoItem-onError==" + response.body());
                        String logStr = "\n" + "外检--获取某辆车的外检拍照的项目-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                WAI_JIAN_PHOTO_ITEM + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
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
                        String logStr = "\n" + "外检项目总结束:总结束:总结束--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_ALL_END + detectionID + "/DetectionID" +
                                "\n" + "result::" + result;
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        finish();

//                        if ("\"ok\"".equals(result)) {
//
//                            updateCarStatusInfor(1);
//                            Toast.makeText(instances, "外检项目完成", Toast
//                                    .LENGTH_LONG).show();
//
//                        } else {
//                            showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        String logStr = "\n" + "外检项目总结束:总结束:总结束-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                ITEM_ALL_END + detectionID + "/DetectionID" +
                                "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());
                        UtilsLog.e("EndAllItem-onError==" + response.body());
                        UtilsLog.e("EndAllItem-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        showDialog("车辆下线失败，请重试，如果还未成功请联系管理员");
                    }
                });
    }

    public static void setListViewHeightBasedONChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

    // 模拟listview中加载的数据
    private String[] CONTENTS = {"北京", "上海", "广州", "深圳", "苏州",
            "南京", "武汉", "长沙", "杭州"};
    //    private List<String> contentList;
    private List<TypeModel> typeModelList;
    private List<TypeModel> typeModelListAdapter;
    private ContentAdapter2 contentAdapter2;

    private void init() {
        typeModelList = new ArrayList<>();
        typeModelListAdapter = new ArrayList<>();
//        TypeModel typeModel = null;
//        for (int i = 0; i < CONTENTS.length; i++) {
//            typeModel=new TypeModel();
//            typeModel.setName(CONTENTS[i]);
//            typeModelList.add(typeModel);
//        }
//        contentAdapter2 = new ContentAdapter2(instances, typeModelList, mListener);
//        //实例化ContentAdapter2类，并传入实现类
//        acWaijianPhotoListview.setAdapter(contentAdapter2);
//
//        acWaijianPhotoListview.setOnItemClickListener(instances);
    }

    //响应item点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
//        Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
//                Toast.LENGTH_SHORT).show();
    }

    /**
     * 实现类，响应按钮点击事件
     */
    TypeModel typeModel;
    int TypeModel_Position = 0;
    private String img_imageUri_path;
    private ContentAdapter2.MyClickListener mListener = new ContentAdapter2.MyClickListener() {
        @Override
        public void myOnClick(int position, View v, int flag) {
            PHOTO_TYPE = typeModelListAdapter.get(position).getCode();
            TypeModel_Position = position;
            if (flag == 0) {
                //跳转到图片预览界面
                if (WAIJIAN_PHOTO_PREVIEW_LISTS.get(Integer.parseInt(PHOTO_TYPE)) != null) {
                    Intent intent_preview = new Intent(instances, PhotoPreviewActivity.class);
                    intent_preview.putExtra("key_integer", Integer.parseInt(PHOTO_TYPE));
                    startActivity(intent_preview);
                }
            } else if (flag == 1) {
//                takePicture();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    takePicture();
//                } else {
                Log.e("photo", "takePhoto---takePhoto");
                takePhoto = getTakePhoto();
//                    configCompress(takePhoto);
//                    File file = new File(Environment.getExternalStorageDirectory(), "/temp_my_app/" + System
//                            .currentTimeMillis() + ".jpg");
                File file = new File(Environment.getExternalStorageDirectory(), "/temp_my_app/" + System
                        .currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                imageUri = Uri.fromFile(file);
                UtilsLog.e("photo", "WAIJIAN_PHOTO_imageUri=7777=" + imageUri);
                img_imageUri_path = imageUri.getPath();
                UtilsLog.e("photo", "WAIJIAN_PHOTO_imageUri=888=" + imageUri.getPath());
                takePhoto.onPickFromCapture(imageUri);
//                }
                typeModel = new TypeModel();
                typeModel.setName(typeModelListAdapter.get(position).getName());
                typeModel.setCode(typeModelListAdapter.get(position).getCode());
            }

//            Toast.makeText(
//                    Main2Activity.this,
//                    "listview的内部的按钮被点击了！，位置是-->" + position + ",内容是-->"
//                            + typeModelList.get(position).getMsg() + ",flag是-->" + flag, Toast.LENGTH_SHORT)
//                    .show();
        }
    };

    /**
     * 更新车辆状态
     *
     * @param flag flag:0 未检测
     * flag:1 项目开始
     * flag:2 项目结束
     * flag:917  中途退出的情况
     */
    String jsonStr22;

    public void updateCarStatusInfor(int flag) {
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
        jsonStr22 = gson.toJson(model);
        UtilsLog.e("updateCarStatusInfor---jsonStr==" + jsonStr22);
        UtilsLog.e("updateCarStatusInfor---url==" + SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS);
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + UPDATE_CAR_STATUS)
                .tag(this)
                .upJson(jsonStr22)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().trim();
                        UtilsLog.e("updateCarStatusInfor-result==" + result);
                        UtilsLog.e("updateCarStatusInfor-result=22=" + result.substring(1, result.length() -
                                1));
                        String logStr = "\n" + "外检项目更新状态--请求成功" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "JSON::" + jsonStr22 + "\n" + "result::" +
                                response.body();
                        PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataWaiJainStart-onError==" + response.body());
                        String logStr = "\n" + "外检项目更新状态-onError-失败" +
                                "\n" + "URL::" + SharedPreferencesUtils.getIP(instances) +
                                UPDATE_CAR_STATUS + "\n" + "result::" + response.body();
                        PDALogUtils.createLogFile(BaseApplication
                                .JIANCE_MODE, logStr.getBytes());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, true);
        int maxSize = 102400;
        int width = 800;
        int height = 1024;
        boolean showProgressBar = false;
        boolean enableRawFile = false;
        CompressConfig config;


        config = new CompressConfig.Builder()
//                .setMaxSize(maxSize)
//                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
//        LubanOptions option = new LubanOptions.Builder()
//
////                .setMaxHeight(height)
////                .setMaxWidth(width)
//                .setMaxSize(maxSize)
//                .create();
//        config = CompressConfig.ofLuban(option);
//        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            String logStr = "\n" + "外检--返回键-屏幕底部" + "\n";
            PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, logStr.getBytes());

//            updateCarStatusInfor(ITEM_MIDDLE_EXIT_CODE);
            finish();
            return true;
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
