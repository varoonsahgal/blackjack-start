package com.intuit.blackjack.domain;

import com.intuit.blackjack.config.GameConfig;
import com.intuit.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;
    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();
    private boolean playerDone;
    private GameMonitor gameMonitor;
    private int minimumBet;
    private int bet;  // Add an internal bet field

    public Game(Deck deck)
    {
        this.deck = deck;
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
        // assign Deck & GameMonitor to private final fields
    }


    public Game(Deck deck, GameMonitor gameMonitor, GameConfig gameConfig) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
        this.minimumBet = gameConfig.getMinimumBet();
    }

    public void setBet(int bet) {
        if (bet < minimumBet) {
            throw new IllegalArgumentException("Bet must be at least " + minimumBet);
        }
        this.bet = bet;
    }

    public void reset() {
        this.playerHand.clear();
        this.dealerHand.clear();
        playerDone = false;
        //this.deck.shuffle(); // Re-shuffle the deck when resetting the game state
    }

    public void initialDeal() {
        // The bet validation has been moved to the setBet method
        dealRoundOfCards();
        dealRoundOfCards();
        if (playerHand.hasBlackjack()) {
            playerDone = true;
        }
    }

    private void dealRoundOfCards() {
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (playerHand.hasBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    public void dealerTurn() {
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
        gameMonitor.roundCompleted(this);
    }

    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
        if (playerDone) {
            gameMonitor.roundCompleted(this);
        }
    }

    public void playerStands() {
        playerDone = true;
        dealerTurn();
        gameMonitor.roundCompleted(this);
    }

    public boolean isPlayerDone() {
        return playerDone;
    }
}
