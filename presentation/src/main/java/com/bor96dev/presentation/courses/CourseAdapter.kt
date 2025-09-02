package com.bor96dev.presentation.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bor96dev.domain.Course
import com.bor96dev.presentation.R
import com.bor96dev.presentation.databinding.ItemCourseBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CourseAdapter(
    private val onFavoriteClick: (course: Course) -> Unit
): ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    private val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    private val coverDrawables = listOf(
        R.drawable.cover1,
        R.drawable.cover2,
        R.drawable.cover3
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private fun formatDate(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString) ?: return dateString
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    private fun getCoverDrawableId(position: Int): Int {
        return coverDrawables[position % coverDrawables.size]
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course, position: Int) = with (binding) {
            tvTitle.text = course.title
            tvDescription.text = course.text
            tvPrice.text = "${course.price} ₽"
            ratingBar.text = course.rate.toFloat().toString()
            tvDate.text = formatDate(course.publishDate)

            val coverDrawable = getCoverDrawableId(position)
            ivCourseImage.setImageResource(coverDrawable)

            val favoriteIcon = if (course.hasLike) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
            favoriteChecker.setImageResource(favoriteIcon)

            ivFavorite.setOnClickListener {
                onFavoriteClick(course)
            }
        }
    }

    class CourseDiffCallback: DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}