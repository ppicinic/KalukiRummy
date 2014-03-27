package com.philpicinic.kalukirummy.score;

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
 * View class of the Score Card
 */
public class ScoreCardView extends View{

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap scorecard;
	
	private int screenW;
	@SuppressWarnings("unused")
	private int screenH;
	
	private int x;
	private int y;
	
	/**
	 * Constructor creates the image of the score card
	 * @param context
	 */
	public ScoreCardView(Context context) {
		super(context);
		
		this.context = context;
		scorecard = BitmapFactory.decodeResource(getResources(), R.drawable.scorecard);
	}
	
	/**
	 * updates sizes if the display is changed
	 * resizes and places score card
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
		
		// Scale and Place score card image
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		scorecard = Bitmap.createScaledBitmap(scorecard, scaleW, scaleH, false);
		x = screenW - (scorecard.getWidth() * 7 / 6);
		y = (scorecard.getHeight()  / 6);
	}

	/**
	 * Draw image onto the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(scorecard, x, y, null);
	}

}
