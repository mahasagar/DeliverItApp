package com.mattricks.deliverit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Distributor;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahasagar on 15/1/17.
 */
public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.MyViewHolder> {

    private List<Distributor> distributorList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView distributorName,distributorPrice;

        public MyViewHolder(View view) {
            super(view);

            Log.d("(((((( #: ",""+ distributorList.size());
            distributorName = (TextView) view.findViewById(R.id.tv_distributorname);
            distributorPrice = (TextView) view.findViewById(R.id.tv_distributorprice);

            Log.d("$$$$$$ #: ",""+ distributorList.size());
        }
    }


    public DistributorAdapter(List<Distributor> distributorList) {

        Log.d("construtor #: ",""+ distributorList.size());
        this.distributorList = distributorList;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("### #: ",""+ distributorList.size());
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_distributor, parent, false);
        itemView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));

        v = itemView;

        Log.d("^^^^^^^^^^ #: ",""+ distributorList.size());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Log.d("!!!!!!!! #: ",""+ distributorList.size());
        final Distributor distributor = distributorList.get(position);
        holder.distributorName.setText(distributor.getDistributorName());
        holder.distributorPrice.setText(distributor.getDistributorPrice());
    }

    @Override
    public int getItemCount() {
        return distributorList.size();
    }

/*
    private void addToCart( final String username, final String password, final View view) {

        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        final String URL = Constants.APP_URL + Constants.API_LOGIN;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {@Override
                public void onResponse(String response) {
                    Log.d("Response #: ", response);
                    try {
                        JSONObject JsonResult = new JSONObject(response.toString());

                        boolean status = JsonResult.getBoolean("status");
                        if(status) {
                            JSONObject JsonResultData = JsonResult.getJSONObject("result");
                            //Toast.makeText(view.getContext(), "JsonResultData : " + JsonResultData.toString(), Toast.LENGTH_LONG).show();

                        }else{
                            String result = JsonResult.getString("result");
                            Toast.makeText(view.getContext(), result.toString(), Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        Log.d("e #: ", e.getMessage());
                    }
                }
                },
                new Response.ErrorListener()
                {@Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response #", error.getMessage());
                }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username",username.trim());
                params.put("password",password.trim());
                return params;
            }
        };
        requestQueue.add(postRequest);
    }*/
}