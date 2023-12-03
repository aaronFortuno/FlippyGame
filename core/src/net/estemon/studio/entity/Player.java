package net.estemon.studio.entity;

import net.estemon.studio.config.GameConfig;

public class Player extends GameObjectBase {

    public Player() {
        super(GameConfig.PLANE_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
}
