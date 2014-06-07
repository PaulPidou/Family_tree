package projet.info.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import projet.info.actions.HandlingBinaryTrees;

/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class main {
	
	/**
	 * NB le premier appel doit avoir lieu dans le thread de l'UI.
	 * 
	 * @return
	 */

	public static void main(final String[] args) throws IOException {
		
		final HandlingBinaryTrees gView = new HandlingBinaryTrees();
		
		Runnable r = new Runnable() {
			public void run() {
				try {
					gView.interpretCommands(args);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r).start();
		
		gView.open();
	}
}
