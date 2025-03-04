package com.example.lab1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentInstagramShareBinding

class InstagramShareFragment : Fragment() {
    private var _binding: FragmentInstagramShareBinding? = null
    private val binding get() = _binding!!
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { shareToInstagram(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstagramShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun shareToInstagram(imageUri: Uri) {
        try {
            val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
                data = imageUri
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra("interactive_asset_uri", imageUri)
                putExtra("background_color", "#FFFFFF")
                putExtra("attribution_link", "https://example.com")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Instagram app not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 