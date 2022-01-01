package com.alientation.chess.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.alientation.chess.game.piece.Piece;

public class GridSlot extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Board board;
	
	private Color color;
	private Piece occupyingPiece;
	
	private int x;
	private int y;
	
	private boolean displayPiece;
	
	public GridSlot(Board board, Color color, int locationX, int locationY) {
		this.board = board;
		this.color = color;
		this.x = locationX;
		this.y = locationY;
		this.displayPiece = true;
		this.setBorder(BorderFactory.createEmptyBorder());
		
	}
	
	public boolean isOccupied() {
		return this.occupyingPiece != null;
	}
	
	public void put(Piece p) {
		if (occupyingPiece != null) {
			capture(occupyingPiece);
		}
		this.occupyingPiece = p;
		repaint();
	}
	
	public Piece removePiece() {
		Piece p = this.occupyingPiece;
		this.occupyingPiece = null;
		repaint();
		return p;
	}
	
	public void capture(Piece p) {
		this.occupyingPiece.team().remove(this.occupyingPiece);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		if (this.occupyingPiece != null && this.displayPiece) {
			this.occupyingPiece.draw(g);
		}
	}
	
	
	public int distanceTo(GridSlot gs) {
		return Math.abs(gs.x - this.x) + Math.abs(gs.y - this.y);
	}
	
	public boolean isOnSameDiagonal(GridSlot gs) {
		return Math.abs(gs.y - this.y) == Math.abs(gs.x - this.x);
	}
	
	public boolean isOnSameAxis(GridSlot gs) {
		return gs.x == this.x || gs.y == this.y;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Piece occupyingPiece() {
		return this.occupyingPiece;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean displayPiece() {
		return this.displayPiece;
	}
	
	public void setDisplayPiece(boolean displayPiece) {
		this.displayPiece = displayPiece;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
}
