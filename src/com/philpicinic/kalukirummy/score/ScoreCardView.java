package com.philpicinic.kalukirummy.score;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.philpicinic.kalukirummy.R;

/**
 * 
 * @author Phil Picinic
 *
 * View class of the Score Card
 */
public class ScoreCardView extends View{

	@SuppressWarnings("unused")
	private Context context;
	private Bitmap scorecard;
	
	private int screenW;
	@SuppressWarnings("unused")
	private int screenH;
	private boolean touched;
	private int x;
	private int y;
	private String botName;
	private String playerName;
	private ArrayList<Integer> playerGames;
	private ArrayList<Integer> botGames;
	
	/**
	 * Constructor creates the image of the score card
	 * @param context
	 */
	public ScoreCardView(Context context) {
		super(context);
		
		this.context = context;
		scorecard = BitmapFactory.decodeResource(getResources(), R.drawable.scorecard);
		
		botName = "Mike (CPU)";
		playerName = "Player (You)";
		playerGames = new ArrayList<Integer>();
		botGames = new ArrayList<Integer>();
	}
	
	/**
	 * updates sizes if the display is changed
	 * resizes and places score card
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
		
		// Scale and Place score card image
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		scorecard = Bitmap.createScaledBitmap(scorecard, scaleW, scaleH, false);
		x = screenW - (scorecard.getWidth() * 7 / 6);
		y = (scorecard.getHeight()  / 6);
	}

	/**
	 * Draw image onto the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(scorecard, x, y, null);
	}

	public boolean onTouchEvent(MotionEvent event){
		int e = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch(e){
		case MotionEvent.ACTION_DOWN:
			if(X > x && X < (x + scorecard.getWidth()) && Y > y && Y <(y + scorecard.getHeight()) ){
				touched = true;
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if(touched){
				// TODO pop up game screen
				showScoreDialog();
				return true;
			}
			break;
		}
		return false;
	}
	
	private void showScoreDialog(){
		final Dialog chooseSuitDialog = new Dialog(context);
		chooseSuitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		chooseSuitDialog.setContentView(R.layout.scorecard_layout);
		String games = "";
		String playerScores = "";
		String botScores = "";
		for(int i = 0; i < playerGames.size(); i++){
			games += i + "\n";
			playerScores += playerGames.get(i) + "\n";
			botScores += botGames.get(i) + "\n";
		}
		TextView textView1 = (TextView) chooseSuitDialog.findViewById(R.id.textView4);
		textView1.setText(games);
		
		TextView textView2 = (TextView) chooseSuitDialog.findViewById(R.id.textView5);
		textView2.setText(playerScores);
		
		TextView textView3 = (TextView) chooseSuitDialog.findViewById(R.id.textView6);
		textView3.setText(botScores);
//		final Spinner rankSpinner = (Spinner) chooseSuitDialog
//				.findViewById(R.id.rankSpinner);
//		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
//				context, R.array.ranks, android.R.layout.simple_spinner_item);
//		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		rankSpinner.setAdapter(adapter2);
//		final Spinner suitSpinner = (Spinner) chooseSuitDialog
//				.findViewById(R.id.suitSpinner);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//				context, R.array.suits, android.R.layout.simple_spinner_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		suitSpinner.setAdapter(adapter);

//		Button okButton = (Button) chooseSuitDialog.findViewById(R.id.okButton);
//		okButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View view) {
//	});
	chooseSuitDialog.show();
	}
}
