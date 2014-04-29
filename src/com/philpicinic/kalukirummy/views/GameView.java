package com.philpicinic.kalukirummy.views;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.philpicinic.kalukirummy.Constants;
import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.bot.Bot;
import com.philpicinic.kalukirummy.bot.BotView;
import com.philpicinic.kalukirummy.buttons.StartHandButton;
import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.Suit;
import com.philpicinic.kalukirummy.card.UndoCards;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DeckView;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.HandView;
import com.philpicinic.kalukirummy.meld.JokerUndo;
import com.philpicinic.kalukirummy.meld.MeldFactory;
import com.philpicinic.kalukirummy.meld.MeldViewGroup;
import com.philpicinic.kalukirummy.score.ScoreCardView;
import com.philpicinic.kalukirummy.sound.SoundManager;
import com.philpicinic.kalukirummy.threads.BotTurn;
import com.philpicinic.kalukirummy.threads.GameStart;

/**
 * 
 * @author Phil Picinic
 * 
 *         Highest Level view of the game activity Contains and manages all
 *         children
 */
public class GameView extends ViewGroup {

	private Context context;

	@SuppressWarnings("unused")
	private int screenW;
	@SuppressWarnings("unused")
	private int screenH;

	private HandView hand;
	private UndoCards undoCards;

	private StartHandButton startHand;

	private DeckView deckV;
	private DiscardView discard;

	private BotView bot;
	private ScoreCardView scoreCard;

	private MeldViewGroup meldViewGroup;
	private Deck deck;

	private boolean animating;

	private TurnState turnState;

	private boolean start;
	private boolean returnToHand;
	private boolean playCards;
	private boolean undo;
	private boolean drawFromDiscard;
	private boolean hasDrawnFromDiscard;

	private VCard movingCard;
	private VCard jokerCard;
	private Stack<JokerUndo> jokerUndo;

	private Bot botPlayer;
	private BotTurn botTurn;
	
	private ToastView toastView;
	private Random random;
	
	private SoundManager sounds;

	/**
	 * Create the GameView Group Creates all child elements of the layout
	 * 
	 * @param context
	 *            the context of the activity
	 */
	public GameView(Context context) {
		super(context);
		this.context = context;

		random = new Random(System.currentTimeMillis());
		
		sounds = new SoundManager(context);
		
		toastView = new ToastView(context);
		this.addView(toastView);

		animating = false;
		turnState = TurnState.START;
		start = false;
		// Creates Background
		BackgroundView bg = new BackgroundView(context);
		this.addView(bg);

		startHand = new StartHandButton(context);
		this.addView(startHand);
		// Creates the Deck and Discard Pile
		deckV = new DeckView(context);
		this.addView(deckV);
		discard = new DiscardView(context);
		this.addView(discard);
		deck = new Deck(discard);

		// Create the bot opponent
		bot = new BotView(context);
		this.addView(bot);

		meldViewGroup = new MeldViewGroup(context, toastView, sounds);
		this.addView(meldViewGroup);

		// Creates the scorecard button
		scoreCard = new ScoreCardView(context);
		this.addView(scoreCard);

		hand = new HandView(context);
		this.addView(hand);

		undoCards = new UndoCards();
		jokerUndo = new Stack<JokerUndo>();
		botPlayer = new Bot(context, this, deck, discard,
				meldViewGroup.getBotView(), meldViewGroup.getPlayerView(), bot, hand, scoreCard);
		botTurn = new BotTurn(botPlayer);
		
		
	}

	/**
	 * Sets animating to the view
	 * @param animating animating setting
	 */
	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (animating || turnState == TurnState.BOT) {
			return true;
		}
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			if (turnState == TurnState.START) {
				return true; 
			} else if (turnState == TurnState.DRAW) {
				return deckV.checkCollision(event);
			} else if (turnState == TurnState.PLAY) {
				if (meldViewGroup.checkPlayCollisions(event)) {
				}
				if (meldViewGroup.checkPlayMeld(event)) {
					return true;
				}
			}

		}

		if (e == MotionEvent.ACTION_MOVE) {
			if (turnState == TurnState.PLAY) {
				if (movingCard == null) {
					if ((movingCard = hand.getMovingCard()) != null) {
						meldViewGroup.initiateMovingCard();
					}
				}
				if (returnToHand) {
				}

			}
		}
		if (e == MotionEvent.ACTION_UP) {
			if (turnState == TurnState.DRAW) {
				hand.handCreated();
			}
			if (turnState == TurnState.PLAY) {
				if (movingCard != null) {

					if (discard.checkCollision(movingCard)
							&& !meldViewGroup.playingCards()) {
						if (meldViewGroup.playerCanToss()) {
							if (hasDrawnFromDiscard
									&& !meldViewGroup.hasInitialBuild()) {
								toastView.showToast(getResources().getString(R.string.initial_discard), Toast.LENGTH_SHORT);
								sounds.playSound("error");
							} else {
								hasDrawnFromDiscard = false;
								hand.removeMovingCard();
								movingCard.dispatchTouchEvent(event);
								discard.toss(movingCard);
								sounds.playSound("play");
								endTurn();
								
								if (hand.handSize() == 0) {
									scoreCard.addGame(0, botPlayer.handValue());
									if (scoreCard.getBotScore() > 100) {
										toastView.showToast(getResources().getString(R.string.game_win), Toast.LENGTH_LONG);
										scoreCard.endGame();
										sounds.playSound("win");
									} else {
										toastView.showToast(getResources().getString(R.string.hand_win), Toast.LENGTH_LONG);
										sounds.playSound("win");
									}
									this.addView(startHand);
									turnState = TurnState.START;
								} else {
									turnState = TurnState.BOT;
									Handler handler = new Handler();
									int wait = random.nextInt(1500) + 1000;
									handler.postDelayed(botTurn, wait);
								}
							}
						} else {
							toastView.showToast(getResources().getString(R.string.initial_build), Toast.LENGTH_SHORT);
							sounds.playSound("error");
						}
					} else if (meldViewGroup.checkCollisionByCard(movingCard)) {
						hand.removeMovingCard();
						meldViewGroup.placeCard(movingCard);
						if (movingCard.getCard().isJoker()) {
							jokerCard = movingCard;
							showChooseSuitDialog(false, false);
						}
					} else if (meldViewGroup.checkAttachCollision(movingCard)) {
						if (movingCard.getCard().isJoker()) {
							movingCard.dispatchTouchEvent(event);
							jokerCard = movingCard;
							hand.removeMovingCard();
							showChooseSuitDialog(true, true);
							// handle a joker dialog and attach
						} else {
							if (meldViewGroup.canAttach(movingCard)) {
								movingCard.dispatchTouchEvent(event);
								hand.removeMovingCard();
								meldViewGroup.attachToPlayer(movingCard);
								undoCards.addAttachedCards(movingCard);
								sounds.playSound("play");
							} else if (meldViewGroup
									.canReplacePlayerJoker(movingCard)) {
								movingCard.dispatchTouchEvent(event);
								hand.removeMovingCard();
								JokerUndo tempUndo = new JokerUndo(movingCard);
								VCard temp = meldViewGroup.replacePlayerJoker(
										movingCard, tempUndo);
								temp.getCard().unSetJoker();
								tempUndo.setJokerCard(temp);
								jokerUndo.push(tempUndo);
								hand.deal(temp);
								sounds.playSound("play");
							}
						}
					}
					movingCard = null;
					if (!meldViewGroup.playingCards()) {
						meldViewGroup.deinitiateMovingCard();
						hand.handCreated();
					}
				} else if (returnToHand) {
				}
			}
		}
		return false;
	}

	/**
	 * Handling ending a turn
	 */
	private void endTurn() {
		meldViewGroup.endTurn();
		this.jokerUndo = new Stack<JokerUndo>();
		undoCards.reset();
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(animating || turnState == TurnState.BOT){
			return true;
		}
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			if (turnState == TurnState.START) {
				start = startHand.checkCollision(event);
				return start;
			} else if (turnState == TurnState.DRAW) {
				if (deckV.checkCollision(event)) {
					start = true;
					return true;
				}
				if (discard.checkDraw(event)) {
					drawFromDiscard = true;
					return true;
				}
			} else if (turnState == TurnState.PLAY) {
				if (meldViewGroup.checkPlayCollisions(event)) {
					returnToHand = true;
					return true;
				}
				if (meldViewGroup.checkPlayMeld(event)) {
					playCards = true;
					return true;
				}
				if (meldViewGroup.checkUndo(event)) {
					undo = true;
					return true;
				}
			}
		}
		if (e == MotionEvent.ACTION_MOVE) {

		}
		if (e == MotionEvent.ACTION_UP) {
			if (animating || turnState == TurnState.BOT) {
				return true;
			}
			if (turnState == TurnState.START) {
				if (start) {
					this.removeView(startHand);
					start = false;
					animating = true;
					turnState = TurnState.DRAW;

					ArrayList<Card> temp = botPlayer.endHand();
					deck.returnCards(temp);
					temp = meldViewGroup.endGame();
					deck.returnCards(temp);
					temp = hand.endGame();
					deck.returnCards(temp);
					temp = discard.endGame();
					deck.returnCards(temp);
					deck.shuffle();
					bot.update(13);
					Handler handler = new Handler();
					GameStart gameStart = new GameStart(this, hand, deck,
							discard, botPlayer, sounds);
					handler.postDelayed(gameStart, Constants.DEAL_DELAY);
					return true;
				}
			} else if (turnState == TurnState.DRAW) {
				hand.handCreated();
				if (start) {
					sounds.playSound("draw");
					
					Card card = deck.deal();
					undoCards.addDrawCard(true, card);
					hand.deal(card);
					turnState = TurnState.PLAY;
					start = false;
					return true;
				}
				if (drawFromDiscard) {
					sounds.playSound("draw");
					VCard card = discard.drawFromPile();
					undoCards.addDrawCard(false, card.getCard());
					hand.deal(card);
					turnState = TurnState.PLAY;
					drawFromDiscard = false;
					hasDrawnFromDiscard = true;
					return true;
				}
			} else if (turnState == TurnState.PLAY) {
				if (returnToHand) {
					VCard card = meldViewGroup.removeCardFromPlay();
					if (card.getCard().isJoker()) {
						card.getCard().unSetJoker();
					}
					hand.deal(card);
					if (!meldViewGroup.playingCards()) {
						hand.handCreated();
					}
					returnToHand = false;
					return true;
				}
				if (playCards) {
					ArrayList<VCard> tempCards = meldViewGroup
							.getPlayingCards();
					if (MeldFactory.validate(tempCards)) {

						meldViewGroup.removeAllPlayingCards();
						undoCards.addMeldCards(tempCards);
						meldViewGroup.addMeld(MeldFactory.buildMeld(tempCards));
						sounds.playSound("play");
					} else {
						toastView.showToast(getResources().getString(R.string.invalid_meld), Toast.LENGTH_SHORT);
						sounds.playSound("error");
					}
					playCards = false;
					return true;
				}
				if (undo) {
					handleUndo();
				}
			}
		}
		return false;
	}

	/**
	 * handling an undo
	 */
	private void handleUndo() {
		while (!jokerUndo.isEmpty()) {
			JokerUndo tempUndo = jokerUndo.pop();
			hand.removeJokerCard(tempUndo.getJokerCard());
			meldViewGroup.undoJokerReplace(tempUndo);
			hand.deal(tempUndo.getReplaceCard());
		}
		meldViewGroup.removeAttachedPlayerCards();
		ArrayList<VCard> cards = undoCards.getCards();
		meldViewGroup.undoCards();
		for (VCard temp : cards) {
			if (temp.getCard().isJoker()) {
				temp.getCard().unSetJoker();
			}
			hand.deal(temp);
		}
		Card card = undoCards.getDrawCard();
		hand.removeCard(card);
		if (undoCards.isFromDeck()) {
			deck.returnToTop(card);
		} else {
			discard.toss(card);
		}
		undoCards.reset();
		hasDrawnFromDiscard = false;
		turnState = TurnState.DRAW;
	}

	/**
	 * updates sizes if the display is changed
	 * 
	 * @param w
	 *            width of the screen
	 * @param h
	 *            height of the screen
	 * @param oldw
	 *            old width of the screen
	 * @param oldh
	 *            old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}

	/**
	 * Updates the layout of all child elements
	 */
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		for (int i = 0; i < this.getChildCount(); i++) {
			this.getChildAt(i).layout(arg1, arg2, arg3, arg4);
		}

		this.meldViewGroup.layout(arg1, arg2, arg3, arg4);
	}

	/**
	 * initiate a hand
	 */
	public void initiateHand() {
		meldViewGroup.initiateHand();
	}

	/**
	 * Show the joker selection dialog
	 * @param isAttach is the card being attached
	 * @param player is the attach on the player side
	 */
	private void showChooseSuitDialog(final boolean isAttach,
			final boolean player) {
		final Dialog chooseSuitDialog = new Dialog(context);
		chooseSuitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		chooseSuitDialog.setContentView(R.layout.choose_joker_dialog);
		final Spinner rankSpinner = (Spinner) chooseSuitDialog
				.findViewById(R.id.rankSpinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				context, R.array.ranks, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rankSpinner.setAdapter(adapter2);
		final Spinner suitSpinner = (Spinner) chooseSuitDialog
				.findViewById(R.id.suitSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.suits, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		suitSpinner.setAdapter(adapter);

		Button okButton = (Button) chooseSuitDialog.findViewById(R.id.okButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				int rank = rankSpinner.getSelectedItemPosition() + 2;
				int i = suitSpinner.getSelectedItemPosition();
				Suit suit = Suit.DIAMONDS;
				switch (i) {
				case 0:
					suit = Suit.DIAMONDS;
					break;
				case 1:
					suit = Suit.CLUBS;
					break;
				case 2:
					suit = Suit.HEARTS;
					break;
				case 3:
					suit = Suit.SPADES;
					break;
				default:
					break;
				}
				jokerCard.getCard().setJoker(rank, suit);
				if (isAttach) {
					if (player) {
						if (meldViewGroup.canAttach(jokerCard)) {
							sounds.playSound("play");
							meldViewGroup.attachToPlayer(jokerCard);
							undoCards.addAttachedCards(jokerCard);
						} else {
							jokerCard.getCard().unSetJoker();
							hand.deal(jokerCard);
						}
					}
					meldViewGroup.endPlayerAttach();
				} else {
					meldViewGroup.sortPlayingCards();
				}
				jokerCard = null;
				chooseSuitDialog.dismiss();
			}
		});
		chooseSuitDialog.show();
	}

	/**
	 * End a bot's turn
	 */
	public void endBotTurn() {
		endTurn();
		if (botPlayer.handSize() == 0) {
			scoreCard.addGame(hand.handValue(), 0);
			if (scoreCard.getPlayerScore() > 100) {
				toastView.showToast(getResources().getString(R.string.game_lose), Toast.LENGTH_LONG);
				sounds.playSound("lose");
				scoreCard.endGame();
			} else {
				toastView.showToast(getResources().getString(R.string.hand_lose), Toast.LENGTH_LONG);
				sounds.playSound("lose");
			}
			this.addView(startHand);
			turnState = TurnState.START;
		} else {
			toastView.showToast(getResources().getString(R.string.your_turn), Toast.LENGTH_SHORT);
			sounds.playSound("turn");
			turnState = TurnState.DRAW;
		}
	}
}
