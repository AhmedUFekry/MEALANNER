package com.example.mealanner.DataLayer.Model.Services.Remote;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;

public class RemoteDataSourceImpl<T> implements RemoteDataSource<T> {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final Api_service apiService;
    private static final String TAG = "NetworkService";
    private static RemoteDataSourceImpl<?> instance = null;

    private RemoteDataSourceImpl(Class<T> responseType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(Api_service.class);
    }

    public static <T> RemoteDataSourceImpl<T> getInstance(Class<T> responseType) {
        if (instance == null) {
            instance = new RemoteDataSourceImpl<>(responseType);
        }
        return (RemoteDataSourceImpl<T>) instance;
    }

    @Override
    public void makeNetworkCall(NetworkCallBack<T> networkInterface, int requestNumber, String... filter) {
        Observable<T> observable;

        switch (requestNumber) {
            case 1:
                observable = (Observable<T>) apiService.getAllCategories();
                break;
            case 2:
                observable = (Observable<T>) apiService.getAllCountries();
                break;
            case 3:
                observable = (Observable<T>) apiService.getAllIngredients();
                break;
            case 4:
                observable = (Observable<T>) apiService.filterMealsByIngredientID(filter[0]);
                break;
            case 5:
                observable = (Observable<T>) apiService.filterMealsByCountryID(filter[0]);
                break;
            case 6:
                observable = (Observable<T>) apiService.filterMealsByCategoryID(filter[0]);
                break;
            case 7:
                observable = (Observable<T>) apiService.getRandomMeal();
                break;
            case 8:
                observable = (Observable<T>) apiService.getMealByID(filter[0]);
                break;
            case 9:
                observable = (Observable<T>) apiService.searchByMealName(filter[0]);
                break;
            default:
                observable = (Observable<T>) apiService.getAllCountries();
                break;
        }

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkInterface.onSuccess(response),
                        throwable ->  {throwable.printStackTrace(); networkInterface.onFailure(throwable.getMessage());}
                );
    }
}