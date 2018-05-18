package com.milkteaking.ui.recycler;


import com.google.auto.value.AutoValue;

/**
 * @author TanJJ
 * @time 2018/5/18 8:38
 * @des rgb值
 */
//AutoValue框架可以自动生成样板代码(get/set..等等)(与AutoService一样会自动生成代码)，解放我们的双手，让我们把更多的精力放在更重要的事情上
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int blue();

    public abstract int green();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
