package com.ru.tgra.shapes;

import java.awt.*;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Kárii on 4.10.2016.
 */
public class MapGenerator {
    private class NoMoveException extends Exception {
    }

    private CellEdges[][] cells;
    private boolean[][] visited;
    private Stack<Point> positionStack;
    private Point currentPos;

    public CellEdges[][] generateNewMap(int dimension) {
        int size = dimension + 2;
        cells = new CellEdges[size][size];
        visited = new boolean[size][size];
        positionStack = new Stack<Point>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 2 && j == size - 2) {
                    cells[i][j] = new CellEdges(false, false);
                } else if (i == size - 2) {
                    cells[i][j] = new CellEdges(true, false);
                } else if (j == size - 2) {
                    cells[i][j] = new CellEdges(false, true);
                } else {
                    cells[i][j] = new CellEdges();
                }
            }
        }
        curvePath();
        /*while(!allVisited(dimension))
        {
            curvePath();
        }*/
        return cells;
    }

    private boolean allVisited(int dimension){
        for(int i = 0; i < dimension - 2; i++){
            for(int j = 0; j < dimension - 2; j++){
                if(!visited[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private Point getStartingPosition() {
        int max = cells.length - 2;
        Random rand = new Random();
        Point position = new Point(rand.nextInt(max), rand.nextInt(max));
        return position;
    }

    private void curvePath() {
        Point pos = getStartingPosition();
        currentPos = pos;
        int i = 0;
        System.out.println(currentPos);
        do{
            try {
                if(!isVisited(pos)){
                    positionStack.push(pos);
                    System.out.println("Position being push : " + pos);
                }
                markAsVisited(currentPos);

                currentPos = getNextMoveAndBreakDownEdge(currentPos);
            }
            catch (NoMoveException mex) {
                System.out.println(currentPos);
                System.out.println("Pop : " + positionStack.peek()  + "   size of stack = " + positionStack.size());
                Point tmp = positionStack.pop();
                System.out.println("After pop : " + tmp + "   size of stack = " + positionStack.size());
                currentPos = tmp;
            }
            i++;
        }while (!positionStack.empty());

    }

    private void markAsVisited(Point pos) {
        visited[pos.x][pos.y] = true;
    }

    private boolean isVisited(Point pos) {
        return visited[pos.x][pos.y];
    }

    private Point getNextMoveAndBreakDownEdge(Point pos) throws NoMoveException{
        Random rand = new Random();
        int direction;
        while(canMove(pos)) {
            direction = rand.nextInt(4);
            if (direction == 0) {
                if (goUp(pos) && !isVisited(new Point(pos.x, pos.y + 1))) {
                    pos.y += 1;
                    cells[pos.x][pos.y].bottom = false;
                    System.out.println("UP");
                    return pos;
                }
            } else if (direction == 1) {
                if (goRight(pos) && !isVisited(new Point(pos.x + 1, pos.y))) {
                    pos.x += 1;
                    cells[pos.x][pos.y].left = false;
                    System.out.println("Right");
                    return pos;
                }
            } else if (direction == 2) {
                if (goDown(pos) && !isVisited(new Point(pos.x, pos.y - 1))) {
                    cells[pos.x][pos.y].bottom = false;
                    pos.y -= 1;
                    System.out.println("Down");
                    return pos;
                }
            } else if (direction == 3) {
                if (goLeft(pos) && !isVisited(new Point(pos.x - 1, pos.y))) {
                    cells[pos.x][pos.y].left = false;
                    pos.x -= 1;
                    System.out.println("Left");
                    return pos;
                }
            }
        }
        throw new NoMoveException();
    }

    private boolean goLeft(Point pos) {
        return pos.x > 1;
    }

    private boolean goDown(Point pos) {
        return pos.y > 1;
    }

    private boolean goRight(Point pos) {
        return pos.x < cells.length - 3;
    }

    private boolean goUp(Point pos) {
        return pos.y < cells.length - 3;
    }

    private boolean canMove(Point pos){
        int cnt = 0;
        if(goUp(pos)){
            if(!visited[pos.x][pos.y+1]){
                cnt++;
            }

        }
        if(goRight(pos)){
            if(!visited[pos.x+1][pos.y]){
                cnt++;
            }
        }
        if(goDown(pos)){
            if(!visited[pos.x][pos.y-1]){
                cnt++;
            }
        }
        if(goLeft(pos)){
            if(!visited[pos.x-1][pos.y]){
                cnt++;
            }
        }
        System.out.println(cnt);
        if(cnt > 0){
            return true;
        }
        return false;
    }
}
