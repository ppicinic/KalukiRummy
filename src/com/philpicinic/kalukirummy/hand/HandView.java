package com.philpicinic.kalukirummy.hand;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.Deck;

public class HandView extends ViewGroup {

	private static final int CIH = 6;

	private int l;
	private int w;
	private int ol;
	private int ow;

	private Deck deck;
	private Context context;
	private ArrayList<VCard> cards;
	private VCard movingCard;
	private CardMove leftArrow;
	private CardMove rightArrow;
	private boolean cardMove;
	private boolean right;
	
	private int left;

	public HandView(Context context) {
		super(context);
		this.context = context;

		deck = new Deck();
		cards = new ArrayList<VCard>();
		for(int i = 0; i < 13; i++){
			Card card = deck.deal();
			//System.out.println(card.getRank());
			VCard vCard = new VCard(this.context, i, card.getSuit(), card.getRank());
			cards.add(vCard);
			this.addView(vCard);
		}
//		cards = new ArrayList<VCard>();
//		// Creates 13 cards in the players hand
//		cards = new ArrayList<VCard>();
//		VCard vCard = new VCard(context, 0, Suit.DIAMONDS, 1);
//		this.addView(vCard);
//		cards.add(vCard);
//		vCard = new VCard(context, 1, Suit.CLUBS, 1);
//		this.addView(vCard);
//		cards.add(vCard);
//
//		for (int i = 0; i < 11; i++) {
//			vCard = new VCard(context, (i + 2), Suit.SPADES, (i + 2));
//			this.addView(vCard);
//
//			cards.add(vCard);
//		}

		// Creates right arrow
		rightArrow = new CardMove(context, true);
		this.addView(rightArrow);

		// Creates left arrow
		leftArrow = new CardMove(context, false);
		//this.addView(leftArrow);

		cardMove = false;
		right = false;
		left = 0;
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		l = arg1;
		w = arg2;
		ol = arg3;
		ow = arg4;
		for (int i = 0; i < this.getChildCount(); i++) {
			this.getChildAt(i).layout(arg1, arg2, arg3, arg4);
		}

	}

	public boolean onTouchEvent(MotionEvent event) {
		//System.out.println(event);
		int e = event.getAction();
		//System.out.println(e);
		if (e == MotionEvent.ACTION_DOWN) {
			for (VCard card : cards) {
				if (card.detectCollision(event)) {
					this.bringChildToFront(card);
					movingCard = card;
					return true;
				}
			}
			if (rightArrow.isPressed(event)) {
				System.out.println("happenny0");
				cardMove = true;
				right = true;
				return true;
			}
			if (leftArrow.isPressed(event)) {
				cardMove = true;
				right = false;
				return true;
			}
		}
		if (e == MotionEvent.ACTION_MOVE) {
			if (movingCard != null) {
				// movingCard.onTouchEvent(event);
			}
		}
		if (e == MotionEvent.ACTION_UP) {
			// System.out.println("up");
			if (movingCard != null) {
				for (VCard card : cards) {
					if (!movingCard.equalsInHand(card)) {
						if (movingCard.collideWithCard(card)) {
							int tempPos = card.getHandPos();
							card.setHandPos(movingCard.getHandPos());
							movingCard.setHandPos(tempPos);
							movingCard = null;
							break;
						}
					}
				}
				if (movingCard != null) {
					movingCard.placeInHand();
					movingCard = null;
				}
			}
			// System.out.println("before");
			if (cardMove) {
				// System.out.println("test");
				for (VCard card : cards) {
					if (right) {
						card.setHandPos(card.getHandPos() - 1);
					} else {
						card.setHandPos(card.getHandPos() + 1);
					}
					// card.;
				}
				cardMove = false;
				System.out.println(right);
				if (right) {
					left += 1;
					if ((!leftArrow.isVisible()) && left >= 1) {
						this.addView(leftArrow);
						leftArrow.layout(l, w, ol, ow);
						leftArrow.flipVisibility();
						
						//this.inva
					}
					if(rightArrow.isVisible() && left >= 7){
						this.removeView(rightArrow);
						rightArrow.flipVisibility();
					}
				} else {
					left -= 1;
					if((!rightArrow.isVisible()) && left <= 6){
						this.addView(rightArrow);
						rightArrow.layout(l, w, ol, ow);
						rightArrow.flipVisibility();
					}
					if(leftArrow.isVisible() && left <= 0){
						this.removeView(leftArrow);
						leftArrow.flipVisibility();
					}

				}
				right = false;
				// this.invalidate();
			}
		}
		System.out.println(cardMove);
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		System.out.println("inter");
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			for (VCard card : cards) {
				if (card.detectCollision(event)) {
					this.bringChildToFront(card);
					movingCard = card;
					// return true;
				}
			}
		}
		if (e == MotionEvent.ACTION_UP) {
			System.out.println("up");
			if (movingCard != null) {
				for (VCard card : cards) {
					if (!movingCard.equalsInHand(card)) {
						if (movingCard.collideWithCard(card)) {
							int tempPos = card.getHandPos();
							card.setHandPos(movingCard.getHandPos());
							movingCard.setHandPos(tempPos);
							movingCard = null;
							break;
						}
					}
				}
				if (movingCard != null) {
					movingCard.placeInHand();
					movingCard = null;
				}
			}
		}
		return false;
	}

}
