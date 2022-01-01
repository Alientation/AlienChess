package com.alientation.chess.game.move;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.Piece;

public class Move {
	
	protected Piece pieceToMove;
	protected GridSlot moveFromSlot;
	protected GridSlot moveToSlot;
	
	public Move(Piece pieceToMove, GridSlot moveToSlot) {
		this.pieceToMove = pieceToMove;
		this.moveFromSlot = pieceToMove.location();
		this.moveToSlot = moveToSlot;
		
	}
	
	public void makeMove(Board b) {
		this.pieceToMove.moveTo(this.moveToSlot);
	}
	
	public void unMakeMove(Board b) {
		this.pieceToMove.moveTo(this.moveFromSlot);
	}
	
	public boolean isMoveLegal() {
		return this.pieceToMove.getMoveTo(this.moveToSlot,false) != null;
	}
	
	public Piece getPiece() {
		return this.pieceToMove;
	}
	
	public GridSlot getMoveFromSlot() {
		return this.moveFromSlot;
	}
	
	public GridSlot getMoveToSlot() {
		return this.moveToSlot;
	}
}
