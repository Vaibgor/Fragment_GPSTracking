package info.androidhive.simdocomo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.androidhive.simdocomo.GamesFragment;
import info.androidhive.simdocomo.MoviesFragment;
import info.androidhive.simdocomo.TopRatedFragment;
import info.androidhive.simdocomo.Video;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	String  feed1;
	String  code;
	String  name1;

	public TabsPagerAdapter(FragmentManager fm,  String  feed,String code, String name1) {
		super(fm);
		this.feed1 = feed;
		this.code=code;
		this.name1=name1;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TopRatedFragment(feed1,code,name1);
		case 1:
			// Games fragment activity
			return new GamesFragment(feed1);
		case 2:
			// Movies fragment activity
			return new MoviesFragment(feed1);
		case 3:
				// video fragment activity
				return new Video();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
