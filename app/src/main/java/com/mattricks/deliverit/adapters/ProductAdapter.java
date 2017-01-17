package com.mattricks.deliverit.adapters;

import android.content.Intent;
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
import com.mattricks.deliverit.MainActivity;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Product;
import com.mattricks.deliverit.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sagar on 31/1/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;
    RequestQueue requestQueue;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemPackType,itemMRP,itemQuantity;
        public ImageView itemImage;
        public Button btn_sub_quantity,btn_add_quantity,btnAddToCart;

        int quantity_product = 1;
        String str_quantity_product = "";
        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.itemName);
            itemImage = (ImageView) view.findViewById(R.id.itemImage);
            itemPackType = (TextView) view.findViewById(R.id.itemPackType);
            itemMRP = (TextView) view.findViewById(R.id.txtViewMRP);
           /* itemQuantity = (TextView) view.findViewById(R.id.itemQuantity);

            btn_sub_quantity = (Button) view.findViewById(R.id.btn_sub_quantity);
            btn_add_quantity = (Button) view.findViewById(R.id.btn_add_quantity);
            btnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);

            btn_sub_quantity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (quantity_product > 1) {
                        quantity_product--;
                        str_quantity_product = Integer.toString(quantity_product);
                        itemQuantity.setText(str_quantity_product);
                    }

                }
            });
            btn_add_quantity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    quantity_product++;
                    str_quantity_product=Integer.toString(quantity_product);
                    itemQuantity.setText(str_quantity_product);

                }
            });*/



        }
    }


    public ProductAdapter(List<Product> productList) {

        Log.d("construtor #: ",""+ productList.size());
        this.productList = productList;
    }
    View v ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        v = itemView;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = productList.get(position);

        Log.d("here #: ",""+ productList.size());
        Log.d("product.getName() #: ",""+ product.getName());
        holder.itemName.setText(product.getName());
        holder.itemPackType.setText(product.getPackType());
        holder.itemMRP.setText("MRP. "+product.getMRP());
        Picasso.with(v.getContext()).load(product.getProductImgUrl()).into(holder.itemImage);
        /*holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String quantity =  holder.itemQuantity.getText().toString();

                Toast.makeText(v.getContext(),quantity,Toast.LENGTH_SHORT).show();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("businessId","UserId");
                params.put("businessName","USername");
                params.put("productId",product.get_id());
                params.put("name",product.getName());
                params.put("packType",product.getName());
                params.put("quantity",quantity);
                params.put("unitPrice",product.getName());
                params.put("MRP",product.getName());
                params.put("supplierId",product.getName());
                params.put("supplierName",product.getName());
               // addToCart(params);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


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
    }
}