package com.eritten;

import java.util.LinkedList;
import java.util.Queue;

public class BarberingShop {
    private Queue<String> mainChair = new LinkedList<>();
    private Queue<String> waitingChairs = new LinkedList<>();
    private int ordClientCount = 1;
    private int vIPClientCount = 1;
    private final int TOTAL_SEATS = 6;

    public void clientLeave() {
        try {
            if (mainChair.isEmpty()) {
                System.out.println("No client available.");
                return;
            }

            String leavingClient = mainChair.poll();
            String clientType = leavingClient.startsWith("VIP") ? "VIP" : "ORD";

            System.out.println(
                    String.format("%s Client %s is done and is leaving the main chair.", clientType, leavingClient));

            shiftClients();
        } catch (Exception e) {
            System.err.println("An error occurred in clientLeave: " + e.getMessage());
        }
    }

    public void ordClientEntered() {
        try {
            if (isShopFull()) {
                System.out.println("Ordinary client has come and is gone back. Barber shop is full.");
                return;
            }

            String ordClientName = "ord" + ordClientCount++;
            handleClient(ordClientName);
        } catch (Exception e) {
            System.err.println("An error occurred in ordClientEntered: " + e.getMessage());
        }
    }

    public void vIPClientEntered() {
        try {
            if (isShopFull()) {
                System.out.println("VIP client has come and is gone back. Barber shop is full.");
                return;
            }

            String vIPClientName = "VIP" + vIPClientCount++;
            handleVIPClient(vIPClientName);
        } catch (Exception e) {
            System.err.println("An error occurred in vIPClientEntered: " + e.getMessage());
        }
    }

    private void handleClient(String client) {
        try {
            if (mainChair.isEmpty()) {
                mainChair.add(client);
                System.out.println(
                        String.format("%s Client %s is seated in the main chair.", getClientType(client), client));
            } else {
                // If the main chair is occupied, and there is space in the waiting area, the
                // client sits in a waiting chair
                if (waitingChairs.size() < TOTAL_SEATS - 1) {
                    waitingChairs.add(client);
                    System.out.println(
                            String.format("%s Client %s has joined the queue.", getClientType(client), client));
                } else {
                    System.out.println(String.format("%s Client %s has come and is gone back. Barber shop is full.",
                            getClientType(client), client));
                }
            }
            displayShopState();
        } catch (Exception e) {
            System.err.println("An error occurred in handleClient: " + e.getMessage());
        }
    }

    private void handleVIPClient(String client) {
        try {
            if (mainChair.isEmpty()) {
                mainChair.add(client);
                System.out.println(String.format("VIP Client %s is seated in the main chair.", client));
            } else {
                // If there are VIP clients in the waiting area and there is space, the new VIP
                // takes a seat after the last VIP
                if (waitingChairs.stream().anyMatch(c -> c.startsWith("VIP"))
                        && waitingChairs.size() < TOTAL_SEATS - 1) {
                    waitingChairs.add(client);
                    System.out.println(String.format("VIP Client %s has joined the queue after the last VIP.", client));
                } else {
                    // VIP takes the first waiting chair, and others shift backward
                    String firstWaitingChair = waitingChairs.poll();
                    if (firstWaitingChair != null) {
                        waitingChairs.add(client);
                        waitingChairs.add(firstWaitingChair);
                        System.out.println(String.format("VIP Client %s is seated in the main chair.", client));
                    } else {
                        // If there is no space in the waiting area, VIP goes back
                        System.out.println(
                                String.format("VIP Client %s has come and is gone back. Barber shop is full.", client));
                    }
                }
            }
            displayShopState();
        } catch (Exception e) {
            System.err.println("An error occurred in handleVIPClient: " + e.getMessage());
        }
    }

    private void shiftClients() {
        try {
            if (!waitingChairs.isEmpty()) {
                String nextClient = waitingChairs.poll();
                mainChair.add(nextClient);
                System.out.println(String.format("Client %s has taken the main chair.", nextClient));
                displayShopState();
            }
        } catch (Exception e) {
            System.err.println("An error occurred in shiftClients: " + e.getMessage());
        }
    }

    private boolean isShopFull() {
        return mainChair.size() == 1 && waitingChairs.size() == TOTAL_SEATS - 1;
    }

    private String getClientType(String client) {
        return client.startsWith("VIP") ? "VIP" : "ORD";
    }

    public String getEvent() {
        return String.format("x ---> ( %s ) [ %s ]", getCurrentState(), getClientSittingOrder());
    }

    private String getClientSittingOrder() {
        // Retrieve the sitting order of clients in the shop
        return String.join(", ", mainChair) + ", " + String.join(", ", waitingChairs);
    }

    private void displayShopState() {
        // Display the current state of the shop in the specified format
        System.out.println(getEvent());
    }

    public String getCurrentState() {
        return "Main Chair: " + mainChair + ", Waiting Chairs: " + waitingChairs;
    }
}
