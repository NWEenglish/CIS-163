package project1;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStopWatch {	

	/*******************************************************************
	 * 
	 * @author Nicholas English
	 * 
	 ******************************************************************/

	// Tests a StopWatch with negative minutes and three parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_1_NE() {
		new StopWatch("-3:2:1");
	}

	/******************************************************************/

	// Test a StopWatch with negative minutes & seconds and three 
	// parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_2_NE() {
		new StopWatch("-3:-2:1");
	}

	/******************************************************************/

	// Tests a StopWatch with negative minutes & milliseconds and
	// three parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_3_NE() {
		new StopWatch("-3:2:-1");
	}

	/******************************************************************/

	// Tests a StopWatch with negative minutes, seconds, & milliseconds
	// and three parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_4_NE() {
		new StopWatch("-3:-2:-1");
	}

	/******************************************************************/

	// Tests a StopWatch with negative seconds and three parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_5_NE() {
		new StopWatch("3:-2:1");
	}

	/******************************************************************/

	// Tests a StopWatch with negative seconds & milliseconds and three
	// parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_6_NE() {
		new StopWatch("3:-2:-1");
	}

	/******************************************************************/

	// Tests a StopWatch with negative milliseconds and three
	// parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegThreeInput_7_NE() {
		new StopWatch("3:2:-1");
	}

	/******************************************************************/

	// Tests the static boolean equals method.
	@Test
	public void testStaticEquals_NE() {
		StopWatch s1 = new StopWatch("4:32:109");
		StopWatch s2 = new StopWatch("6:54:321");

		assertFalse(StopWatch.equals(s1, s2));
	}

	/******************************************************************/

	// Tests that the add method works correctly.
	@Test(expected = IllegalArgumentException.class)
	public void testAddMethod_NE() {
		StopWatch s1 = new StopWatch("8:7:6");
		StopWatch s2 = new StopWatch("1:2:3");

		s1.add(s2);
	}

	/******************************************************************/

	// Tests that the inc method is working.
	@Test
	public void testIncMethod_NE() {
		StopWatch s = new StopWatch("5:00:000");

		s.inc();
		assertEquals(s.toString(), "5:00:001");
	}

	/******************************************************************/

	// Tests that the sub method works correctly.
	@Test(expected = IllegalArgumentException.class)
	public void testSubMethod_NE() {
		StopWatch s1 = new StopWatch("7:6:5");
		StopWatch s2 = new StopWatch("6:5:4");

		s1.sub(s2);
	}

	/******************************************************************/

	// Test that the dec method works correctly.
	@Test
	public void testDecMethod_NE() {
		StopWatch s = new StopWatch(2, 3);
		s.dec();
		assertEquals(s.toString(), "0:02:122");
	}


	/*******************************************************************
	 * 
	 * @author Professor Ferguson
	 * 
	 ******************************************************************/

	// Tests to make ensure the default constructor starts at all zeros.
	@Test
	public void defautContructor() {
		StopWatch s = new StopWatch();
		assertTrue(s.getSeconds() == 0);
		assertTrue(s.getMilliseconds() == 0);
		assertTrue(s.getMinutes() == 0);
	}

	/******************************************************************/

	// Tests the StopWatch if it can accept three parameters.
	@Test
	public void Contructor3Parameters() {
		StopWatch s = new StopWatch(5,2,10);
		assertTrue(s.getSeconds() == 2);
		assertTrue(s.getMilliseconds() == 10);
		assertTrue(s.getMinutes() == 5);
	}

	/******************************************************************/

	// Tests all the StopWatch constructors can accept parameters.
	@Test
	public void testConstructor() {
		StopWatch s = new StopWatch (5,10,300);
		assertEquals(s.toString(),"5:10:300");

		s = new StopWatch("20:10:8");
		assertEquals(s.toString(),"20:10:008");

		s = new StopWatch("20:8");
		assertEquals(s.toString(),"0:20:008");

		s = new StopWatch("8");
		assertEquals(s.toString(),"0:00:008");
	}

	/******************************************************************/

	// Tests a StopWatch with negative milliseconds and two parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegDouble2aInput() {
		new StopWatch("2:-2");

	}

	/******************************************************************/

	// Tests a StopWatch with negative seconds and two parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegDouble2bInput() {
		new StopWatch("-2:7");

	}

	/******************************************************************/

	// Tests a StopWatch with negative seconds & milliseconds and two
	// parameters.
	@Test (expected = IllegalArgumentException.class)
	public void testNegDouble2cInput() {
		new StopWatch("-3:-2");

	}

	/******************************************************************/

	// Tests a StopWatch with negative milliseconds and one parameter.
	@Test (expected = IllegalArgumentException.class)
	public void testNegSingleInput() {
		new StopWatch("-2");

	}

	/******************************************************************/

	// Tests a StopWatch with a character in milliseconds and one
	// parameter.
	@Test (expected = IllegalArgumentException.class)
	public void testAlphaInput() {
		new StopWatch("a");
	}

	/******************************************************************/


	// Tests a StopWatch by counting through it.
	@Test 
	public void testAlotInput() {
		for (int m = 0; m < 50; m++)
			for (int s = 0; s < 60; s++)
				for (int ms = 0; ms < 1000; ms++) {
					String st = m + ":" + s + ":" + ms;
					StopWatch d = new StopWatch(st);
					assertEquals(m, d.getMinutes());
					assertEquals(s, d.getSeconds());
					assertEquals(ms, d.getMilliseconds());					
				}
	}

	/******************************************************************/


	// Tests the add method in a StopWatch.
	@Test
	public void testAddMethod () {
		StopWatch s1 = new StopWatch (5,59,300);
		s1.add(2000);
		assertEquals (s1.toString(),"6:01:300");

		s1 = new StopWatch (5,59,300);
		StopWatch s2 = new StopWatch (2,2,300);
		s1.add(s2);
		System.out.println (s1);
		assertEquals (s1.toString(),"8:01:600");

		for (int i = 0; i < 15000; i++)
			s1.inc();
		System.out.println (s1);
		assertEquals (s1.toString(),"8:16:600");
	}

	/******************************************************************/

	// Tests the equals and compareTo methods.
	@Test 
	public void testEqual () {
		StopWatch s1 = new StopWatch (5,59,300);
		StopWatch s2 = new StopWatch (6,01,200);
		StopWatch s3 = new StopWatch (5,50,200);
		StopWatch s4 = new StopWatch (5,59,300);

		assertFalse(s1.equals(s2));
		assertTrue (s1.equals(s4));

		assertTrue (s2.compareTo(s1) > 0);
		assertTrue (s3.compareTo(s1) < 0);
		assertTrue (s1.compareTo(s4) == 0);

	}

	/******************************************************************/

	// Tests the save and load methods.
	@Test 
	public void testLoadSave () {
		StopWatch s1 = new StopWatch (5,59,300);
		StopWatch s2 = new StopWatch (5,59,300);

		s1.save("file1");
		s1 = new StopWatch ();  // resets to zero

		s1.load("file1");
		assertTrue (s1.equals(s2));
	}

	/******************************************************************/

	// Tests the StopWatch suspend method and equals method.
	// Dev. Note - Currently, this test does NOT work.
	@Test 
	public void testMutate () {
		StopWatch s1 = new StopWatch (5,59,300);
		StopWatch s2 = new StopWatch (5,59,300);

		StopWatch.suspend(true);
		s1.add(1000);

		assertTrue(s1.equals(s2));	
		StopWatch.suspend(false);
	}

	/******************************************************************/

	// Tests the StopWatch equals method.
	@Test 
	public void equalsTest() {
		StopWatch s1 = new StopWatch (5,59,300);
		StopWatch s2 = new StopWatch (5,59,300);

		assertEquals(s1, s2);
	}

	/*******************************************************************
	 * 
	 * @author Zach Thomas
	 * 
	 ******************************************************************/

	// Loads a StopWatch object with a time of 0
	@Test
	public void testLoad0ZT() {
		StopWatch s1 = new StopWatch(0);
		StopWatch s2 = new StopWatch(0);

		s1.save("file1");
		s1 = new StopWatch(); // resets to zero

		s1.load("file1");
		assertTrue(s1.equals(s2));
	}

	/******************************************************************/

	// Try to load a file that doesn't exist
	@Test
	public void testLoadFileZT() {
		StopWatch s1 = new StopWatch(1);
		s1.load("file1");
	}

	/******************************************************************/

	// Throw an exception when more than 60 mins, 60 seconds, and 1000
	// milliseconds are given.
	@Test(expected = IllegalArgumentException.class)
	public void moreAllowedZT() {
		new StopWatch(9999999, 9999999, 9999999);

	}

	/******************************************************************/

	// Throw an exception when more than 60 seconds and 1000
	// milliseconds are given.
	@Test(expected = IllegalArgumentException.class)
	public void moreSecMilAllowedZT() {
		new StopWatch(9999999, 9999999);

	}

	/******************************************************************/

	// Throw an exception when more 1000
	// milliseconds are given.
	@Test(expected = IllegalArgumentException.class)
	public void moreMillAllowedZT() {
		new StopWatch(9999999);

	}

	/******************************************************************/

	// Throw an exception when more than 60 mins, 60 seconds, and 1000
	// milliseconds are given in string form.
	@Test(expected = IllegalArgumentException.class)
	public void moreStringAllowedZT() {
		new StopWatch("9999999:9999999:9999999");

	}

	/******************************************************************/

	// Throw an exception when characters are given.
	@Test(expected = IllegalArgumentException.class)
	public void moreCharsZT() {
		new StopWatch("aaaa:aaaa:aaaa");

	}

	/******************************************************************/

	// Throw an exception when minutes, seconds, or milliseconds
	// are set to negative or when minutes or seconds are over
	// 60.
	@Test(expected = IllegalArgumentException.class)
	public void setVarNegZT () {
		StopWatch s1 = new StopWatch();
		s1.setMinutes(-1);
		s1.setMinutes(70);
		s1.setSeconds(-1);
		s1.setSeconds(70);
		s1.setMilliseconds(-1);
		s1.setMilliseconds(999999999);
	}

}

/**********************************************************************/