package com.example.fluxitdesafio.dao;

import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.service.RandomService;
import com.example.fluxitdesafio.utils.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomDao {

    public static final String BASE_URL = "https://randomuser.me/api/";
    private Retrofit retrofit;
    private RandomService randomService;

    public RandomDao() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        randomService = retrofit.create(RandomService.class);
    }


    public void getUsers(final ResultListener<ResultUser> controllerListener) {
        Call<ResultUser> call = randomService.getResults();

        call.enqueue(new Callback<ResultUser>() {
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser resultUser = response.body();
                controllerListener.onFinish(resultUser);
            }

            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
