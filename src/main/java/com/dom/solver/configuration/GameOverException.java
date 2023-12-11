package com.dom.solver.configuration;

public class GameOverException extends RuntimeException {
    public GameOverException(String errorMessage) {
        super(errorMessage);
    }
}