/**
 * 
 */
package com.kernal.smartvision.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kernal.smartvisionocr.model.TempleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 项目名称：SmartVisionOCR 类名称：SetDocTypeAdapter 类描述： 设置界面选项适配器 创建人：张志朋
 * 创建时间：2016-5-4 上午9:43:16 修改人：user 修改时间：2016-5-4 上午9:43:16 修改备注：
 * 
 * @version
 * 
 */
public class SetDocTypeAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private int width, height;
	private TextView tv_doctype;
	private CheckBox cb_doctype;
	private LinearLayout.LayoutParams params;
	private List<TempleModel> Data = new ArrayList<TempleModel>();//可以是横屏数据  也可以是竖屏数据
	private List<TempleModel> listData = new ArrayList<TempleModel>();//可以是横屏数据  也可以是竖屏数据
	public SetDocTypeAdapter(Context context, int width, int height, List<TempleModel> listdata, List<TempleModel> data) {
		this.context = context;
		this.height = height;
		this.width = width;
		this.listData = listdata;
		this.Data = data;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
	       View view = convertView == null ? inflater.inflate(
	                context.getResources().getIdentifier("activity_setting_item",
	                        "layout", context.getPackageName()), null) : convertView;		
		tv_doctype = (TextView) view
				.findViewById(context.getResources().getIdentifier("textView_setting_item", "id", context.getPackageName()));
//		textView_setting_item
//		checkBox_setting_item
		cb_doctype = (CheckBox) view
				.findViewById(context.getResources().getIdentifier("checkBox_setting_item", "id", context.getPackageName()));
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = (int) (0.02 * width);
	
		params.topMargin = (int) (height * 0.01);
		params.bottomMargin = (int) (height * 0.01);
		params.weight = (float) 1.0;
		tv_doctype.setLayoutParams(params);
		params = new LinearLayout.LayoutParams((int) (width * 0.06), (int) (width * 0.06));
		params.topMargin = (int) (height * 0.01);
		params.bottomMargin = (int) (height * 0.01);
		params.rightMargin = (int) (0.02 * width);
		cb_doctype.setLayoutParams(params);			
//		System.out.println("获取的名称："+listData.get(position).templateName);
		tv_doctype.setTextColor(Color.BLACK);
		tv_doctype.setText(listData.get(position).templateName);		
		 if (listData.get(position).isSelected) {
             cb_doctype.setChecked(true);
         } else { 
             cb_doctype.setChecked(false);
         }

		 cb_doctype.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(listData.get(position).isSelected){
					listData.get(position).isSelected = false;
					Data.get(position).isSelected = false;
				}else{
					listData.get(position).isSelected=true;
					Data.get(position).isSelected = true;
				}
				notifyDataSetChanged();
			}
		});
		return view;
	}

}
