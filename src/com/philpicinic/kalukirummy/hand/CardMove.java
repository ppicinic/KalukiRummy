package com.philpicinic.kalukirummy.hand;

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
 * CardMove is the View for the arrows to sift through a player's hand
 */
public class CardMove extends View {

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap arrow;
	
	private int screenW;
	private int screenH;
	
	private int x;
	private int y;
	
	private boolean right;

	/**
	 * Constructor calls the View Constructor
	 * THIS SHOULD NOT BE USED
	 * @param context
	 */
	public CardMove(Context context) {
		super(context);
	}
	
	/**
	 * Constructor creates the Bitmap and decides the side to place it on
	 * @param context the context of the activity
	 * @param right if the card is on the right (true) or left (false)
	 */
	public CardMove(Context context, boolean right){
		super(context);
		this.right = right;
		if (this.right) {
			arrow = BitmapFactory.decodeResource(getResources(),
					R.drawable.arrow_right);
		} else {
			arrow = BitmapFactory.decodeResource(getResources(),
					R.drawable.arrow_left);
		}
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
		int scaleW = (int) (screenW / 12);
		int scaleH = (int) (scaleW);
		arrow = Bitmap.createScaledBitmap(arrow, scaleW, scaleH, false);
		y = screenH - (arrow.getHeight() * 4);
		if (this.right) {
			x = screenW - (arrow.getWidth() * 5 / 4);
		} else {
			x = arrow.getWidth() / 4;
		}
	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(arrow, x, y, null);
	}

}
