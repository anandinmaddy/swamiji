//package com.example.im037.sastraprakasika.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.im037.sastraprakasika.Activity.PlaylistDetailsActivity;
//import com.example.im037.sastraprakasika.Model.PlayModel;
//import com.example.im037.sastraprakasika.R;
//
//import java.util.ArrayList;
//
//public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.CustomHolder> {
//    Context context;
//    ArrayList<PlayModel> list;
//
//    public PlayListAdapter(Context context, ArrayList<PlayModel> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.play_list_item, parent, false);
//        return new CustomHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final CustomHolder holder, final int position) {
//        holder.title.setText(list.get(position).getTitle());
//        holder.imageView.setImageResource(list.get(position).getImage());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, PlaylistDetailsActivity.class).putExtra("title", list.get(position).getTitle()).putExtra("image", list.get(position).getImage()));
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    public class CustomHolder extends RecyclerView.ViewHolder {
//        TextView title;
//        ImageView imageView;
//
//        public CustomHolder(View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.playTitleTxt);
//            imageView = itemView.findViewById(R.id.playImageView);
//        }
//    }
//}
