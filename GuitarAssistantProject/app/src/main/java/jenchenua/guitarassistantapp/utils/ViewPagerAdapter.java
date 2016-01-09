package jenchenua.guitarassistantapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import jenchenua.guitarassistantapp.fragments.TabFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String mTitles[];
    private int mNumbOfTabs;
    private String mFormula;
    private String[] mPositions;

    public ViewPagerAdapter(FragmentManager fm, String mTitles[], int mNumbOfTabs, String formula, String positions) {
        super(fm);

        this.mTitles = mTitles;
        this.mNumbOfTabs = mNumbOfTabs;
        this.mFormula = formula;
        this.mPositions = positions.split(";");
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return TabFragment.newInstance(mTitles[0], mFormula, mPositions[0]);
        else if (position == 1)
            return TabFragment.newInstance(mTitles[1], mFormula, mPositions[1]);
        else if (position == 2)
            return TabFragment.newInstance(mTitles[2], mFormula, mPositions[2]);
        else if (position == 3)
            return TabFragment.newInstance(mTitles[3], mFormula, mPositions[3]);
        else
            return TabFragment.newInstance(mTitles[4], mFormula, mPositions[4]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mNumbOfTabs;
    }
}
