package com.example.mealanner.DataLayer.Model.Services.Remote;
import com.example.mealanner.DataLayer.Model.DataModels.Categories;
import com.example.mealanner.DataLayer.Model.DataModels.Countries;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediants;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api_service {
    @GET("categories.php")
    Observable<Categories> getAllCategories();

    @GET("list.php?a=list")
    Observable<Countries> getAllCountries();

    @GET("list.php?i=list")
    Observable<Ingrediants> getAllIngredients();

    @GET("search.php?")
    Observable<Meals> searchByMealName(@Query("s") String mealName);

    @GET("filter.php?")
    Observable<Meals> filterMealsByIngredientID(@Query("i") String ingredientId);

    @GET("filter.php?")
    Observable<Meals> filterMealsByCountryID(@Query("a") String countryId);

    @GET("filter.php?")
    Observable<Meals> filterMealsByCategoryID(@Query("c") String categoryId);

    @GET("lookup.php?")
    Observable<Meals> getMealByID(@Query("i") String mealId);

    @GET("random.php")
    Observable<Meals> getRandomMeal();
}
