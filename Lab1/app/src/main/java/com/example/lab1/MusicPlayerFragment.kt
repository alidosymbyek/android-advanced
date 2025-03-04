package com.example.lab1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentMusicPlayerBinding

class MusicPlayerFragment : Fragment() {
    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding get() = _binding!!
    private var musicService: MusicService? = null
    private var bound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            bound = true
            updateButtonStates()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            updateButtonStates()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnStartService.setOnClickListener {
            startMusicService()
        }

        binding.btnPlay.setOnClickListener {
            musicService?.play()
        }

        binding.btnPause.setOnClickListener {
            musicService?.pause()
        }

        binding.btnStop.setOnClickListener {
            musicService?.stop()
        }

        binding.btnStopService.setOnClickListener {
            stopMusicService()
        }
    }

    private fun startMusicService() {
        val intent = Intent(requireContext(), MusicService::class.java)
        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        requireContext().startService(intent)
    }

    private fun stopMusicService() {
        if (bound) {
            requireContext().unbindService(serviceConnection)
            bound = false
        }
        requireContext().stopService(Intent(requireContext(), MusicService::class.java))
        updateButtonStates()
    }

    private fun updateButtonStates() {
        binding.btnPlay.isEnabled = bound
        binding.btnPause.isEnabled = bound
        binding.btnStop.isEnabled = bound
        binding.btnStopService.isEnabled = bound
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (bound) {
            requireContext().unbindService(serviceConnection)
            bound = false
        }
        _binding = null
    }
} 