package com.bor96dev.presentation.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bor96dev.domain.Course
import com.bor96dev.presentation.R
import java.text.SimpleDateFormat
import java.util.Locale

class CourseAdapter(
    private val onFavoriteClick: (courseId: Int, isFavorite: Boolean) -> Unit
): ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CourseViewHolder(
        private val binding: ItemCourseBinding,
        private val onFavoriteClick: (courseId: Int, isFavorite: Boolean) -> Unit
    ): RecyclerView.ViewHolder(binding.root){
        private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))

        fun bind(course: Course) = with (binding) {
            tvTitle.text = course.title
            tvDescription.text = course.text
            tvPrice.text = root.context.getString(R.string.price_format, course.price)
            ratingBar.rating = course.rate.toFloat()
            tvDate.text = dateFormat.format(course.publishDate)

            val favoriteIcon = if(course.hasLike){
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
            ivFavorite.setImageResource(favoriteIcon)

            ivFavorite.setOnClickListener{
                onFavoriteClick(course.id, !course.hasLike)
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