package com.wzx.contractmvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.wzx.contractmvp.R;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/15.
 * version 1.0
 */

public class TabView extends AppCompatTextView {
    private static final int STROKE_WIDTH = 2;
    private int borderCol;

    private Paint borderPaint;

    public TabView(Context context){
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TabView, 0, 0);
        try {
            borderCol = a.getInteger(R.styleable.TabView_bottomLineColor, 000);//0 is default
        } finally {
            a.recycle();
        }

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(STROKE_WIDTH);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        if (0 == this.getText().toString().length())
//            return;

        borderPaint.setColor(borderCol);


        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();

//        RectF r = new RectF(0, 0, 0, h - 1);
        RectF r = new RectF(2, 2, w - 2, h - 2);
        canvas.drawRoundRect(r, 5, 5, borderPaint);
        super.onDraw(canvas);
    }

    public int getBottomLineColor() {
        return borderCol;
    }

    public void setBottomLineColor(int newColor) {
        borderCol = newColor;
        invalidate();
        requestLayout();
    }
}
