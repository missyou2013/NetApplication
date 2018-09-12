package net.com.mvp.ac.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.model.MyFlagModel;
import net.com.mvp.ac.model.TypeModel;

import java.util.List;


public class ContentAdapter2 extends BaseAdapter {

    private static final String TAG = "ContentAdapter";
    private List<TypeModel> mContentList;
    private Context context;
    private LayoutInflater mInflater;
    private MyClickListener mListener;

    public ContentAdapter2(Context context, List<TypeModel> contentList,
                           MyClickListener listener) {
        mContentList = contentList;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount");
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem");
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView");
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_ac_waijian_photo, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView
                    .findViewById(R.id.item_ac_waijian_txt_luntai_guige_r_b);
            holder.msg = (TextView) convertView
                    .findViewById(R.id.item_ac_waijian_tv_msg);
            holder.button = (Button) convertView.findViewById(R.id.item_ac_waijian_btn_luntai_guige_r_b_preview);
            holder.button2 = (Button) convertView.findViewById(R.id.item_ac_waijian_btn_luntai_guige_r_b);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.textView.setText(mContentList.get(position));
////        holder.button.setOnClickListener(mListener);
        holder.button.setOnClickListener(mListener);
        holder.button.setTag(new MyFlagModel(position,0));
        holder.button2.setOnClickListener(mListener);
        holder.button2.setTag(new MyFlagModel(position,1));

        int color = mContentList.get(position).getColor();
        holder.msg.setText(mContentList.get(position).getName());
        if (color == 0) {
//            holder.tv2.setTextColor(context.getResources().getColor(R.color.red));
        } else if (color == 1) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.textcolor_green));
            holder.textView.setText("已拍照");
        }

        return convertView;
    }

    public class ViewHolder {
        public TextView textView,msg;
        public Button button, button2;
    }

    /**
     * 用于回调的抽象类
     *
     * @author Ivan Xu
     *         2014-11-26
     */
    public static abstract class MyClickListener implements OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
//            myOnClick((Integer) v.getTag(), v);
            myOnClick(((MyFlagModel) v.getTag()).getPosition(), v,((MyFlagModel) v.getTag()).getFlag());
        }

        public abstract void myOnClick(int position, View v,int flag);
    }


}