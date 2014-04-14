package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

/**
 * 
 * @author Phil Picinic
 * This is the ViewGroup for handling all placement of cards the player
 * is actively playing
 */
public class MeldPlaceViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceArea meldPlaceArea;
	private MeldPlayButton meldPlayButton;
	private UndoButton undoButton;
	private ArrayList<VCard> cards;
	private int removed;

	/**
	 * Constructor
	 * @param context the context of the activity
	 */
	public MeldPlaceViewGroup(Context context) {
		super(context);
		this.context = context;

		meldPlaceArea = new MeldPlaceArea(this.context);
		meldPlayButton = new MeldPlayButton(this.context);
		undoButton = new UndoButton(this.context);
//		this.addView(undoButton);
		
		cards = new ArrayList<VCard>();

		removed = -1;
	}

	/**
	 * Gets all the cards in the meld place area
	 * @return the cards
	 */
	public ArrayList<VCard> getCards(){
		return cards;
	}
	
	/**
	 * Clears everything at endGame
	 */
	public void endGame(){
		this.removeView(undoButton);
	}
	
	/**
	 * Remove all the cards in the meld place area
	 */
	public void removeAllCards(){
		for(VCard card : cards){
			this.removeView(card);
		}
		cards = new ArrayList<VCard>();
		this.removeView(meldPlaceArea);
		this.removeView(meldPlayButton);
		this.addView(undoButton);
		removed = -1;
	}
	
	/**
	 * adds the meld place area and removes the undo button from the viewgroup
	 */
	public void initiateMovingCard() {
		if (cards.size() == 0) {
			this.addView(meldPlaceArea);
			this.removeView(undoButton);
		}
	}
	
	/**
	 * adds the undo button back into view
	 */
	public void initiateHand(){
		this.addView(undoButton);
	}

	/**
	 * removes the meld place area and meld play button and adds the undo button
	 * back into the viewgroup
	 */
	public void deinitiateMovingCard() {
		this.removeView(meldPlaceArea);
		this.removeView(meldPlayButton);
		this.addView(undoButton);
	}

	/**
	 * Checks if a card collides and fits in hte meld place area
	 * @param card the card to compare to
	 * @return true if collides, otherwise false
	 */
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
		undoButton.layout(arg1, arg2, arg3, arg4);
	}

	/**
	 * Places a card in the meld place area
	 * @param movingCard the card to place
	 */
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
	
	/**
	 * Tells whether the player is playing cards
	 * @return true if playing cards, false otherwise
	 */
	public boolean playingCards() {

		return cards.size() > 0;
	}

	/**
	 * Check if the player touches any cards in the meld place area
	 * @param event the player's input
	 * @return true if any cards are pressed, otherwise false
	 */
	public boolean checkPlayCollisions(MotionEvent event) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).detectCollision(event)) {
				removed = i;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks a player presses play
	 * @param event the player's input
	 * @return true if pressed, otherwise false
	 */
	public boolean checkPlay(MotionEvent event){
		if(cards.size() >= 3){
			return meldPlayButton.checkCollision(event);
		}
		return false;
	}
	
	/**
	 * Removes the card pressed
	 * @return the card removed
	 */
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
				this.addView(undoButton);
			}
			removed = -1;
			return temp;
		}
		return null;
	}

	/**
	 * Checks if the undo button is pressed
	 * @param event the player's input
	 * @return true if pressed, otherwise false
	 */
	public boolean checkUndo(MotionEvent event) {
		if(cards.size() == 0){
			return undoButton.checkCollision(event);
		}
		return false;
	}

	/**
	 * Sort the cards in place area
	 */
	public void sortPlayingCards() {
		Collections.sort(this.cards);
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setMeldPlacePos(i);
		}
	}
}
