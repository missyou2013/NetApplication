package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.model.BuHeGeModel;

import java.util.List;


public class BuHeGe_1Adapter extends BaseAdapter {
    private LayoutInflater minflater;
    private Context context;
    private List<BuHeGeModel> data;

    public BuHeGe_1Adapter(Context context, List<BuHeGeModel> data) {
        super();
        this.minflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int ll = 0;
        if (data.size() > 0) {
            ll = data.size();
        } else {
            ll = 0;
        }
        return ll;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = minflater.inflate(R.layout.item_buhege_1,
                    null);
            holder.itemBuhege1Txt1 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_1);
            holder.itemBuhege1Txt2 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_2);
            holder.itemBuhege1Txt3 = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_3);
            holder.itemBuhege1TxtDelete = (TextView) convertView
                    .findViewById(R.id.item_buhege_1_txt_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        if (!TextUtils.isEmpty(data.get(position).getUserName())) {
//            holder.themeId.setText(data.get(position).getUserName());
//        } else {
//            UtilsLog.e("====账号名字为空");
//        }
        //设置回调监听
        holder. itemBuhege1TxtDelete.setOnClickListener(onDelItem);
        holder.itemBuhege1TxtDelete.setTag(position);

        holder.itemBuhege1Txt1.setText("aaaaaaaa");
        return convertView;
    }


    class ViewHolder {
        //        @BindView(R.id.item_buhege_1_txt_1)
        TextView itemBuhege1Txt1;
        //        @BindView(R.id.item_buhege_1_txt_2)
        TextView itemBuhege1Txt2;
        //        @BindView(R.id.item_buhege_1_txt_3)
        TextView itemBuhege1Txt3;
        //        @BindView(R.id.item_buhege_1_txt_delete)
        TextView itemBuhege1TxtDelete;
    }

    /**
     * 定义监听接口
     */
    private View.OnClickListener onDelItem;//删除单条item的接口
    public void setOnDelItem(View.OnClickListener onDelItem) {
        this.onDelItem = onDelItem;
    }
}
