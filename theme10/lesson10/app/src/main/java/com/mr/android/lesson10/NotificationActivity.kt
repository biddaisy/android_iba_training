package com.mr.android.lesson10

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.RemoteInput

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val results = RemoteInput.getResultsFromIntent(intent)
        if (results != null) {
            val quickReplyResult : String? = results.getString(EXTRA_TEXT_REPLY)
            if (quickReplyResult != null) {
                Log.d("remote input: ", quickReplyResult)
            }
        }
    }
}