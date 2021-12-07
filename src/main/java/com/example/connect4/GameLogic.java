package com.example.connect4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class GameLogic {
	
	private int length;
	
	private int width;

	private int k;
	
	private boolean alphaBeta_Pruning;
	
	private boolean human_isPlayer1;
	
	GameState currentState;
	
	Heuristic heuristicCalc;
	
	public GameState Initialize_Game(int length, int width,int k, boolean alphaBeta_Pruning, boolean human_isPlayer1) {
		this.length = length;
		this.width = width;
		if (k > 42) {
			k = 42;
		}
		this.k = k;
		this.alphaBeta_Pruning = alphaBeta_Pruning;
		this.human_isPlayer1 = human_isPlayer1;
		
		GameState initialState = new GameState(new int[this.width],"",null);
		/*
		initialState.setLength(this.length);
		initialState.setWidth(this.width);
		initialState.setStateArray(new int[this.length][this.width]);
		initialState.setColumnNumber(new int[width]);
		*/
		
		this.currentState = initialState;
		
		return initialState;
	}
	
	public ArrayList<GameState> Gen_Children(GameState root){
		
		GameState currentChild;
		int[] newColNum;
		String newGameMoves;
		ArrayList<GameState> children = new ArrayList<GameState>();
		
		for (int i = 0; i < width; i++) {
			newColNum = Arrays.copyOf(root.getColumnNumber(), width) ;
			newGameMoves = root.getGameMoves();
			if (newColNum[i] < length) {
				newColNum[i] ++;
				newGameMoves += Integer.toString(i);
				currentChild = new GameState(newColNum,newGameMoves,root);
				currentChild.setParentState(root);
				children.add(currentChild);
			}
			
		}
		root.setChildren(children);
				
		return children;
	}
	
	public void Print_K_Levels(GameState root){
		
		int tmp = k;
		ArrayList<GameState> level = new ArrayList<GameState>();
		level.add(root);
		Print_K_Levels(level,tmp);
		
	}
	
	private void Print_K_Levels(ArrayList<GameState> level, int k){
		
		if (k < 0) {
			return;
		}
		ArrayList<GameState> newLevel = new ArrayList<GameState>();
		GameState currentState = null;
		int treeLevel = this.k - k;
		System.out.print("Level " + treeLevel + ": ");
		while (level.size() > 0) {
			currentState = level.get(0);
			newLevel.addAll(Gen_Children(currentState));
			System.out.print(currentState.getGameMoves() + " ");
			level.remove(0);
		}
		System.out.print("\n");
		
		Print_K_Levels(newLevel, k-1);
		/*
		if (k-1 > 0) {
			for (int i = 0; i < root.getChildren().size() ; i++) {
				Gen_K_Levels(root.getChildren().get(i),k-1);
			}
		}
		
				
		return root;*/
	}
	
	
	
	public int[][] buildBoard(GameState state) {
		int[][] gameBoard = new int[length][width];
		String gameMoves = state.getGameMoves();
		int [] colNum = new int[width];
		int player = 1;
		int column;
		for(int i = 0; i < gameMoves.length(); i++) {
			column = Character.getNumericValue(gameMoves.charAt(i));
			gameBoard[length - colNum[column] - 1][column] = player;
			player *= -1;
			colNum[column]++;
		}
		
		return gameBoard;
	}
	
	public int calculateHeuristicValue(GameState state) {
		heuristicCalc = new Heuristic();
		int[][] gameBoard = buildBoard(state);
		int value = heuristicCalc.calculateTotalHeuristic(gameBoard);
		state.setHeuristicValue(value);
		
		return value;

	}
	
	public int[] calculateScore(GameState state) {
		heuristicCalc = new Heuristic();
		int[][] gameBoard = buildBoard(state);
		int length = gameBoard.length;
		int width = gameBoard[0].length;
		int startIndex=0;
		int endIndex=0;
		int currentPlayerChain=0;
		int chainLength = 0;
		int scorePlayerOne = 0;
		int scorePlayerTwo = 0;
		boolean chainStarted;
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//By-row Checking
		boolean emptyRow = true;
		for (int i = length - 1; i > -1 ; i--) {
			emptyRow = true;
			chainStarted = false;
			for (int j = 0; j < width ; j++) {
				if (!chainStarted) {
					if (gameBoard[i][j] != 0) {
						emptyRow = false;
						startIndex = j;
						endIndex = j;
						chainStarted = true;
						currentPlayerChain = gameBoard[i][j];
					}
				} else {
					if (gameBoard[i][j] == currentPlayerChain) {
						endIndex = j;
					} 
					if (j == width - 1 || gameBoard[i][j] != currentPlayerChain) {
						chainStarted = false;
						
						//Calculate score based on number of chains of length four
						chainLength = endIndex - startIndex + 1;
						
						//Add value modifier for score
						if (chainLength >= 4) {
							if (currentPlayerChain == 1) {
								scorePlayerOne += chainLength - 3;
							} else {
								scorePlayerTwo += chainLength - 3;
							}

						}

						j--;
					}
				}
			}
			if (emptyRow) {
				break;
			}
			
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//By-column Checking
		for (int j = 0; j < width ; j++) {
			chainStarted = false;
			for (int i = length - 1; i > -1 ; i--) {
				if (!chainStarted) {
					if (gameBoard[i][j] == 0) {
						break;
					} else {
						startIndex = i;
						endIndex = i;
						chainStarted = true;
						currentPlayerChain = gameBoard[i][j];
					}
				}
				else {
					if (gameBoard[i][j] == currentPlayerChain) {
						endIndex = i;
					} 
					if (i == 0 || gameBoard[i][j] != currentPlayerChain) {
						chainStarted = false;
						
						//Calculate score based on number of chains of length four
						chainLength = startIndex - endIndex + 1;
						
						
						//Add value modifier for score
						if (chainLength >= 4) {
							if (currentPlayerChain == 1) {
								scorePlayerOne += chainLength - 3;
							} else {
								scorePlayerTwo += chainLength - 3;
							}
						}
						i++;
					}
				}
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int i,j,k;
		ArrayList<Point> startIndices;
		ArrayList<Point>[] diagonals = (ArrayList<Point>[]) new ArrayList[2];
		ArrayList<Point> indicesToCheck;
		Point p = new Point(0,0);
		
		//By-Diagonal (Positive Slope) Checking
		
		diagonals[0] = new ArrayList<Point>();
		//Get starting indices
		i = length - 1;
		//Diagonals starting from j = 0 to j = 2 will never form a chain that results in a point
		for (j = 3; j > -1; j--) {
			diagonals[0].add(new Point(i,j));
		}
		j = 0;
		//Diagonals starting from i = 0 to i = 2 will never form a chain that results in a point (and i = length - 1
		// was already added)
		for (i = 3; i < length - 1; i++) {
			diagonals[0].add(new Point(i,j));
		}
		
		
		//By-Diagonal (Negative Slope) Checking
		
		diagonals[1] = new ArrayList<Point>();
		//Get starting indices
		i = length - 1;
		//Diagonals starting from j = 0 to j = 2 will never form a chain that results in a point
		for (j = 3; j < width; j++) {
			diagonals[1].add(new Point(i,j));
		}
		j = width - 1;
		//Diagonals starting from i = 0 to i = 2 will never form a chain that results in a point (and i = length - 1
		// was already added)
		for (i = 3; i < length - 1; i++) {
			diagonals[1].add(new Point(i,j));
		}
		int row,column;
		//Check diagonals
		for (int diagonalDir = 0; diagonalDir < diagonals.length; diagonalDir++) {
			startIndices = diagonals[diagonalDir];
			
			chainStarted = false;
			row = 0;
			column = 0;
			for (Point start : startIndices) {
				row = start.x;
				column = start.y;
				indicesToCheck = new ArrayList<Point>();
				if (diagonalDir == 0) {
					while ( row > -1 && column < width ) {
						indicesToCheck.add(new Point(row,column));
						row--;
						column++;
					}
				} else {
					while ( row > -1 && column > -1 ) {
						indicesToCheck.add(new Point(row,column));
						row--;
						column--;
					}
				}
				
				for (k = 0; k < indicesToCheck.size() ; k++) {
					p = indicesToCheck.get(k);
					if (!chainStarted) {
						if (gameBoard[p.x][p.y] != 0) {
							startIndex = k;
							endIndex = k;
							chainStarted = true;
							currentPlayerChain = gameBoard[p.x][p.y];
						}
					} else {
						if (gameBoard[p.x][p.y] == currentPlayerChain) {
							endIndex = k;
						} 
						if (k == indicesToCheck.size() - 1 || gameBoard[p.x][p.y] != currentPlayerChain) {
							chainStarted = false;
							
							//Calculate score based on number of chains of length four
							chainLength = endIndex - startIndex + 1;
							
							
							//Add value modifier for score
							if (chainLength >= 4) {
								if (currentPlayerChain == 1) {
									scorePlayerOne += chainLength - 3;
								} else {
									scorePlayerTwo += chainLength - 3;
								}
							}

							k--;
						}
					}
				}
				
			}
		}
		int[] result = {scorePlayerOne,scorePlayerTwo};
		
		return result;
	}
	
	

}
