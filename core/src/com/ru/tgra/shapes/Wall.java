package com.ru.tgra.shapes;

import java.util.ArrayList;

public class Wall implements GameObject {
	
	public float width;
	public float length;
	public Point3D center;
	Shader shader;
	
	public Wall(Point3D center, float width, float length) {
		this.center = center;
		this.width = width;
		this.length = length;
		shader = new Shader();
	}
	
	public ArrayList<CollisionEdge> getCollisionEdges() {
		return new ArrayList<CollisionEdge>();
	}

	@Override
	public void draw() {
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(center.x, 0, center.z);
		ModelMatrix.main.addScale(width, 1, length);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
	}
	
	
}
