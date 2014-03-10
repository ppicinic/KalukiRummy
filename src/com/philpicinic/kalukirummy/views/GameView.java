package com.philpicinic.kalukirummy.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.philpicinic.kalukirummy.R;

public class GameView extends View {

	private Context context;
	private Paint redPaint;
	private int circleX;
	private int circleY;
	private float radius;
	private Bitmap deck;
	private Bitmap discardPile;
	private int screenW;
	private int screenH;

	public GameView(Context context) {
		super(context);
		this.context = context;
//		redPaint = new Paint();
//		redPaint.setAntiAlias(true);
//		redPaint.setColor(Color.RED);
//		circleX = 100;
//		circleY = 100;
//		radius = 30;
		deck = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		//deck.setWidth(50);
		discardPile = BitmapFactory.decodeResource(getResources(), R.drawable.card102);
		
		//System.out.println("Success");
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		Bitmap temp = deck;
		int scaleW = (int) (screenW/8);
		int scaleH = (int) (scaleW*1.28);
		deck = Bitmap.createScaledBitmap(temp, scaleW, scaleH, false);
		discardPile = Bitmap.createScaledBitmap(discardPile, scaleW, scaleH, false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawCircle(circleX, circleY, radius, redPaint);
		canvas.drawBitmap(deck, (screenW / 100), (screenH / 200), null);
		canvas.drawBitmap(discardPile, ((screenW / 50) + discardPile.getWidth() ), (screenH / 200), null);
	}

	public boolean onTouchEvent(MotionEvent event) {
//		int eventaction = event.getAction();
//		int X = (int) event.getX();
//		int Y = (int) event.getY();
//		
//		switch (eventaction) {
//		case MotionEvent.ACTION_DOWN:
//			break;
//		case MotionEvent.ACTION_MOVE:
//			circleX = X;
//			circleY = Y;
//			break;
//		case MotionEvent.ACTION_UP:
//			circleX = X;
//			circleY = Y;
//			break;
//		}
//		invalidate();
		return true;
	}

}
