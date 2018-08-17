package lib.kalu.core.frame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
        setContentView(initView());

//        if (!setFull()) {
//
//            ViewGroup root = findViewById(android.R.id.content);
//            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            root.setPadding(0, statusBarHeight, 0, 0);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            final View rectView = new View(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//            rectView.setLayoutParams(params);
//            rectView.setBackgroundColor(BaseApp.getColors(R.color.color_bg_theme));
//            // 添加矩形View到布局中
//            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
//            decorView.addView(rectView);
//            // 设置根布局的参数
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content));
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
//        }

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
