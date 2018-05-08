package com.milkteaking.ec.sign;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TanJJ
 * @time 2018/5/8 16:25
 * @des 登录界面
 */

public class SignInFragment extends MilkTeaFragment {

    @BindView(R2.id.et_sign_in_mail)
    TextInputEditText mEtSignInMail;
    @BindView(R2.id.et_sign_in_password)
    TextInputEditText mEtSignInPassword;

    @OnClick(R2.id.sign_in_login)
    public void clickSignInLogin() {
        if (checkForm()) {
            // 上传到服务器,根据服务器返回消息处理
            ToastUtils.showShort("上传到服务器,根据服务器返回消息处理");
        }
    }

    @OnClick(R2.id.sign_in_to_sign_up)
    public void clickSignInToSignUp() {
        // 跳转到注册界面
        startWithPop(new SignUpFragment());
    }

    @OnClick(R2.id.sign_in_we_chat)
    public void clickSignInWeChat() {

    }

    private boolean checkForm() {
        boolean isOk = true;

        String mail = mEtSignInMail.getText().toString();
        String password = mEtSignInPassword.getText().toString();

        if (mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mEtSignInMail.setError("邮箱地址错误");
            isOk = false;
        } else {
            mEtSignInMail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mEtSignInPassword.setError("请填写至少6位的密码");
            isOk = false;
        } else {
            mEtSignInPassword.setError(null);
        }


        return isOk;
    }


    @Override
    public Object getLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }
}
