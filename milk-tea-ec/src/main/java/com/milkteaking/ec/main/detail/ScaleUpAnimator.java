package com.milkteaking.ec.main.detail;

import android.animation.ObjectAnimator;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;


/**
 * @author TanJJ
 * @time 2018/6/17 21:46
 * @des 按比例缩放
 */

public class ScaleUpAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                // ObjectAnimator底层会根据传入的字符串scaleX通过反射的方式来找到对应的类来执行动画
                ObjectAnimator.ofFloat(target, "scaleX", 0.8f, 1.0f, 1.4f, 1.2f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.8f, 1.0f, 1.4f, 1.2f, 1)
        );
    }
}
