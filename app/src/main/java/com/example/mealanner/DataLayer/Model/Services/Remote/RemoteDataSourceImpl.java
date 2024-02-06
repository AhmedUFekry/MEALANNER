package com.example.mealanner.DataLayer.Model.Services.Remote;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl<T> implements RemoteDataSource<T> {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final Api_service apiService;
    private static final String TAG = "NetworkService";
    private static RemoteDataSourceImpl<?> instance = null;




    private RemoteDataSourceImpl(Class<T> responseType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
    public void makeNetworkCall(NetworkCallBack<T> networkInterface, int requestNumber , String... filter) {
        Call<T> call;
        switch(requestNumber){
            case 1 :
                call = (Call<T>) apiService.getAllCategories();
                break;
            case 2 :
                call = (Call<T>) apiService.getAllCountries();
                break;
            case 3 :
                call = (Call<T>) apiService.getAllIngrediants();
                break;
            case 4 :
                call = (Call<T>) apiService.filterMealsByIngredientID(filter[0]);
                break;
            case 5 :
                call = (Call<T>) apiService.filterMealsByCountryID(filter[0]);
                break;
            case 6 :
                call = (Call<T>) apiService.filterMealsByCategoryID(filter[0]);
                break;
            case  7:
                call = (Call<T>) apiService.getRandomMeal();
                break;
            case  8:
                call = (Call<T>) apiService.getMealByID(filter[0]);
                break;
            default:
                call = (Call<T>) apiService.getAllCountries();
        }
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                networkInterface.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                networkInterface.onFailure(t.getMessage());
            }
        });
    }
}
