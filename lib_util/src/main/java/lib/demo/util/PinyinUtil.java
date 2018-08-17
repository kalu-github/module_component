package lib.demo.util;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据汉字字符串获取拼音首字母
 * Created by wyd on 2016/12/23.
 */
public final class PinyinUtil {
    // 简体中文的编码范围从B0A1（45217）一直到F7FE（63486）
    private static int BEGIN = 45217;
    private static int END = 63486;

    // 按照声 母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
    // i, u, v都不做声母, 自定规则跟随前面的字母
    private static char[] chartable = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝',};

    // 二十六个字母区间对应二十七个端点
    // GB2312码汉字区间十进制表示
    private static int[] table = new int[27];

    // 对应首字母区间表
    private static char[] initialtable = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z',};

    // 初始化
    static {
        for (int i = 0; i < 26; i++) {
            table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
        }
        table[26] = END;// 区间表结尾
    }

    // ------------------------public方法区------------------------
    // 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串 最重要的一个方法，思路如下：一个个字符读入、判断、输出

    public static String ch2py(String SourceStr) {
        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += Char2Initial(SourceStr.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
            e.printStackTrace();
        }
        return Result;
    }

    private final static int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private final static List<String> lc_FirstLetter = Arrays.asList("a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z");

    public final static String getLetter(String chinese) {

        try {
            chinese = new String(chinese.getBytes("GB2312"), "ISO8859-1");
        } catch (Exception ex) {
            Log.e("", ex.getMessage(), ex);
            return "#";
        }

        // 汉字区码
        int li_SectorCode = (int) chinese.charAt(0);
        // 汉字位码
        int li_PositionCode = (int) chinese.charAt(1);

        li_SectorCode = li_SectorCode - 160;
        li_PositionCode = li_PositionCode - 160;

        // 汉字区位码
        int li_SecPosCode = li_SectorCode * 100 + li_PositionCode;

        if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {

            for (int i = 0; i < 23; i++) {
                if (li_SecPosCode >= li_SecPosValue[i]
                        && li_SecPosCode < li_SecPosValue[i + 1]) {
                    chinese = lc_FirstLetter.get(i);
                    break;
                }
            }

            if (lc_FirstLetter.contains(chinese)) {
                return chinese;
            } else {
                return "#";
            }
        } else {
            // 非汉字字符,如图形符号或ASCII码
            try {
                chinese = new String(chinese.getBytes("ISO8859-1"), "GB2312");
                chinese = chinese.substring(0, 1);

                if (lc_FirstLetter.contains(chinese)) {
                    return chinese;
                } else {
                    return "#";
                }

            } catch (Exception ex) {
                Log.e("", ex.getMessage(), ex);
                return "#";
            }
        }
    }

    // ------------------------private方法区------------------------

    /**
     * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0' 　　*
     */
    private static char Char2Initial(char ch) {
        // 对英文字母的处理：小写字母转换为大写，大写的直接返回
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 'a' + 'A');
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch;
        }
        // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
        // 若不是，则直接返回。
        // 若是，则在码表内的进行判断。
        int gb = gbValue(ch);// 汉字转换首字母
        if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回
        {
            return ch;
        }
        int i;
        for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
            if ((gb >= table[i]) && (gb < table[i + 1])) {
                break;
            }
        }
        if (gb == END) {// 补上GB2312区间最右端
            i = 25;
        }
        return initialtable[i]; // 在码表区间中，返回首字母
    }

    /**
     * 取出汉字的编码 cn 汉字
     */
    private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GB2312");
            if (bytes.length < 2) {
                return 0;
            }
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    private static final Pattern mPattern = Pattern.compile("[0-9]");

    public static final boolean isNumber(CharSequence str) {
        final Matcher m = mPattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
