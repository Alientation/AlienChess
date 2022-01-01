package com.alientation.chess.game.move;

import java.util.ArrayList;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.piece.*;

public class PossibleMoveGenerator {
	/*
	 * {x shift,y shift}
	 * 
	 * positive x -> right
	 * positive y -> down
	 */
	
	public static boolean isBlackCheckMated(Board b) {
		return false;
	}
	
	public static boolean isWhiteCheckMated(Board b) {
		return false;
	}
	
	
	public static ArrayList<Move> getFilteredLegalMoves(ArrayList<Move> moves, Board b, Piece p) {
		if (p.team().getPinsCantMove().contains(p)) {
			moves.clear();
			return moves;
		}
		for (int i = 0; i < moves.size(); i++) {
			
		}
		return moves;
	}
	
	
	public static ArrayList<Move> getMoves(Board b, Piece p, int directionX, int directionY, int maxDistance, boolean canJustMove, boolean canJustTake, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if (!isPseudo) {
			if (p.team().getChecks().size() >= 2 && p.getType() != 6) {
				return moves;
			}
		}
		
		int x = p.location().x() + directionX;
		int y = p.location().y() + directionY;
		int distance = 0;
		
		while (b.getSlotAt(x, y) != null && distance < maxDistance) {
			GridSlot gs = b.getSlotAt(x, y);
			if (gs.isOccupied()) {
				if ((gs.occupyingPiece().isDifferentTeam(p) && canJustTake) || (isPseudo && canJustTake)) {
					if (!isPseudo) {
						if (p.getType() == 6 && p.team().getGuarded().contains(gs.occupyingPiece())) {
							break;
						}
					}
					moves.add(new CaptureMove(p,gs));
					break;
				} else {
					break;
				}
			}
			if (canJustMove) {
				moves.add(new Move(p, gs));
			}
			distance++;
			x += directionX;
			y += directionY;
		}
		if (isPseudo) {
			return moves;
		}
		return getFilteredLegalMoves(moves, b, p);
	}
	
	public static ArrayList<Move> getLegalPawnMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		/*
		 * Diagonal up left, up right
		 * Vertical up
		 */
		int doubleMove = 1;
		if (p.team().getTeam().equals("WHITE")) {
			moves.addAll(getMoves(b,p,1,-1,1,false,true,isPseudo));
			moves.addAll(getMoves(b,p,-1,-1,1,false,true,isPseudo));
			if (p.location().y() == 6) {
				doubleMove = 2;
			}
			moves.addAll(getMoves(b,p,0,-1,doubleMove,true,false,isPseudo));
		} else {
			moves.addAll(getMoves(b,p,1,1,1,false,true,isPseudo));
			moves.addAll(getMoves(b,p,-1,1,1,false,true,isPseudo));
			if (p.location().y() == 1) {
				doubleMove = 2;
			}
			moves.addAll(getMoves(b,p,0,1,doubleMove,true,false,isPseudo));
		}
		return moves;
	}
	
	
	public static ArrayList<Move> getLegalKnightMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		/*
		 * 8 possible moves
		 */
		moves.addAll(getMoves(b,p,-1,-2,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,-2,1,true,true,isPseudo));
		
		moves.addAll(getMoves(b,p,2,-1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,2,1,1,true,true,isPseudo));
		
		
		moves.addAll(getMoves(b,p,-1,2,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,2,1,true,true,isPseudo));
		
		moves.addAll(getMoves(b,p,-2,-1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-2,1,1,true,true,isPseudo));
		
		return moves;
	}
	
	
	public static ArrayList<Move> getLegalRookMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		moves.addAll(getMoves(b,p,1,0,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,0,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,0,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,0,-1,8,true,true,isPseudo));
		
		return moves;
	}
	
	public static ArrayList<Move> getLegalBishopMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		moves.addAll(getMoves(b,p,1,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,-1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,-1,8,true,true,isPseudo));
		
		return moves;
	}
	
	public static ArrayList<Move> getLegalQueenMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		moves.addAll(getMoves(b,p,0,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,0,-1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,0,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,0,8,true,true,isPseudo));
		
		moves.addAll(getMoves(b,p,1,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,-1,8,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,-1,8,true,true,isPseudo));
		
		return moves;
	}
	
	public static ArrayList<Move> getLegalKingMoves(Board b, Piece p, boolean isPseudo) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		moves.addAll(getMoves(b,p,0,1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,0,-1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,0,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,0,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,-1,-1,1,true,true,isPseudo));
		moves.addAll(getMoves(b,p,1,-1,1,true,true,isPseudo));
		
		return moves;
	}
	
}
