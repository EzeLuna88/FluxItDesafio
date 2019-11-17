package com.example.fluxitdesafio.controller;

import com.example.fluxitdesafio.dao.RandomDao;
import com.example.fluxitdesafio.model.ResultUser;
import com.example.fluxitdesafio.utils.ResultListener;

public class UserController {

    private RandomDao randomDao;

    public UserController() {
        this.randomDao = new RandomDao();
    }

    public void getUsers(final ResultListener<ResultUser> viewListener) {
        randomDao.getUsers(new ResultListener<ResultUser>() {
            @Override
            public void onFinish(ResultUser result) {
                viewListener.onFinish(result);
            }
        });
    }
}
