// edges are given by their weight (distance between vertices) and start/end vertices
public class Edge {
	public char labelStart; 
	public char labelEnd;
	public boolean wasVisited; // checks if was visited already in MST
	public int weight;
	
	public Edge(char inLabelStart, char inLabelEnd, int inWeight) // constructor
	{
		labelStart = inLabelStart;
		labelEnd = inLabelEnd;
		weight = inWeight;
	}

	
	// get methods for edge variables
	public int getWeight() {
		return weight;
	}
	
	public char getStart() {
		
		return labelStart;
		
	}
	
	public char getEnd () {
		
		return labelEnd;
		
	}		

}
