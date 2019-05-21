package com.ihesen.simpleeventbus;

/**
 * Created by ihesen on 2019-05-21
 */
public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
