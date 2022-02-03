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
	private Texture winImg;
	private Texture headImg;
	private Snake snake;
	private Apple apple;
	private boolean gameOver;
	private boolean collision;
	private  boolean win;

	@Override
	public void create () {
		batch = new SpriteBatch();
		snakePiece = new Texture("snake_part.png");
		appleImg = new Texture("apple.png");
		gameOverImg = new Texture("game_over.png");
		winImg = new Texture("win.png");
		headImg = new Texture("snake_head.png");
		snake = new Snake(snakePiece, headImg);
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
		if (win)
			batch.draw(winImg, WINDOW_WIDTH/2-gameOverImg.getWidth()/2, WINDOW_HEIGHT/2-gameOverImg.getHeight()/2);

		batch.end();
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		snakePiece.dispose();
		appleImg.dispose();
		gameOverImg.dispose();
		winImg.dispose();
		headImg.dispose();
	}

	private void update() {
		if (!gameOver&&!win) {
			snake.act(Gdx.graphics.getDeltaTime());

			if (snake.isHeadEating(apple.applePosition)) {
				if (snake.getSize() == snake.getMaxSnakeSize()-1) {
					win = true;
				}

				if (!win) {
					snake.rise();
					findAppleSpot();
				}
			}

			if (snake.hasHitHimself()) {
				gameOver = true;
			}

		} else {
			if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
				initializeNewGame();
		}
	}



	private void initializeNewGame() {
		snake.initialize();
		findAppleSpot();
		gameOver = false;
		win = false;
	}

	private void findAppleSpot() {
		do {
			apple.randomizeApplePosition();
			for (int i = 0; i < snake.getSize(); i++) {
				if (snake.checkSnakeSegment(i).equals(apple.checkApplePos())) {
					collision = true;
					break;
				} else {
					collision = false;
				}
			}
		}
		while (collision == true);
		collision = false;
		}

}
