package com.stepyen.yframe.core.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ================================================
 * {@link androidx.fragment.app.Fragment} 代理类,用于框架内部在每个 {@link androidx.fragment.app.Fragment} 的对应生命周期中插入需要的逻辑
 *
 */
public interface IFragmentDelegate {
    String FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE";

    void onAttach(@NonNull Context context);

    void onCreate(@Nullable Bundle savedInstanceState);

    void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState);

    void onActivityCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

    /**
     * Return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}
