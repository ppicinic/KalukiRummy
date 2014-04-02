package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public class RunMeld extends AbstractMeld{
	
	public RunMeld(ArrayList<VCard> cards){
		this.cards = cards;
		undoable = true;
		calcSize();
	}
}
