package com.jumpplus.tracker.main;

import com.jumpplus.tracker.exceptions.LoginException;
import com.jumpplus.tracker.runner.Runner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            Runner.main(args);


            throw new LoginException();
        } catch (LoginException e) {

        }
    }
}
