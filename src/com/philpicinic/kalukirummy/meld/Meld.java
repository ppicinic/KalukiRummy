package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public interface Meld {

	public int value();
	public ArrayList<VCard> getCards();
	public int size();
	public void setUndoable(boolean undoable);
	public boolean getUndoable();
	public boolean canAttach(VCard card);
	public void attach(VCard card);
	public VCard removeAttached(VCard card);
	public boolean canReplaceJoker(VCard card);
	public VCard replaceJoker(VCard card);
	public void removeReplaceCard(VCard replaceCard);
	
}
