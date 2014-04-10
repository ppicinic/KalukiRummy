package com.philpicinic.kalukirummy.meld;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import com.philpicinic.kalukirummy.card.VCard;

public class MeldViewGroup extends ViewGroup {

	private Context context;
	private MeldPlaceViewGroup meldPlaceViewGroup;
	private MeldPlayerViewGroup meldPlayerViewGroup;
	private MeldBotViewGroup meldBotViewGroup;
	
	public MeldViewGroup(Context context) {
		super(context);

		this.context = context;

		meldPlaceViewGroup = new MeldPlaceViewGroup(this.context);
		this.addView(meldPlaceViewGroup);
		meldPlayerViewGroup = new MeldPlayerViewGroup(this.context);
		this.addView(meldPlayerViewGroup);
		meldBotViewGroup = new MeldBotViewGroup(this.context);
		this.addView(meldBotViewGroup);
		// TODO Auto-generated constructor stub
	}

	public void initiateMovingCard() {
		meldPlaceViewGroup.initiateMovingCard();
	}

	public void deinitiateMovingCard() {
		meldPlaceViewGroup.deinitiateMovingCard();
	}

	public boolean playingCards() {
		return meldPlaceViewGroup.playingCards();
	}

	public boolean checkCollisionByCard(VCard card) {
		System.out.println("yo2");
		return meldPlaceViewGroup.checkCollisionByCard(card);
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		meldPlaceViewGroup.layout(arg1, arg2, arg3, arg4);
		meldPlayerViewGroup.layout(arg1, arg2, arg3, arg4);
		meldBotViewGroup.layout(arg1, arg2, arg3, arg4);
	}

	public void addMeld(Meld meld) {
		meldPlayerViewGroup.addMeld(meld);
	}

	public void placeCard(VCard movingCard) {
		meldPlaceViewGroup.placeCard(movingCard);

	}

	public boolean checkPlayCollisions(MotionEvent event) {
		// TODO Auto-generated method stub
		return meldPlaceViewGroup.checkPlayCollisions(event);
	}

	public ArrayList<VCard> getPlayingCards() {
		return meldPlaceViewGroup.getCards();
	}

	public void removeAllPlayingCards() {
		meldPlaceViewGroup.removeAllCards();
	}

	public VCard removeCardFromPlay() {
		return meldPlaceViewGroup.removeCard();
	}

	public boolean checkPlayMeld(MotionEvent event) {
		return meldPlaceViewGroup.checkPlay(event);
	}

	public boolean playerCanToss() {
		return meldPlayerViewGroup.playerCanToss();
	}

	public int playerMeldValue() {
		return meldPlayerViewGroup.meldValue();
	}

	public void initiateHand() {
		meldPlaceViewGroup.initiateHand();
	}

	public boolean checkUndo(MotionEvent event) {
		return meldPlaceViewGroup.checkUndo(event);
	}

	public void undoCards() {
		meldPlayerViewGroup.undoCards();
	}

	public void endTurn() {
		meldPlayerViewGroup.endTurn();
	}

	public boolean hasInitialBuild() {
		return meldPlayerViewGroup.initialBuild();
	}

	public void sortPlayingCards() {
		meldPlaceViewGroup.sortPlayingCards();
	}

	public boolean checkAttachCollision(VCard card) {
		boolean result = meldPlayerViewGroup.checkMeldCollision(card);
		if (result && meldPlayerViewGroup.meldValue() < 40) {
			CharSequence text = "You need 40 points to attach a card!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			return false;
		}
		return result;
	}

	public boolean canAttach(VCard card) {
		return meldPlayerViewGroup.canAttach(card);
	}

	public void attachToPlayer(VCard card) {
		meldPlayerViewGroup.attach(card);
	}

	public void removeAttachedPlayerCards() {
		meldPlayerViewGroup.removeAttachedCards();
	}

	public boolean canReplacePlayerJoker(VCard card) {
		return meldPlayerViewGroup.canReplaceJoker(card);
	}

	public VCard replacePlayerJoker(VCard card, JokerUndo undo) {
		return meldPlayerViewGroup.replaceJoker(card, undo);
	}

	public void endPlayerAttach() {
		meldPlayerViewGroup.endAttach();
	}

	public void undoJokerReplace(JokerUndo undo) {
		meldPlayerViewGroup.undoReplaceJoker(undo);
	}

	public MeldBotViewGroup getBotView() {
		return meldBotViewGroup;
	}

	public MeldPlayerViewGroup getPlayerView() {
		// TODO Auto-generated method stub
		return meldPlayerViewGroup;
	}
}
