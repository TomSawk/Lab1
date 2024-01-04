package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
    }

    @Test
    public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        // Attempt to open a new account with a unique name in SweBank
        SweBank.openAccount("Alice");
        assertTrue(SweBank.getBalance("Alice") == 0);

        // Attempt to open an account with the same name, which should throw an AccountExistsException
        assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Alice"));

        // Attempt to open an account with a unique name in DanskeBank
        DanskeBank.openAccount("Eva");

        // Check if the account was opened successfully
        assertTrue(DanskeBank.getBalance("Eva") == 0);
    }


    @Test
    public void testDeposit() throws AccountDoesNotExistException {
       SweBank.deposit("Ulrika", new Money(100, SEK));
       assertEquals(Integer.valueOf(100), SweBank.getBalance("Ulrika"));
        try {
            SweBank.deposit("NonExistentAccount", new Money(50, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException e) {

        }
    }


    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(100, SEK));
        SweBank.withdraw("Ulrika", new Money(50, SEK));
        assertEquals(Integer.valueOf(50), SweBank.getBalance("Ulrika"));

        try {
            SweBank.withdraw("NoName", new Money(70, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {

        }

        try {
            SweBank.withdraw("NonExistentAccount", new Money(30, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }
    }


    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(100, SEK));
        int balance = SweBank.getBalance("Ulrika");
        int balance_2 = Nordea.getBalance("Bob");
        assertEquals(100, balance);
        assertEquals(0, balance_2);
        try {
            int nonExistentBalance = SweBank.getBalance("NonExistentAccount");
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {

        }
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(100, SEK));
        SweBank.transfer("Ulrika", "Bob", new Money(50, SEK));
        assertEquals("transfer fromAccount balance", new Integer(50) ,  SweBank.getBalance("Ulrika"));
        assertEquals("transfer toAccount balance", new Integer(50) ,  SweBank.getBalance("Bob"));
        try {
            SweBank.transfer("Ulrika", Nordea, "Alice", new Money(60, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException e) {

        }

        try {
            SweBank.transfer("NonExistentAccount", Nordea, "Alice", new Money(30, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException e) {

        }
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(100, SEK));

        SweBank.addTimedPayment("Ulrika", "payment1", 2, 0, new Money(20, SEK), SweBank, "Bob");

        for (int i = 0; i < 5; i++) {
            SweBank.tick();
        }

        assertEquals(Integer.valueOf(60), SweBank.getBalance("Ulrika"));
        assertEquals(Integer.valueOf(40), SweBank.getBalance("Bob"));
    }

}
