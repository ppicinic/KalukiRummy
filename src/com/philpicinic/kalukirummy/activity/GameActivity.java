package com.philpicinic.kalukirummy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.philpicinic.kalukirummy.views.GameView;

/**
 *
 * @author Phil Picinic
 * Game Activity is the Activity for the game screen.
 *
 */
public class GameActivity extends Activity {
	
    /** 
     * Called when the activity is first created. 
     * Creates and sets the GameView
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gView = new GameView(this);
        gView.setKeepScreenOn(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(gView);
    }
}

