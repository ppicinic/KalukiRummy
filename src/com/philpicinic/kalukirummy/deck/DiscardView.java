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
	// private Bitmap discardCard;
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

	public DiscardView(Context context) {
		super(context);
		this.context = context;

		pile = new DiscardPile();
		// x =

	}

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
		// TODO Auto-generated method stub
		this.l = l;
		this.w = t;
		this.ol = r;
		this.ow = b;
	}

	public ArrayList<Card> endGame() {
		this.removeView(this.card);
		return pile.endGame();
	}
}
