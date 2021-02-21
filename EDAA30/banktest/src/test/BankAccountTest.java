package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bank.BankAccount;

class BankAccountTest {
	private BankAccount theAccount;

	@BeforeEach
	void setUp() {
		theAccount = new BankAccount();
	}

	@AfterEach
	public void tearDown() {
		theAccount = null;
	}

	@Test
	void testGetBalance() {
		assertEquals(0, theAccount.getBalance(), "New account, balance not 0");
	}

	@Test
	void testDeposit() {
		theAccount.deposit(100);
		assertEquals(100, theAccount.getBalance(), "Wrong balance after deposit");
	}

	@Test
	void testWithdraw() {
		theAccount.deposit(100);
		theAccount.withdraw(20);
		assertEquals(80, theAccount.getBalance(), "Wrong balance after withdraw");
	}

	@Test
	void testOverDraft() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> theAccount.withdraw(200));
		assertEquals("Overdraft", exception.getMessage());
	}
	
	@Test
	void testDepositrNegativeArgumentrDraft() {
		assertThrows(IllegalArgumentException.class, () -> theAccount.deposit(-200));
	}
	
	@Test
	void testWithdrawNegativArgumentrDraft() {
		assertThrows(IllegalArgumentException.class, () -> theAccount.withdraw(-200));
	}
	
	@Test
	void testManyDepositAndWithdrawals() {
		Random rand = new Random(); 
		int expectedBalance = 0;
		for (int i = 0; i < 100; i++) {
			int n = rand.nextInt(1000) + 500;
			theAccount.deposit(n);
			expectedBalance += n;
		}
		assertEquals(expectedBalance, theAccount.getBalance(), "Wrong balance after many deposits");
		for (int i = 0; i < 100; i++) {
			int n = rand.nextInt(200) + 1;
			theAccount.withdraw(n);
			expectedBalance -= n;
		}	
		assertEquals(expectedBalance, theAccount.getBalance(), "Wrong balance after many withrawals");
		
	}

}
