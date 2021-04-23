package com.cg.reminderapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*


class DialogClass: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var myDlg: Dialog
        val bundel=arguments
        val type=bundel?.getInt("type")
        val parent= activity!!

        when(type) {
            1 -> {
                val c = Calendar.getInstance()
                val mHour = c[Calendar.HOUR_OF_DAY]
                val mMinute = c[Calendar.MINUTE]
               myDlg = TimePickerDialog(parent, parent as OnTimeSetListener,
                        mHour, mMinute, false)
            }
            2 -> {
                val current = Calendar.getInstance()
                myDlg = DatePickerDialog(parent, parent as DatePickerDialog.OnDateSetListener,
                        current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                        current.get(Calendar.DAY_OF_MONTH))
                myDlg.datePicker.minDate=current.timeInMillis

            }
        }
            return myDlg
    }

}