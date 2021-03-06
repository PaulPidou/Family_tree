package projet.info.actions;

import java.util.Vector;

import org.eclipse.swt.widgets.Display;

import projet.info.commandInterpreter.CommandLine;

/**
 * Classe de récupération des oncles d'un noeud
 * @author Paul Pidou
 */
public class GetUncles implements CommandLine.ICommand {
	public boolean doIt(Vector v) {
		System.out.println("Running the command : "+v.elementAt(0).toString());
		Display myDisplay = HandlingBinaryTrees.getDisplay();
		try {
			v.elementAt(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cette commande nécessite un paramètre : noeud");
			return true;
		}
		
		int firstElement = (int) Double.parseDouble(v.elementAt(1).toString());
		final String node1 = Integer.toString(firstElement);
		
		myDisplay.asyncExec(new Runnable() {
			public void run() {
				HandlingBinaryTrees grapher = new HandlingBinaryTrees();
				grapher.getUncles(node1);
				grapher.showGraph();
			}
		});
		return true;
	}
}