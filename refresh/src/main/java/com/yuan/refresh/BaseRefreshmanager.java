package com.yuan.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseRefreshmanager {

    public final LayoutInflater mInflater;

    public BaseRefreshmanager(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public abstract View getHeaderView();

    public abstract void iddleRefresh();

    public abstract void refreshing();

    public abstract void downRefresh();

    public abstract void releaseRefresh();
}
