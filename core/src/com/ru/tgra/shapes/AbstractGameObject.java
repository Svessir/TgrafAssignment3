package com.ru.tgra.shapes;

public abstract class AbstractGameObject implements GameObject {
	public Vector3D velocity;
	public float objectRadius;
	
	public boolean updateVelocityAfterCollision(CollisionVertex vertex) {
		Collision collision = GameRunner.getInstance().getCollision(vertex);
		if(collision != null) {
			velocity = collision.newTravelVector;
			return true;
		}
		return false;
	}
}
