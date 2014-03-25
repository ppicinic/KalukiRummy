package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlaceViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceArea meldPlaceArea;
	private ArrayList<VCard> cards;

	public MeldPlaceViewGroup(Context context) {
		super(context);
		this.context = context;

		meldPlaceArea = new MeldPlaceArea(this.context);

		cards = new ArrayList<VCard>();
	}

	public void initiateMovingCard() {
		if (cards.size() == 0) {
			this.addView(meldPlaceArea);
		}
	}

	public void deinitiateMovingCard() {
		this.removeView(meldPlaceArea);
	}

	public boolean checkCollisionByCard(VCard card) {
		System.out.println("yo3");
		if (cards.size() >= 5) {
			return false;
		}
		System.out.println("yo4");
		return meldPlaceArea.checkCollisionByCard(card);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		meldPlaceArea.layout(arg1, arg2, arg3, arg4);
	}

	public void placeCard(VCard movingCard) {
		cards.add(movingCard);
		this.addView(movingCard);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setMeldPlacePos(i);
		}
	}

	public boolean playingCards() {
		// TODO Auto-generated method stub
		return cards.size() > 0;
	}
}
