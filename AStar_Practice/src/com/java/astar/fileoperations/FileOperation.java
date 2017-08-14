package com.java.astar.fileoperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class, which contains file related (read/write) operations.
 * 
 * @author sAGaR
 *
 */
public class FileOperation {
	private static volatile FileOperation instance = null;
	private FileOperation() {
	}
	
	/**
	 * Only way to create the instance of FileOperation class by calling this method. 
	 * 
	 * @return instance This return instance of the singleton class FileOperation.
	 */
	public static FileOperation getFileInstane(){
		if(instance == null){
			synchronized (FileOperation.class) {
				if(instance == null){
					instance = new FileOperation();
				}
			}
		}
		return instance;
	}
	
	/**
	 * This method read file and prepare 2D graph.
	 * 
	 * @param filePath This is the path of the input file.
	 * @return graph This returns 2D graph given in the file.
	 */
	public char[][] readFileAndGetGraph(String filePath){
		BufferedReader bufferedReader = null;
		FileInputStream inputStream = null;
		char[][] graph = null;
		int index = 0;
		// Using ArrayList to get exact length of graph.
		List<String> fileData = new ArrayList<>();
		try {
			inputStream = new FileInputStream(new File(filePath));
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			while(bufferedReader.ready()){
				fileData.add(bufferedReader.readLine());
			}
			
			int rowLength = fileData.size();
			int columngLength = fileData.get(0).length();
			// Assigning the row and column length to graph array, which will be the size of ArrayList 
			graph = new char[rowLength][columngLength];
			for(String line : fileData){
				// if current line is less or greater than the previous one throw exception.
				if(line.length() != columngLength){
					throw new ArrayIndexOutOfBoundsException();
				}
				graph[index++] = line.toCharArray();
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Array index out of bound, inconsistency in column size not a m*n graph.");
			return null;
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found, please provide the correct path.");
			return null;
		} catch (IOException e) {
			System.err.println("Exception occurs while reading the file.");
			return null;
		} finally{
			try {
				if(bufferedReader != null)
					bufferedReader.close();
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				System.err.println("Exception occurs while closing the BufferedReader or FileInputStream connection.");
			}
		}
		return graph;
	}
	
	/**
	 * This method writes the found path of the graph to a output file,
	 * It's a synchronized method so that multiple thread can not write to the file simultaneously.
	 * 
	 * @param graph This is the result after finding shortest path.
	 * @param filePath This is the path of the Output file.
	 */
	public synchronized void writeOutputToFile(char[][] graph, String filePath){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(new File(filePath));
			for(int i=0; i<graph.length; i++){
				fileWriter.write(graph[i]);
				fileWriter.write("\r\n");
			}
		} catch (IOException e) {
			System.err.println("Exception occurs while writing the file using FileWriter.");
		} finally{
			try {
				if(fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				System.err.println("Exception occurs while closing the FileWriter connection.");
			}
		}
	}
}
