package demo.prim.com.moudle1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lib.kalu.core.TestService;
import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

@Router(path = "/module1/test")
public class Main2Activity extends AppCompatActivity {

    @Extra
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RouterManager.getInstance().inject(this);
        Button button = findViewById(R.id.button);
        Button module1_button = findViewById(R.id.module1_button);
        TextView textview = findViewById(R.id.textview);
//        Intent intent = getIntent();
//        final String path = intent.getStringExtra("path");

        textview.setText("我是module1 我的路由地址是：module1/test. 我是被地址：" + path + "调起来的");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().setPath("/module2/test").putString("path", "/module1/test").start(Main2Activity.this);
            }
        });

        module1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance().setPath("/app/main").start(Main2Activity.this);
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

    private void appToService2() {
        TestService testService = (TestService) RouterManager.getInstance().setPath("/module2/service").start(this);
        testService.test(this, "module1");
    }

    private void appToService1() {
        TestService testService = (TestService) RouterManager.getInstance().setPath("/app/service").start(this);
        testService.test(this, "module1");
    }
}
