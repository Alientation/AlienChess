package com.alientation.chess.game.piece;

public enum PieceType {
	PAWN(1),KNIGHT(2),BISHOP(3),ROOK(4),QUEEN(5),KING(6);
	
	private int type;
	
	private PieceType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
