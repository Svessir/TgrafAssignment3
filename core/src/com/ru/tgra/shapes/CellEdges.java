package com.ru.tgra.shapes;

import javax.print.attribute.SetOfIntegerSyntax;
import java.awt.*;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Kárii on 4.10.2016.
 */


public class CellEdges {
    public boolean left;
    public boolean bottom;

    private class NoMoveException extends Exception {
    }

    public CellEdges() {
        left = true;
        bottom = true;
    }

    public CellEdges(boolean left, boolean bottom) {
        this.left = left;
        this.bottom = bottom;
    }


    public CellEdges[][] generateArray(int size) {
        CellEdges[][] edges = new CellEdges[size][size];
        boolean[][] visited = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    edges[i][j] = new CellEdges(false, false);
                } else if (i == size - 1) {
                    edges[i][j] = new CellEdges(true, false);
                } else if (j == size - 1) {
                    edges[i][j] = new CellEdges(false, true);
                } else {
                    edges[i][j] = new CellEdges();
                }
            }

        }
        findPath(edges, size, visited);
        return edges;
    }

    public Point getStartingPosition(int size) {
        Random rand = new Random();
        Point position = new Point(rand.nextInt(size-2) , rand.nextInt(size-2));
        if (position.getX() < 0) {
            position.x *= -1;
        }
        if (position.y < 0) {
            position.y *= -1;
        }
        return position;
    }

    private int getRandomNumber() {
        Random rand = new Random();
        int number = rand.nextInt(4);
        if (number < 0) {
            number *= -1;
        }
        return number;
    }

    private Point getNextMove(Point pos, boolean[][] cell, int dir, CellEdges[][] edges, int size) {
        if (dir == 0 && checkUp(pos, size) && !cell[pos.x][pos.y+1]) {
            pos.y += 1;
            edges[pos.x][pos.y].bottom = false;
            cell[pos.x][pos.y] = true;
        } else if (dir == 1 && checkRight(pos, size)&& !cell[pos.x+1][pos.y]) {
            pos.x += 1;
            edges[pos.x][pos.y].left = false;
            cell[pos.x][pos.y] = true;
        } else if (dir == 2 && checkDown(pos)&& !cell[pos.x][pos.y-1]) {
            edges[pos.x][pos.y].bottom = false;
            pos.y -= 1;
            cell[pos.x][pos.y] = true;
        } else if (dir == 3 && checkLeft(pos)&& !cell[pos.x-1][pos.y]) {
            edges[pos.x][pos.y].left = false;
            pos.x -= 1;
            cell[pos.x][pos.y] = true;
        }
        return pos;
    }

    private boolean checkUp(Point pos, int size) {
        return pos.y < size - 3;
    }

    private boolean checkDown(Point pos) {
        return pos.y > 0;
    }

    private boolean checkRight(Point pos, int size) {
        return pos.x < size - 3;
    }

    private boolean checkLeft(Point pos) {
        return pos.x > 0;
    }

    public boolean nowhereToGo(Point pos, boolean[][] cell, int size) {
        if (checkUp(pos, size)) {
            if (!cell[pos.x][pos.y + 1]) return true;
        }
        if (checkRight(pos, size)) {
            if (!cell[pos.x + 1][pos.y]) return true;
        }
        if (checkDown(pos)) {
            if (!cell[pos.x][pos.y - 1]) return true;
        }
        if (checkLeft(pos)) {
            if (!cell[pos.x - 1][pos.y]) return true;
        }
        return false;
    }


    public void findPath(CellEdges[][] edges, int size, boolean[][] cell) {
        Point position = getStartingPosition(size);
        cell[position.x][position.y] = true;
        Stack<Point> edgesStack = new Stack<Point>();
        int number;
        int i = 0;
        do {
            number = getRandomNumber();
            edgesStack.push(position);
            Point nextPos = getNextMove(position, cell, number, edges, size);
            if(!nowhereToGo(position, cell, size) || nextPos == new Point(0,0)) {
                position = edgesStack.pop();
            }
            i++;
        } while (i < 1500);
    }


    public void curvePath(CellEdges[][] cells, int size, boolean[][] visited) {
        Point position = getStartingPosition(size);
        visited[position.x][position.y] = true;
        Stack<Point> positionStack = new Stack<Point>();

        do {
            positionStack.push(position);

        }while (!positionStack.empty());
    }
}
