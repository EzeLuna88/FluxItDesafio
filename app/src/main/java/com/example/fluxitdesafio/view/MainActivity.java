package com.example.fluxitdesafio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.controller.UserController;
import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.model.User;
import com.example.fluxitdesafio.utils.ResultListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserAdapterListener {


    List<User> userList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarMainActivity);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayourMainActivity);

        final RecyclerView recyclerView = findViewById(R.id.recyclerViewMainActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        UserController userController = new UserController();
        userController.getUsers(new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {
                userList = result.getResults();
                if (userList != null) {
                    Toast.makeText(MainActivity.this, "Pedido exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Pedido fallido", Toast.LENGTH_SHORT).show();
                }
                UserAdapter userAdapter = new UserAdapter(userList, MainActivity.this);
                recyclerView.setAdapter(userAdapter);


                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UserController userController1 = new UserController();
                userController1.getUsers(new ResultListener<ResultUser>() {
                    @Override
                    public void onFinish(ResultUser result) {
                        userList = result.getResults();
                        if (userList != null) {
                            Toast.makeText(MainActivity.this, "Pedido exitoso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Pedido fallido", Toast.LENGTH_SHORT).show();
                        }
                        UserAdapter userAdapter = new UserAdapter(userList, MainActivity.this);
                        recyclerView.setAdapter(userAdapter);


                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemViewCacheSize(20);
                        progressBar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

    }

    @Override
    public void listenerSelectionUser(Integer position, User user) {
        Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(UserDetailsActivity.KEY_POSITION, position);
        bundle.putSerializable(UserDetailsActivity.KEY_USER, user);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}

