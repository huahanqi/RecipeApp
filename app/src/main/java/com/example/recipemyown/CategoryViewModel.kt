package com.example.recipemyown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private var _recipeState = MutableStateFlow(RecipeState())
    val recipeState: StateFlow<RecipeState> = _recipeState.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                if (response.isSuccessful) {
                    val categoryList = response.body()?.categories
                    _recipeState.update {
                        it.copy(
                            loading = false,
                            categories = categoryList ?: emptyList()
                        )
                    }
                }

            } catch (e : Exception) {
                _recipeState.update {
                    it.copy(
                        loading = false,
                        categories = emptyList(),
                        error = "Error in Loading Data $e"
                    )
                }
            }
        }

    }

}