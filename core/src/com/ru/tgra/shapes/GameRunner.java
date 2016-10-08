package com.ru.tgra.shapes;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;

public class GameRunner extends ApplicationAdapter implements InputProcessor {

	private FloatBuffer matrixBuffer;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;
	private int normalLoc;

	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;
	private Camera cam;
	private Camera orthoCam;

	int size = 20;
	
	Maze maze;

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();

		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);

		BoxGraphic.create(positionLoc, normalLoc);
		SphereGraphic.create(positionLoc, normalLoc);
		SincGraphic.create(positionLoc);
		CoordFrameGraphic.create(positionLoc);

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(modelMatrixLoc);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		//OrthographicProjection3D(-2, 2, -2, 2, 1, 100);
		cam = new Camera(viewMatrixLoc, projectionMatrixLoc);
		cam.Look3D(new Point3D((size/2), 0f, (size/2)), new Point3D(0, 0.5f ,0), new Vector3D(0, 1, 0));
		cam.PerspctiveProjection3D(90, 1, 0.01f, 100);
		cam.setShaderMatrices();
		maze = new Maze(size);
		orthoCam = new Camera(viewMatrixLoc, projectionMatrixLoc);
		orthoCam.OrthographicProjection3D(-size, size, -size, size, 0.4f, 1000);
		
		
	}

	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();


		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.yawIgnoreY(60.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.yawIgnoreY(-60.0f * deltaTime);
		}
		/*if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.pitch(90.0f * deltaTime);
		}*/
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-1.1f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(1.1f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.slideIgnoreY(0, 0, -1.1f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.slideIgnoreY(0, 0, 1.1f * deltaTime);
		}
		//do all updates to the game
	}


	private void display()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glUniform4f(colorLoc, 0.9f, 0.3f, 0.1f, 1.0f);
		cam.setShaderMatrices();
		ModelMatrix.main.loadIdentityMatrix();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setShaderMatrices();
		maze.draw();
		Gdx.gl.glViewport(-400, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		orthoCam.Look3D(new Point3D(7.0f, 40.0f, 7.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0, 0, -1));
		//orthoCam.Look3D(new Point3D(cam.eye.x, 40.0f, cam.eye.z), cam.eye, new Vector3D(0, 0, -1));
		orthoCam.setShaderMatrices();
		maze.draw();
		Gdx.gl.glUniform4f(colorLoc, 1, 1, 1, 1.0f);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
		ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
		ModelMatrix.main.setShaderMatrix();
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void render () {

		//put the code inside the update and display methods, depending on the nature of the code
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