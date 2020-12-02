package com.mr.android.lesson7

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mr.android.lesson7.databinding.FragmentDialogsBinding
import java.util.*

class DialogsFragment : Fragment() {

    lateinit var binding: FragmentDialogsBinding
    var calendar: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dialogs, container, false)
        binding.buttonDialog.setOnClickListener {
            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }

            builder?.setMessage(R.string.dialog_message)
                ?.setTitle(R.string.dialog_title)
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
        binding.buttonAlertDialog.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK button
                        })
                    setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                    setNeutralButton(
                        R.string.nothing,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                }
                builder.setMessage(R.string.alert_dialog_message).setTitle(R.string.alert_dialog)
                builder.create()
            }
            alertDialog?.show()
        }
        binding.buttonTimePicker.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minutes ->
                    binding.outputTextView.text = ("${hour} ${minutes}")
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        return binding.root
    }
}