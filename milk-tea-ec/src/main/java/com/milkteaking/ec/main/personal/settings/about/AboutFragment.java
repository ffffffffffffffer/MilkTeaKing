package com.milkteaking.ec.main.personal.settings.about;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestClient;
import com.milkteaking.core.net.callback.IFailed;
import com.milkteaking.core.net.callback.ISuccess;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author TanJJ
 * @time 2018/5/31 16:41
 * @des 关于Fragment
 */

public class AboutFragment extends MilkTeaFragment {

    @BindView(R2.id.tv_info)
    AppCompatTextView mTvInfo;

    @Override
    public Object getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 访问服务器
        RestClient.builder()
                .url(Constant.PERSONAL_SETTING_ABOUT)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        String data = jsonObject.getString("data");
                        mTvInfo.setText(data);
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

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
