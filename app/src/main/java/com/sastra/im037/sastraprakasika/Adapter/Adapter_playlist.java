package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.sastra.im037.sastraprakasika.Fragment.NewFragments.PlayListDetailFragment;
import com.sastra.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.sastra.im037.sastraprakasika.Model.PlayList;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_playlist extends RecyclerView.Adapter<Adapter_playlist.Customview> {

    String title_image;
    String imageview_title;
    ArrayList<PlayList> contentsList;
    Context context;
    FragmentManager fragmentManager;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout playListName;
    public static final String TAG = PlaylistsFragment.class.getSimpleName();

    public Adapter_playlist(ArrayList contentsList, Context context,FragmentManager fragmentManager) {
        this.contentsList = contentsList;
        this.title_image = title_image;
        this.imageview_title = imageview_title;
        this.shimmerFrameLayout = shimmerFrameLayout;
        this.playListName = playListName;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Adapter_playlist.Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_playlist.Customview(LayoutInflater.from(context).inflate(R.layout.fragment_playlist_items, parent, false));
    }


    public class Customview extends RecyclerView.ViewHolder {
        @BindView(R.id.album_image_title)
        TextView title_img;
        @BindView(R.id.album_image_playlist)
        ImageView song_img;
        @BindView(R.id.arrowicon)
        ImageView img_arrow;
        @BindView(R.id.shimmer_view_container)
        ShimmerFrameLayout shimmerFrameLayout;
        @BindView(R.id.playListName)
        LinearLayout playListName;
        //        @BindView(R.id.volume_no)
//        TextView volumeNo;
        public Customview(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    static class ViewHolderItem {
        TextView title_img;
        ImageView song_img;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        ImageView playlist_track;
        ImageView downloadBtn;
        RelativeLayout loadingDownload;
        ShimmerFrameLayout shimmerFrameLayout;
        //   MaterialProgressBar button_progress_2;
        //     CircleProgressView circleProgressView;
        ProgressBar circularProgressbar;
        LinearLayout playListName,playListSelection;

    }



    @Override
    public void onBindViewHolder(@NonNull final Adapter_playlist.Customview holder, final int position) {
       holder.title_img.setText((CharSequence) contentsList.get(position).getPlayer_name());
        imageview_title = contentsList.get(position).getPlayer_name();

        holder.playListName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Playlist")
                        .setMessage("Are you sure you want to delete this playlist?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                deleteWebservice(contentsList.get(position).getPlayer_id());
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            return false;
            }


        });
        holder.playListName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shimmerFrameLayout.setVisibility(View.VISIBLE);

                holder.shimmerFrameLayout.startShimmer();

                callWebservice(contentsList.get(position).getPlayer_id(),position,holder.shimmerFrameLayout);


/*


 */
              /*  Intent intent = new Intent(context,Playlist_detailed_Activity.class);
                 //intent.putExtra("title",holder.title_img.setText((CharSequence) title_image.get(position)));
                context.startActivity(intent);*/

            }
        });

        }


    private void deleteWebservice(String playerId) {
        new WebServices(context, TAG).deletePlayLists(Session.getInstance(context, TAG).getUserId(),playerId, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Constant.currentTab = 3;
                    Constant.backPress = true;
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Toast.makeText(context,"Playlist deleted",Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onError(String message, String title) {
            }
        });
    }


    private void callWebservice(final String player_id, final int position, final ShimmerFrameLayout shimmerFrameLayout) {
        new WebServices(context, TAG).getPlaylistSongs(Session.getInstance(context, TAG).getUserId(),player_id, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                Constant.trackList.clear();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    JSONArray contentArray = response.optJSONArray("data");
                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject jsonObject = contentArray.optJSONObject(i);
                        Constant.trackList.add(Integer.parseInt(jsonObject.optString("track_id")));
                    }
                }
                notifyDataSetChanged();

                Bundle profileData = new Bundle();
                profileData.putString("data",contentsList.get(position).getPlayer_name());
                profileData.putString("player_id",player_id);
                PlayListDetailFragment fragment2 = new PlayListDetailFragment();
                fragment2.setArguments(profileData);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

            @Override
            public void onError(String message, String title) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }





}

