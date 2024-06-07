package com.example.taskque_mobile_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get id & message from intent.
        int taskId = intent.getIntExtra("taskId", 0);
        int timerId = intent.getIntExtra("timerId", 0);

        TasksDB db = new TasksDB(context);
        db.open();
        Tasks t = db.getTasksData(taskId + "");
        Timers timers = db.getATimersData(timerId + "");


        // When notification is tapped, call MainActivity.
        Intent mainIntent = new Intent(context, EditTask.class);
        mainIntent.putExtra("TaskID",taskId);
        mainIntent.putExtra("TimersID",timerId);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);


        // building the notification

        Notification notification = new NotificationCompat.Builder(context, "not")
                // Show controls on lock screen even when user hides sensitive content.
                .setSmallIcon(R.drawable.notification_icon)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // Apply the media style template
                .setContentTitle(t.getTitle()) //title of the notification
                .setContentText(t.getDescription()) //notification context
                .setOnlyAlertOnce(true)
                .setContentIntent(contentIntent)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notification);


        db.deleteTimersEntry(timerId + "");
        db.entryTodayTimers(timers.getTaskID(), timers.getYear(), timers.getMonth(), timers.getDayOFMonth(), timers.getHourOFDay(), timers.getMinute(), timers.getType());
        if(timers.getType().equals("Daily"))
        {
            Calendar time = Calendar.getInstance();
            time.set(Calendar.YEAR,timers.getYear());
            time.set(Calendar.MONTH,timers.getMonth());
            time.set(Calendar.DAY_OF_MONTH,timers.getDayOFMonth());
            time.set(Calendar.HOUR_OF_DAY,timers.getHourOFDay());
            time.set(Calendar.MINUTE,timers.getMinute());
            time.set(Calendar.SECOND,0);
            long alarmTime = time.getTimeInMillis();
            alarmTime = alarmTime + 24*3600*1000;

            time.setTimeInMillis(alarmTime);

            timers.setYear(time.get(Calendar.YEAR));
            timers.setMonth(time.get(Calendar.MONTH));
            timers.setDayOFMonth(time.get(Calendar.DAY_OF_MONTH));
            timers.setHourOFDay(time.get(Calendar.HOUR_OF_DAY));
            timers.setMinute(time.get(Calendar.MINUTE));

            db.entryTimers(timers.getTaskID(), timers.getYear(), timers.getMonth(), timers.getDayOFMonth(), timers.getHourOFDay(), timers.getMinute(), timers.getType());


        }
        else if(timers.getType().equals("Weekly"))
        {
            Calendar time = Calendar.getInstance();
            time.set(Calendar.YEAR,timers.getYear());
            time.set(Calendar.MONTH,timers.getMonth());
            time.set(Calendar.DAY_OF_MONTH,timers.getDayOFMonth());
            time.set(Calendar.HOUR_OF_DAY,timers.getHourOFDay());
            time.set(Calendar.MINUTE,timers.getMinute());
            time.set(Calendar.SECOND,0);
            long alarmTime = time.getTimeInMillis();
            alarmTime = alarmTime + 7*24*3600*1000;

            time.setTimeInMillis(alarmTime);

            timers.setYear(time.get(Calendar.YEAR));
            timers.setMonth(time.get(Calendar.MONTH));
            timers.setDayOFMonth(time.get(Calendar.DAY_OF_MONTH));
            timers.setHourOFDay(time.get(Calendar.HOUR_OF_DAY));
            timers.setMinute(time.get(Calendar.MINUTE));

            db.entryTimers(timers.getTaskID(), timers.getYear(), timers.getMonth(), timers.getDayOFMonth(), timers.getHourOFDay(), timers.getMinute(), timers.getType());

        }
        db.close();
    }
}
