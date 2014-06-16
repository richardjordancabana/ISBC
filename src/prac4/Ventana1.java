package prac4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.Color;

import javax.swing.JButton;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;


import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Ventana1 extends JFrame {
	enum tiposBusqueda {rey, familia, trabajo, romantica, hermanos};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<ImageIcon> listFotos;
	
	private JList<String> listR1;
	private JList<String> listR2;

	private JPanel contentPane;
	
	private static Conector conector;
	
	static JPanel panOnt;
	
	JComboBox<String> comboBoxFoto;
	JComboBox<String> comboBoxAparece;
	JComboBox<String> comboBoxProp;
	
	JLabel labelFoto;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		conector = new Conector();
		panOnt = conector.getPanelOntologia();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana1 frame = new Ventana1(panOnt);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana1(JPanel panOnt) {
		setTitle("Ontologias");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOntologias = panOnt;
		contentPane.add(panelOntologias, BorderLayout.WEST);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(tabbedPane);
		
		JPanel panelMarcado = new JPanel();
		tabbedPane.addTab("Marcado", null, panelMarcado, null);
		panelMarcado.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFotografia = new JPanel();
		panelMarcado.add(panelFotografia, BorderLayout.CENTER);
		
		labelFoto = new JLabel("Foto");
		panelFotografia.add(labelFoto);
		
		JPanel panel_3 = new JPanel();
		panelMarcado.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new MigLayout("", "[grow]", "[][][][][][grow]"));
		
		JLabel lblFoto = new JLabel("Foto:");
		panel_3.add(lblFoto, "cell 0 0");
		
		comboBoxFoto = new JComboBox<String>();
		comboBoxFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFoto((String)comboBoxFoto.getSelectedItem());
			}
		});
		panel_3.add(comboBoxFoto, "cell 0 1,growx");
		
		JLabel lblAparece = new JLabel("Aparece:");
		panel_3.add(lblAparece, "cell 0 2");
		
		comboBoxProp = new JComboBox(new String[]{"aparece_en","esta_en"});
		comboBoxProp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFoto((String)comboBoxFoto.getSelectedItem());
			}
		});
		panel_3.add(comboBoxProp, "cell 0 3,growx");
		
		comboBoxAparece = new JComboBox<String>();
		comboBoxAparece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFoto((String)comboBoxFoto.getSelectedItem());
			}
		});
		panel_3.add(comboBoxAparece, "cell 0 4,growx");
		
		JPanel panel_2 = new JPanel();
		panel_3.add(panel_2, "cell 0 5,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		
		JButton btnMarca = new JButton("Marca");
		btnMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marcarFoto();
				mostrarFoto((String)comboBoxFoto.getSelectedItem());
			}
		});
		panel_2.add(btnMarca, "flowx,cell 0 0");
		
		JLabel lblPersonas = new JLabel("Personas:");
		panel_2.add(lblPersonas, "cell 0 1");
		
		JScrollPane scrollPane_r1 = new JScrollPane();
		scrollPane_r1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_r1.setPreferredSize(new Dimension(150,120));
		panel_2.add(scrollPane_r1, "flowy,cell 0 2,aligny top");
		
		JLabel lblLugares = new JLabel("Lugares:");
		panel_2.add(lblLugares, "cell 0 2");
		
		JScrollPane scrollPane_r2 = new JScrollPane();
		scrollPane_r2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_r2.setPreferredSize(new Dimension(150,120));
		panel_2.add(scrollPane_r2, "cell 0 2,aligny top");
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		listR1 = new JList<String>(model);
		listR1.setForeground(Color.BLACK);
		listR1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//listR1.setModel(new DefaultListModel<String>());
		scrollPane_r1.setViewportView(listR1);
		
		DefaultListModel<String> model2 = new DefaultListModel<String>();
		listR2 = new JList<String>(model2);
		listR2.setForeground(Color.BLACK);
		listR2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_r2.setViewportView(listR2);
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarMarca();
				mostrarFoto((String)comboBoxFoto.getSelectedItem());
			}
		});
		panel_2.add(btnElimina, "cell 0 0");
				
		
		//BUSQUEDA
		
		JPanel panelBusqueda = new JPanel();
		tabbedPane.addTab("Busqueda", null, panelBusqueda, null);
		panelBusqueda.setLayout(new MigLayout("", "[grow][][][][][][][][][][][][][][][][][][grow]", "[grow][][][][][]"));
		
		JPanel panel_1 = new JPanel();
		panelBusqueda.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[]", "[][][][][]"));
		
		JButton btnFotosDelRey = new JButton("Fotos del Rey");
		btnFotosDelRey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaFotos(tiposBusqueda.rey);
			}
		});
		panel_1.add(btnFotosDelRey, "cell 0 0,growx");
		
		JButton btnFotosFamiliares = new JButton("Fotos familiares");
		panel_1.add(btnFotosFamiliares, "cell 0 1,growx");
		
		JButton btnFotosDeTrabajo = new JButton("Fotos de trabajo");
		btnFotosDeTrabajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaFotos(tiposBusqueda.trabajo);
			}
		});
		panel_1.add(btnFotosDeTrabajo, "cell 0 2,growx");
		
		JButton btnFotosRomnticas = new JButton("Fotos romanticas");
		panel_1.add(btnFotosRomnticas, "cell 0 3,growx");
		
		JButton btnFotosDeHermanos = new JButton("Fotos de hermanos");
		btnFotosDeHermanos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaFotos(tiposBusqueda.hermanos);
			}
		});
		panel_1.add(btnFotosDeHermanos, "cell 0 4,growx");
		btnFotosRomnticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaFotos(tiposBusqueda.romantica);
			}
		});
		btnFotosFamiliares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaFotos(tiposBusqueda.familia);
			}
		});
		
		JPanel panel = new JPanel();
		panelBusqueda.add(panel, "cell 1 0 9 1,grow");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		
		DefaultListModel<ImageIcon> model3 = new DefaultListModel<ImageIcon>();
		listFotos = new JList<ImageIcon>(model3);
		listFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFotos.setCellRenderer(new MiCellRenderer());
		scrollPane.setViewportView(listFotos);
		
		//Inicializacion boxes
		loadCBFotos();
		loadCBItems();
	}
	
	
	//
	public void loadCBFotos(){
		List<String> items;
		items = conector.getInstanciasClase("Foto");
		for (int i=0;i<items.size();i++)
			comboBoxFoto.addItem(items.get(i));
	}
	
	public void loadCBItems(){
		List<String> items = conector.getInstanciasClase("Lugar");
		if (comboBoxProp.equals("aparece_en")){
			;
		} else if (comboBoxProp.equals("esta_en")) {
			items = conector.getInstanciasClase("Familia");
		}		
		comboBoxAparece.removeAllItems();
		for (int i=0;i<items.size();i++)
			comboBoxAparece.addItem(items.get(i));
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	//
	public List<String> imagenesDe(tiposBusqueda tB){
		List<String> fotos = new ArrayList<String>();
		switch (tB){
		case rey: return conector.getImagenesRey();
		case trabajo: return conector.getImagenesDespacho();
		case familia: return conector.getImagenesFamiliares();
		case hermanos: return conector.getImagenesHermanos();
		case romantica: return conector.getImagenesRomanticas();
		default: return fotos;
		}
	}
	
	public void cargarListaFotos(tiposBusqueda tB){
		((DefaultListModel<ImageIcon>)listFotos.getModel()).removeAllElements();
		List<String> items = imagenesDe(tB);
		for (int i=0;i<items.size();i++){	
			String nombre = items.get(i);
			ImageIcon icono = new ImageIcon(conector.getUrl(nombre));
			
			((DefaultListModel<ImageIcon>)listFotos.getModel()).addElement(icono);
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void cargarListasMarcas() {
		//Marcas 1
		((DefaultListModel<String>)listR1.getModel()).removeAllElements();
		List<String> items = conector.getDatosImagen((String)comboBoxFoto.getSelectedItem(), (String)"aparece_en");
		int i = 0;
		for (i=0;i<items.size();i++){	
			String nombre = items.get(i);
			((DefaultListModel<String>)listR1.getModel()).addElement(nombre);
		}
		//Marcas 2
		((DefaultListModel<String>)listR2.getModel()).removeAllElements();
		List<String> items2 = conector.getDatosImagen((String)comboBoxFoto.getSelectedItem(), (String)"esta_en");
		for (i=0;i<items2.size();i++){	
			String nombre2 = items2.get(i);
			((DefaultListModel<String>)listR2.getModel()).addElement(nombre2);
		}
	}
	
	public void marcarFoto(){
		conector.marcar((String)comboBoxFoto.getSelectedItem(),(String)comboBoxProp.getSelectedItem(),(String)comboBoxAparece.getSelectedItem());
		cargarListasMarcas();
	}
	
	public void eliminarMarca(){
		int index = listR1.getSelectedIndex();
		if (index > -1)
			conector.desmarcar((String)comboBoxFoto.getSelectedItem(),(String)comboBoxProp.getSelectedItem(),(String)comboBoxAparece.getSelectedItem());
		cargarListasMarcas();
	}
	
	public void mostrarFoto(String url){
		ImageIcon icono = new ImageIcon(conector.getUrl(url));
		icono = new ImageIcon(icono.getImage().getScaledInstance(600, 420, Image.SCALE_DEFAULT));
		labelFoto.setIcon(icono);
		cargarListasMarcas();
	}
}


/*DefaultListModel model = new DefaultListModel();
listFotos = new JList<ImageIcon>(model);
listFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
listFotos.setCellRenderer(new MiCellRenderer());
scrollPane.setViewportView(listFotos);*/