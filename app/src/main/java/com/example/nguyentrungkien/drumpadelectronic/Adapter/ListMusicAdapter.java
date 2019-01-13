package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListMusicAdapter extends FragmentStatePagerAdapter {

    public final List<Fragment> fragmentList = new ArrayList<>();
    public final List<String> titleList = new ArrayList<>();

    public ListMusicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void addFragment (Fragment fragment, String title)
    {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
