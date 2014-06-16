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
	private static final String URL = "http://www.owl-ontologies.com/Ontology1402876120.owl";
	
	private OntoBridge ontologia;
	
	
	public Conector(){
		ontologia = new OntoBridge();
		ontologia.initWithPelletReasoner();// usaremos el razonador de pellet
		//nuestro fichero owl
		OntologyDocument mainOnto = new OntologyDocument(URL,"file:ontologiaMonarquia.owl");
		ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();	
		ontologia.loadOntology(mainOnto, subOntologies, false);
		
	}
	
	public JPanel getPanelOntologia() {
		return new PnlSelectInstance(ontologia);
	}

	
	public List<String> getInstanciasClase(String clase){
		List<String> lista = new ArrayList<String>();
		Iterator<String> it = ontologia.listInstances(clase);
		while (it.hasNext()){
			String nombre = it.next();
			lista.add(recortarNombre(nombre));	
		}
		return lista;
	}
	
	//parsea el nombre del string(foto)
		private String recortarNombre(String string) {
			return string.substring(string.indexOf('#')+1);
		}
		
		//obtiene la url de la foto
		public String getUrl(String imagen) {
			String string = ontologia.listPropertyValue(imagen,"urlFoto").next();
			String url=string.substring(0,string.indexOf("^^"));
			return url ;
		}
		
		public List<String> getPropiedadImagen(String valor, String propiedad){
			List<String> img = new ArrayList<String>();
		    Iterator<String> iteradorFotos = ontologia.listInstances("Foto");
		    while (iteradorFotos.hasNext()){
		    	String imagen = iteradorFotos.next();
		    	Iterator<String> propiedades =  ontologia.listPropertyValue(imagen,propiedad);
		    	boolean coincide = false;
		    	while (propiedades.hasNext() && !coincide){
		    		String prop = recortarNombre(propiedades.next());
		    	
		    		if(prop.equals(valor))
		    		coincide = true;
		    		else coincide =false;
		    		
		    	}
		    	if (coincide )
		    		img.add(recortarNombre(imagen));
		    /*	if(valor.equals(""))
		    		img.add(recortarNombre(imagen)); */
		    }
		    return img;
		}		
		
		public List<String> getImagenesConsulta(String valor, String propiedad){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece");
		    	boolean coincide = false;
		    	while (personajes.hasNext() && !coincide){
		    		String persona = recortarNombre(personajes.next());
	 	    		Iterator<String> itTitulos = ontologia.listPropertyValue(persona,propiedad);
	 	    		boolean encontrado = false;
	 	    		while (itTitulos.hasNext() && !encontrado){
	 	    			String titulo = recortarNombre(itTitulos.next());
	 	    			encontrado = titulo.equals(valor);
	 	    			coincide = encontrado;
	 	    		}
		    	}
		    	if (coincide )
		    		img.add(recortarNombre(imagen));
		    	/*	if(valor.equals(""))
	    		img.add(recortarNombre(imagen)); */
		    }
		    return img;
		}

		
/*	
 *  LUGARES?!!
  public List<String> dameLugares(){
 
		List<String> personas = new ArrayList<String>();
		Iterator<String> iterador = ob.listInstances("Lugares");
		
		while (iterador.hasNext()){
			String nombre = iterador.next();
			personas.add(nombre);	
		}
		
		return personas;
	}*/
	
	
//crea una propiedad entre dos instancias
		public void marcar(String origen, String prop,String destino) {
			ontologia.createOntProperty(origen,prop,destino);
			ontologia.save("ontologiaMonarquia.owl");	
		}
// elimina una propiedad entre dos instancias
		public void desmarcar(String origen, String prop, String destino) {
			ontologia.deleteOntProperty(origen,prop,destino);
			ontologia.save("ontologiaMonarquia.owl");
		}

		public List<String> getDatosImagen( String imagen,String propiedad){
			List<String> datos = new ArrayList<String>();
			Iterator<String> it = ontologia.listPropertyValue(imagen,propiedad);
			while (it.hasNext()){
				String nombre = it.next();
				nombre = recortarNombre(nombre);
				datos.add(nombre);	
			}
			return datos;
		}	

		
		public List<String> getImagenesFamiliares(){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    //cogemos todas las fotos y preguntamos si salen 3 o mas miembros de la familia
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> familiares =  ontologia.listPropertyValue(imagen,"aparece");
		    	boolean parar = false;	int numFamiliares = 0;
		    	while (familiares.hasNext() && !parar){
		    		String nombreFamiliar = recortarNombre(familiares.next());
		    		if (ontologia.existsInstance(nombreFamiliar,"Familia"))
		    			numFamiliares++;
		    		if (numFamiliares >= 3)
		    			parar = true; //si hay mas de 3 paramos la busqueda
		    	}
		    	
		    	if (parar)
		    		img.add(recortarNombre(imagen));
		    }	    
		    return img;
		}
		
		public List<String> getImagenesRey(){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece");
		    	boolean rey = false;
		    	while (personajes.hasNext() && !rey){
		    		String nombre= recortarNombre(personajes.next());
		    		rey = nombre.equals("Juan_Carlos_I");//si es el rey paramos la busqueda
		    	}
		    	if (rey)
		    		img.add(recortarNombre(imagen));
		    }	    
		    return img;
		}
		
		public List<String> getImagenesDespacho(){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"lugarFoto");
		    	boolean despacho = false;
		    	while (personajes.hasNext() && !despacho){
		    		String lugar = recortarNombre(personajes.next());
		    		despacho = lugar.equals("Despacho");
		    	}
		    	if (despacho)
		    		img.add(recortarNombre(imagen));
		    }	    
		    return img;
		}
		
		public List<String> getImagenesRomanticas(){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece");
	 	    	List<String> personajesFoto = new ArrayList<String>();
	 	    	while (personajes.hasNext()){
	 	    		String persona = recortarNombre(personajes.next());
	 	    		personajesFoto.add(persona);
	 	    		Iterator<String> itParejas = ontologia.listPropertyValue(persona,"pareja_de");
	 	    		boolean hayPareja = false;
	 	    		while (itParejas.hasNext() && !hayPareja){
	 	    			String pareja = recortarNombre(itParejas.next());
	 	    			hayPareja = hayPareja || personajesFoto.contains(pareja);
	 	    		}
	 	    		String nombreImagen =recortarNombre(imagen);
	 	    		if (hayPareja && !(img.contains(nombreImagen)))
	 	    			img.add(nombreImagen);
	 	    	}
	 	    	
		    }
		   return img	;	}

		// corregir hermanoa		
		public List<String> getImagenesHermanos(){
			List<String> img = new ArrayList<String>();
		    Iterator<String> it = ontologia.listInstances("Foto");
		    while (it.hasNext()){
		    	String imagen = it.next();
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece");
	 	    	List<String> personajesFoto = new ArrayList<String>();
	 	    	while (personajes.hasNext()){
	 	    		String persona = recortarNombre(personajes.next());
	 	    		personajesFoto.add(persona);
	 	    		Iterator<String> itHermanos = ontologia.listPropertyValue(persona,"hermano_de");
	 	    		boolean hayHermano = false;
	 	    		while (itHermanos.hasNext() && !hayHermano){
	 	    			String hermano = recortarNombre(itHermanos.next());
	 	    			hayHermano = hayHermano || personajesFoto.contains(hermano);
	 	    		}
	 	    		String nombreImagen =recortarNombre(imagen);
	 	    		if (hayHermano && !(img.contains(nombreImagen)))
	 	    			img.add(nombreImagen);
	 	    	}
	 	    	
		    }
		   return img	;	
		}	
	
		
		
}
