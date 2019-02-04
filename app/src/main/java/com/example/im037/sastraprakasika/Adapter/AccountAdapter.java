package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.LoginActivity;
//import com.example.im037.sastraprakasika.Activity.MyProfileActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.AccountModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.CustomAdapter> {
    ArrayList<AccountModel> arrayList;
    Context context;
    String TAG;

    public AccountAdapter(Context context, ArrayList<AccountModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.TAG = context.getClass().getSimpleName();
    }

    @NonNull
    @Override
    public CustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapter(LayoutInflater.from(context).inflate(R.layout.account_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter holder, int position) {

        holder.name.setText(arrayList.get(position).getName());
        if (arrayList.get(position).getType() == 0) {
            holder.rightIcon.setVisibility(View.VISIBLE);
            holder.rootLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            holder.rootLayout.setBackgroundColor(context.getResources().getColor(R.color.white_smoke));
            holder.rightIcon.setVisibility(View.GONE);
        }

        if (holder.name.getText().toString().equalsIgnoreCase("My Downloads")
                || holder.name.getText().toString().equalsIgnoreCase("My Playlists")
                || holder.name.getText().toString().equalsIgnoreCase("Change Password")
                || holder.name.getText().toString().equalsIgnoreCase("Log Out")
                || holder.name.getText().toString().equalsIgnoreCase("Profile")
                || holder.name.getText().toString().equalsIgnoreCase("Settings")
                || holder.name.getText().toString().equalsIgnoreCase("Upanisad")) {
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.mild_grey));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.name.getText().toString().equalsIgnoreCase("Sign out")) {
                    Session.getInstance(context, TAG).logout();
                    CommonMethod.clearAllPreviousActivity(context, LoginActivity.class);
                } else if (holder.name.getText().toString().equalsIgnoreCase("Profile")) {
//                    CommonMethod.changeActivityText(context, MyProfileActivity.class, holder.name.getText().toString(), "", "");
                }else if(holder.name.getText().toString().equalsIgnoreCase("Change Password")){
//                    CommonMethod.changeActivityText(context,Change);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.right_icon)
        ImageView rightIcon;
        @BindView(R.id.parent)
        RelativeLayout rootLayout;
        @BindView(R.id.viewId)
        View view;

        public CustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
