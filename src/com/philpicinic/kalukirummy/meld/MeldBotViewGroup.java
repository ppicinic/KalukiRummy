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
		playerSide = false;
	}
}
