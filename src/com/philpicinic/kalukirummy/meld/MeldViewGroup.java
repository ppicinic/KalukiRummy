package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.sound.SoundManager;
import com.philpicinic.kalukirummy.views.ToastView;

/**
 * 
 * @author Phil Picinic
 * ViewGroup for both meld piles
 */
public class MeldViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceViewGroup meldPlaceViewGroup;
	private MeldPlayerViewGroup meldPlayerViewGroup;
	private MeldBotViewGroup meldBotViewGroup;
	private MeldPlayerViewGroup attach;
	private ToastView toastView;
	private SoundManager sounds;

	/**
	 * Constructor DO NOT USE THIS CONSTRUCTOR IT IS HERE FOR DEFAULT REQUIREMENT
	 * @param context
	 */
	public MeldViewGroup(Context context){
		super(context);
	}
	
	/**
	 * Constructor
	 * @param context the context of the activity
	 * @param toastView the toast view to gain access to.
	 */
	public MeldViewGroup(Context context, ToastView toastView, SoundManager sounds) {
		super(context);

		this.context = context;

		meldPlaceViewGroup = new MeldPlaceViewGroup(this.context);
		this.addView(meldPlaceViewGroup);
		meldPlayerViewGroup = new MeldPlayerViewGroup(this.context);
		this.addView(meldPlayerViewGroup);
		meldBotViewGroup = new MeldBotViewGroup(this.context);
		this.addView(meldBotViewGroup);
		this.toastView = toastView;
		this.sounds = sounds;
		// TODO Auto-generated constructor stub
	}

	/**
	 * initiate the player moving a card
	 */
	public void initiateMovingCard() {
		meldPlaceViewGroup.initiateMovingCard();
	}

	/**
	 * deinitiate the player moving a card
	 */
	public void deinitiateMovingCard() {
		meldPlaceViewGroup.deinitiateMovingCard();
	}

	/**
	 * Check if the player is playing cards
	 * @return true if playing, otherwise false
	 */
	public boolean playingCards() {
		return meldPlaceViewGroup.playingCards();
	}

	/**
	 * Check collision into the place area
	 * @param card the card to check
	 * @return true if collides, otherwise false
	 */
	public boolean checkCollisionByCard(VCard card) {
		return meldPlaceViewGroup.checkCollisionByCard(card);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		meldPlaceViewGroup.layout(arg1, arg2, arg3, arg4);
		meldPlayerViewGroup.layout(arg1, arg2, arg3, arg4);
		meldBotViewGroup.layout(arg1, arg2, arg3, arg4);
	}

	/**
	 * Add a meld
	 * @param meld the meld to add
	 */
	public void addMeld(Meld meld) {
		meldPlayerViewGroup.addMeld(meld);
	}

	/**
	 * add card to the meld place area
	 * @param movingCard the card to place
	 */
	public void placeCard(VCard movingCard) {
		meldPlaceViewGroup.placeCard(movingCard);

	}

	/**
	 * Check collision to the meld place area
	 * @param event the player's input
	 * @return true if collides, otherwise false
	 */
	public boolean checkPlayCollisions(MotionEvent event) {
		return meldPlaceViewGroup.checkPlayCollisions(event);
	}

	/**
	 * Get all the playing cards
	 * @return the playing cards
	 */
	public ArrayList<VCard> getPlayingCards() {
		return meldPlaceViewGroup.getCards();
	}

	/**
	 * Remove all the playing cards
	 */
	public void removeAllPlayingCards() {
		meldPlaceViewGroup.removeAllCards();
	}

	/**
	 * Remove a card from the place area
	 * @return the card removed
	 */
	public VCard removeCardFromPlay() {
		return meldPlaceViewGroup.removeCard();
	}

	/**
	 * Check if play is pressed
	 * @param event the player's input
	 * @return true if pressed, otherwise false
	 */
	public boolean checkPlayMeld(MotionEvent event) {
		return meldPlaceViewGroup.checkPlay(event);
	}

	/**
	 * Can the player toss a card
	 * @return true if player can toss, otherwise false
	 */
	public boolean playerCanToss() {
		return meldPlayerViewGroup.playerCanToss();
	}

	/**
	 * the meld value of the player's side
	 * @return the total value
	 */
	public int playerMeldValue() {
		return meldPlayerViewGroup.meldValue();
	}

	/**
	 * Initiate hand started
	 */
	public void initiateHand() {
		meldPlaceViewGroup.initiateHand();
	}

	/**
	 * Check if undo is pressed
	 * @param event the player's input
	 * @return true if pressed, otherwise false
	 */
	public boolean checkUndo(MotionEvent event) {
		return meldPlaceViewGroup.checkUndo(event);
	}

	/**
	 * undo all the cards
	 */
	public void undoCards() {
		meldPlayerViewGroup.undoCards();
	}

	/**
	 * Take care of end turn states
	 */
	public void endTurn() {
		meldPlayerViewGroup.endTurn();
		meldBotViewGroup.endTurn();
	}
	
	/**
	 * Check if the player has an initial build
	 * @return true if the player has an initial build
	 */
	public boolean hasInitialBuild() {
		return meldPlayerViewGroup.initialBuild();
	}

	/**
	 * Sort the playing cards
	 */
	public void sortPlayingCards() {
		meldPlaceViewGroup.sortPlayingCards();
	}

	/**
	 * Check collision for attachment
	 * @param card the card to check
	 * @return true if it collides, else false
	 */
	public boolean checkAttachCollision(VCard card) {
		boolean result = meldPlayerViewGroup.checkMeldCollision(card);
		if (result) {
			attach = meldPlayerViewGroup;
		} else {
			result = meldBotViewGroup.checkMeldCollision(card);
			if (result) {
				attach = meldBotViewGroup;
			}
		}
		if (result && meldPlayerViewGroup.meldValue() < 40) {
			toastView.showToast(getResources().getString(R.string.cannot_attach), Toast.LENGTH_SHORT);
			sounds.playSound("error");
			attach = null;
			return false;
		}
		return result;
	}

	/**
	 * Can a card be attached to a meld
	 * @param card the card to attach
	 * @return true if the card can be attached, otherwise false
	 */
	public boolean canAttach(VCard card) {
		if (attach != null) {
			return attach.canAttach(card);
		}
		return false;
	}

	/**
	 * Attach a card
	 * @param card the card to attach
	 */
	public void attachToPlayer(VCard card) {
		attach.attach(card);
	}

	/**
	 * Remove attached cards
	 */
	public void removeAttachedPlayerCards() {
		meldPlayerViewGroup.removeAttachedCards();
		meldBotViewGroup.removeAttachedCards();
	}

	/**
	 * Check if a card can replace a joker
	 * @param card the card to replace
	 * @return true if it can replace, otherwise false
	 */
	public boolean canReplacePlayerJoker(VCard card) {
		if (attach != null) {
			boolean result = attach.canReplaceJoker(card);
			if (!result) {
				// attach = null;
			}
			return result;
		}
		return false;
	}

	/**
	 * Replace the joker card
	 * @param card the card to replace
	 * @param undo the undo object
	 * @return the joker card
	 */
	public VCard replacePlayerJoker(VCard card, JokerUndo undo) {
		return attach.replaceJoker(card, undo);
	}

	/**
	 * End attach
	 */
	public void endPlayerAttach() {
		attach.endAttach();
	}

	/**
	 * Undo replacing the joker
	 * @param undo the undo object
	 */
	public void undoJokerReplace(JokerUndo undo) {
		if (undo.isPlayerSide()) {
			meldPlayerViewGroup.undoReplaceJoker(undo);
		} else {
			meldBotViewGroup.undoReplaceJoker(undo);
		}
	}

	/**
	 * Get the bot's view
	 * @return the Bot ViewGroup
	 */
	public MeldBotViewGroup getBotView() {
		return meldBotViewGroup;
	}

	/**
	 * Get the player's viewgroup
	 * @return the Player ViewGroup
	 */
	public MeldPlayerViewGroup getPlayerView() {
		return meldPlayerViewGroup;
	}

	/**
	 * Get all the cards and remove them at end game
	 * @return all the cards
	 */
	public ArrayList<Card> endGame() {
		ArrayList<Card> temp = meldPlayerViewGroup.endGame();
		ArrayList<Card> temp2 = meldBotViewGroup.endGame();
		for (Card card : temp2) {
			temp.add(card);
		}
		meldPlaceViewGroup.endGame();
		return temp;
	}
}
