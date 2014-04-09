package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;
import java.util.HashMap;

import com.philpicinic.kalukirummy.card.VCard;

import android.content.Context;

public class MeldBotViewGroup extends MeldPlayerViewGroup {

	public MeldBotViewGroup(Context context) {
		super(context);
		melds = new ArrayList<Meld>();
		undoableMelds = new ArrayList<Meld>();
		attachSpot = -1;
		attachCards = new ArrayList<VCard>();
		attachSpots = new HashMap<VCard, ArrayList<Integer>>();
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
				card.setMeldPos(false, level, pos);
				pos++;
			}
			pos += 4;
		}
	}
}
