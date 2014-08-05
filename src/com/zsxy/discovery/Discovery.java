package com.zsxy.discovery;


import com.zds_zsxy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Discovery extends Fragment {

	private View mMainView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		mMainView = layoutInflater.inflate(R.layout.activity_discovery,
				(ViewGroup) getActivity().findViewById(R.layout.activity_main),
				false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
			Log.v("huahua", "fragment1-->�Ƴ��Ѵ��ڵ�View");
		}

		return mMainView;
	}

}
