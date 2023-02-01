package devtitans.smartgasapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import devtitans.smartgasmanager.SmartgasManager;

public class MyForegroundService extends Service {

    /**
     * Sensor HAL
     */
    private SensorManager sensorManager;
    private TextView lightLevel;
    public String sensorListString = "";
    public Intent generalIntent;

    long timeCounter = System.nanoTime();
    /**
     *
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Service is running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_background);
        generalIntent = intent;
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        List<Sensor> sensorList = sensorManager.getSensorList(65536);
                        Sensor sensor = sensorList.get(0);
                        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                        timeCounter = System.currentTimeMillis();

                        while (true) {
                            Log.e("smartgas", "Foreground service is running...");
                            /**
                             * Sensor HAL
                             */

                            /**
                             *
                             */

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        startForeground(1001, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Sensor HAL
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
    private SensorEventListener listener = new SensorEventListener() {
        GasNotificationService gasNotificationService = new GasNotificationService();
        @Override
        public void onSensorChanged(SensorEvent event) {
            // The value of the first subscript in the values array is the current light intensity
            float concentration = event.values[0];
            gasNotificationService.concentration = concentration;
            Log.d("smartgas", "concentration = " + concentration);

            float timeElapsedInSeconds = (System.currentTimeMillis() - timeCounter)/1000F;
            if(timeElapsedInSeconds > 2.00F) {
                if (concentration >= 2300) {
                    Log.d("smartgas", "ALTA CONCENTRAÇÃO DE GÁS!!");
                    gasNotificationService.showNotification(MyForegroundService.this, "PERIGO!!!!!!", "ALTA CONCENTRAÇÃO DE GÁS: " + concentration, generalIntent, 1001);
                } else {
                    Log.d("smartgas", "Baixa concentração de gás");
                    gasNotificationService.showNotification(MyForegroundService.this, "ATENÇÃO!", "CONCENTRAÇÃO DE GÁS: " + concentration, generalIntent, 1001);
                }
                timeCounter = System.currentTimeMillis();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    /**
     *
     */
}