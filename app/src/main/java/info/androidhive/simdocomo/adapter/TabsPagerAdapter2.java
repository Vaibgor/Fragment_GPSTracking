package info.androidhive.simdocomo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.androidhive.simdocomo.closed;
import info.androidhive.simdocomo.open;
import info.androidhive.simdocomo.pending_sync;


/**
 * Created by User on 3/2/2016.
 */
public class TabsPagerAdapter2 extends FragmentPagerAdapter {

    String feed1;
    String name1;
    String Val;
    String agency_id;

    public TabsPagerAdapter2(FragmentManager fm, String feed, String name, String qval, String agency_id) {
        super(fm);
        this.feed1 = feed;
        this.name1 = name;
        this.Val = qval;
        this.agency_id = agency_id;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new open(feed1, name1, Val,agency_id);
            case 1:
                // Games fragment activity
                return new closed(feed1, name1);
            case 2:
                // Movies fragment activity
                return new pending_sync(feed1, name1);

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}

