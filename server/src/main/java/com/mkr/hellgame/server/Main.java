package com.mkr.hellgame.server;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server started");

        try
        {
            System.in.read();
        }
        catch (Exception e)
        {
            // Do nothing.
        }
    }
}
