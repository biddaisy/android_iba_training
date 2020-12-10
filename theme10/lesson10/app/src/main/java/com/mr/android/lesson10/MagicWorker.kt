package com.mr.android.lesson10

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MagicWorker (context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        Log.d("Worker:doWork", "start")
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (e: InterruptedException) {
            Log.e("Worker:doWork", e.message, e)
        }

        Log.d("Worker:doWork", "end")
        return Result.success()
    }
}