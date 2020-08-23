package ca.cmpt213.a4.onlinehangman.controllers;

/**
 *  This class is for running a Run Time Exception if the Game ID not found
 *  @author: Mark Angelo Monroy (Student ID: 301326143, SFU ID: mamonroy@sfu.ca)
 */

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String message) {
        super(message);
    }
}
