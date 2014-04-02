package com.philpicinic.kalukirummy.views;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import com.philpicinic.kalukirummy.Constants;
import com.philpicinic.kalukirummy.bot.BotView;
import com.philpicinic.kalukirummy.buttons.StartHandButton;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.Deck;
import com.philpicinic.kalukirummy.deck.DeckView;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.HandView;
import com.philpicinic.kalukirummy.meld.MeldFactory;
import com.philpicinic.kalukirummy.meld.MeldViewGroup;
import com.philpicinic.kalukirummy.score.ScoreCardView;
import com.philpicinic.kalukirummy.threads.GameStart;

/**
 * 
 * @author Phil Picinic
 * 
 *         Highest Level view of the game activity Contains and manages all
 *         children
 */
public class GameView extends ViewGroup {

	@SuppressWarnings("unused")
	private Context context;

	@SuppressWarnings("unused")
	private int screenW;
	@SuppressWarnings("unused")
	private int screenH;

	private HandView hand;

	// private CardMove rightArrow;
	// private CardMove leftArrow;

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

	private VCard movingCard;

	/**
	 * Create the GameView Group Creates all child elements of the layout
	 * 
	 * @param context
	 *            the context of the activity
	 */
	public GameView(Context context) {
		super(context);
		this.context = context;

		deck = new Deck();

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

		// Create the bot opponent
		bot = new BotView(context);
		this.addView(bot);

		meldViewGroup = new MeldViewGroup(context);
		this.addView(meldViewGroup);
		// this.addView(meldArea);

		// Creates the scorecard button
		scoreCard = new ScoreCardView(context);
		this.addView(scoreCard);

		hand = new HandView(context);
		this.addView(hand);

	}

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
				return true; // startHand.checkCollision(event);
			} else if (turnState == TurnState.DRAW) {
				return deckV.checkCollision(event);
			} else if (turnState == TurnState.PLAY) {
				if (meldViewGroup.checkPlayCollisions(event)) {
					// hand.deal(returnToHand);
					// returnToHand = null;
					// returnToHand = true;
					// return true;
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
					// return true;
				}

			}
		}
		if (e == MotionEvent.ACTION_UP) {
			if (turnState == TurnState.DRAW) {
				hand.handCreated();
			}
			if (turnState == TurnState.PLAY) {
				// VCard temp = hand.getMovingCard();
				if (movingCard != null) {

					if (discard.checkCollision(movingCard)
							&& !meldViewGroup.playingCards()) {
						if (meldViewGroup.playerCanToss()) {
							hand.removeMovingCard();
							discard.toss(movingCard);
							turnState = TurnState.DRAW;
						} else {
							CharSequence text = "You need 40 points for your initial build!";
							int duration = Toast.LENGTH_SHORT;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					} else if (meldViewGroup.checkCollisionByCard(movingCard)) {
						hand.removeMovingCard();
						meldViewGroup.placeCard(movingCard);
					}
					movingCard = null;
					if (!meldViewGroup.playingCards()) {
						meldViewGroup.deinitiateMovingCard();
						hand.handCreated();
					}
				} else if (returnToHand) {
					// return true;
				}
			}
		}

		// if (hand.isClicked(event)) {
		// this.bringChildToFront(hand);
		// // return true;
		// }
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			if (turnState == TurnState.START) {
				start = startHand.checkCollision(event);
				return start;
			} else if (turnState == TurnState.DRAW) {
				start = deckV.checkCollision(event);
				return true;
			} else if (turnState == TurnState.PLAY) {
				if (meldViewGroup.checkPlayCollisions(event)) {
					// hand.deal(returnToHand);
					// returnToHand = null;
					returnToHand = true;
					return true;
				}
				if (meldViewGroup.checkPlayMeld(event)) {
					System.out.println("not validation1");
					playCards = true;
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
					// TODO deal hand
					this.removeView(startHand);
					start = false;
					animating = true;
					turnState = TurnState.DRAW;
					// hand.handCreated();
					Handler handler = new Handler();
					GameStart gameStart = new GameStart(this, hand, deck,
							discard);
					handler.postDelayed(gameStart, Constants.DEAL_DELAY);
					return true;
				}
			} else if (turnState == TurnState.DRAW) {
				hand.handCreated();
				if (start) {
					hand.deal(deck.deal());
					turnState = TurnState.PLAY;
				}
			} else if (turnState == TurnState.PLAY) {
				if (returnToHand) {
					hand.deal(meldViewGroup.removeCardFromPlay());
					if (!meldViewGroup.playingCards()) {
						hand.handCreated();
					}
					// hand.deal(returnToHand);
					returnToHand = false;
					return true;
				}
				if (playCards) {
					ArrayList<VCard> tempCards = meldViewGroup
							.getPlayingCards();
					System.out.println("not validation");
					if (MeldFactory.validate(tempCards)) {

						meldViewGroup.removeAllPlayingCards();
						meldViewGroup.addMeld(MeldFactory.buildMeld(tempCards));
						// TODO place cards
					} else {
						// TODO toast cannot play these cards
					}
					playCards = false;
					return true;
				}
			}
		}
		return false;
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

}
