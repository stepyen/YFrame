package com.stepyen.yframe;

import android.view.View;

import com.stepyen.yframe.core.gloading.Gloading;

/**
 * date：2019/4/15
 * author：stepyen
 * description：
用法


 var gloadingHold = Gloading.getDefault().wrap(this)
 gloadingHold.withRetry(Runnable {
 ToastUtils.toast("开始重试")
 Handler().postDelayed(Runnable {
 gloadingHold.showLoadSuccess()
 },1000)
 })


 gloadingHold.showLoading()

 Handler().postDelayed(Runnable {
 gloadingHold.showLoadFailed()
 },2000)






 *
 */
public class GloadingAdapter implements Gloading.Adapter {

    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {


        GloadingStatusView loadingStatusView = null;
        //reuse the old view, if possible
        if (convertView != null && convertView instanceof GloadingStatusView) {
            loadingStatusView = (GloadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GloadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);

        return loadingStatusView;
    }

}
