package com.kernal.smartvision.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kernal.smartvision.view.ViewfinderView;
import com.kernal.smartvisionocr.model.RecogResultModel;

import java.util.List;

/**
 * Created by huangzhen on 2016/3/24.
 */
public class RecogResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<RecogResultModel> data;
    private TextView tv_list_doctype;
    private RelativeLayout.LayoutParams layoutParams;
    private ViewfinderView viewfinder_view;
    private int width,height;
    public  int selectedRecogResultPosition=0;
    public boolean isRecogSuccess=false; 
    public RecogResultAdapter(Context context, List<RecogResultModel> data,
                              int width, int height) {
        this.width=width;
        this.height = height;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent2) {
        // TODO Auto-generated method stub
        View view = convertView == null ? inflater.inflate(
                context.getResources().getIdentifier("activity_list_recogresult",
                        "layout", context.getPackageName()), null) : convertView;
        tv_list_doctype = (TextView) view.findViewById(context.getResources().getIdentifier("tv_list_doctype",
                "id", context.getPackageName()));
        layoutParams = new RelativeLayout.LayoutParams((int) (width * 0.65),
                (int) (height * 0.055));
        layoutParams.leftMargin=10;
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        tv_list_doctype.setLayoutParams(layoutParams);
        tv_list_doctype.setText(data.get(position).resultValue);
        //System.out.println("布局");
        if(selectedRecogResultPosition==position)
        {
            tv_list_doctype.setTextColor(Color.rgb(46, 167, 224));//#f39912
        }else {
            tv_list_doctype.setTextColor(Color.WHITE);
        }
        if(isRecogSuccess){
            tv_list_doctype.setTextColor(Color.WHITE);
        }
    
        return view;

    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        //selectedRecogResultPosition=-1;
        super.notifyDataSetChanged();
    }



}
