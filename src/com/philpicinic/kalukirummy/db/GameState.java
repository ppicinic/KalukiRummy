package com.philpicinic.kalukirummy.db;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author Phil Picinic
 * Singleton class that holds the game settings globally for access
 */
public class GameState {

	private static volatile GameState instance;
	private Context context;
	private boolean sound;
	private boolean choice;
	
	/**
	 * Private constructor
	 * @param context any valid context
	 */
	private GameState(Context context){
		this.context = context;
		sound = false;
		choice = false;
	}
	
	/**
	 * Gets the instance of the singleton
	 * @param context any valid context in case the singleton must be instantiated
	 * @return an instance of the GameState
	 */
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
	
	/**
	 * Tells whether sound should be on
	 * @return true if on, false if off
	 */
	public boolean isSound() {
		return sound;
	}

	/**
	 * Tells which deck of cards to use
	 * @return true for choice 2, false for choice 1 ?!? or reverse?! TODO
	 */
	public boolean isChoice() {
		return choice;
	}

	/**
	 * Update the singleton by checking the database
	 */
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
