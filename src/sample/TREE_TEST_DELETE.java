package sample;

import java.util.ArrayList;


public class TREE_TEST_DELETE {

    public static void main(String[] args) {
        int length = 6;

        int width = 7;

        int k = 42;

        GameLogic logic = new GameLogic();

        logic.Initialize_Game(length, width, k, false, false);

        GameState initialState = new GameState(new int[width],"",null);
		/*
		GameState root = logic.Gen_K_Levels(initialState, k);

		ArrayList<GameState> printQueue = new ArrayList<>();
		printQueue.add(root);

		for (int i = 0; i < printQueue.size(); i++) {
			for (GameState child : printQueue.get(i).getChildren() ) {
				printQueue.add(child);
			}

		}
		/*
		for (int i = 0; i < printQueue.size(); i++) {
			for (int j = 0; j < printQueue.get(i).getGameMoves().length(); j++) {
				System.out.print("-");
			}
			System.out.print(printQueue.get(i).getGameMoves());
			System.out.print("\n");
		}
		*/

        //TREE PRINT (Max k = 4)
		/*
		int tmpWidth = 1;
		for (int i = 0; i < printQueue.size(); i++) {

			int j;
			for (j = i; j < i + tmpWidth; j++) {
				System.out.print(printQueue.get(j).getGameMoves());
				System.out.print("  ");
			}
			System.out.print("\n");
			tmpWidth *= width;

			i = j-1;
		}
		*/

        Heuristic h = new Heuristic();

        long startTime = System.currentTimeMillis();

        logic.Print_K_Levels(initialState);

		/*
		for (int i = 0; i < printQueue.size(); i++) {
			int[][] board = logic.buildBoard(printQueue.get(i));
			System.out.print("//////////////////////////////////////////////////////////\n");
			for (int f = 0; f < length; f++){
				for (int j = 0; j < width; j++) {
					System.out.print(board[f][j] + " ");
				}
				System.out.print("\n");
			}

			System.out.println(h.calculateTotalHeuristic(board));
			System.out.print("//////////////////////////////////////////////////////////\n");
		}
		System.out.println(printQueue.size());
		*/
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken = " + (endTime-startTime) + " ms");

		/*
		int[][] gameBoard = logic.buildBoard(printQueue.get(printQueue.size() - 1));

		for (int i = 0; i < length; i++){
			for (int j = 0; j < width; j++) {
				System.out.print(gameBoard[i][j] + " ");
			}
			System.out.print("\n");
		}
		*/

        int[][] customBoard = { {0,0,1,0,0,0,0},
                {0,0,1,1,1,0,0},
                {0,0,1,1,1,0,0},
                {0,1,1,1,1,1,0},
                {0,1,1,-1,-1,-1,-1},
                {-1,-1,-1,-1,1,1,1} };
        System.out.println("//////////////////////////////////");
        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++) {
                System.out.print(customBoard[i][j] + "   ");
            }
            System.out.print("\n");
        }

        System.out.println(h.calculateTotalHeuristic(customBoard));

        int[][] customBoard2 = { {0,-1,0,0,0,0,0},
                {0,1,0,0,0,0,0},
                {0,-1,0,0,0,0,0},
                {0,1,0,0,0,0,0},
                {0,-1,0,0,0,0,0},
                {0,1,0,0,0,0,0} };

        //System.out.println("Player One = " + logic.calculateScore(customBoard)[0]);
        //System.out.println("Player Two = " + logic.calculateScore(customBoard)[1]);
        //System.out.println(h.calculateTotalHeuristic(customBoard2));

    }

}
