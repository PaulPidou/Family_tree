package projet.info.parser;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import projet.info.graph.core.Network;
import projet.info.nodes.Personne;

/**
 * This class parse the XML file and load the data
 * in virtual graph "Network".
 * 
 * @author Lahoucine Ballihi
 *
 * Created on 10 mars 2014
 */
public class MyHandler extends DefaultHandler {

	boolean tagFname = false;
	boolean tagLname = false;
	boolean tagEmail = false;
	boolean tagDep = false;
	boolean tagSalary = false;
	boolean tagAddress = false;

	private ArrayList<Personne> maListe = new ArrayList<Personne>();
	private Network graphe;
	private Personne pere;

	public MyHandler(Network n) {
		this.graphe = n;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (attributes.getLength() > 0) {

			if (qName.equals("personne")) {
				pere = new Personne();
			} else if (qName.equals("fils")) {
				System.out
						.println(maListe.get(Integer.parseInt(attributes
								.getValue(0)))
								+ " <---> "
								+ maListe.get(Integer.parseInt(attributes
										.getValue(1))));
				graphe.connect(
						maListe.get(Integer.parseInt(attributes.getValue(0))),
						maListe.get(Integer.parseInt(attributes.getValue(1))));
			}
		}

		if (qName.equalsIgnoreCase("firstname")) {
			tagFname = true;
		}

		if (qName.equalsIgnoreCase("lastname")) {
			tagLname = true;
		}

		if (qName.equalsIgnoreCase("email")) {
			tagEmail = true;
		}

		if (qName.equalsIgnoreCase("department")) {
			tagDep = true;
		}

		if (qName.equalsIgnoreCase("salary")) {
			tagSalary = true;
		}

		if (qName.equalsIgnoreCase("address")) {
			tagAddress = true;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {

		if (tagFname) {
			// System.out.println(new String(ch, start, length));
			pere.setName(new String(ch, start, length));
			tagFname = false;
		}

		if (tagLname) {
			// System.out.println(new String(ch, start, length));
			tagLname = false;
		}

		if (tagEmail) {
			// System.out.println(new String(ch, start, length));
			tagEmail = false;
		}

		if (tagDep) {
			// System.out.println(new String(ch, start, length));
			tagDep = false;
		}

		if (tagSalary) {
			// System.out.println(new String(ch, start, length));
			tagSalary = false;
		}

		if (tagAddress) {
			// System.out.println(new String(ch, start, length));
			tagAddress = false;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equals("personne")) {
			graphe.addNode(pere);
			maListe.add(pere);
			pere = null;
		}
	}
}
