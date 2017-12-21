package com.sastroman.angga.androidlearn.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sastroman.angga.androidlearn.R;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.List;
import com.sastroman.angga.androidlearn.app.AppConfig;

public class SplashActivity extends Activity {
    private ProgressBar mProgress;
    private TextView tv;
    private LinearLayout LL, FL;
    private RelativeLayout RL;
    private ImageView iv;
    List<String> list = new ArrayList<>();
    List<String> image = new ArrayList<>();
    List<Integer> color = new ArrayList<>();
    private TransitionSet inset;
    private int progress = 0;
    private boolean visible;
    private AppConfig appConfig;
    String img_url = AppConfig.URL_MAIN + "image/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        tv = (TextView) findViewById(R.id.tv);
        LL = (LinearLayout) findViewById(R.id.LL);
        RL = (RelativeLayout) findViewById(R.id.RL);
        FL = (LinearLayout) findViewById(R.id.FL);
        iv = (ImageView) findViewById(R.id.iv);

        list.add("Easy to use and user friendly.");
        list.add("Great material design with so much features.");
        list.add("Many function and method added.");
        list.add("Support chatting system like WhatsApp");
        list.add("");

        image.add("eye1.png");
        image.add("eye2.png");
        image.add("eye3.png");
        image.add("eye4.png");
        image.add("");

        color.add(R.color.redPrimary);
        color.add(R.color.greenPrimary);
        color.add(R.color.bluePrimary);
        color.add(R.color.magentaPrimary);
        color.add(R.color.cardview_dark_background);

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        TransitionSet set = new TransitionSet()
                .addTransition(new Scale(0.7f))
                .addTransition(new Fade());
        TransitionManager.beginDelayedTransition(FL, set);

        int counter = 100 / list.size();
        for (int i = 0; i < list.size(); i++) {
            progress+=counter;
            final int ii = i;
            FL.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(3000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", progress);
                            animation.setDuration(3000);
                            animation.setInterpolator(new DecelerateInterpolator());
                            animation.start();

                            TransitionSet colorset = new TransitionSet().
                                    addTransition(new Recolor()) .addTransition(new Scale(0.7f))
                                    .addTransition(new Fade())
                                    .setInterpolator(new FastOutLinearInInterpolator())
                                    .setDuration(2000);

                            TransitionManager.beginDelayedTransition(LL, colorset);
                            tv.setTextColor(getResources().getColor(color.get(ii)));

                            TransitionSet set = new TransitionSet()
                                    .addTransition(new Scale(0.7f))
                                    .addTransition(new Fade())
                                    .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                            new FastOutLinearInInterpolator());

                            TransitionManager.beginDelayedTransition(LL, set);
                            tv.setText(list.get(ii));

                            TransitionManager.beginDelayedTransition(FL, new Fade().setDuration(1000));
                            Picasso.with(getBaseContext()).load(img_url+image.get(ii)).into(iv);
                            //TransitionManager.beginDelayedTransition(LL, inset);
                            //tv.setVisibility(View.GONE);
                            //animate(list.get(ii));
                            //tv.setTextColor(color.get(ii));
                            //animate(list.get(ii));
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

    private void startApp() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity_.class);
        startActivity(intent);
    }

    private void hidetv(){
        new Thread(new Runnable() {
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }).start();
    }
}