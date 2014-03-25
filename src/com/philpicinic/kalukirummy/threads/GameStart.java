package com.philpicinic.kalukirummy.threads;

import android.os.Handler;

import com.philpicinic.kalukirummy.Constants;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.HandView;
import com.philpicinic.kalukirummy.views.GameView;

public class GameStart implements Runnable {

	private GameView view;
	private HandView hand;
	private Deck deck;
	private DiscardView discard;
	private int cardsToDeal;

	public GameStart(GameView gameView, HandView handView, Deck deck,
			DiscardView discard) {
		view = gameView;
		hand = handView;
		this.deck = deck;
		this.discard = discard;
		cardsToDeal = 13;
	}

	public void run() {
		hand.deal(deck.deal());
		cardsToDeal--;
		if (cardsToDeal <= 0) {
			discard.toss(deck.deal());
			hand.sortHand();
			view.setAnimating(false);
		} else {
			Handler handler = new Handler();
			handler.postDelayed(this, Constants.DEAL_DELAY);
		}
	}
}
