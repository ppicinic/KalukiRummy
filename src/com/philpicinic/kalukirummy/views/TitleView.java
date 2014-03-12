package com.philpicinic.kalukirummy.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.activity.GameActivity;

/**
 *
 * @author Phil Picinic
 * View class for the title screen
 */
public class TitleView extends View {

	private Bitmap titleGraphic;
	private int screenW;
	private int screenH;
	private Bitmap playButton;
	private Bitmap playButtonDown;
	private boolean playButtonPressed;

	private Bitmap optionsButton;
	private Bitmap optionsButtonPressed;
	private boolean isOptionsPressed;
	private Context context;
	

	/**
	 * Constructor creates all the images of the title screen
	 * @param context the context of the activity
	 */
	public TitleView(Context context) {
		super(context);
		this.context = context;
		titleGraphic = BitmapFactory.decodeResource(getResources(),
				R.drawable.title_graphic);
		playButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.play_button);
		playButtonDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.play_button_pressed);
		optionsButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.options_button);
		optionsButtonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.options_button_pressed);
		
	}

	/**
	 * Draws all the images onto the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(titleGraphic,
				(screenW - titleGraphic.getWidth()) / 2, 0, null);
		if (playButtonPressed) {
			canvas.drawBitmap(playButtonDown,
					(screenW - playButton.getWidth()) / 2,
					(int) (screenH * 0.6), null);
		} else {
			canvas.drawBitmap(playButton,
					(screenW - playButton.getWidth()) / 2,
					(int) (screenH * 0.6), null);
		}
		if (isOptionsPressed) {
			canvas.drawBitmap(optionsButtonPressed,
					(screenW - optionsButton.getWidth()) / 2,
					(int) (screenH * 0.8), null);
		} else {
			canvas.drawBitmap(optionsButton,
					(screenW - optionsButton.getWidth()) / 2,
					(int) (screenH * 0.8), null);
		}
	}
	
	/**
	 * updates sizes if the display is changed
	 * @param w width of the screen
	 * @param h height of the screen
	 * @param oldw old width of the screen
	 * @param oldh old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}

	/**
	 * Handles User input and collision detects the buttons
	 * @param event the event of the user's input
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN:
			if (X > (screenW - playButton.getWidth()) / 2
					&& X < ((screenW - playButton.getWidth()) / 2)
							+ playButton.getWidth()
					&& Y > (int) (screenH * 0.6)
					&& Y < (int) (screenH * 0.6) + playButton.getHeight()) {
				playButtonPressed = true;
			}

			if (X > (screenW - playButton.getWidth()) / 2
					&& X < ((screenW - playButton.getWidth()) / 2)
							+ playButton.getWidth()
					&& Y > (int) (screenH * 0.8)
					&& Y < (int) (screenH * 0.8) + playButton.getHeight()) {
				isOptionsPressed = true;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			if (playButtonPressed) {
				Intent gameIntent = new Intent(context, GameActivity.class);
				context.startActivity(gameIntent);
			} else if (isOptionsPressed) {
				// TODO Go To Options Screen
			}
			isOptionsPressed = false;
			playButtonPressed = false;
			break;
		}
		invalidate();
		return true;
	}
}
