package com.milkteaking.ec.main.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ToastUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ui.banners.ImageHolderCreator;
import com.milkteaking.ui.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/18 12:16
 * @des 商品详情Fragment
 */

public class IndexDetailsFragment extends MilkTeaFragment implements AppBarLayout.OnOffsetChangedListener {
    private static final String DETAIL_GOODS_TAG = "detail_goods_tag";
    private int mId;
    // 能折叠的布局
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBarLayout;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    // 中间部分
    @BindView(R2.id.detail_banner)
    ConvenientBanner mConvenientBanner;
    @BindView(R2.id.frame_goods_info)
    ContentFrameLayout mContentFrameLayout;

    // toolbar部分
    @BindView(R2.id.test_toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_detail_title_text)
    IconTextView mDetailTitleText;

    // ViewPager
    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    // 底部
    @BindView(R2.id.rl_favor)
    RelativeLayout mRelativeLayoutFavorite;
    @BindView(R2.id.rl_shop_cart)
    RelativeLayout mRelativeLayoutShopCart;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRelativeLayoutAddShopCart;

    public static IndexDetailsFragment create(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(DETAIL_GOODS_TAG, id);
        IndexDetailsFragment indexDetailsFragment = new IndexDetailsFragment();
        indexDetailsFragment.setArguments(arguments);
        return indexDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mId = arguments.getInt(DETAIL_GOODS_TAG);
            ToastUtils.showShort("商品: " + mId);
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_index_details;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 设置滑动关联的View时,CollapsingToolbarLayout内部组件的变化颜色
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBarLayout.addOnOffsetChangedListener(this);
        // 初始化数据
        initData();
    }

    private void initData() {
        RestClient.builder()
                .url(Constant.INDEX_GOODS_INFO)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {

                    }
                })
                .build()
                .get();
    }

    @SuppressWarnings("unchecked")
    private void initBanner(JSONObject data) {
        // 取出json里面的banner图片路径,并用集合储存起来
        JSONArray banner = data.getJSONArray("banner");
        int size = banner.size();
        List<String> banners = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String image = banner.getString(i);
            banners.add(image);
        }

        mConvenientBanner
                .setPages(new ImageHolderCreator(), banners)
                .setPageIndicator(new int[]{R.drawable.launcher_dot_normal, R.drawable.launcher_dot_fouce})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
