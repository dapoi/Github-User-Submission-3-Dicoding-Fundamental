package com.dapoidev.ourgithub.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dapoidev.ourgithub.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TIME_FORMAT = "HH:mm"
        const val ID_NOTIF = 1
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_CHANNEL = "daffa"
        private const val NAME_CHANNEL = "reminder_github"
        private const val ID_REPEAT = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        sendNotif(context)
    }

    private fun sendNotif(mContext: Context) {
        val mIntent = mContext.packageManager.getLaunchIntentForPackage("com.dapoidev.ourgithub")
        val pendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, 0)

        val notifManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(mContext, ID_CHANNEL )
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notif)
            .setContentTitle("Hi, there")
            .setContentText("Don't forget to find more connection")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(ID_CHANNEL, NAME_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)

            mBuilder.setChannelId(ID_CHANNEL)
            notifManager.createNotificationChannel(mChannel)
        }

        val notif = mBuilder.build()
        notifManager.notify(ID_NOTIF, notif)
    }

    fun setOnRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        if (TIME_FORMAT.isTimeInvalid(time)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val mIntent = Intent(context, AlarmReceiver::class.java)
        mIntent.apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val arrayTime = time.split(":".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayTime[0]))
            set(Calendar.MINUTE, Integer.parseInt(arrayTime[1]))
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEAT, mIntent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Reminder turn on", Toast.LENGTH_SHORT).show()
    }

    fun setOffRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val mIntent = Intent(context, AlarmManager::class.java)
        val codeReq = ID_REPEAT
        val pendingIntent = PendingIntent.getBroadcast(context, codeReq, mIntent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Reminder turn ff", Toast.LENGTH_SHORT).show()
    }

    private fun String.isTimeInvalid(time: String): Boolean {
        return try {
            val times = SimpleDateFormat(this, Locale.getDefault())
            times.isLenient = false
            times.parse(time)
            false
        } catch (e: ParseException) {
            true
        }
    }
}