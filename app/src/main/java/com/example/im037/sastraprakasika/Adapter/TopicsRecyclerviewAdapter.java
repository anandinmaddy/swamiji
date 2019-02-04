package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.Itopice_listener;
import com.example.im037.sastraprakasika.Activity.Topics_detailed_items;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicsRecyclerviewAdapter extends RecyclerView.Adapter<TopicsRecyclerviewAdapter.Customview> {

    // interface class declare then add conctructor
    private Itopice_listener itopice_listener;
    Context context;
    ArrayList<ListOfTopicsModels> listOfTopicsModels;

    public TopicsRecyclerviewAdapter(Context context, ArrayList<ListOfTopicsModels> listOfTopicsModels,Itopice_listener listener) {
        this.context = context;
        this.listOfTopicsModels = listOfTopicsModels;
        this.itopice_listener = listener;

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
                .into(holder.topics_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfTopicsModels model = listOfTopicsModels.get( position );
                Intent intent = new Intent(context, Topics_detailed_items.class);
                intent.putExtra("Data",model);
                context.startActivity( intent );


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




        holder.songTitle.setText(value);
        holder.volumeNo.setText(listOfTopicsModels.get(position).getSong_volume());




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
        @BindView(R.id.topics_image)
        ImageView topics_image;
        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.volume_no)
        TextView volumeNo;
        public Customview(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
