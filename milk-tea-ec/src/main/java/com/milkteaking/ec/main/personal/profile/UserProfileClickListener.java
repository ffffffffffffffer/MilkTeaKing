package com.milkteaking.ec.main.personal.profile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.util.date.DateDialogUtil;
import com.milkteaking.ec.R;
import com.milkteaking.ec.main.personal.settings.NameFragment;

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
