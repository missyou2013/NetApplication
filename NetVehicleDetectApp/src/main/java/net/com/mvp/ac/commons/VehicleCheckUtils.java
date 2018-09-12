package net.com.mvp.ac.commons;

/**
 * Created by Administrator on 2017/7/4.
 * 车辆信息校验，主要用于外检拍照各种类型车辆的判断
 */

public class VehicleCheckUtils {


    //把车辆颜色“AAA”样式的返回的字段转成“黑白灰”样式的
    public static String CarColorsFilter(String colorStr) {
        String car_color = "";
        String colors_1 = colorStr.substring(0, 1);
        String colors_2 = colorStr.substring(1, 2);
        String colors_3 = colorStr.substring(2, 3);
        car_color = car_color + CarscolorToString(colors_1);
        car_color = car_color + CarscolorToString(colors_2);
        car_color = car_color + CarscolorToString(colors_3);
        if (car_color.equals("无无") || car_color.equals("无无无")) {
            car_color = "无";
        }
        return car_color;
    }

    public static String CarscolorToString(String str) {
        String cl = "";
        if ("A".equalsIgnoreCase(str)) {
            cl = "白";
        } else if ("B".equalsIgnoreCase(str)) {
            cl = "灰";
        } else if ("C".equalsIgnoreCase(str)) {
            cl = "黄";
        } else if ("D".equalsIgnoreCase(str)) {
            cl = "粉";
        } else if ("E".equalsIgnoreCase(str)) {
            cl = "红";
        } else if ("F".equalsIgnoreCase(str)) {
            cl = "紫";
        } else if ("G".equalsIgnoreCase(str)) {
            cl = "绿";
        } else if ("H".equalsIgnoreCase(str)) {
            cl = "蓝";
        } else if ("I".equalsIgnoreCase(str)) {
            cl = "棕";
        } else if ("J".equalsIgnoreCase(str)) {
            cl = "黑";
        } else if ("Z".equalsIgnoreCase(str)) {
            cl = "其他";
        } else if ("0".equals(str)) {
            cl = "无";
        }
        return cl;
    }

    /***
     * 行驶记录装置拍照--校验
     *
     * param carCode  使用性质 特例：YYY代表半挂牵引车
     *
     * registerTime 注册登记时间
     *
     * totalMass  总质量
     *
     * ***/
    public static boolean checkDrivingRecordingMethod(String carCode, String registerTime,
                                                      int totalMass) {
        boolean result = false;
        if (carCode.equals("B")) {
            //公路客运
            result = true;
        } else if (carCode.equals("E")) {
//旅游客运
            result = true;
        } else if (carCode.equals("R")) {
//危化品运输
            result = true;
        } else if (carCode.equals("O")) {
//幼儿校车
            result = true;
        } else if (carCode.equals("P")) {
//小学生校车
            result = true;
        } else if (carCode.equals("Q")) {
//其他校车
            result = true;
        } else if (carCode.equals("S")) {
//中小学生校车
            result = true;
        } else if ("C".equals(carCode)) {
            //公共汽车-公交客运
            //公交客运----判断注册登记时间大于2013年3月1日
//            String registerTime = "/Date(694195200000)/";
            String str = registerTime.substring(6, registerTime.length() - 2).trim();
            String comparisonDate = "2013-03-01";
            String registerdate = DateUtil.getDateTimeFromMillisecond(Long.parseLong(str));
            String registerdateFormat = DateUtil.getDateForSurplus(registerdate);
            result = !DateUtil.isDateOneBigger(comparisonDate, registerdateFormat);
//            result==false;则表示需要行驶记录装置拍照
            UtilsLog.e("DateUtil.isDateOneBigger(comparisonDate, registerdateFormat)==" + DateUtil.isDateOneBigger(comparisonDate, registerdateFormat));
        } else if ("F".equals(carCode)) {
            //货车
            result = totalMass > 12000;
        } else if ("YYY".equals(carCode)) {
            //半挂牵引车
            result = true;
        }
        return result;
    }


    //判断是否是半挂牵引车
    //cartype 车辆类型
    public static boolean IsBanGuaQianYinChe(String cartype) {
        boolean result = false;
        String[] usetype = {"Q11", "Q21", "Q31"};
        for (int i = 0; i < usetype.length; i++) {
            if (cartype.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }

    //判断是否是挂车
    //carType  车辆类型
    public static boolean IsGuaChe(String carType) {
        boolean result = false;
        //挂车
        String[] usetype = {"B11", "B12", "B13", "B14", "B15", "B16", "B17"
                , "B18", "B19", "B1A", "B1B", "B1C", "B1D", "B1E"
                , "B1F", "B1G", "B1H", "B1J", "B1K", "B1U", "B1W", "B21"
                , "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29"
                , "B2A", "B2B", "B2C", "B2D", "B2E", "B2F", "B2G", "B2H"
                , "B2J", "B2K", "B2U", "B2W", "B31", "B32", "B33", "B34"
                , "B35", "B36", "B37", "B38", "B39", "B3C", "B3D", "B3E"
                , "B3F", "B3G", "B3H", "B3J", "B3K", "B3U", "B3W"
                , "G11", "G12", "G13", "G14", "G15", "G16", "G17", "G18"
                , "G19", "G1A", "G1B", "G1C", "G1D", "G1E"
                , "G1F", "G21", "G22", "G23", "G24", "G25"
                , "G26", "G27", "G28", "G29", "G2A", "G2B"
                , "G2C", "G2D", "G2E", "G2F", "G31", "G32"
                , "G33", "G34", "G35", "G36", "G37", "G38"
                , "G3A", "G3B", "G3C", "G3D", "G3E", "G3F"};
        for (int i = 0; i < usetype.length; i++) {
            if (carType.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }

    //判断是否是校车
    //carCode  使用性质
    public static boolean IsSchoolBus(String useCode) {
        boolean result = false;
        if (useCode.equals("O")) {
//幼儿校车
            result = true;
        } else if (useCode.equals("P")) {
//小学生校车
            result = true;
        } else if (useCode.equals("Q")) {
//其他校车
            result = true;
        } else if (useCode.equals("S")) {
//中小学生校车
            result = true;
        }
        return result;
    }

    //判断是否是危险货物运输车
    //useCode  使用性质
    public static boolean IsWeiXianyunshuche(String useCode) {
        boolean result = false;
        if (useCode.equals("R")) {
//危险货物运输车
            result = true;
        }
        return result;
    }

    //判断是否是教练车
    //useCode  使用性质
    public static boolean IsJiaoLianche(String useCode) {
        boolean result = false;
        if (useCode.equals("N")) {
//教练车
            result = true;
        }
        return result;
    }

    //判断是否是客车
//    type 车辆类型
    public static boolean IsKeche(String type) {
        boolean result = false;
        //客车
        String[] usetype = {"K11", "K12", "K13", "K14", "K15", "K17", "K21",
                "K22", "K23", "K24", "K25", "K27", "K31", "K32", "K34", "K41", "K42"};
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }

    //判断是否是货车
//    type 车辆类型
    public static boolean IsHuoche(String type) {
        boolean result = false;
        //货车
        String[] usetype = {"H11", "H12", "H13", "H14", "H15", "H17", "H18", "H19", "H1B"
                , "H1C", "H1D", "H1E", "H1F", "H1G", "H21", "H22", "H23", "H24"
                , "H25", "H27", "H28", "H29", "H2B", "H2C", "H2D", "H2E"
                , "H2F", "H2G", "H31", "H32", "H33", "H34", "H35", "H37", "H38"
                , "H39", "H3B", "H3C", "H3D", "H3E", "H3F", "H3G", "H41", "H42"
                , "H43", "H44", "H45", "H46", "H47", "H4B", "H4C", "H4F", "H4G"
                , "H51", "H52", "H53", "H54", "H55", "H5B", "H5C"};
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }

    //判断是否是专项作业车
//    type 车辆类型
    public static boolean IsZhuanXiangzuoyeche(String type) {
        boolean result = false;
        //专项作业车
        String[] usetype = {"Z11", "Z12", "Z21", "Z22", "Z31", "Z32", "Z41", "Z42", "Z51"
                , "Z52", "Z71", "Z72"
                , "B1A", "B1J", "B2A", "B2J", "B38", "B3J", "G19", "G1F", "G29", "G2F", "G38", "G3F"
        };
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }


    //灭火器拍照的检验
    //useCode  使用性质
//    type 车辆类型
    public static boolean checkExtinguisher(String type, String useCode) {
        boolean result = false;
        //客车
        String[] usetype = {"K11", "K12", "K13", "K14", "K15", "K17", "K21",
                "K22", "K23", "K24", "K25", "K27", "K31", "K32", "K34", "K41", "K42"};
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        if (useCode.equals("R")) {
            result = true;
        }
        return result;
    }

    //    车厢内部拍照
//    type 车辆类型
    public static boolean checkCarInterior(String type) {
        boolean result = false;
        //客车,厢式货车，棚式货车，挂车
        String[] usetype = {"K11", "K12", "K13", "K14", "K15", "K17", "K21",
                "K22", "K23", "K24", "K25", "K27", "K31", "K32", "K34", "K41", "K42"
                , "B12", "B22", "B32", "G12", "G1A", "G22", "G2A", "G32", "G3A", "H12"
                , "H1B", "H22"
                , "H2B", "H32", "H3B", "H42", "H4B", "H52", "H5B"
                , "B11", "B12", "B13", "B14", "B15", "B16", "B17"
                , "B18", "B19", "B1A", "B1B", "B1C", "B1D", "B1E"
                , "B1F", "B1G", "B1H", "B1J", "B1K", "B1U", "B1W", "B21"
                , "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29"
                , "B2A", "B2B", "B2C", "B2D", "B2E", "B2F", "B2G", "B2H"
                , "B2J", "B2K", "B2U", "B2W", "B31", "B32", "B33", "B34"
                , "B35", "B36", "B37", "B38", "B39", "B3C", "B3D", "B3E"
                , "B3F", "B3G", "B3H", "B3J", "B3K", "B3U", "B3W"
                , "G11", "G12", "G13", "G14", "G15", "G16", "G17", "G18"
                , "G19", "G1A", "G1B", "G1C", "G1D", "G1E"
                , "G1F", "G21", "G22", "G23", "G24", "G25"
                , "G26", "G27", "G28", "G29", "G2A", "G2B"
                , "G2C", "G2D", "G2E", "G2F", "G31", "G32"
                , "G33", "G34", "G35", "G36", "G37", "G38"
                , "G3A", "G3B", "G3C", "G3D", "G3E", "G3F"
        };
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }

    // 车辆正后方拍照
    //useCode  使用性质
//    type 车辆类型
    public static boolean checkBehindCar(String type, String useCode) {
        boolean result = false;
        //挂车,货车，专项作业车，校车
        String[] usetype = {"B11", "B12", "B13", "B14", "B15", "B16", "B17"
                , "B18", "B19", "B1A", "B1B", "B1C", "B1D", "B1E"
                , "B1F", "B1G", "B1H", "B1J", "B1K", "B1U", "B1W", "B21"
                , "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29"
                , "B2A", "B2B", "B2C", "B2D", "B2E", "B2F", "B2G", "B2H"
                , "B2J", "B2K", "B2U", "B2W", "B31", "B32", "B33", "B34"
                , "B35", "B36", "B37", "B38", "B39", "B3C", "B3D", "B3E"
                , "B3F", "B3G", "B3H", "B3J", "B3K", "B3U", "B3W"
                , "G11", "G12", "G13", "G14", "G15", "G16", "G17", "G18"
                , "G19", "G1A", "G1B", "G1C", "G1D", "G1E"
                , "G1F", "G21", "G22", "G23", "G24", "G25"
                , "G26", "G27", "G28", "G29", "G2A", "G2B"
                , "G2C", "G2D", "G2E", "G2F", "G31", "G32"
                , "G33", "G34", "G35", "G36", "G37", "G38"
                , "G3A", "G3B", "G3C", "G3D", "G3E", "G3F"
                , "H11", "H12", "H13", "H14", "H15", "H17", "H18", "H19", "H1B"
                , "H1C", "H1D", "H1E", "H1F", "H1G", "H21", "H22", "H23", "H24"
                , "H25", "H27", "H28", "H29", "H2B", "H2C", "H2D", "H2E"
                , "H2F", "H2G", "H31", "H32", "H33", "H34", "H35", "H37", "H38"
                , "H39", "H3B", "H3C", "H3D", "H3E", "H3F", "H3G", "H41", "H42"
                , "H43", "H44", "H45", "H46", "H47", "H4B", "H4C", "H4F", "H4G"
                , "H51", "H52", "H53", "H54", "H55", "H5B", "H5C"
                , "Z11", "Z12", "Z21", "Z22", "Z31", "Z32", "Z41", "Z42", "Z51"
                , "Z52", "Z71", "Z72"
        };
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        if (useCode.equals("O")) {
//幼儿校车
            result = true;
        } else if (useCode.equals("P")) {
//小学生校车
            result = true;
        } else if (useCode.equals("Q")) {
//其他校车
            result = true;
        } else if (useCode.equals("S")) {
//中小学生校车
            result = true;
        }
        return result;
    }

    //    车厢液压臂拍照
    //    type 车辆类型
    public static boolean checkCarHydraulicArm(String type) {
        boolean result = false;
        //重中轻自卸货车，重中轻自卸半挂车，重中轻自卸全挂车
        String[] usetype = {"H17", "H1B","H1C", "H1D","H1E",
                "H1F","H1G", "H27","H2B", "H2C","H2D",
                "H2E","H2F", "H2G","H37", "H3B","H3C", "H3D",
                "H3E", "H3F","H3G",
                "B16","B1D", "B1E","B1F", "B1G",
                "B1H", "B1J","B1K", "B26","B2D", "B2E","B2F", "B2G",
                "B2H", "B2J","B2K", "B35","B3D", "B3E","B3F", "B3G",
                "B3H", "B3J","B3K",
                "G16","G1A", "G1B","G1C", "G1D",
                "G1E", "G1F","G26", "G2A","G2B", "G2C","G2D", "G2E",
                "G2F", "G35","G3A", "G3B","G3C", "G3D","G3E", "G3F"
        };
        for (int i = 0; i < usetype.length; i++) {
            if (type.equals(usetype[i])) {
                result = true;
            }
        }
        return result;
    }


}
