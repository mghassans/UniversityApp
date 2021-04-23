package com.cg.reminderapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)
        Toast.makeText(this,"Click on Icons",Toast.LENGTH_LONG).show()

    }

    fun fabClickedButton(view: View) {
        when(view.id)
        {

            R.id.callfab ->{
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18002001"))
                if (callIntent.resolveActivity(packageManager) != null) {
                    startActivity(callIntent)
                }
            }
            R.id.mailfab ->{

                val visitIntent: Intent = Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+"address@email.com"))
                //visitIntent.putExtra(Intent.EXTRA_EMAIL, "address@email.com")

                if (visitIntent.resolveActivity(packageManager) != null) {
                    startActivity(visitIntent)
                }
            }
            R.id.mapfab ->{
                val mapIntent = Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=Capgemini,bengaluru"))
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
            R.id.smsfab ->{
                val smsIntent=Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+"5667071"))
                if (smsIntent.resolveActivity(packageManager) != null) {
                    startActivity(smsIntent)
                }
            }
        }
    }
}