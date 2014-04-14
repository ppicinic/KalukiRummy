package com.philpicinic.kalukirummy.threads;

import com.philpicinic.kalukirummy.bot.Bot;

/**
 * 
 * @author Phil Picinic
 * The Runnable for starting the bot's turn
 */
public class BotTurn implements Runnable{

	private Bot bot;
	
	public BotTurn(Bot bot){
		this.bot = bot;
	}

	@Override
	public void run() {
		bot.startTurn();
	}
	
	
}
