package com.milkteaking.ec.main.personal.order.comment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.core.util.callback.CallbackManager;
import com.milkteaking.core.util.callback.CallbackType;
import com.milkteaking.core.util.callback.IGlobalCallback;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ui.ui.widget.AutoPhotoLayout;
import com.milkteaking.ui.ui.widget.StarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/6/1 8:39
 * @des 订单评论Fragment
 */

public class OrderCommentFragment extends MilkTeaFragment {

    private String mThumb;
    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout;
    @BindView(R2.id.img_order_comment)
    AppCompatImageView mOrderCommentImageView;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout;

    @OnClick(R2.id.top_tv_comment_commit)
    public void clickCommentCommit() {
        int selectedStarCount = mStarLayout.getSelectedStarCount();
        ToastUtils.showShort("评论:" + selectedStarCount + "星");
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_order_comment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mThumb = arguments.getString("thumb");
        }
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        initImageView();
        mAutoPhotoLayout.setMilkTeaFragment(this);
        // 当调用相机或者选择图片时,裁剪好后,就会在PermissionCheckerDelegate类中的onActivityResult中进行处理
        // 在AutoPhotoLayout类的initAddIcon里调用了startCameraWithCheck()来选取图片或拍照
        // 添加CallbackType.NO_CROP的key,通过接口回调的方式来实现数据跳转到这里
        CallbackManager.getInstance().addCallBack(CallbackType.NO_CROP, new IGlobalCallback<Uri>() {
            @Override
            public void executeCallback(Uri args) {
                mAutoPhotoLayout.onCrop(args);
            }
        });
    }

    private void initImageView() {
        GlideApp.with(this)
                .load(mThumb)
                .into(mOrderCommentImageView);
    }
}
