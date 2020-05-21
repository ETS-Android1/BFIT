package com.ansoft.bfit.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ansoft.bfit.HomeActivity;
import com.ansoft.bfit.R;

import java.util.Calendar;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Jaison on 20/06/17.
 */

public class NotificationScheduler
{
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";


    public static String[] quotes={
            "No pain, no gain. Shut up and train.",
            "Your body can stand almost anything. It’s your mind that you have to convince.",
            "Success isn’t always about greatness. It’s about consistency. Consistent hard work gains success. Greatness will come.",
            "Train insane or remain the same.",
            "Definition of a really good workout: when you hate doing it, but you love finishing it.",
            "Push yourself because no one else is going to do it for you.",
            "Suck it up. And one day you won’t have to suck it in.",
            "I will beat her. I will train harder. I will eat cleaner. I know her strengths. I’ve lost to her before but not this time. She is going down. I have the advantage because I know her well. She is the old me.",
            "Success starts with self-discipline.",
            "Good things come to those who sweat.",
            "Motivation is what gets you started. Habit is what keeps you going.",
            "A one hour workout is 4% of your day. No excuses.",
            "The body achieves what the mind believes.",
            "What seems impossible today will one day become your warm-up.",
            "Never give up on a dream just because of the time it will take to accomplish it. The time will pass anyway.",
            "Someone busier than you is working out right now.",
            "Hustle for that muscle.",
            "Work hard in silence. Let success be your noise."

    };

    public static void setReminder(Context context,Class<?> cls,int hour, int min)
    {
        Calendar calendar = Calendar.getInstance();

        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);

        // cancel already scheduled reminders
        cancelReminder(context,cls);

        if(setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE,1);

        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
       // am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.e("Current time", System.currentTimeMillis()+"");
        Log.e("Notification time", setcalendar.getTimeInMillis()+"");


        am.set(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),pendingIntent);
    }

    public static void cancelReminder(Context context,Class<?> cls)
    {
        // Disable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void showNotification(Context context,Class<?> cls,String title,String content)
    {

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Random random = new Random();
        int index = random.nextInt(quotes.length);

        String quote=quotes[index];

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.woman);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "98251")
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.weightlifting)
                .setContentTitle("Its workout time")
                .setContentText(quote)
                .setSubText("Get your ass movin!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);




        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, builder.build());


    }

}
