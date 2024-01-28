package com.example.mealanner.DataLayer.Model.Services.Remote;

public interface NetworkCallBack<T> {
    void onSuccess(T result);
    void onFailure(String errorMsg);
}
