package com.zsxy.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;



public class ZsxyViewPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragmentList;
	
	public ZsxyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList){
		super(fm);
		this.fragmentList = fragmentList;
	}

	public ZsxyViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragmentList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		return this.fragmentList.get(index);
	}

}
