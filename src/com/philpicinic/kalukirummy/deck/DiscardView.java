package com.philpicinic.kalukirummy.deck;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

/**
 * 
 * @author Phil Picinic
 * 
 *         View Class of the Discard Pile Shows the top card of the discard Pile
 */
public class DiscardView extends ViewGroup {

	private Context context;
	private DiscardPile pile;
	@SuppressWarnings("unused")
	private int screenH;
	private int screenW;

	private int x;
	private int x2;
	private int y;
	private int y2;

	private int l;
	private int w;
	private int ol;
	private int ow;

	private VCard card;

	/**
	 * Constructor
	 * @param context the context of the activity
	 */
	public DiscardView(Context context) {
		super(context);
		this.context = context;

		pile = new DiscardPile();
	}

	/**
	 * Add a card to the top of the pile
	 * @param mCard the card
	 */
	public void toss(Card mCard) {
		pile.push(mCard);
		VCard temp = new VCard(this.context, 0, mCard);
		temp.layout(l, w, ol, ow);
		temp.setTossPos();
		if (card != null) {
			this.removeView(card);
		}
		card = temp;
		this.addView(card);

	}

	/**
	 * check if a player draws from the discard pile
	 * @param event the player's input
	 * @return true if the player touched the card, otherwise false
	 */
	public boolean checkDraw(MotionEvent event) {
		return card.detectCollision(event);
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		screenW = w;
		screenH = h;

		int scaleW = screenW / 7;
		int scaleH = (int) (scaleW * 1.4);
		x = (scaleW * 1) + ((scaleW / 6) * (2));
		y = (scaleH / 6);
		x2 = scaleW;
		y2 = scaleH;

	}

	/**
	 * Draws the top card
	 * @return the card on top
	 */
	public VCard drawFromPile() {
		pile.pop();
		VCard temp = card;
		this.removeView(card);
		if (!pile.isEmpty()) {
			card = new VCard(context, 0, pile.peek());
			card.layout(l, w, ol, ow);
			card.setTossPos();
			this.addView(card);
		} else {
			card = null;
		}
		return temp;
	}

	/**
	 * Add a card to the top of the pile
	 * @param mCard the card
	 */
	public void toss(VCard vCard) {
		pile.push(vCard.getCard());
		if (card != null) {
			this.removeView(card);
		}
		card = vCard;
		card.setTossPos();
		this.addView(card);
		card.layout(l, w, ol, ow);

	}

	/**
	 * Checks collision of the discard pile with a card
	 * @param arg0 the card being compared
	 * @return true if the card is in the discard, otherwise false
	 */
	public boolean checkCollision(VCard arg0) {
		if ((this.x >= arg0.getMyX() && this.x < (arg0.getMyX() + (x2 / 2)))
				|| (((this.x + x2) > (arg0.getMyX() + (x2 / 2))) && (this.x + x2) <= (arg0
						.getMyX() + x2))) {
			if ((this.y >= arg0.getMyY() && this.y < (arg0.getMyY() + (y2 / 2)))
					|| (((this.y + y2) > (arg0.getMyY() + (y2 / 2))) && (this.y + y2) <= (arg0
							.getMyY() + y2))) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		this.l = l;
		this.w = t;
		this.ol = r;
		this.ow = b;
	}

	/**
	 * End game method removes all cards from the pile
	 * @return all the cards in the pile
	 */
	public ArrayList<Card> endGame() {
		this.removeView(this.card);
		return pile.endGame();
	}

	/**
	 * Removes all cards from the pile but the top one
	 * @return all the cards but the top card
	 */
	public ArrayList<Card> returnAllButTop() {
		Card top = pile.pop();
		ArrayList<Card> result = new ArrayList<Card>();
		while(!pile.isEmpty()){
			result.add(pile.pop());
		}
		pile.push(top);
		return result;
	}
}
