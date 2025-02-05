package com.example.recipemyown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {
    private var _mealState = MutableStateFlow(MealState())
    val mealState: StateFlow<MealState> = _mealState.asStateFlow()

    fun fetchMealData(searchString: String) {
        viewModelScope.launch {
            try {
                _mealState.update{
                    it.copy(
                        loading = true,
                        hasSearched = true
                    )
                }
                val response = recipeService.getMeals(searchString)
                if (response.isSuccessful) {
                    val mealList = response.body()?.meals
                    _mealState.update {
                        it.copy(
                            loading = false,
                            relatedMeals = mealList ?: emptyList()
                        )
                    }
                }

            } catch (e : Exception) {
                _mealState.update {
                    it.copy(
                        loading = false,
                        error = "Error in Loading Data $e"
                    )
                }
            }
        }
    }
}