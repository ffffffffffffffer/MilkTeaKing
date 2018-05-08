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
 * @time 2018/5/8 15:04
 * @des 注册界面
 */

public class SignUpFragment extends MilkTeaFragment {

    @BindView(R2.id.et_sign_up_name)
    TextInputEditText mEtSignUpName;

    @BindView(R2.id.et_sign_up_mail)
    TextInputEditText mEtSignUpMail;

    @BindView(R2.id.et_sign_up_phone_number)
    TextInputEditText mEtSignUpPhoneNumber;

    @BindView(R2.id.et_sign_up_password)
    TextInputEditText mEtSignUpPassword;

    @BindView(R2.id.et_sign_up_re_password)
    TextInputEditText mEtSignUpRePassword;

    @OnClick(R2.id.bt_sign_up_register)
    public void SignUpRegister() {
        if (checkForm()) {
            // 上传服务器
            ToastUtils.showShort("上传注册资料中......");
        }
    }

    @OnClick(R2.id.sign_up_to_sign_in)
    public void SignUpToSignIn() {
        // 跳转到登陆界面
    }

    private boolean checkForm() {
        boolean isOk = true;
        String name = mEtSignUpName.getText().toString();
        String mail = mEtSignUpMail.getText().toString();
        String phoneNumber = mEtSignUpPhoneNumber.getText().toString();
        String password = mEtSignUpPassword.getText().toString();
        String rePassword = mEtSignUpRePassword.getText().toString();

        if (name.isEmpty()) {
            isOk = false;
            mEtSignUpName.setError("姓名不能为空!");
        } else {
            mEtSignUpName.setError(null);
        }

        if (mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            isOk = false;
            mEtSignUpMail.setError("请输入正确的邮箱地址");
        } else {
            mEtSignUpMail.setError(null);
        }

        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            isOk = false;
            mEtSignUpPhoneNumber.setError("请输入正确的手机号码");
        } else {
            mEtSignUpName.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 && password.length() > 12) {
            isOk = false;
            mEtSignUpPassword.setError("请输入最少6位,最多12位的密码");
        } else {
            mEtSignUpPassword.setError(null);
        }

        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            isOk = false;
            mEtSignUpRePassword.setError("两次密码不一致");
        } else {
            mEtSignUpRePassword.setError(null);
        }

        return isOk;
    }

    @Override
    public Object getLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }
}
