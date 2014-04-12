package com.philpicinic.kalukirummy.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.Suit;

public class Deck {

	private LinkedList<Card> deck;
	private DiscardView pile;

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

	public boolean isEmpty() {
		return deck.isEmpty();
	}

	public Card deal() {
		System.out.println("deck size: " + deck.size());
		if(deck.isEmpty()){
			ArrayList<Card> temp = pile.returnAllButTop();
			for(Card card: temp){
				deck.add(card);
			}
			Collections.shuffle(deck);
		}
		return deck.remove();
	}

	public void returnToTop(Card card) {
		deck.add(0, card);		
	}

	public void returnCards(ArrayList<Card> temp) {
		for(Card card: temp){
			deck.add(0, card);
			if(card.isJoker()){
				card.unSetJoker();
			}
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}
}
