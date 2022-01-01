package com.alientation.chess.game.move;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.Piece;

public class CaptureMove extends Move{
	protected Piece capturedPiece;
	
	public CaptureMove(Piece pieceToMove, GridSlot moveToSlot) {
		super(pieceToMove, moveToSlot);
		this.capturedPiece = moveToSlot.occupyingPiece();
	}
	
	public void makeMove(Board b) {
		this.pieceToMove.moveTo(this.moveToSlot);
	}
	
	public void unMakeMove(Board b) {
		this.pieceToMove.moveTo(this.moveFromSlot);
		this.capturedPiece.team().add(this.capturedPiece);
		this.capturedPiece.moveTo(this.capturedPiece.location());
	}
	
	public Piece getCapturedPiece() {
		return this.capturedPiece;
	}
	
	public void setCapturedPiece(Piece p) {
		this.capturedPiece = p;
	}
}
