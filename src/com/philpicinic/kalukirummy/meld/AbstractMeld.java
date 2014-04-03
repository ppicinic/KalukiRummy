package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.Collections;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

public abstract class AbstractMeld implements Meld {

	protected boolean undoable;
	protected ArrayList<VCard> cards;
	protected int cardsize;

	public int value() {
		int total = 0;
		for (VCard card : cards) {
			total += card.getCard().meldValue();
		}
		return total;
	}

	public ArrayList<VCard> getCards() {
		return cards;
	}

	protected void calcSize() {
		cardsize = cards.size() + 5;
	}

	public int size() {
		return cardsize;
	}

	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}

	public boolean getUndoable() {
		return undoable;
	}

	public void endTurn() {

	}

	public void attach(VCard card) {
		if (canAttach(card)) {
			cards.add(card);
			Collections.sort(cards);
		}
	}

	public VCard removeAttached(VCard vCard) {
		Card card = vCard.getCard();
		for (int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i).getCard();
			if (card.isJoker()) {
				if (temp.isJoker()) {
					if (card.getMeldRank() == temp.getMeldRank()) {
						if (card.getMeldSuit() == card.getMeldSuit()) {
							VCard result = cards.remove(i);
							Collections.sort(cards);
							return result;
						}
					}
				}
			} else {
				if (!temp.isJoker()) {
					if (card.getMeldRank() == temp.getMeldRank()) {
						if (card.getMeldSuit().ordinal() == temp.getMeldSuit()
								.ordinal()) {
							VCard result = cards.remove(i);
							Collections.sort(cards);
							return result;
						}
					}
				}
			}
		}
		return null;
	}
}
