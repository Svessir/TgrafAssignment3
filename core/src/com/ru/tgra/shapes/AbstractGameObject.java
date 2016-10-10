package com.ru.tgra.shapes;

public abstract class AbstractGameObject implements GameObject {
	public Vector3D velocity;
	public float objectRadius;
	
	public void updateVelocityAfterCollision(CollisionVertex vertex) {
		Collision collision = GameRunner.getInstance().getCollision(vertex);
		if(collision != null)
			velocity = collision.newTravelVector;
			//System.out.println("Abstract " + velocity.x + " " + velocity.y + " " + velocity.z);
	}
}
