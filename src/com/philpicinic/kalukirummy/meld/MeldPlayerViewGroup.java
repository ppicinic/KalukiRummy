package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldPlayerViewGroup extends ViewGroup {

	private ArrayList<Meld> melds;
	private ArrayList<Meld> undoableMelds;
	private int l;
	private int t;
	private int r;
	private int b;

	public MeldPlayerViewGroup(Context context) {
		super(context);
		melds = new ArrayList<Meld>();
		undoableMelds = new ArrayList<Meld>();
	}

	public void addMeld(Meld meld) {
		melds.add(meld);
		undoableMelds.add(meld);
		ArrayList<VCard> temps = meld.getCards();
		for (VCard temp : temps) {
			this.addView(temp);
			temp.layout(l, t, r, b);
		}
		readjustMelds();
	}

	private void readjustMelds() {
		int level = 0;
		int pos = 0;
		for (Meld meld : melds) {
			if (pos + meld.size() > 26) {
				pos = 0;
				level++;
			}
			ArrayList<VCard> cards = meld.getCards();
			for (VCard card : cards) {
				card.setMeldPos(true, level, pos);
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
	}
}
