package com.example.im037.sastraprakasika.Fragment.NewFragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.SearchActivity;
import com.example.im037.sastraprakasika.Adapter.SongRecyclerViewAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.SearchModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.Selected;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView searchRecyclerview;
    Spinner searchSpin;
    EditText searchBar;
    static DiscousesAppDatabase db;


    //  @BindView( R.id.image_close )
//            ImageView imageView_close;

    String type;
    private static final String TAG= SearchActivity.class.getSimpleName();
    public ArrayList<SearchModel> arrayList=new ArrayList<>();
    SongRecyclerViewAdapter adapter;
    ImageView back;
    LinearLayout main_layout;
    TextView titleView;
    TextView noSearchResult;
    private OnFragmentInteractionListener mListener;

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
        View view = inflater.inflate(R.layout.activity_search, container, false);

        CommonActivity.setSelected( Selected.SEARCH);
        titleView = (TextView) getActivity().findViewById(R.id.title);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "ListOfTopics").allowMainThreadQueries().build();

        searchRecyclerview = (RecyclerView) view.findViewById(R.id.searchRecyclerview) ;
        searchSpin = (Spinner) view.findViewById(R.id.search_spin);
        searchBar = (EditText) view.findViewById(R.id.search_bar);
        back = getActivity().findViewById(R.id.back);
        main_layout = view.findViewById(R.id.mainSearch);
        noSearchResult = view.findViewById(R.id.noresult);
        back.setVisibility(View.GONE);
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
       // searchSpin.setOnItemSelectedListener(SearchPageFragment.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("All");
        categories.add("Library");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        searchSpin.setAdapter(dataAdapter);

        type = searchSpin.getSelectedItem().toString().toLowerCase();
        return view;
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
                    noSearchResult.setVisibility(View.VISIBLE);
                    noSearchResult.setText("Searching. Please wait...");
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
        new WebServices(getActivity(), TAG).setSearchUpdate(Session.getInstance(getActivity(), TAG).getUserId(),type, searchBar.getText().toString(), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                noSearchResult.setVisibility(View.GONE);
                searchRecyclerview.setVisibility(View.VISIBLE);
                System.out.println("search response:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    arrayList.clear();
                    JSONArray jsonArray = response.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        db.searchModelDao().deleteAll();
                        SearchModel model = new SearchModel();


                        // model.setParentid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"));
                        model.setTitle(jsonArray.optJSONObject(i).optString("title"));
                        model.setPost_id(jsonArray.optJSONObject(i).optString("post_id"));
                        // model.setSubid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"));
                        // model.setType(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"));
                        // model.setTime(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"));
                        // model.setImage_url(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"));*/
                        arrayList.add(model);

                        db.searchModelDao().insertAll(model);
                    }
                    if (arrayList.size() > 0 ){
                        noSearchResult.setVisibility(View.GONE);
                        searchRecyclerview.setVisibility(View.VISIBLE);
                        adapter = new SongRecyclerViewAdapter(getActivity(), arrayList,type,getFragmentManager());
                        searchRecyclerview.setAdapter(adapter);
                    }else {
                        searchRecyclerview.setVisibility(View.GONE);
                        noSearchResult.setVisibility(View.VISIBLE);
                    }

                }else{
                    searchRecyclerview.setVisibility(View.GONE);
                    noSearchResult.setVisibility(View.VISIBLE);
                    noSearchResult.setText("No Result Found !");
                  //  CommonMethod.showSnackbar(searchSpin,response.optString("resultmessage"),getActivity());
                }

            }

            @Override
            public void onError(String message, String title) {

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

}
