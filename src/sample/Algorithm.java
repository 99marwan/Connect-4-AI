package sample;

import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithm {
    class Node{
        int val;
        ArrayList<Node> children = new ArrayList<>();
        Node (int value){
            val = value;
        }
    }
    public void buildTreeWithPreOrder(Node node) {

        if (node == null) {
            return ;
        }
        System.out.print("(" + node.val + ")");

        String newPointer ;

        for (int i = 0; i < node.children.size();i++){
            if((i + 1 == node.children.size()) || i == node.children.size()-1) {
                newPointer = " └──";
                traverseNodes( "", newPointer, node.children.get(i), false);
            }
            else{
                newPointer  = " ├──";
                traverseNodes( "", newPointer, node.children.get(i), true);
            }
        }

    }
    public void traverseNodes(String padding, String pointer, Node node,
                              boolean hasRightSibling) {
        if (node != null) {

            System.out.println();
            System.out.print(padding);
            System.out.print(pointer);
            System.out.print("(" + node.val + ")");



            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append(" │  ");
            } else {
                paddingBuilder.append("    ");
            }

            String newPadding = paddingBuilder.toString();
            String newPointer ;

            for (int i = 0; i < node.children.size();i++){
                if((i + 1 == node.children.size()) || i == node.children.size()-1) {
                    newPointer = " └──";
                    traverseNodes(newPadding, newPointer, node.children.get(i), false);
                }
                else{
                    newPointer  = " ├──";
                    traverseNodes(newPadding, newPointer, node.children.get(i), true);
                }


            }
        }
    }
    public void printTree(Node root) {
        buildTreeWithPreOrder(root);
    }



    GameLogic logic=new GameLogic();

    GameState decide(GameState state,int k){
        //add node
        Node root = new Node(0);
        Pair<GameState, Integer> pair =MINIMIZE(state,k,root);
        printTree(root);
        System.out.println();
        return pair.getKey();
    }

    Pair<GameState, Integer> MAXIMIZE(GameState state,int k,Node root){
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            root.val = val;
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> max = new Pair<GameState, Integer>(null,Integer.MIN_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        int childCount = 0;

        for (GameState child : children) {
            int temp =k;
            temp--;
            root.children.add(new Node(0));
            Pair<GameState, Integer> util = MINIMIZE(child, temp,root.children.get(childCount));
            childCount++;

            if (util.getValue() > max.getValue()) {
                max = new Pair<>(child, util.getValue());
            }
        }
        root.val = max.getValue();
        return max;
    }

    Pair<GameState, Integer> MINIMIZE(GameState state,int k,Node root){
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            root.val = val;
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> min = new Pair<GameState, Integer>(null,Integer.MAX_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        int childCount = 0;

        for (GameState child : children) {
            int temp =k;
            temp--;
            root.children.add(new Node(0));
            Pair<GameState, Integer> util = MAXIMIZE(child, temp,root.children.get(childCount));
            childCount++;
            if (util.getValue() < min.getValue()) {
                min = new Pair<>(child, util.getValue());
            }
        }
        root.val = min.getValue();
        return min;
    }


    //===================================================================================

    GameState decidePruning(GameState state,int k){
        //add node
        Node root = new Node(0);
        Pair<GameState, Integer> pair =MINIMIZEPruning(state,k,Integer.MIN_VALUE,Integer.MAX_VALUE,root);
        printTree(root);
        System.out.println();
        return pair.getKey();
    }

    Pair<GameState, Integer> MAXIMIZEPruning(GameState state,int k,int alpha,int beta,Node root){
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            root.val = val;
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> max = new Pair<GameState, Integer>(null,Integer.MIN_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        int childCount = 0;

        for (GameState child : children) {
            int temp =k;
            temp--;
            root.children.add(new Node(0));
            Pair<GameState, Integer> util = MINIMIZEPruning(child, temp,alpha,beta,root.children.get(childCount));
            childCount++;
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
        root.val = max.getValue();
        return max;
    }

    Pair<GameState, Integer> MINIMIZEPruning(GameState state,int k,int alpha,int beta,Node root){
        if(k<=0){
            int val = logic.calculateHeuristicValue(state);
            root.val = val;
            return new Pair<GameState, Integer>(null,val);
        }
        Pair<GameState, Integer> min = new Pair<GameState, Integer>(null,Integer.MAX_VALUE);
        ArrayList<GameState> children = logic.Gen_Children(state);

        int childCount = 0;

        for (GameState child : children) {
            int temp =k;
            temp--;
            root.children.add(new Node(0));
            Pair<GameState, Integer> util = MAXIMIZEPruning(child, temp,alpha,beta,root.children.get(childCount));
            childCount++;
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
        root.val = min.getValue();
        return min;
    }
}
