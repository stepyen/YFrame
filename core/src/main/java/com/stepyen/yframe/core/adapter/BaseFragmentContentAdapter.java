package com.stepyen.yframe.core.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * date：2019/7/13
 * author：stepyen
 * description：
 *
 * 视图存在，实例存在
 */
public class BaseFragmentContentAdapter<T extends Fragment> extends BaseFragmentAdapter<T> {

    public BaseFragmentContentAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentContentAdapter(FragmentManager fm, List<T> fragments) {
        super(fm, fragments);
    }

    public BaseFragmentContentAdapter(FragmentManager fm, T[] fragments) {
        super(fm, fragments);
    }

    public BaseFragmentContentAdapter(FragmentManager fm, List<T> fragments, CharSequence[] titles) {
        super(fm, fragments, titles);
    }

    public BaseFragmentContentAdapter(FragmentManager fm, T[] fragments, CharSequence[] titles) {
        super(fm, fragments, titles);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragmentList.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }
}
