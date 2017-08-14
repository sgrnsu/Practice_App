package com.java.astar.delegate.impl;

import java.util.Collections;

import com.java.astar.delegate.AStarDelegate;
import com.java.astar.entity.Vertex;

/**
 * It's a implementation class for delegate interface,
 * which is basically searching the shortest path between source and destination.
 * 
 * @author sAGaR
 *
 */
public class AStarDelegateImpl implements AStarDelegate{
	public int graphRowLength;
	public int graphColumnLength;
	public int destinationXIndex;
	public int destinationYIndex;
	
	// This boolean array will keep the track of all the nodes which are already traversed to avoid cycle. 
	public static boolean[][] traversed;
	
	@Override
	public Integer getNodeCost(char node){
		switch(node){
			case '^':
				return 3;
			case '*':
				return 2;
			case '.':
				return 1;
			case 'X':
				return 1;
			default:
				System.err.println("Not a valid node : " + node);
				return null;
		}
	}

	@Override
	public void searchDestinationNodeInGraph(char[][] graph, int sourceXIndex,
			int sourceYIndex, int destinationXIndex, int destinationYIndex) {
		if(graph == null){
			System.err.println("There is no node in graph to traverse.");
			return;
		}
		// If there are less than 2 node in graph then return
		if(graph.length < 2 && graph[0].length < 2){
			System.err.println("Path is too short to travel.");
			return;
		}
		// Conditional flag, whether to check for neighbors or not.
		boolean checkForNeighborFlag = true;
		graphRowLength = graph.length;
		graphColumnLength = graph[0].length;
		
		// setting up the global variable, so that it can be used in different methods
		this.destinationXIndex = destinationXIndex;
		this.destinationYIndex = destinationYIndex;
		traversed = new boolean[graphRowLength][graphColumnLength];
		
		// Starting traversing the graph from source node.
		char symbol = graph[sourceXIndex][sourceYIndex];
		Vertex currentNode = new Vertex(symbol, sourceXIndex, sourceYIndex, 0);
		currentNode.setParent(null);
		
		// Making traverse true for the Source node, so loop will not cycle back to the same.
		traversed[sourceXIndex][sourceYIndex] = true;

		while(currentNode != null && currentNode.getSymbol() != 'X'){
			// check whether to traverse the neighbors or not, if neighbor already traversed no need to do it again.
			if(checkForNeighborFlag){
				checkNeighbor(graph, currentNode);
			}
			sourceXIndex = currentNode.getRowIndex();
			sourceYIndex = currentNode.getColumnIndex();
			
			if(currentNode.getNeighbors().size() > 0){
				// Moving pointer to the first node in list, as it'll have the least cost.
				currentNode = currentNode.getNeighbors().remove(0);
				// Changing the symbol to '#' after moving to next node, to keep track of the path.
				graph[sourceXIndex][sourceYIndex] = '#';
				// If it is new node, then needs to check the neighbors.
				checkForNeighborFlag = true;
			}
			else{
				// Changing graph's node symbol to the original form, as now it'll take different route.
				graph[sourceXIndex][sourceYIndex] = currentNode.getSymbol();
				currentNode = currentNode.getParent();
				// If reverting back to parent no need to check for neighbor again, as parent already have a list of neighbor.
				checkForNeighborFlag = false;
			}
		}
		
		if(currentNode == null)
			System.err.println("Reverted back to Starting node, no path found.");
		else{
			// As 'X' nodes index has found, changing 'X' to '#' to complete the path.
			graph[destinationXIndex][destinationYIndex] = '#';
		}
	}
	
	/**
	 * Checking for all the possible neighbor for corresponding vertex,
	 * And if they are not traversed earlier adding them to the neighbor list of that vertex.
	 * 
	 * @param graph This is a 2D m*n graph.
	 * @param currentNode This is the object of 'Vertex' class which have current node information.
	 */
	private void checkNeighbor(char[][] graph, Vertex currentNode){
		int row = currentNode.getRowIndex();
		int column = currentNode.getColumnIndex();
		
		// checking all possible neighbor based on the priority.
		if(row > 0 && column > 0)
			addNeighbor(row-1, column-1, currentNode, graph);
		
		if(row > 0)
			addNeighbor(row-1, column, currentNode, graph);
		
		if(column > 0)
			addNeighbor(row, column-1, currentNode, graph);
		
		if(column > 0 && row < graphRowLength-1){
			addNeighbor(row+1, column-1, currentNode, graph);
		}
		
		if(column < graphColumnLength-1 && row > 0)
			addNeighbor(row-1, column+1, currentNode, graph);
		
		if(column < graphColumnLength-1){
			addNeighbor(row, column+1, currentNode, graph);
		}
		
		if(row < graphRowLength-1)
			addNeighbor(row+1, column, currentNode, graph);

		if(column < graphColumnLength-1 && row < graphRowLength-1){
				addNeighbor(row+1, column+1, currentNode, graph);
		}
		
		// Sorting the item of list based on the cost, i.e. minimum cost neighbor will be the first item.
		Collections.sort(currentNode.getNeighbors());
	}
	
	/**
	 * Contains the logic to add the neighbor to the current node list,
	 * takes neighbor's row and column index as input and add it to the current node.
	 * 
	 * @param rowIndex This is row-coordinate value for the current node.
	 * @param columnIndex This is column-coordinate value for the current node.
	 * @param currentNode This is the object of 'Vertex' class which have current node information.
	 * @param graph This is a 2D m*n graph.
	 */
	private void addNeighbor(int rowIndex, int columnIndex, Vertex currentNode, char[][] graph){
		int cost;
		Vertex child = null;
		char node = graph[rowIndex][columnIndex];
		
		// If neighbor node is not water '~' and haven't traversed earlier, add it to the neighbor's list of current node.
		if(node != '~' && !traversed[rowIndex][columnIndex]){
			// Null check, to check for a valid node.
			if(getNodeCost(node) != null){
				// Getting the cost to reach destination node from the neighbor node using 'Manhattan distance formula'.
				cost = getNodeCost(node) + Math.abs(destinationXIndex-rowIndex) + Math.abs(destinationYIndex-columnIndex);
				child = new Vertex(node, rowIndex, columnIndex, cost);
				child.setParent(currentNode);
				// Adding child node to the neighbor's list of currentNode.
				currentNode.getNeighbors().add(child);
			}
			traversed[rowIndex][columnIndex] = true;
		}
	}

}
