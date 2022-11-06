package com.stepyen.yframe.core.adapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * date：2019/5/27
 * author：stepyen
 * description：
 *
 *
 * 实例销毁 视图销毁    内部fragment复用
 */
public class BaseFragmentStateAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    protected List<T> mFragmentList = new ArrayList<>();
    protected CharSequence[] mTitles;
    protected FragmentManager mFragmentManager;
    public BaseFragmentStateAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        setFragments(fragments);
        mFragmentManager = fm;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, T[] fragments) {
        super(fm);
        setFragments(Arrays.asList(fragments));
        mFragmentManager = fm;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<T> fragments, CharSequence[] titles) {
        super(fm);
        setFragments(fragments);
        this.mTitles = titles;
        mFragmentManager = fm;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, T[] fragments, CharSequence[] titles) {
        super(fm);
        setFragments(Arrays.asList(fragments));
        this.mTitles = titles;
        mFragmentManager = fm;
    }

    public void addFragment(T fragment) {
        if (fragment != null) {
            mFragmentList.add(fragment);
        }
    }

    public void setFragments(List<T> fragments) {
        if (fragments != null && fragments.size() > 0) {
            mFragmentList.clear();
            mFragmentList.addAll(fragments);
        }
    }

    public void addFragments(List<T> fragments) {
        if (fragments != null && fragments.size() > 0) {
            mFragmentList.addAll(fragments);
        }
    }

    @Override
    public T getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position < mTitles.length) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }

    public List<T> getFragmentLists() {
        return mFragmentList;
    }


}
