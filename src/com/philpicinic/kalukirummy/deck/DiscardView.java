package com.philpicinic.kalukirummy.deck;

import android.content.Context;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.card.Card;
import com.philpicinic.kalukirummy.card.VCard;

/**
 * 
 * @author Phil Picinic
 *
 * View Class of the Discard Pile
 * Shows the top card of the discard Pile
 */
public class DiscardView extends ViewGroup{
	
	@SuppressWarnings("unused")
	private Context context;
	//private Bitmap discardCard;
	private DiscardPile pile;
	@SuppressWarnings("unused")
	private int screenH;
	private int screenW;
	private int x;
	private int y;
	
	private int l;
	private int w;
	private int ol;
	private int ow;
	
	private VCard card;

	public DiscardView(Context context) {
		super(context);
		this.context = context;

		pile = new DiscardPile();
	}
	
	public void toss(Card mCard){
		pile.push(mCard);
		VCard temp = new VCard(this.context, 0, mCard);
		temp.layout(l, w, ol, ow);
		temp.setTossPos();
		card = temp;
		this.addView(card);
		
	}
	
	public void toss(VCard vCard){
		pile.push(vCard.getCard());
		this.removeView(card);
		card = vCard;
		card.setTossPos();
		this.addView(card);
		card.layout(l, w, ol, ow);
		
	}
	
	public boolean checkCollision(VCard vCard){
		return vCard.collideWithCard(this.card);
	}

	/**
	 * updates sizes if the display is changed
	 * @param w width of the screen
	 * @param h height of the screen
	 * @param oldw old width of the screen
	 * @param oldh old height of the screen
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		this.l = l;
		this.w = t;
		this.ol = r;
		this.ow = b;
	}
}
