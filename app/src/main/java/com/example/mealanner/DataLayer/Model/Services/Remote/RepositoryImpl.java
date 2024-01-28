package com.example.mealanner.DataLayer.Model.Services.Remote;

public class RepositoryImpl<T> implements Repository<T> {

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

    public void getDataFromAPI(NetworkCallBack<T> networkCallBack ,int requestNumber) {
        remoteDataSource.makeNetworkCall(networkCallBack , requestNumber);
    }
}