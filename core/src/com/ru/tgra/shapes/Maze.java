package com.ru.tgra.shapes;

import java.util.ArrayList;

public class Maze implements GameObject {
	
	private final Cell[][] cells;
	
	public Maze(int size) {
		MapGenerator mapGenerator = new MapGenerator();
		cells = mapGenerator.generateNewMap(size);
	}
	
	public void draw() {
		/*ModelMatrix.main.pushMatrix();
		for(int x = 0; x < cells.length; x++) {
			ModelMatrix.main.pushMatrix();
			for(int z = 0; z < cells[0].length; z++) {
				cells[x][z].draw();
				ModelMatrix.main.addTranslation(0, 0, Cell.length);
			}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.addTranslation(Cell.width, 0, 0);
		}
		ModelMatrix.main.popMatrix();*/
		
		for(int x = 0; x < cells.length; x++) {
			for(int z = 0; z < cells[0].length; z++)
				cells[x][z].draw();
		}
		
		drawFloor();
	}

	private void drawFloor(){
		float x = cells[0].length * Cell.length / 2;
		float z = cells[0].length * Cell.width / 2;
		ModelMatrix.main.addTranslation(x - 1, -0.5f, z - 1);
		ModelMatrix.main.addScale(Cell.length * cells.length - 1, 0.2f, Cell.width * cells.length - 1);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.setShaderMatrix();
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
	}
}
