package com.example.fluxitdesafio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.controller.UserController;
import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.model.User;
import com.example.fluxitdesafio.utils.ResultListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = findViewById(R.id.recyclerViewMainActivity);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        UserController userController = new UserController();
        userController.getUsers(new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {
                userList = result.getResults();
                UserAdapter userAdapter = new UserAdapter(userList);
                recyclerView.setAdapter(userAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
            }
        });


    }


}

