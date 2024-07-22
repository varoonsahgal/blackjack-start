package com.intuit.blackjack.adapter.in.web;

import com.intuit.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlackjackController {

    private final ApplicationContext applicationContext;
    private Game game;
    private int bet;

    @Autowired
    public BlackjackController(ApplicationContext applicationContext, Game game) {
        this.applicationContext = applicationContext;
        this.game = game;
    }

    @PostMapping("/start-game")
    public String startGame(@RequestParam int bet, Model model) {
        try {
            game.reset(); // Reset the game state before starting a new game
            game.setBet(bet); // Set the bet before starting the game
            game.initialDeal();
            this.bet = bet; // Store the bet amount
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "index"; // Stay on index page if there's an error
        }
        return "redirect:/game";
    }

    @PostMapping("/hit")
    public String hit() {
        game.playerHits();
        return redirectBasedOnStateOfGame();
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        populateWithGameView(model);
        model.addAttribute("outcome", game.determineOutcome()); // Pass the GameOutcome object
        model.addAttribute("bet", bet); // Add the bet amount to the model
        return "done";
    }


    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        return redirectBasedOnStateOfGame();
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        populateWithGameView(model);
        return "blackjack";
    }

    @PostMapping("/startNewGame")
    public String startNewGame() {
        game.reset(); // Reset the game state for a new game
        return "redirect:/"; // Redirect to the home page to start a new game
    }

    private void populateWithGameView(Model model) {
        model.addAttribute("gameView", GameView.of(game));
    }

    private String redirectBasedOnStateOfGame() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }
}
