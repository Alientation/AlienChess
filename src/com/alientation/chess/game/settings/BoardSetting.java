package com.alientation.chess.game.settings;

import java.awt.Color;

public class BoardSetting extends Setting{
	
	public Color even;
	public Color odd;
	
	public BoardSetting() {
		super();
		even = new Color(125, 158, 81);
		odd = new Color(227, 230, 186);
		
		
	}
}
