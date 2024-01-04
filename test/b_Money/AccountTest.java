package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse(testAccount.timedPaymentExists("payment1"));
		testAccount.addTimedPayment("payment1", 5, 2, new Money(100, SEK), SweBank, "Alice");

		assertTrue(testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");

		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		testAccount.addTimedPayment("payment1", 2, 1, new Money(500, SEK), SweBank, "Alice");


		testAccount.tick();
		testAccount.tick();

		assertEquals(Integer.valueOf(9999500), testAccount.getBalance());
		assertEquals(Integer.valueOf(1000500), SweBank.getBalance("Alice"));

		testAccount.removeTimedPayment("payment1");

		testAccount.tick();
		testAccount.tick();

		assertEquals(Integer.valueOf(9999500), testAccount.getBalance());
		assertEquals(Integer.valueOf(1000500), SweBank.getBalance("Alice"));
	}


	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(500, SEK));
		assertEquals(Integer.valueOf(10000500), testAccount.getBalance());

		testAccount.withdraw(new Money(300, SEK));
		assertEquals(Integer.valueOf(10000200), testAccount.getBalance());
	}


	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(10000000), testAccount.getBalance());

		testAccount.deposit(new Money(1000, SEK));

		assertEquals(Integer.valueOf(10001000), testAccount.getBalance());
	}
}
