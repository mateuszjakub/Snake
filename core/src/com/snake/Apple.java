package com.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public class Apple {
    Texture texture;
    GridPoint2 applePosition;

    public Apple (Texture texture) {
    this.texture = texture;
    this.applePosition = new GridPoint2();
}
public GridPoint2 checkApplePos() {
        return applePosition;
}

public void randomizeApplePosition () {
        applePosition.x = ((int) (Math.random()* SnakeGame.WINDOW_WIDTH/texture.getWidth()))* texture.getWidth();
        applePosition.y = ((int) (Math.random()* SnakeGame.WINDOW_HEIGHT/texture.getHeight()))* texture.getHeight();
}

public void draw(Batch batch) {
        batch.draw(texture, applePosition.x, applePosition.y);
}


}
