package com.zsxy.model;

import java.io.Serializable;

public class Courses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int LessonId; // 课程Id,是自增的
	private String lessonTime = null; // 第几节课
	private int lessonStart;// 课程从第几节课开始
	private int lessonLength;// 课程持续多少节
	private String lessonType = null; // 必修或选修
	private String lessonTeachBy = null; // 教师
	private String lessonWeeks = null; // 上课周
	private String lessonClassroom = null; // 教室
	private String lessonName = null; // 课程名
	private String lessonDay = null; // 星期

	public Courses() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Courses(int lessonId, String lessonTime, int lessonStart,
			int lessonLength, String lessonType, String lessonTeachBy,
			String lessonWeeks, String lessonClassroom, String lessonName,
			String lessonDay) {
		super();
		LessonId = lessonId;
		this.lessonTime = lessonTime;
		this.lessonStart = lessonStart;
		this.lessonLength = lessonLength;
		this.lessonType = lessonType;
		this.lessonTeachBy = lessonTeachBy;
		this.lessonWeeks = lessonWeeks;
		this.lessonClassroom = lessonClassroom;
		this.lessonName = lessonName;
		this.lessonDay = lessonDay;
	}

	public int getLessonStart() {
		return lessonStart;
	}

	public void setLessonStart(int lessonStart) {
		this.lessonStart = lessonStart;
	}

	public int getLessonLength() {
		return lessonLength;
	}

	public void setLessonLength(int lessonLength) {
		this.lessonLength = lessonLength;
	}

	public int getLessonId() {
		return LessonId;
	}

	public void setLessonId(int lessonId) {
		LessonId = lessonId;
	}

	public String getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(String lessonTime) {
		this.lessonTime = lessonTime;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public String getLessonTeachBy() {
		return lessonTeachBy;
	}

	public void setLessonTeachBy(String lessonTeachBy) {
		this.lessonTeachBy = lessonTeachBy;
	}

	public String getLessonWeeks() {
		return lessonWeeks;
	}

	public void setLessonWeeks(String lessonWeeks) {
		this.lessonWeeks = lessonWeeks;
	}

	public String getLessonClassroom() {
		return lessonClassroom;
	}

	public void setLessonClassroom(String lessonClassroom) {
		this.lessonClassroom = lessonClassroom;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getLessonDay() {
		return lessonDay;
	}

	public void setLessonDay(String lessonDay) {
		this.lessonDay = lessonDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
