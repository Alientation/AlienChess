package com.alientation.chess.game.init;

import javax.swing.SwingUtilities;

import com.alientation.chess.gui.StartWindow;

public class Game implements Runnable{
	
	/*
	 * Version System
	 * ========================
	 * major.minor.variant		->		EX. 1.32.c
	 * 
	 */
	public static String version = "0.0.a";
	
	public static String[] AI_LIST = {
			"DummyBot v0.0.a"
	};
	
	public static final String BASEPATH = System.getProperty("user.dir");
	
	public static final String RESOURCEPATH = BASEPATH + "\\src\\com\\alientation\\chess\\gui\\resource";
	
	public static final String SMALL_BLACK_PAWN = RESOURCEPATH + "\\bp.png";
	public static final String SMALL_BLACK_BISHOP = RESOURCEPATH + "\\bb.png";
	public static final String SMALL_BLACK_KNIGHT = RESOURCEPATH + "\\bn.png";
	public static final String SMALL_BLACK_ROOK = RESOURCEPATH + "\\br.png";
	public static final String SMALL_BLACK_QUEEN = RESOURCEPATH + "\\bq.png";
	public static final String SMALL_BLACK_KING = RESOURCEPATH + "\\bk.png";
	
	public static final String LARGE_BLACK_PAWN = RESOURCEPATH + "\\bpawn.png";
	public static final String LARGE_BLACK_BISHOP = RESOURCEPATH + "\\bbishop.png";
	public static final String LARGE_BLACK_KNIGHT = RESOURCEPATH + "\\bknight.png";
	public static final String LARGE_BLACK_ROOK = RESOURCEPATH + "\\brook.png";
	public static final String LARGE_BLACK_QUEEN = RESOURCEPATH + "\\bqueen.png";
	public static final String LARGE_BLACK_KING = RESOURCEPATH + "\\bking.png";
	
	public static final String SMALL_WHITE_PAWN = RESOURCEPATH + "\\wp.png";
	public static final String SMALL_WHITE_BISHOP = RESOURCEPATH + "\\wb.png";
	public static final String SMALL_WHITE_KNIGHT = RESOURCEPATH + "\\wn.png";
	public static final String SMALL_WHITE_ROOK = RESOURCEPATH + "\\wr.png";
	public static final String SMALL_WHITE_QUEEN = RESOURCEPATH + "\\wq.png";
	public static final String SMALL_WHITE_KING = RESOURCEPATH + "\\wk.png";
	
	public static final String LARGE_WHITE_PAWN = RESOURCEPATH + "\\wpawn.png";
	public static final String LARGE_WHITE_BISHOP = RESOURCEPATH + "\\wbishop.png";
	public static final String LARGE_WHITE_KNIGHT = RESOURCEPATH + "\\wknight.png";
	public static final String LARGE_WHITE_ROOK = RESOURCEPATH + "\\wrook.png";
	public static final String LARGE_WHITE_QUEEN = RESOURCEPATH + "\\wqueen.png";
	public static final String LARGE_WHITE_KING = RESOURCEPATH + "\\wking.png";
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

	@Override
	public void run() {
		SwingUtilities.invokeLater(new StartWindow());
	}
}
