package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.ViewGroup;

import net.com.mvp.ac.model.Consumption;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class CardRecordAdapter extends RecyclerAdapter<Consumption> {
    Context context;

    public CardRecordAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder<Consumption> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new CardRecordHolder(parent, context);
    }
}