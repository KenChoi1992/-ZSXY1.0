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

	// ���ݿ�
	private DatabaseManager manager;

	// ����ؼ�
	private TextView mLessonName;
	private TextView mClassroom;
	private TextView mTeacher;
	private TextView mLessonNumber;
	private TextView mWeekNumber;

	// �ӵ���Activity�л�ȡ�Ĳ����ͱ�־
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
			Log.i("��ѯ���", "���Ϊ��");
		for (Courses course : query) {
			mLessonName.setText(course.getLessonName());
			Log.i(course.getLessonName(), course.getLessonName());
			mClassroom.setText(course.getLessonClassroom());
			mTeacher.setText(course.getLessonTeachBy());
			mLessonNumber.setText(JudgeWeek(week) +" ��"+ course.getLessonTime()
					+ "��");
			mWeekNumber.setText(weekNum);
		}
	}

	private String JudgeWeek(String week) {
		if ("Mon".equals(week))
			return "��һ";
		if ("Tue".equals(week))
			return "�ܶ�";
		if ("Wed".equals(week))
			return "����";
		if ("Thu".equals(week))
			return "����";
		if ("Fri".equals(week))
			return "����";
		if ("Sat".equals(week))
			return "����";
		if ("Sun".equals(week))
			return "����";
		return null;
	}

}
