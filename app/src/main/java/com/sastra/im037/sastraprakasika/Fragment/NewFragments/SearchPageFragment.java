package com.sastra.im037.sastraprakasika.Fragment.NewFragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Activity.SearchActivity;
import com.sastra.im037.sastraprakasika.Adapter.SongRecyclerViewAdapter;
import com.sastra.im037.sastraprakasika.Adapter.SongRecyclerViewAdapterNew;
import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.SearchModel;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.sastra.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.sastra.im037.sastraprakasika.utils.Selected;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment implements NetworkStateReceiverListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView searchRecyclerview;
    ListView searchLinearview;
    RelativeLayout.LayoutParams params;
    boolean isSearchClicked;
    Spinner searchSpin;
    EditText searchBar;
    static DiscousesAppDatabase db;
    boolean isDiscourseSelected, isLibrarySelected;
    boolean isLibAvailable,isDiscoursesAvailable;
    RelativeLayout rl_min_header;
    LinearLayout bottomLayoutblank;

    LinearLayout offlineViewer;
    TextView offlineLink;
    //  @BindView( R.id.image_close )
//            ImageView imageView_close;

    String type;
    private static final String TAG= SearchActivity.class.getSimpleName();
    public ArrayList<SearchModel> arrayList=new ArrayList<>();
    SongRecyclerViewAdapter adapter;
    SongRecyclerViewAdapterNew adapterNew = null;

    ImageView back,cleartxt;
    LinearLayout main_layout;
    TextView titleView;
    TextView noSearchResult,libraryTxt,discoursesTx;
    private OnFragmentInteractionListener mListener;
    private TabLayout tablayout;
    int margin=0;
    SlidingUpPanelLayout sliding_layout;
    public SearchPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public SearchPageFragment newInstance(String param1, String param2) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_search, container, false);

        CommonActivity.setSelected( Selected.SEARCH);
        titleView = (TextView) getActivity().findViewById(R.id.title);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "ListOfTopics").allowMainThreadQueries().build();
        rl_min_header = (RelativeLayout) getActivity().findViewById(R.id.rl_min_header);
        bottomLayoutblank = (LinearLayout) getActivity().findViewById(R.id.bottomLayout);

        searchRecyclerview = (RecyclerView) view.findViewById(R.id.searchRecyclerview) ;
        searchLinearview = (ListView) view.findViewById(R.id.searchLinearview) ;

        searchSpin = (Spinner) view.findViewById(R.id.search_spin);
        cleartxt = (ImageView) view.findViewById(R.id.clearTxt);

        searchBar = (EditText) view.findViewById(R.id.search_bar);
        back = getActivity().findViewById(R.id.back);
        main_layout = view.findViewById(R.id.mainSearch);
        noSearchResult = view.findViewById(R.id.noresult);
        back.setVisibility(View.GONE);
        discoursesTx = view.findViewById(R.id.discoursesTx);
        libraryTxt = view.findViewById(R.id.libraryTxt);
        sliding_layout = getActivity().findViewById(R.id.sliding_layout);

        offlineViewer = (LinearLayout) view.findViewById(R.id.offlineViewer);
        offlineLink = view.findViewById(R.id.offlineLectureLink);

//        ButterKnife.bind(getActivity());
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SongRecyclerViewAdapter(getActivity(), arrayList,type,getFragmentManager());
        searchRecyclerview.setAdapter(adapter);
        init();

        titleView.setText("Search");
        searchRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(getActivity(), v);
            }
        });

        offlineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("offline",true);
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 2;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        int dpValue = 65; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;
        margin = (int)(dpValue * d);


        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(getActivity(), v);
            }
        });

        searchRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(getActivity(), v);
            }
        });


        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyboardNew(getActivity(), v);
            }
        });

        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );


        libraryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDiscourseSelected = false;
                isLibrarySelected = true;
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    libraryTxt.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.orange) );
                    discoursesTx.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.txtview_border) );
                } else {
                    libraryTxt.setBackground(ContextCompat.getDrawable(getContext(), R.color.orange));
                    discoursesTx.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.txtview_border));
                }
                libraryTxt.setTextColor(getResources().getColor(R.color.white));
                discoursesTx.setTextColor(getResources().getColor(R.color.orange));
                showLibraryUI();



            }
        });

        discoursesTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDiscourseSelected = true;
                isLibrarySelected = false;
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    discoursesTx.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.orange) );
                    libraryTxt.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.txtview_border) );
                } else {
                    discoursesTx.setBackground(ContextCompat.getDrawable(getContext(), R.color.orange));
                    libraryTxt.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.txtview_border));
                }
                discoursesTx.setTextColor(getResources().getColor(R.color.white));
                libraryTxt.setTextColor(getResources().getColor(R.color.orange));
                showDiscourseUI();
            }
        });
       // searchSpin.setOnItemSelectedListener(SearchPageFragment.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Discourses");
        categories.add("My Library");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_new, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        searchSpin.setAdapter(dataAdapter);

        type = searchSpin.getSelectedItem().toString().toLowerCase();

        searchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CommonMethod.hideKeyboardNew(getActivity(), view);

                if (position == 0){
                    isDiscourseSelected = true;
                    isLibrarySelected = false;
                    showDiscourseUI();
                }else{
                    isDiscourseSelected = false;
                    isLibrarySelected = true;
                    showLibraryUI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isDiscourseSelected = false;
                isLibrarySelected = false;
                showDiscourseUI();
            }
        });


        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 500) {
                  /*  params.setMargins(0, 0, 0, 0);
                    sliding_layout.setLayoutParams(params);
                    rl_min_header.setVisibility(View.GONE);
                    bottomLayoutblank.setVisibility(View.GONE);*/

                }else {
                    if (Constant.isPlaying){
                        params.setMargins(0, 0, 0, margin);
                        sliding_layout.setLayoutParams(params);
                    }

                    //    if (isSearchClicked){
                    //    sliding_layout.setLayoutParams(params);

              //      }

                }

            }
        });


        return view;
    }


    private void showLibraryUI() {
        if(isLibAvailable){
            if (Constant.arrayListLectureslineSongs.size() > 0 ){
                noSearchResult.setVisibility(View.GONE);
                searchRecyclerview.setVisibility(View.GONE);
                searchLinearview.setVisibility(View.VISIBLE);
                adapterNew = new SongRecyclerViewAdapterNew(Constant.arrayListLectureslineSongs, getActivity());
                searchLinearview.setAdapter(adapterNew);
            }else {
                searchLinearview.setVisibility(View.GONE);
                searchRecyclerview.setVisibility(View.GONE);
            }
        }else {
            searchLinearview.setVisibility(View.GONE);
            searchRecyclerview.setVisibility(View.GONE);


        }
    }

    private void showDiscourseUI() {
        if(isDiscoursesAvailable){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            noSearchResult.setVisibility(View.GONE);
                searchLinearview.setVisibility(View.GONE);
                searchRecyclerview.setVisibility(View.VISIBLE);

            adapter = new SongRecyclerViewAdapter(getActivity(), arrayList,type,getFragmentManager());
                searchRecyclerview.setAdapter(adapter);

        }else {
            searchLinearview.setVisibility(View.GONE);
            searchRecyclerview.setVisibility(View.GONE);
           /* noSearchResult.setVisibility(View.VISIBLE);
            noSearchResult.setText("No results were found. Please try another keyword.");*/

        }



    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

        cleartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noSearchResult.setVisibility(View.GONE);
                searchBar .setText("");
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isSearchClicked = true;
                String text = searchBar.getText().toString();
                int maxChar = 2;
                if (text.length() > 0) {
                    cleartxt.setVisibility(View.VISIBLE);
                }else {
                    cleartxt.setVisibility(View.GONE);
                }

                if (text.length() > maxChar) {
                    searchRecyclerview.setVisibility(View.GONE);
                    searchLinearview.setVisibility(View.GONE);
                    noSearchResult.setVisibility(View.VISIBLE);
                    noSearchResult.setText("Searching. Please wait...");
                    showSearchData();
                } else if (text.length() <= maxChar || text.length() == 0) {
                    arrayList.clear();
                    searchRecyclerview.setVisibility(View.GONE);
                    searchLinearview.setVisibility(View.GONE);
                    //  adapter.notifyDataSetChanged();
                }


            }



            @Override
            public void afterTextChanged(Editable editable) {


            }

        });

    }





    public void showSearchData() {
        new WebServices(getActivity(), TAG).setSearchUpdate(Session.getInstance(getActivity(), TAG).getUserId(),type, searchBar.getText().toString(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                noSearchResult.setVisibility(View.GONE);
                searchRecyclerview.setVisibility(View.VISIBLE);
                System.out.println("search response:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    arrayList.clear();
                    JSONObject jsonObject = response.optJSONObject("data");

                    JSONArray jsonArrayDiscourses = jsonObject.optJSONArray("alldiscourses");
                    JSONArray wholelibrary = jsonObject.optJSONArray("library");
                    db.itemSongDao().deleteAll();
                    Constant.arrayListLectureslineSongs.clear();
                        for (int i = 0; i < wholelibrary.length(); i++) {
                            if (wholelibrary.optJSONObject(i) != null && wholelibrary.optJSONObject(i).optString("title")!= null) {
                                isLibAvailable = true;
                                JSONArray jsonArrayLibrary = wholelibrary.getJSONObject(i).getJSONArray("tracks");
                            for (int j = 0; j < jsonArrayLibrary.length(); j++) {

                                SearchModel model = new SearchModel();
                                if (jsonArrayLibrary.optJSONObject(j) != null && jsonArrayLibrary.optJSONObject(j).optString("title") != null) {
                                    ItemSong itemSong = new ItemSong();
                                    itemSong.setUrl(jsonArrayLibrary.optJSONObject(j).optString("mp3"));
                                    itemSong.setTitle(jsonArrayLibrary.optJSONObject(j).optString("title"));
                                    itemSong.setDuration(jsonArrayLibrary.optJSONObject(j).optString("time"));
                                    itemSong.setTrackId(jsonArrayLibrary.optJSONObject(j).optString("track_id"));
                                    itemSong.setClassName(jsonArrayLibrary.optJSONObject(j).optString("track_id"));

                                    db.itemSongDao().insertAll(itemSong);
                                    Constant.arrayListLectureslineSongs.add(itemSong);
                                } else {
                                    searchRecyclerview.setVisibility(View.GONE);
                                    searchLinearview.setVisibility(View.GONE);
                                    noSearchResult.setVisibility(View.VISIBLE);
                                    noSearchResult.setText("No results were found. Please try another keyword.");
                                }
                            }
                        }else {
                                isLibAvailable = false;
                                searchRecyclerview.setVisibility(View.GONE);
                                searchLinearview.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                                noSearchResult.setText("No results were found. Please try another keyword.");
                               /* searchRecyclerview.setVisibility(View.GONE);
                                searchLinearview.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                                noSearchResult.setText("No results were found. Please try another keyword.");*/
                              /*  searchRecyclerview.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                                noSearchResult.setText("No Result Found !");*/
                            }
                    }

                    db.searchModelDao().deleteAll();

                    for (int i = 0; i < jsonArrayDiscourses.length(); i++) {
                            SearchModel model = new SearchModel();
                            if (jsonArrayDiscourses.optJSONObject(i) != null && jsonArrayDiscourses.optJSONObject(i).optString("title")!= null){
                                isDiscoursesAvailable = true;

                                model.setTitle(jsonArrayDiscourses.optJSONObject(i).optString("title"));
                                model.setPost_id(jsonArrayDiscourses.optJSONObject(i).optString("post_id"));
                                model.setParentid(jsonArrayDiscourses.optJSONObject(i).optString("parentid"));
                                model.setSubid(jsonArrayDiscourses.optJSONObject(i).optString("subid"));
                                model.setImage_url(jsonArrayDiscourses.optJSONObject(i).optString("image_url"));
                                model.setDescription(jsonArrayDiscourses.optJSONObject(i).optString("description"));
                                // model.setSubid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"));
                                // model.setType(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"));
                                // model.setTime(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"));
                                // model.setImage_url(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"));*/
                                arrayList.add(model);

                                db.searchModelDao().insertAll(model);
                            }else {
                                isDiscoursesAvailable = false;

                                searchRecyclerview.setVisibility(View.GONE);
                                searchLinearview.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                                noSearchResult.setText("No results were found. Please try another keyword.");
                             /*   searchRecyclerview.setVisibility(View.GONE);
                                searchLinearview.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                                noSearchResult.setText("No results were found. Please try another keyword.");*/
                            }
                        }

                        if(isLibrarySelected){
                            showLibraryUI();
                        }else {
                            showDiscourseUI();
                        }


                            // model.setParentid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"));


                 /*   if (arrayList.size() > 0 ){
                        noSearchResult.setVisibility(View.GONE);
                        searchRecyclerview.setVisibility(View.VISIBLE);
                        adapter = new SongRecyclerViewAdapter(getActivity(), arrayList,type,getFragmentManager());
                        searchRecyclerview.setAdapter(adapter);
                    }else {
                        searchRecyclerview.setVisibility(View.GONE);
                        noSearchResult.setVisibility(View.VISIBLE);
                    }*/

                }else{
                    searchRecyclerview.setVisibility(View.GONE);
                    noSearchResult.setVisibility(View.VISIBLE);
                    noSearchResult.setText("No results were found. Please try another keyword.");
                  //  CommonMethod.showSnackbar(searchSpin,response.optString("resultmessage"),getActivity());
                }

            }

            @Override
            public void onError(String message, String title) {
                searchRecyclerview.setVisibility(View.GONE);
                noSearchResult.setVisibility(View.VISIBLE);
                noSearchResult.setText("No results were found. Please try another keyword.");
                Log.e("Search","Error");

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString().toLowerCase();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void networkAvailable() {
        main_layout.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        main_layout.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        main_layout.setVisibility(View.GONE);
        try {
            ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
            connectivityReceiver.addListener(this);
            getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
