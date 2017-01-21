package com.mattricks.deliverit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignupActivity extends AppCompatActivity {

    public static String TAG="SignupActivity";
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_address)
    EditText et_address;
    @Bind(R.id.et_email)
    EditText et_email;
    @Bind(R.id.et_mobile)
    EditText et_mobile;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_reEnterPassword)
    EditText et_reEnterPassword;

    @Bind(R.id.btnSignup)
    Button btnSignup;

    RequestQueue requestQueue;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        requestQueue = VolleySingleton.getInstance().getREquestQueue();
        sharedPreference = new SharedPreference();
    }

    @OnClick(R.id.tv_loginLink)
    public void goToSignup(View view) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.btnSignup)
    public void doSignup(final View view) {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.strProgressBar));
        progressDialog.show();

        final String name = et_name.getText().toString();
        final String address = et_address.getText().toString();
        final String email = et_email.getText().toString();
        final String mobile = et_mobile.getText().toString();
        final String password = et_password.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        getSignup(name, address, email, mobile, password, view);
                        btnSignup.setEnabled(true);
                    }
                }, 3000);
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.strSignUpFailed), Toast.LENGTH_LONG).show();
        btnSignup.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String email = et_email.getText().toString();
        String mobile = et_mobile.getText().toString();
        String password = et_password.getText().toString();
        String reEnterPassword = et_reEnterPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            et_name.setError(getResources().getString(R.string.strNameLimitMsg));
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (address.isEmpty()) {
            et_address.setError(getResources().getString(R.string.strEnterValidAddress));
            valid = false;
        } else {
            et_address.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError(getResources().getString(R.string.strValidEmail));
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            et_mobile.setError(getResources().getString(R.string.strEnterValidMobileNumber));
            valid = false;
        } else {
            et_mobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError(getResources().getString(R.string.strPasswordLimit));
            valid = false;
        } else {
            et_password.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            et_reEnterPassword.setError(getResources().getString(R.string.strPasswordMsg));
            valid = false;
        } else {
            et_reEnterPassword.setError(null);
        }

        return valid;
    }


    private void getSignup(final String name, final String address, final String email, final String mobile, final String password, final View view) {
        final String URL = Constants.APP_URL + Constants.API_SIGNUP;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JsonResult = new JSONObject(response);
                            boolean status = JsonResult.getBoolean("status");
                            if (status) {
                                JSONObject JsonResultData = JsonResult.getJSONObject("result");
                                sharedPreference.removeUser(view.getContext());
                                sharedPreference.addUser(view.getContext(), JsonResultData);
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                String result = JsonResult.getString("result");
                                Toast.makeText(view.getContext(), result, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name.trim());
                params.put("address", address.trim());
                params.put("username", email.trim());
                params.put("mobile", mobile.trim());
                params.put("password", password.trim());
                return params;
            }
        };

        requestQueue.add(postRequest);
    }

}
