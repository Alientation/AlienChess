package com.alientation.chess.game.move;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.Piece;

public class PawnPromotionMove extends Move{
	protected Piece promoteToPiece;
	
	public PawnPromotionMove(Piece pieceToMove, GridSlot moveToSlot, Piece promoteToPiece) {
		super(pieceToMove, moveToSlot);
		this.promoteToPiece = promoteToPiece;
	}
	
	public void makeMove(Board b) {
		
	}
	
	public void unMakeMove(Board b) {
		
	}
	
	public Piece getPromoteToPiece() { 
		return this.promoteToPiece;
	}
	
	public void setPromoteToPiece(Piece p) {
		this.promoteToPiece = p;
	}
}
