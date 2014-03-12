package com.philpicinic.kalukirummy.deck;

import com.philpicinic.kalukirummy.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * 
 * @author Phil Picinic
 *
 * View Class of the Discard Pile
 * Shows the top card of the discard Pile
 */
public class DiscardView extends View{
	
	@SuppressWarnings("unused")
	private Context context;
	private Bitmap discardCard;
	@SuppressWarnings("unused")
	private int screenH;
	private int screenW;
	private int x;
	private int y;

	public DiscardView(Context context) {
		super(context);
		this.context = context;

		discardCard = BitmapFactory.decodeResource(getResources(),
				R.drawable.card102);
	}

	/**
	 * updates sizes if the display is changed
	 * @param w width of the screen
	 * @param h height of the screen
	 * @param oldw old width of the screen
	 * @param oldh old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		discardCard = Bitmap.createScaledBitmap(discardCard, scaleW, scaleH, false);
		x = (discardCard.getWidth()) + ( (discardCard.getWidth() / 6) * (2));
		y = (discardCard.getHeight()  / 6);
	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(discardCard, x, y, null);
	}
}
