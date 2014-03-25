package com.philpicinic.kalukirummy.meld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlaceArea extends View {

	private Context context;
	private Bitmap meldPlace;
	private int x;
	private int y;

	private int screenW;
	private int screenH;

	public MeldPlaceArea(Context context) {
		super(context);

		this.context = context;

		meldPlace = BitmapFactory.decodeResource(getResources(),
				R.drawable.meldplace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 1.25);
		int scaleH = (int) (scaleW / 4.425);
		meldPlace = Bitmap.createScaledBitmap(meldPlace, scaleW, scaleH, false);
		y = screenH - (int) (meldPlace.getHeight() * 2.25);
		x = screenW / 10;
	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(meldPlace, x, y, null);
	}

	public boolean checkCollisionByCard(VCard card) {
		if ((card.getMyX() + (card.getMyWidth() / 2)) > x
				&& (card.getMyX() + (card.getMyWidth() / 2)) < (x + meldPlace
						.getWidth())
				&& (card.getMyY() + (card.getMyHeight() / 2)) > y
				&& (card.getMyY() + (card.getMyHeight() / 2)) < (y + meldPlace
						.getHeight())) {
			return true;
		}
		return false;
	}

}
