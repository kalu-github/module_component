package lib.demo.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public final class InputUtil {

    public static void toogleKeybord(Context context) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     */
    public static void openKeybord(Context context, EditText mEditText) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * 关闭软键盘
     */
    public static void closeKeybord(Context context, EditText editText) {

        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开;
            if (isOpen) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);//强制关闭
//                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
//        }
        } catch (Exception e) {
            LogUtil.e("", e.getMessage(), e);
        }
    }

    public static void closeKeybord(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开;
        if (!isOpen) return;
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
