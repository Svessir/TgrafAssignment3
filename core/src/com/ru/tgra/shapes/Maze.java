package com.ru.tgra.shapes;


import java.util.ArrayList;

public class Maze implements GameObject {
	
	private final Cell[][] cells;

	public Maze(int size) {
		MapGenerator mapGenerator = new MapGenerator();
		cells = mapGenerator.generateNewMap(size);
	}
	
	public void draw() {
		for(int x = 0; x < cells.length; x++) {
			for(int z = 0; z < cells[0].length; z++)
				cells[x][z].draw();
		}
	}

	public ArrayList<CollisionEdge> getCollisionEdges(float wallPadding){
		ArrayList<CollisionEdge> edges = new ArrayList<CollisionEdge>();
		for(int x = 0; x < cells.length; x++){
			for(int z = 0; z < cells[0].length; z++){
				cells[x][z].addCollisionEdges(edges, wallPadding);
			}
		}
		return edges;
	}


}
