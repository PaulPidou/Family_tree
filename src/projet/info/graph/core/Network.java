package projet.info.graph.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class Network {
	protected List<Node> nodes;
	protected List<Link> links;

	public Network() {
		nodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
	}

	public void addNode(Node n) {
		nodes.add(n);
	}

	public void connect(Node n1, Node n2) {
		if (n1 == n2) {
			throw new IllegalArgumentException(
					"Connexion d'un nÅ“ud Ã  lui-mÃªme");
		}
		// Si le graphe est orienté, désactiver la deuxième ligne
		links.add(this.makeLink(n1, n2));
		// links.add(this.makeLink(n2, n1));
	}

	public void disconnect(Node n1, Node n2) {
		List<Link> toRemove = new LinkedList<Link>();
		for (Link l : links) {
			if (l.isBetween(n1, n2)) {
				toRemove.add(l);
			}
		}
		for (Link l : toRemove) {
			links.remove(l);
			l.detach();
		}
	}

	protected Link makeLink(Node from, Node to) {
		return new Link(from, to);
	}

	public List<Node> nodes() {
		return nodes;
	}

	public List<Link> links() {
		return links;
	}

	public void clear() {
		nodes.clear();
		links.clear();
	}
}
