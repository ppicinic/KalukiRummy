package com.philpicinic.kalukirummy.threads;

import android.os.Handler;

import com.philpicinic.kalukirummy.Constants;
import com.philpicinic.kalukirummy.bot.Bot;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.HandView;
import com.philpicinic.kalukirummy.sound.SoundManager;
import com.philpicinic.kalukirummy.views.GameView;

/**
 * 
 * @author Phil Picinic
 * The Runnable to start the hand and deal the cards
 */
public class GameStart implements Runnable {

	private GameView view;
	private HandView hand;
	private Deck deck;
	private DiscardView discard;
	private int cardsToDeal;
	private Bot bot;
	private SoundManager sounds;

	public GameStart(GameView gameView, HandView handView, Deck deck,
			DiscardView discard, Bot bot, SoundManager sounds) {
		view = gameView;
		hand = handView;
		this.deck = deck;
		this.discard = discard;
		this.bot = bot;
		cardsToDeal = 13;
		this.sounds = sounds;
	}

	public void run() {
		hand.deal(deck.deal());
		bot.deal(deck.deal());
		cardsToDeal--;
		sounds.playSound("draw");
		if (cardsToDeal <= 0) {
			discard.toss(deck.deal());
			hand.handCreated();
			view.initiateHand();
			//hand.sortHand();
			view.setAnimating(false);
			view.beginHand();
		} else {
			Handler handler = new Handler();
			handler.postDelayed(this, Constants.DEAL_DELAY);
		}
	}
}
