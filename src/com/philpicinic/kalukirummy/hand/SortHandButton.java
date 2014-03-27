package com.philpicinic.kalukirummy.hand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

public class SortHandButton extends View {

	private Context context;
	private Bitmap button;
	
	private int screenW;
	private int screenH;

	private int x;
	private int y;

	
	public SortHandButton(Context context) {
		super(context);
		
		this.context = context;
		button = BitmapFactory.decodeResource(getResources(), R.drawable.play_button);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * updates sizes if the display is changed
	 * 
	 * @param w
	 *            width of the screen
	 * @param h
	 *            height of the screen
	 * @param oldw
	 *            old width of the screen
	 * @param oldh
	 *            old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 2);
		int scaleH = (int) (scaleW / 4);
		button = Bitmap.createScaledBitmap(button, scaleW, scaleH, false);
		x = button.getWidth() / 2;
		y = screenH - (int)(button.getHeight() * 3);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(button, x, y, null);
	}
	
	public boolean checkCollision(MotionEvent event){
		int X = (int) event.getX();
		int Y = (int) event.getY();
		
		if(X > x && X < (x + button.getWidth()) && Y > y && Y < (y + button.getHeight())){
			return true;
		}
		return false;
	}
}
