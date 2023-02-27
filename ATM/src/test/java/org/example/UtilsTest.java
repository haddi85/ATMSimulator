package org.example;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


public class UtilsTest {
    private final Utils utils = new Utils();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void messageHelloTest() {
        final String name = "Linda";
        utils.messageHello(name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Hello, " + name + "!", split[0].trim());
    }

    @Test
    public void messageCurrentBalanceTest() {
        final int balance = 50;
        utils.messageCurrentBalance(balance);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Your balance is $" + balance, split[0].trim());
    }

    @Test
    public void messageTransferTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageTransfer(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $" + balance + " to " + name, split[0].trim());
    }

    @Test
    public void messageOwedToTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageOwedTo(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Owed $" + balance + " to " + name, split[0].trim());
    }

    @Test
    public void messageOwedFromTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageOwedFrom(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Owed $" + balance + " from " + name, split[0].trim());
    }

    @Test
    public void messageGoodbyeTest() {
        final String name = "Linda";
        utils.messageGoodbye(name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Goodbye, " + name + "!", split[0].trim());
    }

    @Test
    public void isValidNumberTest() {
        final String num = "5";

        assertTrue(utils.isInteger(num));
    }

    @Test

    public void isInvalidNumberTest() {
        final String num = "a";

        assertFalse(utils.isInteger(num));
    }
}
