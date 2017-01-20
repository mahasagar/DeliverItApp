package com.mattricks.deliverit.widget;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.mattricks.deliverit.MainActivity;
import com.mattricks.deliverit.R;
import com.mattricks.deliverit.TabFragmentCart;
import com.mattricks.deliverit.model.Cart;
import com.mattricks.deliverit.utilities.DataProvider;
import com.mattricks.deliverit.utilities.SampleLoader;

import java.util.List;


public class AndroidWidget extends AppWidgetProvider {

    AndroidWidget widget;

    Cursor cursor=null;

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
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        /*cursor = context.getContentResolver()
                .query(DataProvider.CONTENT_URI, null, null, null, null);*/
        String sone="";
       /* for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            int irow1 = cursor.getColumnIndex("id");
            int irow2 = cursor.getColumnIndex("name");
            int irow3 = cursor.getColumnIndex(DataProvider.productId);
            int irow4 = cursor.getColumnIndex(DataProvider.distributorName);
            int irow5 = cursor.getColumnIndex(DataProvider.distributorPrice);
            sone = sone + cursor.getString(irow1) + "  " + cursor.getString(irow2)+
                    "  " + cursor.getString(irow3)
                    +"  " + cursor.getString(irow4)
                    +"  " + cursor.getString(irow5)
                    + "\n";

        }*/

        /*Log.d("sone@@@ : ",sone);

        try {
            cursor.close();
        }catch(NullPointerException e){
            Log.e("Cursor",e.toString());
        }*/

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(SHOW_POPUP_DIALOG_ACTION)) {
            Intent popUpIntent = new Intent(context, MainActivity.class);
            popUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(popUpIntent);

        }
        String sone="";
       /* for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            int irow1 = cursor.getColumnIndex("id");
            int irow2 = cursor.getColumnIndex("name");
            int irow3 = cursor.getColumnIndex(DataProvider.productId);
            int irow4 = cursor.getColumnIndex(DataProvider.distributorName);
            int irow5 = cursor.getColumnIndex(DataProvider.distributorPrice);
            sone = sone + cursor.getString(irow1) + "  " + cursor.getString(irow2)+
                    "  " + cursor.getString(irow3)
                    +"  " + cursor.getString(irow4)
                    +"  " + cursor.getString(irow5)
                    + "\n";

        }*/

        Log.d("sone@@@ : ",sone);
        Log.d("here","helooo");
        super.onReceive(context, intent);
    }


}
