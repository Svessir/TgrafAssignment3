package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

/**
 * Created by Kárii on 8.10.2016.
 */
public class Shader {

    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private int positionLoc;
    private int normalLoc;

    private int modelMatrixLoc;
    private int viewMatrixLoc;
    private int projectionMatrixLoc;

    private int eyePosLoc;
    private int matShineLoc;

    private int colorLoc;

    private int globalAmbLoc;
    private int lightPosLoc;
    private int lightColorLoc;
    private int matDifLoc;
    private int matSpecLoc;



    public Shader(){
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        Gdx.gl.glCompileShader(fragmentShaderID);
        System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

        //Gdx.gl.glGetError();
        //Gdx.gl.glGetShaderInfoLog();

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

        //colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

        eyePosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");

        lightPosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");
        globalAmbLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbient");
        lightColorLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor");
        matDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
        matShineLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialShininess");
        matSpecLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");

        Gdx.gl.glUseProgram(renderingProgramID);
    }

    /*public void setColor(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(colorLoc, r, g, b, a);
    }*/
    public void setEyePosition(float x, float y, float z, float w){
        Gdx.gl.glUniform4f(eyePosLoc, x, y, z, w);
    }

    public void setLightPosition(float x, float y, float z, float w){
        Gdx.gl.glUniform4f(lightPosLoc, x, y, z, w);
    }

    public void setMaterialDiffuse(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(matDifLoc, r, g, b, a);
    }

    public void setLightColor(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(lightColorLoc, r, g, b, a);
    }

    public void setMaterialSpecular(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(matSpecLoc, r, g, b, a);
    }

    public void setGlobalAmbient(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(globalAmbLoc, r, g, b, a);
    }

    public void setShininess(float shine){
        Gdx.gl.glUniform1f(matShineLoc, shine);
    }


    public int getVertexPointer(){
        return  positionLoc;
    }

    public int getNormalPointer(){
        return  normalLoc;
    }

    public void setModelMatrix(FloatBuffer matrix){
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    public void setViewMatrix(FloatBuffer matrix){
        Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
    }

    public void setProjectionMatrix(FloatBuffer matrix){
        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
    }
}