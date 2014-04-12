package com.philpicinic.kalukirummy.bot;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.db.GameState;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * 
 * @author Phil Picinic
 * 
 *         View class for the Bot Handles showing the number of cards the bot
 *         has remaining
 */
public class BotView extends View {

	private Context context;

	private Bitmap bot;
	private Bitmap cardCount;

	@SuppressWarnings("unused")
	private int screenH;
	private int screenW;
	private int scaleW;
	private int scaleH;

	private int x;
	private int y;

	private int x2;
	private int y2;

	/**
	 * Constructor creates the bot image and card count image
	 * 
	 * @param context
	 *            the context of the activity
	 */
	public BotView(Context context) {
		super(context);

		this.context = context;
		this.bot = BitmapFactory.decodeResource(getResources(), R.drawable.bot);
		if (GameState.getInstance(this.context).isChoice()) {
			this.cardCount = BitmapFactory.decodeResource(getResources(),
					R.drawable.botcard213);
		} else {
			this.cardCount = BitmapFactory.decodeResource(getResources(),
					R.drawable.botcard113);
		}
	}

	/**
	 * updates sizes if the display is changed resizes the images of the both
	 * 
	 * @param w
	 *            width of the screen
	 * @param h
	 *            height of the screen
	 * @param oldw
	 *            old width of the screen
	 * @param oldh
	 *            old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;

		// Scale and place bot image
		scaleW = (int) (screenW / 7);
		scaleH = (int) (scaleW);
		bot = Bitmap.createScaledBitmap(bot, scaleW, scaleH, false);
		x = (bot.getWidth() * 2) + ((bot.getWidth() / 6) * (3));
		y = (bot.getHeight() / 3);

		// Scale and place card count image
		scaleH = (int) (scaleW * 1.4);
		cardCount = Bitmap.createScaledBitmap(cardCount, scaleW, scaleH, false);
		x2 = (cardCount.getWidth() * 3) + ((cardCount.getWidth() / 6) * (4));
		y2 = (cardCount.getHeight() / 6);

	}

	/**
	 * Draw images onto the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bot, x, y, null);
		canvas.drawBitmap(cardCount, x2, y2, null);
	}

	public void update(int handSize) {
		String str = "botcard";
		if(GameState.getInstance(this.context).isChoice()){
			str += "2";
		}else{
			str += "1";
		}
		if (handSize < 10) {
			str += "0";
		}
		str += handSize;
		int resourceId = getResources().getIdentifier(str, "drawable",
				this.context.getPackageName());
		cardCount = BitmapFactory.decodeResource(getResources(), resourceId);
		cardCount = Bitmap.createScaledBitmap(cardCount, scaleW, scaleH, false);
		invalidate();
	}
}
