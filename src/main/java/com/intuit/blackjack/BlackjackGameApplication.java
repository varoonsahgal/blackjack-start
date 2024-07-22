package com.intuit.blackjack;

import com.intuit.blackjack.adapter.out.gamepersistence.GameResultRepository;
import com.intuit.blackjack.adapter.out.gamepersistence.PersistGameMonitor;
import com.intuit.blackjack.config.GameConfig;
import com.intuit.blackjack.domain.Deck;
import com.intuit.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

//how does Spring know WHERE To look for beans?
// From wherever the @SpringBootApplication annotation is and from that package down
@SpringBootApplication
public class BlackjackGameApplication {

//    @Autowired
//    private GameResultRepository gameResultRepository;

    public static void main(String[] args) {
        ApplicationContext ac =   SpringApplication.run(BlackjackGameApplication.class, args);

        for(String bean : ac.getBeanDefinitionNames()) {
            System.out.println(bean);
        }
    }



    //Spring will create a bean, which is basically an object from "somethingReturned"
    // then the KEY is that Spring will auto-inject that dependency wherever you need it - like
    // in an adapter class like ConsoleCard.java
    // WHY DO WE WANT TO DO THIS??
    // MAINTAIN A LOOSE COUPLING SO THAT CONSOLECARD HAS NO KNOWLEDGE OF SOMETHINGRETURNED...
    //

    //what should our Bean be?
    //our Bean should be an object of Game
    //so the method should return a Game object
    //Why should our bean be an object of Game?

    //The reason we create a bean is so that SPring can manage
    // the dependency for us and give us loose coupling

    //default scope of a bean is singleton - same object
    //that gets used by the Spring container

    //can we define a bean for a void method??
    //no - because Spring uses the return value
    // of the method for the bean...
    @Bean
   // @Scope("prototype")
    public Game createGame(PersistGameMonitor persistGameMonitor, GameConfig gameConfig)
    {
        return new Game(new Deck(), persistGameMonitor , gameConfig);
    }
}
