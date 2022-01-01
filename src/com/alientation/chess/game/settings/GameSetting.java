package com.alientation.chess.game.settings;

public class GameSetting extends Setting{
	public String whiteName;
	public String blackName;
	
	public boolean isWhiteAI;
	public AISetting whiteAISetting;
	public String whiteAI;
	
	public boolean isBlackAI;
	public AISetting blackAISetting;
	public String blackAI;
	
	public int hh, mm, ss;
	
	public BoardSetting boardSetting;
	
	public GameSetting() {
		super();
	}
}
