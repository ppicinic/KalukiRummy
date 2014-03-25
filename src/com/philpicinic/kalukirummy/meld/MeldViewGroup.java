package com.philpicinic.kalukirummy.meld;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldViewGroup extends ViewGroup {
	
	private Context context;
	private MeldPlaceViewGroup meldPlaceViewGroup;
	
	public MeldViewGroup(Context context) {
		super(context);
		
		this.context = context;
		
		meldPlaceViewGroup = new MeldPlaceViewGroup(this.context);
		this.addView(meldPlaceViewGroup);
		// TODO Auto-generated constructor stub
	}

	public void initiateMovingCard(){
		 meldPlaceViewGroup.initiateMovingCard();
	}
	
	public void deinitiateMovingCard(){
		meldPlaceViewGroup.deinitiateMovingCard();
	}
	
	public boolean playingCards(){
		return meldPlaceViewGroup.playingCards();
	}
	public boolean checkCollisionByCard(VCard card){
		System.out.println("yo2");
		return meldPlaceViewGroup.checkCollisionByCard(card);
	}
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		meldPlaceViewGroup.layout(arg1, arg2, arg3, arg4);
	}

	public void placeCard(VCard movingCard) {
		meldPlaceViewGroup.placeCard(movingCard);
		
	}

	public boolean checkPlayCollisions(MotionEvent event) {
		// TODO Auto-generated method stub
		return meldPlaceViewGroup.checkPlayCollisions(event);
	}
	
	public VCard removeCardFromPlay(){
		return meldPlaceViewGroup.removeCard();
	}
	
}
