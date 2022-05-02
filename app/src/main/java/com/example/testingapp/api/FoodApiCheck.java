package com.example.testingapp.api;

import com.example.testingapp.models.ExchangeRate;
import com.example.testingapp.models.Order;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApiCheck {

    @GET("darkalor/8b916a24ee746c432165ecefeeb5831a/raw/992f1ca2db87c83d58af8cb8d105dd88790a3195/euroPrice.json")
    Call<ExchangeRate> getExchangeRate();

    @GET("r-casarez-garcia-charter/57260b09dcbf415cef0f4fbe91ab468b/raw/7b27a6846259c6bd2b59d691f6e43c195e4e621d/order.json")
    Call<Order> getOrder();
}
