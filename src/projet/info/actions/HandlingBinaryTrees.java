package projet.info.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import projet.info.commandInterpreter.CommandLine;
import projet.info.graph.core.Link;
import projet.info.graph.core.Network;
import projet.info.graph.core.Node;
import projet.info.parser.MyHandler;
/**
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class HandlingBinaryTrees {

	private static Network myNetwork = new Network();
	private static Display myDisplay = new Display();
	private static Shell myShell = new Shell();
	private static Graph myGraph = new Graph(myShell, SWT.NONE);
	private static ArrayList<GraphNode> listeNoeuds = new ArrayList<GraphNode>();
	private static int nbrNodes = 0;

	public HandlingBinaryTrees(Network network) {
		myNetwork = network;
	}

	public HandlingBinaryTrees() {
	}
	
	public static Display getDisplay() {
		return myDisplay;
	}

	public static Graph getGrapher() {
		return myGraph;
	}


	public void open() {
		
		myShell.setText("Projet Informatique !");
		myShell.setLayout(new FillLayout());		
		myShell.setSize(1080, 640);
		myShell.setVisible(true);
		myShell.open();
		
        myShell.layout();
        
		while (!myShell.isDisposed()) {
			while (!myDisplay.readAndDispatch()) {
				myDisplay.sleep();
			}
		}
		
		myDisplay.dispose();
		System.out.println("Display closed !!!");
		
	}

	public void interpretCommands(String[] args) throws FileNotFoundException {
		
		Reader inputSrc = null;
		boolean interactive = true;
		
		if (args.length > 0) {
			inputSrc = new BufferedReader(new FileReader(args[0]));
		} else {
			inputSrc = new InputStreamReader(System.in);
		}
		
		if ( args.length > 0 ) {
			interactive = false;;
		}
		
		// initialize the command line object.
		CommandLine jr = new CommandLine();
		jr.setCommandLinePrompt("Command > ");
		jr.setCommandLineVersion("Command Line v.01");
		jr.assignClassToCommnd("addNode",
				"projet.info.actions.AddNode");
		jr.assignClassToCommnd("addLink",
				"projet.info.actions.AddLink");
		jr.assignClassToCommnd("loadData",
				"projet.info.actions.LoadData");
		jr.init();
		jr.setIsInteractive(interactive);
		// parse and execute commands.
		jr.parseStream(new StreamTokenizer(inputSrc));
		System.out.println("\nDone.\n");
	}
	
	public void showGraph() {

		myGraph.setLayoutAlgorithm(new TreeLayoutAlgorithm(
				LayoutStyles.NONE), true);
		myGraph.applyLayout();
		myShell.layout();
	}
	
	public void addNode() {
		GraphNode gn = new GraphNode(myGraph, ZestStyles.NONE,
				String.valueOf(nbrNodes));
		gn.setBackgroundColor(ColorConstants.cyan);
		listeNoeuds.add(gn);
		nbrNodes++;
	}
	
	public void addLink() {
		
	}
	
	public void loadData() {
		try{
			// cr�ation d'une fabrique de parseurs SAX
			SAXParserFactory fabrique = SAXParserFactory.newInstance();

			// cr�ation d'un parseur SAX
			SAXParser parseur = fabrique.newSAXParser();

			// lecture d'un fichier XML avec un DefaultHandler
			File fichier = new File("./file.xml");
			DefaultHandler gestionnaire = new MyHandler(myNetwork);
			parseur.parse(fichier, gestionnaire);

		}catch(ParserConfigurationException pce){
			System.out.println("Erreur de configuration du parseur");
			System.out.println("Lors de l'appel � newSAXParser()");
		}catch(SAXException se){
			System.out.println("Erreur de parsing");
			System.out.println("Lors de l'appel � parse()");
		}catch(IOException ioe){
			System.out.println("Erreur d'entr�e/sortie");
			System.out.println("Lors de l'appel � parse()");
		}
		
		
		
		
	}
	
}