package com.kernal.smartvision.utils;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.kernal.smartvision.ocr.Devcode;
import com.kernal.smartvision.view.ViewfinderView;
import com.kernal.smartvisionocr.RecogService;
import com.kernal.smartvisionocr.utils.KernalLSCXMLInformation;
import com.kernal.smartvisionocr.utils.SharedPreferencesHelper;
import com.kernal.smartvisionocr.utils.Utills;
import com.kernal.smartvisionocr.utils.WriteToPCTask;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by user on 2018/5/18.
 */

public class RecogOpera {
    private RecogService.MyBinder recogBinder;
    private Context context;
    public int iTH_InitSmartVisionSDK=-1;
    public int[] regionPos=new int[4];
    public int error=-1;
    private KernalLSCXMLInformation wlci_Landscape, wlci_Portrait;

    public KernalLSCXMLInformation getWlci_Landscape() {
        return wlci_Landscape;
    }

    public KernalLSCXMLInformation getWlci_Portrait() {
        return wlci_Portrait;
    }

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
            } else {
                Toast.makeText(context,
                        "核心初始化失败，错误码：" + iTH_InitSmartVisionSDK,
                        Toast.LENGTH_LONG).show();
            }

        }
    };
    public  RecogOpera(Context context){
    this.context=context;
    }
    public void setRecogBinder(RecogService.MyBinder recogBinder) {
        this.recogBinder = recogBinder;
    }

    /**
     * 初始化核心
     */
  public  void initOcr(){
      Utills.copyFile(context);// 写入本地文件
      ParseXml();
      Intent authIntent = new Intent(context,
              RecogService.class);
      context.bindService(authIntent, recogConn, Service.BIND_AUTO_CREATE);
  }
    // 解析横竖屏下 两个XML文件内容，赋予不同的对象
    private void ParseXml() {
        // 横屏模板解析
        wlci_Landscape =  Utills
                .parseXmlFile(context, "appTemplateConfig.xml", true);
        if (wlci_Landscape.template == null
                || wlci_Landscape.template.size() == 0) {
            // 如果用户不选择任何模板类型，我们会强制选择第一个模板类型
            wlci_Landscape =  Utills.parseXmlFile(context, "appTemplateConfig.xml",
                    false);
            wlci_Landscape.template.get(0).isSelected = true;
            Utills.updateXml(context, wlci_Landscape, "appTemplateConfig.xml");
            wlci_Landscape =  Utills.parseXmlFile(context, "appTemplateConfig.xml",
                    true);
        }
        // 竖屏模板解析
        wlci_Portrait =  Utills.parseXmlFile(context,
                "appTemplatePortraitConfig.xml", true);
        if (wlci_Portrait.template == null
                || wlci_Portrait.template.size() == 0) {
            // 如果用户不选择任何模板类型，我们会强制选择第一个模板类型
            wlci_Portrait =  Utills.parseXmlFile(context,
                    "appTemplatePortraitConfig.xml", false);
            wlci_Portrait.template.get(0).isSelected = true;
            Utills.updateXml(context, wlci_Portrait,
                    "appTemplatePortraitConfig.xml");
            wlci_Portrait =  Utills.parseXmlFile(context,
                    "appTemplatePortraitConfig.xml", true);
        }
    }
    /**
     *
     * @param data1
     * @param type  0为保存全图 目的为了报错显示图片，其他为了点击拍照按钮，传入正图给核心
     * @return
     */
    public String savePicture(byte[] data1, int type, Camera.Size size,int rotation) {
        if(type==0){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPurgeable = true;
            options.inInputShareable = true;
            YuvImage yuvimage = new YuvImage(data1, ImageFormat.NV21, size.width,
                    size.height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, size.width, size.height), 80,
                    baos);
            String picPathString2 = "";
            String PATH = Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera/";
            String name =  com.kernal.smartvisionocr.utils.Utills.pictureName();
            picPathString2 = PATH + "smartVisitionComplete" + name + ".jpg";
            File dirs = new File(PATH);
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            File file = new File(picPathString2);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(picPathString2);
                outStream.write(baos.toByteArray());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    outStream.close();
                    baos.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            return picPathString2;

        }else{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPurgeable = true;
            options.inInputShareable = true;
            YuvImage yuvimage = new YuvImage(data1, ImageFormat.NV21,
                    size.width, size.height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, size.width, size.height), 100,
                    baos);
           Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),
                    0, baos.size(), options);
            Matrix matrix = new Matrix();
            matrix.reset();
            if (rotation == 90) {
                matrix.setRotate(90);
            } else if (rotation == 180) {
                matrix.setRotate(180);
            } else if (rotation == 270) {
                matrix.setRotate(270);
            }
            bitmap = Bitmap
                    .createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
            String picPathString2 = "";
            String PATH = Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera/";
            String name =  com.kernal.smartvisionocr.utils.Utills.pictureName();
            picPathString2 = PATH + "smartVisitionComplete" + name + ".jpg";
            File file = new File(picPathString2);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            try {
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(bitmap!=null&&!bitmap.isRecycled()){
                    bitmap.recycle();
                    bitmap=null;
                }
            }
            return picPathString2;
        }
    }

    /**
     * 核心保存裁切图片
     * @param data1
     * @param bol
     * @return
     */
    public String saveROIPicture(byte[] data1, boolean bol) {
        String picPathString1 = "";
        String PATH = Environment.getExternalStorageDirectory().toString()
                + "/DCIM/Camera/";
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String name =  Utills.pictureName();
        picPathString1 = PATH + "smartVisition" + name + ".jpg";

        if (bol) {
            // 识别区域内图片
            recogBinder.svSaveImage(picPathString1);
        } else {
            // 识别成功剪切的图片
            recogBinder.svSaveImageResLine(picPathString1);
        }
        return picPathString1;
    }

    /**
     *
     * @Title: setRegion 计算识别区域
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param
     * @return void 返回类型 leftPointX 扫描框左坐标系数 leftPointY 纵坐标系数
     */
    public int[] setRegion(boolean islandscape, KernalLSCXMLInformation wlci, int selectedTemplateTypePosition, Camera.Size size) {
        int[] regionPos = new int[4];
        if (islandscape) {

            regionPos[0] = (int) (wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointX * size.width);
            regionPos[1] = (int) (wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointY * size.height);
            regionPos[2] = (int) ((wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointX + wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).width) * size.width);
            regionPos[3] = (int) ((wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointY + wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).height) * size.height);
        } else {
            regionPos[0] = (int) (wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointX * size.height);
            regionPos[1] = (int) (wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointY * size.width);
            regionPos[2] = (int) ((wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointX + wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).width) * size.height);
            regionPos[3] = (int) ((wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).leftPointY + wlci.fieldType
                    .get(wlci.template.get(selectedTemplateTypePosition).templateType)
                    .get(ViewfinderView.fieldsPosition).height) * size.width);
        }
         return  regionPos;
    }
    public VINRecogResult startOcr(VINRecogParameter vinRecogParameter){

        VINRecogResult vinRecogResult=new VINRecogResult();
       int position=0;
       String Imagepath,SavePicPath;
       int[] nCharCount=new int[1];
       int returnResult=-1;
       boolean isToastShow=true;
        // 第一次加载布局 计算识别区域 也用于识别模板切换时调用
        if(recogBinder==null){
            return null;
        }
        if (vinRecogParameter.isFirstProgram) {
            regionPos=setRegion(vinRecogParameter.islandscape,vinRecogParameter.wlci,vinRecogParameter.selectedTemplateTypePosition,vinRecogParameter.size);
            recogBinder
                    .SetCurrentTemplate(vinRecogParameter.wlci.fieldType
                            .get(vinRecogParameter.wlci.template
                                    .get(vinRecogParameter.selectedTemplateTypePosition).templateType)
                            .get(ViewfinderView.fieldsPosition).ocrId);
            position = ViewfinderView.fieldsPosition;
            recogBinder.SetROI(regionPos, vinRecogParameter.size.width, vinRecogParameter.size.height);
            vinRecogParameter.isFirstProgram = false;

        }
        // 点击拍照按钮 保存图片识别，强制跳过未自动识别条目
        if (vinRecogParameter.isTakePic) {
            Imagepath = savePicture(vinRecogParameter.data,1,vinRecogParameter.size,vinRecogParameter.rotation);
            if (Imagepath != null && !"".equals(Imagepath)) {
                // 根据图片路径 加载图片
                recogBinder.LoadImageFile(Imagepath,0);
                recogBinder.Recognize(Devcode.devcode, Devcode.importTempalgeID);
                String recogResultString = recogBinder.GetResults(nCharCount);
                // 识别区域内图片
                if ((recogResultString != null && !recogResultString
                        .equals(""))) {
                    // 有识别结果时，保存识别核心裁切到的图像
                    SavePicPath = saveROIPicture(vinRecogParameter.data, false);
                } else {
                    // 未识别到结果，此时保存识别区域ROI内的图像
                    recogResultString = " ";
                    SavePicPath = saveROIPicture(vinRecogParameter.data, true);
                }
                if (SavePicPath != null && !"".equals(SavePicPath)) {
                    if (SharedPreferencesHelper.getBoolean(
                            context.getApplicationContext(), "upload", false)) {
                        vinRecogResult.httpContent =new String[]{SavePicPath,""};
                        new WriteToPCTask(context)
                                .execute(vinRecogResult.httpContent);
                    }
                }
                File orignPicFile=new File(Imagepath);
                if(orignPicFile.exists()){
                    orignPicFile.delete();
                }

                vinRecogResult.result=vinRecogParameter.wlci.fieldType
                        .get(vinRecogParameter.wlci.template
                                .get(vinRecogParameter.selectedTemplateTypePosition).templateType)
                        .get(ViewfinderView.fieldsPosition).name
                        + ":" + recogResultString;
                vinRecogResult.savePath.add(position, SavePicPath);
            }
            return vinRecogResult;
        } else {
            // 加载视频流数据源
            if (vinRecogParameter.rotation == 90) {
                recogBinder
                        .LoadStreamNV21(vinRecogParameter.data, vinRecogParameter.size.width, vinRecogParameter.size.height, 1);
            } else if (vinRecogParameter.rotation == 0) {
                recogBinder
                        .LoadStreamNV21(vinRecogParameter.data, vinRecogParameter.size.width, vinRecogParameter.size.height, 0);
            } else if (vinRecogParameter.rotation == 180) {
                recogBinder
                        .LoadStreamNV21(vinRecogParameter.data, vinRecogParameter.size.width, vinRecogParameter.size.height, 2);
            } else {
                recogBinder
                        .LoadStreamNV21(vinRecogParameter.data, vinRecogParameter.size.width, vinRecogParameter.size.height, 3);
            }
        }

        // 开始识别
        returnResult = recogBinder
                .Recognize(
                        Devcode.devcode,
                        vinRecogParameter.wlci.fieldType
                                .get(vinRecogParameter.wlci.template
                                        .get(vinRecogParameter.selectedTemplateTypePosition).templateType)
                                .get(ViewfinderView.fieldsPosition).ocrId);

        if (returnResult == 0) {
            // 获取识别结果
            String recogResultString = recogBinder.GetResults(nCharCount);
            Log.i("string","---------结果"+recogResultString+"返回值:"+ RecogService.response);
            if ((recogResultString != null && !recogResultString.equals("") && nCharCount[0] > 0&& RecogService.response==0)) {
                if ((recogResultString != null && !recogResultString
                        .equals(""))) {
                    savePicture(vinRecogParameter.data,0,vinRecogParameter.size,vinRecogParameter.rotation);
                    // 有识别结果时，保存识别核心裁切到的图像
                    SavePicPath = saveROIPicture(vinRecogParameter.data, false);
                } else {
                    // 未识别到结果，此时保存识别区域ROI内的图像
                    recogResultString = " ";
                    SavePicPath = saveROIPicture(vinRecogParameter.data, true);
                }
                if (SavePicPath != null && !"".equals(SavePicPath)) {
                    if (SharedPreferencesHelper.getBoolean(
                           context.getApplicationContext(), "upload", false)) {
                        vinRecogResult.httpContent =new String[]{SavePicPath,""};
                        new WriteToPCTask(context)
                                .execute( vinRecogResult.httpContent);
                    }
                }
                // 将识别结果 赋值到识别结果集合中 以便完成识别后跳转传输
                vinRecogResult.result=vinRecogParameter.wlci.fieldType
                        .get(vinRecogParameter.wlci.template
                                .get(vinRecogParameter.selectedTemplateTypePosition).templateType)
                        .get(ViewfinderView.fieldsPosition).name
                        + ":" + recogResultString;
                vinRecogResult.savePath.add(position, SavePicPath);
            }

            // 测试 存储每一帧图像
            // savePicture(data, recogResultString);
            return vinRecogResult;
        } else {
            if (isToastShow) {
                error=returnResult;
            }
            return null;
        }
    }

    public void freeKernalOpera(Context context){
        if (recogBinder != null) {
            context.unbindService(recogConn);
            recogBinder = null;
        }
    }
}
