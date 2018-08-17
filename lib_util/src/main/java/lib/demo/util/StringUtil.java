package lib.demo.util;

/**
 * description: 字符串工具类
 * created by kalu on 2018/4/3 9:43
 */
public class StringUtil {

    /**
     * 计算字符串长度, 用于检测中文英文混输
     *
     * @param sequence
     * @return
     */
    private int caculStrLength(CharSequence sequence) {

        if (null == sequence || sequence.length() == 0) {
            return 0;
        }
        int charNum = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char word = sequence.charAt(i);

            // 1个中文字符 == 2个英文字母
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(word);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {

                charNum++;
            }
        }
        return charNum;
    }
}
