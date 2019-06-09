package com.news.today.todayinformation.main.hangzhou.view;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AnimationUtils;

import com.news.today.todayinformation.R;
import com.news.today.todayinformation.base.BaseFragment;
import com.news.today.todayinformation.base.ViewInject;
import com.news.today.todayinformation.main.hangzhou.adapter.ZhihuiAdapter;
import com.news.today.todayinformation.main.shanghai.dto.ShangHaiDetailBean;
import com.news.today.todayinformation.main.shanghai.lf.IShanghaiDetailContract;
import com.news.today.todayinformation.main.shanghai.presenter.ShanghaiDetailPresenter;

import butterknife.BindView;
import butterknife.Unbinder;

@ViewInject(mainlayoutid = R.layout.fragment_zhihu)
public class ZhiHuFragment extends BaseFragment implements IShanghaiDetailContract.Iview {
    IShanghaiDetailContract.IPresenter mPresenter = new ShanghaiDetailPresenter(this);
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.zhihu_app_barlayot)
    AppBarLayout zhihuAppBarlayot;
    @BindView(R.id.zhihu_recyclerview)
    RecyclerView zhihuRecyclerview;
    Unbinder unbinder;

    @Override
    public void afterBindView() {
        zhihuRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        zhihuRecyclerview.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.zhihu_recyclerview_show));
        mPresenter.getNetData(20);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(ShangHaiDetailBean data) {
        if (zhihuRecyclerview.getAdapter() == null) {
            ZhihuiAdapter zhihuiAdapter = new ZhihuiAdapter(data.result.data);
            zhihuRecyclerview.setAdapter(zhihuiAdapter);
        }
    }
}
