package com.ru.tgra.shapes;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Collections;
public class GameRunner extends ApplicationAdapter implements InputProcessor {

	private Shader shader;
	private Camera cam;
	private Camera orthoCam;
	private MovingObject movingObject;
	private MovingObject light;

	int size = 10;

	Maze maze;

	private static GameRunner instance = new GameRunner();

	private GameRunner() {
	}

	public static GameRunner getInstance() {
		return instance;
	}

	@Override
	public void create () {

		shader = new Shader();
		Gdx.input.setInputProcessor(this);


		//COLOR IS SET HERE
		//shader.setColor(0.7f, 0.2f, 0, 1);

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		cam.Look3D(new Point3D(5, 0, 2), new Point3D(0,0,0), new Vector3D(0,1,0));
		cam.PerspctiveProjection3D(90, 2, 0.001f, 100);
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
		maze = new Maze(size);
		orthoCam = new Camera();
		orthoCam.OrthographicProjection3D(-size, size, -size, size, 0.4f, 1000);
		movingObject = new MovingObject(5, 0, 5, 0.3f);
		light = new MovingObject(10, 10, 10, 0.1f);


	}

	private void update()
	{
		float deltatime = Gdx.graphics.getDeltaTime();
		cam.update(deltatime);
		movingObject.update(deltatime);
	}

	private void display()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		shader.setLightPosition(12.0f, 7.0f, 10.0f, 1.0f);
		shader.setLightColor(0.0f, 1.0f, 1.0f, 1.0f);

		shader.setGlobalAmbient(0.1f, 0.1f, 0.1f, 1.0f);

		shader.setMaterialDiffuse(0.9f, 0.3f, 0.1f, 1.0f);
		shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setShininess(30.0f);
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
		ModelMatrix.main.loadIdentityMatrix();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
		shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
		light.draw();
		maze.draw();
		movingObject.draw();
		Gdx.gl.glViewport(-400, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		orthoCam.Look3D(new Point3D(7.0f, 40.0f, 7.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0, 0, -1));
		//orthoCam.Look3D(new Point3D(cam.eye.x, 40.0f, cam.eye.z), cam.eye, new Vector3D(0, 0, -1));
		shader.setViewMatrix(orthoCam.getViewMatrix());
		shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
		shader.setEyePosition(orthoCam.eye.x, orthoCam.eye.y, orthoCam.eye.z, 1.0f);
		maze.draw();
		shader.setMaterialDiffuse(1, 1, 1, 1.0f);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
		ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
	}

	public Collision getCollision(CollisionVertex vertex){

		Collision latestCollision = null;
		do {
			ArrayList<CollisionEdge> collisionEdges = maze.getCollisionEdges();
			ArrayList<Collision> collisions = new ArrayList<Collision>();
			for (CollisionEdge edge : collisionEdges) {
				Collision collision = edge.getCollision(vertex);
				if (collision != null)
					collisions.add(collision);
			}
			//System.out.println(collisions.size());
			Collections.sort(collisions, Collision.tHitComparator);
			if(!collisions.isEmpty()) {
				latestCollision = collisions.get(0);
				vertex.setVelocity(latestCollision.newTravelVector);
			}
			else break;
		}while(true);
		return latestCollision;
	}

	@Override
	public void render () {
		update();
		display();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}