package com.stepyen.yframe.app;

import android.app.Application;
import android.content.Context;

import com.stepyen.yframe.api.Api;
import com.stepyen.yframe.core.core.IConfigModule;
import com.stepyen.yframe.core.core.app.GlobalConfigModule;
import com.stepyen.yframe.core.core.app.IAppLifecycles;
import com.stepyen.yframe.core.core.log.RequestInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * date：2019/5/22
 * author：stepyen
 * description：
 */
public class GlobalConfiguration implements IConfigModule {


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {
        //Release 时, 让框架不再打印 Http 请求和响应的信息
        builder.printHttpLogLevel(RequestInterceptor.Level.NONE);

        builder
                .baseurl(Api.EXPERT_BASE_URL)
                .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置 Okhttp 的参数
//                    okhttpBuilder.sslSocketFactory(); //支持 Https, 详情请百度
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
//                    ProgressManager.getInstance().with(okhttpBuilder);
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(okhttpBuilder);
                });
    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<IAppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
