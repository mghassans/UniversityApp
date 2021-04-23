package com.cg.reminderapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ReminderDBWrapper ( context: Context){


    val helper= ReminderDBHelper(context)
    val db: SQLiteDatabase = helper.writableDatabase

    fun saveCredential(title:String, time:String,date:String,note:String): Long{
        //insert into table

        val cValues= ContentValues()
        cValues.put(ReminderDBHelper.CLM_TITLE,title)
        cValues.put(ReminderDBHelper.CLM_TIME,time)
        cValues.put(ReminderDBHelper.CLM_DATE,date)
        cValues.put(ReminderDBHelper.CLM_NOTE,note)
        return db.insert(ReminderDBHelper.TABLE_NAME,null,cValues)
    }
    fun retriveCredential() :Cursor {
        //query operations
        val columns= arrayOf(ReminderDBHelper.CLM_TITLE,ReminderDBHelper.CLM_TIME,ReminderDBHelper.CLM_DATE,ReminderDBHelper.CLM_NOTE)
        return db.query(ReminderDBHelper.TABLE_NAME,columns,null,null,null,null,null)

        }
    fun deleteReminder(title:String){
        db.delete(ReminderDBHelper.TABLE_NAME,"${ReminderDBHelper.CLM_TITLE}=?", arrayOf(title))
    }

    }
