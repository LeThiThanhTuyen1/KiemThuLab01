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

    @Test(expected = AccountExistsException.class)
    public void testOpenAccount() throws AccountExistsException {
        SweBank.openAccount("Ulrika"); // Thử mở tài khoản đã tồn tại
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
    	SweBank.deposit("Bob", new Money(10000, SEK)); // Nạp tiền vào tài khoản
        assertEquals(Integer.valueOf(10000), SweBank.getBalance("Bob")); // Kiểm tra số dư
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void testWithdraw() throws AccountDoesNotExistException {
        SweBank.withdraw("NonExistingAccount", new Money(500, SEK)); // Thử rút tiền từ tài khoản không tồn tại
    }

    @Test
    public void testWithdrawValidAccount() throws AccountDoesNotExistException {
        SweBank.deposit("Bob", new Money(10000, SEK));
        SweBank.withdraw("Bob", new Money(10000, SEK));
        assertEquals(Integer.valueOf(0), SweBank.getBalance("Bob"));
    }
    
    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        assertEquals(Integer.valueOf(0), SweBank.getBalance("Bob")); // Kiểm tra số dư của tài khoản mới mở
    }
    
    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        SweBank.deposit("Bob", new Money(20000, SEK)); // Nạp tiền vào tài khoản nguồn
        SweBank.transfer("Bob", "Ulrika", new Money(10000, SEK)); // Chuyển tiền từ SweBank đến SweBank
        assertEquals(Integer.valueOf(10000), SweBank.getBalance("Bob")); // Kiểm tra số dư tại Bob        
        assertEquals(Integer.valueOf(10000), SweBank.getBalance("Ulrika")); // Kiểm tra số dư tại Ulrika
    }
    
    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK)); // Nạp tiền vào tài khoản nguồn
        SweBank.addTimedPayment("Ulrika", "Payment1", 2, 1, new Money(1000, SEK), Nordea, "Bob"); // Thêm thanh toán định kỳ
        SweBank.tick(); // Tích hợp thời gian
        assertEquals(Integer.valueOf(10000), SweBank.getBalance("Ulrika")); // Kiểm tra số dư tại Ulrika sau lần tick đầu tiên
        SweBank.tick(); // Tích hợp thời gian một lần nữa để thanh toán
        assertEquals(Integer.valueOf(9000), SweBank.getBalance("Ulrika")); // Kiểm tra số dư tại Ulrika sau thanh toán
        assertEquals(Integer.valueOf(1000), Nordea.getBalance("Bob")); // Kiểm tra số dư tại Nordea sau thanh toán
    }
}
