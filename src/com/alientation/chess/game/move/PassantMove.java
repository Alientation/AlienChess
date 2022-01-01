package com.alientation.chess.game.move;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.Piece;

public class PassantMove extends CaptureMove{
	
	public PassantMove(Piece pieceToMove, GridSlot moveToSlot, Piece capturedPiece) {
		super(pieceToMove, moveToSlot);
		this.capturedPiece = capturedPiece;
	}
	
	public void makeMove(Board b) {
		
	}
	
	public void unMakeMove(Board b) {
		
	}
}
