package com.philpicinic.kalukirummy.bot;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.meld.Meld;
import com.philpicinic.kalukirummy.meld.MeldBotViewGroup;
import com.philpicinic.kalukirummy.meld.MeldFactory;
import com.philpicinic.kalukirummy.views.GameView;

public class Bot {

	private Context context;
	private ArrayList<Card> cards;
	private GameView gameView;
	private Deck deck;
	private DiscardView discard;
	private int[] priorities;
	private ArrayList<Meld> playCards;
	private int playedValue;
	private MeldBotViewGroup view;

	public Bot(Context context, GameView gameView, Deck deck,
			DiscardView discard, MeldBotViewGroup meldBotViewGroup) {
		cards = new ArrayList<Card>();
		this.context = context;
		this.gameView = gameView;
		this.deck = deck;
		this.discard = discard;
		playCards = new ArrayList<Meld>();
		playedValue = 0;
		view = meldBotViewGroup;
	}

	public void deal(Card card) {
		cards.add(card);
	}

	public void startTurn() {
		draw();
		// calcPriority();
		pickMeldsWrapper();

	}

	private void playMelds() {
		if (playCards.size() > 0) {
			System.out.println("play: " + playedValue);
			if (playedValue >= 40) {
				for (Meld meld : playCards) {
					view.addMeld(meld);
				}
				playCards = new ArrayList<Meld>();
			}
		}
		toss();
	}

	private void pickMeldsWrapper() {
		pickMelds();
		playMelds();
	}

	private void pickMelds() {
		calcPriority();
		for (int i = cards.size() - 1; i >= 0; i--) {
			// System.out.println("i " + i);
			// main loop through cards in reverse
			// check for set meld
			if (!cards.get(i).isJoker()) {
				for (int j = cards.size() - 1; j >= 0; j--) {
					// System.out.println("j " + j);
					if (j != i
							&& cards.get(i).getRank() == cards.get(j).getRank()
							&& cards.get(i).getSuit().ordinal() != cards.get(j)
									.getSuit().ordinal()) {
						for (int k = cards.size() - 1; k >= 0; k--) {
							// System.out.println("k " + k);
							if (k != i && k != j) {
								if (cards.get(i).getRank() == cards.get(k)
										.getRank()) {
									if (cards.get(i).getSuit().ordinal() != cards
											.get(k).getSuit().ordinal()
											&& cards.get(j).getSuit().ordinal() != cards
													.get(k).getSuit().ordinal()) {
										ArrayList<VCard> temp = new ArrayList<VCard>();
										temp.add(new VCard(context, 0, cards
												.get(i)));
										temp.add(new VCard(context, 0, cards
												.get(j)));
										temp.add(new VCard(context, 0, cards
												.get(k)));
										System.out.println(temp.size());
										Meld meld = MeldFactory.buildMeld(temp);
										playedValue += meld.value();
										playCards.add(meld);
										ArrayList<Integer> rtemp = new ArrayList<Integer>(
												3);
										rtemp.add(i);
										rtemp.add(j);
										rtemp.add(k);
										Collections.sort(rtemp);
										for (int x = rtemp.size() - 1; x >= 0; x--) {
											cards.remove(rtemp.get(x)
													.intValue());
										}
										pickMelds();
										return;
									}
								}
							}
						}
					}
				}
			}
			// check for run meld

		}
	}

	private void calcPriorityForPick() {
		calcPriority();
		pickMelds();
	}

	private void calcPriority() {
		priorities = new int[cards.size()];
		Collections.sort(cards);
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			priorities[i] = calcSingleCard(card, i);
		}
	}

	private int calcSingleCard(Card card, int i) {
		if (card.isJoker()) {
			return 1000;
		} else {
			int total = 0;
			int occurrences = 0;
			for (int j = 0; j < cards.size(); j++) {
				if (i != j && !cards.get(j).isJoker()) {
					if (cards.get(j).getRank() == card.getRank()) {
						if (card.getSuit().ordinal() != cards.get(j).getSuit()
								.ordinal()) {
							total += 10;
							occurrences++;
						}
					} else if (card.getSuit().ordinal() == cards.get(j)
							.getSuit().ordinal()) {
						if (card.getRank() == cards.get(j).getRank() - 1
								|| card.getRank() - 1 == cards.get(j).getRank()) {
							total += 6;
							occurrences++;
						} else if (card.getRank() == cards.get(j).getRank() - 2
								|| card.getRank() - 2 == cards.get(j).getRank()) {
							total += 2;
							occurrences++;
						}
					}

				}
			}

			total *= occurrences;
			if (playedValue < 40) {
				if (card.getRank() >= 7) {
					// total *= 2;
					total += card.getRank();
				}
			} else {
				if (card.getRank() <= 6) {
					total += (28 / card.getRank());
					// total *= 2;
				}
			}
			for (int j = 0; j < cards.size(); j++) {
				if (i != j) {
					if (card.getRank() == cards.get(j).getRank()
							&& card.getSuit().ordinal() == cards.get(j)
									.getSuit().ordinal()) {
						total /= 4;
					}
				}
			}
			return total;
		}

	}

	public void draw() {
		cards.add(deck.deal());
	}

	public void toss() {
		calcPriority();
		int spot = 0;
		for (int i = 1; i < priorities.length; i++) {
			if (priorities[i] < priorities[spot]) {
				spot = i;
			}
		}
		// Log info
		for (int i = 0; i < cards.size(); i++) {
			String result = "Card " + i + ": " + cards.get(i).getRank() + " "
					+ cards.get(i).getSuit();
			System.out.println(result);
		}
		printPriorities();
		System.out.println(spot);
		discard.toss(cards.remove(spot));
		endTurn();
	}

	private void printPriorities() {
		String str = "";
		for (int i = 0; i < priorities.length; i++) {
			str += priorities[i] + ", ";
		}
		System.out.println(str);
	}

	public void endTurn() {
		gameView.endBotTurn();
	}

}
