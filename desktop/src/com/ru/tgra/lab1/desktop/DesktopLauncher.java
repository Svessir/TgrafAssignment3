package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.shapes.GameRunner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Maze Game"; // or whatever you like
		config.width = 1200;  //experiment with
		config.height = 600;  //the window size
		
		config.x = 150;
		config.y = 50;
		//config.fullscreen = true;

		new LwjglApplication(GameRunner.getInstance(), config);
	}
}
