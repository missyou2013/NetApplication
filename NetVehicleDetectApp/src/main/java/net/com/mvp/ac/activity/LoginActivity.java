package net.com.mvp.ac.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.MyAccountModeListAdapter;
import net.com.mvp.ac.adapter.UserAccountListAdapter;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.Constants;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CheckItemModel;
import net.com.mvp.ac.model.UserAccountModel;
import net.com.mvp.ac.widget.EditTextViewWorksSizeCheckUtil;
import net.com.mvp.ac.widget.IEditTextChangeListener;
import net.com.mvp.ac.wuxi.HomeActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.com.mvp.ac.application.BaseApplication.checkItemModelList;

/**
 * @Description: 登录
 * @time 2017/6/8
 */
public class LoginActivity extends BaseActivity {
    int TYPE_POP = 0;//popuwindow 0：检测模式   1：账号
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ac_login_account)
    TextView acLoginAccount;
    @BindView(R.id.ac_login_pwd)
    EditText acLoginPwd;
    @BindView(R.id.ac_login_check_mode)
    TextView acLoginCheckMode;
    @BindView(R.id.ac_login_btn_set)
    Button acLoginBtnSet;
    @BindView(R.id.ac_login_view_m)
    TextView acLoginViewM;
    @BindView(R.id.ac_login_btn_login)
    Button acLoginBtnLogin;
    BaseApplication baseApplication;
    private UserAccountListAdapter userAccountListAdapter;
    private int Currentindex = 0;//用户名当前的position
    private boolean Is_skip = false;//登录按钮点击后是否可以跳转

    private LoginActivity instances = null;


    public static String[] permissionArray = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_SETTINGS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CALENDAR,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PermissionUtils.checkPermissionArray(this, permissionArray, 2);
        ButterKnife.bind(this);
        instances = this;
        baseApplication = BaseApplication.getSelf();

        //删除拍照编辑图片的文件夹
        UtilsLog.e("图片的文件夹===" + Environment.getExternalStorageDirectory() + "Pictures");
        net.com.mvp.ac.commons.FileUtils.deleteDir(Environment.getExternalStorageDirectory() + "/Pictures");

        net.com.mvp.ac.commons.FileUtils.deleteDirectory(Environment.getExternalStorageDirectory() +
                "/Pictures");

//        textView.setText("PDA查验系统" + CommonUtils.getVersionName(instances));
        textView.setText("PDA查验系统");
        UtilsLog.e("当前选择的版本号：" + CommonUtils.getVersionName(instances));
        pop_account();
        pop_account_userlist();
//         设置默认更新接口地址与渠道
//        UpdateManager.setUrl("http://www.baidu.com", "yyb");
// 进入应用时查询更新
//        UpdateManager.check(this);

        //1.创建工具类对象 把要改变颜色的tv先传过去
        EditTextViewWorksSizeCheckUtil.textChangeListener textChangeListener = new
                EditTextViewWorksSizeCheckUtil.textChangeListener(acLoginBtnLogin);

        //2.把所有要监听的edittext都添加进去
        textChangeListener.addAllEditText(acLoginPwd);

        //3.接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        EditTextViewWorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    acLoginBtnLogin.setTextColor(Color.BLUE);
                    acLoginBtnLogin.setEnabled(true);
                } else {
                    acLoginBtnLogin.setTextColor(Color.GRAY);
                    acLoginBtnLogin.setEnabled(false);
                }
            }
        });
//记录上次选择的检测模式
        acLoginCheckMode.setText(strModes.get(BaseApplication.JIANCE_MODE));
    }


    private View view2;
    private RelativeLayout parent22;
    private PopupWindow pop2 = null;
    private ListView listview_modes;
    private List<String> strModes = new ArrayList<String>();
    MyAccountModeListAdapter MyAccountModeListAdapter;

    private void pop_account() {
        for (int i = 0; i < Constants.MY_ACCOUNT_MODEL_LIST.length; i++) {
            strModes.add(i, Constants.MY_ACCOUNT_MODEL_LIST[i]);
        }
        pop2 = new PopupWindow(this);
        view2 = getLayoutInflater().inflate(R.layout.item_popupwindows_bottom,
                null);
        pop2.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop2.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop2.setBackgroundDrawable(new BitmapDrawable());
        pop2.setFocusable(true);
        pop2.setOutsideTouchable(true);
        pop2.setContentView(view2);

        parent22 = (RelativeLayout) view2.findViewById(R.id.parent2);
        listview_modes = (ListView) view2.findViewById(R.id.themeid_listview);
//        //popuwindow 0：检测模式   1：账号
//        if (TYPE_POP == 0) {
//        } else {
//        }
        parent22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop2.dismiss();
            }
        });
    }

    private View view2_userlist;
    private RelativeLayout parent22_userlist;
    private PopupWindow pop2_userlist = null;
    private ListView listview_modes_userlist;

    private void pop_account_userlist() {
        pop2_userlist = new PopupWindow(this);
        view2_userlist = getLayoutInflater().inflate(R.layout.item_popupwindows_bottom,
                null);
        pop2_userlist.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop2_userlist.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop2_userlist.setBackgroundDrawable(new BitmapDrawable());
        pop2_userlist.setFocusable(true);
        pop2_userlist.setOutsideTouchable(true);
        pop2_userlist.setContentView(view2_userlist);

        parent22_userlist = (RelativeLayout) view2_userlist.findViewById(R.id.parent2);
        listview_modes_userlist = (ListView) view2_userlist.findViewById(R.id.themeid_listview);
        parent22_userlist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop2_userlist.dismiss();
            }
        });
    }

    @OnClick({R.id.imageView, R.id.ac_login_account,
            R.id.ac_login_check_mode,
            R.id.ac_login_btn_set,
            R.id.ac_login_btn_login})
    public void onViewClicked(View view) {
        {
            switch (view.getId()) {
                //账号
                case R.id.ac_login_account:
                    TYPE_POP = 1;
                    pop2_userlist.showAsDropDown(acLoginAccount);
                    if (BaseApplication.userAccountModelList != null && BaseApplication
                            .userAccountModelList.size() > 0) {
                        listview_modes_userlist.setOnItemClickListener(new AdapterView
                                .OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int position, long arg3) {
                                acLoginAccount.setText(BaseApplication.userAccountModelList.get(position)
                                        .getUserName());
                                Currentindex = position;
                                pop2_userlist.dismiss();
                            }
                        });
                    } else {
//                        Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
// .LENGTH_LONG).show();
                        return;
                    }


//
//                    String username = acLoginAccount.getText().toString().trim();
//                    String password = acLoginPwd.getText().toString().trim();
//                    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
//                        if (username.length() > 4 && password.length() > 4) {
//                            if ("admin".equals(username) && "admin".equals(password)) {
//                                //进入用户管理页面
//                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
//                                startActivity(intent);
//                            } else {
//                                //进入查询车辆信息页面
//                            }
//
//                        } else {
//                            Toast.makeText(instances, getResources().getString(R.string.username_pwd_error)
//                                    , Toast
//                                            .LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(instances, getResources().getString(R.string.username_pwd_error),
// Toast
//                                .LENGTH_LONG).show();
//                    }


                    break;
                //检测模式
                case R.id.ac_login_check_mode:
                    pop2.showAsDropDown(acLoginCheckMode);
//                    pop2.showAtLocation(acLoginCheckMode, Gravity.CENTER_HORIZONTAL, 0, 30);
                    TYPE_POP = 0;
                    if (strModes.size() > 0) {
                        MyAccountModeListAdapter = new MyAccountModeListAdapter(LoginActivity.this, strModes);
                        listview_modes.setAdapter(MyAccountModeListAdapter);
                        listview_modes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int position, long arg3) {
                                BaseApplication.JIANCE_MODE = position;
                                UtilsLog.e("当前选择的检测模式：" + position);
                                acLoginCheckMode.setText(strModes.get(position));
                                pop2.dismiss();
                            }
                        });
                    }
                    pop2.showAsDropDown(acLoginCheckMode);
                    break;
                //设置
                case R.id.ac_login_btn_set:
                    Intent intent = new Intent(LoginActivity.this, net.com.mvp.ac.activity.SettingActivity
                            .class);
                    startActivity(intent);
//                this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    break;
                //登录
                case R.id.ac_login_btn_login:
                    String UserID = acLoginAccount.getText().toString().trim();
                    String pwd = acLoginPwd.getText().toString().trim();
//                    String pwd = "6";
                    if (!TextUtils.isEmpty(UserID) && !TextUtils.isEmpty(pwd)) {
                        if (!TextUtils.isEmpty(BaseApplication.userAccountModelList.get(Currentindex)
                                .getUserRight())) {
                            //判定用户的权限
//                            String[] roles = userAccountModelList.get(Currentindex).getUserRight().split
// (",");
                            switch (BaseApplication.JIANCE_MODE) {
                                case 0:
                                    //外检
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().contains("0")
                                            || BaseApplication.userAccountModelList.get(Currentindex)
                                            .getUserRight().contains("3")) {
                                        Is_skip = true;
                                    } else {
                                        Is_skip = false;
                                        Toast.makeText(this, getResources().getString(R.string
                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
                                    }
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().equals("")) {
                                        Is_skip = true;
                                    }
                                    break;
                                case 1:
                                    //路试
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().contains("0")
                                            || BaseApplication.userAccountModelList.get(Currentindex)
                                            .getUserRight().contains("6")) {
                                        Is_skip = true;
                                    } else {
                                        Is_skip = false;
                                        Toast.makeText(this, getResources().getString(R.string
                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
                                    }
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().equals("")) {
                                        Is_skip = true;
                                    }
                                    break;
                                case 2:
                                    //底盘动态
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().contains("0")
                                            || BaseApplication.userAccountModelList.get(Currentindex)
                                            .getUserRight().contains("4")) {
                                        Is_skip = true;
                                    } else {
                                        Is_skip = false;
                                        Toast.makeText(this, getResources().getString(R.string
                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
                                    }
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().equals("")) {
                                        Is_skip = true;
                                    }
                                    break;
//                                case 3:
//                                    //调度
//                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
//                                            ().contains("2")
//                                            ) {
//                                        Is_skip = true;
//                                    } else {
//                                        Is_skip = false;
//                                        Toast.makeText(this, getResources().getString(R.string
//                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
//                                    }
////                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
////                                            ().equals("")) {
////                                        Is_skip = true;
////                                    }
//                                    break;
                                case 3:
                                    //外廓尺寸
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().contains("0")
                                            ) {
                                        Is_skip = true;
                                    } else {
                                        Is_skip = false;
                                        Toast.makeText(this, getResources().getString(R.string
                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
                                    }
//                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
//                                            ().equals("")) {
//                                        Is_skip = true;
//                                    }
                                    break;
                                case 4:
                                    //逆反系数
                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
                                            ().contains("0")
                                            ) {
                                        Is_skip = true;
                                    } else {
                                        Is_skip = false;
                                        Toast.makeText(this, getResources().getString(R.string
                                                .quanxian_inconformity), Toast.LENGTH_LONG).show();
                                    }
//                                    if (BaseApplication.userAccountModelList.get(Currentindex).getUserRight
//                                            ().equals("")) {
//                                        Is_skip = true;
//                                    }
                                    break;
                            }
                        }
                        if (Is_skip) {
                            //调用登录接口
                            getDataToLogin(BaseApplication.userAccountModelList.get(Currentindex).getUserID
                                    (), pwd);
                        }
                    } else {
                        Toast.makeText(instances, getResources().getString(R.string.username_pwd_null),
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.imageView:
//                    Intent intent3 = new Intent(LoginActivity.this, DaiJianCarsActivity.class);
                    Intent intent3 = new Intent(LoginActivity.this, OneActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances)) && !TextUtils.isEmpty
                (SharedPreferencesUtils.getFileIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                getDataUsersList();
                //获取所有人工检验项目接口
                getGET_ALL_ITEMList();

            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast
                        .LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(instances, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    //获取用户列表
    private void getDataUsersList() {
        UtilsLog.e("getDataUsersList---url==" + SharedPreferencesUtils.getIP(instances) + GETALLUSERS);
        OkGo.<String>get(SharedPreferencesUtils.getIP(instances) + GETALLUSERS).tag(instances)
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
//                        UtilsLog.e("getDataUsersList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataUsersList-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {
                            BaseApplication.userAccountModelList = JsonUtil.stringToArray(newResult,
                                    UserAccountModel[].class);
                            userAccountListAdapter = new UserAccountListAdapter(LoginActivity.this,
                                    BaseApplication.userAccountModelList);
                            listview_modes_userlist.setAdapter(userAccountListAdapter);
                        } else {
//                            Toast.makeText(instances, getResources().getString(R.string.network_error),
// Toast.LENGTH_LONG).show();
                            UtilsLog.e("getDataUsersList-result==" + "没有数据");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtils.hideLoadingDialog(instances);
                        UtilsLog.e("getDataUsersList-onError==" + response.body());
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
                    }
                });
    }

    //登录
    private void getDataToLogin(final String UserID, String LoginPass) {
        UtilsLog.e("getDataToLogin---url==" + SharedPreferencesUtils.getIP(instances) + USERLOGIN);
//        {"UserID":"2", "LoginPass":"2"}
        Map<String, String> params = new HashMap<>();
        params.put("ID", UserID);
        params.put("Psw", LoginPass);
        JSONObject jsonObject = new JSONObject(params);
        UtilsLog.e("getDataToLogin---json==" + jsonObject.toString());
        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + USERLOGIN).tag(instances)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
//                        UtilsLog.e("getDataToLogin-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataToLogin-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(newResult)) {
                            BaseApplication.accountModelList = JsonUtil.stringToArray(newResult,
                                    UserAccountModel[].class);
                            if (BaseApplication.accountModelList.size() > 0) {
                                UtilsLog.e(" BaseApplication.JIANCE_MODE-login==" + BaseApplication
                                        .JIANCE_MODE);
                                String msg = "=======PDA-LOG=====" + "\n" + "移动检测系统版本号::::" + CommonUtils
                                        .getVersionName(instances) + "\n";
                                PDALogUtils.createLogFile(BaseApplication.JIANCE_MODE, msg.getBytes());
                                Intent intent2 = null;
                                //检测模式 0:外检  1：路试 2：底盘动态 3:调度
                                String login_name = acLoginAccount.getText().toString().trim();
//                                if (BaseApplication.JIANCE_MODE != 3) {
//                                    intent2 = new Intent(LoginActivity.this, DaiJianCars2Activity.class);
//                                } else {
//                                    intent2 = new Intent(LoginActivity.this, NewCarsListActivity.class);
//                                }
                                intent2 = new Intent(LoginActivity.this, HomeActivity.class);
                                intent2.putExtra("check_mode", BaseApplication.JIANCE_MODE);
                                intent2.putExtra("login_UserID", login_name);
                                UtilsLog.e("getDataToLogin-login_IDNumber==" + BaseApplication
                                        .accountModelList.get(0).getIDNumber());
                                if (!TextUtils.isEmpty(BaseApplication.accountModelList.get(0).getIDNumber
                                        ())) {
                                    intent2.putExtra("login_IDNumber", BaseApplication.accountModelList.get
                                            (0).getIDNumber());
                                } else {
                                    intent2.putExtra("login_IDNumber", "");
                                }
                                startActivity(intent2);
//                    finish();
                            }
                        } else {
                            Toast.makeText(instances, LoginActivity.this.getResources().getString(R.string
                                    .try_again), Toast.LENGTH_LONG).show();
                            UtilsLog.e("getDataToLogin-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtils.hideLoadingDialog(instances);
                        UtilsLog.e("getDataUsersList-onError==" + response.body());
                    }
                });
    }

    //获取所有人工检验项目接口
    private void getGET_ALL_ITEMList() {
        UtilsLog.e("getGET_ALL_ITEMList---url==" + SharedPreferencesUtils.getIP(instances) + GET_ALL_ITEM);
        OkGo.<String>get(SharedPreferencesUtils.getIP(instances) + GET_ALL_ITEM).tag(instances)
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
//                        UtilsLog.e("getGET_ALL_ITEMList-result==" + result);
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
//                        UtilsLog.e("getGET_ALL_ITEMList-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals
                                (newResult) && !"[]".equals(result)) {
                            checkItemModelList = JsonUtil.stringToArray(newResult, CheckItemModel[].class);
//                            for (int i = 0; i < checkItemModelList.size(); i++) {
//                                UtilsLog.e("checkItemModelList=11=" + checkItemModelList.get(i).toString());
//                            }

                        } else {
//                            Toast.makeText(instances, getResources().getString(R.string.network_error),
// Toast.LENGTH_LONG).show();
                            UtilsLog.e("getGET_ALL_ITEMList-result==" + "没有数据");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtils.hideLoadingDialog(instances);
                        UtilsLog.e("getGET_ALL_ITEMList-onError==" + response.body());
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast
                                .LENGTH_LONG).show();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        if (pop2_userlist != null) {
            pop2_userlist.dismiss();
            pop2_userlist = null;
        }
        if (pop2 != null) {
            pop2.dismiss();
            pop2 = null;
        }
        instances = null;
    }

}