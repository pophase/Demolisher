package hexagon;

/**
 * @author hypno
 * @date 19.09.2011
 */
public class Node implements Comparable<Node> {
	public HexCell cell;
	public int cost;
	public double heuristic;
	public int total;
	Node parent;

	public Node(HexCell cell, int cost, double heuristic, Node parent) {
		this.cell = cell;
		this.cost = cost;
		this.heuristic = heuristic;
		this.parent = parent;
	}

	public int compareTo(Node node) {
		return (int) (heuristic - node.heuristic);
	}

	public HexCell getHexCell() {
		return cell;
	}
}
