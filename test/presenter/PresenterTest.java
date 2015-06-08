package presenter;

import static org.junit.Assert.*;
import org.junit.Test;

public class PresenterTest {
	//I am just full of example tests right now.
	//Once we've explained and understand them, they'll be removed.
	
	@Test
	public void testAssertTrueExample() {
		boolean example = true;
		assertTrue(example);
	}
	
	@Test
	public void testAssertFalseExample() {
		boolean example = false;
		assertFalse(example);
	}
	
	@Test
	public void testAssertEqualsExample() {
		String example = "I am a test";
		assertEquals("I am a test", example);
	}
	
	@Test
	public void testCreatePresenterObjectAndCallItExample() {
		Presenter presenter = new Presenter();
		int number = 5;
		assertEquals(10, presenter.returnNumberTimes2(number));
	}
}
