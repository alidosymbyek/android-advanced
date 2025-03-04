package com.example.lab1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.ItemCalendarEventBinding
import com.example.lab1.models.CalendarEvent
import java.text.SimpleDateFormat
import java.util.*

class CalendarEventAdapter : ListAdapter<CalendarEvent, CalendarEventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemCalendarEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventViewHolder(
        private val binding: ItemCalendarEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(event: CalendarEvent) {
            binding.titleTextView.text = event.title
            binding.descriptionTextView.text = event.description
            binding.timeTextView.text = "${dateFormat.format(Date(event.startTime))} - ${dateFormat.format(Date(event.endTime))}"
        }
    }

    private class EventDiffCallback : DiffUtil.ItemCallback<CalendarEvent>() {
        override fun areItemsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent): Boolean {
            return oldItem.title == newItem.title && oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent): Boolean {
            return oldItem == newItem
        }
    }
} 