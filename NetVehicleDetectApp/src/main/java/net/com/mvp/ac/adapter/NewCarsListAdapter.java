package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.model.NewCarsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 新车调度车辆列表adapter
 */
public class NewCarsListAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;
    private List<NewCarsModel> data;
    public static Map<Integer, Boolean> sSelected ;// 存放已被选中的CheckBox

    public NewCarsListAdapter(Context context, List<NewCarsModel> data ) {
        super();
        this.context = context;
        this.data = data;

        initData();
        inflater = LayoutInflater.from(context);
    }

    private void initData(){
        sSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i <data.size() ; i++) {
            sSelected.put(i,false);
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder_left holder1 = null;
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.new_holder_consume, parent, false);
            holder1 = new ViewHolder_left();

            holder1.line = (LinearLayout) convertView
                    .findViewById(R.id.new_holder_consume_view_diveder);
            holder1.chepai_haoma = (TextView) convertView
                    .findViewById(R.id.new_hold_consume_chepaihao);
            holder1.cheliang_leixing = (TextView) convertView
                    .findViewById(R.id.new_hold_consume_chepaileixing);
            holder1.jiance_cishu = (TextView) convertView
                    .findViewById(R.id.new_hold_consume_jiancecishu_my_times);
            holder1.cbCheck = (CheckBox) convertView
                    .findViewById(R.id.cb_check);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder_left) convertView.getTag();
        }
        holder1.chepai_haoma.setText(data.get(position).getPlateRegion() + data.get(position)
                .getPlateNO());
        holder1.cheliang_leixing.setText(String.valueOf(data.get(position).getType()));
        holder1.jiance_cishu.setText(String.valueOf(data.get(position).getTimes()));

        holder1.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    sSelected.put(position, true);
                } else {

                    sSelected.put(position, false);
//                    sSelected.remove(position);
                }
            }
        });
//        if (sSelected != null && sSelected.containsKey(position)) {
//            holder1.cbCheck.setChecked(true);
//        } else {
//            holder1.cbCheck.setChecked(false);
//        }
        if (sSelected .get(position)) {
            holder1.cbCheck.setChecked(true);
        } else {
            holder1.cbCheck.setChecked(false);
        }
        return convertView;
    }

    class ViewHolder_left {
        private TextView chepai_haoma;
        private TextView cheliang_leixing;
        private TextView jiance_cishu;
        private LinearLayout line;
        private CheckBox cbCheck;
    }


}
