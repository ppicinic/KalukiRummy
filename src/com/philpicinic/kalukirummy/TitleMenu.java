package com.philpicinic.kalukirummy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TitleMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title_menu, menu);
		return true;
	}

}
