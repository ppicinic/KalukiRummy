package com.philpicinic.kalukirummy.meld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

public class UndoButton extends View {

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap button;
	private int x;
	private int y;

	private int screenW;
	private int screenH;

	public UndoButton(Context context) {
		super(context);

		this.context = context;

		button = BitmapFactory.decodeResource(getResources(),
				R.drawable.undo_button);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 12);
		int scaleH = (int) (scaleW);
		button = Bitmap.createScaledBitmap(button, scaleW, scaleH, false);
		x = screenW - (int) (button.getWidth() * 5 / 4);
		y = screenH - (int) (button.getHeight() * 5 );
	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(button, x, y, null);
	}
	
	public boolean checkCollision(MotionEvent event){
		System.out.println("get here");
		int X = (int) event.getX();
		int Y = (int) event.getY();
		
		if(X > x && X < (x + button.getWidth()) && Y > y && Y < (y + button.getHeight())){
			System.out.println("get here 2");
			return true;
		}
		return false;
	}

}
