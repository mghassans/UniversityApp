package com.cg.reminderapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/*
ReminderAPP

-Add new reminder
-View reminders
-Edit reminder
- Delete reminder


REminder
    - task title
        task description
        reminder date/time

MainActivity
    - ADD REminder -> AddActivity
                        - Get data
                        - Buttons- ADD, CAncel
                        - Add clicked-
                            Status bar notification- new reminder added
    - View REminders
            ListView
                - List of reminders
                - on selection of reminder
                        - Dialog- Edit/Delete
                        Edit- > display all details of reminder
                        Delete -> remove from list


                        =================================
                        - Add options menu (launcher Activity)
     - Contact Us (menu item)
             - launch Activity
                     - Send SMS -> Messaging app -> number
                     - Call -> Phone app-> number
                     - Email -> EMail app -> To:xyz@test.com
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    var reminders = mutableListOf<ReminderData>()
    var adapter: ReminderAdapter? = null
    val MENU_CONTACT = 1
    val MENU_EXIT = 2
    val MENU_SETTINGS=3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listReminderL.setOnItemClickListener(this)
        adapter  = ReminderAdapter(this, R.layout.reminder_list_view_items, reminders)

        listReminderL.adapter = adapter
        val wrapper =ReminderDBWrapper(this)
        val cursor=wrapper.retriveCredential()
        if(cursor?.count!! >0){
            //atleast one number selected
            cursor.moveToFirst()
            val idxTitle=cursor.getColumnIndex(ReminderDBHelper.CLM_TITLE)
            val idxTime=cursor.getColumnIndex(ReminderDBHelper.CLM_TIME)
            val idxDate=cursor.getColumnIndex(ReminderDBHelper.CLM_DATE)
            val idxNote=cursor.getColumnIndex(ReminderDBHelper.CLM_NOTE)
            do {
                val title=cursor.getString(idxTitle)
                val time=cursor.getString(idxTime)
                val date=cursor.getString(idxDate)
                val note=cursor.getString(idxNote)
                reminders.add(ReminderData(title,date,time,note))
            }while (cursor.moveToNext())

        }else
            Toast.makeText(this,"Nothing to Update", Toast.LENGTH_SHORT).show()
    }

    fun fbuttonClicked(view: View) {
        val i = Intent(this, AddReminderActivity::class.java)
        startActivityForResult(i,2)
       // startActivity(i)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
        val new=ReminderData(
                data?.getStringExtra("title").toString(), data?.getStringExtra("date").toString(),data?.getStringExtra("time").toString(),
                "Note: "+data?.getStringExtra("note").toString())
            reminders.add(new)
            adapter?.notifyDataSetChanged()
       }
  }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val contact = menu?.add(0, MENU_CONTACT, 0, "contact")
        contact?.setIcon(android.R.drawable.ic_menu_call)
        contact?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menu?.add(0, MENU_SETTINGS, 0, "Settings")
        menu?.add(0, MENU_EXIT, 0, "Exit")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_CONTACT -> {
                val i = Intent(this, ContactInfo::class.java)
                startActivity(i)
            }
            MENU_EXIT -> {
                this.finish()
            }
            MENU_SETTINGS->{
                val i=Intent(this, settinsMenu::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selected=reminders[position]

        var builder= AlertDialog.Builder(this)
        builder.setMessage("What you want to do?")
        builder.setPositiveButton("VIEW"){ dlg,i->

            var act=Intent(this,ReminderView::class.java)

            act.putExtra("title",selected?.title)
            act.putExtra("time",selected?.time)
            act.putExtra("date",selected?.date)
            act.putExtra("note",selected?.note)
            startActivity(act)
        }
        builder.setNegativeButton("DELETE"){ dlg,i->
            reminders.removeAt(position)
            val cr=contentResolver
            val uri= CalendarContract.Events.CONTENT_URI
            val args= arrayOf(selected.title)
            cr.delete(uri,"${CalendarContract.Events.TITLE}=?",args)
            val wrapper=ReminderDBWrapper(this)
            wrapper.deleteReminder(selected.title)
            adapter?.notifyDataSetChanged()
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("CANCEL"){dlg,i-> dlg.cancel()}
        builder.create().show()
    }
}
class ReminderAdapter( context: Context, val rscID:Int, val arrlist: MutableList<ReminderData>)
    :ArrayAdapter<ReminderData>(context,rscID,arrlist) {

    override fun getItem(position: Int): ReminderData? {
        return arrlist[position]
    }

    //create that view or inflate from xml
    //bind the data
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val remind = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(rscID, null)
        val titltTV = view.findViewById<TextView>(R.id.titltTV)
        val timeTV = view.findViewById<TextView>(R.id.timeTV)
        val dateTV = view.findViewById<TextView>(R.id.dateTV)
        val noteTV = view.findViewById<TextView>(R.id.noteTV)
        titltTV?.text = remind?.title
        timeTV?.text = remind?.time
        dateTV?.text = remind?.date
        noteTV?.text = remind?.note
        return view
    }


}
