package projet.info.graph.core;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class Node {
	protected String name;
	protected Set<Link> outgoing;

	public Node(String name) {
		this.name = name;
		this.outgoing = new HashSet<Link>();
	}

	public String getName() {
		return name;
	}

	public void attach(Link l) {
		this.outgoing.add(l);
	}

	public void detach(Link l) {
		this.outgoing.remove(l);
	}

	public String toString() {
		return "[" + name + "]";
	}

}
