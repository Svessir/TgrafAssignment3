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
	
	public void addCollisionEdges(ArrayList<CollisionEdge> collisionEdges) {
		float halfWidth = width / 2.0f;
		float halfLength = length / 2.0f;
		Point3D leftTop = new Point3D(center.x - halfWidth, 0, center.z + halfLength);
		Point3D leftBottom = new Point3D(center.x - halfWidth, 0, center.z - halfLength);
		Point3D rightTop = new Point3D(center.x + halfWidth, 0, center.z + halfLength);
		Point3D rightBottom = new Point3D(center.x + halfWidth, 0, center.z - halfLength);
		collisionEdges.add(new CollisionEdge(leftTop, leftBottom));
		collisionEdges.add(new CollisionEdge(leftTop, rightTop));
		collisionEdges.add(new CollisionEdge(leftBottom, rightBottom));
		collisionEdges.add(new CollisionEdge(rightTop, rightBottom));
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
