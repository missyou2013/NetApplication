package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.com.mvp.ac.R;

import java.util.List;
import java.util.Map;

/**
 * @Title: 待检测车辆列表adapter
 */
public class DaiJianCarsListAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;

    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private List<Map<String, Object>> data;

    public DaiJianCarsListAdapter(Context context, List<Map<String, Object>> data) {
        super();
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int l = 0;
        if (data != null && data.size() > 0) {
            l = data.size();
        } else {
            l = 0;
        }
        return l;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        int p = position % 6;
        if (p == 0) {
            return TYPE_1;
        } else if (p < 3) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_left holder1 = null;
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.holder_consume, parent, false);
            holder1 = new ViewHolder_left();

            holder1.chepai_haoma = (TextView) convertView
                    .findViewById(R.id.hold_consume_chepaihao);
            holder1.cheliang_leixing = (TextView) convertView
                    .findViewById(R.id.hold_consume_chepaileixing);
            holder1.jiance_cishu = (TextView) convertView
                    .findViewById(R.id.hold_consume_jiancecishu_my_times);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder_left) convertView.getTag();
        }
//        holder1. chepai_haoma.setText(data.get(position).getXm());
//        holder1.cheliang_leixing.setText(data.get(position).getLx());
//        holder1.jiance_cishu.setText(data.get(position).getJe()+"");
//        holder1.shequ_txttop.setText((String) data.get(position).get(
//                "txt_top"));
//        holder1.shequtxt_bottom.setText((String) data.get(position).get(
//                "txt_bottom"));
//        holder1.shequ_txtmiddle.setText((String) data.get(position).get(
//                "time"));
        holder1.chepai_haoma.setText((String) data.get(position).get(
                "txt_top"));
        holder1.cheliang_leixing.setText((String) data.get(position).get(
                "txt_bottom"));
        holder1.jiance_cishu.setText((String) data.get(position).get(
                "time"));
        return convertView;
    }

    class ViewHolder_left {
        private TextView chepai_haoma;
        private TextView cheliang_leixing;
        private TextView jiance_cishu;
    }

    class ViewHolder_right {
        TextView guanfang_txttop, guanfang_txtmiddle, guanfang_txtbottom;
    }
}
