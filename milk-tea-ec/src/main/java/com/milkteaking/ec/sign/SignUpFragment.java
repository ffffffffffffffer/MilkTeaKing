package com.milkteaking.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.milkteaking.core.fragments.MilkTeaFragment;
import com.milkteaking.core.net.RestRxClient;
import com.milkteaking.core.util.log.MilkTeaLogger;
import com.milkteaking.ec.R;
import com.milkteaking.ec.R2;
import com.milkteaking.ec.constant.Constant;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private ISignListener mSignListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
        }

    }

    @OnClick(R2.id.bt_sign_up_register)
    public void SignUpRegister() {
        if (checkForm()) {
            // 上传服务器
            ToastUtils.showShort("上传注册资料中......");
            RestRxClient.build()
                    .url(Constant.SIGN_UP)
                    .params("name", mEtSignUpName.getText().toString())
                    .params("mail", mEtSignUpMail.getText().toString())
                    .params("number", mEtSignUpPhoneNumber.getText().toString())
                    .params("password", mEtSignUpPassword.getText().toString())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull String s) {
                            SignHelper.signOn(s, mSignListener);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            MilkTeaLogger.e("SignUpRegister",e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @OnClick(R2.id.sign_up_to_sign_in)
    public void SignUpToSignIn() {
        // 跳转到登陆界面
        startWithPop(new SignInFragment());
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
