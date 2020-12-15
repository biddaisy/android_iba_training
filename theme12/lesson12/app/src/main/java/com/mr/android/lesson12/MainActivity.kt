package com.mr.android.lesson12

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mr.android.lesson12.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val DENY_PERMISSION = 1
    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationInProgress = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var telephonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_NUMBERS),
                REQUEST_CODE
            )
            return
        }
        init()
    }

    private fun init() {
        try {
            binding.fieldPhoneNumber.setText(telephonyManager.line1Number)
        } catch (ex: SecurityException) {
            exit()
        }
        binding.buttonStartVerification.setOnClickListener(this)
        fireBaseAuth = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
            }
        }
    }

    private fun exit() {
        val intent = Intent()
        setResult(DENY_PERMISSION, intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                    exit()
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    init()
                }
            }
        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val REQUEST_CODE = 101
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonStartVerification -> {
                if (!validatePhoneNumber()) {
                    return
                }
                startPhoneNumberVerification(binding.fieldPhoneNumber.text.toString())
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.fieldPhoneNumber.text.toString()
        if (TextUtils.isEmpty(phoneNumber)) {
            binding.fieldPhoneNumber.error = "Invalid phone number."
            return false
        }

        return true
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(fireBaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        verificationInProgress = true
    }
}