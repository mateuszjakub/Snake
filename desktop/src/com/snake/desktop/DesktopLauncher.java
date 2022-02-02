package com.snake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.snake.SnakeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.width = SnakeGame.WINDOW_WIDTH;
		config.height = SnakeGame.WINDOW_HEIGHT;

		config.title = "SnakeGame";
		new LwjglApplication(new SnakeGame(), config);
	}
}
