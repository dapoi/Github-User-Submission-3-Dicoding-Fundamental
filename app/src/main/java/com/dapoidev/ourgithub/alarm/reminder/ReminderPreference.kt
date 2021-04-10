package com.dapoidev.ourgithub.alarm.reminder

import android.content.Context

class ReminderPreference(mContext: Context) {
    companion object {
        const val NAME_PREF = "pref"
        private const val REMINDER = "reminder"
    }

    private val pref = mContext.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)

    // set alarm editor
    fun setAlarmReminder(util: Reminder) {
        val editor = pref.edit()
        editor.putBoolean(REMINDER, util.alarmReminder)
        editor.apply()
    }

    // get alarm
    fun getAlarmReminder(): Reminder {
        val model = Reminder()
        model.alarmReminder = pref.getBoolean(REMINDER, false)
        return model
    }
}