package com.xjgj.mall.ui.register;

import com.xjgj.mall.injector.PerActivity;
import com.xjgj.mall.injector.component.ApplicationComponent;
import com.xjgj.mall.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by we-win on 2017/7/31.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class})
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}
