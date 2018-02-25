package CampingReg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Test;

/**********************************************************************
 * JUnit Testing for MyLinkedList.java
 *********************************************************************/
public class TestMyLinkedList {

	/******************************************************************
	 * 
	 * size() Tests
	 * 
	 *****************************************************************/
	@Test
	public void Test_SizeAndInsertBefore_SingleItem_() {

		MyLinkedList<Site> s = new MyLinkedList<Site>();

		// Creates a date
		GregorianCalendar date = null;
		String input[] = ("11/29/2017").split("/");
		int inputInt[] = new int[input.length];
		if (input.length == 3) {
			for (int i = 0; i < input.length; i++)
				inputInt[i] = Integer.parseInt(input[i].trim());
			date = new GregorianCalendar(inputInt[2], inputInt[0] - 1, inputInt[1]);
		}


		try {
			Site testSite = new Site("Jah Vah", date, 4, 1);
			s.add(testSite);
			
			assertEquals( 1, s.size() );
			
			s.insertBefore(1, testSite);
			assertEquals( 2, s.size() );

		} catch (Exception e) {
		}

	}

	/******************************************************************
	 * 
	 * insertBefore() Tests
	 * 
	 *****************************************************************/
	@Test
	public void TestInsertBefore_InvalidIndex() {
		MyLinkedList<Site> s = new MyLinkedList<Site>();
		Node top = new Node();
		top.setData(null);
		Site temp = top.getData();

		try {
			s.insertBefore( -1, temp );

		} catch (IllegalArgumentException e) {

		}
	}


	@Test
	public void TestInsertBefore_NoList() {
		MyLinkedList<Site> s = new MyLinkedList<Site>();
		Node top = new Node();
		top.setData(null);
		Site temp = top.getData();

		try {
			s.insertBefore( 0, temp );

		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void TestInsertBefore_SingleItemList() {
		MyLinkedList<Site> s = new MyLinkedList<Site>();
		Node top = new Node();
		Site temp = top.getData();

	}

}
