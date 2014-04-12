package com.philpicinic.kalukirummy.score;

import java.util.ArrayList;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.philpicinic.kalukirummy.R;

/**
 * 
 * @author Phil Picinic
 * 
 *         View class of the Score Card
 */
public class ScoreCardView extends View {

	private Context context;
	private Bitmap scorecard;

	private int screenW;
	@SuppressWarnings("unused")
	private int screenH;
	private boolean touched;
	private int x;
	private int y;
	private String botName;
	private ArrayList<Integer> playerGames;
	private ArrayList<Integer> botGames;

	/**
	 * Constructor creates the image of the score card
	 * 
	 * @param context
	 */
	public ScoreCardView(Context context) {
		super(context);

		this.context = context;
		scorecard = BitmapFactory.decodeResource(getResources(),
				R.drawable.scorecard);
		String[] names = getResources().getStringArray(R.array.bot_names);
		Random random = new Random(System.currentTimeMillis());
		int spot = random.nextInt(names.length);
		botName = names[spot];
		playerGames = new ArrayList<Integer>();
		botGames = new ArrayList<Integer>();
	}

	/**
	 * updates sizes if the display is changed resizes and places score card
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

		// Scale and Place score card image
		int scaleW = (int) (screenW / 7);
		int scaleH = (int) (scaleW * 1.28);
		scorecard = Bitmap.createScaledBitmap(scorecard, scaleW, scaleH, false);
		x = screenW - (scorecard.getWidth() * 7 / 6);
		y = (scorecard.getHeight() / 6);
	}

	/**
	 * Draw image onto the screen
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(scorecard, x, y, null);
	}

	public void addGame(int player, int bot) {
		if (playerGames.size() == 0) {
			playerGames.add(player);
			botGames.add(bot);
		} else {
			playerGames.add(player
					+ playerGames.get(playerGames.size() - 1).intValue());
			botGames.add(bot + botGames.get(botGames.size() - 1).intValue());
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		int e = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch (e) {
		case MotionEvent.ACTION_DOWN:
			if (X > x && X < (x + scorecard.getWidth()) && Y > y
					&& Y < (y + scorecard.getHeight())) {
				touched = true;
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (touched) {
				// TODO pop up game screen
				showScoreDialog();
				return true;
			}
			break;
		}
		return false;
	}

	private void showScoreDialog() {
		final Dialog scoreCardDialog = new Dialog(context);
		scoreCardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		scoreCardDialog.setContentView(R.layout.scorecard_layout);
		StringBuilder g = new StringBuilder();
		StringBuilder p = new StringBuilder();
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < playerGames.size(); i++) {
			g.append("" + (i + 1) + "\n");
			p.append("" + playerGames.get(i).intValue() + "\n");
			b.append(botGames.get(i).intValue() + "\n");
		}
		if (playerGames.size() > 0) {
			g.deleteCharAt(g.length() - 1);
			p.deleteCharAt(p.length() - 1);
			b.deleteCharAt(b.length() - 1);
		}

		TextView botNameView = (TextView) scoreCardDialog
				.findViewById(R.id.textView3);
		botNameView.setText(botName);

		TextView textView1 = (TextView) scoreCardDialog
				.findViewById(R.id.textView4);
		textView1.setText(g.toString());

		TextView textView2 = (TextView) scoreCardDialog
				.findViewById(R.id.textView5);
		textView2.setText(p.toString());

		TextView textView3 = (TextView) scoreCardDialog
				.findViewById(R.id.textView6);
		textView3.setText(b.toString());

		Button okButton = (Button) scoreCardDialog.findViewById(R.id.okButton2);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				scoreCardDialog.dismiss();
			}
		});
		scoreCardDialog.show();
	}

	public int getPlayerScore() {
		if (playerGames.size() > 0) {
			return playerGames.get(playerGames.size() - 1);
		}
		return 0;
	}

	public int getBotScore() {
		if (botGames.size() > 0) {
			return botGames.get(botGames.size() - 1);
		}
		return 0;
	}

	public void endGame() {
		playerGames = new ArrayList<Integer>();
		botGames = new ArrayList<Integer>();
	}
}
