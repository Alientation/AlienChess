package com.alientation.chess.game;

public class Clock {
	private int hh, mm, ss;
	
	public Clock(int hh, int mm, int ss) {
		this.hh = hh;
		this.mm = mm;
		this.ss = ss;
	}
	
	public boolean outOfTime() {
		return (this.hh <= 0 && this.mm <= 0 && this.ss <= 0);
	}
	
	public void decrement() {
		int time = this.hh * 3600 + this.mm * 60 + this.ss - 1;
		this.hh = time / 3600;
		time %= 3600;
		this.mm = time / 60;
		time %= 60;
		this.ss = time;
	}
	
	public String getTime() {
		String fHrs = String.format("%02d", this.hh);
		String fMins = String.format("%02d", this.mm);
		String fSecs = String.format("%02d", this.ss);
		String fTime = fHrs + ":" + fMins + ":" + fSecs;
		return fTime;
	}
}
