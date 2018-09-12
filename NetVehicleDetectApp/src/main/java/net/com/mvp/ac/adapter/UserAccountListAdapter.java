package net.com.mvp.ac.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.commons.UtilsLog;
import net.com.mvp.ac.model.UserAccountModel;

import java.util.List;


/**
 * @Description: 登录界面 用户账号
 * @time 2017/6/12
 */
public class UserAccountListAdapter extends BaseAdapter {
    private LayoutInflater minflater;
    private Context context;
    private List<UserAccountModel> data;

    public UserAccountListAdapter(Context context, List<UserAccountModel> data) {
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
            convertView = minflater.inflate(R.layout.item_my_account_modes,
                    null);
            holder.themeId = (TextView) convertView
                    .findViewById(R.id.item_themeid);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(data.get(position).getUserName())) {
            holder.themeId.setText(data.get(position).getUserName());
        } else {
            UtilsLog.e("====账号名字为空");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView themeId;
    }
}
