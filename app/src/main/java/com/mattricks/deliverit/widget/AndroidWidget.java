package com.mattricks.deliverit.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.mattricks.deliverit.MainActivity;
import com.mattricks.deliverit.R;

/**
 * Created by niteshgarg on 10/04/16.
 */
public class AndroidWidget extends AppWidgetProvider {
    private static final String SHOW_POPUP_DIALOG_ACTION = "com.mattricks.deliverit.widget";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                AndroidWidget.class);
        int[] allWidgetInstancesIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetInstancesIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            // Create an intent that when received will launch the PopUpActivity
            Intent intent = new Intent(context, AndroidWidget.class);
            intent.setAction(SHOW_POPUP_DIALOG_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // Set up the onClickListener of the widget
            // Now, when the widget is pressed the pendingIntent will be sent
           // remoteViews.setOnClickPendingIntent(R.id.myText, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(SHOW_POPUP_DIALOG_ACTION)) {
            Intent popUpIntent = new Intent(context, MainActivity.class);
            popUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(popUpIntent);
        }

        super.onReceive(context, intent);

    }

}
