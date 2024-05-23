package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
    Currency SEK, DKK, EUR;
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
        assertEquals(Integer.valueOf(0), SEK0.getAmount());
        assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
    }

    @Test
    public void testToString() {
        assertEquals("100.00 SEK", SEK100.toString());
        assertEquals("10.00 EUR", EUR10.toString());
        assertEquals("0.00 SEK", SEK0.toString());
        assertEquals("-100.00 SEK", SEKn100.toString());
    }

    @Test
    public void testUniversalValue() {
        assertEquals(Integer.valueOf(1500), SEK100.universalValue());
        assertEquals(Integer.valueOf(1500), EUR10.universalValue());
        assertEquals(Integer.valueOf(0), SEK0.universalValue());
        assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
    }

    @Test
    public void testEqualsMoney() {
        assertTrue(SEK100.equals(new Money(10000, SEK)));
        assertTrue(EUR10.equals(new Money(1000, EUR)));
        assertFalse(SEK100.equals(EUR10));

        Money convertedEURtoSEK = new Money(EUR10.universalValue(), SEK);
        assertFalse(SEK100.equals(convertedEURtoSEK));
    }

    @Test
    public void testAdd() {
        Money result = SEK100.add(EUR10); // 10000 SEK + 1000 EUR
        assertEquals(new Money(20000, SEK), result); // Expecting 20000 SEK
    }

    @Test
    public void testSub() {
        Money result = SEK100.sub(new Money(500, EUR)); // 10000 SEK - 500 EUR
        assertEquals(new Money(5000, SEK), result); // Expecting 5000 SEK

        result = EUR10.sub(new Money(7500, SEK)); // 1000 EUR - 7500 SEK
        assertEquals(new Money(250, EUR), result); // Expecting 250 EUR

        result = SEK100.sub(SEK100); // 10000 SEK - 10000 SEK
        assertEquals(new Money(0, SEK), result); // Expecting 0 SEK
    }

    @Test
    public void testIsZero() {
        assertTrue(SEK0.isZero());
        assertTrue(EUR0.isZero());
        assertFalse(SEK100.isZero());
        assertFalse(SEKn100.isZero());
    }

    @Test
    public void testNegate() {
        assertEquals(new Money(-10000, SEK), SEK100.negate());
        assertEquals(new Money(10000, SEK), SEKn100.negate());
        assertEquals(new Money(0, SEK), SEK0.negate());
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
        assertTrue(SEK100.compareTo(new Money(5000, SEK)) > 0);
        assertTrue(SEK100.compareTo(new Money(15000, SEK)) < 0);

        assertEquals(0, SEK100.compareTo(new Money(1000, EUR)));
    }
}
