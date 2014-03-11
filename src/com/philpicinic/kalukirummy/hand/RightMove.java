package com.philpicinic.kalukirummy.hand;

import com.philpicinic.kalukirummy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class RightMove extends View {

	private Context context;
	private Bitmap arrow;
	private int screenW;
	private int screenH;
	private int x;
	private int y;
	
	public RightMove(Context context) {
		super(context);
		arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 12);
		int scaleH = (int) (scaleW);
		arrow = Bitmap.createScaledBitmap(arrow, scaleW, scaleH, false);
		x = screenW - (arrow.getWidth() * 5 / 4);
		y = screenH - (arrow.getHeight() * 4);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(arrow, x, y, null);
	}

}
