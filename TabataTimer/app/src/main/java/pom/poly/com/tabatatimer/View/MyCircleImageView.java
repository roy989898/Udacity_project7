package pom.poly.com.tabatatimer.View;

import android.content.Context;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 1/7/2016.
 */
public class MyCircleImageView extends CircleImageView {
    public MyCircleImageView(Context context) {
        super(context);
    }

    public MyCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //set the Height equal to the Width
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }
}
