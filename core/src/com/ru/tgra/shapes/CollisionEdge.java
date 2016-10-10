package com.ru.tgra.shapes;

public class CollisionEdge {
	public final Point3D point1;
	public final Point3D point2;
	public final Vector3D normal;

	public CollisionEdge(Point3D point1, Point3D point2){
		this.point1 = point1;
		this.point2 = point2;
		this.normal = new Vector3D(-(point2.z - point1.z), 0.0f ,(point2.x - point1.x));
		this.normal.normalize();
	}

	public Collision getCollision(CollisionVertex vertex){
		Point3D vertexPoint = vertex.getVertex();
		Vector3D velocityVector = vertex.getVelocity();
		float tHit = normal.dot(new Vector3D(point1.x - vertexPoint.x, 0.0f, point1.z - vertexPoint.z))/ normal.dot(velocityVector);

		float col_x = vertexPoint.x + velocityVector.x * tHit;
		float col_z = vertexPoint.z + velocityVector.z * tHit;

		if(Float.isNaN(tHit) || Float.isInfinite(tHit) || tHit < 0 || tHit > 1
				|| col_x < Math.min(point1.x, point2.x) || col_x > Math.max(point1.x, point2.x)
				|| col_z < Math.min(point1.z, point2.z) || col_z > Math.max(point1.z, point2.z)){
				return null;
		}
		Vector3D reflectionVector = getReflectionVector(velocityVector);
		Vector3D newTravelVector = null;

		System.out.println("vertexrRad: "+ vertex.getRadius());
		System.out.println("colX: "+ col_x);
		System.out.println("colZ: "+ col_z);

		if(velocityVector.x >= 0 && reflectionVector.x < 0){
			System.out.println("col_x - vertex.getRadius()= " + (col_x - vertex.getRadius()));
			newTravelVector = new Vector3D(col_x - vertex.getRadius() - vertexPoint.x, velocityVector.y, velocityVector.z);
		}
		else if(velocityVector.x < 0 && reflectionVector.x >= 0){
			System.out.println("col_x + vertex.getRadius()= " + (col_x + vertex.getRadius()));
			newTravelVector = new Vector3D(col_x + vertex.getRadius() - vertexPoint.x, velocityVector.y, velocityVector.z);
		}
		else if(velocityVector.z >= 0 && reflectionVector.z < 0){
			System.out.println("col_z - vertex.getRadius()= " + (col_z - vertex.getRadius()));
			newTravelVector = new Vector3D(velocityVector.x, velocityVector.y, col_z - vertex.getRadius() - vertexPoint.z);
		}
		else{
			System.out.println("col_z + vertex.getRadius()= " + (col_z + vertex.getRadius()));
			newTravelVector = new Vector3D(velocityVector.x, velocityVector.y, col_z + vertex.getRadius() - vertexPoint.z);
		}

		System.out.println("velocity " + velocityVector + ", newVelocity: " + newTravelVector);
		return new Collision(newTravelVector, tHit);
	}

	private Vector3D getReflectionVector(Vector3D velocity){
		float b = 2 * velocity.dot(normal);
		Vector3D c = new Vector3D(b * normal.x, 0, b * normal.z);
		return new Vector3D(velocity.x - c.x, 0, velocity.z - c.z);
	}
}