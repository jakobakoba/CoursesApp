package com.bor96dev.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.domain.Course
import com.bor96dev.domain.usecase.GetCoursesUseCase
import com.bor96dev.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val uiState: StateFlow<CoursesUiState> = _uiState

    private var allCourses: List<Course> = emptyList()
    private var isSortedByDate = false

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading
            try {
                getCoursesUseCase().collect { courses ->
                    allCourses = courses
                    updateUiWithCurrentSort()
                }
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Error loading courses")
            }
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(course.id, !course.hasLike)
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error("Failed to update favorite status")
            }
        }
    }

    fun toggleSort() {
        isSortedByDate = !isSortedByDate
        updateUiWithCurrentSort()
    }

    private fun updateUiWithCurrentSort() {
        val sortedCourses = if (isSortedByDate) {
            allCourses.sortedByDescending { it.publishDate }
        } else {
            allCourses.sortedBy { it.id }
        }
        _uiState.value = CoursesUiState.Success(sortedCourses)
    }
}

sealed class CoursesUiState {
    object Loading : CoursesUiState()
    data class Success(val courses: List<Course>) : CoursesUiState()
    data class Error(val message: String) : CoursesUiState()
}