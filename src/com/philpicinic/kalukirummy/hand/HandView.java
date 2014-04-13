package com.philpicinic.kalukirummy.hand;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

/**
 * 
 * @author Phil Picinic
 *
 * ViewGroup of the player's hand
 */
public class HandView extends ViewGroup {

	private int l;
	private int w;
	private int ol;
	private int ow;

	private Context context;
	private ArrayList<VCard> cards;
	private VCard movingCard;
	private CardMove leftArrow;
	private CardMove rightArrow;
	private boolean cardMove;
	private boolean right;
	private boolean toBeSorted;
	private boolean seeSort;

	private SortHandButton sortHandBtn;

	private int left;

	/**
	 * Constructor
	 * @param context the context of the activity
	 */
	public HandView(Context context) {
		super(context);
		this.context = context;

		cards = new ArrayList<VCard>();

		// Creates right arrow
		rightArrow = new CardMove(context, true);
		// this.addView(rightArrow);

		// Creates left arrow
		leftArrow = new CardMove(context, false);
		// this.addView(leftArrow);

		cardMove = false;
		right = false;
		left = 0;

		sortHandBtn = new SortHandButton(this.context);
		seeSort = false;
	}

	/**
	 * Deal a card to the player's hand
	 * @param card the card being dealt
	 */
	public void deal(Card card) {

		left = 0;
		VCard temp = new VCard(this.context, 0, card);
		cards.add(0, temp);
		this.addView(temp);
		temp.layout(l, w, ol, ow);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setHandPos(left + i);
		}
		if (leftArrow.isVisible()) {
			this.removeView(leftArrow);
			leftArrow.flipVisibility();
		}
		if (!rightArrow.isVisible() && cards.size() > (left + 6)) {
			this.addView(rightArrow);
			rightArrow.layout(l, w, ol, ow);
			rightArrow.flipVisibility();
		}
	}

	/**
	 * Hand is created
	 */
	public void handCreated() {
		if (!seeSort) {
			this.addView(sortHandBtn);
			seeSort = true;
		}
	}

	/**
	 * Hand is ended does nothing
	 * TODO DEPRECATED!?
	 */
	public void handEnded() {
		if (seeSort) {

		}
	}

	/**
	 * Return a card to the player's hand
	 * @param returnToHand the card being returned
	 */
	public void deal(VCard returnToHand) {
		left = 0;
		cards.add(0, returnToHand);
		this.addView(returnToHand);
		returnToHand.layout(l, w, ol, ow);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setHandPos(left + i);
		}
		if (leftArrow.isVisible()) {
			this.removeView(leftArrow);
			leftArrow.flipVisibility();
		}
		if (!rightArrow.isVisible() && cards.size() > (left + 6)) {
			this.addView(rightArrow);
			rightArrow.layout(l, w, ol, ow);
			rightArrow.flipVisibility();
		}
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
		sortHandBtn.layout(l, w, ol, ow);
	}

	/**
	 * Get the card the player is moving
	 * @return the card
	 */
	public VCard getMovingCard() {
		return movingCard;
	}

	public boolean onTouchEvent(MotionEvent event) {
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			for (VCard card : cards) {
				if (card.detectCollision(event)) {
					this.bringChildToFront(card);
					movingCard = card;
					return true;
				}
			}
			if (rightArrow.isPressed(event)) {
				cardMove = true;
				right = true;
				return true;
			}
			if (leftArrow.isPressed(event)) {
				cardMove = true;
				right = false;
				return true;
			}

			if (seeSort && sortHandBtn.checkCollision(event)) {
				return toBeSorted = true;
			}
		}
		if (e == MotionEvent.ACTION_UP) {
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

			if (cardMove) {
				for (VCard card : cards) {
					if (right) {
						card.setHandPos(card.getHandPos() - 1);
					} else {
						card.setHandPos(card.getHandPos() + 1);
					}
				}
				cardMove = false;
				if (right) {
					left += 1;
					if ((!leftArrow.isVisible()) && left >= 1) {
						this.addView(leftArrow);
						leftArrow.layout(l, w, ol, ow);
						leftArrow.flipVisibility();
					}
					if (rightArrow.isVisible() && (left + 6) >= cards.size()) {
						this.removeView(rightArrow);
						rightArrow.flipVisibility();
					}
				} else {
					left -= 1;
					if ((!rightArrow.isVisible()) && (left + 6) < cards.size()) {
						this.addView(rightArrow);
						rightArrow.layout(l, w, ol, ow);
						rightArrow.flipVisibility();
					}
					if (leftArrow.isVisible() && left <= 0) {
						this.removeView(leftArrow);
						leftArrow.flipVisibility();
					}

				}
				right = false;
			}
			if (toBeSorted) {
				sortHand();
				toBeSorted = false;
				return true;
			}
		}
		this.invalidate();
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			if (seeSort && sortHandBtn.checkCollision(event)) {
				return true;
			}
			for (VCard card : cards) {
				if (card.detectCollision(event)) {
					this.bringChildToFront(card);
					movingCard = card;
					handEnded();
				}
			}
		}
		if (e == MotionEvent.ACTION_UP) {
			if (movingCard != null) {
				for (VCard card : cards) {
					if (!movingCard.equalsInHand(card)) {
						if (movingCard.collideWithCard(card)) {
							int tempPos = movingCard.getHandPos();
							cards.set(tempPos + left, card);
							cards.set(card.getHandPos() + left, movingCard);
							movingCard.setHandPos(card.getHandPos());
							card.setHandPos(tempPos);
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

	/**
	 * Remove the moving card from the player's hand
	 * TODO this might help from serializing the cards?!
	 */
	public void removeMovingCard() {
		int tempPos = movingCard.getHandPos();
		tempPos += left;
		cards.remove(tempPos);

		if ((left + 6) > cards.size()) {
			left = cards.size() - 6;
			if (left == 0 && leftArrow.isVisible()) {
				this.removeView(leftArrow);
				leftArrow.flipVisibility();
			}
		}
		if (cards.size() <= 6) {
			left = 0;
			if (leftArrow.isVisible()) {
				this.removeView(leftArrow);
				leftArrow.flipVisibility();
			}
		}

		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setHandPos(i - left);
		}

		if (rightArrow.isVisible()) {
			if (cards.size() <= left + 6) {
				rightArrow.flipVisibility();
				this.removeView(rightArrow);
			}
		}

		this.removeView(movingCard);
		movingCard = null;
	}

	/**
	 * Sort the player's hand
	 */
	public void sortHand() {
		Collections.sort(cards);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setHandPos(i - left);
		}
	}

	/**
	 * Remove a card
	 * @param card the card to remove
	 * TODO Also might help from serialization
	 */
	public void removeCard(Card card) {
		for (int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i).getCard();
			if (temp.getRank() == card.getRank()
					&& card.getSuit() == temp.getSuit()) {
				this.removeView(cards.get(i));
				cards.remove(i);
				if (left == cards.size() - 5) {
					left--;
					if (left == 0 && leftArrow.isVisible()) {
						this.removeView(leftArrow);
						leftArrow.flipVisibility();
					}
				}
				if (cards.size() <= 6) {
					left = 0;
					if (leftArrow.isVisible()) {
						this.removeView(leftArrow);
						leftArrow.flipVisibility();
					}
				}
				for (int j = 0; j < cards.size(); j++) {
					cards.get(j).setHandPos(j - left);
				}
				return;
			}
		}
	}

	/**
	 * Remove a joker from the player's hand
	 * @param card the joker card to be removed
	 * TODO Also might help from card serialization
	 */
	public void removeJokerCard(VCard card) {
		for (int i = 0; i < cards.size(); i++) {
			VCard temp = cards.get(i);
			if (temp.getCard().isJoker()) {
				if (temp.getCard().getRank() == card.getCard().getRank()
						&& temp.getCard().getSuit().ordinal() == card.getCard()
								.getSuit().ordinal()) {
					cards.remove(i);
					this.removeView(card);
					for (int j = 0; j < cards.size(); j++) {
						cards.get(j).setHandPos(j - left);
					}
					return;
				}
			}
		}
	}

	/**
	 * Get the size of the player's hand
	 * @return the size
	 */
	public int handSize() {
		return cards.size();
	}

	/**
	 * Hand end game method
	 * @return all the player's cards
	 */
	public ArrayList<Card> endGame() {
		ArrayList<Card> temp = new ArrayList<Card>();
		for(VCard card : cards){
			this.removeView(card);
			temp.add(card.getCard());
		}
		cards = new ArrayList<VCard>();
		cardMove = false;
		right = false;
		left = 0;
		seeSort = false;
		this.removeView(sortHandBtn);
		if(leftArrow.isVisible()){
			this.removeView(leftArrow);
			leftArrow.flipVisibility();
		}
		if(rightArrow.isVisible()){
			this.removeView(rightArrow);
			rightArrow.flipVisibility();
		}
		this.removeView(sortHandBtn);
		return temp;
	}

	/**
	 * Value of all the cards in the player's hand
	 * @return the total value
	 */
	public int handValue() {
		int total = 0;
		for(VCard card: cards){
			total += card.getCard().handValue();
		}
		return total;
	}

}
