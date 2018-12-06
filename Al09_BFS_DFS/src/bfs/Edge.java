package bfs;

public class Edge {
	int uniq;
	int color;
	int distance;
	Edge predecessor;
	
	public Edge(int uniq, int color, Edge predecessor) {
		this.uniq=uniq;
		this.color = color;
		this.distance = -1;
		this.predecessor = predecessor;
	}
}
