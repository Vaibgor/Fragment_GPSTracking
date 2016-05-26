package info.androidhive.simdocomo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.androidhive.simdocomo.GamesFragment;
import info.androidhive.simdocomo.MoviesFragment;
import info.androidhive.simdocomo.TopRatedFragment;
import info.androidhive.simdocomo.Video;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	String  rec_id,segment,code,name1;

	public TabsPagerAdapter(FragmentManager fm, String  rec_id,String code, String name1, String segment) {
		super(fm);
		this.rec_id = rec_id;
		this.code=code;
		this.name1=name1;
		this.segment=segment;
	}
	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TopRatedFragment(rec_id,code,name1,segment);
		case 1:
			// Games fragment activity
			return new GamesFragment(rec_id);
		case 2:
			// Movies fragment activity
			return new MoviesFragment(rec_id);
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
