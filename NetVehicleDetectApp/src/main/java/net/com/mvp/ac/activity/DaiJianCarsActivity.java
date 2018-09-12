package net.com.mvp.ac.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;


import net.com.mvp.ac.R;
import net.com.mvp.ac.adapter.CardRecordAdapter;
import net.com.mvp.ac.model.Consumption;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * @Description:
 */
public class DaiJianCarsActivity extends BaseActivity {
    private RefreshRecyclerView mRecyclerView;
    private CardRecordAdapter mAdapter;
    private Handler mHandler;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai_jian_cars);

        setBackBtn();
        setTopTitle(R.string.activity_daijian_car_list);


        mHandler = new Handler();
        mAdapter = new CardRecordAdapter(this);
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.ac_daijian_recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
                page++;
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

    }

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll(getVirtualData());
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    mAdapter.addAll(getVirtualData());
                    if (page >= 3) {
                        mRecyclerView.showNoMore();
                    }
                }
            }
        }, 1500);
    }

    public Consumption[] getVirtualData() {
        return new Consumption[]{
        };
    }


}
