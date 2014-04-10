package com.philpicinic.kalukirummy.meld;

import com.philpicinic.kalukirummy.card.VCard;

public class JokerUndo {
	
	private VCard jokerCard;
	private VCard replaceCard;
	private Meld meld;
	private boolean playerSide;
	
	public JokerUndo(VCard card){
		this.replaceCard = card;
	}
	
	public void setJokerCard(VCard card){
		this.jokerCard = card;
	}
	public void setReplaceCard(VCard card){
		this.replaceCard = card;
	}
	
	public void setMeld(Meld meld){
		this.meld = meld;
	}
	
	public void setPlayerSide(boolean playerSide){
		this.playerSide = playerSide;
	}
	
	public VCard getJokerCard(){
		return jokerCard;
	}
	
	public VCard getReplaceCard(){
		return replaceCard;
	}
	
	public Meld getMeld(){
		return meld;
	}

	public boolean isPlayerSide() {
		return playerSide;
	}
}
