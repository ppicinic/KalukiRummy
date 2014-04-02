package com.philpicinic.kalukirummy.card;

import java.util.ArrayList;

public class UndoCards {

	private ArrayList<VCard> playedCards;
	private VCard drawCard;
	private boolean deckCard;
	
	public UndoCards(){
		playedCards = new ArrayList<VCard>();
		deckCard = true;
	}
	
	public ArrayList<VCard> getCards(){
		return playedCards;
	}
	
	public VCard getDrawCard(){
		return drawCard;
	}
	
	public void addDrawCard(boolean deck, VCard card){
		deckCard = deck;
		drawCard = card;
	}
	
	public void addMeldCards(ArrayList<VCard> cards){
		for(VCard card : cards){
			playedCards.add(card);
		}
	}
	
	public void reset(){
		playedCards = new ArrayList<VCard>();
		deckCard = false;
		drawCard = null;
	}
}
