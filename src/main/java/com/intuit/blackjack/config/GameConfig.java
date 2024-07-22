package com.intuit.blackjack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game")
public class GameConfig {

    private int minimumBet;

    // Getters and setters

    public int getMinimumBet() {
        return minimumBet;
    }

    public void setMinimumBet(int minimumBet) {
        this.minimumBet = minimumBet;
    }
}
