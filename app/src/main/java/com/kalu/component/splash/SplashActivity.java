package com.kalu.component.splash;

import android.content.Intent;
import android.os.SystemClock;
import android.view.WindowManager;

import com.kalu.component.MainActivity;
import com.kalu.component.R;

import lib.kalu.core.frame.BaseActivity;
import lib.kalu.widget.timer.TimerView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {

    @Override
    public int initView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initDataLocal() {

        final TimerView timer = findViewById(R.id.splash_timer);
        timer.setOnTimerChangeListener(new TimerView.OnTimerChangeListener() {
            @Override
            public void onTimerEnd() {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                onBackPressed();
            }
        });
    }
}
