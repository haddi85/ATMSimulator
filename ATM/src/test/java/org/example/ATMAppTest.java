package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ATMAppTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void commandTest() {
        String inputLogin = "login Alice\n";
        String inputDeposit = "deposit 100\n";
        String inputInvalidDepositBalance = "deposit a\n";
        String inputWithdraw = "withdraw 50\n";
        String inputInvalidWithdrawBalance = "withdraw a\n";
        String inputTransfer = "transfer Bob 50\n";
        String inputInvalidTransferBalance = "transfer Bob a\n";
        String inputLogout = "logout\n";
        String inputInvalid = "invalid test\n";
        String inputExit = "exit\n";

        String inputStr = inputLogin + inputDeposit + inputInvalidDepositBalance;
        inputStr += inputWithdraw + inputInvalidWithdrawBalance;
        inputStr += inputTransfer + inputInvalidTransferBalance;
        inputStr += inputLogout + inputInvalid + inputExit;

        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        System.setIn(in);
        ATMApp.main(new String[]{"arg1", "arg2"});

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("```bash", split[0].trim());
        assertEquals("Hello, Alice!", split[1].trim());
        assertEquals("Your balance is $0", split[2].trim());
        assertEquals("Your balance is $100", split[3].trim());
        assertEquals("Input is not a Integer!", split[4].trim());
        assertEquals("Your balance is $50", split[5].trim());
        assertEquals("Input is not a Integer!", split[6].trim());
        assertEquals("Transferred account not found!", split[7].trim());
        assertEquals("Input is not a Integer!", split[8].trim());
        assertEquals("Goodbye, Alice!", split[9].trim());
        assertEquals("Unrecognized command!", split[10].trim());
    }
}