package com.intuit.blackjack.adapter.out.gamepersistence;


import com.intuit.blackjack.domain.Game;
import com.intuit.blackjack.domain.port.GameMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//GameMonitor - remember this is the PORT that sits INSIDE the hexagon
// as an interface
// but in the adapter is where we provide the concrete implementation
//TLDR; we implement roundCompleted here and send data to the Database

//@Bean vs @Component: - at a high level these annotations do the same thing -
// they create Spring beans from whatever they annotate
// remember a Spring bean is just an object managed by Spring
// Java bean is an "old school" term, we don't really use them anymore
//@Bean can only be used on methods
//@Component can only be used at the class level

//you can only @Component when you have direct access to the class definitions
// when would you NOT have direct access to a class?
// when you are using 3rd party libraries - you would use a @Bean annotation instead
@Component
public class PersistGameMonitor implements GameMonitor {

    //problem: this reference is NULL, but doesnt Spring wire it up for me??

    //What's the issue here?

    //So, the issue is: the wiring does NOT happen automatically
    //you have to tell Spring you want this instance to be Autowired...

    private final GameResultRepository gameResultRepository;


    // when we write autowired here, we are basically saying "hey Spring
    // JPA, can you instantiate gameResultRepository and pass in the object for me
    // this is even more interesting because GameResultRepository is an INTERFACE
    // that SPRING will IMPLEMENT for you...
    @Autowired
    public PersistGameMonitor(GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
    }

    @Override
    public void roundCompleted(Game game){

        //DTO - grab a subset of things from the game object
        //namely, we want to grab whatever we want to send to the database

        System.out.println("in round completed in persistgamemonitor");
        GameOutcomeEntity gm = new GameOutcomeEntity("Varoon", "WIN", "13", "18");

        //goal: when a round of the game of BJ is completed
        // we want a row of data to be inserted into our H2 database
        // we will use the gameResultRespository object to communicate with H2
        // because this object is given to us by SPring - it allows us to coommunicate
        // with the database, gives us CRUD operations - we don't have to instantiate it
        // ourselves, so we just call t

        //GOAL: see if this entity object gets inserted as a row into the table
        // once you connect to h2, then do a select * FROM GameOutcomeEntity
        gameResultRepository.save(gm);


    }
        //TODO: your code here - utilize the gameResultRepository and the DTO to add entries to the database

    }
