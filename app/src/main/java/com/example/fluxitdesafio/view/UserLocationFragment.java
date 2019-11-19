package com.example.fluxitdesafio.view;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.model.User;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLocationFragment extends Fragment {


    public static final String KEY_USER = "user";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    private NotifyActivity notifyActivity;
    @BindView(R.id.textViewCityFragment)
    TextView textViewCityFragment;
    @BindView(R.id.textViewStreetFragment)
    TextView textViewStreetFragment;
    @BindView(R.id.textViewStateFragment)
    TextView textViewStateFragment;
    @BindView(R.id.textViewCountryFragment)
    TextView textViewCountryFragment;
    @BindView(R.id.textViewCoordinatesFragment)
    TextView textViewCoordinatesFragment;
    @BindView(R.id.textViewMyCoordinatesFragment)
    TextView textViewMyCoordinatesFragment;

    private FusedLocationProviderClient fusedLocationProviderClient;




    public UserLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.notifyActivity = (NotifyActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_location, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable(KEY_USER);
        String latitude = bundle.getString(KEY_LATITUDE);
        String longitude = bundle.getString(KEY_LONGITUDE);

        setInfo(user, latitude, longitude);

        return view;
    }

    private void setInfo(User user, String latitude, String longitude) {
        String city = user.getLocation().getCity();
        String street = Integer.toString(user.getLocation().getStreet().getNumber()) + " " + user.getLocation().getStreet().getName();
        String state = user.getLocation().getState();
        String country = user.getLocation().getCountry();
        String coordinates = user.getLocation().getCoordinates().getLatitude() + " - " + user.getLocation().getCoordinates().getLongitude();
        textViewCityFragment.setText(city);
        textViewStreetFragment.setText(street);
        textViewStateFragment.setText(state);
        textViewCountryFragment.setText(country);
        textViewCoordinatesFragment.setText(coordinates);
        textViewMyCoordinatesFragment.setText(latitude + " " + longitude);
    }

    public interface NotifyActivity {
        public void locationInfo(User user);
    }


}
