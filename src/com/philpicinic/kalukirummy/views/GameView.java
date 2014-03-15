package com.philpicinic.kalukirummy.views;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Suit;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.DeckView;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.CardMove;
import com.philpicininc.kalukirummy.bot.BotView;
import com.philpicininc.kalukirummy.score.ScoreCardView;

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

	private ArrayList<VCard> vCards;

	private CardMove rightArrow;
	private CardMove leftArrow;

	private DeckView deckV;
	private DiscardView discard;

	private BotView bot;
	private ScoreCardView scoreCard;

	/**
	 * Create the GameView Group Creates all child elements of the layout
	 * 
	 * @param context
	 *            the context of the activity
	 */
	public GameView(Context context) {
		super(context);
		this.context = context;

		// Creates Background
		BackgroundView bg = new BackgroundView(context);
		this.addView(bg);

		// Creates right arrow
		rightArrow = new CardMove(context, true);
		this.addView(rightArrow);

		// Creates left arrow
		leftArrow = new CardMove(context, false);
		this.addView(leftArrow);

		// Creates the Deck and Discard Pile
		deckV = new DeckView(context);
		this.addView(deckV);
		discard = new DiscardView(context);
		this.addView(discard);

		// Create the bot opponent
		bot = new BotView(context);
		this.addView(bot);

		// Creates the scorecard button
		scoreCard = new ScoreCardView(context);
		this.addView(scoreCard);

		// Creates 13 cards in the players hand
		vCards = new ArrayList<VCard>();
		VCard vCard = new VCard(context, 0, Suit.DIAMONDS, 1);
		this.addView(vCard);
		vCards.add(vCard);
		vCard = new VCard(context, 1, Suit.CLUBS, 1);
		this.addView(vCard);
		vCards.add(vCard);
		
		for (int i = 0; i < 11; i++) {
			vCard = new VCard(context, (i + 2), Suit.SPADES, (i + 2));
			this.addView(vCard);

			vCards.add(vCard);
		}
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		int e = event.getAction();
		if (e == MotionEvent.ACTION_DOWN) {
			for (VCard card : vCards) {
				if(card.detectCollision(event)){
					this.bringChildToFront(card);
					
					break;
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
	}

}
