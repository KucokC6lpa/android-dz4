package com.example.dz4pop2

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.UUID
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var workId: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }


        val notifTextInput = findViewById<EditText>(R.id.notifText)
        val delayTimeInput = findViewById<EditText>(R.id.notifDelay)

        val setNotifButton = findViewById<Button>(R.id.planButton)

        setNotifButton.setOnClickListener{

            val notifText = notifTextInput.text.toString()
            val delayTime = delayTimeInput.text.toString().toLong()


            val data = workDataOf("text" to notifText)
            val notifWorkerRequest = OneTimeWorkRequestBuilder<NotifWorker>()
                .setInputData(data)
                .setInitialDelay(delayTime, TimeUnit.SECONDS)
                .build()

            workId = notifWorkerRequest.id
            WorkManager.getInstance(this).enqueue(notifWorkerRequest)
        }

        val setCancelButton = findViewById<Button>(R.id.cancelButton)
        setCancelButton.setOnClickListener {
            WorkManager.getInstance(this).cancelWorkById(workId)

        }


    }
}