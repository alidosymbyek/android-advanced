package com.example.lab1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab1.R
import com.example.lab1.databinding.FragmentServiceBinding
import com.example.lab1.services.MusicService

class ServiceFragment : Fragment() {
    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener {
            startMusicService()
        }

        binding.buttonPause.setOnClickListener {
            pauseMusicService()
        }

        binding.buttonStop.setOnClickListener {
            stopMusicService()
        }
    }

    private fun startMusicService() {
        val intent = Intent(requireContext(), MusicService::class.java).apply {
            action = MusicService.ACTION_START
        }
        requireContext().startForegroundService(intent)
    }

    private fun pauseMusicService() {
        val intent = Intent(requireContext(), MusicService::class.java).apply {
            action = MusicService.ACTION_PAUSE
        }
        requireContext().startService(intent)
    }

    private fun stopMusicService() {
        val intent = Intent(requireContext(), MusicService::class.java).apply {
            action = MusicService.ACTION_STOP
        }
        requireContext().startService(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 