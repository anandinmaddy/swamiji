package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Model.NavigationModel;
import com.sastra.im037.sastraprakasika.R;

import java.util.ArrayList;


public class NavigationDrawerBaseAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<NavigationModel> commonNavignationHeaders;
    private DrawerLayout mDrawerLayout;

    public NavigationDrawerBaseAdapter(Context context, ArrayList<NavigationModel> commonNavignationHeaders, DrawerLayout mDrawerLayout) {
        this.context = context;
        this.commonNavignationHeaders = commonNavignationHeaders;
        this.mDrawerLayout = mDrawerLayout;
    }


    @Override
    public int getGroupCount() {
        return commonNavignationHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return commonNavignationHeaders.get(groupPosition).getSubCategories().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commonNavignationHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commonNavignationHeaders.get(groupPosition).getSubCategories().get(childPosition);
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
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {


        Context context = this.context;
        convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_navigation_list_view, null);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(commonNavignationHeaders.get(groupPosition).getMainCategory());
        if (commonNavignationHeaders.get(groupPosition).getMainCategory().equalsIgnoreCase("Vedanta Discourses") ||
                commonNavignationHeaders.get(groupPosition).getMainCategory().equalsIgnoreCase("Generic Discourses") ||
                commonNavignationHeaders.get(groupPosition).getMainCategory().equalsIgnoreCase("Discourses in Tamil") ||
                commonNavignationHeaders.get(groupPosition).getMainCategory().equalsIgnoreCase("Divyanandam")) {
            txtTitle.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sub_menu_layout, null);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        convertView.startAnimation(animation);

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        txtTitle.setText(commonNavignationHeaders.get(groupPosition).getSubCategories().get(childPosition).getSubCategoryName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    private void displayView(String position) {

        switch (position) {
            case "Home":
                mDrawerLayout.closeDrawers();
//                CommonMethod.changeActivity(context, Dashboard.class);
                break;
            case "Privacy & Policy":
                mDrawerLayout.closeDrawers();
//                CommonMethod.changeActivityText(context, PrivacyPolicy.class, "Privacy & Policy", ConstantValues.privacy_and_policy, "");
                break;

            case "Terms of use":
                mDrawerLayout.closeDrawers();
//                CommonMethod.changeActivityText(context, PrivacyPolicy.class, "Terms of use", ConstantValues.terms_of_use, "");
                break;

            case "Help":
                mDrawerLayout.closeDrawers();
//                CommonMethod.changeActivityText(context, PrivacyPolicy.class, "Help", ConstantValues.help, "");
                break;


            default:
                break;
        }

    }

}
