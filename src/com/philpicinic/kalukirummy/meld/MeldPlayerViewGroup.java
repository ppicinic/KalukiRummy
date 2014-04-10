package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlayerViewGroup extends ViewGroup {

	protected ArrayList<Meld> melds;
	protected ArrayList<Meld> undoableMelds;
	protected Meld attachMeld;
	protected int attachSpot;
	protected ArrayList<VCard> attachCards;
	protected HashMap<VCard, ArrayList<Integer>> attachSpots;
	protected boolean playerSide;

	private int l;
	private int t;
	private int r;
	private int b;

	public MeldPlayerViewGroup(Context context) {
		super(context);
		playerSide = true;
		melds = new ArrayList<Meld>();
		undoableMelds = new ArrayList<Meld>();
		attachSpot = -1;
		attachCards = new ArrayList<VCard>();
		attachSpots = new HashMap<VCard, ArrayList<Integer>>();
	}

	public void addMeld(Meld meld) {
		melds.add(meld);
		undoableMelds.add(meld);
		ArrayList<VCard> temps = meld.getCards();
		for (VCard temp : temps) {
			temp.layout(l, t, r, b);
			this.addView(temp);
			// temp.layout(l, t, r, b);
		}
		readjustMelds();
	}

	protected void readjustMelds() {
		int level = 0;
		int pos = 0;
		for (Meld meld : melds) {
			if (pos + meld.size() > 26) {
				pos = 0;
				level++;
			}
			ArrayList<VCard> cards = meld.getCards();
			for (VCard card : cards) {
				this.bringChildToFront(card);
				card.setMeldPos(playerSide, level, pos);
				pos++;
			}
			pos += 4;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		this.l = l;
		this.t = t;
		this.r = r;
		this.b = b;
	}

	public void finishMelds() {
		for (Meld meld : melds) {
			meld.setUndoable(false);
		}
	}

	public void UndoMelds() {
		for (int i = 0; i < melds.size(); i++) {
			if (melds.get(i).getUndoable()) {
				ArrayList<VCard> cards = melds.get(i).getCards();
				for (VCard card : cards) {
					this.removeView(card);
				}
				melds.remove(i);
				i--;
			}
		}
		undoableMelds = new ArrayList<Meld>();
	}

	public int meldValue() {
		int total = 0;
		for (Meld meld : melds) {
			total += meld.value();
		}
		return total;
	}

	public boolean playerCanToss() {
		if (melds.size() > 0 && meldValue() < 40) {
			return false;
		}
		return true;
	}

	public void undoCards() {
		boolean hasRemoved = false;
		for (int i = 0; i < melds.size(); i++) {
			Meld meld = melds.get(i);
			if (meld.getUndoable()) {
				ArrayList<VCard> cards = meld.getCards();
				for (VCard card : cards) {
					this.removeView(card);
				}
				melds.remove(i);
				hasRemoved = true;
				i--;
			}
		}
		if (hasRemoved) {
			readjustMelds();
		}
	}

	public void endTurn() {
		if (undoableMelds.size() > 0) {
			for (Meld meld : undoableMelds) {
				meld.setUndoable(false);
			}
			undoableMelds = new ArrayList<Meld>();
		}
		attachCards = new ArrayList<VCard>();
		attachSpot = -1;
		attachSpots = new HashMap<VCard, ArrayList<Integer>>();
	}

	public boolean initialBuild() {
		return meldValue() >= 40;
	}

	public boolean checkMeldCollision(VCard card) {
		for (int i = 0; i < melds.size(); i++) {
			ArrayList<VCard> cards = melds.get(i).getCards();
			for (VCard temp : cards) {
				if (card.collideWithCard(temp)) {
					attachMeld = melds.get(i);
					attachSpot = i;
					return true;
				}
			}
		}
		return false;
	}

	public boolean canBotAttach(VCard card) {
		for (int i = 0; i < melds.size(); i++) {
			Meld meld = melds.get(i);
			if (meld.canAttach(card)) {
				attachMeld = meld;
				attachSpot = i;
				return true;
			}
		}
		return false;
	}

	public boolean canAttach(VCard card) {
		if (attachMeld.canAttach(card)) {
			return true;
		} else {
			return false;
		}
	}

	public void attach(VCard card) {
		card.layout(l, t, r, b);
		attachCards.add(card);
		if (attachSpots.containsKey(card)) {
			ArrayList<Integer> temp = attachSpots.get(card);
			temp.add(attachSpot);
			attachSpots.put(card, temp);
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>(2);
			temp.add(attachSpot);
			attachSpots.put(card, temp);
		}
		attachSpot = -1;
		attachMeld.attach(card);
		this.addView(card);
		attachMeld = null;
		readjustMelds();
	}

	public void removeAttachedCards() {
		if (attachCards.size() > 0) {
			for (VCard temp : attachCards) {
				ArrayList<Integer> i = attachSpots.get(temp);
				for (Integer x : i) {
					Meld meld = melds.get(x);
					this.removeView(meld.removeAttached(temp));
				}
			}
		}
		readjustMelds();
	}

	public boolean canReplaceJoker(VCard card) {
		if (attachMeld.canReplaceJoker(card)) {
			return true;
		}
		attachSpot = -1;
		attachMeld = null;
		return false;
	}

	public VCard replaceJoker(VCard card, JokerUndo undo) {
		VCard temp = attachMeld.replaceJoker(card);
		if (undo != null) {
			undo.setMeld(attachMeld);
			undo.setPlayerSide(playerSide);
		}
		card.layout(l, t, r, b);
		this.addView(card);
		this.removeView(temp);
		readjustMelds();
		endAttach();
		return temp;
	}

	public void endAttach() {
		attachSpot = -1;
		attachMeld = null;
	}

	public void undoJoker(JokerUndo undo) {
		undo.getMeld().removeReplaceCard(undo.getReplaceCard());
		undo.getMeld().attach(undo.getJokerCard());
		this.removeView(undo.getReplaceCard());
		this.addView(undo.getJokerCard());
	}

	public void undoReplaceJoker(JokerUndo undo) {
		this.removeView(undo.getReplaceCard());
		undo.getMeld().removeReplaceCard(undo.getReplaceCard());
		VCard joker = undo.getJokerCard();
		Card card = undo.getReplaceCard().getCard();
		joker.getCard().setJoker(card.getRank(), card.getSuit());
		undo.getMeld().attach(joker);
		this.addView(joker);
	}

	public ArrayList<Meld> getMelds() {
		// TODO Auto-generated method stub
		return melds;
	}

	public ArrayList<Card> endGame() {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (Meld meld : melds) {
			ArrayList<VCard> temp2 = meld.getCards();
			for (VCard card : temp2) {
				this.removeView(card);
				temp.add(card.getCard());
			}
		}
		melds = new ArrayList<Meld>();
		undoableMelds = new ArrayList<Meld>();
		attachSpot = -1;
		attachCards = new ArrayList<VCard>();
		attachSpots = new HashMap<VCard, ArrayList<Integer>>();
		return temp;
	}

	public void botReplaceJoker(int i) {
		attachMeld = melds.get(i);
	}
}