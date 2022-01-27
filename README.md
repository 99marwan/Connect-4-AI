# Connect 4
Java GUI Desktop game where the user plays against the computer using minimax algorithm with heuristic, there are levels in-game first with alpha-beta pruning and without and the user choose the number of levels in minimax tree.

## Assumptions and Details
-The human player will start the game with a red disk.  
-In the grid the human position takes “1” ,the computer takes “-1” and the empty place takes “0”.  
-For this reason Computer will be the Minimizer in the minmax.   
-Grid size is always 6 rows x 7 columns.  
-Player 2 is the Computer itself.  

![image](https://user-images.githubusercontent.com/54478282/151462056-6fdc70ce-21b5-4fb6-94b1-73a9f4165d9f.png)
![image](https://user-images.githubusercontent.com/54478282/151462096-f606895d-1404-4c61-b970-1e803eac71f0.png)
## GameState
**Data Structures**  
int[]: columnNumber is a one dimensional integer array that holds the count of pieces played in each column.  
ArrayList<>: children is an array list of GameState used to hold the children of the current game state.  

**Algorithms**
N/A  

## GameLogic
**Data Structures**  
N/A  

**Algorithms**  
public GameState Initialize_Game(int length, int width,int k, boolean alphaBeta_Pruning, boolean human_isPlayer1): initializes the game.  

public ArrayList<GameState> Gen_Children(GameState root): generates children states of current state.  

public int[][] buildBoard(GameState state): builds the two dimensional array board from the given game state.  

public int calculateHeuristicValue(GameState state): calculates heuristic of given game state.

public int[] calculateScore(GameState state): calculates score of given game state.
