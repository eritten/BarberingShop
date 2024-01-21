package com.eritten;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        BarberingShop shop = new BarberingShop();

        displayWelcomeMessage();

        while (true) {
            String keyPress = inputScanner.nextLine();
            if (!keyPress.equals(" ")) {
                break;
            }
            processRandomClient(shop);
        }

        inputScanner.close();
    }

    private static void displayWelcomeMessage() {
        System.out.println("Welcome to this program");
    }

    private static void processRandomClient(BarberingShop shop) {
        int randomAction = generateRandomNumber(4);
        System.out.println(randomAction);

        switch (randomAction) {
            case 0:
                shop.clientLeave();
                break;
            case 1:
                shop.vIPClientEntered();
                break;
            case 2:
            case 3:
                shop.ordClientEntered();
                break;
        }

        // Display the event
        System.out.println(shop.getEvent());
    }

    private static int generateRandomNumber(int bound) {
        return (int) (Math.random() * bound);
    }
}
