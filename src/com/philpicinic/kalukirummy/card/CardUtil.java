package com.philpicinic.kalukirummy.card;

public class CardUtil {

	public static int SuitValue(Suit suit) {
		
		switch (suit) {
		
		case DIAMONDS:
			return 1;
			
		case CLUBS:
			return 2;
			
		case HEARTS:
			return 3;
			
		case SPADES:
			return 4;
			
		default:
			break;
		}
		return 0;
	}
}
