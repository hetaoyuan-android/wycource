package com.yuan.refresh;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DeafultRefreshManager extends BaseRefreshmanager {
    private TextView mTvRefresh;

    public DeafultRefreshManager(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        View inflate = mInflater.inflate(R.layout.ulti_header_layout, null, false);
        mTvRefresh = inflate.findViewById(R.id.header_text);
        return inflate;
    }

    @Override
    public void iddleRefresh() {
        mTvRefresh.setText("下拉刷新");
    }

    @Override
    public void refreshing() {

    }

    @Override
    public void downRefresh() {
        mTvRefresh.setText("下拉刷新");
    }

    @Override
    public void releaseRefresh() {
        mTvRefresh.setText("释放刷新");
    }
}
