package com.example.connect4;

import java.awt.Point;
import java.util.ArrayList;

public class Heuristic {
	
	//Static heuristic that shows the maximum number of combinations/chains of length greater than or equal to 4
	//that a piece in the given position can participate in
	private int[][] staticHeuristic = { {3,4,5,7,5,4,3},
										{4,6,8,10,8,6,4},
										{5,8,11,13,11,8,5},
										{5,8,11,13,11,8,5},
										{4,6,8,10,8,6,4},
										{3,4,5,7,5,4,3} };
	
	private int scoreMultiplier = 21;
	
	private int primaryHeuristic(int[][] gameBoard) {
		int length = gameBoard.length;
		int width = gameBoard[0].length;
		int value = 0;
		boolean emptyRow = true;
		for (int i = length - 1; i > -1 ; i--) {
			emptyRow = true;
			for (int j = 0; j < width ; j++) {
				if (gameBoard[i][j] != 0) {
					emptyRow = false;
					value += gameBoard[i][j] * staticHeuristic[i][j];
				}
			}
			if (emptyRow) {
				break;
			}
		}
		
		return value;
	}
	
	private int secondaryHeuristic(int[][] gameBoard) {
		int length = gameBoard.length;
		int width = gameBoard[0].length;
		int value = 0;
		int startIndex=0;
		int endIndex=0;
		int currentPlayerChain=0;
		int chainLength = 0;
		int possibleExtensions = 0;
		int score=0;
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
						
						//Calculate Heuristic based on chain length, possible extensions, and score
						chainLength = endIndex - startIndex + 1;
						if (startIndex - 1 > -1 && gameBoard[i][startIndex - 1] == 0) {
							if (i + 1 >= length || gameBoard[i+1][startIndex - 1] != 0) {
								possibleExtensions++;
								if (startIndex - 2 > -1 && gameBoard[i][startIndex - 2] == 0) {
									if (i + 1 >= length || gameBoard[i+1][startIndex - 2] != 0) {
										possibleExtensions++;
									}
								}
							}
							
							
						}
						
						if (endIndex + 1 < width && gameBoard[i][endIndex + 1] == 0) {
							if (i + 1 >= length || gameBoard[i+1][endIndex + 1] != 0) {
								possibleExtensions++;
								if (endIndex + 2 < width && gameBoard[i][endIndex + 2] == 0) {
									if (i + 1 >= length || gameBoard[i+1][endIndex + 2] != 0) {
										possibleExtensions++;
									}
								}
							}
							
						}
						
						value += currentPlayerChain * chainLength * possibleExtensions;
						
						//Add value modifier for score
						if (chainLength >= 4) {
							score = chainLength - 3;
							value += currentPlayerChain * score * scoreMultiplier;
						}

						possibleExtensions = 0;
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
						
						//Calculate Heuristic based on chain length, possible extensions, and score
						chainLength = startIndex - endIndex + 1;
						if (endIndex - 1 > -1 && gameBoard[endIndex - 1][j] == 0) {
								possibleExtensions++;
							
							if (endIndex - 2 > -1) {
								possibleExtensions++;
							}
							
						}
						
						value += currentPlayerChain * chainLength * possibleExtensions;
						
						//Add value modifier for score
						if (chainLength >= 4) {
							score = chainLength - 3;
							value += currentPlayerChain * score * scoreMultiplier;
						}

						possibleExtensions = 0;
						i++;
					}
				}
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//By-diagonal Checking
		int i,j,k;
		ArrayList<Point> startIndices;
		ArrayList<Point>[] diagonals = (ArrayList<Point>[]) new ArrayList[2];
		ArrayList<Point> indicesToCheck;
		Point p = new Point(0,0);
		
		//By-diagonal (Positive Slope) Checking
		
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
		
		
		//By-diagonal (Negative Slope) Checking
		
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
			
			
			row = 0;
			column = 0;
			for (Point start : startIndices) {
				row = start.x;
				column = start.y;
				chainStarted = false;
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
							
							//Calculate Heuristic based on chain length, possible extensions, and score
							chainLength = endIndex - startIndex + 1;
							Point secondaryPoint = null;
							
							//Point below one space and to the left(diagonalDir = 0) / right(diagonalDir = 1) one space
							if (startIndex - 1 > -1) {
								secondaryPoint = indicesToCheck.get(startIndex - 1);
							}
							
							if (startIndex - 1 > -1 && gameBoard[secondaryPoint.x][secondaryPoint.y] == 0) {
								if (secondaryPoint.x >= length - 1 || gameBoard[secondaryPoint.x + 1][secondaryPoint.y] != 0) {
									possibleExtensions++;
									//Point below two spaces and to the left(diagonalDir = 0) / right(diagonalDir = 1) 
									//two spaces
									if (startIndex - 2 > -1) {
										secondaryPoint = indicesToCheck.get(startIndex - 2);
									}
									if (startIndex - 2 > -1 && gameBoard[secondaryPoint.x][secondaryPoint.y] == 0) {
										if (secondaryPoint.x >= length - 1 || gameBoard[secondaryPoint.x + 1][secondaryPoint.y] != 0) {
											possibleExtensions++;
										}
									}
								}
								
							}
							
							//Point above one space and to the right(diagonalDir = 0) / left(diagonalDir = 1) one space
							if (endIndex + 1 < indicesToCheck.size()) {
								secondaryPoint = indicesToCheck.get(endIndex + 1);
							}
							
							if (endIndex + 1 < indicesToCheck.size() && gameBoard[secondaryPoint.x][secondaryPoint.y] == 0) {
								if ( gameBoard[secondaryPoint.x + 1][secondaryPoint.y] != 0) {
									possibleExtensions++;
									//Point above two spaces and to the  right(diagonalDir = 0) / left(diagonalDir = 1) 
									//two spaces
									if (endIndex + 2 < indicesToCheck.size()) {
										secondaryPoint = indicesToCheck.get(endIndex + 2);
									}
									if (endIndex + 2 < indicesToCheck.size() && gameBoard[secondaryPoint.x][secondaryPoint.y] == 0) {
										if (gameBoard[secondaryPoint.x + 1][secondaryPoint.y] != 0) {
											possibleExtensions++;
										}
									}
								}
								
							}
							
							value += currentPlayerChain * chainLength * possibleExtensions;
							
							//Add value modifier for score
							if (chainLength >= 4) {
								score = chainLength - 3;
								value += currentPlayerChain * score * scoreMultiplier;
							}

							possibleExtensions = 0;
							k--;
						}
					}
				}
				
			}
		}

			

		return value;
	}
	
	
	
	
	public int calculateTotalHeuristic(int[][] gameBoard) {
		
		return primaryHeuristic(gameBoard) + secondaryHeuristic(gameBoard);
	}

}
