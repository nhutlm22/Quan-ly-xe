package com.example.assignment_androidnetwork;

import com.example.assignment_androidnetwork.Model.ServerRequest;
import com.example.assignment_androidnetwork.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("learn-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
