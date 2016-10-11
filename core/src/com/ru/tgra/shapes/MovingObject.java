package com.ru.tgra.shapes;

import java.util.ArrayList;

/**
 * Created by Kárii on 8.10.2016.
 */
public class MovingObject extends AbstractGameObject{
    private Point3D pos = new Point3D();
    private float angle = 0;
    private float rotationSpeed = 90f;
    private Shader shader = new Shader();
    private float speed = 2f;
    private boolean wasCollision = false;
    private Vector3D currentVelocity;

    public MovingObject(int x, int y, int z, float diameter){
        pos.x = x;
        pos.y = y;
        pos.z = z;
        objectRadius = diameter;
        velocity = new Vector3D(speed, 0, 0);
    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(pos.x, pos.y, pos.z);
        ModelMatrix.main.addScale(objectRadius, objectRadius, objectRadius);
        ModelMatrix.main.addRotationZ(angle);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere();
        ModelMatrix.main.popMatrix();
    }

    public void update(float deltatime){
        angle += rotationSpeed * deltatime;
        getVelocity();
        System.out.println(velocity);
        velocity.scale(deltatime);
        System.out.println(velocity);
        wasCollision = updateVelocityAfterCollision(new CollisionVertex(pos, velocity, objectRadius, this));
        pos.x += velocity.x;
        pos.z += velocity.z;
    }

    private void getVelocity() {
        if(currentVelocity == null)
            currentVelocity = new Vector3D(0,0,speed);
        if(!wasCollision) {
            velocity = new Vector3D(currentVelocity);
            return;
        }

        currentVelocity = new Vector3D(0,0, -currentVelocity.z);
        velocity = new Vector3D(currentVelocity);
        wasCollision = false;
    }

    public void addCollisionEdges(ArrayList<CollisionEdge> collisionEdges, float padding){
        Point3D leftTop = new Point3D(pos.x - (objectRadius + padding), 0, pos.z + (objectRadius + padding));
        Point3D leftBottom = new Point3D(pos.x - (objectRadius + padding), 0, pos.z - (objectRadius + padding));
        Point3D rightTop = new Point3D(pos.x + (objectRadius + padding), 0, pos.z + (objectRadius + padding));
        Point3D rightBottom = new Point3D(pos.x + (objectRadius+ padding), 0, pos.z - (objectRadius + padding));
        collisionEdges.add(new CollisionEdge(leftTop, leftBottom));
        collisionEdges.add(new CollisionEdge(leftTop, rightTop));
        collisionEdges.add(new CollisionEdge(leftBottom, rightBottom));
        collisionEdges.add(new CollisionEdge(rightTop, rightBottom));
    }
}
