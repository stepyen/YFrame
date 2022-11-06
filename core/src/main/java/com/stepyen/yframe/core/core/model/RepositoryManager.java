package com.stepyen.yframe.core.core.model;

import com.stepyen.yframe.core.core.cache.Cache;
import com.stepyen.yframe.core.core.cache.CacheType;
import com.stepyen.yframe.core.util.Preconditions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Single;
import rx.functions.Func0;

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link IModel} 层必要的 Api 做数据处理
 */

public class RepositoryManager implements IRepositoryManager {

    private Retrofit mRetrofit;

    private Cache<String, Object> mRetrofitServiceCache;

    private Cache.Factory mCacheFactory;


    public RepositoryManager() {
    }

    public void setRetrofit(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public void setCacheFactory(Cache.Factory cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T createWrapperService(Class<T> serviceClass) {
        Preconditions.checkNotNull(serviceClass, "serviceClass == null");

        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {
                        // 此处在调用 serviceClass 中的方法时触发

                        if (method.getReturnType() == Observable.class) {
                            // 如果方法返回值是 Observable 的话，则包一层再返回，
                            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
                            // 如此对原项目的影响为 0，且更可控。

                            Observable.defer(new Func0<Observable<T>>() {
                                @Override
                                public Observable<T> call() {
                                    final T service = getRetrofitService(serviceClass);
                                    // 执行真正的 Retrofit 动态代理的方法
                                    try {
                                        return ((Observable) getRetrofitMethod(service, method)
                                                .invoke(service, args));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                        return null;
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                        return null;
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                        return null;
                                    }

                                }
                            });
                        } else if (method.getReturnType() == Single.class) {
                            // 如果方法返回值是 Single 的话，则包一层再返回。
                            Single.defer(new Func0<Single<T>>() {
                                @Override
                                public Single<T> call() {
                                    final T service = getRetrofitService(serviceClass);
                                    // 执行真正的 Retrofit 动态代理的方法
                                    try {
                                        return ((Single) getRetrofitMethod(service, method)
                                                .invoke(service, args));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            });
                        }

                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }


    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {

    }

}
