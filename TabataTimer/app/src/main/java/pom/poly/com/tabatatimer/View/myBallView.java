package pom.poly.com.tabatatimer.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import pom.poly.com.tabatatimer.R;

/**
 * TODO: document your custom view class.
 */
public class myBallView extends View {


    private int mOnColor;
    private int mOffColor;
    private boolean mSetOnOff;


    private Paint mBallPaint;
    private float mCircleRadius;

    public myBallView(Context context) {
        super(context);
        init(null, 0);
    }

    public myBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }


    public myBallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public float getmCircleRadius() {
        return mCircleRadius;
    }

    public void setmCircleRadius(float mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
        invalidate();
        requestLayout();
    }

    public int getmOffColor() {
        return mOffColor;
    }

    public void setmOffColor(int mOffColor) {
        this.mOffColor = mOffColor;
        invalidate();
        requestLayout();
    }

    public int getmOnColor() {
        return mOnColor;
    }

    public void setmOnColor(int mOnColor) {
        this.mOnColor = mOnColor;
        invalidate();
        requestLayout();
    }

    public boolean ismSetOnOff() {
        return mSetOnOff;
    }

    public void setmSetOnOff(boolean mSetOnOff) {
        this.mSetOnOff = mSetOnOff;
        invalidate();
        requestLayout();
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.myBallView, defStyle, 0);

        try {
            mOffColor = a.getResourceId(R.styleable.myBallView_offColor, Color.BLUE);
            mOnColor = a.getResourceId(R.styleable.myBallView_onColor, Color.RED);

            mSetOnOff = a.getBoolean(R.styleable.myBallView_setOnOff, false);
            mCircleRadius = a.getDimension(R.styleable.myBallView_circularRadius, 0);


        } finally {
            a.recycle();
        }


        mBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    private int measureHeight(int measureSpec) {
        //determine height
        int size = getPaddingTop() + getPaddingBottom();

        size += mCircleRadius * 2;

        return resolveSizeAndState(size, measureSpec, 0);


    }

    private int measureWidth(int measureSpec) {
        //determine width
        int size = getPaddingLeft() + getPaddingRight();

        size += mCircleRadius * 2;

        return resolveSizeAndState(size, measureSpec, 0);
    }

    private float getMaxinunRadius() {
        float x_radius = (getHeight() - getPaddingBottom() - getPaddingTop()) / 2f;
        float y_radius = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2f;
        return Math.min(x_radius, y_radius);

    }

    private float getVerticalCenter() {
        return getPaddingTop() + (getHeight() - getPaddingBottom() - getPaddingTop()) / 2f;
    }


    private float getHorizontalCenter() {
        return getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private void drawBall(Canvas canvas) {
        float x = getHorizontalCenter();
        float y = getVerticalCenter();
        if (ismSetOnOff()) {
            mBallPaint.setColor(getmOnColor());
        } else {
            mBallPaint.setColor(getmOffColor());
        }

        if (getmCircleRadius() <= 0) {
            canvas.drawCircle(x, y, getMaxinunRadius(), mBallPaint);
        } else {
            canvas.drawCircle(x, y, Math.min(getMaxinunRadius(), getmCircleRadius()), mBallPaint);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBall(canvas);
    }


}
