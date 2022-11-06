package com.stepyen.yframe.core.core.app

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.stepyen.yframe.core.core.cache.Cache
import com.stepyen.yframe.core.core.cache.CacheType

/**
 * ================================================
 * 提供一些框架必须的实例的
 */
abstract class AppModule {

    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }

    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
        val builder = GsonBuilder()
        configuration?.configGson(application, builder)
        return builder.create()
    }

    fun provideExtras(cacheFactory: Cache.Factory): Cache<String, Any> {
        return cacheFactory.build(CacheType.EXTRAS) as Cache<String, Any>
    }
}