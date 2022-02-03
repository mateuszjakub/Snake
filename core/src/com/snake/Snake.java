package com.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

enum MovementDirection {UP, RIGHT, DOWN, LEFT}

public class Snake {
    private final Texture texture;
    private TextureRegion[] headTexture;
    private TextureRegion[] tailTexture;
    private final List<GridPoint2> snakeSegments;
    private MovementDirection direction;
    private MovementDirection tailDirection;
    private float timeSinceLastAct;
    private boolean canChangeDirection;
    private final int maxSnakeSize;

    public Snake(Texture texture, Texture headMultiTexture) {
        this.texture = texture;
        headTexture = new TextureRegion[]{
                new TextureRegion(headMultiTexture, 0, 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 1 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 2 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 3 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                };
        tailTexture = new TextureRegion[]{
                new TextureRegion(headMultiTexture, 4 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 5 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 6 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                new TextureRegion(headMultiTexture, 7 * texture.getWidth(), 0, texture.getWidth(), texture.getHeight()),
                };
        this.maxSnakeSize = (SnakeGame.WINDOW_WIDTH/ texture.getWidth())*(SnakeGame.WINDOW_HEIGHT/ texture.getHeight());
        snakeSegments = new ArrayList<>();
    }

    public void initialize() {
        timeSinceLastAct = 0;
        direction = MovementDirection.RIGHT;
        tailDirection = MovementDirection.RIGHT;
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
        determinateTailDirection();
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
        //rysowanie ciala weza
        GridPoint2 body = new GridPoint2();
        for (int i = 1; i<snakeSegments.size()-1; i++) {
            body = snakeSegments.get(i);
            batch.draw(texture, body.x, body.y);
        }

        //rysowanie glowy weza
        batch.draw(headTexture[direction.ordinal()], head().x, head().y);

        //rysowanie ogona weza
        batch.draw(tailTexture[tailDirection.ordinal()], snakeSegments.get(snakeSegments.size()-1).x, snakeSegments.get(snakeSegments.size()-1).y);
    }

    private void determinateTailDirection() {
        GridPoint2 segmentBeforeTail = snakeSegments.get(snakeSegments.size()-2);
        GridPoint2 tail = snakeSegments.get(snakeSegments.size()-1);

        if (tail.x == 0 && segmentBeforeTail.x == SnakeGame.WINDOW_WIDTH - texture.getWidth()){
            tailDirection = MovementDirection.LEFT;
        }
        else if (tail.x == SnakeGame.WINDOW_WIDTH - texture.getWidth() && segmentBeforeTail.x == 0){
            tailDirection = MovementDirection.RIGHT;
        }
        else if (tail.y == 0 && segmentBeforeTail.y == SnakeGame.WINDOW_HEIGHT - texture.getHeight()){
            tailDirection = MovementDirection.DOWN;
        }
        else if (tail.y == SnakeGame.WINDOW_HEIGHT - texture.getHeight() && segmentBeforeTail.y == 0){
            tailDirection = MovementDirection.UP;
        }
        else if (segmentBeforeTail.x>tail.x) {
            tailDirection = MovementDirection.RIGHT;
        }
        else if (segmentBeforeTail.x<tail.x) {
            tailDirection = MovementDirection.LEFT;
        }
        else if (segmentBeforeTail.y>tail.y) {
            tailDirection = MovementDirection.UP;
        }
        else if (segmentBeforeTail.y<tail.y) {
            tailDirection = MovementDirection.DOWN;
        }
    }

    private GridPoint2 head() {
        return snakeSegments.get(0);
    }
}
