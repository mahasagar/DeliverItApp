package com.mattricks.deliverit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.mattricks.deliverit.utilities.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mahasagar on 23/12/16.
 */

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.et_email) EditText et_email;
    @Bind(R.id.et_password) EditText et_password;
    @Bind(R.id.tv_signupLink) TextView tv_signupLink;
    @Bind(R.id.btnSignIn) Button btnSignIn;

    RequestQueue requestQueue;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        sharedPreference = new SharedPreference();
    }

    @OnClick(R.id.tv_signupLink)
    public void goToSignup(View view) {
       Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    @OnClick(R.id.btnSignIn)
    public void doLogin(final View view) {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnSignIn.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        getLogin(email, password, view);
                        btnSignIn.setEnabled(true);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnSignIn.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_password.setError(null);
        }
        return valid;
    }

    private void getLogin( final String username, final String password, final View view) {
        final String URL =Constants.APP_URL + Constants.API_LOGIN;

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
                            sharedPreference.removeUser(view.getContext());
                            sharedPreference.addUser(view.getContext(), JsonResultData);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
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
                          Log.d("Error.Response #", error.toString());
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
