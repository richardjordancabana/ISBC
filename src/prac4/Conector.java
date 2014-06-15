package prac4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;
import es.ucm.fdi.gaia.ontobridge.test.gui.PnlSelectInstance;


public class Conector {
	
	//nuestra url
	private static final String URL = "http://www.owl-ontologies.com/Ontology1402519238.owl";
	
	private OntoBridge ontologia;
	
	
	public Conector(){
		ontologia = new OntoBridge();
		ontologia.initWithPelletReasoner();// usaremos el razonador de pellet
		//nuestro fichero owl
		OntologyDocument mainOnto = new OntologyDocument(URL,"file:Ejercicio2.owl");
		ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();	
		ontologia.loadOntology(mainOnto, subOntologies, false);
		
	}
	
	public JPanel getPanelOntologia() {
		return new PnlSelectInstance(ontologia);
	}
	
	
}
