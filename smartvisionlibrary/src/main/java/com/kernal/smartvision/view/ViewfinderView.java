package com.kernal.smartvision.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import com.kernal.smartvisionocr.model.ConfigParamsModel;
import com.kernal.smartvisionocr.utils.KernalLSCXMLInformation;

import java.util.List;

/**
 * 项目名称:慧视OCR 类名称；ViewfinderView 类描述：扫描框 创建人：黄震 创建时间：20160322 修改人：${user}
 * 修改时间：${date} ${time} 修改备注：
 */
public final class ViewfinderView extends View {

	/**
	 * 刷新界面的时间
	 */
	private static final long ANIMATION_DELAY = 25L;

	/**
	 * 四周边框的宽度
	 */
	private static final int FRAME_LINE_WIDTH = 4;
	private Rect frame;
	private int width;
	public int height;
	private Paint paint;
	public KernalLSCXMLInformation wlci;
	public static int fieldsPosition = 0;// 输出结果的先后顺序
	public List<ConfigParamsModel> configParamsModel;
	private Context context;
	private static final int[] SCANNER_ALPHA = { 0, 64 };
	private int scannerAlpha;
	private Bitmap bitmap;
	private float count = 1;
	private boolean isAdd = true;
	private DisplayMetrics dm;
	private boolean bool;


	public ViewfinderView(Context context, KernalLSCXMLInformation wlci,
                          String type, boolean bol) {
		super(context);
		this.wlci = wlci;
		paint = new Paint();
		if(type!=null&&!type.equals("")) {
			configParamsModel = wlci.fieldType.get(type);
		}
		this.context = context;
		scannerAlpha = 0;
		count = 1;
		isAdd = true;
		this.bitmap = BitmapFactory.decodeResource(getResources(),
				context.getResources().getIdentifier("scanline", "drawable", context.getPackageName()));
		// 获取当前屏幕
        dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        this.bool = bol;
	}


	@Override
	public void onDraw(Canvas canvas) {
		width = canvas.getWidth();
		height = canvas.getHeight();

//		 System.out.println("刷新:"+width+"    height:"+height);
		if(configParamsModel!=null) {
            //System.out.println("刷新:"+fieldsPosition);
            String a = configParamsModel.get(fieldsPosition).nameTextColor.split("_")[0];
            String r = configParamsModel.get(fieldsPosition).nameTextColor.split("_")[1];
            String g = configParamsModel.get(fieldsPosition).nameTextColor.split("_")[2];
            String b = configParamsModel.get(fieldsPosition).nameTextColor.split("_")[3];
            paint.setColor(Color.argb(Integer.valueOf(a), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b)));
            paint.setTextSize(Float.valueOf(configParamsModel.get(fieldsPosition).nameTextSize));

//            if (configParamsModel.get(fieldsPosition).leftPointX == 0.0 && configParamsModel.get(fieldsPosition).leftPointY == 0.0) {
//                /**
//                 * 这个矩形就是中间显示的那个框框
//                 * true :横屏
//                 */  
//            	if(bool){
//            		 frame = new Rect((int) (0.1 * width), (int) (height * 0.4), (int) ((configParamsModel.get(fieldsPosition).width + 0.1) * width), (int) (height * (0.4 + configParamsModel.get(fieldsPosition).height)));
//            	}else{
//            		 frame = new Rect((int) (0.025 * width), (int) (height * 0.4), (int) ((configParamsModel.get(fieldsPosition).width + 0.025) * width), (int) (height * (0.4 + configParamsModel.get(fieldsPosition).height)));            
//            	}
//                               
//            } else {
                /**
                 * 这个矩形就是中间显示的那个框框
                 */
                frame = new Rect((int) (configParamsModel.get(fieldsPosition).leftPointX * width),
						(int) (height * configParamsModel.get(fieldsPosition).leftPointY),
						(int) ((configParamsModel.get(fieldsPosition).leftPointX + configParamsModel.get(fieldsPosition).width) * width),
						(int) (height * (configParamsModel.get(fieldsPosition).leftPointY + configParamsModel.get(fieldsPosition).height)));
//            }

//			if(dm.densityDpi>320) {
//
//                canvas.drawText(context.getString(getResources().getIdentifier(
//                        "please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
//            }
//			else if(dm.densityDpi==320)
//            {
//                canvas.drawText(context.getString(getResources().getIdentifier(
//                        "please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.9), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
//            }else
//            {
//					paint.setTextSize(Float.valueOf(30));
//                if (configParamsModel.get(fieldsPosition).name.equals(context.getString(getResources().getIdentifier(
//                        "QRCode", "string", context.getPackageName())))) {
//                    canvas.drawText(context.getString(getResources().getIdentifier(
//                            "please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.85), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
//                }else{
//
//                    canvas.drawText(context.getString(getResources().getIdentifier(
//                            "please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.75), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
//                }
//
//            }
			if(dm.densityDpi>320) {

				canvas.drawText(context.getString(getResources().getIdentifier(
						"please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width), configParamsModel.get(fieldsPosition).namePositionY * height, paint);

			}
			else if(dm.densityDpi==320)
			{
				canvas.drawText(context.getString(getResources().getIdentifier(
						"please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.9), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
			}else
			{
				paint.setTextSize(Float.valueOf(30));
				if (configParamsModel.get(fieldsPosition).name.equals(context.getString(getResources().getIdentifier(
						"QRCode", "string", context.getPackageName())))) {
					canvas.drawText(context.getString(getResources().getIdentifier(
							"please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.85), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
				}else{

					canvas.drawText(context.getString(getResources().getIdentifier(
							"please_collect", "string", context.getPackageName())) + configParamsModel.get(fieldsPosition).name + " ", (int) (configParamsModel.get(fieldsPosition).namePositionX * width*0.75), configParamsModel.get(fieldsPosition).namePositionY * height, paint);
				}

			}
                if (frame == null) {
                return;
            }
            // 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
            // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
            paint.setColor(Color.argb(48, 0, 0, 0));
            canvas.drawRect(0, 0, width, frame.top, paint);
            canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1,
                    paint);
            canvas.drawRect(frame.right + 1, frame.top, width,
                    frame.bottom + 1, paint);
            canvas.drawRect(0, frame.bottom + 1, width, height, paint);
            a = configParamsModel.get(fieldsPosition).color.split("_")[0];
            r = configParamsModel.get(fieldsPosition).color.split("_")[1];
            g = configParamsModel.get(fieldsPosition).color.split("_")[2];
            b = configParamsModel.get(fieldsPosition).color.split("_")[3];
            // 绘制两个像素边宽的绿色线框
            paint.setColor(Color.argb(Integer.valueOf(a), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b)));
            canvas.drawRect(frame.left + FRAME_LINE_WIDTH - 2, frame.top,
                    frame.right - FRAME_LINE_WIDTH + 2, frame.top
                            + FRAME_LINE_WIDTH, paint);// 上边
            canvas.drawRect(frame.left + FRAME_LINE_WIDTH - 2, frame.top,
                    frame.left + FRAME_LINE_WIDTH + 2, frame.bottom
                            + FRAME_LINE_WIDTH, paint);// 左边
            canvas.drawRect(frame.right - FRAME_LINE_WIDTH - 2, frame.top,
                    frame.right - FRAME_LINE_WIDTH + 2, frame.bottom
                            + FRAME_LINE_WIDTH, paint);// 右边
            canvas.drawRect(frame.left + FRAME_LINE_WIDTH - 2,
                    frame.bottom, frame.right - FRAME_LINE_WIDTH + 2,
                    frame.bottom + FRAME_LINE_WIDTH, paint);// 底边


        }
		fresh();

	}

	public void fresh() {
		/**
		 * 当我们获得结果的时候，我们更新整个屏幕的内容
		 */

		postInvalidateDelayed(ANIMATION_DELAY, 0, 0, (int) (width * 0.8),
				height);
	}

}
