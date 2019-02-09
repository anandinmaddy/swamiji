package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<VolumeDetailsModel> arrayList;


    public ExpandableListAdapter(Context context, ArrayList<VolumeDetailsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).getFileDetailsModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayList.get(groupPosition).getFileDetailsModels().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.volume_details_row, parent, false);
        }

        TextView title = convertView.findViewById(R.id.titleex);
        TextView volumeValue = convertView.findViewById(R.id.volumevalues);
        TextView classesValue=convertView.findViewById(R.id.classesvalue);
        TextView price = convertView.findViewById(R.id.price);
        final ImageView arrow = convertView.findViewById(R.id.arrow);

        // hypen code static
        String volume = arrayList.get(groupPosition).getTitle().replace( " ","-" );
        //commented below anand
        title.setText(volume);

//

        classesValue.setText(arrayList.get(groupPosition).getClasses());
        price.setText(arrayList.get(groupPosition).getPrice());

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ExpandableListAdapter.this,Detailed_price_activity.class);
//                context.startActivity(intent);
            }
        });

        int imageResourceId = isExpanded ? R.drawable.up : R.drawable.down;
        arrow.setImageResource(imageResourceId);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.volume_details_child_row, parent, false);
        }

        TextView title = convertView.findViewById(R.id.childTitle);
        TextView classValues = convertView.findViewById(R.id.classvalues);
        TextView time = convertView.findViewById(R.id.time);

        title.setText(arrayList.get(groupPosition).getFileDetailsModels().get(childPosition).getFileName());
        classValues.setText(arrayList.get(groupPosition).getFileDetailsModels().get(childPosition).getClassName());
        time.setText(arrayList.get(groupPosition).getFileDetailsModels().get(childPosition).getLength());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
