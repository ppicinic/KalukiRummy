package com.philpicinic.kalukirummy.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.card.Suit;
import com.philpicinic.kalukirummy.card.VCard;
import com.philpicinic.kalukirummy.deck.DeckView;
import com.philpicinic.kalukirummy.deck.DiscardView;
import com.philpicinic.kalukirummy.hand.RightMove;

public class GameView extends ViewGroup {

	private Context context;
	private Bitmap deck;
	private Bitmap discardPile;
	private int screenW;
	private int screenH;
	private ArrayList<VCard> vCards;
	private RightMove rightM;
	private DeckView deckV;
	private DiscardView discard;
	
	public GameView(Context context) {
		super(context);
		this.context = context;

		deck = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		//deck.setWidth(50);
		discardPile = BitmapFactory.decodeResource(getResources(), R.drawable.card102);
		vCards = new ArrayList<VCard>();
		for(int i = 0; i < 13; i++){
			VCard vCard = new VCard(context, i, Suit.SPADES, (i + 2));
			this.addView(vCard);
			
			vCards.add(vCard);
		}
		rightM = new RightMove(context);
		this.addView(rightM);
		deckV = new DeckView(context);
		this.addView(deckV);
		discard = new DiscardView(context);
		this.addView(discard);
		
		//System.out.println("Success");
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		Bitmap temp = deck;
		int scaleW = (int) (screenW/8);
		int scaleH = (int) (scaleW*1.28);
		deck = Bitmap.createScaledBitmap(temp, scaleW, scaleH, false);
		discardPile = Bitmap.createScaledBitmap(discardPile, scaleW, scaleH, false);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawCircle(circleX, circleY, radius, redPaint);
		canvas.drawBitmap(deck, (screenW / 100), (screenH / 200), null);
		canvas.drawBitmap(discardPile, ((screenW / 50) + discardPile.getWidth() ), (screenH / 200), null);
	}

//	public boolean onTouchEvent(MotionEvent event) {
//		int eventaction = event.getAction();
//		int X = (int) event.getX();
//		int Y = (int) event.getY();
//		
//		switch (eventaction) {
//		case MotionEvent.ACTION_DOWN:
//			break;
//		case MotionEvent.ACTION_MOVE:
//			circleX = X;
//			circleY = Y;
//			break;
//		case MotionEvent.ACTION_UP:
//			circleX = X;
//			circleY = Y;
//			break;
//		}
//		invalidate();
//		return true;
//	}
	
	public boolean onInterceptTouchEvent(MotionEvent event){
		//this.removeView(vCards.get(0));
		return false;
	}
//	public boolean onTouchEvent(MotionEvent event){
//		vCard.invalidate();
//		System.out.println("ViewGroup");
//		return false;
//	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.getChildCount(); i++){
			this.getChildAt(i).layout(arg1, arg2, arg3, arg4);
		}
	}
	
	

}
