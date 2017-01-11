package com.mattricks.deliverit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.adapters.ProductAdapter;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.DividerItemDecoration;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by mahasagar on 10/11/16.
 */
public class TabFragmentProducts extends Fragment {

    private ViewPager mViewPager;
    BottomBar bottomBar;
    RequestQueue requestQueue;

    private List<Product> productList = new ArrayList<>();
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private ProductAdapter mAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    public TabFragmentProducts(){
    }

    public static TabFragmentProducts newInstance(String text, BottomBar bottomBar) {
        Bundle args = new Bundle();
        TabFragmentProducts sampleFragment = new TabFragmentProducts();
        sampleFragment.bottomBar = bottomBar;
        sampleFragment.setArguments(args);
        return sampleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.tabfragmentproduct, container, false);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(mAdapter);

        /*FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//*final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity())
                        .setTitle("MaterialDialog")
                        .setMessage("Hello world!");*//*
                final MaterialDialog filter = new MaterialDialog(getActivity())
                        .setTitle("Filter Explore")
                        .setContentView(
                                R.layout.custom_filter_layout);

                filter.setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter.dismiss();
                    }
                }).setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                filter.dismiss();
                            }
                        });
                filter.show();
            }
        });*/
        getProducts(rootView);
        return rootView;
    }


    private void getProducts( final View view) {
        final String URL = Constants.APP_URL + Constants.API_PRODUCTS;

        mAdapter = new ProductAdapter(productList);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {@Override
                public void onResponse(String response) {
                    Log.d("Response #: ", response);
                    productList.clear();
                    try {
                        JSONArray JsonResult = new JSONArray(response);
                        Product product = null;
                        for (int i = 0; i < JsonResult.length(); i++) {
                            JSONObject object = JsonResult.getJSONObject(i);

                            Log.d("object #: ", object.toString());
                            String _id =  object.getString("_id");
                            String brand = object.getString("brand");
                            String form =  object.getString("form");
                            String marketingCompany =  object.getString("marketingCompany");
                            String name = object.getString("name");

                            String productImgUrl =  object.getString("productImgUrl");
                            String packSize =  object.getString("packSize");
                            String packType = object.getString("packType");
                            String distributorPrice =  object.getString("distributorPrice");
                            String MRP =  object.getString("MRP");
                            String distributorId = object.getString("distributorId");
                            String distributorName =  object.getString("distributorName");

                            product = new Product( _id, brand, form,marketingCompany,name,productImgUrl,packSize,
                                    packType,distributorPrice, MRP,distributorId,distributorName);
                            productList.add(product);
                        }
                    }catch(Exception e){
                        Log.d("e #: ", e.getMessage());
                    }

                    try {
                        Log.d("productList after #: ",""+ productList.size());
                        mAdapter = new ProductAdapter(productList);
                        recyclerView.setAdapter(mAdapter);
                        restoreRecycleView();
                        mAdapter.notifyDataSetChanged();
                        Log.d("productList #: ",""+ productList.size());
                    }catch(NullPointerException e){

                    }
                }
                },
                new Response.ErrorListener()
                {@Override
                public void onErrorResponse(VolleyError error) {
//                    Log.e("Error.Response #", error.getMessage());
                }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(postRequest);
    }
    private void restoreRecycleView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
       try {
           recyclerView.setLayoutManager(mLayoutManager);
//           recyclerView.setItemAnimator(new DefaultItemAnimator());
//           RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//           itemAnimator.setAddDuration(1000);
//           itemAnimator.setRemoveDuration(1000);
//           recyclerView.setItemAnimator(itemAnimator);
       }catch(NullPointerException e){

       }
    }
}