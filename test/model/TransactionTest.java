package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
	private Transaction transaction;

	@Before
	public void setUp() {
		transaction = new Transaction();
	}

	@Test
	public void testSetAmountDoesNotAcceptNegativeNumbers() {
		transaction.setAmount(-3);
		assertEquals(transaction.getAmount(), 0.00, 0);
	}

	@Test
	public void testSetAmountDoesNotAccept10000000orHigher() {
		transaction.setAmount(10000000);
		assertEquals(transaction.getAmount(), 0.00, 0);
	}

	@Test
	public void testSetCategoryIdDoesNotAcceptNegativeNumbers() {
		transaction.setCategoryID(-3);
		assertEquals(transaction.getCategoryID(), 0.00, 0);
	}

	@Test
	public void testSetCategoryIdDoesNotAccept100000orHigher() {
		transaction.setCategoryID(100000);
		assertEquals(transaction.getCategoryID(), 0.00, 0);
	}
}
