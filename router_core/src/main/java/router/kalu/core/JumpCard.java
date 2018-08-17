package router.kalu.core;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;

import java.util.ArrayList;

import router.kalu.annotation.RouterMeta;
import router.kalu.core.interfaces.IService;

/**
 * description: 路由跳转的跳卡, 跳卡根据路由地址来跳转, 跳卡还可以设置跳转动画, 传递参数等
 * created by kalu on 2018/8/17 11:46
 */
public final class JumpCard extends RouterMeta {

    private Bundle mBundle;
    private int flags = -1;
    /**
     * 动画
     */
    //新版 md风格
    private Bundle optionsCompat;
    //老版
    private int enterAnim;
    private int exitAnim;

    private IService service;

    public JumpCard(String path, String group) {
        this(path, group, null);
    }

    public JumpCard(String path, String group, Bundle bundle) {
        setPath(path);
        setGroup(group);
        this.mBundle = bundle == null ? new Bundle() : bundle;
    }

    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }

    public Bundle getExtras() {
        return mBundle;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public Bundle getOptionsBundle() {
        return optionsCompat;
    }

    /**
     * Intent.FLAG_ACTIVITY**
     *
     * @param flag
     * @return
     */
    public JumpCard withFlags(int flag) {
        this.flags = flag;
        return this;
    }


    public int getFlags() {
        return flags;
    }

    /**
     * 跳转动画
     *
     * @param enterAnim
     * @param exitAnim
     * @return
     */
    public JumpCard withTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    /**
     * 转场动画
     *
     * @param compat
     * @return
     */
    public JumpCard withOptionsCompat(ActivityOptionsCompat compat) {
        if (null != compat) {
            this.optionsCompat = compat.toBundle();
        }
        return this;
    }

    public <T> T start(Activity activity) {
        return (T) RouterManager.getInstance().start(activity, this, -1, null);
    }

    public <T> T start(Activity activity, int requestCode) {
        return (T) RouterManager.getInstance().start(activity, this, requestCode, null);
    }

    /*********************************************************************************************/

    public JumpCard putString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public JumpCard putBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public JumpCard putShort(@Nullable String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public JumpCard putInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public JumpCard putLong(@Nullable String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public JumpCard putDouble(@Nullable String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public JumpCard putByte(@Nullable String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public JumpCard putChar(@Nullable String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public JumpCard putFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public JumpCard putParcelable(@Nullable String key, @Nullable Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public JumpCard putStringArray(@Nullable String key, @Nullable String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }

    public JumpCard putBooleanArray(@Nullable String key, boolean[] value) {
        mBundle.putBooleanArray(key, value);
        return this;
    }

    public JumpCard putShortArray(@Nullable String key, short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    public JumpCard putIntArray(@Nullable String key, int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }

    public JumpCard putLongArray(@Nullable String key, long[] value) {
        mBundle.putLongArray(key, value);
        return this;
    }

    public JumpCard putDoubleArray(@Nullable String key, double[] value) {
        mBundle.putDoubleArray(key, value);
        return this;
    }

    public JumpCard putByteArray(@Nullable String key, byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    public JumpCard putCharArray(@Nullable String key, char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    public JumpCard putFloatArray(@Nullable String key, float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    public JumpCard putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public JumpCard putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends
            Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public JumpCard putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public JumpCard putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }
}
