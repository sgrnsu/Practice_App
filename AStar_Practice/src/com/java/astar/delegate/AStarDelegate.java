package com.java.astar.delegate;

/**
 * Delegate interface declaring key methods.
 * 
 * @author sAGaR
 *
 */
public interface AStarDelegate {
	/**
	 * Find the cost of a particular vertex.
	 * 
	 * @param node This is node whose cost needs to be find.
	 * @return int This is cost for that particular node.
	 */
	public Integer getNodeCost(char node);
	
	/**
	 * Search for the shortest path between source node and destination node.
	 * 
	 * @param graph This is a 2D m*n graph.
	 * @param sourceXIndex This is row-coordinate value for the source node.
	 * @param sourceYIndex This is column-coordinate value for the source node.
	 * @param destinationXIndex This is row-coordinate value for the destination node.
	 * @param destinationYIndex This is column-coordinate value for the destination node.
	 */
	public void searchDestinationNodeInGraph(char[][] graph, int sourceXIndex, int sourceYIndex, int destinationXIndex, int destinationYIndex);
}
