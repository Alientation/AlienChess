package com.alientation.chess.game;

public enum Direction {
	
	/*
	 * {x shift,y shift}
	 * 
	 * positive x -> right
	 * positive y -> down
	 */
	
	NORTH(0,-1),NORTH_EAST(1,-1),EAST(1,0),SOUTH_EAST(1,1),SOUTH(0,1),SOUTH_WEST(-1,1),WEST(-1,0),NORTH_WEST(-1,-1);

	private int[] direction;
	
	private Direction(int x, int y) {
		direction = new int[2];
		direction[0] = x;
		direction[1] = y;
	}
	
	public int[] getDirection() {
		return this.direction;
	}
	
	public int[] getOppositeDirection() {
		return new int[] {direction[0] * -1, direction[1] * -1};
	}
	
	public static int[][] getAllDirections() {
		return new int[][] {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,1-1}};
	}
	
	public static int[][] getAllPawnAttackDirections(Team team) {
		if (team.getTeamName().equals("WHITE")) {
			return new int[][] {{-1,1},{1,1}};
		} else {
			return new int[][] {{-1,-1},{1,-1}};
		}
	}

}
