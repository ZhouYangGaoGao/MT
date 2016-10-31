package com.matao.matao;

import android.app.Activity;
import android.os.Bundle;

import com.matao.utils.Logger;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Logger.e("ddddd", "da打印啊");

	}
}
