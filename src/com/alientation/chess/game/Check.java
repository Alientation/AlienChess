package com.alientation.chess.game;

import com.alientation.chess.game.piece.Piece;

public class Check {
	private Piece checkedPiece;
	private Piece attackingPiece;
	private Direction direction;
	
	public Check(Piece checkedPiece, Piece attackingPiece, Direction direction) {
		this.checkedPiece = checkedPiece;
		this.attackingPiece = attackingPiece;
		this.direction = direction;
	}
	
	public Piece getCheckedPiece() {
		return this.checkedPiece;
	}
	
	public Piece getAttackingPiece() {
		return this.attackingPiece;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}
