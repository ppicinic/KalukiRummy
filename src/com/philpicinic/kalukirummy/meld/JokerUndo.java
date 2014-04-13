package com.philpicinic.kalukirummy.meld;

import com.philpicinic.kalukirummy.card.VCard;

/**
 * 
 * @author Phil Picinic
 * Handles undoing joker replacements
 */
public class JokerUndo {
	
	private VCard jokerCard;
	private VCard replaceCard;
	private Meld meld;
	private boolean playerSide;
	
	/**
	 * Constructor
	 * @param card the replace card
	 */
	public JokerUndo(VCard card){
		this.replaceCard = card;
	}
	
	/**
	 * Set the joker card
	 * @param card the joker card
	 */
	public void setJokerCard(VCard card){
		this.jokerCard = card;
	}
	
	/**
	 * Set the replace card
	 * @param card the replace card
	 */
	public void setReplaceCard(VCard card){
		this.replaceCard = card;
	}
	
	/**
	 * Set the meld it's from
	 * @param meld the meld
	 */
	public void setMeld(Meld meld){
		this.meld = meld;
	}
	
	/**
	 * Set whether the replacement is on the player or bot's side
	 * @param playerSide the player or bot's side
	 */
	public void setPlayerSide(boolean playerSide){
		this.playerSide = playerSide;
	}
	
	/**
	 * Get the joker card
	 * @return the joker card
	 */
	public VCard getJokerCard(){
		return jokerCard;
	}
	
	/**
	 * Get the Replace card
	 * @return the replace card
	 */
	public VCard getReplaceCard(){
		return replaceCard;
	}
	
	/**
	 * Get the meld it's from
	 * @return the meld
	 */
	public Meld getMeld(){
		return meld;
	}

	/**
	 * Check whether it is on the player side or not
	 * @return true if the player's side, false if the bot side
	 */
	public boolean isPlayerSide() {
		return playerSide;
	}
}
