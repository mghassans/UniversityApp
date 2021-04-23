package com.cg.reminderapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
val PREF_NAME="timebeforeinfo"
class RemindBefore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind_before)
    }
    private fun saveCredentials(time:Int=0){
        val pref =getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor =pref.edit()
        editor.putInt("timeBefore",time)
        editor.commit()
        Toast.makeText(this,"Data Saved!", Toast.LENGTH_LONG).show()
    }

    fun radioBClicked(view: View) {
        when(view.id){
            R.id.fifteenRB->{
                saveCredentials(15)
            }
            R.id.thirtyRB->{
                saveCredentials(30)
            }
            R.id.fourtyFiveRB->{
                saveCredentials(45)
            }
            R.id.oneHourRB->{
                saveCredentials(0)
            }


        }
    }
}