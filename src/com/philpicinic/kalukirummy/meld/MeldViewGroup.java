package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldViewGroup extends ViewGroup {
	
	private Context context;
	private MeldPlaceViewGroup meldPlaceViewGroup;
	private MeldPlayerViewGroup meldPlayerViewGroup;
	
	public MeldViewGroup(Context context) {
		super(context);
		
		this.context = context;
		
		meldPlaceViewGroup = new MeldPlaceViewGroup(this.context);
		this.addView(meldPlaceViewGroup);
		meldPlayerViewGroup = new MeldPlayerViewGroup(this.context);
		this.addView(meldPlayerViewGroup);
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
		meldPlayerViewGroup.layout(arg1, arg2, arg3, arg4);
	}
	
	public void addMeld(Meld meld){
		meldPlayerViewGroup.addMeld(meld);
	}

	public void placeCard(VCard movingCard) {
		meldPlaceViewGroup.placeCard(movingCard);
		
	}

	public boolean checkPlayCollisions(MotionEvent event) {
		// TODO Auto-generated method stub
		return meldPlaceViewGroup.checkPlayCollisions(event);
	}
	
	public ArrayList<VCard> getPlayingCards(){
		return meldPlaceViewGroup.getCards();
	}
	
	public void removeAllPlayingCards(){
		meldPlaceViewGroup.removeAllCards();
	}
	
	public VCard removeCardFromPlay(){
		return meldPlaceViewGroup.removeCard();
	}
	
	public boolean checkPlayMeld(MotionEvent event){
		return meldPlaceViewGroup.checkPlay(event);
	}
	
	public boolean playerCanToss(){
		return meldPlayerViewGroup.playerCanToss();
	}
	public int playerMeldValue(){
		return meldPlayerViewGroup.meldValue();
	}

	public void initiateHand() {
		meldPlaceViewGroup.initiateHand();
	}
	
	public boolean checkUndo(MotionEvent event){
		return meldPlaceViewGroup.checkUndo(event);
	}

	public void undoCards() {
		meldPlayerViewGroup.undoCards();
	}

	public void endTurn() {
		meldPlayerViewGroup.endTurn();
	}
}
