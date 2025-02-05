package com.example.recipemyown

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeAPI {
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("search.php")
    suspend fun getMeals(@Query("s") searchTerm: String): Response<MealResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(RecipeAPI::class.java)


