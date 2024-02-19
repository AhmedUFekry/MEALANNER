package com.example.mealanner.DataLayer.Model.DataModels;

import java.util.List;

public class Ingrediants {
	private List<Ingrediant> meals;

	public List<Ingrediant> getMeals(){
		return meals;
	}
	public void setMeals(List<Ingrediant> newMeals){
		this.meals = newMeals;
	}

}