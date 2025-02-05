package com.example.recipemyown

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson

@Composable
fun RecipeAppView(modifier: Modifier = Modifier) {
    val categoryViewModel: CategoryViewModel = viewModel()
    val viewState by categoryViewModel.recipeState.collectAsState()

    val navController = rememberNavController()
    val gson = Gson()
    val onClickFirstMenuItem = {navController.navigate(Screen.Categories.route)}
    val onClickSecondMenuItem = {navController.navigate(Screen.Search.route)}

    NavHost(navController = navController, startDestination = Screen.Categories.route) {
        composable(Screen.Categories.route) {
            AllCategoriesViewWithTopBar(
                viewState,
                onNavigateToDetails = { category ->
                    navController.navigate(Screen.Details.createRoute(category))},
                onClickFirstMenuItem = onClickFirstMenuItem,
                onClickSecondMenuItem = onClickSecondMenuItem
            )
        }
        composable(
            Screen.Details.route,
            arguments = listOf(navArgument("category") {type = NavType.StringType})
            ) { backStackEntry ->
                val categoryJson = backStackEntry.arguments?.getString("category")
//                val category = categoryJson?.let { gson.fromJson(it, Category::class.java) }
                val category = categoryJson?.let {
                    Uri.decode(it).let { decodedJson ->
                        gson.fromJson(decodedJson, Category::class.java)
                    }
                }
                category?.let { CategoryDetails(it, navController) }
        }
        composable(Screen.Search.route){
            SearchFoodWithTopBar(
                onClickFirstMenuItem = onClickFirstMenuItem,
                onClickSecondMenuItem = onClickSecondMenuItem
            )
        }
    }
}



