package com.philpicinic.kalukirummy.deck;

import java.util.Collections;
import java.util.LinkedList;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.Suit;

public class Deck {

	private LinkedList<Card> deck;

	public Deck() {
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
		return deck.remove();
	}
}
