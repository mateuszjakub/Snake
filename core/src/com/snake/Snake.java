package com.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

enum MovementDirection {UP, RIGHT, DOWN, LEFT}

public class Snake {
    private final Texture texture;
    private final List<GridPoint2> snakeSegments;
    private MovementDirection direction;
    private float timeSinceLastAct;
    private boolean canChangeDirection;
    private int maxSnakeSize;

    public Snake(Texture texture) {
        this.texture = texture;
        this.maxSnakeSize = (SnakeGame.WINDOW_WIDTH/ texture.getWidth())*(SnakeGame.WINDOW_HEIGHT/ texture.getHeight());
        snakeSegments = new ArrayList<>();
    }

    public void initialize() {
        timeSinceLastAct = 0;
        direction = MovementDirection.RIGHT;
        snakeSegments.clear();
        this.snakeSegments.add(new GridPoint2(100,100));
        this.snakeSegments.add(new GridPoint2(80,100));
        this.snakeSegments.add(new GridPoint2(60,100));
        this.snakeSegments.add(new GridPoint2(40,100));
        this.snakeSegments.add(new GridPoint2(20,100));
    }
    public boolean hasHitHimself() {
        for (int i=1; i<snakeSegments.size();i++) {
            if (snakeSegments.get(i).equals(head()))
                return true;
        }
        return false;
    }
    public boolean isHeadEating(GridPoint2 applePosition) {
        return (head().equals(applePosition));
    }
    public void rise() {
        snakeSegments.add(new GridPoint2(snakeSegments.get(snakeSegments.size()-1)));
    }

    public void act(float deltaTime) {
       if (canChangeDirection) {
           handleDirectionChange();
       }
        timeSinceLastAct += deltaTime;
        if (timeSinceLastAct > 0.1) {
            timeSinceLastAct = 0;
            canChangeDirection = true;
            move();
        }
    }

    private void handleDirectionChange() {
        MovementDirection newDirection = direction;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && direction != MovementDirection.DOWN){
            newDirection = MovementDirection.UP;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && direction != MovementDirection.UP){
            newDirection = MovementDirection.DOWN;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && direction != MovementDirection.RIGHT){
            newDirection = MovementDirection.LEFT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && direction != MovementDirection.LEFT){
            newDirection = MovementDirection.RIGHT;
        }
        if (direction != newDirection){
            direction = newDirection;
            canChangeDirection = false;
        }
    }
    public GridPoint2 checkSnakeSegment(int i) {
           return  snakeSegments.get(i);
        }

    public int getSize() {
        return snakeSegments.size();
    }

    public int getMaxSnakeSize() {
        return maxSnakeSize;
    }

    private void move() {
            for (int i = snakeSegments.size() - 1; i > 0; i--) {
                snakeSegments.get(i).set(snakeSegments.get(i - 1));
            }
            GridPoint2 head = head();

            int lastSegmentX = SnakeGame.WINDOW_WIDTH - texture.getWidth();
            int lastSegmentY = SnakeGame.WINDOW_HEIGHT - texture.getHeight();

            switch (direction) {
                case RIGHT:
                    head.x = (head.x == lastSegmentX) ?  0 : head.x + texture.getWidth();
                    break;
                case LEFT:
                    head.x = (head.x == 0) ?  lastSegmentX : head.x - texture.getWidth();
                    break;
                case UP:
                    head.y = (head.y == lastSegmentY) ?  0 : head.y + texture.getHeight();
                    break;
                case DOWN:
                    head.y = (head.y == 0) ? lastSegmentY : head.y - texture.getHeight();
                    break;
            }
            head().set(head);
        }

    public void draw(Batch batch) {
        for (GridPoint2 pos : snakeSegments)
            batch.draw(texture, pos.x, pos.y);
    }

    private GridPoint2 head() {
        return snakeSegments.get(0);
    }
}
