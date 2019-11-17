package com.example.fluxitdesafio.view;

import androidx.annotation.NonNull;
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
    private RecyclerView recyclerView;
    UserController userController;
    private String seed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMainActivity);

        progressBar = findViewById(R.id.progressBarMainActivity);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayourMainActivity);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        userController = new UserController();

        getUsers();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer position = layoutManager.findLastVisibleItemPosition();
                Integer lastCell = layoutManager.getItemCount();

                if (position.equals(lastCell - 4)) {
                    getUsers();
                }
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

    public void getUsers() {
        userController.getUsers(new ResultListener<ResultUser>() {

            @Override
            public void onFinish(ResultUser result) {
                userList = result.getResults();
                seed = result.getInfo().getSeed();
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
        }, seed);
    }
}










