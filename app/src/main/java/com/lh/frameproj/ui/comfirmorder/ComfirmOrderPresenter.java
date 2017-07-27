package com.lh.frameproj.ui.comfirmorder;

import android.support.annotation.NonNull;

import com.android.frameproj.library.util.ToastUtil;
import com.lh.frameproj.api.common.CommonApi;
import com.lh.frameproj.bean.HttpResult;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by we-win on 2017/7/27.
 */

public class ComfirmOrderPresenter  implements ComfirmOrderContract.Presenter {

    private ComfirmOrderContract.View mView;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private CommonApi mCommonApi;

    @Inject
    public ComfirmOrderPresenter(CommonApi commonApi) {
        mCommonApi = commonApi;
    }

    @Override
    public void orderSubmit(String serviceTime, String volume, String weight, String serviceType,
                            String carType, String remark, String counts, String address, String submitType) {
        mView.showLoading();
        disposables.add(mCommonApi.orderSubmit(serviceTime,volume,weight,serviceType,carType,remark,counts,address,submitType)
                .debounce(800, TimeUnit.MILLISECONDS)
                .map(new Function<HttpResult<Void>, Void>() {
                    @Override
                    public Void apply(@io.reactivex.annotations.NonNull HttpResult<Void> voidHttpResult) throws Exception {
                        return voidHttpResult.getResultValue();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Void aVoid) throws Exception {
                        mView.submitSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        ToastUtil.showToast("提交失败，请检查您的网络");
                    }
                }));
    }

    @Override
    public void attachView(@NonNull ComfirmOrderContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        disposables.clear();
        mView = null;
    }

}
