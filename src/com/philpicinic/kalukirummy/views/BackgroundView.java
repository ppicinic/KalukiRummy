package com.philpicinic.kalukirummy.views;

import com.philpicinic.kalukirummy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * 
 * @author Phil Picinic
 *
 * Background View of the game view
 */
public class BackgroundView extends View {

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap bg;
	
	private int screenW;
	private int screenH;
	
	/**
	 * Constructor creates the background bitmap
	 * @param context the context of the activity
	 */
	public BackgroundView(Context context) {
		super(context);
		
		this.context = context;
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
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
		
		// Scale Background
		int scaleW = (int) (screenW);
		int scaleH = (int) (screenH);
		bg = Bitmap.createScaledBitmap(bg, scaleW, scaleH, false);

	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bg, 0, 0, null);
	}

}
