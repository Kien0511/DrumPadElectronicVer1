package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayRecordAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> getList() {
        return list;
    }

    List<Fragment> list = new ArrayList<>();

    public PlayRecordAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addFragment(Fragment fragment)
    {
        list.add(fragment);
    }
}
