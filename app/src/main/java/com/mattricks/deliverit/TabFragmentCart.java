package com.mattricks.deliverit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roughike.bottombar.BottomBar;

/**
 * Created by mahasagar on 10/11/16.
 */
public class TabFragmentCart extends Fragment {


    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    private ViewPager mViewPager;
    BottomBar bottomBar;
    public TabFragmentCart(){

    }

    public static TabFragmentCart newInstance(String text, BottomBar bottomBar) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        TabFragmentCart sampleFragment = new TabFragmentCart();
        sampleFragment.bottomBar = bottomBar;
        sampleFragment.setArguments(args);
        return sampleFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.tabfragmentinbox, container, false);

        return rootView;
    }

}