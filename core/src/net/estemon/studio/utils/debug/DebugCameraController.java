package net.estemon.studio.utils.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DebugCameraController {

    private final Vector2 position = new Vector2();
    private final Vector2 startPosition = new Vector2();
    private float zoom = 1f;

    private final DebugCameraConfig config;

    public DebugCameraController() {
        config = new DebugCameraConfig();
    }

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float delta) {

        // Check if we are not on desktop then not handle inputs
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = config.getMoveSpeed() * delta;
        float zoomSpeed = config.getZoomSpeed() * delta;

        if (config.isLeftPressed()) { moveLeft(moveSpeed); }
        if (config.isRightPressed()) { moveRight(moveSpeed); }
        if (config.isUpPressed()) { moveUp(moveSpeed); }
        if (config.isDownPressed()) { moveDown(moveSpeed); }

        if (config.isZoomInPressed()) { zoomIn(zoomSpeed); }
        if (config.isZoomOutPressed()) { zoomOut(zoomSpeed); }

        if (config.isResetPressed()) { reset(); }
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void setZoom(float value) {
        // check if our values are inside valid min-max zoom levels
        zoom = MathUtils.clamp(value, config.getMaxZoomIn(), config.getMaxZoomOut());
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveLeft(float moveSpeed) {
        moveCamera(-moveSpeed, 0);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0);
    }

    private void moveUp(float moveSpeed) {
        moveCamera(0, moveSpeed);
    }

    private void moveDown(float moveSpeed) {
        moveCamera(0, -moveSpeed);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void reset() {
        position.set(startPosition);
        setZoom(1.0f);
    }
}
