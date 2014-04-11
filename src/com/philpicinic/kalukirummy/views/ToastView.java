package com.philpicinic.kalukirummy.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ToastView extends View{

	private Context context;
	private int screenH;
	
	public ToastView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		screenH = h;
	}
	
	public void showToast(String text, int duration){
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.BOTTOM, 0, (int)(screenH / 6.33));
		toast.show();
	}
}
