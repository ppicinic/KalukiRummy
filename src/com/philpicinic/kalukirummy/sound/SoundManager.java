package com.philpicinic.kalukirummy.sound;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.philpicinic.kalukirummy.R;
import com.philpicinic.kalukirummy.db.GameState;

public class SoundManager {

	private Context context;
	private SoundPool sounds;
	private HashMap<String, Integer> soundMap;

	public SoundManager(Context context) {
		this.context = context;

		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundMap = new HashMap<String, Integer>();

		int drawSound = sounds.load(context, R.raw.draw, 1);
		soundMap.put("draw", drawSound);

		int playSound = sounds.load(context, R.raw.play, 1);
		soundMap.put("play", playSound);

		int errorSound = sounds.load(context, R.raw.error, 1);
		soundMap.put("error", errorSound);

		int turnSound = sounds.load(context, R.raw.turn, 1);
		soundMap.put("turn", turnSound);

		int winSound = sounds.load(context, R.raw.win, 1);
		soundMap.put("win", winSound);

		int loseSound = sounds.load(context, R.raw.lose, 1);
		soundMap.put("lose", loseSound);

	}

	public void playSound(String soundName) {
		if (GameState.getInstance(context).isSound()) {
			AudioManager audioM = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			float vol = (float) audioM
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			sounds.play(soundMap.get(soundName).intValue(), vol, vol, 1, 0, 1);
		}
	}
}
