package com.test.astar.fileoperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.java.astar.fileoperations.FileOperation;

/**
 * JUnit class for FileOperation.class
 * 
 * @author sAGaR
 *
 */
public class FileOperationTest {
	public FileOperation fileOperation = FileOperation.getFileInstane();
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
	 * Test case for read file to get graph object, for valid data in file.
	 * In this scenario we'll get a graph object which will not be null.
	 */
	@Test
	public void readFileAndGetGraphForValidInputTest(){
		String filePath = "src/com/test/astar/fileoperations/valid_data.txt";
		char graph[][] = fileOperation.readFileAndGetGraph(filePath);
		
		assertNotNull(graph);
	}
	
	/**
	 * Test case for read file to get graph object, when no file is presented at location.
	 * In this scenario graph object will be null and console will print an 
	 * error 'File Not Found, please provide the correct path.'.
	 */
	@Test
	public void readFileAndGetGraphForFileNotFoundException(){
		String filePath = "src/com/test/astar/fileoperations/no_file.txt";
		char graph[][] = fileOperation.readFileAndGetGraph(filePath);
		if(graph != null){
			assertFalse(true);
		}
		String expectedResult = "File Not Found, please provide the correct path.";
		String actualResult = consoleError.toString().trim();
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case for read file to get graph object, when file contains invalid data.
	 * In this scenario graph object will be null and console will print an 
	 * error 'Array index out of bound, inconsistency in column size not a m*n graph.'.
	 */
	@Test
	public void readFileAndGetGraphForArrayIndexOutOfBoundsException(){
		String filePath = "src/com/test/astar/fileoperations/Invalid_data.txt";
		char graph[][] = fileOperation.readFileAndGetGraph(filePath);
		if(graph != null){
			assertFalse(true);
		}
		String expectedResult = "Array index out of bound, inconsistency in column size not a m*n graph.";
		String actualResult = consoleError.toString().trim();
		
		assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test case for write resulted graph object to file, for valid scenario.
	 * In this case a output file will be created to specified output path.
	 */
	@Test
	public void writeOutputToFileTest(){
		// writing some demo data to the file.
		char[][] graph = {
				{'@'},
				{'X'}
		};
		String outputFile = "src/com/test/astar/fileoperations/testOutputFile.txt";
		fileOperation.writeOutputToFile(graph, outputFile);
		
		// checking if the file exists, if doesn't exist test case will fail.
		FileInputStream fis = null;
		try {
			File file = new File(outputFile);
			fis = new FileInputStream(file);
			file.deleteOnExit();
		} catch (FileNotFoundException e) {
			// failing the test case if no file exit.
			assertTrue(false);
		} finally{
			try {
				if(fis != null)
					fis.close();
			} catch (IOException e) {
			}
		}
		
		assertTrue(true);
	}
	
}
