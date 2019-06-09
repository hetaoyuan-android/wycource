package com.news.today.todayinformation.main.hangzhou.jike;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.news.today.todayinformation.R;
import com.news.today.todayinformation.main.tools.SystemUtil;


public class LiKeClickView extends View {

    private boolean isLike;
    private Bitmap likeBitmap;
    private Bitmap unLikeBitmap;
    private Bitmap shiningBitmap;
    private Paint bitmapPaint;
    private int left;
    private int top;
    private float handScale = 1.0f;
    private int centerX;
    private int centerY;

    public LiKeClickView(Context context) {
        super(context);
        init();
    }


    public LiKeClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiKeClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JiKeLikeView);
        isLike = typedArray.getBoolean(R.styleable.JiKeLikeView_is_like, false);
        typedArray.recycle();
        init();
    }

    private void init() {
        Resources resources = getResources();
        likeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like);
        unLikeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_unlike);
        shiningBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like_shining);
        bitmapPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = 0;
        int measureHeight = 0;
        int maxHeight = unLikeBitmap.getHeight() + SystemUtil.dp2px(getContext(), 20);
        int maxWidth = unLikeBitmap.getHeight() + SystemUtil.dp2px(getContext(), 30);
        //拿到当前控件的测量模式
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (mode != MeasureSpec.EXACTLY) {
            // 测量模式未指定   如果有背景 背景有多大，我们这个控件就有多大
            int suggestdMininmuWidth = getSuggestedMinimumWidth();
            int suggestedMininumHeight = getSuggestedMinimumHeight();
            if (suggestdMininmuWidth == 0) {
                measureWidth = maxWidth;
            } else {
                measureWidth = Math.min(suggestdMininmuWidth, maxWidth);
            }
            if (suggestedMininumHeight == 0) {
                measureHeight = maxHeight;
            } else {
                measureHeight = Math.min(suggestedMininumHeight, maxHeight);
            }
        } else {
            // 测量模式指定，根据用户大小来判断
            measureWidth = Math.min(maxWidth, widthSize);
            measureHeight = Math.min(maxHeight, heightSize);
        }

        setMeasuredDimension(measureWidth, measureHeight);

        getPading(measureWidth, measureHeight);

    }

    private void getPading(int measureWidth, int measureHeight) {
        int bitmapWidth = likeBitmap.getWidth();
        int bitmapHeight = likeBitmap.getHeight();
        left = (measureWidth - bitmapWidth) / 2;
        top = (measureHeight - bitmapHeight) / 2;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        centerX = width / 2;
        centerY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap handBitmap = isLike ? likeBitmap : unLikeBitmap;
        // 使用canvas scale 及其他效果方法时，必须先调用save，然后再调用restore，这两个方法成对出现
        canvas.save();
        canvas.scale(handScale, handScale, centerX, centerY);
        canvas.drawBitmap(handBitmap, left, top, bitmapPaint);
        if (isLike) {
            canvas.drawBitmap(shiningBitmap, left + 10, 0, bitmapPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onclick();
                break;
            default:
                break;
        }
        return true;
    }

    private void onclick() {
        isLike = !isLike;
        // handScale必须要有一个set方法setHandScale
        ObjectAnimator handScale = ObjectAnimator.ofFloat(this, "handScale", 1.0f, 0.8f, 1.0f);
        handScale.setDuration(250);
        handScale.start();
        invalidate();
    }

    // 属性方法
    public void setHandScale(float value) {
        this.handScale = value;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        likeBitmap.recycle();
        unLikeBitmap.recycle();
        shiningBitmap.recycle();
    }
}
