package com.zsxy.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

import com.zsxy.model.Courses;

public class GetHttp {

	private String resultString;

	// private final static String url =
	// "http://192.168.191.3/scutjw.py/main11";
	// private HttpPost httpPost;
	// private HttpClient httpClient;
	// private HttpResponse httpResponse;
	// private List<NameValuePair> nameValueList;
	//
	// private String result;
	//
	// public GetHttp() {
	// httpPost = new HttpPost(url);
	// nameValueList = new ArrayList<NameValuePair>();
	// nameValueList.add(new BasicNameValuePair("user", "201230674362"));
	// nameValueList.add(new BasicNameValuePair("password", "022666"));
	// nameValueList.add(new BasicNameValuePair("option", "lesson_table"));
	// nameValueList.add(new BasicNameValuePair("force", "yes"));
	//
	// httpClient = new DefaultHttpClient();
	// }
	//
	// public void SendRequest() {
	// try {
	// httpPost.setEntity(new UrlEncodedFormEntity(nameValueList,
	// HTTP.UTF_8));
	// httpResponse = httpClient.execute(httpPost);
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// public void GetResponse() {
	// if (httpResponse.getStatusLine().getStatusCode() == 200) {
	// try {
	// result = EntityUtils.toString(httpResponse.getEntity());
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// public void WriteToSQL(){
	//
	// }

	public void testHttp() throws JSONException {
		Thread thread = new Thread() {
			@Override
			public void run() {

				String uriAPI = "http://192.168.1.125:8080/test.php?type=table&xh=201230675086&pwd=093317";
				HttpGet httpGet = new HttpGet(uriAPI);

				HttpResponse response;
				try {
					response = new DefaultHttpClient().execute(httpGet);
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
					} catch (JSONException e) {
						// TODO Auto-generated catch block
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
	}
}
