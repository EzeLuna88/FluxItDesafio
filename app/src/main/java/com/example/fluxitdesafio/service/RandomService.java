package com.example.fluxitdesafio.service;

import com.example.fluxitdesafio.model.ResultUser;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomService {

    @GET("?results=20")
    Call<ResultUser> getResults();
}
