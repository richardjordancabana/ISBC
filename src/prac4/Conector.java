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
	
	//devuelve las instancias de la clase Persona
	public List<String> getPersonas() {
		List<String> personas = new ArrayList<String>();
		Iterator<String> it= ontologia.listInstances("Persona");
		while (it.hasNext()){
			String nombre = it.next();
			personas.add(nombre);	
		}
		return personas;
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
			ontologia.save("Ejercicio2.owl");	
		}
// elimina una propiedad entre dos instancias
		public void desmarcar(String origen, String prop, String destino) {
			ontologia.deleteOntProperty(origen,prop,destino);
			ontologia.save("Ejercicio2.owl");
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
		    	Iterator<String> familiares =  ontologia.listPropertyValue(imagen,"aparece_en");
		    	boolean parar = false;	int numFamiliares = 0;
		    	while (familiares.hasNext() && !parar){
		    		String nombreFamiliar = recortarNombre(it.next());
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
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece_en");
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
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"esta_en");
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
		    	Iterator<String> personajes =  ontologia.listPropertyValue(imagen,"aparece_en");
	 	    	List<String> personajesFoto = new ArrayList<String>();
	 	    	while (personajes.hasNext()){
	 	    		String persona = recortarNombre(personajes.next());
	 	    		personajesFoto.add(persona);
	 	    		Iterator<String> itParejas = ontologia.listPropertyValue(persona,"tiene_pareja");
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
		
		
		public List<String> getFotoPropiedad(String valor, String propiedad){
			List<String> fotos = new ArrayList<String>();
		    Iterator<String> iteradorFotos = ontologia.listInstances("Foto");
		    while (iteradorFotos.hasNext()){
		    	String foto = iteradorFotos.next();
		    	Iterator<String> iteradorPropiedad =  ontologia.listPropertyValue(foto,propiedad);
		    	boolean esValor = false;
		    	while (iteradorPropiedad.hasNext() && !esValor){
		    		String val = recortarNombre(iteradorPropiedad.next());
		    		esValor = val.equals(valor);
		    	}
		    	if (esValor || valor.equals(""))
		    		fotos.add(recortarNombre(foto));
		    }
		    return fotos;
		}		
		
		
}
