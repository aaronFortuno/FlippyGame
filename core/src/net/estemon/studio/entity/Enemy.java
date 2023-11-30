package net.estemon.studio.entity;

import net.estemon.studio.config.GameConfig;

public class Enemy extends PlaneBase {

    public Enemy() {
        super(GameConfig.PLANE_BOUNDS_RADIUS);
        setSize(GameConfig.ENEMY_MIN_SIZE, GameConfig.ENEMY_MIN_SIZE);
    }
}
