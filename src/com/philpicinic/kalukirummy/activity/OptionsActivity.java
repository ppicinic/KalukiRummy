package com.philpicinic.kalukirummy.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.db.DatabaseAdapter;
import com.philpicinic.kalukirummy.db.GameState;

/**
 * 
 * @author Phil Picinic
 * Options Activity lets user define settings and save them with sqllite.
 */
public class OptionsActivity extends Activity {

	private boolean sound;
	private boolean choice;

	/**
	 * Creates the view for the options activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// gets the previous user settings
		GameState state = GameState.getInstance(this);
		state.update();
		choice = state.isChoice();
		sound = state.isSound();
		
		setContentView(R.layout.activity_options);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// selects the card choice to last user setting
		RadioButton rb0 = (RadioButton) findViewById(R.id.radio0);
		RadioButton rb1 = (RadioButton) findViewById(R.id.radio1);
		if(choice){
			rb0.setChecked(true);
			rb1.setChecked(false);
		}else{
			rb0.setChecked(false);
			rb1.setChecked(true);
		}
		
		// Sets what happens when the radio buttons on checked
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (group.findViewById(R.id.radio0).getId() == checkedId) {
							choice = true;
						} else {
							choice = false;
						}
					}
				});

		// Sets the toggle button to last user setting and sets what occurs when it is clicked
		final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleButton.setChecked(sound);
		
		toggleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(toggleButton.isChecked()){
					sound = true;
				}else{
					sound = false;
				}
			}
		});
		
		// Saves the settings when the ok button is pressed
		Button myButton = (Button) findViewById(R.id.button1);
		final OptionsActivity context = this;
		myButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DatabaseAdapter adapter = new DatabaseAdapter(context);
				adapter.open();
				adapter.insertOrUpdateRecord(sound, choice);
				adapter.close();
				context.finish();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
