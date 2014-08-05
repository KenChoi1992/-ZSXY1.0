package com.zsxy.schedule;

import com.zds_zsxy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	private final static int CANCELRESULTCODE = 1;

	// ��Ӧ�Ŀؼ��Ķ���
	private Button mSubmitButton;
	private Button mCancelButton;
	private EditText mStudentIdEditText;
	private EditText mStudentPasswordEditText;

	// ��Ӧ�����Ķ���
	private String studentId;
	private String studentPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Init();
		AddListener();
	}

	// ��ʼ���ؼ��ͱ���
	private void Init() {
		mSubmitButton = (Button) findViewById(R.id.login_submit);
		mCancelButton = (Button) findViewById(R.id.login_cancel);
		mStudentIdEditText = (EditText) findViewById(R.id.studentId);
		mStudentPasswordEditText = (EditText) findViewById(R.id.studentPassword);
	}

	// Ϊ��Ӧ�Ŀؼ���Ӽ�����
	private void AddListener() {
		mSubmitButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.login_submit:
			studentId = mStudentIdEditText.getText().toString();
			studentPassword = mStudentPasswordEditText.getText().toString();
			if (studentId == null || "".equals(studentId)) {
				Toast toast = Toast.makeText(Login.this, "ѧ�Ų���Ϊ��",
						Toast.LENGTH_LONG);
				toast.show();
				break;
			}
			if (studentPassword == null || "".equals(studentPassword)) {
				Toast toast = Toast.makeText(Login.this, "���벻��Ϊ��",
						Toast.LENGTH_LONG);
				toast.show();
				break;
			}
			intent.putExtra("isShow", true);
			intent.putExtra("studentId", studentId);
			intent.putExtra("studentPassword", studentPassword);
			intent.setClass(Login.this, Schedule.class);
			setResult(CANCELRESULTCODE, intent);
			finish();
			break;
		case R.id.login_cancel:
			intent.putExtra("isShow", false);
			intent.setClass(Login.this, Schedule.class);
			setResult(CANCELRESULTCODE, intent);
			finish();
			break;
		}
	}

}
