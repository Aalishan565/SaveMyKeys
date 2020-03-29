package com.savemykeys.receivers

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context?) : ContextWrapper(base) {

    companion object {
        const val channelID = "reminderChannelId"
        const val channelName = "Reminder Channel"
    }

    private var mManager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(
            applicationContext,
            channelID
        )
            .setContentTitle("Reminder")
            .setContentText("You have some reminder.")
}