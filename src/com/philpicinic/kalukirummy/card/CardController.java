package com.philpicinic.kalukirummy.card;

import android.content.Context;

public class CardController {

	private Card card;
	private VCard view;
	
	public CardController(Card card){
		this.card = card;
	}
	
	public CardController(int rank, Suit suit){
		this.card = new Card(rank, suit);
	}
	
	public VCard createView(Context context, int pos){
		//this.view = new VCard(context, pos, card.getSuit(), card.getRank());
		return this.view;
		
	}
	
	public Card getCard(){
		return this.card;
	}
	
	public VCard getView(){
		return this.view;
	}
}
