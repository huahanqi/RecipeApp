package com.example.recipemyown

import android.net.Uri
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Details : Screen("details/{category}") {
        fun createRoute(category: Category): String {
            val categoryJson = Uri.encode(Gson().toJson(category))
            return "details/$categoryJson"
        }
    }
    object Search : Screen("search")
    object SearchDetails: Screen("searchDetails/{meal}") {
        fun createRoute(meal: Meal): String {
            val mealJson = Uri.encode(Gson().toJson(meal))
            return "searchDetails/$mealJson"
        }
    }
}