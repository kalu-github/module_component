package lib.kalu.core.frame;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.ParameterizedType;

import lib.demo.util.LogUtil;

/**
 * description: 基类Activity
 * created by kalu on 17-10-16 下午3:17
 */
public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity {

    private final String TAG = BaseActivity.class.getName();

    private T mPresenter;

    protected boolean setFull() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initOrientation();
        final View content = LayoutInflater.from(getApplicationContext()).inflate(initView(), null);
        setContentView(content);

//        if (!setFull()) {
//            final ViewGroup root = findViewById(android.R.id.content);
//            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            root.setPadding(0, statusBarHeight, 0, 0);
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            // 状态栏透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            // 添加矩形View到布局中
//            final View top = new View(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//            params.gravity = Gravity.CENTER;
//            top.setLayoutParams(params);
//            top.setBackgroundColor(Color.parseColor("#dd13ad67"));
//            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
//            decorView.addView(top);
//            // 设置根布局的参数
//            // root.setFitsSystemWindows(true);
//            // root.setClipToPadding(true);
//        }

        // AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));

        try {
            // final Constructor constructor = Class.forName("类的绝对路径").getConstructor(Context.class);
            // T clazz = (T) constructor.newInstance(getApplicationContext());
            final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();

            initDataLocal();
            initDataNet();
        } catch (Exception e) {
            LogUtil.e(TAG, "onCreate ==> exception = " + e.getMessage(), e);
        }
    }

    protected void initOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏
    }

//    protected final void addFull() {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
////        lp.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//    }
//
//    protected final void clearFull() {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
////        lp.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//        getWindow().setAttributes(lp);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//    }

    @Override
    public void onBackPressed() {

        // 销毁数据
        if (null != mPresenter) {
            mPresenter.recycler();
        }
        super.onBackPressed();
        // overridePendingTransition(0, 0);
    }

    public abstract int initView();

    public void initDataNet() {
    }

    public void initDataLocal() {
    }

    public T getPresenter() {
        return mPresenter;
    }
}
