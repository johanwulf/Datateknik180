package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bank.BankAccount;

import org.junit.jupiter.api.Test;

class MyFirstTest {

    private final BankAccount acc = new BankAccount();

    @Test
    void addition() {
        assertEquals(0, acc.getBalance());
    }

}