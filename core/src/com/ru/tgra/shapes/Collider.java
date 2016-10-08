package com.ru.tgra.shapes;

public class Collider {
	public final Point3D position;
	public final Vector3D velocity;
	public final float radius;
	
	public Collider(Point3D position, Vector3D velocity, float radius) {
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
	}
}
