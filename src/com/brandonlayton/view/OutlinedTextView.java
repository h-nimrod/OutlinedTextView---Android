package com.brandonlayton.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class OutlinedTextView extends TextView {
private String strokeColor = "#000";
	private int strokeWidth = 3;
	private TextPaint paint = null;
	private boolean strokeEnabled = true;
	
	public OutlinedTextView(Context context){
		super(context);
	}
	
	public OutlinedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(attrs == null) return;
		
		String packageName = "http://schemas.android.com/apk/res-auto"; 
		strokeWidth = attrs.getAttributeIntValue(packageName, "stroke_width", 1);
		strokeColor = attrs.getAttributeValue(packageName, "stroke_color");
		if(strokeColor == null){
			strokeColor = "#222222";
		}
	}
	
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public String getStrokeColor(){
		return this.strokeColor;
	}
	
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public int getStrokeWidth(){
		return this.strokeWidth;
	}
	
	public void setStrokeEnabled(boolean strokeEnabled) {
		this.strokeEnabled = strokeEnabled;
	}
	public boolean getStrokeEnabled() {
		return this.strokeEnabled;
	}
 
	@Override
	protected void onDraw(Canvas canvas){
		if(!this.strokeEnabled){
			super.onDraw(canvas);
			return;
		}
		if(paint == null) paint = new TextPaint();
		
		TextPaint otherPaint = getPaint();
		paint.setTextSize(otherPaint.getTextSize());
		paint.setTypeface(otherPaint.getTypeface());
		paint.setFlags(otherPaint.getFlags());
		paint.setAlpha(otherPaint.getAlpha());
		
		paint.setStyle(Paint.Style.STROKE);
		try{
		paint.setColor(Color.parseColor(strokeColor));
		} catch (Exception e) { paint.setColor(Color.BLACK); }
		paint.setStrokeWidth(strokeWidth);
		
		String text = getText().toString();
		int gravity = this.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
		float x = 0;
		
		if(gravity == Gravity.RIGHT){
			x = getWidth() - paint.measureText(text);
		}
		else if (gravity == Gravity.LEFT || gravity == Gravity.NO_GRAVITY){
			x = (float)getPaddingLeft();
		}
		else if (gravity == (Gravity.CENTER & Gravity.HORIZONTAL_GRAVITY_MASK)) {
			x = (getWidth() - paint.measureText(text))/2;
		}
		canvas.drawText(text, x, getBaseline(), paint);
		super.onDraw(canvas);
	}
	
	
}
