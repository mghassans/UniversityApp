package com.cg.reminderapp

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import java.util.*
import kotlin.concurrent.thread

class AlarmRService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val hour=intent?.getIntExtra("hour",12)
        val mins=intent?.getIntExtra("mins",0)

        val t1 = thread {
            //Toast.makeText(this,"Thread executed",Toast.LENGTH_LONG).show()
            when(Calendar.HOUR_OF_DAY==hour && Calendar.MINUTE==mins){
               true->{
                   val nManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                   val builder: Notification.Builder
                   if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                       val channel= NotificationChannel("test","Mychannel", NotificationManager.IMPORTANCE_DEFAULT)
                       nManager.createNotificationChannel(channel)
                       builder= Notification.Builder(this,"test")
                   }
                   else builder= Notification.Builder(this)

                   builder.setContentTitle("Reminder for $hour:$mins ")
                   builder.setContentText("Wake up!!!!")
                   builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                   val i=Intent(this,MainActivity::class.java)
                   val pIntent= PendingIntent.getActivity(this,0,i,0)
                   builder.setContentIntent(pIntent)

                   val notif=builder.build()
                   startForeground(1,notif)
                   Thread.sleep(10000)
                   stopSelf()
               }
            }





        }
        //status bar notification
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        Toast.makeText(this," Service Destroyed", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}