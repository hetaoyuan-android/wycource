package com.yuan.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GoRefreshLayout extends LinearLayout {
    private BaseRefreshmanager mRefreshManager;
    private Context mContext;
    private View mHeadView;
    private int mHeadViewHeight;
    private int minHeadViewHeight; // 头部布局最小的一个高度
    private int maxHeadViewHeight; // 头部布局最大的一个高度
    private RefreshingListener mRefreshingListener; //正在刷新的回调接口

    public GoRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public GoRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GoRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        mContext = context;
    }

    /**
     * 开启下拉刷新， 下拉刷新的效果是默认的
     */
    public void setRefreshManager() {
        mRefreshManager = new DeafultRefreshManager(mContext);
        intHeaderView();
    }

    /**
     * 开启下拉刷新， 下拉刷新的效果是自定义
     *
     * @param manager
     */
    public void setRefreshManager(BaseRefreshmanager manager) {
        mRefreshManager = manager;
        intHeaderView();
    }


    private void intHeaderView() {
        Log.i("hetaorefresh", "intHeaderView:");
        setOrientation(VERTICAL);
        mHeadView = mRefreshManager.getHeaderView();
        mHeadView.measure(0, 0);
        mHeadViewHeight = mHeadView.getMeasuredHeight();
        minHeadViewHeight = -mHeadViewHeight;
        maxHeadViewHeight = (int) (mHeadViewHeight * 0.3f);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeadViewHeight);
        params.topMargin = minHeadViewHeight;
        addView(mHeadView, 0, params);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("hetaorefresh", "MotionEvent:");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                performClick();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getY();
                int dy = moveY - downY;
                Log.i("hetaorefresh", "dy:" + dy);
                if (dy > 0) {
                    LayoutParams layoutParams = getHeadViewLayoutParams();
                    int topMargin = (int) Math.min(dy / 1.8f + minHeadViewHeight, maxHeadViewHeight);

                    if (topMargin < 0 && mCurrentRefreshState != RefreshState.DOWNREFRESH) {
                        mCurrentRefreshState = RefreshState.DOWNREFRESH;
                        //提示下拉刷新的一个状态
                        handleRefreshState(mCurrentRefreshState);
                    } else if (topMargin >= 0 && mCurrentRefreshState != RefreshState.RELEASEREFRESH) {
                        mCurrentRefreshState = RefreshState.RELEASEREFRESH;
                        //提示释放刷新的一个状态
                        handleRefreshState(mCurrentRefreshState);
                    }
                    //阻尼效果
                    layoutParams.topMargin = topMargin;
                    mHeadView.setLayoutParams(layoutParams);
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (handleEventUp(event)) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean handleEventUp(MotionEvent event) {
        final LayoutParams layoutParams = getHeadViewLayoutParams();
        if (mCurrentRefreshState == RefreshState.DOWNREFRESH) {
           hideHeadView(layoutParams);
        } else if (mCurrentRefreshState == RefreshState.RELEASEREFRESH) {
            layoutParams.topMargin = 0;
            mHeadView.setLayoutParams(layoutParams);
            mCurrentRefreshState = RefreshState.REFRESHING;
            handleRefreshState(mCurrentRefreshState);
            if (mRefreshingListener != null) {
                mRefreshingListener.onRefreshing();
            }
        }
        return layoutParams.topMargin > minHeadViewHeight;
    }

    private LayoutParams getHeadViewLayoutParams() {
        return (LayoutParams) mHeadView.getLayoutParams();
    }

    /**
     * 刷新完成后的操作
     */
    public void refreshOver() {
        hideHeadView(getHeadViewLayoutParams());
    }

    private void hideHeadView(final LayoutParams layoutParams) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, minHeadViewHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                layoutParams.topMargin = animatedValue;
                mHeadView.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentRefreshState = RefreshState.IDDLE;
                handleRefreshState(mCurrentRefreshState);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private void handleRefreshState(RefreshState mCurrentRefreshState) {
        switch (mCurrentRefreshState) {
            case IDDLE:
                mRefreshManager.iddleRefresh();
                break;
            case REFRESHING:
                mRefreshManager.refreshing();
                break;
            case DOWNREFRESH:
                mRefreshManager.downRefresh();
                break;
            case RELEASEREFRESH:
                mRefreshManager.releaseRefresh();
                break;
            default:
                break;
        }
    }

    private RefreshState mCurrentRefreshState = RefreshState.IDDLE;
    //定义下拉刷新的状态 ，依次为  静止、下拉刷新、释放刷新、正在刷新、刷新完成

    private enum RefreshState {
        IDDLE, DOWNREFRESH, RELEASEREFRESH, REFRESHING
    }


    public interface RefreshingListener {
        void onRefreshing();
    }

    public void setRefreshListener( RefreshingListener refreshListener) {
        this.mRefreshingListener = refreshListener;
    }

}
