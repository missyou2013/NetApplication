package net.com.mvp.ac.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.CommonUtils;
import net.com.mvp.ac.commons.JsonUtil;
import net.com.mvp.ac.commons.SharedPreferencesUtils;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.BuHeGeModel;
import net.com.mvp.ac.model.CarsInforModel;
import net.com.mvp.ac.model.ReportedModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//不合格项目录入
public class DisqualificationActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @BindView(R.id.ac_buhege_et_search)
    EditText acBuhegeEtSearch;
    @BindView(R.id.ac_buhege_btn_search)
    Button acBuhegeBtnSearch;
    @BindView(R.id.list_buhge_listview)
    ListView listBuhgeListview;
    BuHeGe_1Adapter2 BuHeGe_1Adapter;
    List<String> data = new ArrayList<String>();

    private DisqualificationActivity instances = null;
    List<BuHeGeModel> buHeGeModelList = new ArrayList<>();
    List<BuHeGeModel> searchList = new ArrayList<>();
    CarsInforModel carsInforModel;
    List<ReportedModel> reportList = new ArrayList<>();
    int Buhege_Statement = 0;//不合格项目跳转 0：外检 1：底盘动态33
    String DateType = "";//上报类型 F1:外检  DC：底盘动态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disqualification);
        ButterKnife.bind(this);
        instances = this;
        setBackBtn();
        setTopTitle(R.string.activity_buhege);
        carsInforModel = (CarsInforModel) getIntent().getSerializableExtra("WaiJianPhotoActivity_CarsInforModel");
        Buhege_Statement = getIntent().getExtras().getInt("buhege_flag", 0);
        if (Buhege_Statement == 0) {
            DateType = "F1";
        } else {
            DateType = "DC";
        }
        setRightTxt("上报", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (searchList != null && searchList.size() > 0) {
                            getData("", 1);
                        } else {
                            Toast.makeText(instances, "数据不能为空", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //组装要上报的json
    private String reportedJsonStr() {
        String myjson = "";
        if (searchList != null && searchList.size() > 0) {
            ReportedModel model;
            for (int i = 0; i < searchList.size(); i++) {
                model = new ReportedModel();
                model.setDataType(DateType);
                model.setDetection_ID(String.valueOf(carsInforModel.getID()));
                model.setGUID(carsInforModel.getGUID());
                model.setTest_times(carsInforModel.getTimes());
                model.setUnqualified_Code1(searchList.get(i).getCode1());
                model.setUnqualified_Code2(searchList.get(i).getCode2());
                model.setUnqualified_Code3(searchList.get(i).getCode3());
                reportList.add(model);
            }
            if (reportList != null && reportList.size() > 0) {
                Gson gson = new Gson();
                myjson = gson.toJson(reportList);
                UtilsLog.e("getDataReportData---myjson==" + myjson);
            }
        }
        return myjson;
    }

    @OnClick(R.id.ac_buhege_btn_search)
    public void onViewClicked() {
        String str = acBuhegeEtSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(str) && str.length() > 4) {
            boolean b = true;
            if (searchList != null && searchList.size() > 0) {
                for (int i = 0; i < searchList.size(); i++) {
                    if (str.equals(searchList.get(i).getCode_123())) {
                        b = false;
                        Toast.makeText(this, "已有相同的数据", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        b = true;
                    }
                }
            }
            if (b) {
                getData(str, 0);
            }
        } else {
            Toast.makeText(this, "请输入正确的格式", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void getData(String str, int type) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getIP(instances))) {
            if (CommonUtils.isOnline(instances)) {
                switch (type) {
                    case 0:
                        getDataBuHeGe(str);
                        break;
                    case 1:
                        getDataReportData();
                        break;
                }

            } else {
                Toast.makeText(instances, getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "请设置IP与端口号", Toast.LENGTH_LONG).show();
        }
    }

    private void getDataBuHeGe(final String str) {
        UtilsLog.e("getDataBuHeGe---url==" + SharedPreferencesUtils.getIP(instances) + BUHEGE_XIANGMU_LIST);
        OkGo.<String>get(SharedPreferencesUtils.getIP(instances) + BUHEGE_XIANGMU_LIST + str).tag(instances)

                .execute(new StringCallback() {
                    @Override
                    public void onStart( Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataBuHeGe-result==" + result);
//                        UtilsLog.e("response.headers()-result==" + response.headers());
                        String newResult = result.substring(1, result.length() - 1).replaceAll("\\\\", "");
                        UtilsLog.e("getDataBuHeGe-newResult==" + newResult);
                        if (!TextUtils.isEmpty(newResult) && !"[[]]".equals(newResult) && !"[{}]".equals(newResult) && !"[]".equals(newResult)) {
                            buHeGeModelList = JsonUtil.stringToArray(newResult, BuHeGeModel[].class);
//                            BuHeGeModel model=new BuHeGeModel();
                            String[] s = str.split("/");
                            for (int i = 0; i < buHeGeModelList.size(); i++) {
                                buHeGeModelList.get(i).setCode_123(str);
                                if (!TextUtils.isEmpty(s[0]) && !TextUtils.isEmpty(s[1]) && !TextUtils.isEmpty(s[2])) {
                                    buHeGeModelList.get(i).setCode1(s[0]);
                                    buHeGeModelList.get(i).setCode2(s[1]);
                                    buHeGeModelList.get(i).setCode3(s[2]);
                                }
                                searchList.add(buHeGeModelList.get(i));
                            }
                            BuHeGe_1Adapter = new BuHeGe_1Adapter2(instances, searchList);
                            listBuhgeListview.setAdapter(BuHeGe_1Adapter);
                        } else {
                            Toast.makeText(instances, "没有查询到相关数据", Toast.LENGTH_LONG).show();
                            UtilsLog.e("getDataBuHeGe-result==" + "没有数据");
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataBuHeGe-onError==" + response.body());
                        UtilsLog.e("getDataBuHeGe-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getDataReportData() {
        UtilsLog.e("getDataReportData---url==" + SharedPreferencesUtils.getIP(instances) + REPORTED_DATA);
        String myjson = reportedJsonStr();
        OkGo.<String>post(SharedPreferencesUtils.getIP(instances) + REPORTED_DATA).tag(instances)
                .upJson(myjson)
                .execute(new StringCallback() {
                    @Override
                    public void onStart( Request<String, ? extends Request> request) {
                        super.onStart(request);
                        CommonUtils.showLoadingDialog(instances, "加载中...");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        UtilsLog.e("getDataReportData-result==" + result);
                        if ("\"ok\"".equals(result)) {
                            Toast.makeText(instances, "上报数据成功", Toast.LENGTH_LONG).show();
                            instances.finish();
                        }
                        CommonUtils.hideLoadingDialog(instances);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        UtilsLog.e("getDataReportData-onError==" + response.body());
                        UtilsLog.e("getDataReportData-onError==" + response.getException());
                        CommonUtils.hideLoadingDialog(instances);
                        Toast.makeText(instances, getResources().getString(R.string.services_error), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        instances = null;
    }
}

class BuHeGe_1Adapter2 extends BaseAdapter {
    private LayoutInflater minflater;
    private Context context;
    private List<BuHeGeModel> data;

    public BuHeGe_1Adapter2(Context context, List<BuHeGeModel> data) {
        super();
        this.minflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int ll = 0;
        if (data.size() > 0) {
            ll = data.size();
        } else {
            ll = 0;
        }
        return ll;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = minflater.inflate(R.layout.item_buhege_1,
                    null);
            holder.itemBuhege1Txt1 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_1);
            holder.itemBuhege1Txt2 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_2);
            holder.itemBuhege1Txt3 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_3);
            holder.itemBuhege1TxtDelete = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemBuhege1TxtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyDataSetChanged();
//                DisqualificationActivity.showInfo(position);
            }
        });
//        holder.itemBuhege1Txt1.setText("aaaaaaaa");
        holder.itemBuhege1Txt1.setText(data.get(position).getName1());
        holder.itemBuhege1Txt2.setText(data.get(position).getName2());
        holder.itemBuhege1Txt3.setText(data.get(position).getName3());
        return convertView;
    }


    class ViewHolder {
        //        @BindView(R.id.item_buhege_1_txt_1)
        TextView itemBuhege1Txt1;
        //        @BindView(R.id.item_buhege_1_txt_2)
        TextView itemBuhege1Txt2;
        //        @BindView(R.id.item_buhege_1_txt_3)
        TextView itemBuhege1Txt3;
        //        @BindView(R.id.item_buhege_1_txt_delete)
        TextView itemBuhege1TxtDelete;
    }


}
