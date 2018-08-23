package lib.kalu.widget.timer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import lib.demo.util.LogUtil;
import lib.kalu.widget.R;

/**
 * description: 圆环倒计时View
 * created by kalu on 2017/7/13 20:34
 */
public class TimerView extends View {

    private final String TAG = TimerView.class.getName();
    private final ValueAnimator animator = new ValueAnimator();

    private int bgColor = Color.BLACK;
    private int circleColor = Color.RED;
    private boolean showTime = true;
    private float boardWidth = 8;
    /*************************************************************************************/

    private RectF rectF;
    private Paint paintText;
    private Paint paintBg;
    private Paint paintYh;
    private Paint paintYh2;
    private float temp;
    private long currentPlayTime;
    private int countdownTime;
    private boolean timrAuto = true;
    private float textSize = 10f;
    private int stockColor = Color.GRAY;
    /***************************************************************************************/

    private OnTimerChangeListener listener;

    /*************************************************************************************/

    public TimerView(Context context) {
        this(context, null, 0);
    }

    public TimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
        try {
            countdownTime = array.getInt(R.styleable.TimerView_tv_time_long, 5000);
            bgColor = array.getColor(R.styleable.TimerView_tv_bg_color, bgColor);
            circleColor = array.getColor(R.styleable.TimerView_tv_circle_color, circleColor);
            showTime = array.getBoolean(R.styleable.TimerView_tv_time_show, showTime);
            timrAuto = array.getBoolean(R.styleable.TimerView_tv_time_auto, timrAuto);
            textSize = array.getDimension(R.styleable.TimerView_tv_text_size, textSize);
            stockColor = array.getColor(R.styleable.TimerView_tv_stock_color, stockColor);
            boardWidth = array.getDimension(R.styleable.TimerView_tv_stock_size, boardWidth);
        } catch (Exception e) {
            // LogUtil.e("", e.getMessage(), e);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (!timrAuto) {

            temp = 0f;
            currentPlayTime = 0;
            postInvalidate();

            return;
        }
        animator.setFloatValues(0, 360);
        animator.setDuration(countdownTime);
        animator.addUpdateListener(animation -> {
            temp = (float) animation.getAnimatedValue();
            currentPlayTime = animation.getCurrentPlayTime();
            postInvalidate();

            if (null != listener && temp == 360) {
                listener.onTimerEnd();
            }
        });
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean clickable = isClickable();
        if (!clickable) return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setAlpha(1f);
                return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFinishTemporaryDetach() {

        if (null != animator) {
            animator.cancel();
        }

        if (null != listener) {
            listener = null;
        }

        super.onFinishTemporaryDetach();
    }

    public void clearTimer() {
        if (null != animator) {
            animator.cancel();
        }
    }

    public void startTimer() {
        animator.setFloatValues(0, 360);
        animator.setDuration(countdownTime);
        animator.addUpdateListener(animation -> {
            temp = (float) animation.getAnimatedValue();
            currentPlayTime = animation.getCurrentPlayTime();
            postInvalidate();

            if (null != listener && temp == 360) {
                listener.onTimerEnd();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        if (null == paintBg) {
            paintBg = new Paint();
            paintBg.setAntiAlias(true);
            paintBg.setFilterBitmap(true);
            paintBg.setStrokeCap(Paint.Cap.ROUND);
            paintBg.setStrokeJoin(Paint.Join.ROUND);
            paintBg.setStrokeWidth(0f);
            paintBg.setColor(bgColor);
            paintBg.setStyle(Paint.Style.FILL);
        }

        // 1. 画圆形背景
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paintBg);

        if (null == paintYh) {
            paintYh = new Paint();
            paintYh.setAntiAlias(true);
            paintYh.setFilterBitmap(true);
            paintYh.setStrokeCap(Paint.Cap.ROUND);
            paintYh.setStrokeJoin(Paint.Join.ROUND);
            paintYh.setStrokeWidth(boardWidth);
            paintYh.setColor(circleColor);
            paintYh.setStyle(Paint.Style.STROKE);
        }

        if (null == paintYh2) {
            paintYh2 = new Paint();
            paintYh2.setAntiAlias(true);
            paintYh2.setFilterBitmap(true);
            paintYh2.setStrokeCap(Paint.Cap.ROUND);
            paintYh2.setStrokeJoin(Paint.Join.ROUND);
            paintYh2.setStrokeWidth(boardWidth);
            paintYh2.setColor(stockColor);
            paintYh2.setStyle(Paint.Style.STROKE);
        }

        if (null == rectF) {
            rectF = new RectF();
        }
        rectF.left = boardWidth / 2;
        rectF.top = boardWidth / 2;
        rectF.right = getWidth() - boardWidth / 2;
        rectF.bottom = getHeight() - boardWidth / 2;

        // 2. 倒计时圆弧 - 后
        canvas.drawArc(rectF, -90, 360, false, paintYh2);

        // 3. 倒计时圆弧 - 前
        canvas.drawArc(rectF, -90, temp, false, paintYh);

        // 3. 倒计时时间
        if (showTime) {
            if (null == paintText) {
                paintText = new Paint();
                paintText.setAntiAlias(true);
                paintText.setFilterBitmap(true);
                paintText.setStrokeCap(Paint.Cap.ROUND);
                paintText.setStrokeJoin(Paint.Join.ROUND);
                paintText.setColor(Color.BLACK);
                paintText.setStyle(Paint.Style.FILL);
                paintText.setFakeBoldText(true);
                paintText.setTextSize(textSize);
                paintText.setTextAlign(Paint.Align.CENTER);
            }

            int newTime = (int) ((countdownTime - currentPlayTime) / 1000);
            String time = String.valueOf(newTime);
            Rect bounds = new Rect();
            paintText.getTextBounds(time, 0, time.length(), bounds);
            Paint.FontMetricsInt fontMetricsInt = paintText.getFontMetricsInt();
            float temp = (fontMetricsInt.bottom - fontMetricsInt.top) * 0.3f;
            canvas.drawText(time, getWidth() / 2, getHeight() / 2 + temp, paintText);
        }

        canvas.restore();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        LogUtil.e(TAG, "onVisibilityChanged ==> visibility = "+visibility);
        super.onVisibilityChanged(changedView, visibility);
    }

    public void setOnTimerChangeListener(final OnTimerChangeListener listener) {
        this.listener = listener;
    }

    public interface OnTimerChangeListener {

        void onTimerEnd();
    }
}