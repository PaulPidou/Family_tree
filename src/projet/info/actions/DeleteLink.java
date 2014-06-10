package projet.info.actions;

import java.util.Vector;
import org.eclipse.swt.widgets.Display;
import projet.info.commandInterpreter.CommandLine;

public class DeleteLink implements CommandLine.ICommand {
	public boolean doIt(Vector v) {
		System.out.println("Running the command : "+v.elementAt(0).toString());
		Display myDisplay = HandlingBinaryTrees.getDisplay();
		try {
			v.elementAt(1);
			v.elementAt(2);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cette commande nécessite deux paramètres : source destination");
			return true;
		}
		
		int firstElement = (int) Double.parseDouble(v.elementAt(1).toString());
		int secondElement = (int) Double.parseDouble(v.elementAt(2).toString());
		final String node1 = Integer.toString(firstElement);
		final String node2 = Integer.toString(secondElement);
		
		myDisplay.asyncExec(new Runnable() {
			public void run() {
				HandlingBinaryTrees grapher = new HandlingBinaryTrees();
				grapher.deleteLink(node1, node2);
				grapher.showGraph();
			}
		});
		return true;
	}
}
