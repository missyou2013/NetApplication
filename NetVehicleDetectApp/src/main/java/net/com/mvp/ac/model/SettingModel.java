package net.com.mvp.ac.model;

import java.io.Serializable;

/**
 * Name: SettingModel
 * Comment: 系統設置model
 * Date: 2017-06-14 08:51
 */
public class SettingModel implements Serializable {

    //站端IP
    private String IP;
    //端口号
    private String Port;
    //机器码
    private String jiqima;
    //授权码
    private String shouquanma;
    //数据库调试信息
    private String shuuku_tiaoshi_xinxi;
    //检测模式
    private String jiance_mode;
    //照片添加时间水印
    private String zhaopian_shuiyin;
    //检测项目使用默认
    private String jiance_xiangmu_moren;
    //上传使用编码
    private String bianma;
    //时间限制
    private String shijian_xianzhi;
    //默认车牌前缀左
    private String chepai_left;
    //默认车牌前缀右
    private String chepai_right;
    //是否裁剪图片
    private String photo_clip;

    public String getPhoto_clip() {
        return photo_clip;
    }

    public void setPhoto_clip(String photo_clip) {
        this.photo_clip = photo_clip;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getJiqima() {
        return jiqima;
    }

    public void setJiqima(String jiqima) {
        this.jiqima = jiqima;
    }

    public String getShouquanma() {
        return shouquanma;
    }

    public void setShouquanma(String shouquanma) {
        this.shouquanma = shouquanma;
    }

    public String getShuuku_tiaoshi_xinxi() {
        return shuuku_tiaoshi_xinxi;
    }

    public void setShuuku_tiaoshi_xinxi(String shuuku_tiaoshi_xinxi) {
        this.shuuku_tiaoshi_xinxi = shuuku_tiaoshi_xinxi;
    }

    public String getJiance_mode() {
        return jiance_mode;
    }

    public void setJiance_mode(String jiance_mode) {
        this.jiance_mode = jiance_mode;
    }

    public String getZhaopian_shuiyin() {
        return zhaopian_shuiyin;
    }

    public void setZhaopian_shuiyin(String zhaopian_shuiyin) {
        this.zhaopian_shuiyin = zhaopian_shuiyin;
    }

    public String getJiance_xiangmu_moren() {
        return jiance_xiangmu_moren;
    }

    public void setJiance_xiangmu_moren(String jiance_xiangmu_moren) {
        this.jiance_xiangmu_moren = jiance_xiangmu_moren;
    }

    public String getBianma() {
        return bianma;
    }

    public void setBianma(String bianma) {
        this.bianma = bianma;
    }

    public String getShijian_xianzhi() {
        return shijian_xianzhi;
    }

    public void setShijian_xianzhi(String shijian_xianzhi) {
        this.shijian_xianzhi = shijian_xianzhi;
    }

    public String getChepai_left() {
        return chepai_left;
    }

    public void setChepai_left(String chepai_left) {
        this.chepai_left = chepai_left;
    }

    public String getChepai_right() {
        return chepai_right;
    }

    public void setChepai_right(String chepai_right) {
        this.chepai_right = chepai_right;
    }
}
