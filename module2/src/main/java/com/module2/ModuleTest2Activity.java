package com.module2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

@Router(path = "/module2/test")//注意不同的module 不能使用同一个 group 作为分组名 否则会生成相同的类导致报错
public class ModuleTest2Activity extends AppCompatActivity {

    @Extra(name = "path")
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_test2);
        RouterManager.getInstance().inject(this);
        TextView module2_textView = findViewById(R.id.module2_textView);
        Button module2_button = findViewById(R.id.module2_button);
        Button app_button = findViewById(R.id.app_button);
//        Intent intent = getIntent();
//        final String path = intent.getStringExtra("path");
        module2_textView.setText("我是module2，我的地址是：/module2/test. 我是被地址：" + url + " 调起的");
        module2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().setPath("/module1/test").putString("path", "/module2/test").start(ModuleTest2Activity.this);
            }
        });

        app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_button();
            }
        });

        Button appToService1 = findViewById(R.id.appToService1);
        Button appToService2 = findViewById(R.id.appToService2);

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
    }

    private void app_button() {
        RouterManager.getInstance().setPath("/app/main").start(this);
    }

    private void appToService2() {
        Module2Service testService = RouterManager.getInstance().setPath("/module1/service").start(this);
        testService.make("module2");
    }

    private void appToService1() {
        Module2Service testService = RouterManager.getInstance().setPath("/app/service").start(this);
        testService.make("module2");
    }
}
