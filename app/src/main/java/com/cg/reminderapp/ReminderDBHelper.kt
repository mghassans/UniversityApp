package com.cg.reminderapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ReminderDBHelper(context : Context): SQLiteOpenHelper(context,"credentials.db",null,1) {

    companion object{
        val TABLE_NAME="Reminders"
        val CLM_TITLE="title"
        val CLM_TIME="time"
        val CLM_DATE="date"
        val CLM_NOTE="note"
        val TABLE_QUERY="create table ${TABLE_NAME}($CLM_TITLE text,$CLM_TIME text,$CLM_DATE text,$CLM_NOTE text)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(TABLE_QUERY)
        } catch (e : Exception){
            Log.e("DBHelper","String Invalid while creating")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
    }
}