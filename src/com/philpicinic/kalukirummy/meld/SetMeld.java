package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public class SetMeld extends AbstractMeld{

	public SetMeld(ArrayList<VCard> cards){
		this.cards = cards;
		undoable = true;
		calcSize();
	}
}
