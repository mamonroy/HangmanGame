package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Message;
import ca.cmpt213.a4.onlinehangman.model.Game;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  This class is for the Controller. Responsible in synchronization between the html View and game Model
 *  @author: Mark Angelo Monroy (Student ID: 301326143, SFU ID: mamonroy@sfu.ca)
 */

@Controller
public class HangmanController {
    private Message promptMessage; //a reusable String object to display a prompt message at the screen
    private AtomicLong nextID = new AtomicLong();
    private List<Game> games = new ArrayList<>();

    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");
    }

    @GetMapping("/helloworld")
    public String showHelloworldPage(Model model) {

        promptMessage.setMessage("You are at the helloworld page!");
        model.addAttribute("promptMessage", promptMessage);

        // take the user to helloworld.html
        return "helloworld";
    }

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        return "welcome";
    }

    @GetMapping("/game")
    public String showForm(Model model) {
        Game myGame = new Game();
        myGame.startGame();
        myGame.setGameID(nextID.incrementAndGet());
        games.add(myGame);
        model.addAttribute("game", myGame);
        return "game";
    }

    @PostMapping("/game")
    public String submitForm(@ModelAttribute("game") Game myGame) {

        for(int i = 0; i < games.size(); i++) {
            if(games.get(i).getGameID() == myGame.getGameID()) {
                games.set(i, myGame);
            }
        }
        myGame.guessLetter(myGame.getInputLetter());
        if(myGame.isWon()) {
            myGame.setStatus("Won");
            return "gameover";
        } else if(myGame.isLost()) {
            myGame.setStatus("Lost");
            return "gameover";
        }
        myGame.clearLetter();
        return "resultgame";
    }

    @GetMapping("/game/{id}")
    public String getOneGame(@PathVariable("id") long id, Model model) {
        for(Game game: games) {
            if(game.getGameID() == id) {
                model.addAttribute("game",game);
                if(game.isWon()) {
                    return "gameover";
                } else if(game.isLost()) {
                    return "gameover";
                }
                return "game";
            }
        }
        throw new GameNotFoundException("Game not found!");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(GameNotFoundException.class)
    public ModelAndView gameNotAvailable(GameNotFoundException ex) {
        return new ModelAndView("gamenotfound", "error", ex.getMessage());
    }
}