package demo.prim.com.moudle1;

import android.content.Context;
import android.widget.Toast;

import lib.kalu.core.TestService;
import router.kalu.annotation.Router;

@Router(path = "/module1/service")
public class Module1Service implements TestService {
    private static final String TAG = "Module1Service";

    @Override
    public void test(Context context, String s) {
        Toast.makeText(context, "我是Module1，我是被：" + s + "模块调用的，模块测试通信成功", Toast.LENGTH_SHORT).show();
    }
}
