package com.example.mealanner.DataLayer.Model.Services.Remote;

import android.util.Log;

public class RepositoryImpl<T> implements Repository<T> {
    //network Calls IDs
    public static final int CATEGORIES = 1 ;
    public static final int COUNTRIES = 2 ;
    public static final int INGREDIENTS = 3;
    public static final int MEALSBYINGREDIENTSID = 4;
    public static final int MEALSBYCountryID = 5;
    public static final int MEALSBYCategoryID = 6;
    public static final int RANDOMMEAL = 7;
    public static final int MealID = 8;

    //////////////////
    private final RemoteDataSource<T> remoteDataSource;
    private static RepositoryImpl<?> repo = null;

    private RepositoryImpl(RemoteDataSource<T> remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static <T> RepositoryImpl<T> getInstance(RemoteDataSource<T> remoteDataSource) {
        if (repo == null)
            repo = new RepositoryImpl<>(remoteDataSource);
        return (RepositoryImpl<T>) repo;
    }

    public void getDataFromAPI(NetworkCallBack<T> networkCallBack ,int requestNumber , String... filter) {
        if(filter.length != 0) {
            remoteDataSource.makeNetworkCall(networkCallBack, requestNumber, filter[0]);
        }else {
            remoteDataSource.makeNetworkCall(networkCallBack, requestNumber);

        }
    }
}