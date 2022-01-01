package com.alientation.chess.game.piece;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.GridSlot;
import com.alientation.chess.game.Team;
import com.alientation.chess.game.Util;
import com.alientation.chess.game.move.Move;

public abstract class Piece {
	/*
	 * 1) pawn
	 * 2) knight
	 * 3) bishop
	 * 4) rook
	 * 5) queen
	 * 6) king
	 */
	protected GridSlot location;
	protected Team team;
	protected BufferedImage img;
	
	
	public Piece(Team team, GridSlot slot, final String IMAGE_PATH) {
		this.team = team;
		this.location = slot;
		this.img = (BufferedImage) Util.loadImage(IMAGE_PATH);
		
		System.out.println("PIECE_CREATED: " + this + " | AT: " + slot);
	}
	
	
	public void draw(Graphics g) {
        g.drawImage(this.img, this.location.getX(), this.location.getY(), null);
	}
	
	public GridSlot location() {
		return this.location;
	}
	
	public void moveTo(GridSlot gs) {
		System.out.println("PIECE_MOVED: " + this + " | TO: " + gs + " | FROM: " + this.location);
		
		this.location.removePiece();
		this.location = gs;
		this.location.put(this);
	}
	
	public Team team() {
		return this.team;
	}
	
	public BufferedImage image() {
		return this.img;
	}
	
	public boolean isDifferentTeam(Piece p) {
		if (p == null) {
			return false;
		}
		return !p.team().getTeam().equals(this.team.getTeam());
	}
	
	
	public abstract ArrayList<Move> getLegalMoves(Board b, boolean isPseudo);
	public abstract Move getMoveTo(GridSlot slot, boolean isPseudo);
	public abstract Move getMoveTo(Piece p, boolean isPseudo);
	public abstract boolean canMoveTo(GridSlot slot, boolean isPseudo);
	public abstract boolean canMoveTo(Piece p, boolean isPseudo);
	public abstract int getType();
	
	@Override
	public String toString() {
		return this.team.getTeam() + "_TYPE=" + getType();
	}
}
