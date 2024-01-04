package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(Integer.valueOf(20000), SEK200.getAmount());
		assertEquals(Integer.valueOf(2000), EUR20.getAmount());
		assertEquals(Integer.valueOf(0), SEK0.getAmount());
		assertEquals(Integer.valueOf(0), EUR0.getAmount());
		assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
    }


	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
		assertEquals("200.00 SEK", SEK200.toString());
		assertEquals("20.00 EUR", EUR20.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("0.00 EUR", EUR0.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(1500), EUR10.universalValue());
		assertEquals(Integer.valueOf(3000), SEK200.universalValue());
		assertEquals(Integer.valueOf(3000), EUR20.universalValue());
		assertEquals(Integer.valueOf(0), SEK0.universalValue());
		assertEquals(Integer.valueOf(0), EUR0.universalValue());
		assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		Money SEK100Copy = new Money(10000, SEK);
		Money EUR10Copy = new Money(1000, EUR);

		assertTrue(SEK100.equals(SEK100Copy));
		assertTrue(EUR10.equals(EUR10Copy));
		assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money SEK300 = SEK100.add(SEK200);
		Money EUR30 = EUR10.add(EUR20);

		assertEquals("300.00 SEK", SEK300.toString());
		assertEquals("30.00 EUR", EUR30.toString());
	}

	@Test
	public void testSub() {
		Money SEK100Minus200 = SEK100.sub(SEK200);
		Money EUR20Minus10 = EUR20.sub(EUR10);

		assertEquals("-100.00 SEK", SEK100Minus200.toString());
		assertEquals("10.00 EUR", EUR20Minus10.toString());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(EUR10.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money negatedSEK100 = SEK100.negate();
		Money negatedEUR10 = EUR10.negate();

		assertEquals("-100.00 SEK", negatedSEK100.toString());
		assertEquals("-10.00 EUR", negatedEUR10.toString());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(SEK100));
		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(EUR20.compareTo(EUR10) > 0);
	}
}
