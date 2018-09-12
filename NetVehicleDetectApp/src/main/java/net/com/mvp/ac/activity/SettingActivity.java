package net.com.mvp.ac.activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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

import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.MyAccountModeListAdapter;
import net.com.mvp.ac.adapter.MyProvincesListAdapter;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.IPPortModel;
import net.com.mvp.ac.model.SettingModel;
import net.com.mvp.ac.wuxi.blesample.MainActivity;
import net.com.mvp.ac.wuxi.finger.FingerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 系统设置
 * @time 2017/6/13
 */
public class SettingActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.ac_setting_et_ip)
    EditText acSettingEtIp;
    @BindView(R.id.ac_setting_et_port)
    EditText acSettingEtPort;
    @BindView(R.id.ac_setting_et_jiqima)
    EditText acSettingEtJiqima;
    @BindView(R.id.ac_setting_et_shouquanma)
    EditText acSettingEtShouquanma;
    @BindView(R.id.ac_setting_txt_shujuku)
    TextView acSettingTxtShujuku;
    @BindView(R.id.ac_setting_txt_jiane_mode)
    TextView acSettingTxtJianeMode;
    @BindView(R.id.ac_setting_txt_zhaopianshuiyin)
    TextView acSettingTxtZhaopianshuiyin;
    @BindView(R.id.ac_setting_txt_moren)
    TextView acSettingTxtMoren;
    @BindView(R.id.ac_setting_txt_bianma)
    TextView acSettingTxtBianma;
    @BindView(R.id.ac_setting_txt_shijian)
    TextView acSettingTxtShijian;
    @BindView(R.id.ac_setting_txt_chepai_left)
    TextView acSettingTxtChepaiLeft;
    @BindView(R.id.ac_setting_txt_chepai_right)
    TextView acSettingTxtChepaiRight;
    @BindView(R.id.ac_set_btn_cancle)
    Button acSetBtnCancle;
    @BindView(R.id.ac_set_view_m)
    TextView acSetViewM;
    @BindView(R.id.ac_set_btn_save)
    Button acSetBtnSave;
    @BindView(R.id.ac_setting_et_ip_file)
    EditText acSettingEtIpFile;
    @BindView(R.id.ac_setting_et_port_file)
    EditText acSettingEtPortFile;
    @BindView(R.id.ac_setting_txt_paizhao)
    TextView acSettingTxtPaizhao;
    @BindView(R.id.ac_setting_et_ip_webservices)
    EditText acSettingEtIpWebservices;
    @BindView(R.id.ac_setting_et_port_webservices)
    EditText acSettingEtPortWebservices;
    @BindView(R.id.ac_set_btn_obd)
    Button acSetBtnObd;
    @BindView(R.id.ac_set_btn_finger)
    Button acSetBtnFinger;

    //popuwindow
    private View popu_view;
    private View popu_provinces_view;
    private LinearLayout parent22;
    private RelativeLayout parent_view;
    private PopupWindow pop = null;
    private PopupWindow pop_provinces = null;
    private ListView listview_modes;
    private ListView listview_provinces;
    private List<String> strModes = new ArrayList<String>();
    private List<String> yes_no_Modes = new ArrayList<String>();
    private MyAccountModeListAdapter MyAccountModeListAdapter;
    private MyProvincesListAdapter myProAdapter;
    private String CURRENT_SELECT_TYPE = "否";
    private int SELECT_TYPE = 0;//:0：检测模式，，1：是否
    private int view_type = 0;//当前是第几个下拉列表
    private List<String> provinces_list = new ArrayList<String>();//省会简称列表
    private List<String> letter_list = new ArrayList<String>();//字母列表
    SettingModel settingModel = new SettingModel();//保存系统设置的model
    BluetoothManager bluetoothManager;
    Context context;
    private BluetoothAdapter defaultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        if (SharedPreferencesUtils.getIPModel(SettingActivity.this) != null) {
            UtilsLog.e("==" + SharedPreferencesUtils.getIPModel(this).toString());
        } else {
            UtilsLog.e("=1111111111111111=");
        }

        setBackBtn();
        setTopTitle(R.string.title_activity_setting);

        initData();
        pop_account();
        pop_provinces();

        UtilsLog.e("是否自动刷新==" + SharedPreferencesUtils.getAutoRefresh(this));
        if (SharedPreferencesUtils.getAutoRefresh(this)) {
            acSettingTxtMoren.setText("是");
        } else {
            acSettingTxtMoren.setText("否");
        }
        UtilsLog.e("是否裁剪图片==" + SharedPreferencesUtils.getPhotoClipping(this));
        if (SharedPreferencesUtils.getPhotoClipping(this)) {
            acSettingTxtPaizhao.setText("是");
        } else {
            acSettingTxtPaizhao.setText("否");
        }
        context=this;
//        BluetoothManager bluetoothManager=(BluetoothManager) context.getService(Context.BLUETOOTH_SERVICE);

//        if (defaultAdapter.getScanMode()!=defaultAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
//        Intent intent =new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,200);
//        startActivity(intent);

//        chackBluetooth();
    }

    @OnClick({R.id.ac_setting_txt_shujuku, R.id.ac_setting_txt_jiane_mode,
            R.id.ac_setting_txt_zhaopianshuiyin, R.id.ac_setting_txt_moren,
            R.id.ac_setting_txt_bianma, R.id.ac_setting_txt_shijian,
            R.id.ac_setting_txt_chepai_left, R.id.ac_setting_txt_chepai_right,
            R.id.ac_set_btn_cancle, R.id.ac_set_btn_save, R.id.ac_set_btn_finger,
            R.id.ac_setting_txt_paizhao, R.id.ac_set_btn_obd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_set_btn_finger:
                //指纹测试
                Intent i22 = new Intent(this, FingerActivity.class);
                startActivity(i22);
                break;
            case R.id.ac_set_btn_obd:
                //OBD测试
                Intent intent_obd = new Intent(this, MainActivity.class);
                startActivity(intent_obd);


//                getBondBluetooth();

                break;
            case R.id.ac_setting_txt_shujuku:
                view_type = 0;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtShujuku, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_jiane_mode:
//                pop.showAsDropDown(acSettingTxtJianeMode);
                view_type = 1;
                SELECT_TYPE = 0;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtJianeMode, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_zhaopianshuiyin:
                view_type = 2;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtZhaopianshuiyin, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_moren:
                view_type = 3;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtMoren, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_bianma:
                view_type = 4;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtBianma, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_shijian:
                view_type = 5;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtShijian, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_paizhao:
                //是否裁剪图片
                view_type = 6;
                SELECT_TYPE = 1;
                popuwindow_type();
                pop.showAtLocation(acSettingTxtPaizhao, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ac_setting_txt_chepai_left:
                for (int i = 0; i < Constants.PROVINCE_JIANCHEN.length; i++) {
                    provinces_list.add(i, Constants.PROVINCE_JIANCHEN[i]);
                }
                myProAdapter = new MyProvincesListAdapter(this, provinces_list);
                listview_provinces.setAdapter(myProAdapter);
                pop_provinces.showAtLocation(acSettingTxtChepaiLeft, Gravity.LEFT, 0, 0);
                listview_provinces.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acSettingTxtChepaiLeft.setText(provinces_list.get(position));
                        settingModel.setChepai_left(provinces_list.get(position));
                        pop_provinces.dismiss();
                    }
                });
                break;
            case R.id.ac_setting_txt_chepai_right:
                for (int i = 0; i < Constants.LETTER_TABLE.length; i++) {
                    letter_list.add(i, Constants.LETTER_TABLE[i]);
                }
                myProAdapter = new MyProvincesListAdapter(this, letter_list);
                listview_provinces.setAdapter(myProAdapter);
                pop_provinces.showAtLocation(acSettingTxtChepaiLeft, Gravity.RIGHT, 0, 0);
                listview_provinces.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        acSettingTxtChepaiRight.setText(letter_list.get(position));
                        settingModel.setChepai_left(letter_list.get(position));
                        pop_provinces.dismiss();
                    }
                });
                break;
            case R.id.ac_set_btn_cancle:
                finish();
                break;
            case R.id.ac_set_btn_save:

                String WebservicesIp = acSettingEtIpWebservices.getText().toString().trim();
                String WebservicesAddress = acSettingEtPortWebservices.getText().toString().trim();
                SharedPreferencesUtils.setWebServicesIP(this, WebservicesIp);
                SharedPreferencesUtils.setWebServicesAddress(this, WebservicesAddress);

                String strIp = null;//ip
                String strPort = null;//端口号
                String file_strIp = null;//上传文件ip
                String file_strPort = null;//上传文件端口号
                String strJiqima = null;//机器码
                String strShouquanma = null;//授权码

                String photo_clip = "否";//是否裁剪图片
                photo_clip = acSettingTxtPaizhao.getText().toString().trim();

                String IsAutoRefresh = "是";//是否自动刷新
                IsAutoRefresh = acSettingTxtMoren.getText().toString().trim();

                strIp = acSettingEtIp.getText().toString().trim();
                strPort = acSettingEtPort.getText().toString().trim();
                file_strIp = acSettingEtIpFile.getText().toString().trim();
                file_strPort = acSettingEtPortFile.getText().toString().trim();
                strJiqima = acSettingEtJiqima.getText().toString().trim();
                strShouquanma = acSettingEtShouquanma.getText().toString().trim();
                if (!TextUtils.isEmpty(strIp) && !TextUtils.isEmpty(strPort) && !TextUtils.isEmpty
                        (file_strIp) && !TextUtils.isEmpty(file_strPort)) {
                    //端口号与IP地址不为空的前提下验证授权码
                    String ip_port = BASE_URL_front + strIp + ":" + strPort;
                    String file_ip_port = BASE_URL_front + file_strIp + ":" + file_strPort;
                    IPPortModel model = new IPPortModel();
                    model.setIp(strIp);
                    model.setIp_port(strPort);
                    model.setFile_ip(file_strIp);
                    model.setFile_ip_port(file_strPort);
                    SharedPreferencesUtils.setIPModel(this, model);
//                    UtilsLog.e(SharedPreferencesUtils.getIPModel(this).toString());
                    SharedPreferencesUtils.setIP(this, ip_port);//一般请求接口的ip
                    SharedPreferencesUtils.setFileIP(this, file_ip_port);//上传图片文件接口的ip

                    //是否自动刷新数据
                    if ("是".equals(IsAutoRefresh)) {
                        SharedPreferencesUtils.setAutoRefresh(this, true);
                    } else {
                        SharedPreferencesUtils.setAutoRefresh(this, false);
                    }

                    //是否裁剪照片
                    if ("是".equals(photo_clip)) {
                        SharedPreferencesUtils.setPhotoClipping(this, true);
                    } else {
                        SharedPreferencesUtils.setPhotoClipping(this, false);
                    }
                    finish();
                } else {
                    Toast.makeText(this, R.string.activity_setting_ip_null, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void initData() {
        for (int i = 0; i < Constants.MY_ACCOUNT_MODEL_LIST.length; i++) {
            strModes.add(i, Constants.MY_ACCOUNT_MODEL_LIST[i]);
        }
        for (int i = 0; i < Constants.YES_NO.length; i++) {
            yes_no_Modes.add(i, Constants.YES_NO[i]);
        }
        acSettingEtJiqima.setText(CommonUtils.getIMEI(this));
        if (SharedPreferencesUtils.getIPModel(SettingActivity.this) != null) {
            acSettingEtIp.setText(SharedPreferencesUtils.getIPModel(this).getIp());
            acSettingEtPort.setText(SharedPreferencesUtils.getIPModel(this).getIp_port());
            acSettingEtIpFile.setText(SharedPreferencesUtils.getIPModel(this).getFile_ip());
            acSettingEtPortFile.setText(SharedPreferencesUtils.getIPModel(this).getFile_ip_port());
//            UtilsLog.e(SharedPreferencesUtils.getIPModel(this).toString());
        }
    }

    private void pop_account() {
        pop = new PopupWindow(this);
        popu_view = getLayoutInflater().inflate(R.layout.item_setting_popupwindows,
                null);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(popu_view);
        listview_modes = (ListView) popu_view.findViewById(R.id.setting_listview);
    }

    private void pop_provinces() {
        pop_provinces = new PopupWindow(this);
        popu_provinces_view = getLayoutInflater().inflate(R.layout.item_setting_province_letter,
                null);
        pop_provinces.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_provinces.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_provinces.setBackgroundDrawable(new BitmapDrawable());
        pop_provinces.setFocusable(true);
        pop_provinces.setOutsideTouchable(true);
        pop_provinces.setContentView(popu_provinces_view);
        parent22 = (LinearLayout) popu_provinces_view.findViewById(R.id.setting_provinces_parent);
        listview_provinces = (ListView) popu_provinces_view.findViewById(R.id.setting_provinces_listview2);
        parent22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop_provinces.dismiss();
            }
        });
    }

    private void popuwindow_type() {
        if (SELECT_TYPE == 0) {
            //检测模式
            MyAccountModeListAdapter = new MyAccountModeListAdapter(this, strModes);
        } else {
            //是否
            MyAccountModeListAdapter = new MyAccountModeListAdapter(this, yes_no_Modes);
        }
        listview_modes.setAdapter(MyAccountModeListAdapter);
        listview_modes.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (SELECT_TYPE) {
            case 0:
                //检测模式
                CURRENT_SELECT_TYPE = strModes.get(position);
                break;
            case 1:
                //是否
                CURRENT_SELECT_TYPE = yes_no_Modes.get(position);
                break;
        }
        switch (view_type) {
            case 0:
                settingModel.setShuuku_tiaoshi_xinxi(CURRENT_SELECT_TYPE);
                acSettingTxtShujuku.setText(CURRENT_SELECT_TYPE);
                break;
            case 1:
                settingModel.setJiance_mode(CURRENT_SELECT_TYPE);
                acSettingTxtJianeMode.setText(CURRENT_SELECT_TYPE);
                break;
            case 2:
                settingModel.setZhaopian_shuiyin(CURRENT_SELECT_TYPE);
                acSettingTxtZhaopianshuiyin.setText(CURRENT_SELECT_TYPE);
                break;
            case 3:
                settingModel.setJiance_xiangmu_moren(CURRENT_SELECT_TYPE);
                acSettingTxtMoren.setText(CURRENT_SELECT_TYPE);
                break;
            case 4:
                settingModel.setBianma(CURRENT_SELECT_TYPE);
                acSettingTxtBianma.setText(CURRENT_SELECT_TYPE);
                break;
            case 5:
                settingModel.setShijian_xianzhi(CURRENT_SELECT_TYPE);
                acSettingTxtShijian.setText(CURRENT_SELECT_TYPE);
                break;
            case 6:
                settingModel.setPhoto_clip(CURRENT_SELECT_TYPE);
                acSettingTxtPaizhao.setText(CURRENT_SELECT_TYPE);
                break;
        }
        pop.dismiss();
    }
    private void chackBluetooth() {
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter==null) {
//            show_tv.setText("本机不支持蓝牙功能");
            Toast.makeText(this,"本机不支持蓝牙功能",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!defaultAdapter.isEnabled()) {
//            show_tv.setText("有蓝牙功能，还没打开");
            Toast.makeText(this,"有蓝牙功能，还没打开",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder =new AlertDialog.Builder(this);

            builder.setTitle("提示");
            builder.setMessage("蓝牙设备没打开，是否打开");
            builder.setNegativeButton("取消",null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,100);
                }
            });
            builder.show();

        }else {
            Toast.makeText(this,"蓝牙已开启",Toast.LENGTH_SHORT).show();
//            show_tv.setText("蓝牙已开启");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== this.RESULT_OK) {
            Toast.makeText(this,"蓝牙已开启",Toast.LENGTH_SHORT).show();
//            show_tv.setText("蓝牙已开启");
        }
    }
//    public void onClick(View view) {
//        getBondBluetooth();
//    }
    private void getBondBluetooth() {
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();


        List<String> list =new ArrayList<>();

        for (BluetoothDevice bond:bondedDevices) {

            String msg ="设备名称"+bond.getName()+"\n设备地址"+bond.getAddress();
            Log.e("tag",msg);
            list.add(msg);
        }
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//
//        lv.setAdapter(adapter);


    }
}
