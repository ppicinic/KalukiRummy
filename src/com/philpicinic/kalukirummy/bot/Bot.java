package com.philpicinic.kalukirummy.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.Suit;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.meld.Meld;
import com.philpicinic.kalukirummy.meld.MeldBotViewGroup;
import com.philpicinic.kalukirummy.meld.MeldFactory;
import com.philpicinic.kalukirummy.meld.MeldPlayerViewGroup;
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
	private boolean initial;
	private boolean attached;
	private Random random;
	private MeldPlayerViewGroup playerView;

	public Bot(Context context, GameView gameView, Deck deck,
			DiscardView discard, MeldBotViewGroup meldBotViewGroup,
			MeldPlayerViewGroup playerView) {
		cards = new ArrayList<Card>();
		this.context = context;
		this.gameView = gameView;
		this.deck = deck;
		this.discard = discard;
		playCards = new ArrayList<Meld>();
		playedValue = 0;
		view = meldBotViewGroup;
		random = new Random(System.currentTimeMillis());
		this.playerView = playerView;
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
			if (true){//playedValue >= 40) {
				for (Meld meld : playCards) {
					view.addMeld(meld);
				}
				initial = true;
				playCards = new ArrayList<Meld>();
			}
		}
		jokerReplaceWrapper();
	}

	private void attach() {
		calcPriority();
		if (initial && !attached) {
			for (int i = 0; i < cards.size(); i++) {
				if (cards.size() <= 3
						|| (priorities[i] * (random.nextInt(100) + 1) < 200)) {
					if (cards.get(i).isJoker()) {
						ArrayList<Meld> melds = playerView.getMelds();
						for (Meld meld : melds) {
							ArrayList<VCard> temp = meld.getCards();
							Card first = temp.get(0).getCard();
							Card second = temp.get(1).getCard();
							if (first.getRank() == second.getRank()) {
								// handle set
								if (temp.size() < 4) {
									Suit suit = Suit.DIAMONDS;
									if (temp.get(0).getCard().getSuit()
											.ordinal() == 0) {
										suit = Suit.CLUBS;
										if (temp.get(1).getCard().getSuit()
												.ordinal() == 1) {
											suit = Suit.HEARTS;
											if (temp.get(2).getCard().getSuit()
													.ordinal() == 2) {
												suit = Suit.SPADES;
											}
										}
									}
									cards.get(i)
											.setJoker(first.getRank(), suit);
									if (playerView.canBotAttach(new VCard(
											context, 2, cards.get(i)))) {
										view.attach(new VCard(context, 0, cards
												.get(i)));
										cards.remove(i);
										attached = true;
										break;
									} else {
										cards.get(i).unSetJoker();
									}
								}
							} else {
								if (temp.size() < 12) {
									int rank = temp.get(temp.size() - 1)
											.getCard().getRank() + 1;
									if (rank == 15) {
										rank = temp.get(0).getCard().getRank() - 1;
									}
									cards.get(i).setJoker(rank,
											temp.get(0).getCard().getSuit());
									if (playerView.canBotAttach(new VCard(
											context, 2, cards.get(i)))) {
										view.attach(new VCard(context, 0, cards
												.get(i)));
										cards.remove(i);
										attached = true;
										break;
									} else {
										cards.get(i).unSetJoker();
									}
								}
							}
						}
						if (!attached) {
							melds = view.getMelds();
							for (Meld meld : melds) {
								ArrayList<VCard> temp = meld.getCards();
								Card first = temp.get(0).getCard();
								Card second = temp.get(1).getCard();
								if (first.getRank() == second.getRank()) {
									// handle set
									if (temp.size() < 4) {
										Suit suit = Suit.DIAMONDS;
										if (temp.get(0).getCard().getSuit()
												.ordinal() == 0) {
											suit = Suit.CLUBS;
											if (temp.get(1).getCard().getSuit()
													.ordinal() == 1) {
												suit = Suit.HEARTS;
												if (temp.get(2).getCard()
														.getSuit().ordinal() == 2) {
													suit = Suit.SPADES;
												}
											}
										}
										cards.get(i).setJoker(first.getRank(),
												suit);
										if (playerView.canBotAttach(new VCard(
												context, 2, cards.get(i)))) {
											view.attach(new VCard(context, 0,
													cards.get(i)));
											cards.remove(i);
											attached = true;
											break;
										} else {
											cards.get(i).unSetJoker();
										}
									}
								} else {
									if (temp.size() < 12) {
										int rank = temp.get(temp.size() - 1)
												.getCard().getRank() + 1;
										if (rank == 15) {
											rank = temp.get(0).getCard()
													.getRank() - 1;
										}
										cards.get(i)
												.setJoker(
														rank,
														temp.get(0).getCard()
																.getSuit());
										if (playerView.canBotAttach(new VCard(
												context, 2, cards.get(i)))) {
											view.attach(new VCard(context, 0,
													cards.get(i)));
											cards.remove(i);
											attached = true;
											break;
										} else {
											cards.get(i).unSetJoker();
										}
									}
								}
							}
						}
						break;
					}
					if (view.canBotAttach(new VCard(context, 2, cards.get(i)))) {
						view.attach(new VCard(context, 0, cards.get(i)));
						cards.remove(i);
						attached = true;
						break;
					}
					if (playerView.canBotAttach(new VCard(context, 2, cards
							.get(i)))) {
						playerView.attach(new VCard(context, 0, cards.get(i)));
						cards.remove(i);
						attached = true;
						break;
					}
				}
			}
		}
		toss();
	}

	private void pickMeldsWrapper() {
		pickMelds();
		pickJokerMelds();
		playMelds();
	}

	private void pickJokerMelds() {
		if (cards.size() >= 4) {
			int jokers = jokerCount();
			if (jokers == 2 && (cards.size() == 4 || cards.size() == 5)) {
				if (cards.get(0).isJoker()) {
					// Create 3 jokers of 2 each different suit
					ArrayList<VCard> temp = new ArrayList<VCard>();
					cards.get(0).setJoker(2, Suit.CLUBS);
					cards.get(1).setJoker(3, Suit.DIAMONDS);
					cards.get(2).setJoker(3, Suit.SPADES);
					temp.add(new VCard(context, 0, cards.get(0)));
					temp.add(new VCard(context, 0, cards.get(1)));
					temp.add(new VCard(context, 0, cards.get(2)));
					Meld meld = MeldFactory.buildMeld(temp);
					playedValue += meld.value();
					playCards.add(meld);
					cards.remove(0);
					cards.remove(0);
					cards.remove(0);
					pickJokerMelds();
					return;
				} else {
					// Create 3 cards run of first suit this is easy
					Suit suit = cards.get(0).getSuit();
					int rank = cards.get(0).getRank();
					ArrayList<VCard> temp = new ArrayList<VCard>();
					if (rank == 2) {
						cards.get(cards.size() - 1).setJoker(3, suit);
						cards.get(cards.size() - 2).setJoker(4, suit);
					} else if (rank == 14) {
						cards.get(cards.size() - 1).setJoker(13, suit);
						cards.get(cards.size() - 2).setJoker(12, suit);
					} else {
						cards.get(cards.size() - 1).setJoker(rank + 1, suit);
						cards.get(cards.size() - 2).setJoker(rank - 1, suit);
					}
					temp.add(new VCard(context, 0, cards.get(cards.size() - 1)));
					temp.add(new VCard(context, 0, cards.get(cards.size() - 2)));
					temp.add(new VCard(context, 0, cards.get(0)));
					Meld meld = MeldFactory.buildMeld(temp);
					playedValue += meld.value();
					playCards.add(meld);
					cards.remove(cards.size() - 1);
					cards.remove(cards.size() - 1);
					cards.remove(0);
					pickJokerMelds();
					return;
				}
			}
			if ((jokers >= 1 && cards.size() <= 5)
					|| (jokers >= 1 && random.nextInt(100) < 50)) {
				for (int i = 0; i < cards.size() - 1; i++) {
					if (!cards.get(i).isJoker()) {
						for (int j = 0; j < cards.size() - 1; j++) {
							if (i != j && !cards.get(j).isJoker()) {
								if (cards.get(i).getRank() == cards.get(j)
										.getRank()
										&& cards.get(i).getSuit().ordinal() != cards
												.get(j).getSuit().ordinal()) {
									// Create set with joker
									Suit suit = Suit.DIAMONDS;
									if (cards.get(i).getSuit().ordinal() == 0
											|| cards.get(j).getSuit().ordinal() == 0) {
										if (cards.get(i).getSuit().ordinal() == 1
												|| cards.get(j).getSuit()
														.ordinal() == 1) {
											suit = Suit.SPADES;
										} else {
											suit = Suit.CLUBS;
										}
									}
									cards.get(cards.size() - 1).setJoker(
											cards.get(i).getRank(), suit);
									ArrayList<VCard> temp = new ArrayList<VCard>();
									temp.add(new VCard(context, 0, cards.get(i)));
									temp.add(new VCard(context, 0, cards.get(j)));
									temp.add(new VCard(context, 0, cards
											.get(cards.size() - 1)));
									Meld meld = MeldFactory.buildMeld(temp);
									playedValue += meld.value();
									playCards.add(meld);
									ArrayList<Integer> rtemp = new ArrayList<Integer>(
											3);
									rtemp.add(i);
									rtemp.add(j);
									rtemp.add(cards.size() - 1);
									Collections.sort(rtemp);
									for (int x = rtemp.size() - 1; x >= 0; x--) {
										cards.remove(rtemp.get(x).intValue());
									}
									pickJokerMelds();
									return;
								}
								if (cards.get(i).getSuit().ordinal() == cards
										.get(j).getSuit().ordinal()
										&& cards.get(i).getRank() == cards.get(
												j).getRank() - 1) {
									// Create run with joker
									if (cards.get(j).isAce()) {
										cards.get(cards.size() - 1).setJoker(
												12, cards.get(i).getSuit());
									} else {
										cards.get(cards.size() - 1).setJoker(
												cards.get(i).getRank() + 2,
												cards.get(i).getSuit());
									}
									ArrayList<VCard> temp = new ArrayList<VCard>();
									temp.add(new VCard(context, 0, cards.get(i)));
									temp.add(new VCard(context, 0, cards.get(j)));
									temp.add(new VCard(context, 0, cards
											.get(cards.size() - 1)));
									Meld meld = MeldFactory.buildMeld(temp);
									playedValue += meld.value();
									playCards.add(meld);
									ArrayList<Integer> rtemp = new ArrayList<Integer>(
											3);
									rtemp.add(i);
									rtemp.add(j);
									rtemp.add(cards.size() - 1);
									Collections.sort(rtemp);
									for (int x = rtemp.size() - 1; x >= 0; x--) {
										cards.remove(rtemp.get(x).intValue());
									}
									pickJokerMelds();
									return;
								}
							}
						}
					}
				}
			}

		}
	}

	private int jokerCount() {
		int result = 0;
		for (int i = cards.size() - 1; i >= 0; i--) {
			if (cards.get(i).isJoker()) {
				result++;
			} else {
				return result;
			}
		}
		return result;
	}

	private void pickMelds() {
		// calcPriority();
		if (cards.size() >= 4) {
			Collections.sort(cards);
			for (int i = cards.size() - 1; i >= 0; i--) {
				// System.out.println("i " + i);
				// main loop through cards in reverse
				// check for set meld
				if (!cards.get(i).isJoker()) {
					for (int j = cards.size() - 1; j >= 0; j--) {
						// System.out.println("j " + j);
						if (j != i
								&& cards.get(i).getRank() == cards.get(j)
										.getRank()
								&& cards.get(i).getSuit().ordinal() != cards
										.get(j).getSuit().ordinal()) {
							for (int k = cards.size() - 1; k >= 0; k--) {
								// System.out.println("k " + k);
								if (k != i && k != j) {
									if (cards.get(i).getRank() == cards.get(k)
											.getRank()) {
										if (cards.get(i).getSuit().ordinal() != cards
												.get(k).getSuit().ordinal()
												&& cards.get(j).getSuit()
														.ordinal() != cards
														.get(k).getSuit()
														.ordinal()) {
											ArrayList<VCard> temp = new ArrayList<VCard>();
											temp.add(new VCard(context, 0,
													cards.get(i)));
											temp.add(new VCard(context, 0,
													cards.get(j)));
											temp.add(new VCard(context, 0,
													cards.get(k)));
											System.out.println(temp.size());
											Meld meld = MeldFactory
													.buildMeld(temp);
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
			for (int i = cards.size() - 1; i >= 0; i--) {
				if (!cards.get(i).isJoker()) {
					for (int j = cards.size() - 1; j >= 0; j--) {
						if (j != i
								&& !cards.get(j).isJoker()
								&& cards.get(i).getSuit().ordinal() == cards
										.get(j).getSuit().ordinal()
								&& cards.get(i).getRank() == cards.get(j)
										.getRank() + 1) {
							for (int k = cards.size() - 1; k >= 0; k--) {
								if (k != i
										&& k != j
										&& !cards.get(k).isJoker()
										&& cards.get(i).getSuit().ordinal() == cards
												.get(k).getSuit().ordinal()
										&& cards.get(i).getRank() == cards.get(
												k).getRank() + 2) {
									ArrayList<VCard> temp = new ArrayList<VCard>();
									temp.add(new VCard(context, 0, cards.get(i)));
									temp.add(new VCard(context, 0, cards.get(j)));
									temp.add(new VCard(context, 0, cards.get(k)));
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
										cards.remove(rtemp.get(x).intValue());
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
		view.endTurn();
		playerView.endTurn();
		gameView.endBotTurn();
	}

	public int handSize() {
		return cards.size() + playCards.size();
	}

	public ArrayList<Card> endHand() {
		ArrayList<Card> temp = cards;
		for(Meld meld : playCards){
			ArrayList<VCard> temp2 = meld.getCards();
			for(VCard card: temp2){
				temp.add(card.getCard());
			}
		}
		cards = new ArrayList<Card>();
		playCards = new ArrayList<Meld>();
		playedValue = 0;
		initial = false;
		attached = false;
		return cards;
	}
	
	public void jokerReplaceWrapper(){
		jokerReplace();
		attach();
	}
	public void jokerReplace(){
		ArrayList<Meld> melds = view.getMelds();
		for(int i = 0; i < melds.size(); i++){
			ArrayList<VCard> temp = melds.get(i).getCards();
			for(VCard card: temp){
				if(card.getCard().isJoker()){
					Card c = card.getCard();
					for(int j = 0; j < cards.size(); j++){
						Card c2 = cards.get(j);
						if(!c2.isJoker() && c2.getRank() == c.getMeldRank() && c2.getSuit().ordinal() == c.getMeldSuit().ordinal()){
							view.botReplaceJoker(i);
							if(view.canReplaceJoker(new VCard(context, 0, c2))){
								VCard c3 = view.replaceJoker(new VCard(context, 0, c2), null);
								cards.remove(j);
								cards.add(c3.getCard());
								jokerReplace();
								return;
							}
						}
					}
				}
			}
		}
		melds = playerView.getMelds();
		for(int i = 0; i < melds.size(); i++){
			ArrayList<VCard> temp = melds.get(i).getCards();
			for(VCard card: temp){
				if(card.getCard().isJoker()){
					Card c = card.getCard();
					for(int j = 0; j < cards.size(); j++){
						Card c2 = cards.get(j);
						if(!c2.isJoker() && c2.getRank() == c.getMeldRank() && c2.getSuit().ordinal() == c.getMeldSuit().ordinal()){
							playerView.botReplaceJoker(i);
							if(playerView.canReplaceJoker(new VCard(context, 0, c2))){
								VCard c3 = playerView.replaceJoker(new VCard(context, 0, c2), null);
								cards.remove(j);
								c3.getCard().unSetJoker();
								cards.add(c3.getCard());
								jokerReplace();
								return;
							}
						}
					}
				}
			}
		}
		
	}
}
