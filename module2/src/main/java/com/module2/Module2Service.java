package com.module2;

import router.kalu.annotation.Router;
import router.kalu.core.interfaces.IService;

@Router(path = "/module2/service")
public class Module2Service implements IService {

    public String make(String s) {
        return "返回值 ==>" + s;
    }
}
