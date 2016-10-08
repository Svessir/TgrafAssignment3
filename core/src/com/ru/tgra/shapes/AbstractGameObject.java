package com.ru.tgra.shapes;

public abstract class AbstractGameObject implements GameObject {
	public Vector3D velocity;
	public Point3D position;
	public float objectRatdius;
	
	public void updateVelocityAfterCollision() {
	}
}
