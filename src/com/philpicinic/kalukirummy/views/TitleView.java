package com.philpicinic.kalukirummy.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

public class TitleView extends View {

	private Bitmap titleGraphic;
	private int screenW;
	private int screenH;
	
	public TitleView(Context context) {
		super(context);
		titleGraphic =
				BitmapFactory.decodeResource(getResources(),
				R.drawable.title_graphic);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(titleGraphic, (screenW - titleGraphic.getWidth())/2, 0, null);
	}
	
	@Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        System.out.println("SCREEN W: " + screenW);
        System.out.println("SCREEN H: " + screenH);
    }

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}
}
