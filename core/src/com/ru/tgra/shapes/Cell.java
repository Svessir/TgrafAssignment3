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
    
    public Cell() {
        left = true;
        bottom = true;
    }

    public Cell(boolean left, boolean bottom) {
        this.left = left;
        this.bottom = bottom;
    }
    
    public void draw() {
    	/*float translationX = width / 2.0f;
    	float translationZ = height / 2.0f;
    	
    	if (left) {
	    	ModelMatrix.main.pushMatrix();
	    	ModelMatrix.main.addTranslation(-translationX, 0, 0);
	    	ModelMatrix.main.addScale(0.2f, 1, 1);
	    	ModelMatrix.main.setShaderMatrix();
	    	BoxGraphic.drawSolidCube();
	    	ModelMatrix.main.popMatrix();
    	}
    	
    	if(bottom) {
	    	ModelMatrix.main.pushMatrix();
	    	ModelMatrix.main.addTranslation(0, 0, -translationZ);
	    	ModelMatrix.main.addScale(1, 1, 0.2f);
	    	ModelMatrix.main.setShaderMatrix();
	    	BoxGraphic.drawSolidCube();
	    	ModelMatrix.main.popMatrix();
    	}*/
    	
    	if(left)
    		this.leftWall.draw();
    	if(bottom)
    		this.bottomWall.draw();
    }

    public void addCollisionEdges(ArrayList<CollisionEdge> collisionEdges) {
        if(left)
            leftWall.addCollisionEdges(collisionEdges);
        if(bottom)
            bottomWall.addCollisionEdges(collisionEdges);
    }
}
