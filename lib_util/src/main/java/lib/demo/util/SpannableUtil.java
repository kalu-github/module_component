package lib.demo.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;

public final class SpannableUtil {

    private static final SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();
    private static final SpannableStringBuilder mSpannableStringBuilder2 = new SpannableStringBuilder();

    /**
     * 文字背景颜色
     *
     * @param color
     * @return
     */
    private static final BackgroundColorSpan createBackgroundColorSpan(int color) {
        return new BackgroundColorSpan(color);
    }

    /**
     * 文字颜色
     *
     * @param color
     * @return
     */
    private static final ForegroundColorSpan createForegroundColorSpan(int color) {
        return new ForegroundColorSpan(color);
    }

    /**
     * 文字大小
     *
     * @param size
     * @return
     */
    private static final RelativeSizeSpan createRelativeSizeSpan(float size) {
        return new RelativeSizeSpan(size);
    }

    public static final void newline() {
        mSpannableStringBuilder.append("\n");
    }

    public static final SpannableStringBuilder build() {

        mSpannableStringBuilder2.clear();
        mSpannableStringBuilder2.append(mSpannableStringBuilder);
        mSpannableStringBuilder.clear();
        return mSpannableStringBuilder2;
    }

    public static final void append(String str) {

        final int length1 = mSpannableStringBuilder.length();
        mSpannableStringBuilder.append(str);
        final int length2 = mSpannableStringBuilder.length();

        final ForegroundColorSpan colorSpan = createForegroundColorSpan(Color.BLACK);
        mSpannableStringBuilder.setSpan(colorSpan, length1, length2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static final void append(String str, int color) {

        final int length1 = mSpannableStringBuilder.length();
        mSpannableStringBuilder.append(str);
        final int length2 = mSpannableStringBuilder.length();

        final ForegroundColorSpan colorSpan = createForegroundColorSpan(color);
        mSpannableStringBuilder.setSpan(colorSpan, length1, length2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static final void append(String str, int color, float size) {

        final int length1 = mSpannableStringBuilder.length();
        mSpannableStringBuilder.append(str);
        final int length2 = mSpannableStringBuilder.length();

        final ForegroundColorSpan colorSpan = createForegroundColorSpan(color);
        mSpannableStringBuilder.setSpan(colorSpan, length1, length2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        final RelativeSizeSpan sizeSpan = createRelativeSizeSpan(size);
        mSpannableStringBuilder.setSpan(sizeSpan, length1, length2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static final void appendImageSpan(int start, int end, ImageSpan span) {
        mSpannableStringBuilder.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static final class RoundBackgroundSpan extends ReplacementSpan {

        private int bgColor;
        private int textColor;

        public RoundBackgroundSpan(int bgColor, int textColor) {
            super();
            this.bgColor = bgColor;
            this.textColor = textColor;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return ((int) paint.measureText(text, start, end) + 60);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int color1 = paint.getColor();
            paint.setColor(this.bgColor);
            canvas.drawRoundRect(new RectF(x, top + 1, x + ((int) paint.measureText(text, start, end) + 40), bottom - 1), 15, 15, paint);
            paint.setColor(this.textColor);
            canvas.drawText(text, start, end, x + 20, y, paint);
            paint.setColor(color1);
        }
    }
}
