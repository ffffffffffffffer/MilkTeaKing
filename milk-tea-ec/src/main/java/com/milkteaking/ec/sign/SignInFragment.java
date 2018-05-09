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
 * @time 2018/5/8 16:25
 * @des 登录界面
 */

public class SignInFragment extends MilkTeaFragment {

    @BindView(R2.id.et_sign_in_mail)
    TextInputEditText mEtSignInMail;
    @BindView(R2.id.et_sign_in_password)
    TextInputEditText mEtSignInPassword;
    private ISignListener mSignListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mSignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.sign_in_login)
    public void clickSignInLogin() {
        if (checkForm()) {
            // 上传到服务器,根据服务器返回消息处理
            ToastUtils.showShort("上传到服务器,根据服务器返回消息处理");
            RestRxClient.build()
                    .url(Constant.SIGN_IN)
                    .params("mail", mEtSignInMail.getText().toString())
                    .params("password", mEtSignInPassword.getText().toString())
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
                            SignHelper.signIn(s, mSignListener);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            MilkTeaLogger.e("SignInLogin",e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
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
