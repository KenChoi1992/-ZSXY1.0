package com.zsxy.schedule;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.zds_zsxy.MainActivity;
import com.zds_zsxy.R;
import com.zsxy.controls.ZsxyTextView;
import com.zsxy.database.DatabaseHelper;
import com.zsxy.database.DatabaseManager;
import com.zsxy.http.GetJson;
import com.zsxy.model.Courses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Schedule extends Fragment implements OnClickListener,
		OnItemSelectedListener {
	// 锟斤拷锟捷革拷SourseInfo Activity锟侥诧拷锟斤拷锟酵憋拷志
	private String weekNum;
	private int startTime;
	private String week;
	private final static String STARTTIME = "startTime";
	private final static String WEEK = "week";
	private final static String WEEKNUM = "weekNum";

	// 锟斤拷录锟斤拷示锟皆伙拷锟斤拷
	private Dialog progressDialog = null;

	// 锟斤拷锟捷匡拷锟斤拷锟斤拷锟截碉拷
	// private DatabaseHelper helper;
	private DatabaseManager manager;

	private final static int ADDREQUESTCODE = 1;
	private final static int REFRESHREQUESTCODE = 1;

	private final static int LOGINRESULTCODE = 1;
	// 锟斤拷锟斤拷锟斤拷锟捷碉拷Tag
	private final static String STUDENTID = "studentId";
	private final static String STUDENTPASSWORD = "studentPassword";

	private Handler handler = null;
	private String resultString = "";

	private String studentId;
	private String studentPassword;

	private View mMainView;
	private View userView;

	// 锟斤拷锟斤拷PopupWindow
	private PopupWindow userPopWin = null;

	// 锟斤拷应锟侥憋拷锟斤拷锟侥讹拷锟斤拷
	private int[] userTextViewId; // 锟斤拷锟斤拷没锟酵凤拷竦锟斤拷拇锟斤拷诘锟斤拷锟斤拷ID

	// 锟斤拷锟斤拷activity_schedule锟斤拷锟斤拷目丶锟�
	private Spinner mWeekSpinner;
	private RelativeLayout mAddLayout;
	private ScrollView mShowSchedule;
	private ImageView mAddSchedule;
	private ImageView mUser;
	private ImageView mLogin;
	private ZsxyTextView[] mUserTextView;// 锟斤拷锟斤拷没锟酵凤拷竦锟斤拷拇锟斤拷诘锟斤拷锟斤拷

	// 锟轿憋拷每锟斤拷锟斤拷锟接的伙拷锟�
	private ZsxyTextView[] mWeek;
	private ZsxyTextView[] mSerial;
	private ZsxyTextView[] mMon;
	private ZsxyTextView[] mTuse;
	private ZsxyTextView[] mWed;
	private ZsxyTextView[] mThur;
	private ZsxyTextView[] mFri;
	private ZsxyTextView[] mSat;
	private ZsxyTextView[] mSun;

	private int[] weekId;
	private int[] serialId;
	private int[] monId;
	private int[] tuesId;
	private int[] wedId;
	private int[] thurId;
	private int[] friId;
	private int[] satId;
	private int[] sunId;

	private int[][] scheduleColor;// 课程背景图片数组
	boolean isWeek = false;// 判断是否按周数查询课程

	// private ZsxyTextView[][] mLessonTextView;// 锟斤拷示锟轿筹拷锟斤拷锟捷的控硷拷锟斤拷锟斤拷
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		mMainView = layoutInflater.inflate(R.layout.activity_schedule,
				(ViewGroup) getActivity().findViewById(R.layout.activity_main),
				false);
		Init();
		handler = new Handler();
		AddListener();

		SetSpinner();
		// try {
		// manager.delete();
		// testHttp("", "");
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// InsertAndQuery();
		// progressDialog = ProgressDialog.show(getActivity(), "锟斤拷锟斤拷",
		// "锟斤拷锟斤拷锟斤拷锟斤拷,锟斤拷锟皆猴拷");
	}

	public void InsertAndQuery() {
		List<Courses> courses = new ArrayList<Courses>();
		// Courses course = new Courses(1, "10", 1, 2, "锟斤拷选", "锟斤拷锟斤拷",
		// "1,2,3,4,5",
		// "A1-604", "锟斤拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷", "Tue");
		// Courses course1 = new Courses(1, "10", 1, 2, "锟斤拷选", "锟斤拷锟斤拷",
		// "1,2,3,4,5",
		// "A1-604", "锟斤拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷", "Wed");
		// Courses course2 = new Courses(1, "10", 3, 4, "锟斤拷选", "锟斤拷锟斤拷",
		// "1,2,3,4,5",
		// "A1-604", "锟斤拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷", "Mon");
		// Courses course3 = new Courses(1, "10", 5, 6, "锟斤拷选", "锟斤拷锟斤拷",
		// "1,2,3,4,5",
		// "A1-604", "锟斤拷锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷", "thr");

		// courses.add(course);
		// courses.add(course1);
		// courses.add(course2);
		// courses.add(course3);

		manager.Add(courses);

		List<Courses> query = manager.queryAll();
		for (int i = 0; i < query.size(); i++) {
			Log.e("lessonTime", query.get(i).getLessonTime());
			Log.e("lessonStart", query.get(i).getLessonStart() + "");
			Log.e("lessonLength", query.get(i).getLessonLength() + "");
			Log.e("lessonType", query.get(i).getLessonType());
			Log.e("lessonTeachBy", query.get(i).getLessonTeachBy());
			Log.e("lessonWeeks", query.get(i).getLessonWeeks());
			Log.e("lessonClassroom", query.get(i).getLessonClassroom());
			Log.e("lessonName", query.get(i).getLessonName());
			Log.e("lessonDay", query.get(i).getLessonDay());
		}
	}

	@SuppressLint("ResourceAsColor")
	public void CleanClass() {

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		int densityDpi = dm.densityDpi;// 锟斤拷锟斤拷锟侥伙拷芏龋锟矫匡拷锟斤拷锟斤拷兀锟�20/160/240/320锟斤拷
		for (int i = 0; i < mMon.length; i++) {
			mMon[i].setText("");
			mMon[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mTuse[i].setText("");
			mTuse[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mWed[i].setText("");
			mWed[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mThur[i].setText("");
			mThur[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mFri[i].setText("");
			mFri[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mSat[i].setText("");
			mSat[i].setBackgroundResource(R.color.zsxyCourseBackground);
			mSun[i].setText("");
			mSun[i].setBackgroundResource(R.color.zsxyCourseBackground);

			mMon[i].setVisibility(View.VISIBLE);
			mTuse[i].setVisibility(View.VISIBLE);
			mWed[i].setVisibility(View.VISIBLE);
			mThur[i].setVisibility(View.VISIBLE);
			mFri[i].setVisibility(View.VISIBLE);
			mSat[i].setVisibility(View.VISIBLE);
			mSun[i].setVisibility(View.VISIBLE);

			mMon[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mTuse[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mWed[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mThur[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mFri[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mSat[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
			mSun[i].setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (80 * densityDpi) / 160, 1));
		}
	}

	// 锟轿憋拷锟斤拷示锟轿筹拷
	@SuppressLint("ResourceAsColor")
	public void SetClass(boolean isWeek, int position) {
		List<Courses> query;
		if (isWeek) {
			query = manager.queryByWeek(position + 1);// 按星期查询课表
		} else {
			query = manager.queryAll();

			for (int i = 0; i < query.size(); i++) {
				Log.e("lessonTime", query.get(i).getLessonTime());
				Log.e("lessonStart", query.get(i).getLessonStart() + "");
				Log.e("lessonLength", query.get(i).getLessonLength() + "");
				Log.e("lessonType", query.get(i).getLessonType());
				Log.e("lessonTeachBy", query.get(i).getLessonTeachBy());
				Log.e("lessonWeeks", query.get(i).getLessonWeeks());
				Log.e("lessonClassroom", query.get(i).getLessonClassroom());
				Log.e("lessonName", query.get(i).getLessonName());
				Log.e("lessonDay", query.get(i).getLessonDay());
			}
		}
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		int densityDpi = dm.densityDpi;// 锟斤拷锟斤拷锟侥伙拷芏龋锟矫匡拷锟斤拷锟斤拷兀锟�20/160/240/320锟斤拷
		int courseLength;
		for (Courses course : query) {
			if ("Mon".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						mMon[i - 1].setTextColor(0xFFFFFFFF);
						courseLength = course.getLessonLength();
						mMon[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mMon[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mMon[i + j - 1].setVisibility(View.GONE);
						}
						mMon[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						mMon[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i - 1]);
					}
				}
			}
			if ("Tue".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mTuse[i - 1].setTextColor(0xFFFFFFFF);
						mTuse[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mTuse[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mTuse[i + j - 1].setVisibility(View.GONE);
						}
						mTuse[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						mTuse[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i]);
					}
				}
			}
			if ("Wed".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mWed[i - 1].setTextColor(0xFFFFFFFF);
						mWed[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mWed[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mWed[i + j - 1].setVisibility(View.GONE);
						}
						mWed[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						mWed[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i + 1]);
					}
				}
			}
			if ("Thu".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mThur[i - 1].setTextColor(0xFFFFFFFF);
						mThur[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mThur[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mThur[i + j - 1].setVisibility(View.GONE);
						}
						mThur[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						mThur[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i + 2]);
					}
				}
			}
			if ("Fri".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mFri[i - 1].setTextColor(0xFFFFFFFF);
						mFri[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mFri[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mFri[i + j - 1].setVisibility(View.GONE);
						}
						mFri[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						if (i > 10)
							i = 0;
						mFri[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i + 3]);
					}
				}
			}
			if ("Sat".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mSat[i - 1].setTextColor(0xFFFFFFFF);
						mSat[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mSat[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mSat[i + j - 1].setVisibility(View.GONE);
						}
						mSat[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						if (i > 10)
							i = 0;
						mSat[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i + 4]);
					}
				}
			}
			if ("Sun".equals(course.getLessonDay())) {
				for (int i = 1; i <= 11; i++) {
					if (course.getLessonStart() == i) {
						courseLength = course.getLessonLength();
						mSun[i - 1].setTextColor(0xFFFFFFFF);
						mSun[i - 1].setText(course.getLessonName() + "@"
								+ course.getLessonClassroom());
						mSun[i - 1].setOnClickListener(this);
						for (int j = 1; j < courseLength; j++) {
							mSun[i + j - 1].setVisibility(View.GONE);
						}
						mSun[i - 1]
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										(80 * densityDpi * courseLength) / 160,
										courseLength));
						if (i > 10)
							i = 0;
						mSun[i - 1]
								.setBackgroundResource(scheduleColor[courseLength - 1][i + 5]);
					}
				}
			}
		}
	}

	public void InitCourse() {

	}

	// 锟斤拷始锟斤拷锟斤拷应锟侥憋拷锟斤拷锟酵控硷拷
	private void Init() {
		manager = new DatabaseManager(getActivity());
		// Layout锟侥筹拷始锟斤拷
		userView = getActivity().getLayoutInflater().inflate(
				R.layout.functionitem, null);

		// 锟斤拷始锟斤拷PopupWindow
		userPopWin = new PopupWindow(userView, 200, LayoutParams.WRAP_CONTENT,
				true);

		// 锟斤拷锟斤拷锟侥筹拷始锟斤拷
		userTextViewId = new int[] { R.id.search, R.id.homework,
				R.id.notification, R.id.partner, R.id.set };
		mUserTextView = new ZsxyTextView[5];

		// 锟截硷拷锟侥筹拷始锟斤拷
		mUser = (ImageView) mMainView.findViewById(R.id.imageView1);
		mWeekSpinner = (Spinner) mMainView.findViewById(R.id.spinner1);
		mLogin = (ImageView) mMainView.findViewById(R.id.imageView3);
		for (int i = 0; i < userTextViewId.length; i++) {
			mUserTextView[i] = new ZsxyTextView(getActivity());
			mUserTextView[i] = (ZsxyTextView) userView
					.findViewById(userTextViewId[i]);
		}
		mAddLayout = (RelativeLayout) mMainView.findViewById(R.id.scheduleInit);
		mAddSchedule = (ImageView) mMainView.findViewById(R.id.addSchedule);
		mShowSchedule = (ScrollView) mMainView.findViewById(R.id.schedule);

		// 课表背景颜色
		scheduleColor = new int[][] {
				{ R.drawable.onege_1, R.drawable.onege_2, R.drawable.onege_3,
						R.drawable.onege_4, R.drawable.onege_5,
						R.drawable.onege_6, R.drawable.onege_7,
						R.drawable.onege_8, R.drawable.onege_9,
						R.drawable.onege_10, R.drawable.onege_11,
						R.drawable.onege_12, R.drawable.onege_13,
						R.drawable.onege_14 },
				{ R.drawable.twoge_1, R.drawable.twoge_2, R.drawable.twoge_3,
						R.drawable.twoge_4, R.drawable.twoge_5,
						R.drawable.twoge_6, R.drawable.twoge_7,
						R.drawable.twoge_8, R.drawable.twoge_9,
						R.drawable.twoge_10, R.drawable.twoge_11,
						R.drawable.twoge_12, R.drawable.twoge_13,
						R.drawable.twoge_14 },
				{ R.drawable.threege_1, R.drawable.threege_2,
						R.drawable.threege_3, R.drawable.threege_4,
						R.drawable.threege_5, R.drawable.threege_6,
						R.drawable.threege_7, R.drawable.threege_8,
						R.drawable.threege_9, R.drawable.threege_10,
						R.drawable.threege_11, R.drawable.threege_12,
						R.drawable.threege_13, R.drawable.threege_14 } };

		// ID锟侥筹拷始锟斤拷
		weekId = new int[] { R.id.month, R.id.week1, R.id.week2, R.id.week3,
				R.id.week4, R.id.week5, R.id.week6, R.id.week7 };
		serialId = new int[] { R.id.serial1, R.id.serial2, R.id.serial3,
				R.id.serial4, R.id.serial5, R.id.serial6, R.id.serial7,
				R.id.serial8, R.id.serial9, R.id.serial10, R.id.serial11 };
		monId = new int[] { R.id.mon1, R.id.mon2, R.id.mon3, R.id.mon4,
				R.id.mon5, R.id.mon6, R.id.mon7, R.id.mon8, R.id.mon9,
				R.id.mon10, R.id.mon11, R.id.mon12 };
		tuesId = new int[] { R.id.Tues1, R.id.Tues2, R.id.Tues3, R.id.Tues4,
				R.id.Tues5, R.id.Tues6, R.id.Tues7, R.id.Tues8, R.id.Tues9,
				R.id.Tues10, R.id.Tues11, R.id.Tues12 };
		wedId = new int[] { R.id.Wed1, R.id.Wed2, R.id.Wed3, R.id.Wed4,
				R.id.Wed5, R.id.Wed6, R.id.Wed7, R.id.Wed8, R.id.Wed9,
				R.id.Wed10, R.id.Wed11, R.id.Wed12 };
		thurId = new int[] { R.id.Thur1, R.id.Thur2, R.id.Thur3, R.id.Thur4,
				R.id.Thur5, R.id.Thur6, R.id.Thur7, R.id.Thur8, R.id.Thur9,
				R.id.Thur10, R.id.Thur11, R.id.Thur12 };
		friId = new int[] { R.id.Fri1, R.id.Fri2, R.id.Fri3, R.id.Fri4,
				R.id.Fri5, R.id.Fri6, R.id.Fri7, R.id.Fri8, R.id.Fri9,
				R.id.Fri10, R.id.Fri11, R.id.Fri12 };
		satId = new int[] { R.id.Sat1, R.id.Sat2, R.id.Sat3, R.id.Sat4,
				R.id.Sat5, R.id.Sat6, R.id.Sat7, R.id.Sat8, R.id.Sat9,
				R.id.Sat10, R.id.Sat11, R.id.Sat12 };
		sunId = new int[] { R.id.Sun1, R.id.Sun2, R.id.Sun3, R.id.Sun4,
				R.id.Sun5, R.id.Sun6, R.id.Sun7, R.id.Sun8, R.id.Sun9,
				R.id.Sun10, R.id.Sun11, R.id.Sun12 };

		mWeek = new ZsxyTextView[weekId.length];
		for (int i = 0; i < weekId.length; i++) {
			mWeek[i] = (ZsxyTextView) mMainView.findViewById(wedId[i]);
		}

		mSerial = new ZsxyTextView[serialId.length];
		for (int i = 0; i < serialId.length; i++) {
			mSerial[i] = (ZsxyTextView) mMainView.findViewById(serialId[i]);
		}

		mMon = new ZsxyTextView[monId.length];
		for (int i = 0; i < monId.length; i++) {
			mMon[i] = (ZsxyTextView) mMainView.findViewById(monId[i]);
		}

		mTuse = new ZsxyTextView[tuesId.length];
		for (int i = 0; i < tuesId.length; i++) {
			mTuse[i] = (ZsxyTextView) mMainView.findViewById(tuesId[i]);
		}

		mWed = new ZsxyTextView[wedId.length];
		for (int i = 0; i < wedId.length; i++) {
			mWed[i] = (ZsxyTextView) mMainView.findViewById(wedId[i]);
		}

		mThur = new ZsxyTextView[thurId.length];
		for (int i = 0; i < thurId.length; i++) {
			mThur[i] = (ZsxyTextView) mMainView.findViewById(thurId[i]);
		}

		mFri = new ZsxyTextView[friId.length];
		for (int i = 0; i < friId.length; i++) {
			mFri[i] = (ZsxyTextView) mMainView.findViewById(friId[i]);
		}

		mSat = new ZsxyTextView[satId.length];
		for (int i = 0; i < satId.length; i++) {
			mSat[i] = (ZsxyTextView) mMainView.findViewById(satId[i]);
		}

		mSun = new ZsxyTextView[sunId.length];
		for (int i = 0; i < sunId.length; i++) {
			mSun[i] = (ZsxyTextView) mMainView.findViewById(sunId[i]);
		}
	}

	private void SetSpinner() {
		// ArrayAdapter<String> adapter;
		List<String> list = new ArrayList<String>();
		String[] sArray = getResources().getStringArray(R.array.weeks);
		for (int i = 0; i < sArray.length; i++) {
			list.add(sArray[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
		// 锟斤拷锟斤拷一锟斤拷应锟斤拷锟斤拷锟斤拷锟斤拷源锟侥凤拷式
		// ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		// getActivity(), R.array.weeks,
		// android.R.layout.simple_spinner_item);
		mWeekSpinner.setAdapter(adapter);
	}

	// 为锟截硷拷锟斤拷锟斤拷锟接︼拷募锟斤拷锟斤拷锟�
	private void AddListener() {
		mUser.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		for (int i = 0; i < userTextViewId.length; i++) {
			mUserTextView[i].setOnClickListener(this);
		}
		mAddSchedule.setOnClickListener(this);
		mWeekSpinner.setOnItemSelectedListener(this);
	}

	private void StartSourseInfoActivity() {
		Intent intent = new Intent();
		intent.putExtra(WEEKNUM, weekNum);
		intent.putExtra(STARTTIME, startTime);
		intent.putExtra(WEEK, week);
		Log.i("Start Activity", "锟斤拷锟斤拷锟斤拷锟街匡拷锟斤拷煞锟�");
		intent.setClass(getActivity(), SourseInfo.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.imageView1:
			userPopWin.setTouchable(true);
			userPopWin.setOutsideTouchable(true);
			userPopWin.setBackgroundDrawable(new BitmapDrawable(getResources(),
					(Bitmap) null));
			if (userPopWin.isShowing())
				userPopWin.dismiss();
			else
				userPopWin.showAsDropDown(mMainView.findViewById(R.id.user));
			break;
		case R.id.imageView3:
			intent.setClass(getActivity(), Login.class);
			startActivityForResult(intent, REFRESHREQUESTCODE);
			Thread updateThread = new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					try {
						Thread.sleep(120);
						progressDialog = createLoadingDialog(getActivity(),
								"登录中");
						progressDialog.show();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Looper.loop();
				}
			};
			updateThread.start();
			break;
		// 锟矫伙拷头锟斤拷锟斤拷锟斤拷锟斤拷锟杰的硷拷锟斤拷
		case R.id.search:
		case R.id.homework:
		case R.id.notification:
		case R.id.partner:
		case R.id.set:
			// intent.setClass(getActivity(), SourseInfo.class);
			// startActivity(intent);
			break;
		// 锟斤拷锟斤拷锟斤拷锟接课憋拷钮
		case R.id.addSchedule:

			intent.setClass(getActivity(), Login.class);
			startActivityForResult(intent, ADDREQUESTCODE);
			// handler.post(r);
			Thread addThread = new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					try {
						Thread.sleep(120);
						progressDialog = createLoadingDialog(getActivity(),
								"登录中");
						progressDialog.show();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Looper.loop();
				}
			};
			addThread.start();
			break;

		// 锟斤拷示锟轿憋拷锟斤拷锟捷控硷拷锟侥硷拷锟斤拷锟铰硷拷锟斤拷锟斤拷应
		// 锟斤拷一锟轿憋拷
		case R.id.mon1:
			startTime = 1;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon2:
			startTime = 2;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon3:
			startTime = 3;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon4:
			startTime = 4;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon5:
			startTime = 5;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon6:
			startTime = 6;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon7:
			startTime = 7;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon8:
			startTime = 8;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon9:
			startTime = 9;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon10:
			startTime = 10;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon11:
			startTime = 11;
			week = "Mon";
			StartSourseInfoActivity();
			break;
		case R.id.mon12:
			startTime = 12;
			week = "Mon";
			StartSourseInfoActivity();
			break;

		// 锟杰讹拷锟轿憋拷
		case R.id.Tues1:
			startTime = 1;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues2:
			startTime = 2;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues3:
			startTime = 3;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues4:
			startTime = 4;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues5:
			startTime = 5;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues6:
			startTime = 6;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues7:
			startTime = 7;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues8:
			startTime = 8;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues9:
			startTime = 9;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues10:
			startTime = 10;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues11:
			startTime = 11;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		case R.id.Tues12:
			startTime = 12;
			week = "Tue";
			StartSourseInfoActivity();
			break;
		// 锟斤拷锟斤拷锟轿憋拷
		case R.id.Wed1:
			startTime = 1;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed2:
			startTime = 2;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed3:
			startTime = 3;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed4:
			startTime = 4;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed5:
			startTime = 5;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed6:
			startTime = 6;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed7:
			startTime = 7;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed8:
			startTime = 8;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed9:
			startTime = 9;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed10:
			startTime = 10;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed11:
			startTime = 11;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		case R.id.Wed12:
			startTime = 12;
			week = "Wed";
			StartSourseInfoActivity();
			break;
		// 锟斤拷锟侥课憋拷
		case R.id.Thur1:
			startTime = 1;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur2:
			startTime = 2;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur3:
			startTime = 3;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur4:
			startTime = 4;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur5:
			startTime = 5;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur6:
			startTime = 6;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur7:
			startTime = 7;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur8:
			startTime = 8;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur9:
			startTime = 9;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur10:
			startTime = 10;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur11:
			startTime = 11;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		case R.id.Thur12:
			startTime = 12;
			week = "Thu";
			StartSourseInfoActivity();
			break;
		// 锟斤拷锟斤拷伪锟�
		case R.id.Fri1:
			startTime = 1;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri2:
			startTime = 2;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri3:
			startTime = 3;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri4:
			startTime = 4;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri5:
			startTime = 5;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri6:
			startTime = 6;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri7:
			startTime = 7;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri8:
			startTime = 8;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri9:
			startTime = 9;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri10:
			startTime = 10;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri11:
			startTime = 11;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		case R.id.Fri12:
			startTime = 12;
			week = "Fri";
			StartSourseInfoActivity();
			break;
		// 锟斤拷锟斤拷锟轿憋拷
		case R.id.Sat1:
			startTime = 1;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat2:
			startTime = 2;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat3:
			startTime = 3;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat4:
			startTime = 4;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat5:
			startTime = 5;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat6:
			startTime = 6;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat7:
			startTime = 7;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat8:
			startTime = 8;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat9:
			startTime = 9;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat10:
			startTime = 10;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat11:
			startTime = 11;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		case R.id.Sat12:
			startTime = 12;
			week = "Sat";
			StartSourseInfoActivity();
			break;
		// 锟斤拷锟秸课憋拷
		case R.id.Sun1:
			startTime = 1;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun2:
			startTime = 2;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun3:
			startTime = 3;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun4:
			startTime = 4;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun5:
			startTime = 5;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun6:
			startTime = 6;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun7:
			startTime = 7;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun8:
			startTime = 8;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun9:
			startTime = 9;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun10:
			startTime = 10;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun11:
			startTime = 11;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		case R.id.Sun12:
			startTime = 12;
			week = "Sun";
			StartSourseInfoActivity();
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stubViewGroup
		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return mMainView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO Auto-generated method stub
		if (resultCode == LOGINRESULTCODE) {
			boolean isShow = data.getBooleanExtra("isShow", false);
			if (isShow) {
				studentId = data.getStringExtra(STUDENTID);
				studentPassword = data.getStringExtra(STUDENTPASSWORD);
				Log.e("usr", studentId);
				Log.e("pwd", studentPassword);
				mAddLayout.setVisibility(View.GONE);
				mShowSchedule.setVisibility(View.VISIBLE);
				try {
					manager.delete();

					testHttp(studentId, studentPassword);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("锟斤拷锟斤拷锟斤拷锟届常", e.toString());
				}
				CleanClass();
				isWeek = false;
				SetClass(isWeek, 0);
				handler.post(r2);
			} else {
				handler.post(r2);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// spinner锟斤拷
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long arg3) {
		// TODO Auto-generated method stub
		String s = adapterView.getItemAtPosition(position).toString(); // 锟斤拷锟斤拷锟斤拷墙锟斤拷锟侥匡拷锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟絪
		weekNum = s;
		Toast toast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
		toast.show();
		isWeek = true;
		CleanClass();
		SetClass(isWeek, position);// 按星期显示课表
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub

	}

	public void testHttp(final String studentId, final String studentPassword)
			throws JSONException {

		Thread thread = new Thread() {
			@Override
			public void run() {

				Log.e("usr1", studentId);
				Log.e("pwd1", studentPassword);
				// String uriAPI =
				// "http://192.168.1.108:8080/test.php?type=table&xh="
				// + studentId + "&pwd=" + studentPassword+"";
				String uriAPI = "http://192.168.1.108:8080/test.php?type=table&xh=201230675086&pwd=093317";
				HttpGet httpGet = new HttpGet(uriAPI);

				HttpResponse response;
				try {
					response = new DefaultHttpClient().execute(httpGet);
					// progressDialog.cancel();
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						resultString = EntityUtils.toString(entity, HTTP.UTF_8);
					}
					Log.e("tag", resultString);
					// handler.post(r);

					try {
						JSONObject jsonObject = new JSONObject(resultString);
						GetJson tolist = new GetJson();
						List<Courses> list = tolist.getLessons(jsonObject);
						Log.e("Json", list.get(0).getLessonClassroom());
						Log.e("Json", list.get(0).getLessonDay());
						Log.e("Json", list.get(0).getLessonName());
						manager.Add(list);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.e("Fuck", e.toString());
						e.printStackTrace();
					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Runnable r2 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}
	};

	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 锟矫碉拷锟斤拷锟斤拷view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 锟斤拷锟截诧拷锟斤拷
		// main.xml锟叫碉拷ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 锟斤拷示锟斤拷锟斤拷
		// 锟斤拷锟截讹拷锟斤拷
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.drawable.loading_animation);
		// 使锟斤拷ImageView锟斤拷示锟斤拷锟斤拷
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 锟斤拷锟矫硷拷锟斤拷锟斤拷息
		tipTextView.setTextColor(Color.WHITE);
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 锟斤拷锟斤拷锟皆讹拷锟斤拷锟斤拷式dialog

		loadingDialog.setCancelable(false);// 锟斤拷锟斤拷锟斤拷锟矫★拷锟斤拷锟截硷拷锟斤拷取锟斤拷
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 锟斤拷锟矫诧拷锟斤拷
		return loadingDialog;

	}
}
