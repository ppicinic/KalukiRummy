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
 * DeckView class is a View Class for the Deck. 
 * DeckView class shows the top card of the Deck
 */
public class DeckView extends View {

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap backCard;
	
	@SuppressWarnings("unused")
	private int screenH;
	private int screenW;
	
	private int x;
	private int y;

	/**
	 * Constructor for the DeckView
	 * Creates the the Bitmap of the back of the card.
	 * @param context the context of the Activity it exists in
	 */
	public DeckView(Context context) {
		super(context);
		this.context = context;

		backCard = BitmapFactory.decodeResource(getResources(),
				R.drawable.card_back);
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
		// Scale the card bitmap
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		backCard = Bitmap.createScaledBitmap(backCard, scaleW, scaleH, false);
		// Place the card bitmap in the top left corner
		x = (backCard.getWidth() * 0) + ( (backCard.getWidth() / 6) * (1));
		y = (backCard.getHeight()  / 6);
	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(backCard, x, y, null);
	}

}
