package sample;

import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithm {
    class Node{
        int val;
        Node[] children = new Node[7];
        Node (int value){
            val = value;
        }
    }
    public String buildTreeWithPreOrder(Node node) {

        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(" + node.val + ")");



        String newPointer ;

        for (int i = 0; i < 7;i++){
            if((i+1 < 7 && node.children[i+1] == null) || i == 6) {
                newPointer = " └──";
                traverseNodes(sb, "", newPointer, node.children[i], false);
            }
            else{
                newPointer  = " ├──";
                traverseNodes(sb, "", newPointer, node.children[i], true);
            }


        }

        return sb.toString();
    }
    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append("(" + node.val + ")");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append(" │  ");
            } else {
                paddingBuilder.append("    ");
            }

            String newPadding = paddingBuilder.toString();
            String newPointer ;

            for (int i = 0; i < 7;i++){
                if((i+1 < 7 && node.children[i+1] == null) || i == 6) {
                    newPointer = " └──";
                    traverseNodes(sb, newPadding, newPointer, node.children[i], false);
                }
                else{
                    newPointer  = " ├──";
                    traverseNodes(sb, newPadding, newPointer, node.children[i], true);
                }


            }
        }
    }
    public void printTree(Node root) {
        System.out.print(buildTreeWithPreOrder(root));
    }
//    public void buildTreeWithPreOrder(StringBuilder sb, String padding, String pointer, Node node) {
//        if (node != null) {
//            sb.append(padding);
//            sb.append(pointer);
//            sb.append("(" + node.val + ")");
//            sb.append("\n");
//
//            StringBuilder paddingBuilder = new StringBuilder(padding);
//            paddingBuilder.append("│  ");
//
//            String newPadding = paddingBuilder.toString();
//            String newPointer ;
//
//            for (int i = 0; i < 7;i++){
//                if((i+1 < 7 && node.children[i+1] == null) || i == 6)
//                    newPointer =  "└──";
//                else
//                    newPointer  = "├──";
//                buildTreeWithPreOrder(sb, newPadding, newPointer, node.children[i]);
//            }
//        }
//    }
//    public void printTree(Node root) {
//        StringBuilder sb = new StringBuilder();
//        buildTreeWithPreOrder(sb, "", "",root);
//        System.out.print(sb.toString());
//    }


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
            root.children[childCount] = new Node(0);
            Pair<GameState, Integer> util = MINIMIZE(child, temp,root.children[childCount++]);

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
            root.children[childCount] = new Node(0);
            Pair<GameState, Integer> util = MAXIMIZE(child, temp,root.children[childCount++]);
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
            root.children[childCount] = new Node(0);
            Pair<GameState, Integer> util = MINIMIZEPruning(child, temp,alpha,beta,root.children[childCount++]);
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
            root.children[childCount] = new Node(0);
            Pair<GameState, Integer> util = MAXIMIZEPruning(child, temp,alpha,beta,root.children[childCount++]);
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
