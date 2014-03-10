package com.philpicinic.kalukirummy.deck;

public class Card {

	// rank is an int value of the card. 1 is used for Joker 14 for Ace
	// Number cards use the same value for rank, 11 for Jack, 12 for Queen and
	// 13 for King
	private int rank;
	// Suit uses a enum suit
	private Suit suit;
	
	private int jRank;

	public Card(int rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public int getRank() {
		return rank;
	}
	
	public int getJRank(){
		return jRank;
	}
	
	public Suit getSuit(){
		return suit;
	}
	
	public void setJRank(int jRank){
		if(rank == 1){
			this.jRank = jRank;
		}
		// TODO throw exception if not a Joker?
	}
	
	public boolean isAce(){
		if(rank == 14){
			return true;
		} else {
			return false;
		}
	}
	public boolean isFace(){
		if(rank == 11 || rank == 12 || rank == 13){
			return true;
		} else{
			return false;
		}
	}

	public boolean isJoker() {
		if (rank == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public int handValue(){
		if(rank == 1){
			return 25;
		}
		
		if(rank == 11 || rank == 12 || rank == 13){
			return 10;
		}
		if(rank == 14){
			return 11;
		}
		
		return rank;
	}
	
	public int meldValue(){
		if(rank == 1){
			return jRank;
		}
		
		if(rank == 11 || rank == 12 || rank == 13){
			return 10;
		}
		if(rank == 14){
			// TODO consider Ace acts as a 1? implement aRank?
			return 11;
		}
		return rank;
	}
}
