package com.example.lab1.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lab1.MainActivity
import com.example.lab1.R

class MusicService : android.app.Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    companion object {
        const val CHANNEL_ID = "MusicServiceChannel"
        const val NOTIFICATION_ID = 1

        const val ACTION_START = "com.example.lab1.ACTION_START"
        const val ACTION_PAUSE = "com.example.lab1.ACTION_PAUSE"
        const val ACTION_STOP = "com.example.lab1.ACTION_STOP"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music)
        mediaPlayer?.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startMusic()
            ACTION_PAUSE -> pauseMusic()
            ACTION_STOP -> stopMusic()
        }
        return START_NOT_STICKY
    }

    private fun startMusic() {
        if (!isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
            startForeground(NOTIFICATION_ID, createNotification("Playing"))
        }
    }

    private fun pauseMusic() {
        if (isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            updateNotification("Paused")
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        isPlaying = false
        stopForeground(true)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Channel for music service notifications"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(status: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText(status)
            .setSmallIcon(R.drawable.ic_music)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification(status: String) {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID, createNotification(status))
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
} 