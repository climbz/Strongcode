package com.climbz.strongcode;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareScaleImg extends ImageView {
    public SquareScaleImg(Context context)
    {
        super(context);
    }

    public SquareScaleImg(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareScaleImg(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}

