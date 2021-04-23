package com.cg.reminderapp

import android.app.*
import android.content.*
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_reminder.*
import java.util.*



val MY_ALARM = "com.cg.reminderapp.action.alarm"
val filterLocal = IntentFilter(MY_ALARM)
val alarmReciever=AlarmReciever()
val calendar: Calendar = Calendar.getInstance()
lateinit var pref: SharedPreferences

class AddReminderActivity : AppCompatActivity() ,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        //val subject=findViewById(R.id.NoteTV)
      registerReceiver(alarmReciever, filterLocal)
        pref=getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    }


    fun AddButtonClicked(view: View){

        val id = view.id
        when (id) {
            R.id.timeButtonB -> {
                val dlg = DialogClass()
                val bund = Bundle()
                bund.putInt("type", 1)
                dlg.arguments = bund
                dlg.show(supportFragmentManager, "time")
            }
            R.id.dateButtonB -> {

                val dlg = DialogClass()
                val bund = Bundle()
                bund.putInt("type", 2)
                dlg.arguments = bund
                dlg.show(supportFragmentManager, "date")
            }
            R.id.cancelAddButtonB -> {
                this.finish()
            }
        }

        }
    fun addClicked(view: View){
                val timeBefore=pref.getInt("timeBefore",15)
                val nextIntent = Intent(this, MainActivity::class.java)
                val title = subjectEV.text.toString()
                if (title.isNotEmpty()) {
                    nextIntent.putExtra("title", title)
                }
                val note = getNoteEV.text.toString()
                if (note.isNotEmpty()) {
                    nextIntent.putExtra("note", note)
                }
                nextIntent.putExtra("date", date)
                nextIntent.putExtra("time", time)
                setResult(RESULT_OK, nextIntent)

                saveToDB(title,time,date,note)
        if(addToCalCB.isChecked) {
            addToCal(title, note)
        }
                sendNotification(title)

        if(mins>=timeBefore){
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, mins-timeBefore)
        }
        else{
            calendar.set(Calendar.HOUR_OF_DAY, hour-1)
            calendar.set(Calendar.MINUTE, mins+60-timeBefore)
        }

        val myIntent = Intent(this, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)
        finish()

    }

     var hour: Int=0
     var mins: Int=0
    lateinit var time: String
    lateinit var date: String
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        time = "$hourOfDay:$minute"
        hour=hourOfDay
        mins=minute
        Toast.makeText(this, "Seleced Time is: $time", Toast.LENGTH_SHORT).show()
        getTimeEV.setText(time)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth,hour,mins)
        val exactMonth = month + 1
        date = "$year-$exactMonth-$dayOfMonth"
        Toast.makeText(this, "Date time selected $date", Toast.LENGTH_LONG).show()
        getDateEV.setText(date)
    }

    private fun saveToDB(titledb:String,timedb:String,datedb:String,notedb:String){
        val wrapper =ReminderDBWrapper(this)
        val rowid=wrapper.saveCredential(titledb,timedb,datedb,notedb)


        if (rowid.toInt() != -1){
            Toast.makeText(this,"Data saved at row $rowid",Toast.LENGTH_LONG).show()
        }

        else  Toast.makeText(this,"Data saved at row $rowid",Toast.LENGTH_LONG).show()
    }
    private fun addToCal(titledb:String,notedb:String){
        val cr=contentResolver
        val cVal=ContentValues()
        cVal.put(CalendarContract.Events.DTSTART,calendar.timeInMillis)
        cVal.put(CalendarContract.Events.DTEND,calendar.timeInMillis+(15*60*1000))
        cVal.put(CalendarContract.Events.TITLE,titledb)
        cVal.put(CalendarContract.Events.DESCRIPTION,notedb)
        cVal.put(CalendarContract.Events.CALENDAR_ID,3)
        cVal.put(CalendarContract.Events.EVENT_TIMEZONE,TimeZone.getDefault().id)
        cr.insert(CalendarContract.Events.CONTENT_URI,cVal)
    }


    override fun onDestroy() {
       unregisterReceiver(alarmReciever)
        super.onDestroy()
    }
    private fun sendNotification(titleNotif:String) {
        // 1. get refrence to notification manager
        val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        lateinit var builder: Notification.Builder
        //create Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("tester", "ReminderApp", NotificationManager.IMPORTANCE_DEFAULT)

            nManager.createNotificationChannel(channel)
            builder = Notification.Builder(this, "testing")
        } else {
            val builder = Notification.Builder(this)
        }

        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle("Reminder Added $titleNotif")
        builder.setContentText("We'll notify you on time for $titleNotif")


        val serIntent = Intent(this, MainActivity::class.java)
        val resumeIntent = PendingIntent.getActivity(this, 0, serIntent, 0)
        val resumeAction = Notification.Action.Builder(
            android.R.drawable.ic_dialog_info,
            "View..",
            resumeIntent
        )
        builder.addAction(resumeAction.build())
        val intent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(this, 0, intent, 0)
        builder.setContentIntent(pending)
        val myNotification = builder.build()
        myNotification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_NO_CLEAR
        //3. send Notification
        nManager.notify(1, myNotification)
    }

}






