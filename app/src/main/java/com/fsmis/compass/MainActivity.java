package com.fsmis.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//public class MainActivity extends AppCompatActivity {
//
//    private SensorManager sensorManager;
//    private ImageView compassImg;
//    private TextView CompassText;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        compassImg = (ImageView) findViewById(R.id.compass_img);
//        CompassText = (TextView) findViewById(R.id.OrientationText);
//        compassImg.setKeepScreenOn(true);
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
//    }
//
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        if (sensorManager != null){
//                sensorManager.unregisterListener(listener);
//        }
//    }
//
//    private SensorEventListener listener = new SensorEventListener() {
//        float[] accelerometerValues = new float[3];
//
//        float[] magneticValues = new float[3];
//        private float lastRotateDegree;
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//                accelerometerValues = event.values.clone();
//            }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
//                magneticValues = event.values.clone();
//            }
//
//            float[] R = new float[9];
//            float[] values = new float[3];
//            sensorManager.getRotationMatrix(R, null, accelerometerValues,magneticValues);
//            SensorManager.getOrientation(R, values);
//            float rotateDegree = - (float) Math.toDegrees(values[0]);
//            if(Math.abs(rotateDegree - lastRotateDegree) > 1){
//                RotateAnimation animation = new RotateAnimation(lastRotateDegree,rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                animation.setFillAfter(true);
//                compassImg.startAnimation(animation);
//                animation.setDuration(200);
//                lastRotateDegree = rotateDegree;
//                CompassText.setText("角度："+rotateDegree);
//
//
//            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };
//}

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView imageView;
    private float currentDegree = 0f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.compass_img);

        // 传感器管理器
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 注册传感器(Sensor.TYPE_ORIENTATION(方向传感器);SENSOR_DELAY_FASTEST(0毫秒延迟);
        // SENSOR_DELAY_GAME(20,000毫秒延迟)、SENSOR_DELAY_UI(60,000毫秒延迟))
        sm.registerListener(MainActivity.this,
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);

    }
    //传感器报告新的值(方向改变)
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float degree = event.values[0];

            /*
            RotateAnimation类：旋转变化动画类

            参数说明:

            fromDegrees：旋转的开始角度。
            toDegrees：旋转的结束角度。
            pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotXValue：X坐标的伸缩值。
            pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotYValue：Y坐标的伸缩值
            */
            RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            //旋转过程持续时间
            ra.setDuration(200);
            //設置動畫結束後的保留狀態
            ra.setFillAfter(true);
            //罗盘图片使用旋转动画
            imageView.startAnimation(ra);

            currentDegree = -degree;

        }
    }
    @Override
    //传感器精度的改变
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

