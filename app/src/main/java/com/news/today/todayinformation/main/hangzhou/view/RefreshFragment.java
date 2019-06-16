package com.news.today.todayinformation.main.hangzhou.view;

import com.news.today.todayinformation.R;
import com.news.today.todayinformation.base.BaseFragment;
import com.news.today.todayinformation.base.ViewInject;
import com.yuan.refresh.GoRefreshLayout;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_refresh)
public class RefreshFragment extends BaseFragment {

    @BindView(R.id.god_refresh)
    GoRefreshLayout goRefresh;

    @Override
    public void afterBindView() {
        goRefresh.setRefreshManager();
        goRefresh.setRefreshListener(new GoRefreshLayout.RefreshingListener() {
            @Override
            public void onRefreshing() {
                goRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goRefresh.refreshOver();
                    }
                }, 2000);
            }
        });
    }
}
