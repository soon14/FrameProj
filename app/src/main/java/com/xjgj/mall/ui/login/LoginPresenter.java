package com.xjgj.mall.ui.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.frameproj.library.util.ToastUtil;
import com.xjgj.mall.api.common.CommonApi;
import com.xjgj.mall.bean.HttpResult;
import com.xjgj.mall.bean.LoginEntity;
import com.xjgj.mall.bean.User;
import com.xjgj.mall.components.retrofit.UserStorage;
import com.squareup.otto.Bus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by we-win on 2017/7/21.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private CommonApi mCommonApi;
    private Bus mBus;
    private UserStorage mUserStorage;

    private LoginContract.View mLoginView;

    @Inject
    public LoginPresenter(CommonApi commonApi, Bus bus, UserStorage userStorage) {
        mCommonApi = commonApi;
        mBus = bus;
        mUserStorage = userStorage;
    }

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            mLoginView.showUserNameError("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.showPassWordError("请输入密码");
            return;
        }
        mLoginView.showLoading();
        disposables.add(mCommonApi.mallLogin(userName, password)
                .debounce(800, TimeUnit.MILLISECONDS)
                .map(new Function<HttpResult<LoginEntity>, LoginEntity>() {
                    @Override
                    public LoginEntity apply(@io.reactivex.annotations.NonNull HttpResult<LoginEntity> stringHttpResult) throws Exception {
                        return stringHttpResult.getResultValue();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<LoginEntity, Observable<HttpResult<User>>>() {
                    @Override
                    public Observable<HttpResult<User>> apply(@io.reactivex.annotations.NonNull LoginEntity loginEntity) throws Exception {
                        //保存token
                        mUserStorage.setToken(loginEntity.getToken());
                        return mCommonApi.mallInformation();
                    }
                }).map(new Function<HttpResult<User>, User>() {
                    @Override
                    public User apply(@io.reactivex.annotations.NonNull HttpResult<User> mallInformationEntityHttpResult) throws Exception {
                        return mallInformationEntityHttpResult.getResultValue();
                    }
                }).doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mLoginView.hideLoading();
                    }
                }).subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull User user) throws Exception {
                        if (user != null) {
                            mLoginView.loginSuccess();
                            mUserStorage.setUser(user);
                        } else {
                            ToastUtil.showToast("登录失败，请检查您的网络");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        ToastUtil.showToast("登录失败，请检查您的网络");
                    }
                }));

    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        disposables.clear();
        mLoginView = null;
    }


}