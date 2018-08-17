package com.kalu.component;

import android.content.Context;
import android.widget.Toast;

import lib.kalu.core.TestService;
import router.kalu.annotation.Router;

@Router(path = "/app/service")
public class AppService implements TestService {
    @Override
    public void test(Context context, String s) {
        Toast.makeText(context, "我是app，我是被：" + s + "模块调用的，模块间通信测试成功", Toast.LENGTH_SHORT).show();
    }
}
