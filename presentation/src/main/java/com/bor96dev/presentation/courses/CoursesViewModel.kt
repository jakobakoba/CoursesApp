package com.bor96dev.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.domain.Course
import com.bor96dev.domain.usecase.GetCoursesUseCase
import com.bor96dev.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

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
            try {
                getCoursesUseCase().collectLatest { courses ->
                    allCourses = if (isSortedByDate) {
                        courses.sortedByDescending { it.publishDate }
                    } else {
                        courses
                    }
                    _uiState.value = CoursesUiState.Success(allCourses)
                }
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(courseId)
        }
    }

    fun toggleSort() {
        isSortedByDate = !isSortedByDate
        allCourses = if (isSortedByDate) {
            allCourses.sortedByDescending { it.publishDate }
        } else {
            allCourses.sortedBy { it.id }
        }
        _uiState.value = CoursesUiState.Success(allCourses)
    }
}

sealed class CoursesUiState {
    object Loading : CoursesUiState()
    data class Success(val courses: List<Course>) : CoursesUiState()
    data class Error(val message: String) : CoursesUiState()
}