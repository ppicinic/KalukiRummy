package com.philpicinic.kalukirummy.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

/**
 * 
 * @author Phil Picinic
 * View for the Start Hand Button.
 */
public class StartHandButton extends View{

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap start;
	
	private int screenW;
	private int screenH;
	private int x;
	private int y;
	
	/**
	 * Constructor to create the view and fetch the bit map
	 * @param context
	 */
	public StartHandButton(Context context) {
		super(context);
		
		this.context = context;
		
		start = BitmapFactory.decodeResource(getResources(), R.drawable.start);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Resizes and Positions the button
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		
		// Scale Background
		int scaleW = (int) (screenW * 2 / 3);
		int scaleH = (int) (scaleW / 3);
		start = Bitmap.createScaledBitmap(start, scaleW, scaleH, false);
		x = (screenW / 2) - (start.getWidth() / 2);
		y = (screenH / 2) - (start.getHeight() / 2);
	}

	/**
	 * Draw the button on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(start, x, y, null);
	}
	
	/**
	 * Checks if player input collides with the button
	 * @param event the touch event
	 * @return true if the player touches, otherwise false
	 */
	public boolean checkCollision(MotionEvent event){
		int X = (int) event.getX();
		int Y = (int) event.getY();
		if(X >= x && X <= (x + start.getWidth()) && Y >= y && Y <= (y + start.getHeight())){
			return true;
		}
		return false;
	}
}
