package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public abstract class AbstractMeld implements Meld {
	
	protected boolean undoable;
	protected ArrayList<VCard> cards;
	protected int cardsize;
	
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
	
	protected void calcSize(){
		cardsize = cards.size() + 5;
	}
	
	public int size(){
		return cardsize;
	}
	
	public void setUndoable(boolean undoable){
		this.undoable = undoable;
	}
	
	public boolean getUndoable(){
		return undoable;
	}
	
	public void endTurn(){
		
	}
}
