package com.example.im037.sastraprakasika.Fragment.NewFragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.FragmentAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.utils.Selected;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyLibraryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyLibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLibraryFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView back;
    LinearLayout commonactivity_linearlayout;
    TextView commonactivity_titleText;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private  String title[]={"Topics","Lectures","Download","Playlists"};
    private TextView titleView;
    private FragmentAdapter adapter;
    private String TAG="";
    RelativeLayout common_dragview;
    View common_view;
    FrameLayout common_shadow;
    ShimmerFrameLayout shimmerFrameLayout;

    Activity activity;
    Context context;
    String passvalue = "";
    //add me0
    protected View mView;
    private OnFragmentInteractionListener mListener;

    public MyLibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyLibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyLibraryFragment newInstance(String param1, String param2) {
        MyLibraryFragment fragment = new MyLibraryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mylibrary, container, false);
        this.mView = view;
        CommonActivity.setSelected(Selected.MYLIBRARY);

        back=(ImageView)getActivity().findViewById(R.id.back);
        titleView = (TextView) getActivity().findViewById(R.id.title);
        titleView.setText("My Library");
       // titleView.setTextColor(getResources().getColor(R.color.black));

        common_view=(View)getActivity().findViewById(R.id.viewId);
         common_shadow=(FrameLayout)getActivity().findViewById(R.id.shadow);
        //common_shadow.setVisibility(View.GONE);
//        common_view.setVisibility(View.VISIBLE);
         viewpager = (ViewPager) view.findViewById(R.id.viewpager);
         tablayout = (TabLayout) view.findViewById(R.id.tablayout);
         commonactivity_titleText=(TextView)getActivity().findViewById(R.id.title) ;
         commonactivity_linearlayout=(LinearLayout)getActivity().findViewById(R.id.ss);
       // back.setVisibility(View.GONE);
        viewpager.setOffscreenPageLimit(4);
        adapter=new FragmentAdapter(getFragmentManager(),title);

        viewpager.setAdapter(adapter);
        if("player".equalsIgnoreCase( passvalue) ){
            viewpager.setCurrentItem( 2 );
        }else if("playlist".equalsIgnoreCase( passvalue ))
        {
            viewpager.setCurrentItem( 3 );
        }else {
            viewpager.setCurrentItem( 0 );
        }
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        adapter=new FragmentAdapter(getFragmentManager(),title);

        viewpager.setAdapter(adapter);
        if("player".equalsIgnoreCase( passvalue) ){
            viewpager.setCurrentItem( 2 );
        }else if("playlist".equalsIgnoreCase( passvalue ))
        {
            viewpager.setCurrentItem( 3 );
        }else {
            viewpager.setCurrentItem( 0 );
        }
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        // Inflate the layout for this fragment
        return view;
    }

    public void setPosition(int position) {
        viewpager.setCurrentItem(position);
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
}
