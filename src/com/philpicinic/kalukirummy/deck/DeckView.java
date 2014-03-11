package com.philpicinic.kalukirummy.deck;

import com.philpicinic.kalukirummy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class DeckView extends View {

	private Context context;
	private Bitmap backCard;
	private int screenH;
	private int screenW;
	private int x;
	private int y;

	public DeckView(Context context) {
		super(context);
		this.context = context;

		backCard = BitmapFactory.decodeResource(getResources(),
				R.drawable.card_back);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		backCard = Bitmap.createScaledBitmap(backCard, scaleW, scaleH, false);
		x = (backCard.getWidth() * 0) + ( (backCard.getWidth() / 6) * (1));
		y = (backCard.getHeight()  / 6);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(backCard, x, y, null);
	}

}
