package com.kernal.smartvision.ocr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kernal.smartvisionocr.RecogService;
import com.kernal.smartvisionocr.utils.Utills;

import java.io.File;

/**导入识别示例界面
 * Created by user on 2018/3/28.
 */
public class PicRecogActivity extends Activity {
    public RecogService.MyBinder recogBinder;
    private int iTH_InitSmartVisionSDK = -1;
    private String Imagepath;
    private int[] nCharCount = new int[2];// 返回结果中的字符数
    private TextView textView;
    private  ProgressDialog mypDialog;
    public ServiceConnection recogConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            recogConn = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            recogBinder = (RecogService.MyBinder) service;
            iTH_InitSmartVisionSDK = recogBinder.getInitSmartVisionOcrSDK();
            if (iTH_InitSmartVisionSDK == 0) {
                recogBinder.AddTemplateFile();// 添加识别模板
                recogBinder
                        .SetCurrentTemplate("SV_ID_VIN_MOBILE");// 设置当前识别模板ID
                recogBinder.LoadImageFile(Imagepath,0);
               int returnRecogize=  recogBinder
                        .Recognize(
                                Devcode.devcode, "SV_ID_VIN_MOBILE");
                String recogResultString = recogBinder.GetResults(nCharCount);
                if (recogResultString==null||"".equals(recogResultString)) {
                    if(returnRecogize!=0){
                        recogResultString = "识别失败，错误代码为:"+returnRecogize;
                    }else {
                        recogResultString = "识别失败";
                    }

                }else{
                    Imagepath = savePicture();
                }
                Intent intent = new Intent(PicRecogActivity.this,ShowResultActivity.class);
                intent.putExtra("PicRecogPath",Imagepath);
                intent.putExtra("PicResult",recogResultString);
                intent.putExtra("templateName","VIN码");
                startActivity(intent);
                overridePendingTransition(getResources().getIdentifier("zoom_enter", "anim", getApplication().getPackageName()), getResources().getIdentifier("push_down_out", "anim", getApplication().getPackageName()));
                finish();
            } else {
                Toast.makeText(PicRecogActivity.this,
                        "核心初始化失败，错误码：" + iTH_InitSmartVisionSDK,
                        Toast.LENGTH_LONG).show();
                finish();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utills.copyFile(this);// 写入本地文件
        CreateDialog(this);
        Intent intent = getIntent();
        Imagepath = intent.getStringExtra("recogImagePath");
        if (recogBinder == null) {
            Intent authIntent = new Intent(this,
                    RecogService.class);
            bindService(authIntent, recogConn, Service.BIND_AUTO_CREATE);
        }
    }
    @SuppressLint("NewApi")
    public  void CreateDialog(Context context){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mypDialog=new ProgressDialog(context);
        //实例化
        mypDialog.setProgressStyle(context.getResources().getIdentifier("dialog","styles",context.getPackageName()));
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setMessage("识别中.....");
        mypDialog.show();

    }
    public  void DeletDialog(){
        mypDialog.dismiss();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( recogBinder != null) {
            unbindService(recogConn);
        }
        DeletDialog();
    }
    public String savePicture() {
        String picPathString1 = "";
        String PATH = Environment.getExternalStorageDirectory().toString()
                + "/DCIM/Camera/";
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String name =  Utills.pictureName();
        picPathString1 = PATH + "smartVisition" + name + ".jpg";
            // 识别区域内图片
        recogBinder.svSaveImageResLine(picPathString1);
        return picPathString1;
    }
}
