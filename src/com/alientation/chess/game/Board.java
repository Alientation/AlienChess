package com.alientation.chess.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.alientation.chess.game.settings.BoardSetting;
import com.alientation.chess.game.settings.GameSetting;
import com.alientation.chess.gui.GameWindow;
import com.alientation.chess.game.move.Move;
import com.alientation.chess.game.piece.*;

public class Board extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Special FEN key for the standard chess layout
	public static final String STANDARD_LAYOUT = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
	
	//Special properties for the board like colors
	private BoardSetting boardSetting;
	
	//Game data for FEN
	private int fullMoveCount;
	private int halfMoveCount;
	private Piece potentialPassantTarget;
	private boolean kCanCastle;
	private boolean qCanCastle;
	private boolean KCanCastle;
	private boolean QCanCastle;
	private boolean whiteTurn; // true == white, false == black
	
	
	private GridSlot[][] board;
	private ArrayList<Move> moves;
	
	
	private Team whiteTeam;
	private Team blackTeam;
	
	private final GameWindow g;
	
	//mouse stuff
	private Piece currPiece;
	private int currX;
	private int offsetX;
	private int currY;
	private int offsetY;
	
	public Board(GameWindow g, final String FEN, GameSetting gs) {
		this.g = g;
		this.board = new GridSlot[8][8];
		this.boardSetting = gs.boardSetting;
		this.moves = new ArrayList<Move>();
		this.whiteTeam = new Team("WHITE", gs.whiteName, gs.isWhiteAI, gs.whiteAISetting, this);
		this.blackTeam = new Team("BLACK", gs.blackName, gs.isBlackAI, gs.blackAISetting, this);
		
		setLayout(new GridLayout(8,8,0,0));
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		initializeBoard(FEN);
		
		this.setPreferredSize(new Dimension(400,400));
		this.setMaximumSize(new Dimension(400, 400));
		this.setMinimumSize(new Dimension(400,400));
		this.setSize(new Dimension(400,400));
		
		this.whiteTurn = true;
		whiteTeam.updatePinsAndChecks();
		blackTeam.updatePinsAndChecks();
	}
	
	private void initializeBoard(String FEN) {
		System.out.println("\n\n============INITIALIZING_BOARD============\n\n");
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (x % 2 == y % 2) {
					this.board[y][x] = new GridSlot(this, this.boardSetting.even, x, y);
				} else {
					this.board[y][x] = new GridSlot(this, this.boardSetting.odd, x, y);
				}
				this.add(this.board[y][x]);
			}
		}
		System.out.println("\n\n============INITIALIZING_PIECES============\n\n");
		initializePieces(FEN);
	}
	
	private void initializePieces(String FEN) {
		Util.ConvertFromFEN(this, FEN);
	}
	
	
	public void makeMove(Move move) {
		
	}
	
	public void unMakeMove(Move move) {
		
	}
	
	public Team getEnemyTeam(Team team) {
		if (team.getTeamName().equals("WHITE")) {
			return this.blackTeam;
		} else {
			return this.whiteTeam;
		}
	}
	
	public boolean getWhiteTurn() {
		return this.whiteTurn;
	}
	
	public Team getWhiteTeam() {
		return this.whiteTeam;
	}
	
	public Team getBlackTeam() {
		return this.blackTeam;
	}
	
	public BoardSetting getBoardSetting() {
		return this.boardSetting;
	}
	
	public GridSlot[][] getBoard() {
		return this.board;
	}
	
	public void setBoard(GridSlot[][] board) {
		this.board = board;
	}
	
	public GridSlot getSlotAt(int x, int y) {
		if (x < 0 || y < 0 || x >= 8 || y >= 8) {
			return null;
		}
		return this.board[y][x];
	}
	
	public Piece getPieceAt(int x, int y) {
		return this.getSlotAt(x, y).occupyingPiece();
	}
	
	/*
	 * After each move, check for checkmates/draws due to insufficient material or repetition of 3 positions
	 */
	public void moveFinished(String team) {
		System.out.println("\n============NEXT_TURN============");
		this.whiteTurn = !this.whiteTurn;
		if (whiteTurn)
			whiteTeam.updatePinsAndChecks();
		else
			blackTeam.updatePinsAndChecks();
	}
	
	
	public boolean canPlayerMove() {
		if ((this.currPiece.team().getTeam().equals("BLACK") && this.whiteTurn) || this.currPiece.team().isAI()) {
			return false;
		}
		if ((this.currPiece.team().getTeam().equals("WHITE") && !this.whiteTurn) || this.currPiece.team().isAI()) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				this.board[y][x].paintComponent(g);
			}
		}
		
		if (this.currPiece != null) {
			if ((this.currPiece.team().getTeam().equals("WHITE") && this.whiteTurn) || (currPiece.team().getTeam().equals("BLACK") && !this.whiteTurn)) {
				g.drawImage(this.currPiece.image(), this.currX - this.offsetX, this.currY - this.offsetY, null);
			}
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.currX = e.getX();
		this.currY = e.getY();
		
		GridSlot slot = (GridSlot) this.getComponentAt(new Point(this.currX, this.currY));
		
		this.offsetX = slot.getWidth() / 2;
		this.offsetY = slot.getHeight() / 2;
		
		
		
		if (slot.isOccupied()) {
			this.currPiece = slot.occupyingPiece();
			if (!canPlayerMove()) {
				this.currPiece = null;
				return;
			}
			slot.setDisplayPiece(false);
		}
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		GridSlot slot = (GridSlot) this.getComponentAt(new Point(e.getX(), e.getY()));
		if (this.currPiece != null) {
			if (!canPlayerMove()) {
				return;
			}
			Move m = this.currPiece.getMoveTo(slot, false);
			if (m != null) {
				this.currPiece.location().setDisplayPiece(true);
				
				m.makeMove(slot.getBoard());
				this.moves.add(m);
				System.out.println("\n\n==========BOARD==========\n" + this + "\n============END_BOARD===========\n");
				
				slot.setDisplayPiece(true);
				moveFinished(this.currPiece.team().getTeam());
				this.currPiece = null;
				
			} else {
				this.currPiece.location().setDisplayPiece(true);
				this.currPiece = null;
			}
		}
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.currX = e.getX();
		this.currY = e.getY();
		
		repaint();
	}
	
	
	
	@Override
	public void mouseMoved(MouseEvent e) { }
	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	
	
	
	
	
	public int getFullMoveCount() {
		return this.fullMoveCount;
	}

	public void setFullMoveCount(int fullMoveCount) {
		this.fullMoveCount = fullMoveCount;
	}

	public int getHalfMoveCount() {
		return this.halfMoveCount;
	}

	public void setHalfMoveCount(int halfMoveCount) {
		this.halfMoveCount = halfMoveCount;
	}

	public Piece getPotentialPassantTarget() {
		return this.potentialPassantTarget;
	}

	public void setPotentialPassantTarget(Piece potentialPassantTarget) {
		this.potentialPassantTarget = potentialPassantTarget;
	}

	public boolean iskCanCastle() {
		return this.kCanCastle;
	}

	public void setkCanCastle(boolean kCanCastle) {
		this.kCanCastle = kCanCastle;
	}

	public boolean isqCanCastle() {
		return this.qCanCastle;
	}

	public void setqCanCastle(boolean qCanCastle) {
		this.qCanCastle = qCanCastle;
	}

	public boolean isKCanCastle() {
		return this.KCanCastle;
	}

	public void setKCanCastle(boolean kCanCastle) {
		this.KCanCastle = kCanCastle;
	}

	public boolean isQCanCastle() {
		return this.QCanCastle;
	}

	public void setQCanCastle(boolean qCanCastle) {
		this.QCanCastle = qCanCastle;
	}
	
	public void setWhiteTurn(boolean whiteTurn) {
		this.whiteTurn = whiteTurn;
	}
	
	@Override
	public String toString() {
		String board = "";
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				board = board + " " + (this.board[y][x].occupyingPiece() != null ? this.board[y][x].occupyingPiece().getType() + "" : "0");
			}
			board = board + "\n";
		}
		return board;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	public void setBoardSetting(BoardSetting boardSetting) {
		this.boardSetting = boardSetting;
	}

	public void setWhiteTeam(Team whiteTeam) {
		this.whiteTeam = whiteTeam;
	}

	public void setBlackTeam(Team blackTeam) {
		this.blackTeam = blackTeam;
	}
	
}
