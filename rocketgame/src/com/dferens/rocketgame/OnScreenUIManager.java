package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.dferens.core.UIManager;

public class OnScreenUIManager extends UIManager {
    public Boolean isMovingLeft() {
        if (isTouched()) {
            float x = this.getTouchPosX();
            float y = this.getTouchPosY();

            return ((x < Gdx.graphics.getWidth() / 2) &&
                    (y < Gdx.graphics.getHeight() / 2));
        }
        return false;
    }
    public Boolean isMovingRight() {
        if (isTouched()) {
            float x = this.getTouchPosX();
            float y = this.getTouchPosY();

            return ((x > Gdx.graphics.getWidth() / 2) &&
                    (y < Gdx.graphics.getHeight() / 2));
        }
        return false;
    }
    public Boolean isJumping() {
        return isTouched();
    }

    @Override
    public void render(float deltaTime) {

    }
}
