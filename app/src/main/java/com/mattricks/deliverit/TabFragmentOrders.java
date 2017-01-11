package com.mattricks.deliverit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mahasagar on 10/11/16.
 */
public class TabFragmentOrders extends Fragment {

    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";

    @Bind(R.id.btn_startExplore)
    Button btn_startExplore;
    private ViewPager mViewPager;
    private BottomBar bottomBar;
    public TabFragmentOrders(){

    }

    public static TabFragmentOrders newInstance(String text,BottomBar bottomBar ) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);
        TabFragmentOrders sampleFragment = new TabFragmentOrders();
        sampleFragment.bottomBar = bottomBar;
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

   /* public static TabFragmentBookings newInstance(ViewPager viewPager) {
        TabFragmentBookings fragment = new TabFragmentBookings();
        fragment.mViewPager = viewPager;
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.tabfragmentbookings, container, false);

        ButterKnife.bind(this, rootView);

        btn_startExplore = (Button)rootView.findViewById(R.id.btn_startExplore);
        btn_startExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getActivity(),"welcome",Toast.LENGTH_SHORT).show();
                    bottomBar.selectTabAtPosition(0,true);
                }catch(NullPointerException e){

                }
            }
        });

        return rootView;
    }

}