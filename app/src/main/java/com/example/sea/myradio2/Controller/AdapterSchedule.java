package com.example.sea.myradio2.Controller;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sea.myradio2.R;
import com.example.sea.myradio2.UI.MainActivity;


public class AdapterSchedule extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Map<String, String>> mDataText;
    boolean expanded;
    private int currentPosForSchedule;
    private MainActivity mMainActivity;
    private int color;

    public AdapterSchedule(Context c, ArrayList<Map<String, String>> lDataText){
        context = c;
        mMainActivity = (MainActivity) context;
        color = context.getResources().getColor(R.color.colorPrimary);
        mDataText = lDataText;
        currentPosForSchedule = mMainActivity.currentProgramPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_group_item, null);
        }

        ImageView groupImage = (ImageView) convertView.findViewById(R.id.group_image);
//        AsyncPicturesReceiver.fetchImage("http://cdn.static3.rtr-vesti.ru/vh/pictures/r/465/300.jpg", groupImage);


        TextView tvGroup = (TextView) convertView.findViewById(R.id.group_text);
        tvGroup.setText(mDataText.get(groupPosition).get("title"));

        TextView tvGroupTime = (TextView) convertView.findViewById(R.id.group_time);
        tvGroupTime.setText(mDataText.get(groupPosition).get("time"));
        if (currentPosForSchedule == groupPosition) {
            tvGroupTime.setBackgroundColor(color);
        }

        if (isExpanded) {
            tvGroupTime.setVisibility(View.GONE);
        } else { tvGroupTime.setVisibility(View.VISIBLE); }

        convertView.setClickable(false);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_child_item, null);

        }

        TextView tvChild = (TextView) convertView.findViewById(R.id.child_content);
        tvChild.setText(mDataText.get(groupPosition).get("content"));

        TextView tvChildTime = (TextView) convertView.findViewById(R.id.child_time);
        tvChildTime.setText(mDataText.get(groupPosition).get("time"));

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getGroupCount() {
        return mDataText.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataText.get(groupPosition).get("content") == "") {
            return 0;
        } else return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public Object getGroup(int groupPosition) {     return mDataText.get(groupPosition);    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {        return null;    }

    @Override
    public long getGroupId(int groupPosition) {        return 0;    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }


}
