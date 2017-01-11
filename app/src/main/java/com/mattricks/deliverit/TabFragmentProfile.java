package com.mattricks.deliverit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mattricks.deliverit.utilities.SharedPreference;
import com.roughike.bottombar.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mahasagar on 12/11/16.
 */
public class TabFragmentProfile extends Fragment {

    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    private ViewPager mViewPager;
    BottomBar bottomBar ;
    SharedPreference sharedPreference;

    @Bind(R.id.btn_startExplore)
    Button btn_user_to_client;
    public TabFragmentProfile(){

        sharedPreference = new SharedPreference();
    }

    public static TabFragmentProfile newInstance(String text, BottomBar bottomBar) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        TabFragmentProfile sampleFragment = new TabFragmentProfile();
        sampleFragment.bottomBar = bottomBar;
        sampleFragment.setArguments(args);

        return sampleFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.tabfragmentprofile, container, false);

        btn_user_to_client = (Button)rootView.findViewById(R.id.btn_user_to_client);
        btn_user_to_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String User = sharedPreference.getUser(v.getContext());
                    sharedPreference.removeUser(v.getContext());
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }catch(NullPointerException e){

                }

            }
        });

        return rootView;
    }

}