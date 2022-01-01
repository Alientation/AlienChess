package com.alientation.chess.game.piece;

import java.util.ArrayList;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.Team;
import com.alientation.chess.game.move.Move;
import com.alientation.chess.game.move.PossibleMoveGenerator;

public class Pawn extends Piece{
	public static final int MAX_DISTANCE = 1;
	public static final int[][] DIRECTIONS = {
			{0,-1},
			{1,-1},
			{-1,-1}
	};
	/*
	 * {canJustMove,canJustTake}
	 */
	public static final boolean[][] CONDITION = {
			{true,false},
			{false,true},
			{false,true}
	};
	public static final int TYPE = 1;
	
	public Pawn(Team team, GridSlot slot, String IMAGE_PATH) {
		super(team, slot, IMAGE_PATH);
	}

	@Override
	public ArrayList<Move> getLegalMoves(Board b, boolean isPseudo) {
		return PossibleMoveGenerator.getLegalPawnMoves(b, this, isPseudo);
	}
	
	@Override
	public boolean canMoveTo(GridSlot slot, boolean isPseudo) {
		return getMoveTo(slot, isPseudo) != null;
	}
	
	@Override
	public boolean canMoveTo(Piece p, boolean isPseudo) {
		return getMoveTo(p.location(), isPseudo) != null;
	}
	
	@Override
	public Move getMoveTo(Piece p, boolean isPseudo) {
		return getMoveTo(p.location(), isPseudo);
	}

	@Override
	public Move getMoveTo(GridSlot slot, boolean isPseudo) {
		if (slot == null) {
			return null;
		}
		//Optimized checks
		if (this.location.distanceTo(slot) > 2) {
			return null;
		}
		for (Move m : this.getLegalMoves(slot.getBoard(), isPseudo)) {
			if (m.getMoveToSlot().x() == slot.x() && m.getMoveToSlot().y() == slot.y()) {
				return m;
			}
		}
		return null;
	}
	
	@Override
	public int getType() {
		return TYPE;
	}
}
