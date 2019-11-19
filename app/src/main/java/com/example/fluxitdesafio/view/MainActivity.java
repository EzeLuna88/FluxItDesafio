package com.example.fluxitdesafio.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.controller.UserController;
import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.model.User;
import com.example.fluxitdesafio.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserAdapterListener {


    List<User> userList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    UserController userController;
    private String seed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMainActivity);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayourMainActivity);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        userController = new UserController();

        getUsers();

        swipe(layoutManager);
    }

    private void swipe(final LinearLayoutManager layoutManager) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsersSwipe();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer position = layoutManager.findLastVisibleItemPosition();
                Integer lastCell = layoutManager.getItemCount();

                if (position.equals(lastCell - 5)) {
                    userController.getUsers(new ResultListener<ResultUser>() {
                        @Override
                        public void onFinish(ResultUser result) {
                            getUsers();
                        }
                    }, seed);
                }
            }
        });
    }

    @Override
    public void listenerSelectionUser(Integer position, User user) {
        Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(UserDetailsActivity.KEY_USER, user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getUsers() {
        final ProgressBar progressBar = findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
        userController.getUsers(new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {
                if (userList == null) {
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
                } else {
                    userList.addAll(result.getResults());
                }
                progressBar.setVisibility(View.GONE);

            }
        }, seed);
    }

    public void getUsersSwipe() {
        final ProgressBar progressBar = findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.GONE);
            }
        }, seed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.itemToolbarSearchView).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.itemToolbarSearchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });
        return true;
    }

    public void search(String name) {
        List<User> users = new ArrayList<>();
        for (User user : userList) {
            String firstName = user.getName().getFirst().toLowerCase();
            String lastName = user.getName().getLast().toLowerCase();
            if (firstName.contains(name) || lastName.contains(name)) {
                users.add(user);
            }
            UserAdapter userAdapter = new UserAdapter(users, MainActivity.this);
            recyclerView.setAdapter(userAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
        }
    }


}










