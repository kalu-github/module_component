package permission.kalu.core.wrapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import permission.kalu.core.intent.IntentType;
import permission.kalu.core.listener.OnAnnotationChangeListener;
import permission.kalu.core.listener.OnPermissionChangeListener;


/**
 * description: 当前类描述信息
 * created by kalu on 2018/5/13 1:08
 */
public abstract class WrapperAbstract implements WrapperImp {

    private final List<String> list = Collections.synchronizedList(new ArrayList<>());

    @IntentType
    private static final int DEFAULT_PAGE_TYPE = IntentType.GOOGLE_SETTING;
    @IntentType
    private int pageType = DEFAULT_PAGE_TYPE;


    private OnPermissionChangeListener listener1;

    // 请求码
    private int requestCode = -1;
    // 是否强制弹出权限申请对话框
    private boolean force = false;

    @Override
    public WrapperImp setOnPermissionChangeListener(OnPermissionChangeListener listener) {
        this.listener1 = listener;
        return this;
    }

    @Override
    public OnPermissionChangeListener getPermissionChangeListener() {
        return listener1;
    }

    @Override
    public OnAnnotationChangeListener getAnnotationChangeListener(String className) {
        String clazz = className + OnAnnotationChangeListener.NAME;
        try {
            return (OnAnnotationChangeListener) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            Log.e("Permission", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public WrapperImp setRequestCode(int code) {
        if (code < 0) {
            throw new IllegalArgumentException("请求码必须大于0, code = " + code);
        }
        this.requestCode = code;
        return this;
    }

    @Override
    public WrapperImp setPermissionName(String... names) {
        for (String name : names) {
            list.add(name);
        }
        return this;
    }

    @Override
    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public WrapperImp setPageType(@IntentType int pageType) {
        this.pageType = pageType;
        return this;
    }

    @Override
    public WrapperImp setForce(boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public boolean isForce() {
        return force;
    }

    @Override
    public int getPageType() {
        return pageType;
    }

    @Override
    public List<String> getPermission() {
        return list;
    }
}
