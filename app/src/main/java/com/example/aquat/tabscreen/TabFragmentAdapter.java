package com.example.aquat.tabscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by aquat on 2017/12/06.
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {


    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
