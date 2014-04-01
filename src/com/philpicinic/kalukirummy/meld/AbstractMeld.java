package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public abstract class AbstractMeld implements Meld {
	
	protected ArrayList<VCard> cards;
	
	public int value(){
		int total = 0;
		for(VCard card : cards){
			total += card.getCard().meldValue();
		}
		return total;
	}
	
	public ArrayList<VCard> getCards(){
		return cards;
	}
}
