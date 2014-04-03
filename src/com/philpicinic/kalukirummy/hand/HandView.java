package com.philpicinic.kalukirummy.hand;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

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

	// private boolean start;
	// private boolean started;
	// private LinkedList<VCard> deal;

	private int left;

	public HandView(Context context) {
		super(context);
		this.context = context;

		cards = new ArrayList<VCard>();
		// deal = new LinkedList<VCard>();

		// this.start = false;
		// this.started = false;
		//
		// cards = new ArrayList<VCard>();
		// // Creates 13 cards in the players hand
		// cards = new ArrayList<VCard>();
		// VCard vCard = new VCard(context, 0, Suit.DIAMONDS, 1);
		// this.addView(vCard);
		// cards.add(vCard);
		// vCard = new VCard(context, 1, Suit.CLUBS, 1);
		// this.addView(vCard);
		// cards.add(vCard);
		//
		// for (int i = 0; i < 11; i++) {
		// vCard = new VCard(context, (i + 2), Suit.SPADES, (i + 2));
		// this.addView(vCard);
		//
		// cards.add(vCard);
		// }

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

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// if(!deal.isEmpty()){
		// System.out.println("shit happening");
		// VCard temp = deal.remove();
		// this.addView(temp);
		// this.layout(l, w, ol, ow);
		// cards.add(0, temp);
		// for(int i = 0; i < cards.size(); i++){
		// cards.get(i).setHandPos(i);
		// }
		// }
	}

	public void deal(Card card) {
		// for(int i = 0; i < 13; i++){
		// Card card = deck.deal();
		// for(VCard temp : cards){
		// temp.setHandPos(temp.getHandPos() + 1);
		// }
		// System.out.println(card.getRank());
		// VCard temp = new VCard(this.context, 0, card);
		// cards.add(0, vCard);
		// deal.add(temp);
		// this.addView(vCard);
		// vCard.layout(l, w, ol, ow);

		// this.invalidate();
		// try{
		// Thread.sleep(1000);
		// }catch(InterruptedException ie){

		// }
		// SystemClock.sleep(1000);
		// }
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

	public void handCreated() {
		if (!seeSort) {
			this.addView(sortHandBtn);
			seeSort = true;
		}
	}

	public void handEnded() {
		if (seeSort) {
			// this.removeView(sortHandBtn);
			// seeSort = false;
		}
	}

	public void deal(VCard returnToHand) {
		// TODO Auto-generated method stub
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

	public VCard getMovingCard() {
		return movingCard;
	}

	public boolean onTouchEvent(MotionEvent event) {
		// System.out.println(event);
		int e = event.getAction();
		// System.out.println(e);
		if (e == MotionEvent.ACTION_DOWN) {
			// if(!started){
			// start = true;
			// return true;
			// }
			for (VCard card : cards) {
				if (card.detectCollision(event)) {
					this.bringChildToFront(card);
					movingCard = card;
					return true;
				}
			}
			if (rightArrow.isPressed(event)) {
				// System.out.println("happenny0");
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
		if (e == MotionEvent.ACTION_MOVE) {
			if (movingCard != null) {
				// movingCard.onTouchEvent(event);
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

						// this.inva
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
				// this.invalidate();
			}
			if (toBeSorted) {
				sortHand();
				toBeSorted = false;
				return true;
			}
		}
		this.invalidate();
		// System.out.println(cardMove);
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		// System.out.println("inter");
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
					// return true;
				}
			}
		}
		if (e == MotionEvent.ACTION_UP) {
			// System.out.println("up");
			if (movingCard != null) {
				// this.addView(sortHandBtn);
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

	public void removeMovingCard() {
		int tempPos = movingCard.getHandPos();
		tempPos += left;
		cards.remove(tempPos);

		if ((left + 6) > cards.size()) {
			left = cards.size() - 6;
			if(left == 0 && leftArrow.isVisible()){
				this.removeView(leftArrow);
				leftArrow.flipVisibility();
			}
		}
		if (cards.size() <= 6) {
			left = 0;
			if(leftArrow.isVisible()){
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

	public void sortHand() {
		Collections.sort(cards);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setHandPos(i - left);
		}
	}

	public void removeCard(Card card) {
		for (int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i).getCard();
			if (temp.getRank() == card.getRank()
					&& card.getSuit() == temp.getSuit()) {
				this.removeView(cards.get(i));
				cards.remove(i);
				System.out.println(left);
				if (left == cards.size() - 5) {
					left--;
					if(left == 0 && leftArrow.isVisible()){
						this.removeView(leftArrow);
						leftArrow.flipVisibility();
					}
				}
				if (cards.size() <= 6) {
					left = 0;
					if(leftArrow.isVisible()){
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

	
}
