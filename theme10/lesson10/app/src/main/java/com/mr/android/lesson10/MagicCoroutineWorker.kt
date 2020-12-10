package com.mr.android.lesson10

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class MagicCoroutineWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 1L
    }

    override suspend fun doWork(): ListenableWorker.Result = coroutineScope {
        val firstUpdate = workDataOf(Progress to 0)
        setProgress(firstUpdate)
        delay(100)
        var lastUpdate = workDataOf(Progress to 50)
        Log.d("Worker", "delay")
        setProgress(lastUpdate)
        delay(100)
        lastUpdate = workDataOf(Progress to 100)
        Log.d("Worker", "delay")
        setProgress(lastUpdate)
        delay(100)

        return@coroutineScope ListenableWorker.Result.success()
    }
}