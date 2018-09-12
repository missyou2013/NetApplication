package net.com.mvp.ac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.com.mvp.ac.R;
import net.com.mvp.ac.model.City;
import net.com.mvp.ac.model.Group;

import java.util.List;


public class WaiJianResultAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private List<Group> groupList;
    LayoutInflater inflater;

    public WaiJianResultAdapter(Context ctx, List<Group> groupList) {
        this.ctx = ctx;
        this.groupList = groupList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).getCityList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getCityList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_item, null);
        }
        TextView groupText = (TextView) convertView.findViewById(R.id.groupText);
//        CheckBox groupCheckBox = (CheckBox) convertView.findViewById(R.id.groupCheckBox);
        groupText.setText(group.getName());
//        groupCheckBox.setChecked(group.isChecked());
//        groupCheckBox.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                group.changeChecked();
//                notifyDataSetChanged();
//                ((WaiJianResultActivity) ctx).reFlashGridView();
//            }
//        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        City city = (City) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        TextView childText = (TextView) convertView.findViewById(R.id.childText);
        CheckBox childCheckBox = (CheckBox) convertView.findViewById(R.id.childCheckBox);
        childText.setText(city.getName());
        childCheckBox.setChecked(city.isChecked());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
