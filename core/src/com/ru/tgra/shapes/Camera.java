package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Kari on 22.9.2016.
 */
public class Camera extends AbstractGameObject {
    private Vector3D n;
    private Vector3D u;
    private Vector3D v;
    public Point3D eye;
    
    private boolean orthographic;

    private float left;
    private float right;
    private float bottom;
    private float top;
    private float near;
    private float far;

    private final float speed = 1.1f;
    private final float rotationPerSecond = 60f;
    private final float cameraRadius = 0.1f;

    private FloatBuffer matrixBuffer;

    public Camera(){
        matrixBuffer = BufferUtils.newFloatBuffer(16);

        objectRadius = 0.1f;
        eye = new Point3D();
        u = new Vector3D(1,0,0);
        v = new Vector3D(0,1,0);
        n = new Vector3D(0,0,1);

        orthographic = true;

        this.left = -1;
        this.right = 1;
        this.bottom = -1;
        this.top = 1;
        this.near = -1;
        this.far = 1;
    }

    public void Look3D(Point3D eye, Point3D center, Vector3D up) {
        this.eye = eye;
        n = Vector3D.difference(eye, center);
        u = up.cross(n);
        n.normalize();
        u.normalize();
        v = n.cross(u);
    }


    public void slide(float delU, float delV, float delN){
        eye.x += delU * u.x + delV * v.x + delN * n.x;
        eye.y += delU * u.y + delV * v.y + delN * n.y;
        eye.z += delU * u.z + delV * v.z + delN * n.z;
    }

    public void roll(float angle){
        float radians = angle *(float)Math.PI/180.0f;
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);
        Vector3D t = new Vector3D(u.x, u.y, u.z);

        u.x = t.x * c - v.x * s;
        u.y = t.y * c - v.y * s;
        u.z = t.z * c - v.z * s;
        v.x = t.x * c + v.x * s;
        v.y = t.y * c + v.y * s;
        v.z = t.z * c + v.z * s;
    }

    public void slideIgnoreY(float delU, float delV, float delN){
        eye.x += delU * u.x + delV * v.x + delN * n.x;
        //eye.y += delU * u.y + delV * v.y + delN * n.y;
        eye.z += delU * u.z + delV * v.z + delN * n.z;
    }

    public void yaw(float angle){
        float c = (float)Math.cos((double)angle * Math.PI / 180.0);
        float s = (float)Math.sin((double)angle * Math.PI / 180.0);
        float tmp ;

        tmp = c * u.x - s * n.x;
        n.x = s * u.x + c * n.x;
        u.x = tmp;

        tmp = c * u.y - s * n.y;
        n.y = s * u.y + c * n.y;
        u.y = tmp;

        tmp = c * u.z - s * n.z;
        n.z = s * u.z + c * n.z;
        u.z = tmp;
    }

    public void yawIgnoreY(float angle){
        float c = (float)Math.cos((double)angle * Math.PI / 180.0);
        float s = (float)Math.sin((double)angle * Math.PI / 180.0);
        float tmp ;

        tmp = c * u.x + s * u.z;
        u.z = -s * u.x + c * u.z;
        u.x = tmp;

        tmp = c * v.x + s * v.z;
        v.z = -s * v.x + c * v.z;
        v.x = tmp;

        tmp = c * n.x + s * n.z;
        n.z = -s * n.x + c * n.z;
        n.x = tmp;
    }

    public void pitch(float angle) {
        float c = (float) Math.cos((double) angle * Math.PI / 180.0);
        float s = (float) Math.sin((double) angle * Math.PI / 180.0);

        float tmp;

        tmp = v.x * c + n.x * s;
        n.x = v.x * (-s) + n.x * c;
        v.x = tmp;

        tmp = v.y * c + n.y * s;
        n.y = v.y * (-s) + n.y * c;
        v.y = tmp;

        tmp = v.z * c + n.z * s;
        n.z = v.z * (-s) + n.z * c;
        v.z = tmp;
    }
    
    public void OrthographicProjection3D(float left, float right, float bottom, float top, float near, float far) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
        orthographic = true;
    }

    public void PerspctiveProjection3D(float fovy, float ratio, float near, float far) {
        this.top = (float) (near * Math.tan((fovy/2.0) * (Math.PI/180.0)));
        this.bottom = -top;
        this.right = top * ratio;
        this.left = -right;
        this.near = near;
        this.far = far;

        orthographic = false;
    }

    public FloatBuffer getViewMatrix(){
        float[] pm = new float[16];

        Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);

        pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
        pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
        pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;


        matrixBuffer = BufferUtils.newFloatBuffer(16);
        matrixBuffer.put(pm);
        matrixBuffer.rewind();

        return matrixBuffer;
    }

    public FloatBuffer getProjectionMatrix(){
        float[] pm = new float[16];

        if(orthographic){
            pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
            pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
            pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f / (near - far); pm[14] = (near + far) / (near - far);
            pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;
        }else {
            pm[0] = (2.0f * near) / (right - left); pm[4] = 0.0f; pm[8] = (right + left) / (right - left); pm[12] = 0.0f;
            pm[1] = 0.0f; pm[5] = (2.0f * near) / (top - bottom); pm[9] = (top + bottom) / (top - bottom); pm[13] = 0.0f;
            pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -(far + near) / (far - near); pm[14] = -(2.0f* near * far) / (far - near);
            pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;


        }

        matrixBuffer = BufferUtils.newFloatBuffer(16);
        matrixBuffer.put(pm);
        matrixBuffer.rewind();

        return matrixBuffer;
    }

    public void update(float deltatime) {
    	input(deltatime);
        //System.out.println("Velocity = " + velocity.x + " " + velocity.y + " " + velocity.z);
        updateVelocityAfterCollision(new CollisionVertex(eye, velocity, objectRadius), deltatime);
    	slide(velocity.x * deltatime, velocity.y * deltatime, velocity.z * deltatime);
    }
    
    /*private void input(float deltaTime) {
    	velocity = new Vector3D(0,0,0);
    	
    	if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			yawIgnoreY(rotationPerSecond * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			yawIgnoreY(-rotationPerSecond * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			velocity.x -= speed *deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			velocity.x += speed *deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			velocity.z -= speed *deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			velocity.z += speed *deltaTime;
		}
    }*/

    private void input(float deltaTime) {
        velocity = new Vector3D(0,0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            yawIgnoreY(rotationPerSecond * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            yawIgnoreY(-rotationPerSecond * deltaTime);
        }
        /*if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pitch(rotationPerSecond * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pitch(-rotationPerSecond * deltaTime);
        }*/
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x -= speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.z -= speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.z += speed;
        }
    }

	@Override
	public void draw() {
	}

}
