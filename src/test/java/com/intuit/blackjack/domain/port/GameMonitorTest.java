package com.intuit.blackjack.domain.port;

import com.intuit.blackjack.config.GameConfig;


// use these static imports in the test class
import com.intuit.blackjack.domain.Deck;
import com.intuit.blackjack.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GameMonitorTest {


    @Test
    public void playerStandsThenGameIsOverAndResultsSentToMonitor() throws Exception {
        // create the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        //hey Mockito, i want you to spy on this class
        // WHY? ultimately i just need to know when round completed is called
        //

        //Question for later on the 2nd test case. To ensure the player busts I did
        //
       // Game game = new Game(StubDeck.playerHitsAndGoesBust(), gameMonitorSpy);
        //
        //
        //But StubDeck is protected by default so I had to move the GameMonitorTest to the domain package instead of the sub package port to access it. This breaks the logical separation. I think the better approach was to make the StubDeck methods public since it’s only used for testing, maybe move into its own testUtil folder as well.
        //
        //Wondering if others saw this issue and what would be the best approach.


        // TODO: finish the rest of this setup...
        GameConfig gameConfig = new GameConfig();
        gameConfig.setMinimumBet(10); // Set the minimum bet to match your application properties

        Game game = new Game(new Deck(), gameMonitorSpy, gameConfig );
         game.initialDeal();
         game.playerStands(); //now roundCompleted will be called here b/c we added that code

        //
        // TODO: execute the behavior that you expect to call the GameMonitor
        //

        // verify that the roundCompleted method was called with any instance of a Game class
        // THIS IS THE HEART OF THE TEST:
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

}
