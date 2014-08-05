package com.zsxy.database;

import java.util.ArrayList;
import java.util.List;

import com.zsxy.model.Courses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {

	private DatabaseHelper dataBaseHelper;
	private SQLiteDatabase db;

	public DatabaseManager(Context context) {
		dataBaseHelper = new DatabaseHelper(context);
		db = dataBaseHelper.getWritableDatabase();
	}

	// 增加记录
	public void Add(List<Courses> courses) {
		db.beginTransaction();
		try {
			for (Courses course : courses) {
				db.execSQL(
						"INSERT INTO " + DatabaseHelper.TABLE_NAME
								+ " VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
						new Object[] { course.getLessonTime(),
								course.getLessonStart(),
								course.getLessonLength(),
								course.getLessonType(),
								course.getLessonTeachBy(),
								course.getLessonWeeks(),
								course.getLessonClassroom(),
								course.getLessonName(), course.getLessonDay() });
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	// 删除记录
	public void delete() {
		db.execSQL("delete from " + DatabaseHelper.TABLE_NAME);
	}

	// 更新记录
	public void update() {

	}

	// 按照一定的条件查找
	public List<Courses> queryByTime(int startTime, String week) {
		ArrayList<Courses> courses = new ArrayList<Courses>();
		Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME
				+ " WHERE lessonStart = ? AND lessonDay like ? ",
				new String[] { startTime + "", week });
		if (c.getCount() == 0)
			Log.i("查询结果", "结果为空");
		while (c.moveToNext()) {
			Courses course = new Courses();
			course.setLessonTime(c.getString(c.getColumnIndex("lessonTime")));
			Log.i(c.getString(c.getColumnIndex("lessonName")),
					c.getString(c.getColumnIndex("lessonName")));
			course.setLessonStart(c.getInt(c.getColumnIndex("lessonStart")));
			course.setLessonLength(c.getInt(c.getColumnIndex("lessonLength")));
			course.setLessonType(c.getString(c.getColumnIndex("lessonType")));
			course.setLessonTeachBy(c.getString(c
					.getColumnIndex("lessonTeachBy")));
			course.setLessonWeeks(c.getString(c.getColumnIndex("lessonWeeks")));
			course.setLessonClassroom(c.getString(c
					.getColumnIndex("lessonClassroom")));
			course.setLessonName(c.getString(c.getColumnIndex("lessonName")));
			course.setLessonDay(c.getString(c.getColumnIndex("lessonDay")));
			courses.add(course);
		}
		return courses;
	}

	// 查询记录
	public List<Courses> queryAll() {
		ArrayList<Courses> courses = new ArrayList<Courses>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Courses course = new Courses();
			course.setLessonTime(c.getString(c.getColumnIndex("lessonTime")));
			course.setLessonStart(c.getInt(c.getColumnIndex("lessonStart")));
			course.setLessonLength(c.getInt(c.getColumnIndex("lessonLength")));
			course.setLessonType(c.getString(c.getColumnIndex("lessonType")));
			course.setLessonTeachBy(c.getString(c
					.getColumnIndex("lessonTeachBy")));
			course.setLessonWeeks(c.getString(c.getColumnIndex("lessonWeeks")));
			course.setLessonClassroom(c.getString(c
					.getColumnIndex("lessonClassroom")));
			course.setLessonName(c.getString(c.getColumnIndex("lessonName")));
			course.setLessonDay(c.getString(c.getColumnIndex("lessonDay")));
			courses.add(course);
		}
		return courses;
	}
	
	//按照星期查询，返回课表
	public List<Courses> queryByWeek(int position) {
		ArrayList<Courses> courses = new ArrayList<Courses>();
		Cursor c = queryTheCursor();
		String week = position + "";//传入周数，得到week 
		while(c.moveToNext()){
			String str = c.getString(c.getColumnIndex("lessonWeeks"));
			if(str.contains(week)){
				Courses course = new Courses();
				course.setLessonTime(c.getString(c.getColumnIndex("lessonTime")));
				course.setLessonStart(c.getInt(c.getColumnIndex("lessonStart")));
				course.setLessonLength(c.getInt(c.getColumnIndex("lessonLength")));
				course.setLessonType(c.getString(c.getColumnIndex("lessonType")));
				course.setLessonTeachBy(c.getString(c
						.getColumnIndex("lessonTeachBy")));
				course.setLessonWeeks(c.getString(c.getColumnIndex("lessonWeeks")));
				course.setLessonClassroom(c.getString(c
						.getColumnIndex("lessonClassroom")));
				course.setLessonName(c.getString(c.getColumnIndex("lessonName")));
				course.setLessonDay(c.getString(c.getColumnIndex("lessonDay")));
				courses.add(course);
			}
		}
		return courses;
	}

	// 获得游标
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME,
				null);
		return c;
	}
}
