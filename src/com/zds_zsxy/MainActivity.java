package com.zds_zsxy;

import java.util.ArrayList;

import com.zsxy.discovery.Discovery;
import com.zsxy.portal.Portal;
import com.zsxy.schedule.Schedule;
import com.zsxy.service.Service;
//import com.zsxy.adapter.ZsxyViewPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {

	private TextView[] mTextViewList;
	private int[] textViewListId;

	// 定义各个Fragment
	private Portal portal;
	private Discovery discovery;
	private Service servise;
	private Schedule schedule;

	private ViewPager mViewPager;

	// 页面列表
	private ArrayList<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		Init();
		AddListener();
		mViewPager.setAdapter(new ZsxyViewPagerAdapter(
				getSupportFragmentManager()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 初始化控件以及变量
	private void Init() {
		// 初始化title控件
		textViewListId = new int[] { R.id.protal, R.id.discovery, R.id.service,
				R.id.schedule };
		mTextViewList = new TextView[4];
		for (int i = 0; i < 4; i++) {
			mTextViewList[i] = (TextView) findViewById(textViewListId[i]);
		}

		mTextViewList[0].setBackgroundResource(R.color.zsxyBlue);

		// 页面的初始化
		portal = new Portal();
		discovery = new Discovery();
		servise = new Service();
		schedule = new Schedule();

		// 控件的初始化
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		// 页面数组的初始化
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(portal);
		fragmentList.add(discovery);
		fragmentList.add(servise);
		fragmentList.add(schedule);

	}

	// 为控件增加监听器
	private void AddListener() {
		// 为ViewPager增加监听器
		mViewPager.setOnPageChangeListener(this);

		// 为标题控件增加监听器
		for (int i = 0; i < 4; i++) {
			mTextViewList[i].setOnClickListener(this);
		}
	}

	public class ZsxyViewPagerAdapter extends FragmentPagerAdapter {

		public ZsxyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			if (arg0 == i)
				mTextViewList[i].setBackgroundResource(R.color.zsxyBlue);
			else
				mTextViewList[i].setBackgroundResource(R.color.white);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.protal:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.discovery:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.service:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.schedule:
			mViewPager.setCurrentItem(3);
			break;
		}
	}
}
