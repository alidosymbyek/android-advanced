package com.example.lab1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val calendarAdapter = CalendarAdapter()
    private val PERMISSION_REQUEST_CODE = 123

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = calendarAdapter
        }
    }

    private fun setupButtons() {
        binding.btnFetchEvents.setOnClickListener {
            checkPermissionAndFetchEvents()
        }
    }

    private fun checkPermissionAndFetchEvents() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_CALENDAR),
                PERMISSION_REQUEST_CODE
            )
        } else {
            fetchCalendarEvents()
        }
    }

    private fun fetchCalendarEvents() {
        val events = mutableListOf<CalendarEvent>()
        val calendar = Calendar.getInstance()
        val startTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, 7) // Fetch events for next 7 days
        val endTime = calendar.timeInMillis

        val projection = arrayOf(
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
        )

        val selection = "${CalendarContract.Events.DTSTART} >= ? AND ${CalendarContract.Events.DTSTART} <= ?"
        val selectionArgs = arrayOf(startTime.toString(), endTime.toString())

        val cursor = requireContext().contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            "${CalendarContract.Events.DTSTART} ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(0)
                val description = it.getString(1)
                val start = it.getLong(2)
                val end = it.getLong(3)

                events.add(CalendarEvent(title, description, start, end))
            }
        }

        calendarAdapter.submitList(events)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchCalendarEvents()
                } else {
                    Toast.makeText(context, "Calendar permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 