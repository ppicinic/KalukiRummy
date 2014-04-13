package com.philpicinic.kalukirummy.deck;

import java.util.ArrayList;
import java.util.Stack;

import com.philpicinic.kalukirummy.card.Card;

/**
 * 
 * @author Phil Picinic
 * Model class of the discard pile.
 * Holds a stack of cards, We only need to get the top card and put a card on the top
 */
public class DiscardPile {
	
	private Stack<Card> discard;
	
	/**
	 * Constructor
	 */
	public DiscardPile(){
		discard = new Stack<Card>();
	}
	
	/**
	 * Look at the top card
	 * @return the card on top
	 */
	public Card peek(){
		return discard.peek();
	}
	
	/**
	 * Get and remove the top card
	 * @return the card on top
	 */
	public Card pop(){
		return discard.pop();
	}
	
	/**
	 * Add a card to the top
	 * @param card the card being added
	 */
	public void push(Card card){
		discard.push(card);
	}

	/**
	 * Tells whether the pile is empty or not
	 * @return true if empty, otherwise false
	 */
	public boolean isEmpty() {
		return discard.isEmpty();
	}

	/**
	 * Remove all the cards from the pile
	 * @return all the cards in the pile
	 */
	public ArrayList<Card> endGame() {
		ArrayList<Card> temp = new ArrayList<Card>();
		while(!discard.isEmpty()){
			temp.add(discard.pop());
		}
		return temp;
	}
	
}
