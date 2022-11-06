package com.stepyen.yframe.core.core.model

import com.stepyen.yframe.core.util.YFrameUtils

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */

abstract class BaseModel<T>: IModel {

    protected var repositoryManager: RepositoryManager = YFrameUtils.getAppComponent().repositoryManager() as RepositoryManager

    protected var apiService: T? = null

    abstract fun getApiServiceClass():Class<T>

    init {
        apiService = repositoryManager.obtainRetrofitService(getApiServiceClass())
    }


    override fun onDestroy() {

    }
}