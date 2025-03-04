package com.example.lab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.ItemCalendarEventBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter : ListAdapter<CalendarEvent, CalendarAdapter.CalendarViewHolder>(CalendarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CalendarViewHolder(
        private val binding: ItemCalendarEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(event: CalendarEvent) {
            binding.apply {
                titleText.text = event.title
                descriptionText.text = event.description ?: "No description"
                timeText.text = "${dateFormat.format(Date(event.startTime))} - ${dateFormat.format(Date(event.endTime))}"
            }
        }
    }

    private class CalendarDiffCallback : DiffUtil.ItemCallback<CalendarEvent>() {
        override fun areItemsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent): Boolean {
            return oldItem.title == newItem.title && oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent): Boolean {
            return oldItem == newItem
        }
    }
} 