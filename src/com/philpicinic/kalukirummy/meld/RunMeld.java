package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

public class RunMeld extends AbstractMeld{
	
	public RunMeld(ArrayList<VCard> cards){
		this.cards = cards;
		undoable = true;
		calcSize();
		jokerSpot = -1;
	}

	@Override
	public boolean canAttach(VCard vCard) {
		Card card = vCard.getCard();
		Card firstCard = cards.get(0).getCard();
		if(firstCard.getMeldSuit() == card.getMeldSuit()){
			if((firstCard.getMeldRank() - 1) == card.getMeldRank()){
				return true;
			}
			firstCard = cards.get(cards.size() - 1).getCard();
			if((firstCard.getMeldRank() + 1) == card.getMeldRank()){
				return true;
			}
		}
		return false;
	}
}
