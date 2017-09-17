package com.wzx.contractmvp.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 描述 在view底部绘制矩形
 * Created by 王治湘 on 2017/9/17.
 * version 1.0
 */

public class IndicatorDrawable extends Drawable {
    private Paint mPaint;
    private View mView;
    private int mLineHeight = 2;

    /**
     *
     * @param view
     * @param color
     */
    public IndicatorDrawable(@NonNull View view, @ColorInt int color){
        mView = view;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }

    /**
     *
     * @param view
     * @param color
     * @param lineHeight
     */
    public IndicatorDrawable(@NonNull View view, @ColorInt int color, @NonNull int lineHeight){
        mView = view;
        this.mLineHeight = lineHeight;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        /*
         * 矩形位置
         * start x:0
         * start y:view的高度减去线高
         * end x:view的宽度
         * end y:view的高度
         */
        canvas.drawRect(0, mView.getHeight() - mLineHeight, mView.getWidth(), mView.getHeight(), mPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
