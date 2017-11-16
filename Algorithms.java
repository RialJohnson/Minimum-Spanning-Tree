import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Algorithms {

	private int matrix[][]; // our graph
	private String labelString = ""; // used as an array of our labels
	private int labelLength; // dimension of graph
	private Vertex vertexList[]; 
	int parentArray[]; // used for indexes in Kruskal

	// constructor will read in a matrix and the labels in two seperate files
	public Algorithms(String matrixName, String labelName) {
		readLabels(labelName);
		readMatrix(matrixName);
	}

	// reads the labels input file, creates a new vertex for each label, and
	// saves each new vertex into the vertexList field
	private void readLabels(String filePath) {

		try {
			labelString = new Scanner(new File(filePath)).useDelimiter("//A").next();
		} catch (FileNotFoundException exception) { // catches if a file isn't
													// found
			System.out.print("File not found!");
		}

		// gets length for using in creating adjacency matrix
		labelLength = labelString.length();

		// initialize with length of label string
		vertexList = new Vertex[labelLength];

		// adds labels to vertex and stores them in array
		for (int i = 0; i < labelLength; i++) {
			Vertex v = new Vertex(labelString.charAt(i));
			vertexList[i] = v;
		}
	}

	// reads in matrix
	private void readMatrix(String filePath) {

		// matrix should be labelLength * labelLength
		matrix = new int[labelLength][labelLength];

		try {
			Scanner scanner = new Scanner(new File(filePath));

			int current = 0; // use for input stream

			for (int i = 0; i < labelLength; i++) {
				for (int j = 0; j < labelLength; j++) {

					current = scanner.nextInt(); // reads in next int

					if (current <= 1000) {

						matrix[i][j] = current;
						// System.out.print(matrix[i][j] + " ");
					}

					// if the number is infinity
					else {

						matrix[i][j] = 2000;
						if (matrix[i][j] == 2000) {
							// System.out.print("inf ");
						}

					}
				}
			}
		}

		// catches if a file isn't found
		catch (FileNotFoundException exception) {

			System.out.print("File not found!");
		}

		// catches everything else
		catch (IOException exception) {
			System.out.println("Something went terribly wrong!");
		}
	}

	// method to find MST using Prim's Algorithm
	public void prims() {

		// whether vertex is in MST or not
		Boolean inMinSpanTree[] = new Boolean[labelLength]; 

		int mstArray[] = new int[labelLength]; // array to put values in MST		
		int weight[] = new int[labelLength]; // array to store weights
		int totalWeight = 0; // keeps track of total weight of MST

		// sets all weights to infinite and in the MST to false to begin
		for (int i = 0; i < labelLength; i++) {
			weight[i] = 1000; // set to our 'infinite' value
			inMinSpanTree[i] = false; // set all values to false to start
		}

		// the first node should always be included and is the root
		weight[0] = 0;
		mstArray[0] = -1;

		// loops for the algorithm
		for (int j = 0; j < labelLength - 1; j++) {

			// calls method to find the minimum edge not currently in the tree
			int indexOfMin = findMinimum(weight, inMinSpanTree);

			inMinSpanTree[indexOfMin] = true; // now in the MST

			for (int k = 0; k < labelLength; k++) {

				// updates weights and index of parents, completing the algorithm
				if (matrix[indexOfMin][k] != 0 && inMinSpanTree[k] == false && matrix[indexOfMin][k] < weight[k]) {
					mstArray[k] = indexOfMin;
					weight[k] = matrix[indexOfMin][k];
				}
			}
		}

		// prints out the MST using Prim's Algorithm and figures out totalWEight
		System.out.println("Edge  | Weight");
		for (int i = 1; i < labelLength; i++) {
			System.out.println(
					labelString.charAt(mstArray[i]) + " - " + labelString.charAt(i) + "     " + matrix[mstArray[i]][i]);

			totalWeight += matrix[mstArray[i]][i]; // adds on to total weight of MST
		}
		
		// prints total weight of MST
		System.out.println("\nTotal Weight of Prim's MST: " + totalWeight);
	}
	
	// method to find minimum vertex in MST
	public int findMinimum(int weight[], Boolean inMinSpanTree[]) {

		int min = 1000; // sets our current min to our infinite value
		int index = -1;

		for (int i = 0; i < labelLength; i++) {

			if (inMinSpanTree[i] == false && weight[i] < min) {

				min = weight[i]; // change min value to weight
				index = i; // change our index
			}

		}

		return index;

	}

	// Kruskal's MST algorithm implementation, main method
	public void kruskal() {

		// variables for use in algorithm
		int vStart = 0; // index of start vertex (int for comparing)
		int vEnd = 0; // index of start vertex (int for comparing)
		int vWeight = 0; // index of start vertex (int for comparing)

		// finds number of edges in our matrix
		int numEdges = getNumEdges(matrix);

		Edge edgeArray[] = new Edge[numEdges]; // contains edge weight, start
												// vertex, and end vertex

		// ensures no duplicate edges are added for undirected graph
		int start = 0;
		int count = 0; // used to track index for storage in edgeArray

		// loops through matrix (2d array), adding valid edges to an array
		for (int i = 0; i < labelLength; i++) {
			for (int j = start; j < labelLength; j++) {
				// only adds if there is an edge that is not 0 or inf (1000+)
				if (matrix[i][j] > 0 && matrix[i][j] < 1000) {

					// creates an edge
					Edge e = new Edge(labelString.charAt(i), labelString.charAt(j), matrix[i][j]);
					edgeArray[count] = e;
					count++;
				}
			}
			start++; // shifts start position to prevent the same edge being
						// added twice
		}

		// initializes parent array with a little extra room
		parentArray = new int[labelLength * labelLength];

		// loops through giving values to parent array
		for (int n = 0; n < labelLength * labelLength; n++) {
			parentArray[n] = n;
		}

		// creates an array list to store (and sort) edges
		ArrayList<Edge> kruskEdges = new ArrayList<Edge>();

		// inputs all edges into the arrayList
		for (int i = 0; i < numEdges; i++) {
			kruskEdges.add(new Edge(edgeArray[i].labelStart, edgeArray[i].labelEnd, edgeArray[i].weight));
		}

		// sorts the edges of the array by weight, as a priority queue would
		Collections.sort(kruskEdges, new Comparator<Edge>() {

			public int compare(Edge startVertex, Edge endVertex) {
				return startVertex.weight - endVertex.weight;
			}
		});
		
		// formatting for output
		System.out.println("Edge  | Weight");

		// variables for comparing and finding unions
		int totalWeight = 0;
		int edgesInTree = 0;
		int treeIndex = 0;

		// labelLength is equal to number of vertices/nodes
		// loops for edges in graph - 1
		while ((edgesInTree < labelLength - 1) || (treeIndex < numEdges)) {
			// we brake the edge into the three integers they describe it
			
			// typecasts to int and subtract from ascii value to get index 
			vStart = ((int) kruskEdges.get(treeIndex).labelStart) - 64; 
			vEnd = ((int) kruskEdges.get(treeIndex).labelEnd) - 64; 
																	
			vWeight = kruskEdges.get(treeIndex).weight;

			// checks for cycle by checking if the nodes are in the same tree
			if (checkTrees(vStart) != checkTrees(vEnd)) {
				
				addTrees(vStart, vEnd); // adds two trees
				totalWeight += vWeight; // increments total weight of MST
				edgesInTree++; // increments number of edges in MST (while dependent)
				
				// prints out an edge for MST
				System.out.println(labelString.charAt(vStart - 1) + " - " + labelString.charAt(vEnd - 1) + "     " + vWeight);
			}
			
			treeIndex++; // increments index of edge to check in tree
		}

		// prints total weight of MST
		System.out.println("\nTotal Weight of Kruskal's MST: " + totalWeight);
	}
	
	// a method to check if two vertices are in the same tree (Kruskal)
	public int checkTrees(int x) {
		if (parentArray[x] == x) {
			return x;
		}
		return checkTrees(parentArray[x]);
	}

	// unites two sub-trees together (Kruskal)
	public void addTrees(int iStart, int iEnd) {
		int start = checkTrees(iStart);
		int end = checkTrees(iEnd);
		parentArray[start] = end;
	}
	
	// method to find number of edges connecting vertices in a matrix (Kruskal)
	public int getNumEdges(int[][] inMatrix) {

		int[][] matrix = inMatrix;

		int numEdges = 0;

		// finds number of edges in the matrix
		for (int i = 0; i < labelLength; i++) {
			for (int j = 0; j < labelLength; j++) {
				if (matrix[i][j] < 1000 && matrix[i][j] > 0) {
					numEdges++;
				}
			}
		}

		numEdges = numEdges / 2; // divides by two since undirected matrix

		return numEdges; // returns an int of number of edges

	}

	// algorithm for Floyd-Warshall MST
	public void floyd() {
		
		int stepCount = 1; // counter variable for number of steps in the algorithm
		
		// matrix to hold solutions of Floyd's MST
		int floydMatrix[][] = new int[labelLength][labelLength]; 
		
		// this matrix will start he same as our input matrix
		for (int i = 0; i < labelLength; i++) {
			for (int j = 0; j < labelLength; j++) {
				floydMatrix[i][j] = matrix[i][j];
			}
		}

		// prints out the initial matrix (same as first step)
		System.out.println("Initial Matrix: ");
		printFloyd(floydMatrix);

		// triple for loop of matrix dimension to teest every value against each ohter
		for (int k = 0; k < labelLength; k++) {
			for (int i = 0; i < labelLength; i++) {
				for (int j = 0; j < labelLength; j++) {
					
					// finds the shortest path and updates the matrix
					if ((floydMatrix[i][k] + floydMatrix[k][j]) < floydMatrix[i][j]) {
						floydMatrix[i][j] = floydMatrix[i][k] + floydMatrix[k][j];

						// prints out the intermediate matrix
						System.out.println("Step #" + stepCount);
						printFloyd(floydMatrix);
						stepCount++; // increments counter variable for number of steps
					}
				}
			}
		}

		// prints out our final solution matrix (same as last step)
		System.out.println("\n\nThe Final Solution: ");
		printFloyd(floydMatrix);
	}

	// method to print matrix for floyd's algorithm
	public void printFloyd(int floydMatrix[][]) {

		// formatting for nice tables
		for (int i = 0; i < labelLength; i++) {
			if (i < 1) {
				System.out.print("     " + labelString.charAt(i));
			} else
				System.out.print("   " + labelString.charAt(i));
		}

		System.out.println();// formatting
		
		// loops through and prints values
		for (int i = 0; i < labelLength; ++i) {

			System.out.print(" " + labelString.charAt(i) + "   ");

			for (int j = 0; j < labelLength; ++j) {
				if (floydMatrix[i][j] > 1000)
					System.out.print("INF "); // prints infinite for our values of 1000+
				else
					System.out.print(floydMatrix[i][j] + "   "); // otherwise prints the value
			}
			System.out.println(); // formatting
		}
		System.out.println();// formatting
	}
}
