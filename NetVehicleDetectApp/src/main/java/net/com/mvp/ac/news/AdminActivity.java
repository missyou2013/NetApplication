package net.com.mvp.ac.news;

import android.os.Bundle;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.BaseActivity;

public class AdminActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_admin);


        setBackBtn();
        setTopTitle("用户管理");
    }
}
