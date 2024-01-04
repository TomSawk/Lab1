package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		String sek_name = SEK.getName();
		String dkk_name = DKK.getName();
//		Throwable nok_name = assertThrows(NullPointerException.class, () -> NOK.getName());
		String eur_name = EUR.getName();

		assertEquals("SEK", sek_name);
		assertEquals("DKK", dkk_name);
//		assertEquals("Currency name is not specified", nok_name.getMessage());
		assertEquals("EUR", eur_name);
	}

	@Test
	public void testGetRate() {
		Double sek_rate = SEK.getRate();
		Double dkk_rate = DKK.getRate();
//		Throwable nok_rate = assertThrows(NullPointerException.class, ()-> NOK.getRate());
		Double eur_rate = EUR.getRate();

		assertEquals((Double)0.15, sek_rate);
		assertEquals((Double)0.20, dkk_rate);
//		assertEquals("Currency rate is not specified", nok_rate.getMessage());
		assertEquals((Double)1.5, eur_rate);
	}

	@Test
	public void testSetRate() {
		Double sek_new_rate = 0.20;
		Double dkk_new_rate = 0.10;

		Double eur_new_rate = 1.25;
		SEK.setRate(sek_new_rate);
		DKK.setRate(dkk_new_rate);
		EUR.setRate(eur_new_rate);

		assertEquals(sek_new_rate, SEK.getRate());
		assertEquals(dkk_new_rate, DKK.getRate());
		assertEquals(eur_new_rate, EUR.getRate());
	}

	@Test
	public void testGlobalValue() {
		Integer sek_global_value = SEK.universalValue(1000); //1000*0.15 = 150
		Integer dkk_global_value = DKK.universalValue(200); //200*0.20 = 40
//		Throwable nok_global_exeption = assertThrows(NullPointerException.class, () -> NOK.universalValue(50)); //50*null = exception
		Integer eur_global_value = EUR.universalValue(666); //666*1.5 = 999

		assertEquals(sek_global_value, (Integer) 150);
		assertEquals(dkk_global_value, (Integer) 40);
//		assertEquals("Currency rate is not specified", nok_global_exeption.getMessage());
		assertEquals(eur_global_value, (Integer) 999);

	}

	@Test
	public void testValueInThisCurrency() {
		// Converting 1000 SEK to DKK
		// 1000 SEK is 150 in universal currency, 150 in universal currency is 750 DKK (since 1 DKK = 0.20)
		Integer sekToDkk = DKK.valueInThisCurrency(1000, SEK);
		// Converting 500 DKK to EUR
		// 500 DKK is 100 in universal currency, 100 in universal currency is approximately 100 EUR (since 1 EUR = 1.5)
		Integer dkkToEur = EUR.valueInThisCurrency(750, DKK);

//		Throwable nok_exeption = assertThrows(NullPointerException.class, () -> SEK.valueInThisCurrency(100, NOK));
		// Converting 100 EUR to SEK
		// 100 EUR is 150 in universal currency, 150 in universal currency is 1000 SEK (since 1 SEK = 0.15)
		Integer eurToSek = SEK.valueInThisCurrency(100, EUR);

		assertEquals((Integer) 750, sekToDkk);
		assertEquals((Integer) 100, dkkToEur);
//		assertEquals("Currency rate is not specified", nok_exeption.getMessage());
		assertEquals((Integer) 1000, eurToSek);

	}
}
