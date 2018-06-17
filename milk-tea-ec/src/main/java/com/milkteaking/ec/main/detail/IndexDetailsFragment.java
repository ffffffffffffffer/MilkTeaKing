package com.milkteaking.ec.main.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.detail.tab.TabPagerAdapter;
import com.milkteaking.ui.anotation.BezierAnimation;
import com.milkteaking.ui.anotation.BezierUtil;
import com.milkteaking.ui.banners.ImageHolderCreator;
import com.milkteaking.ui.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/18 12:16
 * @des 商品详情Fragment
 */

public class IndexDetailsFragment extends MilkTeaFragment implements AppBarLayout.OnOffsetChangedListener, BezierUtil
        .AnimationListener {
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
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;

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
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart;
    // 添加购物车时动画显示的图片
    private String mThumbImageUrl;
    // 购物车数量
    private int mShopCartCount;

    public static IndexDetailsFragment create(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt(DETAIL_GOODS_TAG, id);
        IndexDetailsFragment indexDetailsFragment = new IndexDetailsFragment();
        indexDetailsFragment.setArguments(arguments);
        return indexDetailsFragment;
    }

    @OnClick(R2.id.rl_add_shop_cart)
    public void clickAddShopCart() {
        CircleImageView imageView = new CircleImageView(getContext());
        // 这个限制了图片的大小
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
                .override(100, 100);
        // 加载图片
        GlideApp.with(getContext())
                .load(mThumbImageUrl)
                .apply(options)
                .into(imageView);

        /*
            start: 从那个View开始移动
            end:   移动到那个view停止消失
         */
        BezierAnimation.addCart(this, mRelativeLayoutAddShopCart, mIconShopCart, imageView, this);
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
        // 初始化tab布局
        initTabLayout();
        // 设置购物车的原形textView为红色
        mCircleTextView.setCircleBackgroundColor(Color.RED);
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.we_chat_black));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        RestClient.builder()
                .url(Constant.INDEX_GOODS_INFO)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initViewPager(data);
                        setShopCartCount(data);
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

    private void initViewPager(JSONObject data) {
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void initGoodsInfo(JSONObject data) {
        String dataInfo = data.toJSONString();
        GoodsInfoDetailFragment goodsInfoDetailFragment = GoodsInfoDetailFragment.create(dataInfo);
        // 将contentFragment填充进来
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info, goodsInfoDetailFragment);
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

    public void setShopCartCount(JSONObject shopCartCount) {
        mThumbImageUrl = shopCartCount.getString("thumb");
        if (mShopCartCount == 0) {
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationEnd() {
        // 比例放大动画
        YoYo.with(new ScaleUpAnimator())
                .duration(5000)
                .playOn(mIconShopCart);
        mCircleTextView.setVisibility(View.GONE);
        mCircleTextView.setText(String.valueOf(2));
        RestClient.builder()
                // http://服务器地址/add_shop_cart_count
                .url(Constant.INDEX)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mShopCartCount++;
                        mCircleTextView.setVisibility(View.VISIBLE);
                        mCircleTextView.setText(String.valueOf(mShopCartCount));
                    }
                })
                .failed(new IFailed() {
                    @Override
                    public void onFailed(Throwable t) {
                        ToastUtils.showShort(t.getMessage());
                    }
                })
                .build()
                .get();
    }
}
