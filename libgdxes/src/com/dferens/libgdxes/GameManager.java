package com.dferens.libgdxes;

import com.badlogic.gdx.Screen;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.RenderScope;

public abstract class GameManager<S extends Settings,
                                  E extends EntityManager,
                                  R extends RenderScope,
                                  I extends InputScope> implements SettingsProvider, Screen {
    private final GameWorld world;
    protected final S settings;
    protected E entities;
    protected R renderScope;
    protected I inputScope;

    public E getEntities() { return this.entities; }
    public R getRenderScope() { return this.renderScope; }
    public final Settings getSettings() { return this.settings; }

    public GameManager(S settings) {
        this.settings = settings;
        this.world = new GameWorld(this);
        this.setupComponents(this.world);
    }

    protected abstract void setupComponents(GameWorld world);

    protected final void innerRender(float deltaTime) {
        this.renderScope.clearScreen();
        this.renderScope.beginDraw();
        renderEntities(deltaTime);
        renderUI(deltaTime);
        this.renderScope.commitDraw();
    }

    protected final void update(float deltaTime) {
        updateWorld(deltaTime);
        updateEntities(deltaTime);
    }

    protected void renderEntities(float deltaTime) {
        for (Renderable entity : entities.iterateRenderables()) {
            Context context = entities.getContext(entity);
            entity.render(deltaTime, context, renderScope);
        }
    }
    protected final void renderUI(float deltaTime) {
        inputScope.render(deltaTime);
    }
    protected void updateWorld(float deltaTime) {
        this.entities.updateWorld(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        for (Updatable entity : entities.iterateUpdatables()) {
            Context context = entities.getContext(entity);
            entity.update(deltaTime, context, inputScope, renderScope);
        }
    }

    public final void process(float deltaTime) {
        deltaTime = Math.min(deltaTime, settings.maxTimeStep);
        this.update(deltaTime);
        this.innerRender(deltaTime);
    }

    /** Begin of {@link Screen} interface */

    @Override
    public void render(float delta) { this.process(delta); }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void show() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {
        this.world.dispose();
        this.renderScope.dispose();
    }
}
