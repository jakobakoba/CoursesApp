package com.bor96dev.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bor96dev.presentation.R
import com.bor96dev.presentation.courses.CourseAdapter
import com.bor96dev.presentation.courses.CoursesUiState
import com.bor96dev.presentation.courses.CoursesViewModel
import com.bor96dev.presentation.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoursesViewModel by activityViewModels()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = CourseAdapter { course ->
            viewModel.toggleFavorite(course)
        }

        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state is CoursesUiState.Success) {
                    val favoriteCourses = state.courses.filter { it.hasLike }
                    adapter.submitList(favoriteCourses)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}