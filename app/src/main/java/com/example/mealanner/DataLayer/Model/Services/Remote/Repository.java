package com.example.mealanner.DataLayer.Model.Services.Remote;

public interface Repository<T> {
    public void getDataFromAPI(NetworkCallBack<T> networkCallBack ,int requestNumber);

    }
