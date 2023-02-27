package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
    private Account account;
    private static final String initialName = "bham";

    @Before
    public void setUp() {
        account = new Account(initialName);
    }

    @Test
    public void getInitTest() {
        final int balance = 0;
        final int owedBalance = 0;

        assertEquals(initialName, account.getName());
        assertEquals(balance, account.getBalance());
        assertEquals(owedBalance, account.getOwedBalance());
    }

    @Test
    public void normalIncreaseBalanceTest() {
        final int balance = 50;
        final int owedBalance = 0;

        account.increaseBalance(balance);

        assertEquals(balance, account.getBalance());
        assertEquals(owedBalance, account.getOwedBalance());
    }

    @Test
    public void normalDecreaseBalanceTest() {
        final int increasedBalance = 50;
        final int decreasedBalance = 30;

        account.increaseBalance(increasedBalance);
        account.decreaseBalance(decreasedBalance);

        assertEquals(increasedBalance - decreasedBalance, account.getBalance());
    }

    @Test
    public void increaseBalanceLessThanOwedBalanceTest() {
        final int[] increasedBalance = {50, 10};
        final int decreasedBalance = 70;

        account.increaseBalance(increasedBalance[0]);
        account.decreaseBalance(decreasedBalance);
        account.increaseBalance(increasedBalance[1]);

        assertEquals(0, account.getBalance());
        assertEquals(decreasedBalance - increasedBalance[0] - increasedBalance[1], account.getOwedBalance());
    }

    @Test
    public void increaseBalanceMoreThanOwedBalanceTest() {
        final int[] increasedBalance = {50, 40};
        final int decreasedBalance = 70;

        account.increaseBalance(increasedBalance[0]);
        account.decreaseBalance(decreasedBalance);
        account.increaseBalance(increasedBalance[1]);

        assertEquals(20, account.getBalance());
        assertEquals(0, account.getOwedBalance());
    }

    @Test
    public void transferBalanceDecreaseMoreThanInitTest() {
        final int initBalance = 50;
        final int decreasedBalance = 70;

        account.increaseBalance(initBalance);
        int transferBalance = account.decreaseBalance(decreasedBalance);

        assertEquals(initBalance, transferBalance);
    }

    @Test
    public void transferBalanceDecreaseLessThanInitTest() {
        final int initBalance = 50;
        final int decreasedBalance = 20;

        account.increaseBalance(initBalance);
        int transferBalance = account.decreaseBalance(decreasedBalance);

        assertEquals(decreasedBalance, transferBalance);
    }

    @Test
    public void transferBalanceIncreaseMoreThanOwedTest() {
        final int initBalance = 20;
        final int decreasedBalance = 50;
        final int increasedBalance = 40;

        account.increaseBalance(initBalance);
        account.decreaseBalance(decreasedBalance);
        int transferBalance = account.increaseBalance(increasedBalance);

        assertEquals(decreasedBalance - initBalance, transferBalance);
    }

    @Test
    public void transferBalanceIncreaseLessThanOwedTest() {
        final int initBalance = 20;
        final int decreasedBalance = 50;
        final int increasedBalance = 20;

        account.increaseBalance(initBalance);
        account.decreaseBalance(decreasedBalance);
        int transferBalance = account.increaseBalance(increasedBalance);

        assertEquals(increasedBalance, transferBalance);
    }
}
