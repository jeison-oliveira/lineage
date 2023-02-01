package devtitans.smartgasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.*;

import android.os.RemoteException;

import java.util.List;

import devtitans.smartgasmanager.SmartgasManager;                          // Biblioteca do Manager

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DevTITANS.SmartgasApp";

    private TextView textStatus, textConcentration;
    //private SmartgasManager manager;                                        // Atributo para o Manager

    /**
     * Sensor HAL
     */
    private SensorManager sensorManager;
    private TextView lightLevel;
    public String sensorListString = "";
    /**
     *
     */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus =     findViewById(R.id.textStatus);                      // Acessa os componentes da tela
        textConcentration = findViewById(R.id.textConcentration);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(65536);
        Sensor sensor = sensorList.get(0);

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

        updateAll(null);
//        GasCheckScheduler.scheduleGasCheck(this);

//        Intent serviceIntent = new Intent(this,
//                MyBackgroundService.class);
//        startService(serviceIntent);
        if(!foregroundServiceRunning()) {
            Intent serviceIntent = new Intent(this, MyForegroundService.class);
            startForegroundService(serviceIntent);
        }
    }

    public void updateAll(View view) {
        Log.d(TAG, "Atualizando dados do dispositivo ...");

        textStatus.setText("Atualizando ...");
        textStatus.setTextColor(Color.parseColor("#c47e00"));




    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // The value of the first subscript in the values array is the current light intensity
            float concentration = event.values[0];
            Log.d("smartgas", "concentration = " + concentration);             // Executa o método getConcentration via manager
            textConcentration.setText(String.valueOf(concentration));
                               // Executa o método connect via manager
            if (concentration <=0 ) {
                textStatus.setText("Desconectado");
                textStatus.setTextColor(Color.parseColor("#73312f"));
            }
            else if (concentration > 0) {
                textStatus.setText("Conectado");
                textStatus.setTextColor(Color.parseColor("#6d790c"));
            }
            else {
                textStatus.setText("Simulado");
                textStatus.setTextColor(Color.parseColor("#207fb5"));
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(MyForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}