package lib.kalu.core.frame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import lib.demo.util.LogUtil;

/**
 * description: 基类Fragment
 * created by kalu on 17-10-16 上午10:28
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private final String TAG = BaseFragment.class.getName();

    private View rootView;

    private boolean isLoad;
    private boolean isActivityCreated;

    private T mPresenter;

    protected boolean setFull() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

//        if (!setFull()) {
//            final ViewGroup root = getActivity().findViewById(android.R.id.content);
//            final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            final int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            root.setPadding(0, statusBarHeight, 0, 0);
////            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
////            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 窗口支持透明度
        // getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

        final int theme = initTheme();
        if (-1 == theme) {
            rootView = inflater.inflate(initView(), container, false);
        } else {
            final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), theme);
            final LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
            rootView = localInflater.inflate(initView(), container, false);
        }
        return rootView;
    }

//    protected void initBar() {
//        final int color = getResources().getColor(R.color.color_bg_theme);
//        BarManger.setColor(getActivity(), color);
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if (!setFull()) {
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 获取状态栏高度
//            // 绘制一个和状态栏一样高的矩形，并添加到视图中
//            final View rectView = new View(getContext().getApplicationContext());
//            final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            final int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//            rectView.setLayoutParams(params);
//            rectView.setBackgroundColor(BaseApp.getColors(R.color.color_bg_theme));
//            // 添加矩形View到布局中
//            ViewGroup decorView = (ViewGroup) getActivity().getWindow().getDecorView();
//            decorView.addView(rectView);
//            // 设置根布局的参数
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content));
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            // final Constructor constructor = Class.forName("类的绝对路径").getConstructor(Context.class);
            // T clazz = (T) constructor.newInstance(getApplicationContext());
            final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();
            initDataLocal();

            isActivityCreated = true;

            // 第一个fragment会调用
            if (!getUserVisibleHint()) return;
            initDataNet();
            isLoad = true;

        } catch (Exception e) {
            LogUtil.e(TAG, "onActivityCreated ==> exception = " + e.getMessage(), e);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isLoad && isActivityCreated) {
            initDataNet();
            isLoad = true;
        }
    }

    @Override
    public void onDetach() {

        // 销毁数据
        if (null != mPresenter) {
            mPresenter.recycler();
        }

        super.onDetach();
    }

    public View getView() {
        return rootView;
    }

    public abstract int initView();

    public int initTheme() {
        return -1;
    }

    public void initDataNet() {
    }

    public void initDataLocal() {
    }

    public T getPresenter() {
        return mPresenter;
    }
}