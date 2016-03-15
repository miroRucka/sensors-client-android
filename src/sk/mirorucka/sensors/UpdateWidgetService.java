package sk.mirorucka.sensors;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

public class UpdateWidgetService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        ComponentName thisWidget = new ComponentName(getApplicationContext(), SensorsWidget.class);
        for (int widgetId : allWidgetIds) {
            int number = (new Random().nextInt(100));
            RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.sensors_widget_layout);
            remoteViews.setTextViewText(R.id.update, "Random: " + String.valueOf(number));
            Intent clickIntent = new Intent(this.getApplicationContext(), SensorsWidget.class);
            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            HttpSettingsApplicableReset test = new HttpSettingsApplicableReset("http://horske.info", this);
            try {
                String response = test.execute("").get();
                Log.i(Constant.APP_TAG.getCode() + "response", "anoooo " + response);
            } catch (Exception e) {
                Log.e(Constant.APP_TAG.getCode(), e.getMessage(), e);
            }
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
