package com.ru.tgra.shapes;

/**
 * Created by Kárii on 9.10.2016.
 */
public class CollisionVertex {
    private Point3D vertex;
    private Vector3D velocity;
    private float radius;
    public final Object caller;

    public CollisionVertex(Point3D vertex, Vector3D velocity, float radius, Object caller){
        this.vertex = vertex;
        this.velocity = velocity;
        this.radius = radius;
        this.caller = caller;
    }


    public Point3D getVertex() {
        return vertex;
    }

    public Vector3D getVelocity() {
        return velocity;
    }
    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public float getRadius() {
        return radius;
    }
}
