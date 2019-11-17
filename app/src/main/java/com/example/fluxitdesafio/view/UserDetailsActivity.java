package com.example.fluxitdesafio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.imageViewPictureUserDetailsActivity)
    ImageView imageViewPictureUserDetailsActivity;
    @BindView(R.id.textViewNameUserDetailsActivity)
    TextView textViewNameUserDetailsActivity;
    @BindView(R.id.textViewAgeUserDetailsActivity)
    TextView textViewAgeUserDetailsActivity;
    @BindView(R.id.textViewEmailUserDetailsActivity)
    TextView textViewEmailUserDetailsActivity;
    private String name;

    public static final String KEY_POSITION = "position";
    public static final String KEY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Integer position = bundle.getInt(KEY_POSITION);
        User user = (User) bundle.getSerializable(KEY_USER);
        Glide.with(this)
                .load(user.getPicture().getLarge())
                .into(imageViewPictureUserDetailsActivity);
        textViewNameUserDetailsActivity.setText(user.getName().getTitle() + " "
                + user.getName().getFirst() + " "
                + user.getName().getLast());
        textViewAgeUserDetailsActivity.setText(Integer.toString(user.getDob().getAge()));
        textViewEmailUserDetailsActivity.setText(user.getEmail());


    }


}


