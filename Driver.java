import java.util.Scanner;

/** Rial Johnson & Connor Overcast
 *  CSCI Lab 3 (Graph Algorithms)
 *  11/06/2017
 *  
 *  Note: This program requires two separate files for a matrix and labels. By definition, Prim's and Kruskal's
 *  are also unable to handle directed graphs.
 *  
 */

public class Driver {
	
	public static void main(String[] args) {
		
		
		// reads in file names from user
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("This program requires 4 four text files. They are the following: ");
		System.out.println("Undirected Graph (Prim's * Kruskal's) - included is matrix.txt >"); 
		String matrix1 = scanner.nextLine();
		System.out.println("Undirected Graph Labels - included is labels.txt >"); 
		String label1 = scanner.nextLine();
		System.out.println("Directed Graph (Floyd-Warshall's) - included is matrix2.txt >"); 
		String matrix2 = scanner.nextLine();
		System.out.println("Directed Graph Labels - included is labels2.txt >"); 
		String label2 = scanner.nextLine();
		
		// performs algorithms with given matrices
		
		// prims
		Algorithms prim = new Algorithms(matrix1, label1); // new instance of Algorithms
		System.out.println("\nPrim's MST Algorithm");
		System.out.println("--------------------");
		prim.prims();
		
		// kruskal's
		System.out.println("\nKruskal's MST Algorithm");
		System.out.println("--------------------");
		Algorithms krusk = new Algorithms(matrix1, label1); // new instance of Algorithms
		krusk.kruskal();
		
		// floyd's
		System.out.println("\nFloyd's MST Algorithm");
		System.out.println("--------------------");
		Algorithms f = new Algorithms(matrix2, label2); // new instance of Algorithms
		f.floyd();

	}

}
