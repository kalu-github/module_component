package lib.kalu.core;

import android.content.Context;

import router.kalu.core.interfaces.IService;

public interface TestService extends IService {
    void test(Context context,String s);
}
