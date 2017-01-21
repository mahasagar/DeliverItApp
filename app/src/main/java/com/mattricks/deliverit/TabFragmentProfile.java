package com.mattricks.deliverit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mattricks.deliverit.utilities.SharedPreference;
import com.roughike.bottombar.BottomBar;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabFragmentProfile extends Fragment implements LocationListener {

    public final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    BottomBar bottomBar;
    SharedPreference sharedPreference;
    private AdView mAdView;
    String User;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private TextView latitude, longitude;
    private double fusedLatitude = 0.0;
    private double fusedLongitude = 0.0;

    @Bind(R.id.btnLogout)
    Button btnLogout;
    @Bind(R.id.txt_username)
    TextView txt_username;

    public TabFragmentProfile() {

        sharedPreference = new SharedPreference();
    }

    public static TabFragmentProfile newInstance(String text, BottomBar bottomBar) {
        Bundle args = new Bundle();
        TabFragmentProfile sampleFragment = new TabFragmentProfile();
        sampleFragment.bottomBar = bottomBar;
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabfragmentprofile, container, false);
        ButterKnife.bind(this, rootView);
        initializeViews(rootView);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        try {
            User = sharedPreference.getUserName(getActivity());
        }catch(JSONException e){
            Log.e(getResources().getString(R.string.strTabProfile),e.toString());
        }

        txt_username.setText(User);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getResources().getString(R.string.strTestDevices))
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                //  Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //  Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //   Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);
        if (checkPlayServices()) {
            startFusedLocation();
            registerRequestUpdate(this);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sharedPreference.removeUser(v.getContext());
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } catch (NullPointerException e) {
                    Log.e("TagFragmentProfile", e.toString());
                }

            }
        });
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        return rootView;
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }


    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
            }
        }
    }

    private void initializeViews(View rootView) {
        latitude = (TextView) rootView.findViewById(R.id.textview_latitude);
        longitude = (TextView) rootView.findViewById(R.id.textview_longitude);
    }

    @Override
    public void onStop() {
        stopFusedLocation();
        super.onStop();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getContext().getApplicationContext(),
                        getResources().getString(R.string.strGoogleAPIMsg), Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getContext().getApplicationContext(),
                        getResources().getString(R.string.strDevicesNotSupport), Toast.LENGTH_LONG)
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void registerRequestUpdate(final LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }


    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult result) {
                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            setFusedLatitude(location.getLatitude());
            setFusedLongitude(location.getLongitude());
            latitude.setText(String.format("%s %s", getString(R.string.latitude_string), getFusedLatitude()));
            longitude.setText(String.format("%s %s", getString(R.string.longitude_string), getFusedLongitude()));
        } catch (NullPointerException e) {
            Log.e("", e.toString());
        }

    }

    public void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    public void setFusedLongitude(double lon) {
        fusedLongitude = lon;
    }

    public double getFusedLatitude() {
        return fusedLatitude;
    }

    public double getFusedLongitude() {
        return fusedLongitude;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (checkPlayServices()) {
            startFusedLocation();
            registerRequestUpdate(this);
        }
        if (mAdView != null) {
            mAdView.resume();
        }
    }
}