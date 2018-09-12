package com.kernal.smartvision.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.kernal.smartvisionocr.utils.Utills;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhen on 2016/2/3.
 */
public class CameraSetting {
	Camera.Parameters parameters;
	public int srcWidth, srcHeight;
	public int preWidth, preHeight;
	public int picWidth, picHeight;
	public int surfaceWidth, surfaceHeight;
	List<Camera.Size> list;
	private boolean isShowBorder = false;
	private Context context;
	public static int currentCameraId = -1;
	private Camera camera;
	private CameraSetting(Context context) {
		this.context = context;
		setScreenSize(context);
	}

	private static CameraSetting single = null;

	// 静态工厂方法
	public static CameraSetting getInstance(Context context) {
		if (single == null) {
			single = new CameraSetting(context);
		}
		return single;
	}
	public Camera open(int cameraId , Camera camera) {
		this.camera = camera;
	    int numCameras = Camera.getNumberOfCameras();
	    if (numCameras == 0) {
	      return null;
	    }
	    boolean explicitRequest = cameraId >= 0;
	    if (!explicitRequest) {	     
	      int index = 0;
	      while (index < numCameras) {
	        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
	        Camera.getCameraInfo(index, cameraInfo);
	        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
	          break;
	        }
	        index++;
	      }      
	      cameraId = index;
	    }
	    if (cameraId < numCameras) {	    
	    	camera = Camera.open(cameraId);
	    } else {
	      if (explicitRequest) {	    	
	    	  camera = null;
	      } else {	    	 
	    	  cameraId = 0;
	    	  camera = Camera.open(0);
	      }
	    }
	    currentCameraId = cameraId;
	    return camera;
	  } 
	 
	  public  int  setCameraDisplayOrientation(int uiRot) {
		     Camera.CameraInfo info =new Camera.CameraInfo();
		     camera.getCameraInfo(currentCameraId, info);		    
		     int degrees = 0;
		     switch (uiRot) {
		         case Surface.ROTATION_0: degrees = 0; break;
		         case Surface.ROTATION_90: degrees = 90; break;
		         case Surface.ROTATION_180: degrees = 180; break;
		         case Surface.ROTATION_270: degrees = 270; break;
		     }
		     int result;
		     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
		         result = (info.orientation + degrees) % 360;
		         result = (360 - result) % 360;  // compensate the mirror
		     } else {  // back-facing
		         result = (info.orientation - degrees + 360) % 360;
		     }		   		    
		     return result;
		 }
	/**
	 * @return ${return_type} 返回类型
	 * @throws
	 * @Title: 关闭相机
	 * @Description: 释放相机资源
	 */
	public Camera closeCamera(Camera camera) {
		try {
			if (camera != null) {
				camera.setPreviewCallback(null);
				camera.stopPreview();
				camera.release();
				camera = null;
			}
		} catch (Exception e) {
			Log.i("TAG", e.getMessage());
		}
		return camera;
	}

	/**
	 * @Title: ${enclosing_method}
	 * @Description: 打开闪光灯
	 * @param camera
	 *            相机对象
	 * @return void 返回类型
	 * @throws
	 */
	public void openCameraFlash(Camera camera) {
		try {
			if (camera == null)
				camera = Camera.open();
			Camera.Parameters parameters = camera.getParameters();
			List<String> flashList = parameters.getSupportedFlashModes();
			if (flashList != null
					&& flashList.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				parameters.setExposureCompensation((int)(parameters.getMinExposureCompensation()*0.7));
				camera.setParameters(parameters);
			} else {
				Toast.makeText(
						context,
						context.getString(context.getResources().getIdentifier(
								"unsupportflash", "string",
								context.getPackageName())), Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	};

	/**
	 * @Title: ${enclosing_method}
	 * @Description: 关闭闪光灯
	 * @param camera
	 *            相机对象
	 * @return void 返回类型
	 * @throws
	 */
	public void closedCameraFlash(Camera camera) {
		try {
			if (camera == null)
				camera = Camera.open();
			Camera.Parameters parameters = camera.getParameters();
			List<String> flashList = parameters.getSupportedFlashModes();
			if (flashList != null
					&& flashList.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				parameters.setExposureCompensation(0);
				camera.setParameters(parameters);
			} else {
				Toast.makeText(
						context,
						context.getString(context.getResources().getIdentifier(
								"unsupportflash", "string",
								context.getPackageName())), Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param currentActivity
	 * @param sizes
	 *            最理想的预览分辨率的宽和高
	 * @param targetRatio
	 * @return 获得最理想的预览尺寸
	 */
	public static Camera.Size getOptimalPreviewSize(Activity currentActivity,
													List<Camera.Size> sizes, double targetRatio) {
		// Use a very small tolerance because we want an exact match.
//		final double ASPECT_TOLERANCE = 0.001;
		final double ASPECT_TOLERANCE = 0.23;
		if (sizes == null) {
			return null;
		}
		for (int i = 0; i < sizes.size(); i++){
			Log.e("zhang:","支持的所有预览分辨率组:"+sizes.get(i).width+"    "+sizes.get(i).height);
		}
		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		// Because of bugs of overlay and layout, we sometimes will try to
		// layout the viewfinder in the portrait orientation and thus get the
		// wrong size of mSurfaceView. When we change the preview size, the
		// new overlay will be created before the old one closed, which causes
		// an exception. For now, just get the screen size

		Display display = currentActivity.getWindowManager()
				.getDefaultDisplay();

		int targetHeight = Math.min(display.getHeight(), display.getWidth());

		if (targetHeight <= 0) {
			// We don't know the size of SurfaceView, use screen height
			targetHeight = display.getHeight();
		}

		// Try to find an size match aspect ratio and size
		
		for (Camera.Size size : sizes) {
			//预览分辨的比
			if(size.width<=1280||size.height<=960){
				double ratio = (double) size.width / size.height;

				if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
					continue;
				}
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}

		}

		// Cannot find the one match the aspect ratio. This should not happen.
		// Ignore the requirement.
		if (optimalSize == null) {
			System.out.println("No preview size match the aspect ratio");
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		System.out.println("预览分辨率"+optimalSize.width  +"    "+optimalSize.height);
		return optimalSize;
	}

	/**
	 * 检查相机参数,以便启动自定义相机
	 */
	public List<Integer> checkCameraParameters(Camera camera, int width,
											   int height) {
		int srcwidth = 640;
		int srcheight = 480;
		List<Integer> list = new ArrayList<Integer>();
		try {

			if (camera != null) {
				// 读取支持的预览尺寸,优先选择640后320
				Camera.Parameters parameters = camera.getParameters();
				List<Camera.Size> PictureSizes = Utills.splitSize(
						parameters.get("picture-size-values"), camera);// parameters.getSupportedPictureSizes();
				if (width * 9 == height * 16) {
					for (int i = 0; i < PictureSizes.size(); i++) {
						if ((PictureSizes.get(i).width <= 2048 || PictureSizes
								.get(i).height <= 1536)
								&& ((PictureSizes.get(i).width * 9 == PictureSizes
										.get(i).height * 16) || PictureSizes
										.get(i).width * 3 == PictureSizes
										.get(i).height * 4)) {
							if (PictureSizes.get(i).width > srcwidth
									|| PictureSizes.get(i).height > srcheight) {
								srcwidth = PictureSizes.get(i).width;
								srcheight = PictureSizes.get(i).height;
							}

						}
					}
				} else {
					for (int i = 0; i < PictureSizes.size(); i++) {

						if (PictureSizes.get(i).width == 2048
								&& PictureSizes.get(i).height == 1536) {
							srcwidth = 2048;
							srcheight = 1536;

						}
						if (PictureSizes.get(i).width == 1920
								&& PictureSizes.get(i).height == 1080
								&& srcwidth < PictureSizes.get(i).width) {

							srcwidth = PictureSizes.get(i).width;
							srcheight = PictureSizes.get(i).height;

						}
						if (PictureSizes.get(i).width == 1600
								&& PictureSizes.get(i).height == 1200
								&& srcwidth < PictureSizes.get(i).width) {

							srcwidth = 1600;
							srcheight = 1200;

						}
						if (PictureSizes.get(i).width == 1280
								&& PictureSizes.get(i).height == 960
								&& srcwidth < PictureSizes.get(i).width) {

							srcwidth = 1280;
							srcheight = 960;

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		list.clear();
		list.add(0, srcwidth);
		list.add(1, srcheight);
		return list;
	}

	/**
	 * @param mDecorView
	 *            {tags} 设定文件
	 * @return ${return_type} 返回类型
	 * @throws
	 * @Title: 沉寂模式
	 * @Description: 隐藏虚拟按键
	 */
	@TargetApi(19)
	public void hiddenVirtualButtons(View mDecorView) {
		if (Build.VERSION.SDK_INT >= 19) {
			mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE);
		}
	}
	private int clamp(int x, int min, int max) {
		if (x > max) {
			return max;
		}
		if (x < min) {
			return min;
		}
		return x;
	}

	public Camera.Size getResolution(Camera camera) {
		Camera.Parameters params = camera.getParameters();
		Camera.Size s = params.getPreviewSize();
		return s;
	}

	/**
	 *
	 * @param camera
	 * @param x 为对应到图像上的点的横坐标
	 * @param y 为对应到图像上的点的纵坐标
	 * @param coefficient
	 * @param isPortrait
	 * @return
	 */
	public Rect calculateTapArea(Camera camera,float x, float y, float coefficient,boolean isPortrait) {
		float focusAreaSizeX = 400;
		float focusAreaSizeY = 200;
		Camera.Size sise=getResolution(camera);
		RectF rectF;
		int left,top;
		int areaSizeX = Float.valueOf(focusAreaSizeX * coefficient).intValue();
		int areaSizeY = Float.valueOf(focusAreaSizeY * coefficient).intValue();
		if(sise.width<areaSizeX){
			areaSizeX=sise.width;
		}
		if(sise.height<areaSizeY){
			areaSizeY=sise.height;
		}
		Log.i("calculateTapArea", "areaSize--->" + areaSizeX);//300
		Log.i("calculateTapArea", "x--->" + x + ",,,y--->" + y);//对的
		int centerX =0;
		int centerY =0;
		if(isPortrait){
			 centerX = (int) ((x / sise.height) * 2000 - 1000);
			 centerY = (int) ((y / sise.width) * 2000 - 1000);
		}else{
			centerX = (int) ((x / sise.width) * 2000 - 1000);
			centerY = (int) ((y / sise.height) * 2000 - 1000);
		}
		Log.i("calculateTapArea", "getResolution().width--->" + sise.width + ",,,,getResolution().height--->" + sise.height);

		if(isPortrait){
			left = clamp(centerX - (areaSizeY / 2), -1000, 1000);
			top = clamp(centerY - (areaSizeX / 2), -1000, 1000);
			rectF = new RectF(left, top, left +areaSizeY , top + areaSizeX);
		}else{
			left = clamp(centerX - (areaSizeX / 2), -1000, 1000);
			top = clamp(centerY - (areaSizeY / 2), -1000, 1000);
			rectF = new RectF(left, top, left + areaSizeX, top + areaSizeY);
		}


		Log.i("calculateTapArea", "left--->" + left + ",,,top--->" + top + ",,,right--->" + (left + areaSizeX) + ",,,bottom--->" + (top + areaSizeY));
		Log.i("calculateTapArea", "centerX--->" + centerX + ",,,centerY--->" + centerY);
		return new Rect(Math.round(rectF.left), Math.round(rectF.top),
				Math.round(rectF.right), Math.round(rectF.bottom));
	}
	/**
	 * @param previewCallback
	 *            相机界面预览回调函数
	 * @param surfaceHolder
	 *            相机界面的holder
	 * @param camera
	 *            相机对象
	 * @param cancelAutoFocus
	 *            是否取消自动对焦
	 * @param currentActivity
	 *            相机界面的Activity对象
	 * @param screenRatio
	 *            屏幕的宽高比
	 * @param srcList
	 *            拍照分辨率的宽高容器
	 * @return ${return_type} 返回类型
	 * @throws
	 * @Title: ${enclosing_method}
	 * @Description: 设置相机的详细参数
	 */
	public void setCameraParameters(Camera.PreviewCallback previewCallback,
									SurfaceHolder surfaceHolder, Activity currentActivity,
									Camera camera, float screenRatio, List<Integer> srcList,
									boolean cancelAutoFocus, int rotation,boolean isSetZoom) {

		Camera.Parameters parameters = camera.getParameters();
//		 if (parameters.getSupportedFocusModes().contains(
//		 parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//		 parameters.setFocusMode(parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//		 }
		
		if (parameters.getSupportedFocusModes().contains(
				Camera.Parameters.FOCUS_MODE_AUTO)) {
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

		}
		parameters.setPictureFormat(PixelFormat.JPEG);
	//	parameters.setExposureCompensation(0);
		parameters.setExposureCompensation((int)(parameters.getMinExposureCompensation()*0.7));
		Camera.Size optimalPreviewSize =getOptimalPreviewSize(currentActivity,
						camera.getParameters().getSupportedPreviewSizes(),
						screenRatio);
		parameters.setPreviewSize(optimalPreviewSize.width,
				optimalPreviewSize.height);
		try {
			camera.setPreviewDisplay(surfaceHolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cancelAutoFocus) {
			camera.cancelAutoFocus();
		}


			if(isSetZoom){
				if(parameters.isZoomSupported()){
					parameters.setZoom((int)(parameters.getMaxZoom()*0.4));  //因为近距离时有时不好对焦，现将焦距拉远，以便近距离时能清晰对焦   可自定义值
				}
			}



		//焦距调整完毕
		camera.setParameters(parameters);
		camera.setDisplayOrientation(rotation);
		camera.setPreviewCallback(previewCallback);
		camera.startPreview();

	}

	@SuppressLint("NewApi")
	private void setScreenSize(Context context) {
		int x, y;
		WindowManager wm = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE));
		Display display = wm.getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point screenSize = new Point();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				display.getRealSize(screenSize);
				x = screenSize.x;
				y = screenSize.y;
			} else {
				display.getSize(screenSize);
				x = screenSize.x;
				y = screenSize.y;
			}
		} else {
			x = display.getWidth();
			y = display.getHeight();
		}

		srcWidth = x;
		srcHeight = y;
	}

	/**
	 * 获取设备的预览分辨率的宽和高
	 * 
	 * @param camera
	 */
	public void getCameraPreParameters(Camera camera)

	{
		isShowBorder = false;
		// 荣耀七设备
		if ("PLK-TL01H".equals(Build.MODEL)) {
			preWidth = 1920;
			preHeight = 1080;
			return;
		}
		// 其他设备
		parameters = camera.getParameters();
		list = parameters.getSupportedPreviewSizes();
		float ratioScreen = (float) srcWidth / srcHeight;

		for (int i = 0; i < list.size(); i++) {

			float ratioPreview = (float) list.get(i).width / list.get(i).height;
			if (ratioScreen == ratioPreview) {// 判断屏幕宽高比是否与预览宽高比一样，如果一样执行如下代码
				if (list.get(i).width >= 1280 || list.get(i).height >= 720) {// 默认预览以1280*720为标准
					if (preWidth == 0 && preHeight == 0) {// 初始值
						preWidth = list.get(i).width;
						preHeight = list.get(i).height;
					}
					if (list.get(0).width > list.get(list.size() - 1).width) {
						// 如果第一个值大于最后一个值
						if (preWidth > list.get(i).width
								|| preHeight > list.get(i).height) {
							// 当有大于1280*720的分辨率但是小于之前记载的分辨率，我们取中间的分辨率
							preWidth = list.get(i).width;
							preHeight = list.get(i).height;
						}
					} else {
						// 如果第一个值小于最后一个值
						if (preWidth < list.get(i).width
								|| preHeight < list.get(i).height) {
							// 如果之前的宽度和高度大于等于1280*720，就不需要再筛选了
							if (preWidth >= 1280 || preHeight >= 720) {

							} else {
								// 为了找到合适的分辨率，如果preWidth和preHeight没有比1280*720大的就继续过滤
								preWidth = list.get(i).width;
								preHeight = list.get(i).height;
							}
						}
					}
				}
			}
		}
		// 说明没有找到程序想要的分辨率
		if (preWidth == 0 || preHeight == 0) {
			isShowBorder = true;
			preWidth = list.get(0).width;
			preHeight = list.get(0).height;
			for (int i = 0; i < list.size(); i++) {

				if (list.get(0).width > list.get(list.size() - 1).width) {
					// 如果第一个值大于最后一个值
					if (preWidth >= list.get(i).width
							|| preHeight >= list.get(i).height) {
						// 当上一个选择的预览分辨率宽或者高度大于本次的宽度和高度时，执行如下代码:
						if (list.get(i).width >= 1280) {
							// 当本次的预览宽度和高度大于1280*720时执行如下代码
							preWidth = list.get(i).width;
							preHeight = list.get(i).height;

						}
					}
				} else {
					if (preWidth <= list.get(i).width
							|| preHeight <= list.get(i).height) {
						if (preWidth >= 1280 || preHeight >= 720) {

						} else {
							// 当上一个选择的预览分辨率宽或者高度大于本次的宽度和高度时，执行如下代码:
							if (list.get(i).width >= 1280) {
								// 当本次的预览宽度和高度大于1280*720时执行如下代码
								preWidth = list.get(i).width;
								preHeight = list.get(i).height;

							}
						}

					}
				}
			}
		}
		// 如果没有找到大于1280*720的分辨率的话，取集合中的最大值进行匹配
		if (preWidth == 0 || preHeight == 0) {
			isShowBorder = true;
			if (list.get(0).width > list.get(list.size() - 1).width) {
				preWidth = list.get(0).width;
				preHeight = list.get(0).height;
			} else {
				preWidth = list.get(list.size() - 1).width;
				preHeight = list.get(list.size() - 1).height;
			}
		}
		if (isShowBorder) {
			if (ratioScreen > (float) preWidth / preHeight) {
				surfaceWidth = (int) (((float) preWidth / preHeight) * srcHeight);
				surfaceHeight = srcHeight;
			} else {
				surfaceWidth = srcWidth;
				surfaceHeight = (int) (((float) preHeight / preWidth) * srcHeight);
			}
		} else {
			surfaceWidth = srcWidth;
			surfaceHeight = srcHeight;
		}

	}
	/**
	 * 手动聚焦
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @SuppressLint("NewApi")
	public void onFocus(Camera mCamera) {
		Camera.Parameters parameters = mCamera.getParameters();
		//不支持设置自定义聚焦，则使用自动聚焦，返回
		if (parameters.getMaxNumFocusAreas() <= 0) {
			mCamera.autoFocus(null);
			return;
		}
		setScreenSize(context);
		Rect focusRect;
		Rect meteringRect;
		if(srcWidth>srcHeight){
			focusRect = calculateTapArea(mCamera,getResolution(mCamera).width/2, getResolution(mCamera).height/2, 1f,false);
			meteringRect = calculateTapArea(mCamera,getResolution(mCamera).width/2, getResolution(mCamera).height/2, 1.5f,false);
		}else {
			focusRect = calculateTapArea(mCamera,getResolution(mCamera).height/2, getResolution(mCamera).width/2, 1f,true);
			meteringRect = calculateTapArea(mCamera,getResolution(mCamera).height/2, getResolution(mCamera).width/2, 1.5f,true);
		}
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

		if (parameters.getMaxNumFocusAreas() > 0) {
			ArrayList focusAreas = new ArrayList<Camera.Area>();
			focusAreas.add(new Camera.Area(focusRect, 1000));
			parameters.setFocusAreas(focusAreas);
		}

		if (parameters.getMaxNumMeteringAreas() > 0) {
			ArrayList meteringAreas = new ArrayList<Camera.Area>();
			meteringAreas.add(new Camera.Area(meteringRect, 1000));
			parameters.setMeteringAreas(meteringAreas);
		}
		try {
			mCamera.cancelAutoFocus();
			mCamera.setParameters(parameters);
		} catch (Exception e) {

		}
		mCamera.autoFocus(null);
	}

	public void autoFocus(Camera mCamera) {
		if (mCamera != null) {
			try {
				if (mCamera.getParameters().getSupportedFocusModes() != null
						&& mCamera.getParameters().getSupportedFocusModes()
						.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
					onFocus(mCamera);
				} else {
					Toast.makeText(
							context,
							context.getString(context.getResources().getIdentifier(
									"unsupport_auto_focus", "string",
									context.getPackageName())), Toast.LENGTH_LONG)
							.show();
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("对焦失败");
			}
		}
	}
}
