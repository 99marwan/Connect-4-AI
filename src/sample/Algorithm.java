package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class Algorithm {

    GameLogic logic=new GameLogic();

    GameState decide(GameState state,int k){
        Pair<GameState, Integer> pair =MINIMIZE(state,k);
        return pair.getKey();
    }

    Pair<GameState, Integer> MAXIMIZE(GameState state,int k){
        System.out.println(k);
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> max = new Pair<GameState, Integer>(null,Integer.MIN_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        for (GameState child : children) {
            int temp =k;
            temp--;
            Pair<GameState, Integer> util = MINIMIZE(child, temp);
            if (util.getValue() > max.getValue()) {
                max = new Pair<>(child, util.getValue());
            }
        }
        return max;
    }

    Pair<GameState, Integer> MINIMIZE(GameState state,int k){
        System.out.println(k);
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> min = new Pair<GameState, Integer>(null,Integer.MAX_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        for (GameState child : children) {
            int temp =k;
            temp--;
            Pair<GameState, Integer> util = MAXIMIZE(child, temp);
            if (util.getValue() < min.getValue()) {
                min = new Pair<>(child, util.getValue());
            }
        }
        return min;
    }


    //===================================================================================

    GameState decidePruning(GameState state,int k){
        Pair<GameState, Integer> pair =MINIMIZEPruning(state,k,Integer.MIN_VALUE,Integer.MAX_VALUE);
        return pair.getKey();
    }

    Pair<GameState, Integer> MAXIMIZEPruning(GameState state,int k,int alpha,int beta){
        System.out.println(k);
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> max = new Pair<GameState, Integer>(null,Integer.MIN_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        for (GameState child : children) {
            int temp =k;
            temp--;
            Pair<GameState, Integer> util = MINIMIZEPruning(child, temp,alpha,beta);
            if (util.getValue() > max.getValue()) {
                max = new Pair<>(child, util.getValue());
            }

            if(max.getValue() >=beta){
                break;
            }

            if(max.getValue() > alpha){
                alpha =max.getValue();
            }
        }
        return max;
    }

    Pair<GameState, Integer> MINIMIZEPruning(GameState state,int k,int alpha,int beta){
        System.out.println(k);
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> min = new Pair<GameState, Integer>(null,Integer.MAX_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        for (GameState child : children) {
            int temp =k;
            temp--;
            Pair<GameState, Integer> util = MAXIMIZEPruning(child, temp,alpha,beta);
            if (util.getValue() < min.getValue()) {
                min = new Pair<>(child, util.getValue());
            }

            if(min.getValue() <=alpha){
                break;
            }
            if(min.getValue() < beta){
                beta = min.getValue();
            }
        }
        return min;
    }
}
