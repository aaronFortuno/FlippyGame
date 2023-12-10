package net.estemon.studio.entity;

import net.estemon.studio.config.GameConfig;

public enum BonusKind {
    GOLD(
            GameConfig.GOLD_BONUS
    ),
    SILVER(
            GameConfig.SILVER_BONUS
    ),
    BRONZE(
            GameConfig.BRONZE_BONUS
    );

    private final int value;

    BonusKind (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

