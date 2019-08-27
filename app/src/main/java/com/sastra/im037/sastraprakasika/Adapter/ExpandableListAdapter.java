package com.sastra.im037.sastraprakasika.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<VolumeDetailsModel> arrayList;
    public static final String TAG = ExpandableListAdapter.class.getSimpleName();
    BillingClient billingClient;
    SkuDetails skuDetails;
    Activity activity;

    private int lastPosition = -1;

    public ExpandableListAdapter(Activity activity, Context context, ArrayList<VolumeDetailsModel> arrayList, BillingClient billingAppClient) {
        this.context = context;
        this.arrayList = arrayList;
        this.billingClient = billingAppClient;
        this.activity = activity;
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Context context = this.context;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.volume_details_row, parent, false);
        }

        TextView title = convertView.findViewById(R.id.titleex);
        TextView volumeValue = convertView.findViewById(R.id.volumevalues);
        TextView classesValue=convertView.findViewById(R.id.classesvalue);
        final ImageView arrow = convertView.findViewById(R.id.arrow);
        Button priceBtn = convertView.findViewById(R.id.price);
        // hypen code static
        String volume = arrayList.get(groupPosition).getTitle();
        //commented below anand
        title.setText(volume);

//




        List<String> skuList = new ArrayList<> ();
        skuList.add("purchase_free");

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                        if (skuDetailsList != null && skuDetailsList.size() > 0){
                            skuDetails = skuDetailsList.get(0);

                        }
                    }
                });



        classesValue.setText(arrayList.get(groupPosition).getClasses());

        if (arrayList != null && arrayList.size() > 0){

            if (arrayList.get(groupPosition).isPurchase()){
                priceBtn.setVisibility(View.INVISIBLE);
            }else {
                priceBtn.setVisibility(View.VISIBLE);
                priceBtn.setText(arrayList.get(groupPosition).getPrice());
            }
        }



        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList.get(groupPosition).isPurchase()){
                    //Already Purchasedoa
                    Toast.makeText(context, "Already purchased / Not available currently", Toast.LENGTH_LONG).show();
                }else {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetails)
                            .build();
                    int responseCode = billingClient.launchBillingFlow(activity,flowParams);
                    if (responseCode == 0){
                        callwebservice(arrayList.get(groupPosition).getPostid());
                    }


                }

               /* AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Kindly confirm your In-App Purchase of topic Rs.280");
                alertbox.setTitle("In-App Purchase");

                alertbox.setPositiveButton("Buy Now",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                callwebservice(arrayList.get(groupPosition).getPostid());

                            }
                        });
                alertbox.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                Toast.makeText(context, "Payment Declined by user", Toast.LENGTH_LONG).show();
                            }
                        });

                alertbox.show();
*/


            }

        });

        int imageResourceId = isExpanded ? R.drawable.up : R.drawable.down;
        arrow.setImageResource(imageResourceId);

        Animation animation = AnimationUtils.loadAnimation(context, (groupPosition > lastPosition) ? R.anim.up_from_bottom1 : R.anim.up_from_bottom1);
        convertView.startAnimation(animation);
        lastPosition = groupPosition;
        return convertView;
    }

    private boolean callwebservicePayment(String postid) {
        final boolean status= false;
        new WebServices(context, TAG).getPayment_verification(Session.getInstance(context, TAG).getUserId(),postid, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.optString("status").equalsIgnoreCase("0")){

                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
        return false;

    }


    private void callwebservice(String postid) {
        new WebServices(context, TAG).getPayment_list(Session.getInstance(context, TAG).getUserId(),postid, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {

            }

            @Override
            public void onError(String message, String title) {

            }
        });
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
        Animation animation = AnimationUtils.loadAnimation(context, (groupPosition > lastPosition) ? R.anim.slide_down : R.anim.slide_down);
        convertView.startAnimation(animation);
        lastPosition = groupPosition;
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}