package com.philpicinic.kalukirummy.hand;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Suit;
import com.philpicinic.kalukirummy.card.VCard;

public class HandView extends ViewGroup {

	private ArrayList<VCard> cards;

	public HandView(Context context) {
		super(context);

		cards = new ArrayList<VCard>();
		// Creates 13 cards in the players hand
		cards = new ArrayList<VCard>();
		VCard vCard = new VCard(context, 0, Suit.DIAMONDS, 1);
		this.addView(vCard);
		cards.add(vCard);
		vCard = new VCard(context, 1, Suit.CLUBS, 1);
		this.addView(vCard);
		cards.add(vCard);

		for (int i = 0; i < 11; i++) {
			vCard = new VCard(context, (i + 2), Suit.SPADES, (i + 2));
			this.addView(vCard);

			cards.add(vCard);
		}
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		for (int i = 0; i < this.getChildCount(); i++) {
			this.getChildAt(i).layout(arg1, arg2, arg3, arg4);
		}

	}

	public boolean isClicked(MotionEvent event) {
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			for (VCard card : cards) {
				if(card.detectCollision(event)){
					this.bringChildToFront(card);
					return true;
				}
			}
		}
		return false;
	}

}
