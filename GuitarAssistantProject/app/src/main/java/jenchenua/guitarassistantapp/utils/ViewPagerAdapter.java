package jenchenua.guitarassistantapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import jenchenua.guitarassistantapp.fragments.Tab1;
import jenchenua.guitarassistantapp.fragments.Tab2;
import jenchenua.guitarassistantapp.fragments.Tab3;
import jenchenua.guitarassistantapp.fragments.Tab4;
import jenchenua.guitarassistantapp.fragments.Tab5;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    // if the position is 0 we are returning the First tab
    // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new Tab1();
        else if (position == 1)
            return new Tab2();
        else if (position == 2)
            return new Tab3();
        else if (position == 3)
            return new Tab4();
        else
            return new Tab5();
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
