package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title:
 */
public class DiPanDongTaiAdapter extends BaseAdapter {
    private LayoutInflater minflater;
    private Context context;
//    private List<String> data;
    private List<Map<String, Object>> mData;
    public static Map<Integer, Boolean> isSelected;
    public DiPanDongTaiAdapter(Context context) {
        super();
        this.minflater = LayoutInflater.from(context);
        this.context = context;
//        this.mData = data;
        init();
    }
    //初始化
    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Constants.JDIPAN_DONGTAI_XIANGMU.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", Constants.JDIPAN_DONGTAI_XIANGMU[i]);
            mData.add(map);
        }
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < mData.size(); i++) {
            isSelected.put(i, true);
        }
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mData.get(arg0);
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
            convertView = minflater.inflate(R.layout.item_dipan_dongtai,
                    null);
            holder.title = (TextView) convertView
                    .findViewById(R.id.item_dipan_txt);
            holder.cBox = (CheckBox) convertView
                    .findViewById(R.id.item_dipan_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mData.get(position).get("title").toString());
        holder.cBox.setChecked(isSelected.get(position));
        return convertView;
    }


    public final class ViewHolder {
        public TextView title;
        public CheckBox cBox;
    }
}
