package projet.info.nodes;

import projet.info.graph.core.Node;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class Personne extends Node {

	protected String gender;
	
	public Personne() {
		super("Inconnu");
	}
	
	public Personne(String name) {
		super(name);
	}

	public String getGender() {
		return gender;
	}
	
	public void setName(String name) {
		super.name = name;
	}
}
