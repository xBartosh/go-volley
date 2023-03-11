package pl.go.volley.govolley.exception;

import java.util.function.Supplier;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(){
        super("Game not found!");
    }
}
