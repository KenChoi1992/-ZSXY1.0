package com.zsxy.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zsxy.model.Courses;

public class GetJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long count;
	private List<Courses> lessons;
	private Courses courses;

	public GetJson() {

	}

	public List<Courses> getLessons(JSONObject json) throws JSONException {

		count = json.getJSONArray("lessons").length();
		lessons = new ArrayList<Courses>();
		for (int i = 0; i < count; i++) {
			courses = new Courses();
			courses.setLessonTime(json.getJSONArray("lessons").getJSONObject(i)
					.getString("lesson_time").toString());
			String lessonsTime = json.getJSONArray("lessons").getJSONObject(i)
					.getString("lesson_time").toString();
			if(!lessonsTime.equals(" ")){
				String[] as = lessonsTime.split(",");
				courses.setLessonStart(Integer.parseInt(as[0]));
				courses.setLessonLength(as.length);
			}else{
				
				courses.setLessonStart(0);
				courses.setLessonLength(0);
			}
			
			courses.setLessonType(json.getJSONArray("lessons").getJSONObject(i)
					.getString("lesson_type").toString());
			courses.setLessonTeachBy(json.getJSONArray("lessons")
					.getJSONObject(i).getString("lesson_teach_by").toString());
			courses.setLessonWeeks(json.getJSONArray("lessons")
					.getJSONObject(i).getString("lesson_weeks").toString());
			courses.setLessonClassroom(json.getJSONArray("lessons")
					.getJSONObject(i).getString("lesson_classroom").toString());
			courses.setLessonName(json.getJSONArray("lessons").getJSONObject(i)
					.getString("lesson_name").toString());
			courses.setLessonDay(json.getJSONArray("lessons").getJSONObject(i)
					.getString("lesson_day").toString());
			lessons.add(courses);
		}
		return lessons;
	}

}
