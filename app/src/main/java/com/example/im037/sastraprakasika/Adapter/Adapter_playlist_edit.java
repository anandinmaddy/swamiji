package com.example.im037.sastraprakasika.Adapter;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Fragment.NewFragments.ClickEditFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.NewPlaylistFragment;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.PlayList;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_playlist_edit  extends RecyclerView.Adapter<Adapter_playlist_edit.Edit_view>{

    Context context;
    ArrayList<ItemSong> arrayListSong = new ArrayList();
    String playerId;
    FragmentManager fragentManager;
    public static final String TAG = ClickEditFragment.class.getSimpleName();

    public Adapter_playlist_edit(ArrayList title_images, String playerId, Context context, FragmentManager fragentManager) {
        this.arrayListSong = title_images;
        this.playerId = playerId;
        this.context = context;
        this.fragentManager = fragentManager;
    }

    @NonNull
    @Override
    public Adapter_playlist_edit.Edit_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_playlist_edit.Edit_view(LayoutInflater.from(context).inflate(R.layout.playlist_detailed_edit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Edit_view holder, final int position) {

        holder.title_imge_edit.setText(arrayListSong.get(position).getTitle());
        try {
            Picasso.get().load(arrayListSong.get(position).getImageBig()).placeholder(R.drawable.placeholder_default)
                    .into(holder.song_imge_edit);
        }catch (Exception e){
            e.printStackTrace();
        }

        //holder.song_class_edit.setText((CharSequence) class_no.get(position));
        holder.song_dur_edit.setText(arrayListSong.get(position).getDuration());

        holder.add_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callwebservices(arrayListSong.get(position).getTrackId());
                Constant.playListSongSync.remove(arrayListSong.get(position));
                notifyDataSetChanged();
            }
        });

    }

    private void callwebservices(String trackId) {
        {
            final DiscousesAppDatabase db;

            db = Room.databaseBuilder(context,
                    DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

            new WebServices(context, TAG).deleteSong(Session.getInstance(context, TAG).getUserId(),playerId,trackId, new VolleyResponseListerner() {
                List<PlayList> playLists ;

                @Override
                public void onResponse(JSONObject response) throws JSONException {

                    if (response.optString("resultcode").equalsIgnoreCase("200")) {


                    }
                }

                @Override
                public void onError(String message, String title) {
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return arrayListSong.size();
    }

    public class Edit_view extends RecyclerView.ViewHolder {
        @BindView(R.id.album_title_det_list_next_edit)
        TextView title_imge_edit;
        @BindView(R.id.ablum_image_det_list_next_edit)
        ImageView song_imge_edit;
        @BindView(R.id.song_type_next_edit)
        TextView song_class_edit;
        @BindView(R.id.duration_det_list_next_edit)
        TextView song_dur_edit;
        @BindView(R.id.add_edit)
        ImageView add_edit;
        @BindView(R.id.shimmer_view_container)
        ShimmerFrameLayout shimmerFrameLayout;
        public Edit_view(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
