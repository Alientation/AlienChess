package com.alientation.chess.game;

import com.alientation.chess.game.piece.Piece;

public class Pin {
	
	private Piece pinnedPiece;
	private Piece attackingPiece;
	private Direction direction;
	
	public Pin(Piece pinnedPiece, Piece attackingPiece, Direction direction) {
		this.pinnedPiece = pinnedPiece;
		this.attackingPiece = attackingPiece;
		this.direction = direction;
	}
	
	public Piece getPinnedPiece() {
		return this.pinnedPiece;
	}
	
	public Piece getAttackingPiece() {
		return this.attackingPiece;
	};
	
	public Direction getDirection() {
		return this.direction;
	}
}
