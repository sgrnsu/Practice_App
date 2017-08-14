package com.test.astar.application;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.java.astar.application.AStar;

/**
 * JUnit class for AStar.class
 * 
 * @author sAGaR
 *
 */
public class AStarTest {
	private final ByteArrayOutputStream consoleError = new ByteArrayOutputStream();
	
	/**
	 * Adding console error to System to read it during test case execution.
	 */
	@Before
	public void setUpStreams() {
	    System.setErr(new PrintStream(consoleError));
	}

	/**
	 * Cleaning up the console errors.
	 */
	@After
	public void cleanUpStreams() {
	    System.setErr(null);
	}
	
	/**
	 * Test case to get source and destination coordinates for valid scenario.
	 * In this scenario there will be no error on console.
	 */
	@Test
	public void getSourceAndDestinationCoordinatesForValidScenarioTest(){
		// Valid input
		char[][] graph = {
				{'@', '^', '.'},
				{'~', '~', '*'},
				{'~', '^', 'X'}
		};
		// Method will return true.
		Assert.assertTrue(AStar.getSourceAndDestinationCoordinates(graph));
		
		String expectedResult = "";
		String actualResult = consoleError.toString().trim();
		// If there is any error printed on console fail the test case.
		Assert.assertEquals(actualResult, expectedResult);
	}
	
	/**
	 * Test case to get source and destination coordinates for In-valid scenario where no source is given.
	 * In this case console will print error 'Source or destination is not defined properly.'
	 */
	@Test
	public void getSourceAndDestinationCoordinatesWithNoSourceSpecifiedTest(){
		// Invalid input no source '@' is specified.
		char[][] graph = {
				{'~', '^', '.'},
				{'~', '~', '*'},
				{'~', '^', 'X'}
		};
		// Method will return false.
		Assert.assertFalse(AStar.getSourceAndDestinationCoordinates(graph));

		String expectedResult = "Source or destination is not defined properly.";
		String actualResult = consoleError.toString().trim();
		
		Assert.assertEquals(actualResult, expectedResult);
	}
	
	/**
	 * Test case to get source and destination coordinates for In-valid scenario where no destination is given.
	 * In this case console will print error 'Source or destination is not defined properly.'
	 */
	@Test
	public void getSourceAndDestinationCoordinatesWithNoDestinationSpecifiedTest(){
		// Invalid input no destination 'X' is specified.
		char[][] graph = {
				{'@', '^', '.'},
				{'~', '~', '*'},
				{'~', '^', '~'}
		};
		// Method will return false.
		Assert.assertFalse(AStar.getSourceAndDestinationCoordinates(graph));

		String expectedResult = "Source or destination is not defined properly.";
		String actualResult = consoleError.toString().trim();
		
		Assert.assertEquals(actualResult, expectedResult);
	}
}
