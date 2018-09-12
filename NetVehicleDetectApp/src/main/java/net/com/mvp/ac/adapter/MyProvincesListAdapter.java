package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.com.mvp.ac.R;

import java.util.List;


/**
 * @Title: MyAccountModeListAdapter.java

 */
public class MyProvincesListAdapter extends BaseAdapter {
	private LayoutInflater minflater;
	private Context context;
	private List<String> data;

	public MyProvincesListAdapter(Context context, List<String> data) {
		super();
		this.minflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
			convertView = minflater.inflate(R.layout.item_my_provinces,
					null);
			holder.themeId = (TextView) convertView
					.findViewById(R.id.item_my_province);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.themeId.setText(data.get(position));

		return convertView;
	}

	static class ViewHolder {
		TextView themeId;
	}
}
