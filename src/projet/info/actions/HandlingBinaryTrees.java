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
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
			interactive = false;
		}
		
		// initialize the command line object.
		CommandLine jr = new CommandLine();
		jr.setCommandLinePrompt("Command > ");
		jr.setCommandLineVersion("Command Line v.01");
		jr.assignClassToCommnd("addNode",
				"projet.info.actions.AddNode");
		jr.assignClassToCommnd("addLink",
				"projet.info.actions.AddLink");
		jr.assignClassToCommnd("deleteLink",
				"projet.info.actions.DeleteLink");
		jr.assignClassToCommnd("getChilds",
				"projet.info.actions.GetChilds");
		jr.assignClassToCommnd("getDescendants",
				"projet.info.actions.GetDescendants");
		jr.assignClassToCommnd("getAscendants",
				"projet.info.actions.GetAscendants");
		jr.assignClassToCommnd("getUncles",
				"projet.info.actions.GetUncles");
		jr.assignClassToCommnd("getCousins",
				"projet.info.actions.GetCousins");
		jr.assignClassToCommnd("getCommonUncles",
				"projet.info.actions.GetCommonUncles");
		jr.assignClassToCommnd("getCommonCousins",
				"projet.info.actions.GetCommonCousins");
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
	
	/**
	 * Ajoute un noeud à myGraph et à myNetwork
	 */
	public void addNode() {
		GraphNode gn = new GraphNode(myGraph, ZestStyles.NONE,String.valueOf(nbrNodes));
		gn.setBackgroundColor(ColorConstants.cyan);
		Node node = new Node(String.valueOf(nbrNodes));
		listeNoeuds.add(gn);
		nbrNodes++;
		myNetwork.addNode(node);
	}
	
	/**
	 * Convertit une String reçue en un GraphNode correspondant
	 * @param nodus La String à convertir
	 * @return Le GraphNode correspondant à la String sinon null
	 */
	private GraphNode StringToGraphNode(String nodus) {
		List<GraphNode> listNodes = new ArrayList<GraphNode>();
		listNodes = myGraph.getNodes();
		
		for(GraphNode node : listNodes) {
			if(node.toString().substring(16).equals(nodus)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Ajoute un lien(GraphConnection) à myGraph entre deux noeuds (GraphNode)
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 * @return vrai(true) si la connection à été faite avec sucès ou faux(false) si au moins un des deux noeuds n'a pas été trouvé
	 */
	private boolean addLinkGraph(String node1, String node2) {
		GraphNode source = null, destination = null;
		
		source = StringToGraphNode(node1);
		destination = StringToGraphNode(node2);
		
		if(source != null && destination != null) {
			new GraphConnection(myGraph, ZestStyles.NONE, source, destination);
			return true;
		} else
			return false;
	}
	
	/**
	 * Ajoute un lien(link) à myNetowrk entre deux noeuds (node)
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 * @return vrai(true) si la connection à été faite avec sucès ou faux(false) si au moins un des deux noeuds n'a pas été trouvé
	 */
	private boolean addLinkNetwork(String node1, String node2) {
		Node source = null, destination = null;
		String nodeString = "";
		int found = 2;
		List<Node> listNodes = new ArrayList<Node>();
		listNodes = myNetwork.nodes();
		
		for(Node node : listNodes) {
			nodeString = node.getName();
			if(nodeString.equals(node1)) {
				source = node;
				found--;
			} else if(nodeString.equals(node2)) {
				destination = node;
				found--;
			}
			if(found <= 0) // Leave the loop once we have found the source and the destination
				break;
		}
		
		if(source != null && destination != null) {
			myNetwork.connect(source, destination);
			return true;
		} else
			return false;
	}
	
	/**
	 * Essaye d'ajouter un lien entre deux noeuds et indique si cela a été effectué ou non
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 */
	public void addLink(String node1, String node2) {
		if(addLinkGraph(node1, node2) && addLinkNetwork(node1, node2))
			System.out.println("Liaison créée avec succès.");
		 else
			System.out.println("Paramètres non valides.");
	}
	
	/**
	 * Supprime un lien(GraphConnection) de myGraph entre deux noeuds (GraphNode)
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 * @return vrai(true) si la connection à été supprimé avec sucès ou faux(false) si la connection n'a pas été trouvé
	 */
	private boolean deleteLinkGraph(String node1, String node2) {
		String sourceString, destinationString;
		List<GraphConnection> listConnections = new ArrayList<GraphConnection>();
		listConnections = myGraph.getConnections();
		
		for(GraphConnection gc : listConnections) {
			sourceString = gc.getSource().toString().substring(16);
			destinationString = gc.getDestination().toString().substring(16);

			if(sourceString.equals(node1) && destinationString.equals(node2)) {
				gc.dispose();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Supprime un lien(link) de myNetwork entre deux noeuds (node)
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 * @return vrai(true) si la connection à été supprimé avec sucès ou faux(false) si la connection n'a pas été trouvé
	 */
	private boolean deleteLinkNetwork(String node1, String node2) {
		List<Link> listLinks = new ArrayList<Link>();
		listLinks = myNetwork.links();
		
		for(Link link : listLinks) {
			if(link.source().getName().equals(node1) && link.destination().getName().equals(node2)) {
				myNetwork.disconnect(link.source(), link.destination());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Essaye de supprimer un lien entre deux noeuds et indique si cela a été effectué ou non
	 * @param node1 Le noeud source sous forme de String
	 * @param node2 Le noeud destination sous forme de String
	 */
	public void deleteLink(String node1, String node2) {
		if(deleteLinkGraph(node1, node2) && deleteLinkNetwork(node1, node2))
			System.out.println("Liaison supprimée avec succès.");
		 else
			System.out.println("Paramètres non valides.");
	}
	
	/**
	 * Remet tous les noeuds (GraphNode) de myGraph à la même couleur
	 */
	private void resetColor() {
		List<GraphNode> listNodes = new ArrayList<GraphNode>();
		listNodes = myGraph.getNodes();
		
		for(GraphNode node : listNodes) {
			node.setBackgroundColor(ColorConstants.buttonLightest);
		}
	}
	
	/**
	 * Met le noeud (GraphNode) envoyé en paramètre en rouge
	 * @param node Le noeud à mettre en rouge
	 */
	private void colorSelect(GraphNode node) {
		node.setBackgroundColor(ColorConstants.red);
	}
	
	/**
	 * Met les noeuds (GraphNode) envoyés en paramètre en vert
	 * @param nodes Liste des noeuds à mettre en vert
	 */
	private void colorAll(List<GraphNode> nodes) {
		for(GraphNode node : nodes) {
			node.setBackgroundColor(ColorConstants.green);
		}
	}
	
	/**
	 * Affiche les fils d'un noeud donné
	 * @param node Le noeud dont on doit afficher les enfants
	 */
	private void colorChilds(GraphNode node) {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		List<GraphNode> childs = new ArrayList<GraphNode>();
		
		for(GraphConnection connection : listConnection) {
			if(connection.getSource().equals(node)) {
				childs.add(connection.getDestination()); // On met les enfants de node dans une liste
			}
		}
		colorAll(childs); // On met en vert les enfants
	}

	/**
	 * Récupère les enfants d'un noeud donné
	 * @param nodus Le noeud dont on veut récupérer les enfants
	 */
	public void getChilds(String nodus) {
		GraphNode node = null;
		node = StringToGraphNode(nodus);
		
		if(node != null) {
			resetColor();
			colorSelect(node); // On met en rouge le noeud sélectionné
			colorChilds(node); // On met en vert les enfants du noeud sélectionné
		} else {
			System.out.println("Paramètre non valide."); // On indique que l'on n'a pas trouvé le noeud dont on veut afficher les fils
		}
	}
	
	/**
	 * Met en couleur tous les descendants d'un noeud donné par récursivité
	 * @param node Le noeud dont on veut les descendants
	 * @return Le GraphNode node pour permettre la récursivité
	 */
	private GraphNode colorDescendants(GraphNode node) {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		for(GraphConnection connection : listConnection) {
			if(connection.getSource().equals(node)) {
				connection.getDestination().setBackgroundColor(ColorConstants.green);
				colorDescendants(connection.getDestination());
			}
		}
		return node;
	}
	
	/**
	 * Récupère tous les descendants d'un noeud donné
	 * @param nodus Le noeud dont on veut les descendants
	 */
	public void getDescendants(String nodus) {
		GraphNode node = null;
		
		node = StringToGraphNode(nodus);
		if(node != null) {
			resetColor();
			colorSelect(node); // On met en rouge le noeud sélectionné
			colorDescendants(node); // On met en vert les descendants du noeud sélectionné
		} else {
			System.out.println("Paramètre non valide.");
		}
	}
	
	/**
	 * Met en couleur tous les ascendants d'un noeud donné par récursivité
	 * @param node Le noeud dont on veut les ascendants
	 * @return Le GraphNode node pour permettre la récursivité
	 */
	private GraphNode colorAscendants(GraphNode node) {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		for(GraphConnection connection : listConnection) {
			if(connection.getDestination().equals(node)) {
				connection.getSource().setBackgroundColor(ColorConstants.green);
				colorAscendants(connection.getSource());
			}
		}
		return node;
	}
	
	/**
	 * Récupère tous les ascendants d'un noeud donné
	 * @param nodus Le noeud dont on veut les ascendants
	 */
	public void getAscendants(String nodus) {
		GraphNode node = null;
		
		node = StringToGraphNode(nodus);
		if(node != null) {
			resetColor();
			colorSelect(node);
			colorAscendants(node);
		} else {
			System.out.println("Paramètre non valide.");
		}
	}
	
	/**
	 * Récupère les pères d'un noeud donné
	 * @param node Le noeud (GraphNode) dont on veut les pères
	 * @return La liste contenant les pères du noeud donné
	 */
	private List<GraphNode> getFatherOf(GraphNode node)  {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		List<GraphNode> fathers = new ArrayList<GraphNode>();
		
		for(GraphConnection connection : listConnection) {
			if(connection.getDestination().equals(node)) {
				fathers.add(connection.getSource());
			}
		}
		return fathers;
	}
	
	/**
	 * Récupère les oncles d'un noeud donné
	 * @param node Le noeud (GraphNode) dont on veut les oncles
	 * @return La liste contenant les oncles du noeud donné
	 */
	private List<GraphNode> getUnclesOf(GraphNode node) {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		List<GraphNode> fathers = new ArrayList<GraphNode>();
		List<GraphNode> grandFathers = new ArrayList<GraphNode>();
		List<GraphNode> uncles = new ArrayList<GraphNode>();
		
		fathers = getFatherOf(node); // ON récupère les pères du noeud
		
		for(GraphNode father : fathers) {
			grandFathers.addAll(getFatherOf(father)); // On récupère les grand père du noeud (pères des pères)
		}
		
		for(GraphConnection connection : listConnection) {
			for(GraphNode grandFather : grandFathers) {
				if(connection.getSource().equals(grandFather)) {
					if(!fathers.contains(connection.getDestination())) {
						uncles.add(connection.getDestination());
					}
				}
			}
		}
		return uncles;
	}
	
	/**
	 * Récupère et met en couleurs les oncles d'un noeud donné
	 * @param nodus Le noeud (GraphNode) dont on veut les oncles
	 */
	public void getUncles(String nodus) {	
		List<GraphNode> uncles = new ArrayList<GraphNode>();

		GraphNode node = null;
		node = StringToGraphNode(nodus);
		
		if(node != null) {
			resetColor();
			colorSelect(node);
			uncles = getUnclesOf(node);
			colorAll(uncles);
		} else {
			System.out.println("Paramètre non valide.");
		}
	}
	
	/**
	 * Récupère les cousins d'un noeud donné
	 * @param node Le noeud (GraphNode) dont on veut les cousins
	 * @return La liste contenant les cousins du noeud donné
	 */
	private List<GraphNode> getCousinsOf(GraphNode node) {
		List<GraphConnection> listConnection = new ArrayList<GraphConnection>();
		listConnection = myGraph.getConnections();
		
		List<GraphNode> uncles = new ArrayList<GraphNode>();
		List<GraphNode> cousins = new ArrayList<GraphNode>();
		
		uncles = getUnclesOf(node);
		
		for(GraphConnection connection : listConnection) {
			for(GraphNode uncle : uncles) {
				if(connection.getSource().equals(uncle)) {
					cousins.add(connection.getDestination());
				}
			}
		}
		return cousins;
	}
	
	/**
	 * Récupère et met en couleur les cousins d'un noeud donné
	 * @param nodus Le noeud (GraphNode) dont on veut les cousins
	 */
	public void getCousins(String nodus) {		
		List<GraphNode> cousins = new ArrayList<GraphNode>();
		
		GraphNode node = null;
		
		node = StringToGraphNode(nodus);
		if(node != null) {
			resetColor();
			colorSelect(node);
			cousins = getCousinsOf(node);
			colorAll(cousins);
		} else {
			System.out.println("Paramètre non valide.");
		}
	}
	
	/**
	 * Récupère les oncles en commun de deux noeuds donnés
	 * @param node1 Le noeud (GraphNode) dont on veut les oncles en commun
	 * @param node2 Le noeud (GraphNode) dont on veut les oncles en commun
	 * @return La liste contenant les oncles en commun de noeuds donnés
	 */
	private List<GraphNode> getCommonUnclesOf(GraphNode node1, GraphNode node2) {		
		List<GraphNode> unclesNode1 = new ArrayList<GraphNode>();
		List<GraphNode> unclesNode2 = new ArrayList<GraphNode>();
		
		List<GraphNode> commonUncles = new ArrayList<GraphNode>();
		
		unclesNode1 = getUnclesOf(node1);
		unclesNode2 = getUnclesOf(node2);
		
		for(GraphNode nodus1 : unclesNode1) {
			for(GraphNode nodus2 : unclesNode2) {
				if(nodus1.equals(nodus2)) {
					commonUncles.add(nodus1);
				}
			}
		}
		return commonUncles;
	}
	
	/**
	 * Récupère et met en couleur les oncles en commun de deux noeuds donnés
	 * @param node1 Le noeud (GraphNode) dont on veut les oncles en commun
	 * @param node2 Le noeud (GraphNode) dont on veut les oncles en commun
	 */
	public void getCommonUncles(String node1, String node2) {
		List<GraphNode> commonUncles = new ArrayList<GraphNode>();
		
		GraphNode nodus1 = null, nodus2 = null;
		
		nodus1 = StringToGraphNode(node1);
		nodus2 = StringToGraphNode(node2);
		
		if(nodus1 != null && nodus2 != null) {
			resetColor();
			colorSelect(nodus1);
			colorSelect(nodus2);
			commonUncles = getCommonUnclesOf(nodus1, nodus2);
			colorAll(commonUncles);
		} else {
			System.out.println("Paramètres non valides.");
		}
	}
	
	/**
	 * Récupère les cousins en commun de deux noeuds donnés
	 * @param node1 Le noeud (GraphNode) dont on veut les cousins en commun
	 * @param node2 Le noeud (GraphNode) dont on veut les cousins en commun
	 * @return La liste contenant les cousins en commun de noeuds donnés
	 */
	private List<GraphNode> getCommonCousinsOf(GraphNode node1, GraphNode node2) {
		List<GraphNode> cousinsNode1 = new ArrayList<GraphNode>();
		List<GraphNode> cousinsNode2 = new ArrayList<GraphNode>();
		
		List<GraphNode> commonCousins = new ArrayList<GraphNode>();
		
		cousinsNode1 = getCousinsOf(node1);
		cousinsNode2 = getCousinsOf(node2);
		
		for(GraphNode nodus1 : cousinsNode1) {
			for(GraphNode nodus2 : cousinsNode2) {
				if(nodus1.equals(nodus2)) {
					commonCousins.add(nodus1);
				}
			}
		}
		return commonCousins;
	}
	
	/**
	 * Récupère et met en couleur les cousins en commun de deux noeuds donnés
	 * @param node1 Le noeud (GraphNode) dont on veut les cousins en commun
	 * @param node2 Le noeud (GraphNode) dont on veut les cousins en commun
	 */
	public void getCommonCousins(String node1, String node2) {
		List<GraphNode> commonCousins = new ArrayList<GraphNode>();
		
		GraphNode nodus1 = null, nodus2 = null;
		
		nodus1 = StringToGraphNode(node1);
		nodus2 = StringToGraphNode(node2);
		
		if(nodus1 != null && nodus2 != null) {
			resetColor();
			colorSelect(nodus1);
			colorSelect(nodus2);
			commonCousins = getCommonCousinsOf(nodus1, nodus2);
			colorAll(commonCousins);
		} else {
			System.out.println("Paramètres non valides.");
		}
	}
	
	/**
	 * Charge le fichier XML reçue en paramètre
	 * @param file Le fichier XML que l'on veut charger
	 */
	
	private GraphNode printGraph(GraphNode source, Link current, List<Link> links)  {
		GraphNode destination = new GraphNode(myGraph, ZestStyles.NONE, String.valueOf(current.destination().getName()));
		listeNoeuds.add(destination);
		nbrNodes++;
		new GraphConnection(myGraph, ZestStyles.NONE, source, destination);
		for(Link i : links) {
			if(current.destination().equals(i.source())) {
				printGraph(destination, i, links);
			}
		}
		return source;
	}
	
	public void loadData(String file) {
		try{
			// création d'une fabrique de parseurs SAX
			SAXParserFactory fabrique = SAXParserFactory.newInstance();

			// création d'un parseur SAX
			SAXParser parseur = fabrique.newSAXParser();

			// lecture d'un fichier XML avec un DefaultHandler
			file = "./"+file;
			File fichier = new File(file);
			if(!fichier.exists()) { // Si le fichier n'exite pas on charge le fichier par défaut
				System.out.println("Le fichier spécifié n'existe pas. Chargement du fichier par défaut !");
				fichier = new File("file.xml");
			}
			DefaultHandler gestionnaire = new MyHandler(myNetwork);
			parseur.parse(fichier, gestionnaire);
			
		}catch(ParserConfigurationException pce){
			System.out.println("Erreur de configuration du parseur");
			System.out.println("Lors de l'appel à newSAXParser()");
		}catch(SAXException se){
			System.out.println("Erreur de parsing");
			System.out.println("Lors de l'appel à parse()");
		}catch(IOException ioe){
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("Lors de l'appel à parse()");
		}
		
		List<Link> links = new ArrayList<Link>();
		links = myNetwork.links();
		
		if(links.size() > 0) {
			GraphNode source = new GraphNode(myGraph, ZestStyles.NONE, String.valueOf(links.get(0).source().getName()));
			listeNoeuds.add(source);
			nbrNodes++;
			
			for(Link link : links) {
				if(link.source().equals(links.get(0).source()))
					printGraph(source, link, links);
			}
			resetColor();
		}
	}
	
}
