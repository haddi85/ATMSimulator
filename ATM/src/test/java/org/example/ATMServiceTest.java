package org.example;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ATMServiceTest {
    private ATMService atmService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUpEach() {
        atmService = new ATMService();
        System.setOut(new PrintStream(outputStreamCaptor));
        atmService.login("Alice");
    }

    @Test
    public void loginTest() {
        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Hello, Alice!", split[0].trim());
        assertEquals("Your balance is $0", split[1].trim());
    }

    @Test
    public void depositTest() {
        atmService.deposit(100);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Your balance is $100", split[2].trim());
    }

    @Test
    public void withdrawTest() {
        atmService.deposit(100);
        atmService.withdraw(90);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Your balance is $10", split[3].trim());
    }

    @Test
    public void insufficientWithdrawTest() {
        atmService.deposit(100);
        atmService.withdraw(110);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Insufficient balance to withdraw!", split[3].trim());
    }

    @Test
    public void selfTransferTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Bob", 50);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Cannot transfer to same account!", split[7].trim());
    }

    @Test
    public void transferToInvalidAccountTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Yudi", 50);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred account not found!", split[7].trim());
    }

    @Test
    public void transferMoreThanZeroBalanceTest() {
        atmService.logout();
        atmService.login("Bob");
        atmService.transfer("Alice", 50);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Cannot transfer balance less than or equal to 0!", split[5].trim());
    }

    @Test
    public void logoutTest() {
        atmService.logout();

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Goodbye, Alice!", split[2].trim());
    }

    @Test
    public void transferLessThanBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 50);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $50 to Alice", split[7].trim());
        assertEquals("Your balance is $30", split[8].trim());
    }

    @Test
    public void transferMoreThanBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $80 to Alice", split[7].trim());
        assertEquals("Your balance is $0", split[8].trim());
        assertEquals("Owed $70 to Alice", split[9].trim());
    }

    @Test
    public void owedToLoginTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Bob");

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Hello, Bob!", split[11].trim());
        assertEquals("Your balance is $0", split[12].trim());
        assertEquals("Owed $70 to Alice", split[13].trim());
    }

    @Test
    public void owedFromLoginTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Alice");

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Hello, Alice!", split[11].trim());
        assertEquals("Your balance is $180", split[12].trim());
        assertEquals("Owed $70 from Bob", split[13].trim());
    }

    @Test
    public void depositLessThanOwedBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(60);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $60 to Alice", split[14].trim());
        assertEquals("Your balance is $0", split[15].trim());
        assertEquals("Owed $10 to Alice", split[16].trim());
    }

    @Test
    public void depositMoreThanOwedBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(100);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $70 to Alice", split[14].trim());
        assertEquals("Your balance is $30", split[15].trim());
    }

    @Test
    public void transferLessThanOwedBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Alice");
        atmService.transfer("Bob", 50);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Your balance is $180", split[14].trim());
        assertEquals("Owed $20 from Bob", split[15].trim());
    }

    @Test
    public void transferMoreThanOwedBalanceTest() {
        atmService.deposit(100);
        atmService.logout();
        atmService.login("Bob");
        atmService.deposit(80);
        atmService.transfer("Alice", 150);
        atmService.logout();
        atmService.login("Alice");
        atmService.transfer("Bob", 100);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $30 to Bob", split[14].trim());
        assertEquals("Your balance is $150", split[15].trim());
    }
}
