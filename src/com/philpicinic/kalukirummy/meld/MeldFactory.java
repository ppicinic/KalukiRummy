package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.Collections;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;


public class MeldFactory {

	public static boolean validate(ArrayList<VCard> cards){
		// check size is between 3 and 5
		if(cards.size() < 3 || cards.size() > 5){
			return false;
		}
		Collections.sort(cards);
		Card firstCard = cards.get(0).getCard();
		Card secondCard = cards.get(1).getCard();
		
		if( firstCard.getMeldRank() == secondCard.getMeldRank() ){
			// validate set meld
			if(cards.size() > 4){
				return false;
			}
			for(int i = 1; i < cards.size(); i++ ){
				secondCard = cards.get(i).getCard();
				if(firstCard.getMeldSuit().ordinal() == secondCard.getMeldSuit().ordinal()){
					return false;
				}
				if(!(firstCard.getMeldRank() == secondCard.getMeldRank())){
					return false;
				}
				firstCard = secondCard;
			}
			return true;
		}else{
			// validate run meld
			for(int i = 1; i < cards.size(); i++){
				secondCard = cards.get(i).getCard();
				if(!(firstCard.getMeldSuit().ordinal() == secondCard.getMeldSuit().ordinal())){
					return false;
				}
				if(!(firstCard.getMeldRank() + 1 == secondCard.getMeldRank())){
					return false;
				}
				firstCard = secondCard;
			}
			return true;
		}
	}
	
	public static Meld buildMeld(ArrayList<VCard> cards){
		if(validate(cards)){
			Card firstCard = cards.get(0).getCard();
			Card secondCard = cards.get(1).getCard();
			if(firstCard.getMeldRank() == secondCard.getMeldRank()){
				return new SetMeld(cards);
			}else{
				return new RunMeld(cards);
			}
		}
		return null;
	}
}