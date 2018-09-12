package net.com.mvp.ac.commons;

import android.os.Environment;

import java.util.List;

/**
 * @ClassName: Constants
 * @Description:静??常量
 * @author: liuxj
 * @date: 2014-4-14 上午11:47:53
 */
public class Constants {
    //	public static CookieStore cookieStore = null;
    // 选择的联系人列表id
    public static List<String> lianxiren_id_lsit;
    public static List<Long> lianxiren_id;
    // 选择的联系人列表name
    public static List<String> lianxiren_name_lsit;
    // 是否登录
    public static boolean isLogin = false;
    // 图片宽高的限制条??
    public static String PICTURE_W_H = "!w150h150";

    // 加密的开关，false:未加??---true:加密
    public static boolean isEncryption = false;
    public static boolean ishasnews = false;

    public static String log_tag = "shequ";

    // 下拉刷新相关
    public static int curPageForRestList = 1;

    public static int curPageForCarList = 1;

    public static String PACKAGE_NAME = "com.kuanyu.spy.app.activity";

    public static String SD_PATH = Environment.getExternalStorageDirectory()
            .toString();

    public static String FILE_PATH = SD_PATH + "/" + PACKAGE_NAME;

    public static String FILE_CACHE = FILE_PATH + "/dataCache";

    public static String IMAGE_CACHE = FILE_PATH + "/imgCache/";

    // 百度地图map--key
    public static final String BAIDU_MAP_KEY = "CC1bfcc3c10ccc257d71f5c2e1c449f2";

    public static String API_KEY = "";

    public static String SECRET_KEY = "";

    public static String TIME_STAMP = String
            .valueOf(System.currentTimeMillis());
    // apikey
    // secretKey
    // timestamp
    public static String SHIPIN_USERNAME = "cuihuxinyuan";
    public static String SHIPIN_PASSWORD = "123456";
    public static final String APP_ID = "wxe544423c9955d614";// wxe544423c9955d614
    //检测模式
//    public static String[] MY_ACCOUNT_MODEL_LIST = {"外检", "路试", "底盘动态","调度"};
    public static String[] MY_ACCOUNT_MODEL_LIST = {"外检", "路试", "底盘动态" };

    //是否
    public static String[] YES_NO = {"否", "是"};

    //合格，不合格
    public static String[] HEGE_BUHEGE = {"未检测", "合格", "不合格"};

    //操纵力
    public static String[] CAOZONGLI = {"手操纵", "脚操纵"};

    //省会简称表
    public static String[] PROVINCE_JIANCHEN = {"测", "京", "津", "冀", "晋", "蒙", "辽", "吉",
            "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼",
            "川", "贵", "云", "渝", "藏", "陕", "甘", "青", "宁", "新", "港", "澳", "台"};

    //字母表
    public static String[] LETTER_TABLE = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    //检测类型
    public static String[] JIANCE_TYPE = {"初检", "复检"};

    //坡度
    public static String[] GRADIENT_TYPE = {"20%", "15%"};

    //稳定性
    public static String[] STABILITY_TYPE = {"未跑偏", "左跑偏", "右跑偏"};

    //外检车道或车间线号
    public static String[] WAIJIAN_CHEDAO_CHEJIAN_XIANHAO = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    //检测结果大类
    public static String[] JIANCE_RESULT_GROUP = {"号牌号码/车辆类型", "车辆品牌/型号", "车辆识别代码(或整车出厂编号)",
            "发动机号码(或电动机号码)", "车辆颜色和外形",
            "外廓尺寸", "轴距", "整备质量", "核定载人数", "核定载质量", "栏板高度", "后轴钢板弹簧片数",
            "客车应急出口", "客车乘客通道和引道", "货箱", "车身外观",
            "外观标识、标注和标牌", "外观照明和信号灯", "轮胎", "号牌及号牌安装", "加装/改装灯具",
            "汽车安全带", "机动车用三角警告牌", "灭火器", "行驶记录装置", "车身反光标识", "车辆尾部标志板",
            "侧后防护装置", "应急锤", "急救箱", "限速功能或限速装置", "防抱死制动装置", "辅助制动装置", "盘式制动器",
            "紧急切断装置",
            "发送机舱自动灭火装置",
            "手动机械断电开关",
            "副制动踏板",
            "校车标志灯和校车停车指示标志牌",
            "危险货物运输车标志",
            "肢体残疾人操纵辅助装置",
            "联网查询"};
    //检测结果具体条目
    public static String[] JIANCE_RESULT_CHILD = {"初检", "复检"};

    //底盘动态检验项目
    public static String[] JDIPAN_DONGTAI_XIANGMU = {"转向系", "传动系", "制动系", "仪表和指示器"};

    //车道宽度
    public static String[] CHEDAO_WIDTH = {"2.5", "3","0.5"};

    //轴数
    public static String[] ZHOUSHOU_LIST= {"1", "2", "3", "4", "5", "6"};
}
