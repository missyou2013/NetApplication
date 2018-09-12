package net.com.mvp.ac.commons;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 记录PDA的LOG文件
 */

public class PDALogUtils {

    /*
    * 创建txt格式的log文件
    *
    * CheckMode 检测模式
    * 0：外检
    * 2：底盘动态
    * */
    public static void createLogFile(int CheckMode, byte[] bytes) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory() + "/PDALogs/";
            switch (CheckMode) {
                case 0:
                    filePath = filePath + "WaiJian/" + DateUtil.getFormatToday() + ".txt";
                    break;
                case 1:
                    filePath = filePath + "Lushi/" + DateUtil.getFormatToday() + ".txt";
                    break;
                case 2:
                    filePath = filePath + "DiPanDongTai/" + DateUtil.getFormatToday() + ".txt";
                    break;
                case 4:
                    filePath = filePath + "Crash/" + DateUtil.getFormatToday() + ".txt";
                    break;
                case 3:
                    filePath = filePath + "DiaoDu/" + DateUtil.getFormatToday() + ".txt";
                    break;
                case 5:
                    filePath = filePath + "WebServices/" + DateUtil.getFormatToday() + ".txt";
                    break;
            }
            File logFile = new File(filePath);
            if (!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdirs();
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            RandomAccessFile raf = null;
            try {
//                //新建一个FileOutputStream()，把文件的路径传进去
//                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//                //给定一个字符串，将其转换成字节数组
////                 bytes = "=======PDA-LOG=====".getBytes();
//                //通过输出流对象写入字节数组
//                fileOutputStream.write(bytes);
//                //关流
//                fileOutputStream.close();

                //如果为追加则在原来的基础上继续写文件
                raf = new RandomAccessFile(logFile, "rw");
                raf.seek(logFile.length());
                raf.write("\n".getBytes());
                raf.write(DateUtil.currentTime().getBytes());
                raf.write("\n".getBytes());
                raf.write(bytes);
                raf.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("tag", "log-请确认已经插入SD卡");
        }
    }

    public static void bufferSave(String msg, File file) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file, true));
            bfw.write(msg);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
        * 此方法为android程序写入sd文件文件，用到了android-annotation的支持库@
             * @param buffer   写入文件的内容
     * @param folder   保存文件的文件夹名称,如log；可为null，默认保存在sd卡根目录
     * @param fileName 文件名称，默认app_log.txt
     * @param append   是否追加写入，true为追加写入，false为重写文件
     * @param autoLine 针对追加模式，true为增加时换行，false为增加时不换行
     */
    public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer,
                                                      @Nullable final String folder,
                                                      @Nullable final String fileName,
                                                      final boolean append, final boolean autoLine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
                String folderPath = "";
                if (sdCardExist) {
                    //TextUtils为android自带的帮助类
                    if (TextUtils.isEmpty(folder)) {
                        //如果folder为空，则直接保存在sd卡的根目录
                        folderPath = Environment.getExternalStorageDirectory()
                                + File.separator;
                    } else {
                        folderPath = Environment.getExternalStorageDirectory()
                                + File.separator + folder + File.separator;
                    }
                } else {
                    return;
                }

                File fileDir = new File(folderPath);
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        return;
                    }
                }
                File file;
                //判断文件名是否为空
                if (TextUtils.isEmpty(fileName)) {
                    file = new File(folderPath + "app_log.txt");
                } else {
                    file = new File(folderPath + fileName);
                }
                RandomAccessFile raf = null;
                FileOutputStream out = null;
                try {
                    if (append) {
                        //如果为追加则在原来的基础上继续写文件
                        raf = new RandomAccessFile(file, "rw");
                        raf.seek(file.length());
                        raf.write(buffer);
                        if (autoLine) {
                            raf.write("\n".getBytes());
                        }
                    } else {
                        //重写文件，覆盖掉原来的数据
                        out = new FileOutputStream(file);
                        out.write(buffer);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (raf != null) {
                            raf.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
