package com.example.connect4;

import java.util.ArrayList;

public class GameState {
	
	//private int length;
	
	//private int width;
	
	//private int[][] stateArray = new int[length][width];
	
	private int[] columnNumber;
	
	private String gameMoves;
	
	private ArrayList<GameState> children;
	
	private GameState parentState;
	
	private int heuristicValue;
	
	//private int score;
	
	public GameState (int[] columnNumber, String gameMoves, GameState parentState) {
		//this.length = length;
		//this.width = width;
		//this.stateArray = stateArray;
		this.columnNumber = columnNumber;
		this.gameMoves = gameMoves;
		this.parentState = parentState;
		this.children = new ArrayList<GameState>();
	}
	/*
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int[][] getStateArray() {
		return stateArray;
	}

	public void setStateArray(int[][] stateArray) {
		this.stateArray = stateArray;
	}
	*/
	public int[] getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int[] columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public String getGameMoves() {
		return gameMoves;
	}

	public void setGameMoves(String gameMoves) {
		this.gameMoves = gameMoves;
	}
	
	public ArrayList<GameState> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<GameState> children) {
		this.children = children;
	}
	
	public GameState getParentState() {
		return parentState;
	}
	public void setParentState(GameState parentState) {
		this.parentState = parentState;
	}
	
	public int getHeuristicValue() {
		return heuristicValue;
	}
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

}
