package projet.info.actions;

import java.util.Vector;
import org.eclipse.swt.widgets.Display;
import projet.info.commandInterpreter.CommandLine;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */

public class AddNode implements CommandLine.ICommand {
	@Override
	public boolean doIt(Vector v) {
		System.out.println("Running the command : "+v.elementAt(0).toString());
		Display myDisplay = HandlingBinaryTrees.getDisplay();
		myDisplay.asyncExec(new Runnable() {
			public void run() {
				HandlingBinaryTrees grapher = new HandlingBinaryTrees();
				grapher.addNode();
				grapher.showGraph();
			}
		});
		return true;
	}
}
