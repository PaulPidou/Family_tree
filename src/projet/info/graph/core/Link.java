package projet.info.graph.core;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class Link {
	protected Node from, to;

	public Link(Node from, Node to) {
		this.from = from;
		this.to = to;
		this.from.attach(this);
	}

	public boolean isBetween(Node from, Node to) {
		return this.from == from && this.to == to;
	}

	public void detach() {
		from.detach(this);
	}
	
	public String toString() {
		return "Link(" + from.getName() + "->" + to.getName() + ")";
	}

	public Node source() { return from; }
	public Node destination() { return to; }
}