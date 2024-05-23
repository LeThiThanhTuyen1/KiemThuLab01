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
        NOK = new Currency("NOK", 0.18);
        EUR = new Currency("EUR", 1.5);
    }

    @Test
    public void testGetName() {
    	assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
        assertEquals("NOK", NOK.getName());
        assertEquals("EUR", EUR.getName());
    }

    @Test
    public void testGetRate() {
    	assertEquals(0.15, SEK.getRate(), 0.001);
        assertEquals(0.20, DKK.getRate(), 0.001);
        assertEquals(0.18, NOK.getRate(), 0.001);
        assertEquals(1.5, EUR.getRate(), 0.001);
    }

    @Test
    public void testSetRate() {
    	SEK.setRate(0.16);
        assertEquals(0.16, SEK.getRate(), 0.001);

        DKK.setRate(0.22);
        assertEquals(0.22, DKK.getRate(), 0.001);

        NOK.setRate(0.19);
        assertEquals(0.19, NOK.getRate(), 0.001);

        EUR.setRate(1.45);
        assertEquals(1.45, EUR.getRate(), 0.001);
    }

    @Test
    public void testUniversalValue() {
        // Test if the universalValue method works correctly.
        assertEquals((Integer)30000, SEK.universalValue(200000)); // 2000.00 SEK
        assertEquals((Integer)40000, DKK.universalValue(200000)); // 2000.00 DKK
        assertEquals((Integer)36000, NOK.universalValue(200000)); // 2000.00 NOK
        assertEquals((Integer)300000, EUR.universalValue(200000)); // 2000.00 EUR
    }
    
    public void testValueInThisCurrency() {
        // Allow for minor precision errors by specifying an acceptable error range.
        int acceptableErrorRange = 1;

        assertEquals((Integer)266667, SEK.valueInThisCurrency(200000, DKK), acceptableErrorRange); // Convert 2000.00 DKK to SEK
        assertEquals((Integer)120000, DKK.valueInThisCurrency(200000, NOK), acceptableErrorRange); // Convert 2000.00 NOK to DKK
        assertEquals((Integer)16667, NOK.valueInThisCurrency(25000, EUR), acceptableErrorRange); // Convert 250.00 EUR to NOK
        assertEquals((Integer)400000, EUR.valueInThisCurrency(300000, SEK), acceptableErrorRange); // Convert 3000.00 SEK to EUR
    }
}
