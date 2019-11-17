package com.example.fluxitdesafio.service;

import com.example.fluxitdesafio.model.ResultUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomService {

    @GET("?results=20&seed")
    Call<ResultUser> getResults(
            @Query("seed") String seed,
            @Query("page") Integer page
    );
}
