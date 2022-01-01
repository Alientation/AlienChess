package com.alientation.chess.game.move;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.Piece;

public class CastleMove extends Move{
	
	protected boolean isKingSide;
	
	public CastleMove(Piece pieceToMove, GridSlot moveToSlot, boolean isKingSide) {
		super(pieceToMove, moveToSlot);
		this.isKingSide = isKingSide;
	}
	
	public void makeMove(Board b) {
		
	}
	
	public void unMakeMove(Board b) {
		
	}
	
	public boolean getIsKingSide() {
		return this.isKingSide;
	}
	
	public void setIsKingSide(boolean isKingSide) {
		this.isKingSide = isKingSide;
	}
}
