package com.stepyen.yframe.core.core.app;

import android.app.Application;

import com.google.gson.Gson;
import com.stepyen.yframe.core.core.model.IRepositoryManager;
import com.stepyen.yframe.core.core.model.RepositoryManager;
import com.stepyen.yframe.core.core.cache.Cache;
import com.stepyen.yframe.core.core.log.RequestInterceptor;

import java.io.File;
import java.util.concurrent.ExecutorService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 */
class AppComponent implements IAppComponent {
    private Application mApplication;
    private GlobalConfigModule mGlobalConfigModule;
    private AppModule mAppModule;
    private ClientModule mClientModule;
    private RepositoryManager mRepositoryManager;
    private Gson mGson;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private ExecutorService mExecutorService;
    private File mCacheFile;
    private Cache.Factory mCacheFactory;
    private Cache mCache;

    public AppComponent(Builder builder) {
        mApplication = builder.mApplication;
        mGlobalConfigModule = builder.mGlobalConfigModule;

        mAppModule = new AppModule(){};
        mClientModule = new ClientModule() {};

        mCacheFactory = mGlobalConfigModule.provideCacheFactory(mApplication);
        mCache = mAppModule.provideExtras(mCacheFactory);
        mGson = mAppModule.provideGson(mApplication, mGlobalConfigModule.provideGsonConfiguration());
        mCacheFile = mGlobalConfigModule.provideCacheFile(mApplication);
        mExecutorService = mGlobalConfigModule.provideExecutorService();
        mOkHttpClient = ClientModule.provideClient(mApplication,  mGlobalConfigModule.provideOkhttpConfiguration(),  ClientModule.provideClientBuilder(),new RequestInterceptor(),mGlobalConfigModule.provideInterceptors(), mGlobalConfigModule.provideGlobalHttpHandler(),mExecutorService);
        mRetrofit = ClientModule.provideRetrofit(mApplication, mGlobalConfigModule.provideRetrofitConfiguration(), ClientModule.provideRetrofitBuilder(), mOkHttpClient,  mGlobalConfigModule.provideBaseUrl(), mGson);

        mRepositoryManager = new RepositoryManager();
        mRepositoryManager.setRetrofit(mRetrofit);
        mRepositoryManager.setCacheFactory(mCacheFactory);

    }

    @Override
    public Application application() {
        return mApplication;
    }

    @Override
    public IRepositoryManager repositoryManager() {
        return mRepositoryManager;
    }

    @Override
    public OkHttpClient okHttpClient() {
        return mOkHttpClient;
    }

    @Override
    public Gson gson() {
        return mGson;
    }

    @Override
    public File cacheFile() {
        return mCacheFile;
    }

    @Override
    public Cache<String, Object> extras() {
        return mCache;
    }

    @Override
    public Cache.Factory cacheFactory() {
        return mCacheFactory;
    }

    @Override
    public ExecutorService executorService() {
        return mExecutorService;
    }

    @Override
    public void inject(AppDelegate delegate) {

    }

    public static class Builder implements IAppComponent.Builder {
        private Application mApplication;
        private GlobalConfigModule mGlobalConfigModule;
        @Override
        public IAppComponent.Builder application(Application application) {
            mApplication = application;
            return this;
        }

        @Override
        public IAppComponent.Builder globalConfigModule(GlobalConfigModule globalConfigModule) {
            mGlobalConfigModule = globalConfigModule;
            return this;
        }

        @Override
        public IAppComponent build() {
            return new AppComponent(this);
        }
    }
}
