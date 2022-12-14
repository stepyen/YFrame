package com.stepyen.yframe.core.core.activity;
import android.app.Activity;
import android.os.Bundle;

import com.stepyen.yframe.core.core.app.IAppComponent;
import com.stepyen.yframe.core.core.cache.Cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * ================================================
 * 框架要求框架中的每个 {@link Activity} 都需要实现此类,以满足规范
 *
 */
public interface IActivity {

    /**
     * 是否使用 EventBus
     * 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true}, Arms 会自动注册 EventBus
     */
    boolean isUseEventBus();

    /**
     * 获取布局id
     *
     * @return
     */
    int getLayoutId();

    void initData(@Nullable Bundle savedInstanceState);

}
