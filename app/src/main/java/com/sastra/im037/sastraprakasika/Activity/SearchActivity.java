package com.sastra.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sastra.im037.sastraprakasika.Adapter.SongRecyclerViewAdapter;
import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.Model.SearchModel;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.utils.Selected;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends CommonActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.searchRecyclerview)
    RecyclerView searchRecyclerview;
    @BindView(R.id.search_spin)
    Spinner searchSpin;
    @BindView(R.id.search_bar)
    EditText searchBar;

  //  @BindView( R.id.image_close )
//            ImageView imageView_close;

    String type;
    private static final String TAG=SearchActivity.class.getSimpleName();
    public ArrayList<SearchModel>arrayList=new ArrayList<>();
    SongRecyclerViewAdapter adapter;
    ImageView back;
    LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_search);
        setView(R.layout.activity_search, "Search");
        setSelected( Selected.SEARCH);

        back = findViewById(R.id.back);
        main_layout = findViewById(R.id.mainSearch);
        back.setVisibility(View.GONE);
        ButterKnife.bind(this);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new SongRecyclerViewAdapter(SearchActivity.this, arrayList,type);
        searchRecyclerview.setAdapter(adapter);
        init();


        searchRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(SearchActivity.this, v);
            }
        });


        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(SearchActivity.this, v);
            }
        });

//        searchBar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String text = searchBar.getText().toString();
//                int maxChar = 2;
//                if (text.length() > maxChar) {
//                    showSearchData();
//                } else if (text.length() == 0) {
//                    arrayList.clear();
//                  //  adapter.notifyDataSetChanged();
//                }
//
//
//            }
//
//
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//
//            }
//
//        });




//        searchBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    if (motionEvent.getX()>(view.getWidth()-view.getPaddingRight())){
//                        ((EditText)view).setText("");
//                    }
//                }
//                return false;
//            }
//        });

        // searchview close operation

//        SearchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                // This is where you can be notified when the `SearchView` is closed
//                // and change your views you see fit.
//            }
//        });
        searchSpin.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("All");
        categories.add("Library");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        searchSpin.setAdapter(dataAdapter);
        //System.out.println("arraylist title :::" +adapter);


    }


    public void init()  {

//            // Set bounds of our X button
//            imgX.setBounds(0, 0, imgX.getIntrinsicWidth(), imgX.getIntrinsicHeight());
//
//            // There may be initial text in the field, so we may need to display the button
//            manageClearButton();

        searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                searchBar .setText("");

//                    // Is there an X showing?
//                    if (et.getCompoundDrawables()[2] == null) return false;
//                    // Only do this for up touches
//                    if (event.getAction() != MotionEvent.ACTION_UP) return false;
//                    // Is touch on our clear button?
//                    if (event.getX() > et.getWidth() - et.getPaddingRight() - imgX.getIntrinsicWidth()) {
//                        et.setText("");
//                        ClearableEditText.this.removeClearButton();
//                    }
                return false;
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = searchBar.getText().toString();
                int maxChar = 2;
                if (text.length() > maxChar) {
                    showSearchData();
                } else if (text.length() == 0) {
                    arrayList.clear();
                    //  adapter.notifyDataSetChanged();
                }


            }



            @Override
            public void afterTextChanged(Editable editable) {


            }

        });

    }





    public void showSearchData() {
        new WebServices(SearchActivity.this, TAG).setSearchUpdate(Session.getInstance(getApplicationContext(), TAG).getUserId(),type, searchBar.getText().toString(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println("search response:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    arrayList.clear();
                    JSONArray jsonArray = response.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SearchModel model = new SearchModel();


                       // model.setParentid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"));
                        model.setTitle(jsonArray.optJSONObject(i).optString("title"));
                        model.setPost_id(jsonArray.optJSONObject(i).optString("post_id"));
                       // model.setSubid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"));
                       // model.setType(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"));
                       // model.setTime(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"));
                       // model.setImage_url(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"));*/
                        arrayList.add(model);


                    }

                  //  adapter = new SongRecyclerViewAdapter(SearchActivity.this, arrayList,type,getFragmentManager());
                    searchRecyclerview.setAdapter(adapter);
                }else{
                    CommonMethod.showSnackbar(searchSpin,response.optString("resultmessage"),SearchActivity.this);
                }

            }

            @Override
            public void onError(String message, String title) {

                Log.e("Search","Error");

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         type = parent.getItemAtPosition(position).toString().toLowerCase();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
