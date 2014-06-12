package projet.info.actions;

import java.util.Vector;
import org.eclipse.swt.widgets.Display;
import projet.info.commandInterpreter.CommandLine;

/**
 * Classe de récupération des cousins communs de deux noeuds
 * @author Paul Pidou
 */
public class GetCommonCousins implements CommandLine.ICommand {
	public boolean doIt(Vector v) {
		System.out.println("Running the command : "+v.elementAt(0).toString());
		Display myDisplay = HandlingBinaryTrees.getDisplay();
		try {
			v.elementAt(1);
			v.elementAt(2);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cette commande nécessite deux paramètres : noeud1 noeud2");
			return true;
		}
		
		int firstElement = (int) Double.parseDouble(v.elementAt(1).toString());
		final String node1 = Integer.toString(firstElement);
		int secondElement = (int) Double.parseDouble(v.elementAt(2).toString());
		final String node2 = Integer.toString(secondElement);
		
		myDisplay.asyncExec(new Runnable() {
			public void run() {
				HandlingBinaryTrees grapher = new HandlingBinaryTrees();
				grapher.getCommonCousins(node1, node2);
				grapher.showGraph();
			}
		});
		return true;
	}
}