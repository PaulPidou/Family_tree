package projet.info.actions;

import java.util.Vector;

import org.eclipse.swt.widgets.Display;

import projet.info.commandInterpreter.CommandLine;

/**
 * Classe de chargement d'un fichier XML
 * @author Paul Pidou
 */
public class LoadData implements CommandLine.ICommand {
	public boolean doIt(Vector v) {
		System.out.println("Running the command : "+v.elementAt(0).toString());
		try {
			v.elementAt(1);
		} catch (ArrayIndexOutOfBoundsException e) { // Si il n'y pas de paramètre on charge le fichier par défaut
			System.out.println("On charge le fichier par défaut : file.xml");
			v.add("file.xml");
		}
		final String file = v.elementAt(1).toString();
		Display myDisplay = HandlingBinaryTrees.getDisplay();
		myDisplay.asyncExec(new Runnable() {
			public void run() {
				HandlingBinaryTrees grapher = new HandlingBinaryTrees();
				grapher.loadData(file);
				grapher.showGraph();
			}
		});
		return true;
	}
}
