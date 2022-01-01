package com.alientation.chess.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alientation.chess.game.init.Game;
import com.alientation.chess.game.piece.*;

public class Util {
	public static Image loadImage(String path) {
		Image img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/*
	 * STANDARD FEN (To find possible stalemate by repetition (3 repeated positions), just compare the first section. The rest are just used to save and load games)
	 * ===========================
	 * p = pawn
	 * n = knight
	 * b = bishop
	 * r = rook
	 * q = queen
	 * k = king
	 * 
	 * UPPERCASE = white
	 * All Sections are separated by a space
	 * 
	 * FIRST_SECTION
	 * Letters represent a piece, numbers represent how many spaces till the next piece or edge
	 * '/' represents a new line
	 * 
	 * SECOND_SECTION
	 * w or b detailing who's turn it is
	 * 
	 * THIRD_SECTION
	 * k = king side castling is available
	 * q = queen side castling is available
	 * if no side can castle, then '-' is used
	 * UPPERCASE = white
	 * 
	 * FOURTH_SECTION
	 * location a potential passant target (format: x,y-1 where x,y is the target)   essentially details where the most recent move was a pawn moving 2 tiles
	 * if no en passant targets are available, then '-' is used **only 1 passant target is possible since it is only a target for the very next move of the opposing side
	 * 
	 * FIFTH_SECTION
	 * Halfmove clock - number of total that have passed since the last pawn advance or piece capture
	 * When this counter reaches 100, the game ends in a draw
	 * 
	 * SIXTH_SECTION
	 * Fullmove count - number of black moves
	 * 
	 */
	
	public static void ConvertToFEN(Board board) {
		
		
		
	}
	
	public static void ConvertFromFEN(Board board, final String FEN){
		String[] segments = FEN.split(" ");
		if (segments.length != 6) {
			System.out.println("DEBUG: INVALID FEN");
		} else {
			int x = 0, y = 0;
			
			for (int i = 0; i < segments[0].length(); i++) {
				if (isADigit(segments[0].charAt(i))) {
					x += Integer.parseInt(segments[0].charAt(i) + "");
				} else if (segments[0].charAt(i) == '/'){
					y++;
					x = 0;
				} else {
					if (x >= 8) {
						x = 0;
						y++;
					}
					Piece p;
					switch(segments[0].charAt(i)) {
					case 'p':
						p = new Pawn(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_PAWN);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().getPawns().add(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'P':
						p = new Pawn(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_PAWN);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().getPawns().add(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					case 'n':
						p = new Knight(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_KNIGHT);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().getKnights().add(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'N':
						p = new Knight(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_KNIGHT);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().getKnights().add(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					case 'b':
						p = new Bishop(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_BISHOP);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().getBishops().add(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'B':
						p = new Bishop(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_BISHOP);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().getBishops().add(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					case 'r':
						p = new Rook(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_ROOK);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().getRooks().add(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'R':
						p = new Rook(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_ROOK);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().getRooks().add(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					case 'q':
						p = new Queen(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_QUEEN);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().getRooks().add(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'Q':
						p = new Queen(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_QUEEN);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().getQueens().add(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					case 'k':
						p = new King(board.getBlackTeam(), board.getSlotAt(x, y), Game.LARGE_BLACK_KING);
						board.getSlotAt(x, y).put(p);
						board.getBlackTeam().setKing(p);
						board.getBlackTeam().getPieces().add(p);
						break;
					case 'K':
						p = new King(board.getWhiteTeam(), board.getSlotAt(x, y), Game.LARGE_WHITE_KING);
						board.getSlotAt(x, y).put(p);
						board.getWhiteTeam().setKing(p);
						board.getWhiteTeam().getPieces().add(p);
						break;
					}
					x++;
				}
			}
			if (segments[1].equals("w")) {
				board.setWhiteTurn(true);
			} else {
				board.setWhiteTurn(false);
			}
			
			/*
			 * TODO: make the booleans for castling part of each team, not the board
			 */
			
			if (segments[2].indexOf("k") != -1) {
				board.setkCanCastle(true);
			}
			if (segments[2].indexOf("K") != -1) {
				board.setKCanCastle(true);
			}
			if (segments[2].indexOf("q") != -1) {
				board.setqCanCastle(true);
			}
			if (segments[2].indexOf("Q") != -1) {
				board.setQCanCastle(true);
			}
			
			
			if (!segments[3].equals("-")) {
				board.setPotentialPassantTarget(board.getSlotAt((int) segments[3].charAt(0) - 97, Integer.parseInt(segments[3].charAt(1) + "")).occupyingPiece());
			}
			
			board.setHalfMoveCount(Integer.parseInt(segments[4]));
			board.setFullMoveCount(Integer.parseInt(segments[5]));
		}
	}
	
	public static boolean isADigit(char c) {
		if ((int) c >= 48 && (int) c <= 57) {
			return true;
		}
		return false;
	}
}
