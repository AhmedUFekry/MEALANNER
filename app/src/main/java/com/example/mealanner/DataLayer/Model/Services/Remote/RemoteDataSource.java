package com.example.mealanner.DataLayer.Model.Services.Remote;

public interface RemoteDataSource<T> {
    void makeNetworkCall(NetworkCallBack<T> networkInterface , int requestNumber);

}
