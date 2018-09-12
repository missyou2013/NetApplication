/**
 * 
 */
package com.kernal.smartvision.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.kernal.smartvision.adapter.SetDocTypeAdapter;
import com.kernal.smartvisionocr.utils.KernalLSCXMLInformation;
import com.kernal.smartvisionocr.utils.SharedPreferencesHelper;
import com.kernal.smartvisionocr.utils.Utills;
import com.kernal.smartvisionocr.utils.WriteToPCTask;

import java.util.ArrayList;

/**
 * 
 * 项目名称：SmartVisionOCR 类名称：SettingActivity 类描述： 创建人：张志朋 创建时间：2016-5-3 下午3:33:16
 * 修改人：user 修改时间：2016-5-3 下午3:33:16 修改备注：
 * 
 * @version
 * 
 */
public class SettingActivity extends Activity {
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private RelativeLayout Flayout_setting;
	private TextView tv_setting;
	private ListView listview_setting;
	private ImageButton imbtn_setting_back;
	private int srcWidth, srcHeight;
	private SetDocTypeAdapter adapter;
	private KernalLSCXMLInformation wlci_Landscape, wlci_Portrait;
	private View view1, view2;
	private RelativeLayout layout_set, layout_menu;
	private Button btn_setting_back;
	private ViewPager mTabPager;
    private TextView setting_upload, Document_formats;
    private int zero = 0;
    private int currIndex = 0;
    private int one;
    private int two;
    private int three;
    private Boolean isDocTypeSetting = true;
    private TextView btn_upload_http, btn_upload_ftp,tv_set_upload_ip;
    private EditText et_set_upload_ip;
    private CheckBox cb_isupload;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(getResources().getIdentifier("activity_setting", "layout", getPackageName()));
		Utills.copyFile(this);//写入本地文件   已写入的情况下，根据version.txt中版本号判断是否需要更新，如不需要不执行写入操作
		srcWidth = displayMetrics.widthPixels;
		srcHeight = displayMetrics.heightPixels;
		wlci_Landscape =  Utills.parseXmlFile(this,"appTemplateConfig.xml",
				false);
		wlci_Portrait =  Utills
				.parseXmlFile(this,"appTemplatePortraitConfig.xml",
						false);
		adapter = new SetDocTypeAdapter(getApplicationContext(), srcWidth,
				srcHeight, wlci_Landscape.template,wlci_Portrait.template);
		
		LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(getResources().getIdentifier(
                "activity_set_upload", "layout", this.getPackageName()), null);
        view2 = mLi.inflate(getResources().getIdentifier(
                "activity_set_doctype", "layout", this.getPackageName()), null);
//		findView();
        findTotalView();
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void finishUpdate(View arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void restoreState(Parcelable arg0, ClassLoader arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public Parcelable saveState() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void startUpdate(View arg0) {
                // TODO Auto-generated method stub

            }
        };

        mTabPager.setAdapter(mPagerAdapter);
        isDocTypeSetting = SharedPreferencesHelper.getBoolean(
                getApplicationContext(), "isDocTypeSetting", true);
        if (SharedPreferencesHelper.getBoolean(getApplicationContext(),
                "isDocTypeSetting", true)) {
            setting_upload.setBackgroundResource(getResources().getIdentifier("unselect_settype",
                    "drawable", this.getPackageName()));
            Document_formats
                    .setBackgroundResource(getResources().getIdentifier("set_select_backgroud",
                            "drawable", this.getPackageName()));

            mTabPager.setCurrentItem(1);
        } else {
            setting_upload
                    .setBackgroundResource(getResources().getIdentifier("set_select_backgroud",
                            "drawable", this.getPackageName()));
            Document_formats.setBackgroundResource(getResources().getIdentifier("unselect_settype",
                    "drawable", this.getPackageName()));

            mTabPager.setCurrentItem(0);
        }
       
        findUploadView();
        findDocTypeView();
       
	}
	//图片及结果上传界面UI布局
	private void findUploadView(){
		
		tv_set_upload_ip=(TextView)view1.findViewById(getResources().getIdentifier("tv_set_upload_ip", "id", getPackageName()));
		et_set_upload_ip=(EditText)view1.findViewById(getResources().getIdentifier("et_set_upload_ip", "id", getPackageName()));
		cb_isupload=(CheckBox)view1.findViewById(getResources().getIdentifier("cb_isupload", "id", getPackageName()));
	LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	params.leftMargin=(int) (srcWidth*0.01);
	params.topMargin=(int) (srcHeight*0.05);
	tv_set_upload_ip.setLayoutParams(params);
	params=new LayoutParams((int) (srcWidth*0.8),(int) (srcHeight*0.05));
	params.leftMargin=(int) (srcWidth*0.12);
	params.topMargin=(int) (srcHeight*0.045);
	et_set_upload_ip.setLayoutParams(params);
	et_set_upload_ip.setText(SharedPreferencesHelper.getString(
                getApplicationContext(), "URL", WriteToPCTask.httpPath));
	params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	params.addRule(RelativeLayout.CENTER_HORIZONTAL);;
	params.topMargin=(int) (srcHeight*0.15);
	cb_isupload.setLayoutParams(params);
	if(SharedPreferencesHelper.getBoolean(getApplicationContext(), "upload",
                false)){
		cb_isupload.setChecked(true);
	}else{
		cb_isupload.setChecked(false);
	}
	}
	//设置界面总UI布局
	private void findTotalView() {
		// TODO Auto-generated method stub
		 layout_menu = (RelativeLayout) this.findViewById(getResources().getIdentifier("layout_menu",
	                "id", this.getPackageName()));
	        btn_setting_back = (Button) this.findViewById(getResources().getIdentifier("btn_setting_back",
	                "id", this.getPackageName()));
	        btn_setting_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(cb_isupload.isChecked())
						{
							SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
				                    true);	
						}else{
							SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
				                    false);	
						}
						if(et_set_upload_ip.getText().toString()!=null){
							WriteToPCTask.httpPath=et_set_upload_ip.getText().toString();
						}
						SharedPreferencesHelper.putBoolean(getApplicationContext(),
				                "isDocTypeSetting", isDocTypeSetting);
					Utills.updateXml(SettingActivity.this,wlci_Landscape,"appTemplateConfig.xml");
					Utills.updateXml(SettingActivity.this,wlci_Portrait,"appTemplatePortraitConfig.xml");
						finish();
				}
			});
	        layout_set = (RelativeLayout) this.findViewById(getResources().getIdentifier("layout_set",
	                "id", this.getPackageName()));
	        mTabPager = (ViewPager) this.findViewById(getResources().getIdentifier("tabpager",
	                "id", this.getPackageName()));
	        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
	        setting_upload = (TextView) this.findViewById(getResources().getIdentifier("setting_upload",
	                "id", this.getPackageName()));
	        Document_formats = (TextView) this.findViewById(getResources().getIdentifier("Document_formats",
	                "id", this.getPackageName()));
	        setting_upload.setOnClickListener(new MyOnClickListener(0));
	        Document_formats.setOnClickListener(new MyOnClickListener(1));
	        LayoutParams layoutParams = new LayoutParams(
	        		srcWidth, (int) (srcHeight * 0.05));
	        layoutParams.topMargin = 0;
	        layout_set.setLayoutParams(layoutParams);
	        layoutParams = new LayoutParams((int) (srcHeight * 0.1),
	                (int) (srcHeight * 0.03));
	        layoutParams.leftMargin = (int) (srcWidth * 0.02);
	        layoutParams.topMargin = (int) (srcHeight * 0.01);
	        btn_setting_back.setLayoutParams(layoutParams);
	        layoutParams = new LayoutParams((int) (srcWidth * 0.5),
	                (int) (srcHeight * 0.05));
	        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
	                RelativeLayout.TRUE);
	        setting_upload.setLayoutParams(layoutParams);
	        layoutParams = new LayoutParams((int) (srcWidth * 0.5),
	                (int) (srcHeight * 0.05));
	        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
	                RelativeLayout.TRUE);
	        layoutParams.leftMargin = srcWidth / 2;
	        Document_formats.setLayoutParams(layoutParams);
	        layoutParams = new LayoutParams(srcWidth, (int) (srcHeight * 0.05));
	        layoutParams.topMargin = (int) (srcHeight * 0.05);
	        layout_menu.setLayoutParams(layoutParams);
	}

	/**
	 * @Title: findView
	 * @Description: 设置模板界面UI布局
	 * @return void 返回类型
	 * @throws
	 */
	private void findDocTypeView() {
		// TODO Auto-generated method stub

		listview_setting = (ListView) view2.findViewById(getResources().getIdentifier("listview_setting", "id", getPackageName()));
		LinearLayout.LayoutParams LinearParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearParams.leftMargin = (int) (srcWidth * 0.07);
		LinearParams.rightMargin = (int) (srcWidth * 0.07);
		LinearParams.topMargin = (int) (srcHeight * 0.05);
		listview_setting.setLayoutParams(LinearParams);
		listview_setting.setAdapter(adapter);
		listview_setting.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parentView, View view,
									int position, long id) {
				// TODO Auto-generated method stub
//				if(wlci_Landscape.template.get(position).isSelected){
//					wlci_Portrait.template.get(position).isSelected=true;
//				}else{
//					wlci_Portrait.template.get(position).isSelected = false;
//				}				
				if(wlci_Landscape.template.get(position).isSelected){
					wlci_Landscape.template.get(position).isSelected=false;
				}else{
					wlci_Landscape.template.get(position).isSelected=true;	
					
				}
				if(wlci_Portrait.template.get(position).isSelected){					
					wlci_Portrait.template.get(position).isSelected=false;				
				}else{
					
					wlci_Portrait.template.get(position).isSelected=true;				
				}				
				adapter.notifyDataSetChanged();
			}
		});
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_setting_back:
//			if(cb_isupload.isChecked())
//			{
//				SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
//	                    true);	
//			}else{
//				SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
//	                    false);	
//			}
//			if(et_set_upload_ip.getText().toString()!=null){
//				WriteToPCTask.httpPath=et_set_upload_ip.getText().toString();
//			}
//			SharedPreferencesHelper.putBoolean(getApplicationContext(),
//	                "isDocTypeSetting", isDocTypeSetting);
//			Utils.updateXml(wlci);
//			finish();
//			break;
//
//		default:
//			break;
//		}
//		
//	}
	 public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
	        @Override
	        public void onPageSelected(int arg0) {
	            Animation animation = null;
	            switch (arg0) {
	            case 0:

	                if (currIndex == 1) {
	                    animation = new TranslateAnimation(one, 0, 0, 0);

	                    setting_upload
	                            .setBackgroundResource(getResources().getIdentifier("set_select_backgroud",
	                                    "drawable", SettingActivity.this.getPackageName()));
	                    Document_formats
	                            .setBackgroundResource(getResources().getIdentifier("unselect_settype",
	                                    "drawable", SettingActivity.this.getPackageName()));
	                    isDocTypeSetting = false;
	                }
	                break;
	            case 1:

	                if (currIndex == 0) {
	                    animation = new TranslateAnimation(zero, one, 0, 0);

	                    setting_upload
	                            .setBackgroundResource(getResources().getIdentifier("unselect_settype",
	                                    "drawable", SettingActivity.this.getPackageName()));
	                    Document_formats
	                            .setBackgroundResource(getResources().getIdentifier("set_select_backgroud",
	                                    "drawable", SettingActivity.this.getPackageName()));

	                    isDocTypeSetting = true;
	                }
	                break;

	            }
	            currIndex = arg0;

	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    }

	   
	    public class MyOnClickListener implements OnClickListener {
	        private int index = 0;

	        public MyOnClickListener(int i) {
	            index = i;
	        }

	        @Override
	        public void onClick(View v) {
	            mTabPager.setCurrentItem(index);
	            if (index == 1) {
	                isDocTypeSetting = true;
	            } else if (index == 0) {
	                isDocTypeSetting = false;
	            }
	        }
	    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			SharedPreferencesHelper.putBoolean(getApplicationContext(),
                    "isDocTypeSetting", isDocTypeSetting);
			if(et_set_upload_ip.getText().toString()!=null){
				WriteToPCTask.httpPath=et_set_upload_ip.getText().toString();
			}
			if(cb_isupload.isChecked())
			{
				SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
	                    true);
				
			}else{
				SharedPreferencesHelper.putBoolean(getApplicationContext(), "upload",
	                    false);	
			}
			Utills.updateXml(SettingActivity.this,wlci_Landscape,"appTemplateConfig.xml");
			Utills.updateXml(SettingActivity.this,wlci_Portrait,"appTemplatePortraitConfig.xml");
			finish();
		};
		return super.onKeyDown(keyCode, event);
		
		
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShowResultActivity.selectedTemplateTypePosition =0;
	}
}
