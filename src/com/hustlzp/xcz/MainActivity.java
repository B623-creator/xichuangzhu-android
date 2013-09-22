package com.hustlzp.xcz;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DataBaseHelper myDbHelper;
	private TextView mWorkContentTextView;

	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myDbHelper = new DataBaseHelper(MainActivity.this);

		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		Cursor c = myDbHelper.getCursor();
		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("_id"));
			String title = c.getString(c.getColumnIndex("title"));
			String content = c.getString(c.getColumnIndex("content"));
			Log.i("db", "_id=>" + _id + ", title=>" + title + ", content=>"
					+ content);
		}

		myDbHelper.close();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar bar = getActionBar();
			bar.setDisplayShowTitleEnabled(false);
			/*
			 * bar.setBackgroundDrawable(new ColorDrawable(Color
			 * .parseColor("#aaaaaa")));
			 */
		}

		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/simsun.ttc");

		((TextView) findViewById(R.id.work_title)).setTypeface(typeface);
		((TextView) findViewById(R.id.work_title)).getPaint().setFakeBoldText(
				true);

		((TextView) findViewById(R.id.work_info)).setTypeface(typeface);
		((TextView) findViewById(R.id.work_info)).getPaint().setFakeBoldText(
				true);

		mWorkContentTextView = (TextView) findViewById(R.id.work_content);
		mWorkContentTextView.setTypeface(typeface);
		mWorkContentTextView.getPaint().setFakeBoldText(true);
		mWorkContentTextView
				.setText(Html
						.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;����ҹ�Ż�ǧ��<sup><small><small>��1��</small></small></sup>�������䡢������<sup><small><small>��2��</small></small></sup>�����������·�����������������ת��һҹ������<sup><small><small>��3��</small></small></sup>��  ���ѩ���ƽ���<sup><small><small>��4��</small></small></sup>��Ц��ӯӯ����ȥ������Ѱ��ǧ�ٶȣ���Ȼ���ף�����ȴ�ڣ��ƻ���ɺ��<sup><small><small>��5��</small></small></sup>��"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
