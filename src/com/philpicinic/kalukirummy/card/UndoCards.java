package com.philpicinic.kalukirummy.card;

import java.util.ArrayList;

public class UndoCards {

	private ArrayList<VCard> playedCards;
	private Card drawCard;
	private boolean deckCard;
	//
	
	public UndoCards(){
		playedCards = new ArrayList<VCard>();
		deckCard = true;
	}
	
	public ArrayList<VCard> getCards(){
		return playedCards;
	}
	
	public Card getDrawCard(){
		return drawCard;
	}
	
	public boolean isFromDeck(){
		return deckCard;
	}
	
	public void addDrawCard(boolean deck, Card card){
		deckCard = deck;
		drawCard = card;
	}
	
	public void addMeldCards(ArrayList<VCard> cards){
		for(VCard card : cards){
			playedCards.add(card);
		}
	}
	
	public void addAttachedCards(VCard card){
		playedCards.add(card);
	}
	
	public void reset(){
		playedCards = new ArrayList<VCard>();
		deckCard = false;
		drawCard = null;
	}
}
