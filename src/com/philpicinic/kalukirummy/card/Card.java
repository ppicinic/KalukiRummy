package com.philpicinic.kalukirummy.card;

/**
 * 
 * @author Phil Picinic
 * 
 * Card class is the model for a Card.
 * This class is responsible for all card info and values.
 */
public class Card {

	// rank is an int value of the card. 1 is used for Joker 14 for Ace
	// Number cards use the same value for rank, 11 for Jack, 12 for Queen and
	// 13 for King
	// A red Joker is a faux Diamonds and a black Joker is a faux Clubs
	private int rank;
	
	// Suit uses a enum suit
	private Suit suit;

	// Rank and Suit for a Played Joker
	private int jRank;
	private Suit jSuit;

	/**
	 * Constructor to create the card
	 * @param rank rank of the card
	 * @param suit suit of the card
	 */
	public Card(int rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * gets the rank
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * gets the rank of a played Joker
	 * @return the rank
	 */
	public int getJRank() {
		return jRank;
	}

	/**
	 * gets the suit of the card
	 * @return the rank
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * gets the suit of a player Joker
	 * @return the suit
	 */
	public Suit getJSuit() {
		return jSuit;
	}

	/**
	 * Sets the rank for a joker being played
	 * @param jRank the rank the joker takes
	 */
	public void setJRank(int jRank) {
		if (isJoker()) {
			this.jRank = jRank;
		}
		// TODO throw exception if not a Joker?
	}

	/**
	 * Sets the suit for a joker being played
	 * @param suit the suit the Joker takes
	 */
	public void setJSuit(Suit suit){
		if(isJoker()){
			this.jSuit = suit;
		}
	}
	/**
	 * Tells if a card is an ace
	 * @return true if ace, otherwise false
	 */
	public boolean isAce() {
		if (rank == 14) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tells if a card is a face card
	 * @return true if a face card, otherwise false
	 */
	public boolean isFace() {
		if (rank == 11 || rank == 12 || rank == 13) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tells if a card is a Joker
	 * @return true if a joker, otherwise false
	 */
	public boolean isJoker() {
		if (rank == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the value of the card if it in a player's hand
	 * @return the value of the card if held in a player's hand
	 */
	public int handValue() {
		if (isJoker()) {
			return 25;
		}

		if (isFace()) {
			return 10;
		}
		if (isAce()) {
			return 11;
		}

		return rank;
	}

	/**
	 * Returns the value of the card if it is in a meld
	 * @return the value of the card played in a meld
	 */
	public int meldValue() {
		if (isJoker()) {
			return jRank;
		}

		if (isFace()) {
			return 10;
		}
		if (isAce()) {
			// TODO consider Ace acts as a 1? implement aRank?
			return 11;
		}
		return rank;
	}

	/**
	 * Tells the suit of the card if played in a meld
	 * @return the suit of the card
	 */
	public Suit meldSuit() {
		if (isJoker()) {
			return jSuit;
		}
		return suit;
	}
}
