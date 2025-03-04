package com.example.lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import com.example.lab1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonIntent.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_intent)
        }

        binding.buttonService.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_service)
        }

        binding.buttonCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_calendar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 