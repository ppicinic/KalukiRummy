package com.philpicinic.kalukirummy.card;

import com.philpicinic.kalukirummy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class VCard extends View {

	// private Card card;
	private String cardName;
	private Context context;
	private Bitmap card;
	private int screenW;
	private int screenH;
	private int x;
	private int y;
	private boolean touched;
	private int pos;
	private boolean inHand;

	public VCard(Context context, int pos, Suit suit, int rank) {
		super(context);
		this.context = context;
		
		touched = false;
		this.pos = pos;
		String cardName = "card";
		cardName += (suit.ordinal() + 1);
		String rTemp = "" + rank;
		if(rTemp.length() == 1){
			rTemp = "0" + rTemp;
		}
		cardName += rTemp;
		int resourceId = getResources().getIdentifier(cardName, "drawable", this.context.getPackageName());
		card = BitmapFactory.decodeResource(getResources(), resourceId);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		Bitmap temp = card;
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		card = Bitmap.createScaledBitmap(temp, scaleW, scaleH, false);
		x = (card.getWidth() * pos) + ( (card.getWidth() / 6) * (pos + 1));
		y = screenH - (card.getHeight() * 7 / 6);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(card, x, y, null);
	}

	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("View");
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			if (X > x && X < (x + card.getWidth()) && Y > y && Y < (y + card.getHeight()) ) {
				touched = true;
				x = X - (card.getWidth() / 2);
				y = Y - (card.getHeight());
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (touched) {
				x = X - (card.getWidth() / 2);
				y = Y - (card.getHeight());
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (touched) {
				x = (card.getWidth() * pos) + ( (card.getWidth() / 6) * (pos + 1));
				y = screenH - (card.getHeight() * 7 / 6);
				touched = false;
				invalidate();
			}
			break;
		}
		
		return touched;
	}
	
	public void setHandPos(int p){
		pos = p;
		x = (card.getWidth() * pos) + ( (card.getWidth() / 6) * (pos + 1));
		y = screenH - (card.getHeight() * 7 / 6);
		invalidate();
	}

	// public Card getCard() {
	// return card;
	// }

	public String getCardName() {
		return cardName;
	}
}
