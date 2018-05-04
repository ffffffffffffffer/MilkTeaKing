package com.example.milkteaking;


import com.milkteaking.core.activitys.ProxyActivity;
import com.milkteaking.core.fragments.MilkTeaFragment;

public class MainActivity extends ProxyActivity {

    @Override
    public MilkTeaFragment getRootFragment() {
        // 返回根Fragment
        return new RootFragment();
    }
}
