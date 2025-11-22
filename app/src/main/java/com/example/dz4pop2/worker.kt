package com.example.dz4pop2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

const val  CHANNEL_ID = "channel"
class NotifWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val notificationText = inputData.getString("text")?: "Напоминание"
        callNotification(notificationText)
        return Result.success()
    }

    private fun callNotification(text: String) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Notifications",
            NotificationManager.IMPORTANCE_HIGH)
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Напоминание")
            .setContentText(text)
            .build()

        manager.notify(1, builder)
    }


}





