package com.kalu.component.main;

import com.kalu.component.R;

import lib.kalu.core.frame.BaseActivity;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {

    @Override
    public int initView() {
        return R.layout.activity_splash;
    }

}
