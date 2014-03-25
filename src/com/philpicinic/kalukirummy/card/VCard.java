package com.philpicinic.kalukirummy.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author Phil Picinic
 * 
 *         This is the View module of the Card This class is responsible for
 *         drawing the card and updating its position from user movement or move
 *         calls from Hand View (eventually)
 */
public class VCard extends View implements Comparable<VCard>{

	private Context context;
	private Bitmap card;
	private Card cardModel;

	private int screenW;
	private int screenH;

	private int x;
	private int y;

	private boolean touched;

	private int pos;

	private boolean inHand;
	@SuppressWarnings("unused")
	private boolean inPlaceArea; 

	/**
	 * Constructor calls the view constructor THIS SHOULD NOTE BE USED
	 * 
	 * @param context
	 *            the context of the activity
	 */
	public VCard(Context context) {
		super(context);
	}

	/**
	 * Constructor sets up the Card View choosing the right bitmap based on suit
	 * and rank and placing it in the right position
	 * 
	 * @param context
	 *            the context of the activity
	 * @param pos
	 *            the hand position of the card
	 * @param suit
	 *            the suit of the card
	 * @param rank
	 *            the rank of the card TODO handle spawning cards in spots other
	 *            than the player's hand
	 */
	public VCard(Context context, int pos, Card cardModel) {
		super(context);
		this.context = context;

		this.pos = pos;
		this.cardModel = cardModel;
		Suit suit = this.cardModel.getSuit();
		int rank = this.cardModel.getRank();

		// Create the card name
		String cardName = "card";
		cardName += (suit.ordinal() + 1);
		String rTemp = "" + rank;

		if (rTemp.length() == 1) {
			rTemp = "0" + rTemp;
		}
		cardName += rTemp;

		// Get the resource id of the card and get the Bitmap
		int resourceId = getResources().getIdentifier(cardName, "drawable",
				this.context.getPackageName());
		card = BitmapFactory.decodeResource(getResources(), resourceId);
		inHand = true;
	}

	/**
	 * updates sizes if the display is changed rescales the card and updates in
	 * position TODO See Above
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
		Bitmap temp = card;
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		card = Bitmap.createScaledBitmap(temp, scaleW, scaleH, false);
		x = (card.getWidth() * pos) + ((card.getWidth() / 6) * (pos))
				+ (card.getWidth() / 10);
		y = screenH - (card.getHeight() * 7 / 6);
	}

	/**
	 * Draw the card on the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(card, x, y, null);
	}

	public void setTossPos(){
		inHand = false;
		x = (card.getWidth() * 1) + ( (card.getWidth() / 6) * (2));
		y = (card.getHeight()  / 6);
		invalidate();
	}
	
	public int getMyX(){
		return x;
	}
	
	public int getMyY(){
		return y;
	}
	
	public int getMyWidth(){
		return card.getWidth();
	}
	
	public int getMyHeight(){
		return card.getHeight();
	}
	
	public void setMeldPlacePos(int pos){
		touched = false;
		this.pos = pos;
		inHand = false;
		inPlaceArea = true;
		x = (card.getWidth() * pos) + (int) (card.getWidth() * .77) + ((card.getWidth() / 8) * pos );
		y = screenH - (int) (card.getHeight() * 2.2285);
		invalidate();
	}
	/**
	 * Checks if the player is moving the card and updates the cards location1
	 * 
	 * @param event
	 *            the action the user does
	 */
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("yo hey 1");
		if(!inHand){
			System.out.println("yo hey");
			return false;
		}
		System.out.println("yo hey 3");
		// Get event action and location
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			// Check if the player presses on a card, initiate moving the card
			if (X > x && X < (x + card.getWidth()) && Y > y
					&& Y < (y + card.getHeight())) {
				touched = true;
				x = X - (card.getWidth() / 2);
				y = Y - (card.getHeight() * 3 / 4);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// Move the card to where the player's finger is
			if (touched) {
				x = X - (card.getWidth() / 2);
				y = Y - (card.getHeight() * 3 / 4);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			// The player has let go of the card, put it back to the hand (there
			// is no where to place it yet
			if (touched) {
				touched = false;
				invalidate();
			}
			break;
		}
		// invalidate();
		return touched;
	}

	public int getHandPos() {
		return this.pos;
	}

	/**
	 * Detects if the event has a collision with the card
	 * 
	 * @param event
	 *            the user event
	 * @return true if there was a collision detected, false otherwise
	 */
	public boolean detectCollision(MotionEvent event) {
		int X = (int) event.getX();
		int Y = (int) event.getY();
		if (X > x && X < (x + card.getWidth()) && Y > y
				&& Y < (y + card.getHeight())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the position in the hand of the card Updates the visual location
	 * 
	 * @param p
	 *            the new position of the card
	 */
	public void setHandPos(int p) {
		pos = p;
		inHand = true;
		inPlaceArea = false;
		//touched = false;
		placeInHand();
	}

	public void placeInHand() {
		x = (card.getWidth() * pos) + ((card.getWidth() / 6) * (pos))
				+ (card.getWidth() / 10);
		y = screenH - (card.getHeight() * 7 / 6);
		invalidate();

	}

	public boolean collideWithCard(VCard arg0) {
		if ((this.x >= arg0.x && this.x < (arg0.x + (card.getWidth() / 2)))
				|| (((this.x + card.getWidth()) > (arg0.x + (card.getWidth() / 2))) && (this.x + card
						.getWidth()) <= (arg0.x + card.getWidth()))) {
			if ((this.y >= arg0.y && this.y < (arg0.y + (card.getHeight() / 2)))
					|| (((this.y + card.getHeight()) > (arg0.y + (card
							.getHeight() / 2))) && (this.y + card.getHeight()) <= (arg0.y + card
							.getHeight()))) {
				return true;
			}
		}
		return false;
	}

	public boolean equalsInHand(VCard arg0) {
		return this.pos == arg0.pos;
	}
	
	public Card getCard(){
		return cardModel;
	}

	@Override
	public int compareTo(VCard arg0) {
		return getCard().compareTo(arg0.getCard());
	}
	
	
}
