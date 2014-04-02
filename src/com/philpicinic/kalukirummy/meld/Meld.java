package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import com.philpicinic.kalukirummy.card.VCard;

public interface Meld {

	public int value();
	public ArrayList<VCard> getCards();
	public int size();
	public void setUndoable(boolean undoable);
	public boolean getUndoable();
//	public boolean canAttach();
//	public void attach();
//	public boolean canReplaceJoker();
//	public VCard replaceJoker();
	
}
