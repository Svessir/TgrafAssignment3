package com.ru.tgra.shapes;

import java.util.ArrayList;

/**
 * Created by Sverrir on 6.10.2016.
 */
public class Cell implements GameObject{
    public boolean left;
    public boolean bottom;
    public static final float width = 1f;
    public static final float length = 1f;
	public  static final float height = 1f;
    
    public Wall leftWall;
    public Wall bottomWall;

    public Cell(boolean left, boolean bottom) {
        this.left = left;
        this.bottom = bottom;
    }
    
    public void draw() {
    	if(left)
    		this.leftWall.draw();
    	if(bottom)
    		this.bottomWall.draw();
    }

    public void addCollisionEdges(ArrayList<CollisionEdge> collisionEdges, float wallPadding) {
        if(left)
            leftWall.addCollisionEdges(collisionEdges, wallPadding);
        if(bottom)
            bottomWall.addCollisionEdges(collisionEdges, wallPadding);
    }
}
