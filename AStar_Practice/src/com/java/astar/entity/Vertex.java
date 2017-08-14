package com.java.astar.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a bean class which represents a single vertex
 * and keep all of the information related to it. 
 * It implements comparable interface to arrange the neighbor nodes based on their cost value.
 * 
 * @author sAGaR
 *
 */
public class Vertex implements Comparable<Vertex>{
	private char symbol;
	private int rowIndex;
	private int columnIndex;
	private int cost;
	private Vertex parent;
	private List<Vertex> neighbors;
	
	public Vertex(char symbol, int rowIndex, int columnIndex, int cost) {
		this.symbol = symbol;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.cost = cost;
		neighbors = new ArrayList<Vertex>(); 
	}
	
	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	/**
	 * @return the columnIndex
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	/**
	 * @param columnIndex the columnIndex to set
	 */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	/**
	 * @return the parent
	 */
	public Vertex getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Vertex parent) {
		this.parent = parent;
	}
	/**
	 * @return the neighbors
	 */
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
	@Override
	public int compareTo(Vertex node) {
		int diff = this.getCost() - node.getCost();
		if(diff == 0)
			return -1;
		else
			return diff; 
	}
	
}
