package com.mr.android.lesson10

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mr.android.lesson10.MagicCoroutineWorker.Companion.Progress
import java.util.concurrent.TimeUnit

const val CHANNEL_ID = "2133"
const val NOTIFICATION_ID = 1
const val EXTRA_TEXT_REPLY = "RES"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startWorker()
        startCoroutineWorker()
        notificationWithActionReply()
    }

    fun startWorker() {
        val work = OneTimeWorkRequest.Builder(MagicWorker::class.java)
            .addTag("firstWork")
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(work)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(work.id).observe(this,
            { t -> Log.d("Worker", "onChanged: ${t!!.state} ") })
    }

    fun startCoroutineWorker() {
        val magicCoroutineWorker =
            OneTimeWorkRequest.Builder(MagicCoroutineWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(magicCoroutineWorker)
        WorkManager.getInstance(this)
            // requestId is the WorkRequest id
            .getWorkInfoByIdLiveData(magicCoroutineWorker.id)
            .observe(this, { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    val progress = workInfo.progress
                    val value = progress.getInt(Progress, -1)
                    Log.d("Worker", "Progress $value")
                }
            })
    }

    fun notificationWithActionReply() {
        val remoteInput = RemoteInput.Builder(EXTRA_TEXT_REPLY)
            .setLabel("Type message")
            .build()

        val remoteInputIntent = Intent(this, NotificationActivity::class.java)
        val action = NotificationCompat.Action.Builder(
            android.R.drawable.ic_menu_send,
            "Reply",
            PendingIntent.getActivity(
                this,
                0,
                remoteInputIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        ).addRemoteInput(remoteInput).build()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sample")
            .setContentText("I notify u")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(action)
            .build()
        val mChannel =
            NotificationChannel(CHANNEL_ID, "my_channel", NotificationManager.IMPORTANCE_HIGH)
        mChannel.description = "SampleChannel"
        mChannel.enableLights(true)

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

}