package com.philpicinic.kalukirummy.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

public class StartHandButton extends View{

	private Context context;
	private Bitmap start;
	
	private int screenW;
	private int screenH;
	private int x;
	private int y;
	
	public StartHandButton(Context context) {
		super(context);
		
		this.context = context;
		
		start = BitmapFactory.decodeResource(getResources(), R.drawable.start);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		
		// Scale Background
		int scaleW = (int) (screenW);
		int scaleH = (int) (scaleW * .5);
		start = Bitmap.createScaledBitmap(start, scaleW, scaleH, false);
		x = (screenW / 2) - (start.getWidth() / 2);
		y = (screenH / 2) - (start.getHeight() / 2);
	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(start, x, y, null);
	}
	
	public boolean checkCollision(MotionEvent event){
		int X = (int) event.getX();
		int Y = (int) event.getY();
		System.out.println("check start collision");
		if(X >= x && X <= (x + start.getWidth()) && Y >= y && Y <= (y + start.getHeight())){
			System.out.println("pass");
			return true;
		}
		return false;
	}
}
