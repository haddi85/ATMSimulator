package org.example;

import java.util.Scanner;

public class ATMApp {
    public static void main(String[] args) {
        ATMService atmService = new ATMService();
        Utils utils = new Utils();

        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        System.out.println("```bash");
        while (!isExit) {
            String rawInput = scanner.nextLine();
            String[] splitInput = rawInput.split("\\s+");

            switch (splitInput[0]) {
                case "login":
                    atmService.login(splitInput[1]);
                    break;
                case "deposit":
                    if (!utils.isInteger(splitInput[1])) break;

                    atmService.deposit(Integer.parseInt(splitInput[1]));
                    break;
                case "withdraw":
                    if (!utils.isInteger(splitInput[1])) break;

                    atmService.withdraw(Integer.parseInt(splitInput[1]));
                    break;
                case "transfer":
                    if (!utils.isInteger(splitInput[2])) break;

                    atmService.transfer(splitInput[1], Integer.parseInt(splitInput[2]));
                    break;
                case "logout":
                    atmService.logout();
                    break;
                case "exit":
                    isExit = true;
                    break;
                default:
                    System.out.println("Unrecognized command!");
            }
        }
    }
}
