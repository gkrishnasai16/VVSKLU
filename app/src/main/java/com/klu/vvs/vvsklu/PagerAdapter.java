package com.klu.vvs.vvsklu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public PagerAdapter(FragmentManager fm,int noOfTabs){
        super(fm);
        this.noOfTabs=noOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                TabApplication tabApplication=new TabApplication();
                return tabApplication;
            case 1:
                TabShortList tabShortList=new TabShortList();
                return tabShortList;
            default:
                return null;

        }




    }

    @Override
    public int getCount() {
        return 0;
    }
}
