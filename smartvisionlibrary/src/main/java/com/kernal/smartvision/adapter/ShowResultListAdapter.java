package com.kernal.smartvision.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kernal.smartvision.R;
import com.kernal.smartvision.utils.EditTextWatcher;
import com.kernal.smartvision.utils.ViewUtil;
import com.kernal.vinparseengine.VinParseInfo;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.util.ArrayList;

/**
 * Created by huangzhen on 2016/4/11.
 */
public class ShowResultListAdapter extends BaseAdapter {
	private Context context;
	private VinParseInfo vpi;
	private LayoutInflater inflater;
	private int width, height;
	private TextView tv_FieldName;
	private EditText et_FieldName;
	private ImageView image_FieldName;
	public ArrayList<String> recogResult;
	private RelativeLayout bg_re_showResult;
	private ArrayList<String> SavePicPath;// 图片路径集合
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private double screenInches;
	private View view;
	private ViewHolder holder = null;
	private EditTextWatcher myNum1_show;
	private VinParseResultAdapter adapter;
	private boolean isfirst = true;
	private RelativeLayout.LayoutParams params;

	public ShowResultListAdapter(Context context, int width, int height,
								 ArrayList<String> recogResult, ArrayList<String> savePath,
								 double screenInches, VinParseResultAdapter adapter) {
		this.width = width;
		this.height = height;
		this.context = context;
		this.recogResult = recogResult;
		this.inflater = LayoutInflater.from(context);
		this.SavePicPath = savePath;
		this.screenInches = screenInches;
		this.adapter = adapter;
		initLoader();

	}

	@Override
	public int getCount() {
		return recogResult.size();
	}

	@Override
	public Object getItem(int position) {
		return recogResult.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public ImageView image_FieldName = null;
		public TextView tv_FieldName = null;
		public EditText et_FieldName = null;
		public RelativeLayout bg_re_showResult = null;
	}

	@Override
	public View getView(int position, View convertView,
						ViewGroup viewGroup) {
		
		if (convertView == null) {
			holder  = new ViewHolder();
			convertView = inflater.inflate(
					context.getResources().getIdentifier(
							"activity_showresult_list_result", "layout",
							context.getPackageName()), null);

			holder.et_FieldName = (EditText) convertView
					.findViewById(R.id.et_FieldName);
			holder.tv_FieldName = (TextView) convertView
					.findViewById(R.id.tv_FieldName);
			holder.image_FieldName = (ImageView) convertView
					.findViewById(R.id.image_FieldName);
			holder.bg_re_showResult = (RelativeLayout) convertView
					.findViewById(R.id.bg_re_showResult);

			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT,
					RelativeLayout.LayoutParams.FILL_PARENT);
			params.leftMargin = (int) (width * 0.05);
			params.topMargin = (int) (height * 0.07);
			holder.tv_FieldName.setLayoutParams(params);

			params = new RelativeLayout.LayoutParams((int) (width * 0.82),
					(int) (height * 0.06));
			params.addRule(RelativeLayout.BELOW, holder.image_FieldName.getId());
			params.leftMargin = (int) (width * 0.04);
			holder.et_FieldName.setLayoutParams(params);

			params = new RelativeLayout.LayoutParams((int) (width * 0.8),
					(int) (height * 0.06));
			params.topMargin = (int) (height * 0.12);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			holder.image_FieldName.setLayoutParams(params);
			params = new RelativeLayout.LayoutParams(width,
					(int) (height * 0.3));
			params.leftMargin = 0;
			params.topMargin = (int) (height * 0.05);
			holder.bg_re_showResult.setLayoutParams(params);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (recogResult.size() > position) {
			if (recogResult.get(position).split(":")[0] != null) {			
				holder.tv_FieldName.setText(recogResult.get(position)
						.split(":")[0]);
			}
			if (recogResult.get(position).split(":")[1] != null) {
				if (recogResult.get(position).split(":").length > 2) {// 针对二维码识别结果
					String recogReSult = "";
					for (int i = 1; i < recogResult.get(position).split(":").length; i++) {
						if (recogReSult.equals("")) {
							recogReSult = recogResult.get(position).split(":")[i];
						} else {
							recogReSult = recogReSult
									+ recogResult.get(position).split(":")[i];
						}

					}
					
					holder.et_FieldName.setText(recogReSult);

				} else {
					// 判断是否为二维码显示结果 如果是不显示保存的图片
					if (holder.tv_FieldName.getText().equals("二维码")) {
						params = new RelativeLayout.LayoutParams(
								(int) (width * 0.8), (int) (height * 0.06));
						params.addRule(RelativeLayout.BELOW,
								holder.image_FieldName.getId());
						params.addRule(RelativeLayout.CENTER_HORIZONTAL);
						params.leftMargin = (int) (width * 0.04);
						// params.topMargin = (int) (height * 0.12);
						holder.et_FieldName.setLayoutParams(params);
						// holder.image_FieldName.setVisibility(View.GONE);
					} else {
						holder.image_FieldName.setVisibility(View.VISIBLE);
						ImageLoader.getInstance().displayImage("file:/"+SavePicPath.get(position),holder.image_FieldName);
					}

					if (holder.tv_FieldName.getText().equals("VIN码")) {
						if (isfirst) {
							holder.et_FieldName.setText(recogResult.get(
									position).split(":")[1]);
							System.out.println("screenInches：" + screenInches);
							if (screenInches < 5.7 && screenInches >= 5.4) {
								holder.et_FieldName.setTextSize(16);
								holder.et_FieldName.setPadding(
										1 + (int) (width * 0.01), 1, 1, 1);
								ViewUtil.addLetterSpacing(holder.et_FieldName,
										0.3f);
							} else if (screenInches > 6.2 && screenInches < 7) {
								holder.et_FieldName.setTextSize(14);
								holder.et_FieldName.setPadding(
										1 + (int) (width * 0.01), 1, 1, 1);
								// ViewUtil.addLetterSpacing(holder.holder.et_FieldName,
								// (float) 0.5);
								if (width < 1080) {
									ViewUtil.addLetterSpacing(
											holder.et_FieldName, (float) 0.88);
								} else {
									ViewUtil.addLetterSpacing(
											holder.et_FieldName, (float) 1);
								}
							} else if (screenInches >= 9) {
								holder.et_FieldName.setTextSize(30);
								holder.et_FieldName.setPadding(
										1 + (int) (width * 0.01), 1, 1, 1);
								ViewUtil.addLetterSpacing(holder.et_FieldName,
										(float) 1);
							} else if (screenInches >= 7 && screenInches < 9) {
								holder.et_FieldName.setTextSize(30);
								holder.et_FieldName.setPadding(
										1 + (int) (width * 0.01), 1, 1, 1);
								ViewUtil.addLetterSpacing(holder.et_FieldName,
										(float) 0.8);
							} else if (screenInches < 5.4) {
							
								holder.et_FieldName.setTextSize(16);
								holder.et_FieldName.setPadding(
										3 + (int) (width * 0.01), 1, 1, 1);
								ViewUtil.addLetterSpacing(holder.et_FieldName,
										(float) 0.52);
							} else if (screenInches >= 5.7
									&& screenInches <= 6.2) {
								holder.et_FieldName.setTextSize(14);
								holder.et_FieldName.setPadding(
										1 + (int) (width * 0.01), 1, 1, 1);
								ViewUtil.addLetterSpacing(holder.et_FieldName,
										(float) 0.45);

							}
							myNum1_show = new EditTextWatcher(context,
									holder.et_FieldName, screenInches, adapter);
							holder.et_FieldName
									.addTextChangedListener(myNum1_show);
							isfirst = false;

						}
					} else {
						holder.et_FieldName.setText(recogResult.get(position)
								.split(":")[1]);
					}

					holder.et_FieldName.setTextSize(15);

				}
			}
		}
		return convertView;
	}
	void initLoader(){
		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisk(false)
				// 设置图片在下载前是否重置，复位
				.resetViewBeforeLoading(false)
				// 保留Exif信息
				.considerExifParams(false)
				// 设置图片的解码类型
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				//即保存的每个缓存文件的最大长宽
				.memoryCacheExtraOptions(400, 400)
				//设置缓存的详细信息，最好不要设置这个
//                .diskCacheExtraOptions(400, 400, null)
				// 线程池内加载的数量
				.threadPoolSize(5)
				//线程优先级  default Thread.NORM_PRIORITY - 1
				.threadPriority(Thread.NORM_PRIORITY)
				// default FIFO
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// default
//                .denyCacheImageMultipleSizesInMemory()
				//你可以通过自己的内存缓存实现
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(13)
				// default
				.diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context,true)))
				//硬盘缓存50MB
				.diskCacheSize(50 * 1024 * 1024)
				//缓存的File数量
				.diskCacheFileCount(100)
				//将保存的时候的URI名称用HASHCODE加密
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				// default
				.imageDownloader(new BaseImageDownloader(context))
				// default
				.imageDecoder(new BaseImageDecoder(false))
				// default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// default
				.defaultDisplayImageOptions(imageOptions)
				.build();
		ImageLoader.getInstance().init(config);

	}
}
