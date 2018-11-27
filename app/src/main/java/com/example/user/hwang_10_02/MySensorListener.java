package com.example.user.hwang_10_02;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class MySensorListener implements SensorEventListener {      //센서 이벤트를 implement한것이다
  protected   double accelX ,accelY, accelZ;
  protected  Context context;

    public MySensorListener(Context context) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {            //센서의 값이 바뀔경우 실행되고 event안에 센서의 정보가 들어있다

        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelX = event.values[0];    //x 값
            accelY = event.values[1];    //y 값
            accelZ = event.values[2];    //z 값
            Toast.makeText(context, "X = "+accelX+"Y = "+accelY+"Z ="+accelZ, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}





