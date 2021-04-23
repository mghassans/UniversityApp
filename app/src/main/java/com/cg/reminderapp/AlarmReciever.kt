package com.cg.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast
import kotlin.concurrent.thread


class AlarmReciever : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        //this will update the UI with message
    Toast.makeText(context,"Broadcast Received",Toast.LENGTH_SHORT).show()
thread{

    var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
    if (alarmUri == null) {
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }
    val ringtone = RingtoneManager.getRingtone(context, alarmUri)
    ringtone.play()
    Thread.sleep(45000)

    ringtone.stop()

    }
}
}




