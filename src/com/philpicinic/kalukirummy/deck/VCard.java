package com.philpicinic.kalukirummy.deck;

public class VCard {
	
	private Card card;
	private String cardName;
	
	
	public VCard(Card card){
		this.card = card;
		cardName = "card";
		cardName += (card.getSuit().ordinal() + 1);
		cardName += card.getRank();
		
	}
	
	public Card getCard(){
		return card;
	}
	
	public String getCardName(){
		return cardName;
	}
}
