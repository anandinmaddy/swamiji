package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.Itopice_listener;
import com.example.im037.sastraprakasika.Activity.Topics_detailed_items;
import com.example.im037.sastraprakasika.Fragment.NewFragments.dummy.TopicsDetailsFragment;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicsRecyclerviewAdapter extends RecyclerView.Adapter<TopicsRecyclerviewAdapter.Customview> {

    // interface class declare then add conctructor
    FragmentManager fragmentManager;
    Context context;
    ArrayList<ListOfTopicsModels> listOfTopicsModels;


    public TopicsRecyclerviewAdapter(Context context, ArrayList<ListOfTopicsModels> listOfTopicsModels, FragmentManager fragmentManager) {
        this.context = context;
        this.listOfTopicsModels = listOfTopicsModels;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Customview(LayoutInflater.from(context).inflate(R.layout.item_topics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Customview holder, final int position) {

        ListOfTopicsModels model =listOfTopicsModels.get( position );
        Log.d("value", model.getSong_title());
        String value=model.getSong_title();
        String  imgUrl=model.getSong_image();
        System.out.println("Image url:::: "+imgUrl);
        Picasso.get()
                .load(model.getSong_image())
                .placeholder(R.drawable.placeholder_default)
                .into(holder.topics_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle profileData = new Bundle();
                profileData.putString("data",listOfTopicsModels.get(position).getSong_title());
                profileData.putString("data1",listOfTopicsModels.get(position).getSong_image());
                profileData.putString("data2",listOfTopicsModels.get(position).getSong_post_id());
                profileData.putString("data4",listOfTopicsModels.get(position).getSong_image());


                TopicsDetailsFragment fragment2 = new TopicsDetailsFragment();
                fragment2.setArguments(profileData);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
/*

 */
  /*              ListOfTopicsModels model = listOfTopicsModels.get( position );
                Intent intent = new Intent(context, Topics_detailed_items.class);
                intent.putExtra("Data",model);
                context.startActivity( intent );*/


            }
        });

        /*if(value.contains("Tamil")){
            holder.topics_image.setImageResource(R.drawable.tamillib);


        }else if(value.contains("Generic")){

            holder.topics_image.setImageResource(R.drawable.generallib);
        }else if(value.contains("Vedenta")){
            holder.topics_image.setImageResource(R.drawable.vedantalib);
        }
        else if(value.contains("Divyanandam")){
            holder.topics_image.setImageResource(R.drawable.divyanantham1);
        }
        else{
            holder.topics_image.setImageResource(R.drawable.vedanta_icon);
        }*/
       /* Glide.with(context)
                .load(imgUrl)
                .into(holder.topics_image);*/




        holder.song_title.setText(value);
        holder.volume_no.setText(listOfTopicsModels.get(position).getSong_volume());




//       holder.topics_image.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//               mostrarDatosFactura();
//
//
//           }
//      });


//        final LecturesFragment_Audioplay fragment = new LecturesFragment_Audioplay();
//        FragmentTransaction transaction = null;
//        transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.viewpager, fragment); //id of ViewPager
//        transaction.addToBackStack(null);
//        transaction.commit();

    }

    @Override
    public int getItemCount() {
        return listOfTopicsModels.size();
    }



    public class Customview extends RecyclerView.ViewHolder {
        ImageView topics_image;
        TextView song_title;
        TextView volume_no;

        public Customview(View itemView) {
            super(itemView);
            topics_image = (ImageView) itemView.findViewById(R.id.topics_image);
            song_title = (TextView) itemView.findViewById(R.id.song_title);
            volume_no = (TextView) itemView.findViewById(R.id.volume_no);

           ButterKnife.bind(this,itemView);

        }
    }
}
