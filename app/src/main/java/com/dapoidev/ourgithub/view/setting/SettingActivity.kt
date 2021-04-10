package com.dapoidev.ourgithub.view.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dapoidev.ourgithub.alarm.AlarmReceiver
import com.dapoidev.ourgithub.databinding.ActivitySettingBinding
import com.dapoidev.ourgithub.alarm.reminder.Reminder
import com.dapoidev.ourgithub.alarm.reminder.ReminderPreference

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPref = ReminderPreference(this)
        binding.swReminder.isChecked = reminderPref.getAlarmReminder().alarmReminder

        alarmReceiver = AlarmReceiver()

        binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                true.saveAlarm()
                alarmReceiver.setOnRepeatingAlarm(this,
                    "Alarm repeat", "09:00", "Reminder User")
            } else {
                alarmReceiver.setOffRepeatingAlarm(this)
            }
        }
    }

    private fun Boolean.saveAlarm() {
        val reminderPreference = ReminderPreference(this@SettingActivity)
        reminder = Reminder()

        reminder.alarmReminder = this
        reminderPreference.setAlarmReminder(reminder)
    }
}