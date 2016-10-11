package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;

import java.awt.*;

/**
 * Created by Kárii on 8.10.2016.
 */
public class MovingObject implements GameObject {
    private Point3D pos = new Point3D();
    private float diameter;
    private float angle = 0;
    private float rotationSpeed = 90f;
    private Shader shader = new Shader();
    public MovingObject(int x, int y, int z, float diameter){
        pos.x = x;
        pos.y = y;
        pos.z = z;
        this.diameter = diameter;
    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(pos.x, pos.y, pos.z);
        ModelMatrix.main.addScale(diameter, diameter, diameter);
        ModelMatrix.main.addRotationZ(angle);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere();
        //BoxGraphic.drawSolidCube();
        ModelMatrix.main.popMatrix();
    }

    public void update(float deltatime){
        angle += rotationSpeed * deltatime;
    }
}
