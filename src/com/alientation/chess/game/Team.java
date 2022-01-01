package com.alientation.chess.game;

import java.util.ArrayList;

import com.alientation.chess.game.move.Move;
import com.alientation.chess.game.piece.Piece;
import com.alientation.chess.game.settings.AISetting;

public class Team {
	private final String TEAM;
	private String teamName;
	
	private boolean isAI;
	private AISetting AI;
	private Board board;
	private ArrayList<Piece> pieces;
	
	private ArrayList<Piece> pawns;
	private ArrayList<Piece> knights;
	private ArrayList<Piece> rooks;
	private ArrayList<Piece> bishops;
	private ArrayList<Piece> queens;
	
	private Piece king;
	private boolean inCheck = false;
	
	//by enemy
	private ArrayList<Piece> pinsCanMove;
	private ArrayList<Piece> pinsCantMove;
	private ArrayList<Piece> checks;
	private ArrayList<Piece> guarded;
	
	
	//TESTS
	
	private ArrayList<Pin> testPins;
	private ArrayList<Check> testChecks;
	private boolean[][] testGuardedSquares;
	
	
	public Team(String TEAM, String teamName, boolean isAI, AISetting AI, Board board) {
		this.TEAM = TEAM;
		this.teamName = teamName;
		this.isAI = isAI;
		this.AI = AI;
		
		this.board = board;
		
		this.pieces = new ArrayList<Piece>();
		this.pawns = new ArrayList<Piece>();
		this.knights = new ArrayList<Piece>();
		this.rooks = new ArrayList<Piece>();
		this.bishops = new ArrayList<Piece>();
		this.queens = new ArrayList<Piece>();
		
		this.pinsCanMove = new ArrayList<Piece>();
		this.pinsCantMove = new ArrayList<Piece>();
		this.checks = new ArrayList<Piece>();
		this.guarded = new ArrayList<Piece>();
		
		this.testPins = new ArrayList<Pin>();
		this.testChecks = new ArrayList<Check>();
		this.testGuardedSquares = new boolean[8][8];
	}
	
	public void testUpdatePinsAndChecks() {
		this.testPins.clear();
		this.testChecks.clear();
		
		/*
		 * Get guarded squares around the king
		 */
		
		for (int y = -1; y < 2; y++) {
			for (int x = -1; x < 2; x++) {
				this.testGuardedSquares[y+1][x+1] = inBounds(king.location(),x,y) && !canBeAttacked(king.location());
			}
		}
		
		
	}
	
	/**
	 * Returns whether a location is within bounds of the chess board.
	 * 
	 * @param	location	the starting location
	 * @param	direction	direction of the distance
	 * @param	distance	distance relative to the starting location
	 * @return	whether the location is within bounds of the chess board
	 */
	public boolean inBounds(GridSlot location, int[] direction, int distance) {
		if (location.x() + direction[0] * distance >= 0 && location.x() + direction[0] * distance < 8) {
			if (location.y() + direction[1] * distance >= 0 && location.y() + direction[1] * distance < 8) {
				return true;
			}
		}
		return false;
	}
	
	public boolean inBounds(GridSlot location, int xShift, int yShift) {
		return location.x() + xShift >= 0 && location.x() + xShift < 8 && location.y() + yShift <= 0 && location.y() + yShift < 8;
	}
	
	public boolean canBeAttacked(GridSlot slot) {
		for (int[] direction : Direction.getAllDirections()) {
			for (int distance = 1; inBounds(slot,direction,distance); distance++) {
				Piece p = this.board.getSlotAt(slot.x() + direction[0] * distance, slot.y() + direction[1] * distance).occupyingPiece();
				if (p == null) {
					continue;
				} else {
					switch(p.getType()) {
					case 1:
						//pawn -> separate check
						break;
					case 2:
						//knight -> separate check
						break;
					case 3:
						//bishop
						break;
					case 4:
						//rook
						break;
					case 5:
						//queen
						break;
					case 6:
						//king
						if (distance == 1) {
							return true;
						}
						break;
					}
					break; //since there is a piece here, it will block others from attacking
				}
			}
		}
		return false;
	}
	
	
	/*
	 * The piece and target are already on the same line (horizontal,vertical, or diagonal) and there are no pieces in between.
	 * Thus its more efficient to just do some simple conditionals rather than call the Piece.canMoveTo() method since that will loop through all possible moves for that piece.
	 */
	public boolean helperCanMoveTo(Piece p, GridSlot target, int[] direction) {
		if (p.getType() == 1 && p.location().distanceTo(target) == 2 && (direction[0] + direction[1] == 0 || direction[0] + direction[1] == 2)) {
			//This means the pawn is directing on the diagonal next to the piece.
			//Whether the pawn is facing the right direction
			if (p.team().getTeam().equals("WHITE")) {
				if (direction[1] == -1) {
					return true;
				}
			} else {
				if (direction[1] == 1) {
					return true;
				}
			}
		} else if (p.getType() == 2) {
			
		}
		return false;
	}
	
	public void updatePinsAndChecks() {
		this.pinsCanMove.clear();
		this.pinsCantMove.clear();
		this.checks.clear();
		this.guarded.clear();
		
		//checks
		
		int[][] directions = {
				{-1,-1},
				{0,-1},
				{1,-1},
				{1,0},
				{1,1},
				{0,1},
				{-1,1},
				{-1,0}
		};
		for (int dir = 0; dir < directions.length; dir++) {
			Piece seenPiece = null;
			
			int distance = 1;
			while (this.board.getSlotAt(directions[dir][0] * distance + this.king.location().x(),directions[dir][1] * distance + this.king.location().y()) != null) {
				Piece p = this.board.getPieceAt(directions[dir][0] * distance + this.king.location().x(),directions[dir][1] * distance + this.king.location().y());
				if (p == null) {
					distance++;
					continue;
				}
				System.out.println(TEAM + "'s KING sees " + p + " at location " + p.location() + " in direction (" + directions[dir][0] + ", " + directions[dir][1] + ")");
				if (seenPiece != null && p.isDifferentTeam(this.king)) {
					//Saw 2 pieces
					if (!seenPiece.isDifferentTeam(this.king)) {
						//Same team thus is blocking the attack from piece 'p'
						if ((p.canMoveTo(seenPiece,false))) {
							if (this.pinsCanMove.contains(seenPiece)) {
								this.pinsCanMove.remove(seenPiece);
								this.pinsCantMove.add(seenPiece);
							} else if (!this.pinsCantMove.contains(seenPiece)) {
								this.pinsCanMove.add(seenPiece);
							}
							System.out.println(TEAM + " cannot move " + seenPiece + " at location " + seenPiece.location() + " because it is pinned by " + p + " at location " + p.location());
						}
						break;
					} else if (p.isDifferentTeam(this.king)) {
						/*
						 * Check for guarded pieces independently kind of like how you would check for checks for a king
						 */
						
						if (this.king.canMoveTo(seenPiece,true) && p.canMoveTo(seenPiece,true)) {
							//King cannot attack this piece since the 'p' piece is guarding this 'seenPiece'
							this.guarded.add(seenPiece);
							System.out.println(TEAM + "'s KING cannot attack " + seenPiece + " at location " + seenPiece.location());
							break;
						}
					}
				} else if (p.isDifferentTeam(this.king)) {
					if (p.canMoveTo(this.king,true)) {
						this.checks.add(p);
						System.out.println(TEAM + "'s KING is checked by " + p + " at location " + p.location());
					}
				}
				seenPiece = p;
				
				distance++;
			}
		}
		
		
	}
	
	public ArrayList<Piece> findAllAttackingPieces(Piece target) {
		ArrayList<Piece> attackingPieces = new ArrayList<Piece>();
		int[][] directions = {
				{-1,-1},
				{0,-1},
				{1,-1},
				{1,0},
				{1,1},
				{0,1},
				{-1,1},
				{-1,0}
		};
		for (int dir = 0; dir < directions.length; dir++) {
			int distance = 1;
			while (this.board.getSlotAt(directions[dir][0] * distance + target.location().x(),directions[dir][1] * distance + target.location().y()) != null) {
				Piece p = this.board.getPieceAt(directions[dir][0] * distance + this.king.location().x(),directions[dir][1] * distance + this.king.location().y());
				if (p == null) {
					distance++;
					continue;
				}
				if (p.isDifferentTeam(target)) {
					if (p.canMoveTo(target,false)) {
						attackingPieces.add(p);
					}
				} else {
					break;
				}
			}
		}
		return attackingPieces;
	}
	
	
	public ArrayList<Move> generateAllPossibleMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		if (this.checks.size() >= 2) {
			return this.king.getLegalMoves(this.board,false);
		} else {
			/*
			 * TODO: Implement Move ordering
			 */
			for (Piece p : this.pieces) {
				moves.addAll(p.getLegalMoves(this.board,false));
			}
		}
		return moves;
	}
	
	public void add(Piece p) {
		this.pieces.add(p);
		switch(p.getType()) {
		case 1:
			this.pawns.add(p);
			break;
		case 2:
			this.knights.add(p);
			break;
		case 3:
			this.bishops.add(p);
			break;
		case 4:
			this.rooks.add(p);
			break;
		case 5:
			this.queens.add(p);
			break;
		case 6:
			this.king = p;
			break;
		}
	}
	
	public void remove(Piece p) {
		this.pieces.remove(p);
		switch(p.getType()) {
		case 1:
			this.pawns.remove(p);
			break;
		case 2:
			this.knights.remove(p);
			break;
		case 3:
			this.bishops.remove(p);
			break;
		case 4:
			this.rooks.remove(p);
			break;
		case 5:
			this.queens.remove(p);
			break;
		case 6:
			this.king = null;
			break;
		}
	}
	
	public boolean isAI() {
		return this.isAI;
	}
	
	public ArrayList<Piece> getPieces() {
		return this.pieces;
	}
	
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
	
	public ArrayList<Piece> getPawns() {
		return this.pawns;
	}
	
	public void setPawns(ArrayList<Piece> pawns) {
		this.pawns = pawns;
	}
	
	public ArrayList<Piece> getKnights() {
		return this.knights;
	}
	
	public void setKnights(ArrayList<Piece> knights) {
		this.knights = knights;
	}
	
	public ArrayList<Piece> getRooks() {
		return this.rooks;
	}
	
	public void setRooks(ArrayList<Piece> rooks) {
		this.rooks = rooks;
	}
	
	public ArrayList<Piece> getBishops() {
		return this.bishops;
	}
	
	public void setBishops(ArrayList<Piece> bishops) {
		this.bishops = bishops;
	}
	
	public ArrayList<Piece> getQueens() {
		return this.queens;
	}
	
	public void setQueens(ArrayList<Piece> queens) {
		this.queens = queens;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public void setAISetting(AISetting AI) {
		this.AI = AI;
	}
	
	public AISetting getAISetting() {
		return this.AI;
	}
	
	public String getTeam() {
		return this.TEAM;
	}
	
	public Piece getKing() {
		return this.king;
	}
	
	public void setKing(Piece p) {
		this.king = p;
	}
	
	public boolean getIsChecked() {
		return this.inCheck;
	}
	
	public void setIsChecked(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public ArrayList<Piece> getChecks() {
		return checks;
	}

	public void setChecks(ArrayList<Piece> checks) {
		this.checks = checks;
	}

	public ArrayList<Piece> getGuarded() {
		return guarded;
	}

	public void setGuarded(ArrayList<Piece> guarded) {
		this.guarded = guarded;
	}

	public ArrayList<Piece> getPinsCanMove() {
		return pinsCanMove;
	}

	public void setPinsCanMove(ArrayList<Piece> pinsCanMove) {
		this.pinsCanMove = pinsCanMove;
	}

	public ArrayList<Piece> getPinsCantMove() {
		return pinsCantMove;
	}

	public void setPinsCantMove(ArrayList<Piece> pinsCantMove) {
		this.pinsCantMove = pinsCantMove;
	}

	public AISetting getAI() {
		return AI;
	}

	public void setAI(AISetting aI) {
		AI = aI;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isInCheck() {
		return inCheck;
	}

	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public ArrayList<Pin> getTestPins() {
		return testPins;
	}

	public void setTestPins(ArrayList<Pin> testPins) {
		this.testPins = testPins;
	}

	public ArrayList<Check> getTestChecks() {
		return testChecks;
	}

	public void setTestChecks(ArrayList<Check> testChecks) {
		this.testChecks = testChecks;
	}

	public boolean[][] getTestGuardedSquares() {
		return testGuardedSquares;
	}

	public void setTestGuardedSquares(boolean[][] testGuardedSquares) {
		this.testGuardedSquares = testGuardedSquares;
	}

	public String getTEAM() {
		return TEAM;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}
}