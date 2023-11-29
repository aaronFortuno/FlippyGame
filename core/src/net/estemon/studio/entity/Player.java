package net.estemon.studio.entity;

import net.estemon.studio.config.GameConfig;

public class Player extends PlaneBase {

    public Player() {
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
}
