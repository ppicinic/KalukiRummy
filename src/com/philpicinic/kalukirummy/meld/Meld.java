package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public interface Meld {

	/**
	 * The value of all the card
	 * @return the value
	 */
	public int value();
	/**
	 * Gets all the cards
	 * @return all the cards
	 */
	public ArrayList<VCard> getCards();
	/**
	 * Gets the size of the meld (card-count-wise)
	 * @return the number of cards
	 */
	public int size();
	/**
	 * Sets the meld as undoable 
	 * @param undoable
	 */
	public void setUndoable(boolean undoable);
	/**
	 * Tells if the meld is undoable
	 * @return true if undoable, otherwise false
	 */
	public boolean getUndoable();
	/**
	 * Tells whether a card can be attached to the meld
	 * @param card the card to be attached
	 * @return true if it can be attached, otherwise false
	 */
	public boolean canAttach(VCard card);
	/**
	 * Attaches a card to the meld
	 * @param card the card to attach
	 */
	public void attach(VCard card);
	/**
	 * Removes an attached card
	 * @param card the card to remove
	 * TODO Serialize
	 * @return the card removed
	 */
	public VCard removeAttached(VCard card);
	/**
	 * Tells whether the card can replace a joker in the meld
	 * @param card the card to replace
	 * @return true if the card can replace, otherwise false
	 */
	public boolean canReplaceJoker(VCard card);
	/**
	 * Replace the joker
	 * @param card the card replacing the joker
	 * @return the joker that was replaced
	 */
	public VCard replaceJoker(VCard card);
	/**
	 * Undo a joker replacement
	 * @param replaceCard the card that was replaced or the joker?
	 * TODO this could be broken based on parameters
	 */
	public void removeReplaceCard(VCard replaceCard);
	
}
