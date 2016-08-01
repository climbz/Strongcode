package com.climbz.strongcode;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AutoWarmupTimer extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    //My code
    TextView clockText;
    long startTime = 0;
    boolean alarm = false;
    CountDownTimer countdown;
    CountDownTimer countdownRest;
    int numExercises = 6; //number of exercises (planks for example)
    int holdTime = 60; //seconds
    int restTime = 4; //seconds
    boolean rest = true;
    int round = 0; //0 of numExercises exercises left to do
    String[] planks = {"plank","Right side plank","Left side plank","reverse plank","hollow hold","supermeng"};


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            clockText = (TextView)findViewById(R.id.fullscreen_text);
            clockText.setText(String.format("%d:%02d", minutes, seconds));
            if(seconds==1 && !alarm) {
                playAlarm();
                alarm = true;
            }
            timerHandler.postDelayed(this, 500);
        }
    };

    private void playAlarm(){
        final MediaPlayer mpAlarm = MediaPlayer.create(this, R.raw.donnng);
        mpAlarm.start();
    }

    private void playComplete(){
        final MediaPlayer mpComplete = MediaPlayer.create(this, R.raw.complete);
        mpComplete.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auto_warmup_timer);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_text);
        clockText = (TextView)findViewById(R.id.fullscreen_text);
        clockText.setText("1:00" + "\n" + planks[round]); //TODO: don't hardcode the time

        createTimers();


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        FloatingActionButton timerButton = (FloatingActionButton)findViewById(R.id.timerButton);
        timerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                forceCountdowns();
            }
        });


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        Button button = (Button)findViewById(R.id.dummy_button);
        button.setText("Start");

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //mp.start();
                Button b = (Button) v;
                if (b.getText().equals("Stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("Start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Stop");
                }
                return false;
            }
        });
    }
    private void createTimers(){
        countdown = new CountDownTimer(holdTime*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                clockText.setText(String.format("%d:%02d", minutes, seconds) + "\n" + planks[round]);
            }

            public void onFinish() {
                if(round==numExercises-1){
                    android.util.Log.d("Derek", "Countdowns complete");
                    playComplete();
                }else {
                    playAlarm();
                    round++;
                    android.util.Log.d("Derek", "Countdown: " + round);
                    countdownRest.start();
                }
            }
        };

        countdownRest = new CountDownTimer(restTime*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                clockText.setText("0:" + millisUntilFinished / 1000);
                android.util.Log.d("Derek", "restTimer millis:" + millisUntilFinished);
            }

            public void onFinish() {
                android.util.Log.d("Derek", "Rest timer complete");
                playAlarm();
                countdown.start();
            }
        };
    }

    private void forceCountdowns(){
        countdown.start();
    }

    private void restCount(){

        countdownRest.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.dummy_button);
        b.setText("start");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
