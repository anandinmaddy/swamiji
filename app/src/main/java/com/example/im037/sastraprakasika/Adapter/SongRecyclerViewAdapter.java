package com.example.im037.sastraprakasika.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.SearchDetailedActivity;
import com.example.im037.sastraprakasika.Model.SearchModel;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<SearchModel> arrayList;
    String type;

    public SongRecyclerViewAdapter(Activity context, ArrayList<SearchModel> arrayList,String type) {
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_row_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.searchTitle.setText(arrayList.get(position).getTitle());
        holder.imageicon.setImageResource(R.drawable.righticonsearch);
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchModel search_det = arrayList.get(position);
                Intent intent = new Intent(context, SearchDetailedActivity.class);
                intent.putExtra( "model",search_det );
                intent.putExtra( "TYPE",type );
                context.startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.search_image)
//        ImageView searchImage;
        @BindView(R.id.search_title)
        TextView searchTitle;
        @BindView(R.id.img1)
        ImageView imageicon;

//        @BindView(R.id.search_type)
//       TextView searchType;
//       @BindView(R.id.search_duration)
//        TextView searchDuration;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
