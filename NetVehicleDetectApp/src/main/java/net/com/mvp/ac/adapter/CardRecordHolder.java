package net.com.mvp.ac.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.activity.SettingActivity;
import net.com.mvp.ac.model.Consumption;

import cn.lemon.view.adapter.BaseViewHolder;


public class CardRecordHolder extends BaseViewHolder<Consumption> {

    private TextView chepai_haoma;
    private TextView cheliang_leixing;
    private TextView jiance_cishu;
    private Context context;

    public CardRecordHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.holder_consume);
        this.context = context;
    }

    @Override
    public void setData(final Consumption object) {
        super.setData(object);
        chepai_haoma.setText(object.getXm());
        cheliang_leixing.setText(object.getLx());
        jiance_cishu.setText(object.getJe()+"");

    }


    @Override
    public void onInitializeView() {
        super.onInitializeView();
        chepai_haoma = findViewById(R.id.hold_consume_chepaihao);
        cheliang_leixing = findViewById(R.id.hold_consume_chepaileixing);
        jiance_cishu = findViewById(R.id.hold_consume_jiancecishu_my_times);
    }

    @Override
    public void onItemViewClick(Consumption object) {
        super.onItemViewClick(object);
        //点击事件
        Log.i("CardRecordHolder", "onItemViewClick");
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}