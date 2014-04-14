package com.philpicinic.kalukirummy.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author Phil Picinic
 * The view to print toasts
 */
public class ToastView extends View{

	private Context context;
	private int screenH;
	
	/**
	 * Constructor
	 * @param context the context of the activityy
	 */
	public ToastView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		screenH = h;
	}
	
	/**
	 * Show a toast on the screen
	 * @param text the text to show
	 * @param duration the duration of the toast
	 */
	public void showToast(String text, int duration){
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.BOTTOM, 0, (int)(screenH / 6.33));
		toast.show();
	}
}
