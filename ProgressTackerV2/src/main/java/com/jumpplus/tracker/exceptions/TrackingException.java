package com.jumpplus.tracker.exceptions;

public class TrackingException extends Exception {
    public TrackingException() {
        System.out.println("This is already tracked.");
    }
    public TrackingException(String message) {
        System.out.println(message);
    }
}
