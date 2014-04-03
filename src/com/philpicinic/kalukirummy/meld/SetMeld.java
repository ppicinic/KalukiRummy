package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

public class SetMeld extends AbstractMeld{

	public SetMeld(ArrayList<VCard> cards){
		this.cards = cards;
		undoable = true;
		calcSize();
	}

	@Override
	public boolean canAttach(VCard vCard) {
		Card card = vCard.getCard();
		Card temp = cards.get(0).getCard();
		if(cards.size() < 4 && temp.getMeldRank() == card.getMeldRank()){
			for(VCard temp2 : cards){
				temp = temp2.getCard();
				if(temp.getMeldSuit().ordinal() == card.getMeldSuit().ordinal()){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
}
