package com.stepyen.yframe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.stepyen.yframe.core.gloading.Gloading;

/**
 * date：2022/9/15
 * author：stepyen
 * description：
 */
public class GloadingStatusView extends FrameLayout {
    private final Runnable mRetryTask;
    private View mLoadingView;
    private View mErrorView;
    private View mNetErrorView;
    private View mEmptyView;
    private Context mContext;

    public GloadingStatusView(Context context, Runnable retryTask) {
        super(context);
        this.mContext = context;
        this.mRetryTask = retryTask;
    }

    public void setStatus(int status) {
        removeAllViews();

        boolean isShow = false;

        switch (status) {
            case Gloading.STATUS_LOADING:
                if (mLoadingView == null) {
                    mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.public_view_loading, null);
                }
                addView(mLoadingView);
                break;
            case Gloading.STATUS_LOAD_SUCCESS:
                isShow = true;
                break;
            case Gloading.STATUS_LOAD_FAILED:

                if (mErrorView == null) {
                    mErrorView = LayoutInflater.from(mContext).inflate(R.layout.public_view_error, null);
                    Button errorBtn = (Button) mErrorView.findViewById(R.id.btn_error);
                    errorBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mRetryTask != null) {
                                mRetryTask.run();
                            }
                        }
                    });
                }

                addView(mErrorView);
                break;

            case Gloading.STATUS_NO_NET:
                if (mNetErrorView == null) {
                    mNetErrorView = LayoutInflater.from(mContext).inflate(R.layout.public_view_net_error, null);
                    Button netErrorBtn = (Button) mNetErrorView.findViewById(R.id.common_neterror_refresh);
                    netErrorBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mRetryTask != null) {
                                mRetryTask.run();
                            }
                        }
                    });

                }

                addView(mNetErrorView);
                break;

            case Gloading.STATUS_EMPTY_DATA:
                if (mEmptyView == null) {
                    mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.public_view_empty, null);
                }

                addView(mEmptyView);
                break;
        }



        setVisibility(isShow ? View.GONE : View.VISIBLE);


    }

}

