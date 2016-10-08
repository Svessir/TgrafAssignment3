package com.ru.tgra.shapes;

import com.badlogic.gdx.math.Vector3;

public class CollisionEdge {
	public final Point3D point1;
	public final Point3D point2;
	public final Vector3 normal;
	
	public CollisionEdge(Point3D point1, Point3D point2) {
		this.point1 = point1;
		this.point2 = point2;
		this.normal = new Vector3(-(this.point2.y - this.point1.y), this.point2.x - this.point1.x, 0.0f);
		this.normal.nor();
	}
	
	public Collision getCollision(Vector3 vertex, Vector3 velocity, float deltatime) {
		float time_hit = normal.dot(new Vector3(point1.x - vertex.x, point1.y - vertex.y, 0)) 
				/ normal.dot(velocity);
		
		float col_x = vertex.x + velocity.x * time_hit;
		float col_y = vertex.y + velocity.y * time_hit;
		
		if(Float.isNaN(time_hit) || Float.isInfinite(time_hit) || time_hit < 0 || time_hit > deltatime ||
				col_x < Math.min(point1.x, point2.x) || col_x > Math.max(point1.x, point2.x) || 
				col_y < Math.min(point1.y, point2.y) || col_y > Math.max(point1.y, point2.y))
			return null;
		
		return null;
	}
}