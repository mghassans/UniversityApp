package com.cg.reminderapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reminder_view.*

class ReminderView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_view)
        titleTVLay.text= intent.getStringExtra("title")
        timeTVlay.text= intent.getStringExtra("time")
        dateTVLay.text= intent.getStringExtra("date")
        noteTVLay.text= intent.getStringExtra("note")
    }

    fun okayClicked(view: View) {

        this.finish()
    }
}