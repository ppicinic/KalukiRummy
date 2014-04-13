package com.philpicinic.kalukirummy.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.Suit;

/**
 * 
 * @author Phil Picinic
 * Deck class holds all the cards for the player to play from
 */
public class Deck {

	private LinkedList<Card> deck;
	private DiscardView pile;

	/**
	 * Constructor creates all the cards
	 * This creates a double deck each containing 2 jokers
	 * @param discard the discard pile if the deck runs out of cards it can grab them
	 */
	public Deck(DiscardView discard) {
		this.pile = discard;
		deck = new LinkedList<Card>();
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 2; y <= 14; y++) {
					Suit suit = Suit.DIAMONDS;
					switch (x) {
					case 0:
						suit = Suit.DIAMONDS;
						break;
					case 1:
						suit = Suit.CLUBS;
						break;
					case 2:
						suit = Suit.HEARTS;
						break;
					case 3:
						suit = Suit.SPADES;
						break;
					default:
						break;
					}
					deck.add(new Card(y, suit));
				}
			}
			deck.add(new Card(1, Suit.DIAMONDS));
			deck.add(new Card(1, Suit.CLUBS));
		}
		Collections.shuffle(deck);
	}

	/**
	 * Checks if the deck is empty
	 * @return true if it is empty, otherwise false
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}

	/**
	 * Deals a card
	 * Will grab from the discard pile if it runs out of cards
	 * @return the card to be dealt
	 */
	public Card deal() {
		if(deck.isEmpty()){
			ArrayList<Card> temp = pile.returnAllButTop();
			for(Card card: temp){
				deck.add(card);
			}
			Collections.shuffle(deck);
		}
		return deck.remove();
	}

	/**
	 * Returns a card to top of the deck
	 * @param card the card being returned
	 */
	public void returnToTop(Card card) {
		deck.add(0, card);		
	}

	/**
	 * Returns a list of cards to the deck
	 * @param temp all the cards being returned
	 */
	public void returnCards(ArrayList<Card> temp) {
		for(Card card: temp){
			deck.add(0, card);
			if(card.isJoker()){
				card.unSetJoker();
			}
		}
	}

	/**
	 * Shuffles the deck
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
}
