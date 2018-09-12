package net.com.mvp.ac.wuxi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;
import net.com.mvp.ac.application.BaseApplication;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.PDALogUtils;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.CarsInforModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticesActivity extends BaseActivity {

    @BindView(R.id.ac_notices_et_1)
    EditText acNoticesEt1;
    @BindView(R.id.ac_notices_et_2)
    EditText acNoticesEt2;
    @BindView(R.id.ac_notices_et_3)
    EditText acNoticesEt3;
    @BindView(R.id.ac_notices_btn_search)
    Button acNoticesBtnSearch;
    @BindView(R.id.ac_notices_tv_result)
    TextView acNoticesTvResult;
    private String Login_name = "", login_IDNumber;//当前登录的检测员的名字
    private int CHECK_MODE = 0;//检测模式 0:外检  1：路试 2：底盘动态
    private NoticesActivity instances = null;
    private CarsInforModel carsInforModel;
    private String Item_Code = "";//F1外检,R1路试，DC底盘动态
    private String bh = "", clpp1 = "", clxh = "";//整车公告编号,车辆品牌(中文),车辆型号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        ButterKnife.bind(this);
        setBackBtn();
        setTopTitle("查询公告");

        instances = this;

        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("model_CarsInforModel");
        login_IDNumber = getIntent().getExtras().getString("login_IDNumber", "");
        Login_name = getIntent().getExtras().getString("Login_name", "");
        CHECK_MODE = BaseApplication.JIANCE_MODE;
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
        if (!TextUtils.isEmpty(carsInforModel.getBrand())) {
            acNoticesEt2.setText(carsInforModel.getBrand());
        }
        if (!TextUtils.isEmpty(carsInforModel.getCarModel())) {
            acNoticesEt3.setText(carsInforModel.getCarModel());
        }
    }

//    class WebsevicesMethod extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            WebServiceHelper WebServiceHelper = new WebServiceHelper();
//            List<String> stringList = WebServiceHelper.getProvince();
//            Log.e("liu", stringList.toString());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
////            super.onPostExecute(s);
//        }
//
//    }
//
//    String namespace = "http://WebXml.com.cn/";
//    String transUrl = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
//    String method = "getSupportCity";

    @OnClick({R.id.ac_notices_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_notices_btn_search:


//                new WebsevicesMethod().equals("");
//                String bh="",  clpp1="",  clxh="";//整车公告编号,车辆品牌(中文),车辆型号
                String carModel = acNoticesEt3.getText().toString().trim();
                String carBrand = acNoticesEt2.getText().toString().trim();
                String number = acNoticesEt1.getText().toString().trim();
                bh = number;
                clpp1 = carBrand;
                clxh = carModel;
                String logStr = "\n" + "webservices====" +
                        "\n" + "URL::" + SharedPreferencesUtils.getWebServicesIP(instances) +
                        "\n" + SharedPreferencesUtils.getWebServicesAddress(instances) + "\n" + "\n"
                        + "bh::" + bh + "\n" + "clpp1::" + clpp1 + "\n" + "clxh::" + clxh + "\n";
                PDALogUtils.createLogFile(5, logStr.getBytes());
                if (!TextUtils.isEmpty(number)) {
                    if (!TextUtils.isEmpty(carBrand)) {
                        if (!TextUtils.isEmpty(carModel)) {
                            //18C08-机动车公告技术参数文本信息下载
//                            getDataSource(SharedPreferencesUtils.getWebServicesIP(instances), method2,
//                                    SharedPreferencesUtils.getWebServicesAddress(instances));
//                            getDataSource(SharedPreferencesUtils.getWebServicesIP(instances) +
//                                            SharedPreferencesUtils.getWebServicesAddress(instances),
// method2,
//                                    SharedPreferencesUtils.getWebServicesAddress(instances));
//                            getDataSource(namespace, method, transUrl);
                        } else {
                            Toast.makeText(instances, "车辆型号不能为空", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Toast.makeText(instances, "车辆品牌不能为空", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(instances, "整车公告编号不能为空", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    private void getData(int flag) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(this))) {
            if (CommonUtils.isOnline(this)) {
                getNoticesInfor();
            } else {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 查询公告
     */
    private void getNoticesInfor() {
        OkGo.<String>post(SharedPreferencesUtils.getIP(this) + GET_SERVICE_TIME).tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getNoticesInfor-result==" + result);
                        CommonUtils.hideLoadingDialog(instances);
                        if (!TextUtils.isEmpty(result) && !"[]".equals(result)) {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getNoticesInfor-onError==" + response.body());
                        CommonUtils.hideLoadingDialog(instances);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }

//    String namespace2 = "http://192.168.100.26/";
//    String transUrl2 = "http://192.168.100.26/pnweb/services/TmriOutNewAccess";
//    String method2 = "queryObjectOutNew";
//    String soapAction2 = namespace2 + method2;
//
//    private void getDataSource(final String namespace2, final String method2, String transUrl2) {
//        String logStr = "\n" + "webservices====" +
//                "\n" + "namespace2::" + namespace2 + "\n"
//                + "\n" + "method2::" + method2 + "\n" + "transUrl2::" + transUrl2;
//        PDALogUtils.createLogFile(5, logStr.getBytes());
//        SoapObject soapObject = new SoapObject(namespace2, method2);
////        SharedPreferencesUtils.getWebServicesIP(instances)
////        soapObject.addProperty("theCityName", "青岛市");
////        soapObject.addProperty("byProvinceName", "山东");
//        soapObject.addProperty("bh", bh);
//        soapObject.addProperty("clpp1", CommonUtils.encodeUTF8(clpp1));
//        soapObject.addProperty("clxh", clxh);
//
//
//        // new调用webservice方法的SOAP请求信息，并指定版本，也可是VER11
//        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
//        envelope.bodyOut = soapObject;
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(soapObject);
////        SharedPreferencesUtils.getWebServicesIP
////                (instances)
////                + SharedPreferencesUtils.getWebServicesAddress(instances)
//        final HttpTransportSE httpTransportSE = new HttpTransportSE(transUrl2);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    httpTransportSE.call(namespace2 + method2,
//                            envelope);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                }
//
//                // 获取返回的数据
////                SoapObject object = (SoapObject) envelope.bodyIn;
//                SoapObject object = null;
//                object = (SoapObject) envelope.bodyIn;
////                try {
////                    object = (SoapObject) envelope.getResponse();
////                } catch (SoapFault soapFault) {
////                    soapFault.printStackTrace();
////                }
////                if(object==null){
////                    return;
////                }
//                String str = "";
//                if (!TextUtils.isEmpty(object.toString())) {
//                    str = object.toString();
//                    String logStr = "\n" + "webservices====" +
//                            "\n" + "URL::" + SharedPreferencesUtils.getWebServicesIP(instances) +
//                            "\n" + SharedPreferencesUtils.getWebServicesAddress(instances) + "\n" + "\n" +
//                            "result==" + object;
//                    PDALogUtils.createLogFile(5, logStr.getBytes());
////                    if (!TextUtils.isEmpty(object.getProperty(0).toString())) {
////                        String jsonData = object.getProperty(0).toString();
////                        str = str + "\n\n" + jsonData;
////                    }
//
//                }
//                Log.e("liu", "object是返回的JSON串==" + str);
////                System.out.println("object是返回的JSON串" + object);
////                String jsonData = object.getProperty(0).toString();
////                System.out.println("JSON的数据是:" + jsonData);
////                Log.e("liu", jsonData);
//                Message message = Message.obtain();
//                message.obj = str;
//                mHandler.handleMessage(message);
////                mHandler.sendEmptyMessage(0);
//
//            }
//        }).start();
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
////            resultListViewAdapter.notifyDataSetChanged();
////            Log.e("liu", (String)msg.obj);
////            acNoticesTvResult.setText((String)msg.obj);
//        }
//    };


}
