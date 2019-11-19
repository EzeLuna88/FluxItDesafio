package com.example.fluxitdesafio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.model.User;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends AppCompatActivity implements UserLocationFragment.NotifyActivity {

    @BindView(R.id.imageViewPictureUserDetailsActivity)
    ImageView imageViewPictureUserDetailsActivity;
    @BindView(R.id.textViewNameUserDetailsActivity)
    TextView textViewNameUserDetailsActivity;
    @BindView(R.id.textViewAgeUserDetailsActivity)
    TextView textViewAgeUserDetailsActivity;
    @BindView(R.id.textViewEmailUserDetailsActivity)
    TextView textViewEmailUserDetailsActivity;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String latitude;
    private String longitude;
    private User user;


    public static final String KEY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable(KEY_USER);
        Glide.with(this)
                .load(user.getPicture().getLarge())
                .into(imageViewPictureUserDetailsActivity);
        textViewNameUserDetailsActivity.setText(user.getName().getTitle() + " "
                + user.getName().getFirst() + " "
                + user.getName().getLast());
        textViewAgeUserDetailsActivity.setText(Integer.toString(user.getDob().getAge()));
        textViewEmailUserDetailsActivity.setText(user.getEmail());

        location();


    }

    private void location() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(UserDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(UserDetailsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                    Bundle args = new Bundle();
                    args.putSerializable(KEY_USER, user);
                    args.putString(UserLocationFragment.KEY_LATITUDE, latitude);
                    args.putString(UserLocationFragment.KEY_LONGITUDE, longitude);
                    UserLocationFragment userLocationFragment = new UserLocationFragment();
                    userLocationFragment.setArguments(args);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, userLocationFragment).commit();
                }
            }
        });
    }


    @Override
    public void locationInfo(User user) {

    }
}


