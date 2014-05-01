package com.pulsedroid.pulsecompass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.pulsedroid.pulsecompass.R;

public class MainActivity extends SlidingActivity implements SensorEventListener {

    
    // Angle of the compass
    private float currentDegree = 0f;

    // Sensor used
    private SensorManager mSensorManager;
    // Which picture to use
    private ImageView image;
    
    TextView tvHeading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // About sliding menu
        
        setBehindContentView(R.layout.activity_menu);
       
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);
        sm.setBehindOffset(125);
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
       
       
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // Heading text
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // Initialize the sensor
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop the sensor when exited
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Z-Axis
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // Reverse turn
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // Animation while turning: duration
        ra.setDuration(210);

       
        ra.setFillAfter(true);

        // Initiate the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
       
    }
}

