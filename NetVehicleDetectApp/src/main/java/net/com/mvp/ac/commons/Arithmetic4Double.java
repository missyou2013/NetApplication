package net.com.mvp.ac.commons;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @ClassName: Arithmetic4Double
 * @Description: 数字运算工具??
 * @author: liuxj
 * @date: 2014-4-21 下午7:55:45
 */
public class Arithmetic4Double {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	// ????方法均用静??方法实现，不允许实例??
	private Arithmetic4Double() {
	}

	/**
	 * 实现浮点数的加法运算功能
	 *
	 * @param v1
	 *            加数1
	 * @param v2
	 *            加数2
	 * @return v1+v2的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 实现浮点数的减法运算功能
	 *
	 * @param v1
	 *            被减??
	 * @param v2
	 *            减数
	 * @return v1-v2的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 实现浮点数的乘法运算功能
	 *
	 * @param v1
	 *            被乘??
	 * @param v2
	 *            乘数
	 * @return v1×v2的积
	 */
	public static double multi(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 实现浮点数的除法运算功能 当发生除不尽的情况时，精确到小数点以后DEF_DIV_SCALE??默认??0??，后面的位数进行四舍五入??
	 *
	 * @param v1
	 *            被除??
	 * @param v2
	 *            除数
	 * @return v1/v2的商
	 */
	public static double div(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 实现浮点数的除法运算功能 当发生除不尽的情况时，精确到小数点以后scale位，后面的位数进行四舍五入??
	 *
	 * @param v1
	 *            被除??
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示????精确到小数点以后几位
	 * @return v1/v2的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入功能
	 *
	 * @param v
	 *            ????四舍五入的数??
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 保留小数点后两位数字
	 * @param v
	 * @return
	 */
	public static String keepTwo(double v){
		DecimalFormat df2 = new DecimalFormat("0.00");//保留2??
//		DecimalFormat df2 = new DecimalFormat("###.000");//保留4??
//		System.out.println(df2.format(doube_var));//doube_var不能是字符串
		return df2.format(v);
	}
	
 
}
