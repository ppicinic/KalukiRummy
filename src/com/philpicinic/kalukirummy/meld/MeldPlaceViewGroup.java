package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlaceViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceArea meldPlaceArea;
	private MeldPlayButton meldPlayButton;
	private ArrayList<VCard> cards;
	private int removed;

	public MeldPlaceViewGroup(Context context) {
		super(context);
		this.context = context;

		meldPlaceArea = new MeldPlaceArea(this.context);
		meldPlayButton = new MeldPlayButton(this.context);

		cards = new ArrayList<VCard>();

		removed = -1;
	}

	public ArrayList<VCard> getCards(){
		return cards;
	}
	
	public void removeAllCards(){
		for(VCard card : cards){
			this.removeView(card);
		}
		cards = new ArrayList<VCard>();
		this.removeView(meldPlaceArea);
		this.removeView(meldPlayButton);
		removed = -1;
	}
	
	public void initiateMovingCard() {
		if (cards.size() == 0) {
			this.addView(meldPlaceArea);
			//this.addView(meldPlayButton);
		}
	}

	public void deinitiateMovingCard() {
		this.removeView(meldPlaceArea);
		this.removeView(meldPlayButton);
	}

	public boolean checkCollisionByCard(VCard card) {
		if (cards.size() >= 5) {
			return false;
		}
		return meldPlaceArea.checkCollisionByCard(card);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		meldPlaceArea.layout(arg1, arg2, arg3, arg4);
		meldPlayButton.layout(arg1, arg2, arg3, arg4);
	}

	public void placeCard(VCard movingCard) {
		
		cards.add(movingCard);
		this.addView(movingCard);
		Collections.sort(cards);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setMeldPlacePos(i);
		}
		if(cards.size() == 3){
			this.addView(meldPlayButton);
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

	public boolean checkPlay(MotionEvent event){
		if(cards.size() >= 3){
			return meldPlayButton.checkCollision(event);
		}
		return false;
	}
	
	public VCard removeCard() {
		if (removed >= 0) {
			VCard temp = cards.get(removed);
			this.removeView(temp);
			cards.remove(removed);
			Collections.sort(cards);
			for (int j = 0; j < cards.size(); j++) {
				cards.get(j).setMeldPlacePos(j);
			}
			if(cards.size() < 3){
				this.removeView(meldPlayButton);
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
