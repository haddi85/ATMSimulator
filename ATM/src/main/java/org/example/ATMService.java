package org.example;

import org.example.model.Account;

import java.util.HashMap;

public class ATMService {
    private final HashMap<String, Account> accountDetails = new HashMap<>();
    private final HashMap<String, String> oweToDetails = new HashMap<>();
    private final HashMap<String, String> oweFromDetails = new HashMap<>();
    private Account currentAccount = null;
    private final Utils utils = new Utils();

    private boolean isNontransferable(String targetName) {
        if (!accountDetails.containsKey(targetName)) {
            System.out.println("Transferred account not found!");
            return true;
        }

        if (currentAccount.getName().equals(targetName)) {
            System.out.println("Cannot transfer to same account!");
            return true;
        }

        if (currentAccount.getBalance() <= 0) {
            System.out.println("Cannot transfer balance less than or equal to 0!");
            return true;
        }
        return false;
    }

    public void login(String name) {
        if (!accountDetails.containsKey(name)) {
            Account createdAccount = new Account(name);
            accountDetails.put(name, createdAccount);
        }
        currentAccount = accountDetails.get(name);

        utils.messageHello(currentAccount.getName());
        utils.messageCurrentBalance(currentAccount.getBalance());

        if (currentAccount.getOwedBalance() > 0) {
            Account owedToAccount = accountDetails.get(oweToDetails.get(name));
            utils.messageOwedTo(currentAccount.getOwedBalance(), owedToAccount.getName());
        } else {
            if (oweFromDetails.containsKey(name)) {
                Account owedFromAccount = accountDetails.get(oweFromDetails.get(name));
                utils.messageOwedFrom(owedFromAccount.getOwedBalance(), owedFromAccount.getName());
            }
        }
    }

    public void deposit(int depositBalance) {
        if (currentAccount.getOwedBalance() > 0) {
            String targetName = oweToDetails.get(currentAccount.getName());
            Account targetAccount = accountDetails.get(targetName);

            int movingBalance = currentAccount.increaseBalance(depositBalance);
            targetAccount.increaseBalance(movingBalance);

            utils.messageTransfer(movingBalance, targetAccount.getName());
            utils.messageCurrentBalance(currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                utils.messageOwedTo(currentAccount.getOwedBalance(), targetName);
            } else {
                oweToDetails.remove(currentAccount.getName());
                oweFromDetails.remove(targetName);
            }
        } else {
            currentAccount.increaseBalance(depositBalance);
            utils.messageCurrentBalance(currentAccount.getBalance());
        }
    }

    public void withdraw(int withdrawBalance) {
        int deductedBalance = currentAccount.getBalance() - withdrawBalance;

        if (deductedBalance >= 0) {
            currentAccount.decreaseBalance(withdrawBalance);
            utils.messageCurrentBalance(currentAccount.getBalance());
        } else {
            System.out.println("Insufficient balance to withdraw!");
        }
    }

    public void transfer(String targetName, int transferBalance) {
        if (isNontransferable(targetName)) return;

        if (oweFromDetails.containsKey(currentAccount.getName())) {
            Account owedFromAccount = accountDetails.get(oweFromDetails.get(currentAccount.getName()));

            int movingBalance = owedFromAccount.increaseBalance(transferBalance);
            if (transferBalance > movingBalance) {
                currentAccount.decreaseBalance(transferBalance - movingBalance);
                utils.messageTransfer((transferBalance - movingBalance), owedFromAccount.getName());
            }

            utils.messageCurrentBalance(currentAccount.getBalance());
            if (owedFromAccount.getOwedBalance() > 0) {
                utils.messageOwedFrom(owedFromAccount.getOwedBalance(), owedFromAccount.getName());
            } else {
                oweToDetails.remove(targetName);
                oweFromDetails.remove(currentAccount.getName());
            }
        } else {
            Account targetAccount = accountDetails.get(targetName);

            int movingBalance = currentAccount.decreaseBalance(transferBalance);
            targetAccount.increaseBalance(movingBalance);

            if (currentAccount.getOwedBalance() > 0) {
                oweToDetails.put(currentAccount.getName(), targetAccount.getName());
                oweFromDetails.put(targetAccount.getName(), currentAccount.getName());
            }

            utils.messageTransfer(movingBalance, targetAccount.getName());
            utils.messageCurrentBalance(currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                utils.messageOwedTo(currentAccount.getOwedBalance(), targetAccount.getName());
            }
        }
    }

    public void logout() {
        utils.messageGoodbye(currentAccount.getName());
        currentAccount = null;
    }
}