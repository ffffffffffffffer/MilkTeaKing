package com.milkteaking.core.fragments.bottom;

import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;

/**
 * @author TanJJ
 * @time 2018/5/9 13:29
 */

public abstract class BottomItemFragment extends MilkTeaFragment implements View.OnKeyListener {

    private long mExitTime = 0;

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || event.getAction() == KeyEvent.KEYCODE_BACK) {
            int exitTime = 2000;
            if (System.currentTimeMillis() - mExitTime > exitTime) {
                // 提示双击退出
                ToastUtils.showShort("双击退出!");
                mExitTime = System.currentTimeMillis();
            } else {
                // 退出程序
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
                return true;
            }
        }
        return false;
    }
}
