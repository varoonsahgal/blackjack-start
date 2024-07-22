package com.intuit.blackjack.domain;

import com.intuit.blackjack.domain.port.GameMonitor;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameOutcomeTest {

    @Test
    public void playerHitsAndGoesBustThenPlayerLoses() throws Exception {
        // Create a mock GameMonitor
        GameMonitor gameMonitor = mock(GameMonitor.class);

        // Create a Game instance with the mock GameMonitor
        Game game = new Game(StubDeck.playerHitsAndGoesBust(), gameMonitor);
        game.initialDeal();
        game.playerHits();

        // Assert that the game outcome is PLAYER_BUSTED
        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_BUSTED);

        // Verify that roundCompleted was called on the mock GameMonitor
        verify(gameMonitor).roundCompleted(game);
    }

    @Test
    public void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() throws Exception {
        // Create a mock GameMonitor
        GameMonitor gameMonitor = mock(GameMonitor.class);

        // Create a Game instance with the mock GameMonitor
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer(), gameMonitor);
        game.initialDeal();
        game.playerStands();
        game.dealerTurn();

        // Assert that the game outcome is PLAYER_BEATS_DEALER
        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_BEATS_DEALER);

        // Verify that roundCompleted was called on the mock GameMonitor
        verify(gameMonitor).roundCompleted(game);
    }

    @Test
    public void playerDealtBlackjackUponInitialDealThenWinsBlackjack() throws Exception {
        // Create a mock GameMonitor
        GameMonitor gameMonitor = mock(GameMonitor.class);

        // Create a Game instance with the mock GameMonitor
        Game game = new Game(new StubDeck(Rank.ACE, Rank.NINE, Rank.JACK, Rank.EIGHT), gameMonitor);
        game.initialDeal();
        game.playerStands();
        game.dealerTurn();

        // Assert that the game outcome is PLAYER_WINS_BLACKJACK
        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_WINS_BLACKJACK);

        // Assert that the player is done
        assertThat(game.isPlayerDone())
                .isTrue();

        // Verify that roundCompleted was called on the mock GameMonitor
        verify(gameMonitor).roundCompleted(game);
    }
}
