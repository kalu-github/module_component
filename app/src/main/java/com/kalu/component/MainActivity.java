package com.kalu.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kalu.component.api.HttpClient;
import com.model.photo.main.MainPhotoActivity;
import com.module2.Module2Service;

import lib.demo.util.LogUtil;
import lib.kalu.core.TestService;
import lib.kalu.core.click.SingleClick;
import lib.kalu.core.http.listener.OnSimpleHttpChangeListener;
import model.kalu.x5.X5OfficeActivity;
import model.kalu.x5.browser.X5BrowserActivity;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

@Router(path = "/app/main")
public class MainActivity extends AppCompatActivity {

    private Button appToModule, appToService1, appToService2, appToModule2, appToFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1112 && resultCode == 1111 && null != data) {
            final String respond = data.getStringExtra("respond");
            Toast.makeText(getApplicationContext(), respond, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试重复点击, 过滤
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {

            @SingleClick
            @Override
            public void onClick(View view) {
                LogUtil.e("kalu", "onClick ==>");
            }
        });

        HttpClient.subscribe(getApplicationContext(), HttpClient.getInstance().getHttpService().test(), new OnSimpleHttpChangeListener<Object>() {

            @Override
            protected void onSucc(Object result) {

            }
        });

        LogUtil.e("", BuildConfig.isLog + "");
        LogUtil.e("kalu", BuildConfig.isLog + "");

        appToModule = findViewById(R.id.appToModule);
        appToService1 = findViewById(R.id.appToService1);
        appToService2 = findViewById(R.id.appToService2);
        appToModule2 = findViewById(R.id.appToModule2);
        appToFragment = findViewById(R.id.appToFragment);

        findViewById(R.id.appToBrowser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RouterManager.getInstance().setPath(X5BrowserActivity.PATH)
                        .putString(X5BrowserActivity.URL, "http://www.atguigu.com/")
                        .putString(X5BrowserActivity.NAME, "测试信息")
                        .start(MainActivity.this, 1112);
            }
        });

        findViewById(R.id.appToOffice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RouterManager.getInstance().setPath(X5OfficeActivity.PATH)
                        .putString(X5OfficeActivity.URL, "http://cdn.mozilla.net/pdfjs/tracemonkey.pdf")
                        .putString(X5OfficeActivity.NAME, "TBS测试.pdf")
                        .start(MainActivity.this, 1112);
            }
        });

        findViewById(R.id.appToPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RouterManager.getInstance().setPath(MainPhotoActivity.PATH).putString("path", "哈哈").start(MainActivity.this, 1112);
            }
        });

        appToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appToFragment();
            }
        });

        appToModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                module1();
            }
        });

        appToService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appToService1();
            }
        });

        appToService2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appToService2();

            }
        });

        appToModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appToModule2();
            }
        });
    }

    private void appToFragment() {
        Object navigation = RouterManager.getInstance().setPath("/module2/fragment").start(this);
        if (navigation instanceof Fragment) {
            Fragment fragment = (Fragment) navigation;
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
    }

    private void appToModule2() {
        RouterManager.getInstance().setPath("/module2/test").putString("path", "/app/main").start(this);
    }

    private void appToService2() {

        Module2Service testService = RouterManager.getInstance().setPath("/module2/service").start(this);
        final String make = testService.make("123456");
        Toast.makeText(getApplicationContext(), make, Toast.LENGTH_SHORT).show();
    }

    private void appToService1() {
        TestService testService = RouterManager.getInstance().setPath("/module1/service").start(this);
        testService.test(this, "app");
    }

    public void module1() {
        RouterManager.getInstance().setPath("/module1/test").putString("path", "/app/main").start(this);
    }
}
