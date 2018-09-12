package com.example.myapplication.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiConfig;


public abstract class BaseActivity extends AppCompatActivity implements ApiConfig {

    public static int SCREEN_WIDTH;//
    public static int SCREEN_HEIGHT;//
    public ImageView im_back;
    public static float SCREEN_DENSITY;//
    public static int SCREEN_DENSITYDPI; //
    protected Context mContext = this;
    protected boolean mIsFront;
    protected byte activityType;
    protected BaseApplication mApp = BaseApplication.getSelf();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (BaseApplication.screenWidth == 0) {
            int width = getWindowManager().getDefaultDisplay().getWidth();
            BaseApplication.screenWidth = width;
        }
        init();
    }

    private void init() {
        SCREEN_WIDTH = getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT = getWindowManager().getDefaultDisplay().getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        SCREEN_DENSITY = dm.density;
        SCREEN_DENSITYDPI = dm.densityDpi;
    }

    /**
     * Back
     */
    public void setHideLeftBtn() {
        View v = findViewById(R.id.title_btn_left);
        if (v == null)
            return;
        v.setVisibility(View.GONE);

    }

    /**
     * Back  activity
     */

    public void setBackBtn() {
        View v = findViewById(R.id.title_btn_left);
        if (v == null)
            return;
        v.setVisibility(View.VISIBLE);
        ((ImageButton) v).setImageResource(R.mipmap.nav_backarrow);
        v.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void setBackBtn_click() {
        View v = findViewById(R.id.title_btn_left);
        if (v == null)
            return;
        v.setVisibility(View.VISIBLE);
        ((ImageButton) v).setImageResource(R.mipmap.nav_backarrow);
        v.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
    }




    public void setBackBtn_toHomeFragment() {
        Intent intent = new Intent();
        intent.setAction("shouye");
        sendBroadcast(intent);
        View v = findViewById(R.id.title_btn_left);
        if (v == null)
            return;
        v.setVisibility(View.VISIBLE);
        ((ImageButton) v).setImageResource(R.mipmap.nav_backarrow);
        v.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

//	/**
//	 * Back按钮退出 activity
//	 */
//	public void setBackBtn2() {
//		Intent intent = new Intent();
//		intent.setAction("shouye");
//		sendBroadcast(intent);
//		View v = findViewById(R.id.title_btn_left);
//		if (v == null)
//			return;
//		v.setVisibility(View.VISIBLE);
//		((ImageButton) v).setImageResource(R.drawable.nav_backarrow);
//		((ImageButton) v).setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Bimp.max = 0;
//				if (Bimp.tempSelectBitmap != null) {
//					Bimp.tempSelectBitmap.clear();
//					Bimp.tempSelectBitmap = new ArrayList<ImageItem>();
//				}
//				for (int i = 0; i < PublicWay.activityList.size(); i++) {
//					if (null != PublicWay.activityList.get(i)) {
//						PublicWay.activityList.get(i).finish();
//					}
//				}
//				finish();
//			}
//		});
//	}


    public void setTopTitle(Object str) {
        View v = findViewById(R.id.title_txt);
        if (v == null)
            return;
        if (str instanceof String) {
            ((TextView) v).setText(String.valueOf(str));
        } else if (str instanceof Integer) {
            ((TextView) v).setText(((Integer) str).intValue());
        }
    }

    protected void onPause() {
        super.onPause();
        this.mIsFront = false;
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void onResume() {
        super.onResume();
        mIsFront = true;
        if (activityType == 0)
            mApp.push(this);
    }

    protected void onDestroy() {
        if (this.mContext == this)
            if (mApp != null)
                mApp.pull((Activity) this.mContext);
        super.onDestroy();
    }


    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void setLeftTxt(String str, OnClickListener click) {
        View v = findViewById(R.id.title_btn_left);
        if (v != null) {
            v.setVisibility(View.GONE);
        }
        View v2 = findViewById(R.id.title_btn_left_txt);
        if (v2 != null) {
            v2.setVisibility(View.VISIBLE);
            ((Button) v2).setText("" + str);
            v2.setOnClickListener(click);
        }
    }


    public void setRightTxt(String str, OnClickListener click) {
        View v = findViewById(R.id.title_btn_right);
        if (v != null) {
            v.setVisibility(View.GONE);
        }
        View v2 = findViewById(R.id.title_btn_right_txt);
        if (v2 != null) {
            v2.setVisibility(View.VISIBLE);
            ((Button) v2).setText("" + str);
            v2.setOnClickListener(click);
        }
    }


    public void setRightBtnClick(int resId, OnClickListener click) {
        View v = findViewById(R.id.title_btn_right);
        if (v == null)
            return;
        ((ImageButton) v).setImageResource(resId);
        v.setVisibility(View.VISIBLE);
        v.setOnClickListener(click);
    }


//
//	/**
//	 * post
//	 *
//	 * @param keyValues
//	 * @return
//	 */
//	public BaseAjaxParams AddAjaxParamValuse(List<NameValues> keyValues) {
//		BaseAjaxParams params = new BaseAjaxParams();
//		params.put("version", CommonUtils.getVersionName(this));
//		params.put("apikey", Constants.API_KEY);
//		params.put("secretKey", Constants.SECRET_KEY);
//		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
//		params.put("deviceType", "1");
//		for (int i = 0; i < keyValues.size(); i++) {
//			params.put(keyValues.get(i).getKeyName(), keyValues.get(i)
//					.getKeyValues().toString());
//		}
//		return params;
//	}
//
//	public static BaseAjaxParams AddAjaxParamValuse2(
//			List<NameValues> keyValues, Activity activity) {
//		BaseAjaxParams params = new BaseAjaxParams();
//		params.put("version", CommonUtils.getVersionName(activity));
//		params.put("apikey", Constants.API_KEY);
//		params.put("secretKey", Constants.SECRET_KEY);
//		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
//		params.put("deviceType", "1");
//		for (int i = 0; i < keyValues.size(); i++) {
//			params.put(keyValues.get(i).getKeyName(), keyValues.get(i)
//					.getKeyValues().toString());
//		}
//		return params;
//	}
}
