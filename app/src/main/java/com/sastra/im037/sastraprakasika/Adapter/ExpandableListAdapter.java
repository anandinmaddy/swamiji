package com.sastra.im037.sastraprakasika.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
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
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.sastra.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements BillingClientStateListener, PurchasesUpdatedListener {
    Context context;
    ArrayList<VolumeDetailsModel> arrayList;
    public static final String TAG = ExpandableListAdapter.class.getSimpleName();
    private BillingClient billingClient;
    SkuDetails skuDetails;
    Activity activity;
    List<String> skuList = new ArrayList<> ();
    boolean test = false;
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
        final Button priceBtn = convertView.findViewById(R.id.price);
        // hypen code static
        String volume = arrayList.get(groupPosition).getTitle();
        Constant.arrayListPurchase = arrayList;
        //commented below anand
        title.setText(volume);

        skuList.add("purchase_free");

     /*   Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        for (Purchase sourcePurchase : purchasesResult.getPurchasesList()) {
            if(sourcePurchase != null){

                ConsumeResponseListener listener = new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {

                    }

                };
                billingClient.consumeAsync(ConsumeParams.newBuilder().build(), listener);
            }else{
                System.out.println("null");
            }
        }*/


        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                        if (skuDetailsList != null && skuDetailsList.size() > 0){
                            skuDetails = skuDetailsList.get(groupPosition);
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
                    BillingResult response = billingClient.launchBillingFlow(activity,flowParams);

                    if (response.getResponseCode() == BillingClient.BillingResponseCode.OK){
                        //priceBtn.setVisibility(View.INVISIBLE);
                      //  callwebservice(arrayList.get(groupPosition).getPostid());
                        Constant.purchaseKey = groupPosition;
                    }else {
                        Toast.makeText(context, response.getDebugMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onBillingSetupFinished(BillingResult billingResult) {

    }


    @Override
    public void onBillingServiceDisconnected() {

    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                // handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

}