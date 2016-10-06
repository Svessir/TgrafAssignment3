package com.ru.tgra.shapes;

public class Maze implements GameObject {
	
	private final Cell[][] cells;
	
	public Maze(int size) {
		MapGenerator mapGenerator = new MapGenerator();
		cells = mapGenerator.generateNewMap(size);
	}
	
	public void draw() {
		ModelMatrix.main.pushMatrix();
		for(int x = 0; x < cells.length; x++) {
			ModelMatrix.main.pushMatrix();
			for(int z = 0; z < cells[0].length; z++) {
				cells[x][z].draw();
				ModelMatrix.main.addTranslation(0, 0, Cell.height);
			}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.addTranslation(Cell.width, 0, 0);
		}
		ModelMatrix.main.popMatrix();
	}
}
