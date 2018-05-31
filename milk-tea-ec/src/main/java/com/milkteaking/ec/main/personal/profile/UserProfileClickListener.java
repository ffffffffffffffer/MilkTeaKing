package com.milkteaking.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.core.ui.image.GlideApp;
import com.milkteaking.core.util.callback.CallbackManager;
import com.milkteaking.core.util.callback.CallbackType;
import com.milkteaking.core.util.callback.IGlobalCallback;
import com.milkteaking.core.util.date.DateDialogUtil;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.constant.Constant;
import com.milkteaking.ec.main.personal.settings.NameFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author TanJJ
 * @time 2018/5/27 14:10
 * @des 用户信息的点击监听
 */

public class UserProfileClickListener extends SimpleClickListener {

    private MilkTeaFragment mMilkTeaFragment;
    CharSequence[] genders = new CharSequence[]{"男", "女", "保密"};

    public UserProfileClickListener(MilkTeaFragment fragment) {
        mMilkTeaFragment = fragment;
    }

    @Override
    public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
        ToastUtils.showShort(String.valueOf(position));
        switch (position) {
            case 0:
                // 注册裁剪监听
                CallbackManager.getInstance().addCallBack(CallbackType.NO_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(Uri args) {
                        if (args != null) {
                            // 更新图片
                            CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id
                                    .img_arrow_avatar);
                            GlideApp.with(mMilkTeaFragment.getContext())
                                    .load(args)
                                    .into(circleImageView);

                            // 上传头像到服务器
                            RestClient.builder()
                                    .url(UploadConfig.UPLOAD_IMG)
                                    .file(args.getPath())
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            // 服务器返回上传的图片在服务器的路径
                                            // 模拟服务器返回的数据有result节点和path
                                            // String path = JSON.parseObject(response).getJSONObject("result")
                                            // .getString("path");
                                            String path = "模拟服务器返回的数据";
                                            // 通知服务器更新信息
                                            RestClient.builder()
                                                    .url(Constant.PERSONAL_USER_PROFILE)
                                                    .params("avatar", path)
                                                    .success(new ISuccess() {
                                                        @Override
                                                        public void onSuccess(String response) {
                                                            // 上传成功后服务器一般会返回最新的用户信息回来
                                                            // 获取更新后的用户信息,然后更新本地数据库
                                                            // 如果没有本地数据的app,每次打开app都请求API获取信息
                                                        }
                                                    })
                                                    .failed(new IFailed() {
                                                        @Override
                                                        public void onFailed(Throwable t) {
                                                            MilkTeaLogger.d("onUploadFailed", "上传数据出错了");
                                                        }
                                                    })
                                                    .build()
                                                    .post();
                                        }
                                    })
                                    .failed(new IFailed() {
                                        @Override
                                        public void onFailed(Throwable t) {
                                            MilkTeaLogger.d("onUploadFailed", "上传图片出错了");
                                        }
                                    })
                                    .build()
                                    .upload();
                        }
                    }
                });
                // 拍照和选择本地图片
                mMilkTeaFragment.startCameraWithCheck();
                break;
            case 1:
                // 设置名字
                NameFragment nameFragment = new NameFragment();
                mMilkTeaFragment.start(nameFragment);
                break;
            case 2:
                // 设置性别
                choiceGender(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppCompatTextView valueTextView = (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
                        valueTextView.setText(genders[which]);
                        dialog.dismiss();
                    }
                });
                break;
            case 3:
                // 弹出日期选择器
                DateDialogUtil dateUtils = new DateDialogUtil();
                dateUtils.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        AppCompatTextView valueTextView = (AppCompatTextView) view.findViewById(R.id.tv_arrow_value);
                        valueTextView.setText(date);
                    }
                });
                // 显示对话框
                dateUtils.showDateDialog(mMilkTeaFragment);
                break;
            default:
                break;
        }

    }

    private void choiceGender(DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mMilkTeaFragment.getContext());
        // 设置显示的单选数据和默认的选择,将listener抽取出来,让外面提供
        alertDialog.setSingleChoiceItems(genders, 0, listener);
        alertDialog.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
