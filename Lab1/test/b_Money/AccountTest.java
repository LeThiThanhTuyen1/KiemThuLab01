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
        // Thêm một thanh toán định kỳ
        testAccount.addTimedPayment("payment1", 30, 5, new Money(1000, SEK), SweBank, "Alice");
        assertTrue("Timed payment should exist after being added", testAccount.timedPaymentExists("payment1"));

        // Xóa thanh toán định kỳ
        testAccount.removeTimedPayment("payment1");
        assertFalse("Timed payment should not exist after being removed", testAccount.timedPaymentExists("payment1"));
    }
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
	    // Thêm một thanh toán định kỳ
	    testAccount.addTimedPayment("payment1", 1, 1, new Money(1000, SEK), SweBank, "Alice");

	    // Tăng thời gian để thực hiện thanh toán theo thời gian
	    testAccount.tick();  // Lần đầu gọi tick, next sẽ giảm từ 1 xuống 0
	    SweBank.tick();      // Tăng thời gian của ngân hàng
	    
	    // Gọi tick lần nữa để thực hiện thanh toán
	    testAccount.tick();  // Lần thứ hai gọi tick, thanh toán sẽ được thực hiện
	    SweBank.tick();      // Tăng thời gian của ngân hàng

	    // Tạo một đối tượng Money mới với giá trị mong đợi và đơn vị tiền tệ tương ứng
	    Money expectedBalance = new Money(9999000, SEK);

	    // Kiểm tra số dư của tài khoản kiểm thử
	    assertEquals("Số dư phải giảm sau khi thanh toán định kỳ", expectedBalance.getAmount(), testAccount.getBalance().getAmount());

	    // Kiểm tra số dư của Alice trong SweBank
	    assertEquals("Số dư của Alice phải tăng sau khi nhận thanh toán định kỳ", Integer.valueOf(1001000), SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
	    // Thêm tiền vào tài khoản
	    testAccount.deposit(new Money(5000, SEK));
	    assertEquals("Balance should increase after deposit", Integer.valueOf(10005000), testAccount.getBalance().getAmount());

	    // Rút tiền từ tài khoản
	    testAccount.withdraw(new Money(2000, SEK));
	    assertEquals("Balance should decrease after withdrawal", Integer.valueOf(10003000), testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
	    // Kiểm tra số dư ban đầu
	    assertEquals("Initial balance should be correct", Integer.valueOf(10000000), testAccount.getBalance().getAmount());

	    // Thêm tiền và kiểm tra lại số dư
	    testAccount.deposit(new Money(5000, SEK));
	    assertEquals("Balance should be correct after deposit", Integer.valueOf(10005000), testAccount.getBalance().getAmount());
	}

}
