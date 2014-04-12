package com.philpicinic.kalukirummy.db;

import android.content.Context;
import android.database.Cursor;

public class GameState {

	private static volatile GameState instance;
	private Context context;
	private boolean sound;
	private boolean choice;
	
	private GameState(Context context){
		this.context = context;
	}
	
	public static GameState getInstance(Context context){
		if(instance == null){
			synchronized(GameState.class){
				if(instance == null){
					instance = new GameState(context);
				}
			}
		}
		return instance;
	}
	
	public boolean isSound() {
		return sound;
	}

	public boolean isChoice() {
		return choice;
	}

	public void update(){
		DatabaseAdapter db = new DatabaseAdapter(context);
		db.open();
		Cursor cursor = db.getRecord(1);
		if(cursor.moveToFirst()){
			int soundFlag = cursor.getInt(1);
			int choiceFlag = cursor.getInt(2);
			sound = (soundFlag == 1)? true : false;
			choice = (choiceFlag == 1)? true : false;
		}
		db.close();
	}
}
