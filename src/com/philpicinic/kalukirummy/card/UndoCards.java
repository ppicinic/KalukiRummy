package com.philpicinic.kalukirummy.card;

import java.util.ArrayList;

/**
 * 
 * @author Phil Picinic
 * Holds references to played cards that can be undone to
 * allow implementation of the undo button for the player
 */
public class UndoCards {

	private ArrayList<VCard> playedCards;
	private Card drawCard;
	private boolean deckCard;
	
	/**
	 * Constructor
	 */
	public UndoCards(){
		playedCards = new ArrayList<VCard>();
		deckCard = true;
	}
	
	/**
	 * Gets the played cards
	 * @return all the played card
	 */
	public ArrayList<VCard> getCards(){
		return playedCards;
	}
	
	/**
	 * Gets the card the player drew
	 * @return the card
	 */
	public Card getDrawCard(){
		return drawCard;
	}
	
	/**
	 * Tells whether the player drew from the discard or the deck
	 * @return true if the player drew from the deck, false if the player drew from the discard pile
	 */
	public boolean isFromDeck(){
		return deckCard;
	}
	
	/**
	 * Adds the draw card
	 * @param deck if the player drew from the deck or discard pile
	 * @param card the card the player drew
	 */
	public void addDrawCard(boolean deck, Card card){
		deckCard = deck;
		drawCard = card;
	}
	
	/**
	 * Adds played card
	 * @param cards the cards
	 */
	public void addMeldCards(ArrayList<VCard> cards){
		for(VCard card : cards){
			playedCards.add(card);
		}
	}
	
	/**
	 * Adds cards the player attaches
	 * @param card the card the player attached
	 */
	public void addAttachedCards(VCard card){
		playedCards.add(card);
	}
	
	/**
	 * Resets this class when the turn ends
	 */
	public void reset(){
		playedCards = new ArrayList<VCard>();
		deckCard = false;
		drawCard = null;
	}
}
