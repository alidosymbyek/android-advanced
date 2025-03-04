package com.example.lab1

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentSystemEventsBinding

class SystemEventsFragment : Fragment() {
    private var _binding: FragmentSystemEventsBinding? = null
    private val binding get() = _binding!!
    private val airplaneModeReceiver = AirplaneModeReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnRegisterReceiver.setOnClickListener {
            registerAirplaneModeReceiver()
        }

        binding.btnUnregisterReceiver.setOnClickListener {
            unregisterAirplaneModeReceiver()
        }
    }

    private fun registerAirplaneModeReceiver() {
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireContext().registerReceiver(airplaneModeReceiver, filter)
        binding.btnRegisterReceiver.isEnabled = false
        binding.btnUnregisterReceiver.isEnabled = true
    }

    private fun unregisterAirplaneModeReceiver() {
        requireContext().unregisterReceiver(airplaneModeReceiver)
        binding.btnRegisterReceiver.isEnabled = true
        binding.btnUnregisterReceiver.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            requireContext().unregisterReceiver(airplaneModeReceiver)
        } catch (e: IllegalArgumentException) {
            // Receiver was not registered
        }
        _binding = null
    }
} 