package com.test.astar.delegate.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.java.astar.delegate.AStarDelegate;
import com.java.astar.delegate.impl.AStarDelegateImpl;

/**
 * JUnit file for AStarDelegateImpl.class
 * 
 * @author sAGaR
 *
 */
public class AStarDelegateImplTest {
	AStarDelegate service = new AStarDelegateImpl();
	private final ByteArrayOutputStream consoleError = new ByteArrayOutputStream();
	private final ByteArrayOutputStream consoleOutput  = new ByteArrayOutputStream();
	
	/**
	 * Adding console output and console error to System to read it during test case execution.
	 */
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(consoleOutput));
	    System.setErr(new PrintStream(consoleError));
	}

	/**
	 * Cleaning up the console output and errors.
	 */
	@After
	public void cleanUpStreams() {
		System.setOut(null);
	    System.setErr(null);
	}
	
	/**
	 * Test case to get node cost, if the node is mountain '^'
	 * Expected cost is 3.
	 */
	@Test
	public void getNodeCostForMountain(){
		int expectedResult = 3;
		int actualResult = service.getNodeCost('^');
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to get node cost, if the node is Forest '*'
	 * Expected cost is 2.
	 */
	@Test
	public void getNodeCostForForest(){
		int expectedResult = 2;
		int actualResult = service.getNodeCost('*');
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to get node cost, if the node is Flat Lands '.'
	 * Expected cost is 1.
	 */
	@Test
	public void getNodeCostForFlatLands(){
		int expectedResult = 1;
		int actualResult = service.getNodeCost('.');
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to get node cost, if the node is Destination node 'X'
	 * Expected cost is 1.
	 */
	@Test
	public void getNodeCostForDestinationNode(){
		int expectedResult = 1;
		int actualResult = service.getNodeCost('X');
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to get node cost, if the node is invalid
	 * it'll return a null value and console will print an error 'Not a valid node'.
	 */
	@Test
	public void getNodeCostForInvalidNode(){
		char node = '&';
		// null check.
		assertNull(service.getNodeCost(node));
		String expectedResult = "Not a valid node : " + node;
		String actualResult = consoleError.toString().trim();
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to search path between source and destination, if graph in null.
	 * it'll return without doing anything and console will print an error 'There is no node in graph to traverse.'.
	 */
	@Test
	public void searchDestinationNodeInGraphForNullGraphTest(){
		char[][] graph = null;
		int sourceRow = 0;
		int sourceColumn = 0;
		int destinationRow = 0;
		int destinationColumn = 0;
		
		service.searchDestinationNodeInGraph(graph, sourceRow, sourceColumn, destinationRow, destinationColumn);;
		
		String expectedResult = "There is no node in graph to traverse.";
		String actualResult = consoleError.toString().trim();
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to search path between source and destination, if graph have only single node.
	 * it'll return without doing anything and console will print an error 'Path is too short to travel.'.
	 */
	@Test
	public void searchDestinationNodeInGraphForSingleNodeGraphTest(){
		char[][] graph = {
				{'@'}
		};
		
		int sourceRow = 0;
		int sourceColumn = 0;
		int destinationRow = 0;
		int destinationColumn = 0;
		
		service.searchDestinationNodeInGraph(graph, sourceRow, sourceColumn, destinationRow, destinationColumn);;
		
		String expectedResult = "Path is too short to travel.";
		String actualResult = consoleError.toString().trim();
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to search path between source and destination, if no path exist between source and destination in given graph.
	 * Console will print an error 'Reverted back to Starting node, no path found.'.
	 */
	@Test
	public void searchDestinationNodeInGraphForNoPathExistTest(){
		char[][] graph = {
				{'@', '^', '.'},
				{'~', '~', '~'},
				{'~', '^', 'X'}
		};
		
		int sourceRow = 0;
		int sourceColumn = 0;
		int destinationRow = 2;
		int destinationColumn = 2;
		
		service.searchDestinationNodeInGraph(graph, sourceRow, sourceColumn, destinationRow, destinationColumn);;
		
		// If no path is found, it'll return back to source.
		String expectedResult = "Reverted back to Starting node, no path found.";
		String actualResult = consoleError.toString().trim();
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case to search path between source and destination, for a valid graph.
	 * Traversed path will be changed to '#' in the graph, so checking for destination node's symbol.
	 */
	@Test
	public void searchDestinationNodeInGraphForValidGraphTest(){
		char[][] graph = {
				{'@', '^', '.'},
				{'~', '~', '*'},
				{'~', '^', 'X'}
		};
		
		int sourceRow = 0;
		int sourceColumn = 0;
		int destinationRow = 2;
		int destinationColumn = 2;
		
		service.searchDestinationNodeInGraph(graph, sourceRow, sourceColumn, destinationRow, destinationColumn);;
		
		// If completed successfully 'X' will convert to '#'.
		char expectedResult = '#';
		char actualResult = graph[destinationRow][destinationColumn];
		
		assertEquals(expectedResult, actualResult);
	}
}
