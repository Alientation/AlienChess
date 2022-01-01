package com.alientation.chess.game.settings;

import com.alientation.chess.game.ai.BaseAI;
import com.alientation.chess.game.ai.BaseEvaluate;

public class AISetting extends Setting{
	
	public BaseAI AI;
	public BaseEvaluate Evaluate;
	
	public int MaxDepth;
	
	
	public AISetting() {
		super();
	}
}
