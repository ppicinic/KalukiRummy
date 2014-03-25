package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlaceViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceArea meldPlaceArea;
	private ArrayList<VCard> cards;
	private int removed;

	public MeldPlaceViewGroup(Context context) {
		super(context);
		this.context = context;

		meldPlaceArea = new MeldPlaceArea(this.context);

		cards = new ArrayList<VCard>();

		removed = -1;
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

	public boolean checkPlayCollisions(MotionEvent event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).detectCollision(event)) {
				removed = i;
				return true;
			}
		}
		return false;
	}

	public VCard removeCard() {
		if (removed >= 0) {
			VCard temp = cards.get(removed);
			this.removeView(temp);
			cards.remove(removed);
			for (int j = 0; j < cards.size(); j++) {
				cards.get(j).setMeldPlacePos(j);
			}
			if(cards.size() <= 0){
				this.removeView(meldPlaceArea);
			}
			removed = -1;
			return temp;
		}
		return null;
	}
}
