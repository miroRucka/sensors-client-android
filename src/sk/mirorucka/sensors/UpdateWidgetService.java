package sk.mirorucka.sensors;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UpdateWidgetService extends Service {

    private static final DecimalFormat DF = new DecimalFormat("0.00##");
    private static final DateFormat IN_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ", Locale.ENGLISH);
    private static final DateFormat OUT_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.sensors_widget_layout);
            Intent clickIntent = new Intent(this.getApplicationContext(), SensorsWidget.class);
            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);
            HttpSensorsLastData test = new HttpSensorsLastData("http://horske.info", this);
            try {
                SensorsData[] response = test.execute().get();
                SensorsData first = response[0];
                remoteViews.setTextViewText(R.id.pressure, "Tlak: " + DF.format(first.getPressure()) + " hPa");
                remoteViews.setTextViewText(R.id.humidity, "Vlhkosť: " + DF.format(first.getHumidity()) + " %");
                remoteViews.setTextViewText(R.id.light, "Svetlo: " + DF.format(first.getLight()) + " lx");
                remoteViews.setTextViewText(R.id.temperatureIn, "Von: " + DF.format(first.getTemperature().get(0).getValue()) + " °C");
                remoteViews.setTextViewText(R.id.temperatureOut, "Dnu: " + DF.format(first.getTemperature().get(1).getValue()) + " °C");
                remoteViews.setTextViewText(R.id.updated, "updated: " + OUT_FORMAT.format(ISO8601.parse(first.getTimestamp())));
            } catch (Exception e) {
                Log.e(Constant.APP_TAG.getCode(), e.getMessage(), e);
                remoteViews.setTextViewText(R.id.updated, "updated: error!");
            } finally {
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
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
