package com.philpicinic.kalukirummy.deck;

import java.util.Stack;

import com.philpicinic.kalukirummy.card.Card;

public class DiscardPile {
	
	private Stack<Card> discard;
	
	public DiscardPile(){
		discard = new Stack<Card>();
	}
	
	public Card peek(){
		return discard.peek();
	}
	
	public Card pop(){
		return discard.pop();
	}
	
	public void push(Card card){
		discard.push(card);
	}

	public boolean isEmpty() {
		return discard.isEmpty();
	}
	
}
