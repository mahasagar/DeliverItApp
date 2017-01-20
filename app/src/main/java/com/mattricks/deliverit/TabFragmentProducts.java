package com.mattricks.deliverit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mattricks.deliverit.adapters.DistributorAdapter;
import com.mattricks.deliverit.adapters.ProductAdapter;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Distributor;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.CommonService;
import com.mattricks.deliverit.utilities.DividerItemDecoration;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.roughike.bottombar.BottomBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabFragmentProducts extends Fragment {

    BottomBar bottomBar;
    RequestQueue requestQueue;
    int mPosition = 0;
    int quantity = 1;
    EditText itemQuantity;
    String User;
    private List<Product> productList = new ArrayList<>();
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.listEmptyMsg)
    CardView listEmptyMsg;


    RecyclerView recyclerViewDistributor;
    private ProductAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreference sharedPreference;
    CommonService common;

    public TabFragmentProducts() {
        sharedPreference = new SharedPreference();
        common = new CommonService();
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
        View rootView = inflater.inflate(R.layout.tabfragmentproduct, container, false);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //User = sharedPreference.getUser(rootView.getContext());

        mAdapter = new ProductAdapter(productList,getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
                Product oneProduct = productList.get(mPosition);
                // Toast.makeText(getActivity(),"size : "+,Toast.LENGTH_SHORT).show();
                selectDistributorPopUp(productList.get(mPosition).get_id(), oneProduct);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /*FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                *//*final MaterialDialog filter = new MaterialDialog(getActivity())
                        .setTitle("Filter Explore")
                        .setContentView(
                                R.layout.custom_filter_layout);


                filter.show();*//*
            }
        });*/

        getProducts(rootView);
        return rootView;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    protected void selectDistributorPopUp(String productId, Product oneProduct) {
        getDistributor(productId, oneProduct);
    }


    private void getDistributor(final String productId, final Product oneProduct) {
        final String SelectDistributorURL = Constants.APP_URL + Constants.URL_SELECTDISTRIBUTOR;
        final ArrayList<Distributor> distributorArrayList = new ArrayList<>();
        final DistributorAdapter mAdapterDistributor = new DistributorAdapter(distributorArrayList);
        RecyclerView.LayoutManager mLayoutManagerDistributor;
        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Select Distributor")
                .customView(R.layout.selectdistributor_popup, true)
                .cancelable(true)
                .show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        recyclerViewDistributor = (RecyclerView) dialog.findViewById(R.id.recycler_view_distributor);

        distributorArrayList.clear();
        mLayoutManagerDistributor = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewDistributor.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDistributor.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewDistributor.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerViewDistributor.setItemAnimator(itemAnimator);

        recyclerViewDistributor.setLayoutManager(mLayoutManagerDistributor);


        recyclerViewDistributor.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mPosition = position;
                Distributor selItem = distributorArrayList.get(position);
                dialog.dismiss();
                addQuantityPopUp(selItem, oneProduct);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        StringRequest postRequest = new StringRequest(Request.Method.POST, SelectDistributorURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray JsonResult = new JSONArray(response);

                            for (int i = 0; i < JsonResult.length(); i++) {
                                JSONObject object = JsonResult.getJSONObject(i);

                                JSONArray JsonDistributorArray = new JSONArray(object.getString("distributors"));
                                for (int j = 0; j < JsonDistributorArray.length(); j++) {

                                    Distributor distributor = new Distributor();
                                    JSONObject JsonDistributorObject = JsonDistributorArray.getJSONObject(j);
                                    try {
                                        distributor.DistributorID = JsonDistributorObject.getString("_id");
                                        distributor.DistributorName = JsonDistributorObject.getString("name");
                                        distributor.DistributorPrice = JsonDistributorObject.getString("distributorPrice");
                                        distributorArrayList.add(distributor);

                                    } catch (NullPointerException e) {
                                        Log.e("TagFragmentProduct",e.toString());
                                    }
                                 }
                            }
                        } catch (Exception e) {
                            Log.d("e #: ", e.getMessage());
                        }

                        try {
                            dialog.show();
                            recyclerViewDistributor.setAdapter(mAdapterDistributor);
                        } catch (Exception e) {
                            Log.d("@@@ #: ", "" + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.e("Error.Response #", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("productId", productId);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void addQuantityPopUp(final Distributor distributor, final Product oneProduct) {

        quantity = 1;
        final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Add To Cart")
                .customView(R.layout.add_to_cart, true)
                .cancelable(true)
                .show();
        final String URL = Constants.APP_URL + Constants.URL_ADDTOCART;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        AdView mAdView;

        mAdView = (AdView) dialog.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                //Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
              //  Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);


        Button btn_addToCart = (Button) dialog.findViewById(R.id.btn_addToCart);

        Button btn_sub = (Button) dialog.findViewById(R.id.btn_sub);
        Button btn_add = (Button) dialog.findViewById(R.id.btn_add);
        itemQuantity = (EditText) dialog.findViewById(R.id.et_quantity);

        ImageView addQuantityItemImage = (ImageView) dialog.findViewById(R.id.addQuantityItemImage);
        final TextView addQuantityItemName = (TextView) dialog.findViewById(R.id.addQuantityItemName);
        final TextView addQuantityItemPackType = (TextView) dialog.findViewById(R.id.addQuantityItemPackType);
        final TextView addQuantityItemMRP = (TextView) dialog.findViewById(R.id.addQuantityItemMRP);
        final TextView addQuantityDistributorname = (TextView) dialog.findViewById(R.id.addQuantityDistributorname);
        final TextView addQuantityDistributorprice = (TextView) dialog.findViewById(R.id.addQuantityDistributorprice);

        Picasso.with(dialog.getContext()).load(oneProduct.getProductImgUrl()).into(addQuantityItemImage);
        addQuantityItemName.setText(oneProduct.getName());
        addQuantityItemPackType.setText(oneProduct.getPackType());
        addQuantityItemMRP.setText(oneProduct.getMRP());
        addQuantityDistributorname.setText(distributor.getDistributorName());
        addQuantityDistributorprice.setText(distributor.getDistributorPrice());

        btn_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String quan = itemQuantity.getText().toString();
                quantity = Integer.valueOf(quan);
                if (quantity > 1) {
                    quantity--;
                    itemQuantity.setText(String.valueOf(quantity));
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quan = itemQuantity.getText().toString();
                quantity = Integer.valueOf(quan);
                quantity = quantity + 1;
                itemQuantity.setText(String.valueOf(quantity));
            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject JsonResult = new JSONObject(response);
                                    boolean status = JsonResult.getBoolean("status");
                                    if (!status) {
                                        String result = JsonResult.getString("result");
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), oneProduct.getName() + " Added To Cart", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    Log.d("e #: ", e.getMessage());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                    Log.e("Error.Response #", error.getMessage());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        try {
                            params.put("businessId", sharedPreference.getUserId(getActivity()));
                            params.put("businessName", sharedPreference.getUserName(getActivity()));
                            params.put("quantity", itemQuantity.getText().toString());

                            params.put("distributorPrice", distributor.getDistributorPrice());
                            params.put("MRP", oneProduct.getMRP());
                            params.put("productId", oneProduct.get_id());
                            params.put("distributorId", distributor.getDistributorID());
                            params.put("distributorName", distributor.getDistributorName());
                            params.put("brand", oneProduct.getBrand());
                            params.put("form", oneProduct.getForm());
                            params.put("marketingCompany", oneProduct.getMarketingCompanyName());

                            params.put("name", oneProduct.getName());
                            params.put("packSize", oneProduct.getPackSize());
                            params.put("packType", oneProduct.getPackType());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return params;
                    }
                };
                requestQueue.add(postRequest);

            }
        });

    }

    private void getProducts(final View view) {
        final String URL = Constants.APP_URL + Constants.API_PRODUCTS;

        mAdapter = new ProductAdapter(productList,getActivity());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        productList.clear();
                        try {
                            JSONArray JsonResult = new JSONArray(response);
                            Product product = null;
                            for (int i = 0; i < JsonResult.length(); i++) {
                                JSONObject object = JsonResult.getJSONObject(i);

                                String _id = object.getString("_id");
                                String brand = object.getString("brand");
                                String form = object.getString("form");
                                String marketingCompany = object.getString("marketingCompany");
                                String name = object.getString("name");

                                String productImgUrl = object.getString("productImgUrl");
                                String packSize = object.getString("packSize");
                                String packType = object.getString("packType");
                                String MRP = object.getString("MRP");
                                product = new Product(_id, brand, form, marketingCompany, name, productImgUrl, packSize,
                                        packType, "", MRP, "", "");
                                productList.add(product);
                            }
                        } catch (Exception e) {
                            Log.e("TagFragmentProduct",e.toString());
                        }

                        try {
                            mAdapter = new ProductAdapter(productList,getActivity());
                            recyclerView.setAdapter(mAdapter);
                            restoreRecycleView();
                            mAdapter.notifyDataSetChanged();
                            if(productList.isEmpty()){
                                recyclerView.setVisibility(View.GONE);
                                listEmptyMsg.setVisibility(View.VISIBLE);
                            }else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                   listEmptyMsg.setVisibility(View.GONE);
                            }
                        } catch (NullPointerException e) {
                            Log.e("TagFragmentProduct",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.e("Error.Response #", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
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
        } catch (NullPointerException e) {
            Log.e("TagFragmentProduct",e.toString());
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }




}