package com.example.fluxitdesafio.controller;

import com.example.fluxitdesafio.dao.RandomDao;
import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.utils.ResultListener;

public class UserController {

    private Integer page = 0;
    private RandomDao randomDao;


    public UserController() {
        this.randomDao = new RandomDao();
    }

    public void getUsers(final ResultListener<ResultUser> viewListener, String seed) {
        randomDao.getUsers(seed, new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {

                page = page + 1;
                viewListener.onFinish(result);
            }
        }, page);
    }

    public void getUsersSearch(final ResultListener<ResultUser> viewListener, String seed, String name) {
        randomDao.getUsersSearch(seed, name, new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {
                viewListener.onFinish(result);
            }
        });
    }
}
