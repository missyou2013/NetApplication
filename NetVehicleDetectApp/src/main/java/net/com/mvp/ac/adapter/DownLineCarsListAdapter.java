package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.model.CarsInforModel;

import java.util.List;

/**
 * @Title: 复检车辆列表adapter
 */
public class DownLineCarsListAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;
    private List<CarsInforModel> data;

    public DownLineCarsListAdapter(Context context, List<CarsInforModel> data) {
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
        ViewHolder_left holder1 = null;
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.holder_consume2, parent, false);
            holder1 = new ViewHolder_left();

            holder1.line = (LinearLayout) convertView
                    .findViewById(R.id.holder_consume_view_diveder2);
            holder1.chepai_haoma = (TextView) convertView
                    .findViewById(R.id.hold_consume_chepaihao2);
            holder1.cheliang_leixing = (TextView) convertView
                    .findViewById(R.id.hold_consume_chepaileixing2);
            holder1.jiance_cishu = (TextView) convertView
                    .findViewById(R.id.hold_consume_jiancecishu_my_times2);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder_left) convertView.getTag();
        }
        holder1.chepai_haoma.setText(data.get(position).getPlateRegion() + data.get(position)
                .getPlateNO());
        holder1.cheliang_leixing.setText(String.valueOf(data.get(position).getTimes()));
        holder1.jiance_cishu.setText(String.valueOf(data.get(position).getSN()));

        return convertView;
    }

    class ViewHolder_left {
        private TextView chepai_haoma;
        private TextView cheliang_leixing;
        private TextView jiance_cishu;
        private LinearLayout line;
    }

    class ViewHolder_right {
        TextView guanfang_txttop, guanfang_txtmiddle, guanfang_txtbottom;
    }
}
