package com.stepyen.yframe.api

import com.stepyen.yframe.bean.ArticleListWrap
import com.stepyen.yframe.bean.BaseResponse
import kotlinx.coroutines.flow.Flow

import retrofit2.http.GET

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */
interface MainApiService{


    @GET(Api.ARTICLE_LIST)
    fun getArticleList(): Flow<BaseResponse<ArticleListWrap>>



}