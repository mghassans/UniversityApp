package com.cg.reminderapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class settinsMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settins_menu)
    }

    fun FABClicked(view: View) {
        when(view.id){
            R.id.remindBeforeFAB->{
                val i= Intent(this,RemindBefore::class.java)
                startActivity(i)
            }
        }
    }
}