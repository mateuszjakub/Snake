package com.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.Input.Keys.*;

public class SnakeGame extends ApplicationAdapter {
	public static final int WINDOW_WIDTH = 300;
	public static final int WINDOW_HEIGHT = 200;

	private SpriteBatch batch;
	private Texture snakePiece;
	private Texture appleImg;
	private Texture gameOverImg;
	private Snake snake;
	private Apple apple;
	private boolean gameOver;

	@Override
	public void create () {
		batch = new SpriteBatch();
		snakePiece = new Texture("snake_part.png");
		appleImg = new Texture("apple.png");
		gameOverImg = new Texture("game_over.png");
		snake = new Snake(snakePiece);
		apple = new Apple(appleImg);
		initializeNewGame();
	}

	@Override
	public void render () {
		update();

		ScreenUtils.clear(0, 1, 0, 1);
		batch.begin();

		snake.draw(batch);
		apple.draw(batch);

		if (gameOver)
			batch.draw(gameOverImg, WINDOW_WIDTH/2-gameOverImg.getWidth()/2, WINDOW_HEIGHT/2-gameOverImg.getHeight()/2);

		batch.end();
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		snakePiece.dispose();
		appleImg.dispose();
		gameOverImg.dispose();
	}

	private void update() {
	if (!gameOver) {
		snake.act(Gdx.graphics.getDeltaTime());

		if (snake.isHeadEating(apple.applePosition)) {
			snake.rise();
			apple.randomizeApplePosition();
		}

		if (snake.hasHitHimself()) {
			gameOver = true;
		}
	}
	else {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
			initializeNewGame();
	}
	}

	private void initializeNewGame() {
		snake.initialize();
		apple.randomizeApplePosition();
		gameOver = false;
	}


}
