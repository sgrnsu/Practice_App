package com.java.astar.application;

import com.java.astar.delegate.AStarDelegate;
import com.java.astar.delegate.impl.AStarDelegateImpl;
import com.java.astar.fileoperations.FileOperation;

/**
 * AStar class is the entry point for the application, 
 * it initialize the objects and call methods from different class.
 * 
 * @author sAGaR
 *
 */
public class AStar {

	static int sourceXIndex;
	static int sourceYIndex;
	static int destinationXIndex;
	static int destinationYIndex;
	public static boolean[][] traversed;
	
	/**
	 * This is main method, entry point of the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String inputFile = "input.txt";
		String outputFile = "output.txt";
		// Getting the instance of Singleton class FileOperation to execute file related operations.
		FileOperation fileOperation = FileOperation.getFileInstane();
		// Instantiating the delegate file.
		AStarDelegate service = new AStarDelegateImpl();
		
		// Reading the file and getting the 2D m*n graph.
		char[][] graph = fileOperation.readFileAndGetGraph(inputFile);
		
		if(graph != null){
			// Reading the source and destination coordinates and assigning variable.
			if(getSourceAndDestinationCoordinates(graph)){
				// calling the delegate class method to find the path between source and destination.
				service.searchDestinationNodeInGraph(graph, sourceXIndex, sourceYIndex, destinationXIndex, destinationYIndex);
				// Writing the result back to output file.
				fileOperation.writeOutputToFile(graph, outputFile);
				for(int i=0; i<graph.length; i++){
					for(int j=0; j<graph[i].length; j++){
						System.out.print(graph[i][j] +" ");
					}
					System.out.println();
				}
				System.out.println("\nExecution completed, check '" + outputFile +"' file for result.");
			}
		}
	}
	
	/**
	 * Searching for the source and destination coordinates in given graph.
	 * 
	 * @param graph This is a 2D m*n graph.
	 */
	public static boolean getSourceAndDestinationCoordinates(char[][] graph){
		for(int i=0; i<graph.length; i++){
			for(int j=0; j<graph[i].length; j++){
				// Source Check.
				if(graph[i][j] == '@'){
					sourceXIndex = i;
					sourceYIndex = j;
				}
				// Destination Check.
				if(graph[i][j] == 'X'){
					destinationXIndex = i;
					destinationYIndex = j;
					break;
				}
			}
		}
		// If '@' is not available at source and 'X' is not available at destination coordinates, then return false.
		if(graph[sourceXIndex][sourceYIndex] != '@' || graph[destinationXIndex][destinationYIndex] != 'X'){
			System.err.println("Source or destination is not defined properly.");
			return false;
		}
		return true;
	}

}
