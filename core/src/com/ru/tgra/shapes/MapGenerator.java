package com.ru.tgra.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Sverrir on 6.10.2016.
 */
public class MapGenerator {
    private class NoMoveException extends Exception {
    }
    
    private Cell[][] cells;
    private boolean[][] visited;
    private Stack<Point> positionStack;
    private Point currentPos;
    
    private Point[] allMoves = 
    	{
    		new Point(1, 0),
    		new Point(0, 1),
    		new Point(-1, 0),
    		new Point(0, -1)
    	};
    
    public Cell[][] generateNewMap(int dimension) {
        int size = dimension + 1;
        cells = new Cell[size][size];
        visited = new boolean[size][size];
        positionStack = new Stack<Point>();
        
        for(int x = 0; x < size; x++) {
        	for(int z = 0; z < size; z++)
        		cells[x][z] = new Cell(true, true);
        }

        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
            	if(x == size - 1) {
            		markAsVisited(x, z);
            		cells[x][z].bottom = false;
            	}
            	if(z == size - 1) {
            		markAsVisited(x, z);
            		cells[x][z].left = false;
            	}
            }
        }
        curvePath();
        return cells;
    }

    private Point getStartingPosition() {
        int max = cells.length - 2;
        Random rand = new Random();
        Point position = new Point(rand.nextInt(max), rand.nextInt(max));
        return position;
    }

    private void curvePath() {
    	currentPos = getStartingPosition();
    	markAsVisited(currentPos.x, currentPos.y);
    	
    	do {
    		try {
				Point move = getNextMove();
				makeMove(move);
			} catch (NoMoveException mex) {
				if(!positionStack.isEmpty())
					currentPos = positionStack.pop();
				else
					break;
			}
    		
    	}while(true);
    	
    }
    
    private Point getNextMove() throws NoMoveException{
    	Point[] moves = getPossibleMoves();
    	
    	if(moves.length == 0)
    		throw new NoMoveException();
    	
    	Random rand = new Random();
    	int moveIndex = rand.nextInt(moves.length);
    	return moves[moveIndex];
    }
    
    private Point[] getPossibleMoves() {
    	ArrayList<Point> possibleMoves = new ArrayList<Point>();
    	for(int i = 0; i < allMoves.length; i++) {
    		if(isMoveValid(allMoves[i]))
    			possibleMoves.add(allMoves[i]);
    	}
    	
    	Point[] moves = new Point[possibleMoves.size()];
    	
    	for(int i = 0; i < moves.length; i++)
    		moves[i] = possibleMoves.get(i);
    	
    	return moves;
    }
    
    private boolean isMoveValid(Point move) {
    	int x = currentPos.x + move.x;
    	int z = currentPos.y + move.y;
    	
    	if(x >= cells.length || z >= cells[0].length || x < 0 || z < 0)
    		return false;
    	
    	return !visited[x][z];
    }
    
    private void makeMove(Point move) {
    	Point newPos = new Point(currentPos.x + move.x, currentPos.y + move.y);
    	markAsVisited(newPos.x, newPos.y);
    	
    	if(move.equals(allMoves[0]))
    		cells[newPos.x][newPos.y].left = false;
    	else if(move.equals(allMoves[1]))
    		cells[newPos.x][newPos.y].bottom = false;
    	else if(move.equals(allMoves[2]))
    		cells[currentPos.x][currentPos.y].left = false;
		else if(move.equals(allMoves[3]))
			cells[currentPos.x][currentPos.y].bottom = false;
    	
    	positionStack.push(currentPos);
    	currentPos = newPos;
    }
    
    private void markAsVisited(int x, int z) {
        visited[x][z] = true;
    }
}
