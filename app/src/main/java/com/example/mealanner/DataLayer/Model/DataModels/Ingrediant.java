package com.example.mealanner.DataLayer.Model.DataModels;

public class Ingrediant {
	private String strDescription;
	private String strIngredient;
	private Object strType;
	private String idIngredient;

	public String getStrDescription(){
		return strDescription;
	}

	public String getStrIngredient(){
		return strIngredient;
	}
	public void setStrIngredient(String ingredientName){
		this.strIngredient = ingredientName;
	}

	public Object getStrType(){
		return strType;
	}

	public String getIdIngredient(){
		return idIngredient;
	}
}
