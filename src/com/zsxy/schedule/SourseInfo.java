package com.zsxy.schedule;

import java.util.List;

import com.zds_zsxy.R;
import com.zsxy.database.DatabaseManager;
import com.zsxy.model.Courses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class SourseInfo extends Activity {

	// 数据库
	private DatabaseManager manager;

	// 定义控件
	private TextView mLessonName;
	private TextView mClassroom;
	private TextView mTeacher;
	private TextView mLessonNumber;
	private TextView mWeekNumber;

	// 从调用Activity中获取的参数和标志
	private String weekNum;
	private int startTime;
	private String week;
	private final static String STARTTIME = "startTime";
	private final static String WEEK = "week";
	private final static String WEEKNUM = "weekNum";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_course_info);

		Init();

		Intent intent = this.getIntent();
		startTime = intent.getIntExtra(STARTTIME, 0);
		Log.i(STARTTIME, startTime + "");
		week = intent.getStringExtra(WEEK);
		Log.i(WEEK, week);
		weekNum = intent.getStringExtra(WEEKNUM);
		Log.i(WEEKNUM, weekNum);

		ShowInfo(startTime, week);
	}

	private void Init() {
		manager = new DatabaseManager(this);
		mLessonName = (TextView) findViewById(R.id.lesson_name);
		mClassroom = (TextView) findViewById(R.id.classroom);
		mTeacher = (TextView) findViewById(R.id.teacher);
		mLessonNumber = (TextView) findViewById(R.id.lesson_number);
		mWeekNumber = (TextView) findViewById(R.id.week_number);
	}

	private void ShowInfo(int startTime, String week) {

		List<Courses> query = manager.queryByTime(startTime, week);
		// List<Courses> query = manager.queryAll();
		if (query == null)
			Log.i("查询结果", "结果为空");
		for (Courses course : query) {
			mLessonName.setText(course.getLessonName());
			Log.i(course.getLessonName(), course.getLessonName());
			mClassroom.setText(course.getLessonClassroom());
			mTeacher.setText(course.getLessonTeachBy());
			mLessonNumber.setText(JudgeWeek(week) +" 第"+ course.getLessonTime()
					+ "节");
			mWeekNumber.setText(weekNum);
		}
	}

	private String JudgeWeek(String week) {
		if ("Mon".equals(week))
			return "周一";
		if ("Tue".equals(week))
			return "周二";
		if ("Wed".equals(week))
			return "周三";
		if ("Thu".equals(week))
			return "周四";
		if ("Fri".equals(week))
			return "周五";
		if ("Sat".equals(week))
			return "周六";
		if ("Sun".equals(week))
			return "周日";
		return null;
	}

}
