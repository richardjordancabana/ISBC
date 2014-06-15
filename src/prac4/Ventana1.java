package prac4;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

public class Ventana1 extends JFrame {
	enum tiposBusqueda {rey, familia, trabajo, romantica, hermanos};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<ImageIcon> listFotos;

	private JPanel contentPane;
	
	private static Conector conector;
	
	static JPanel panOnt;

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
		setBounds(100, 100, 1000, 550);
		
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
		
		JPanel panelBusqueda = new JPanel();
		tabbedPane.addTab("Busqueda", null, panelBusqueda, null);
		panelBusqueda.setLayout(new MigLayout("", "[grow][][][][][][][][][][][][][][][][][][grow]", "[grow][][][][][]"));
		
		JPanel panel_1 = new JPanel();
		panelBusqueda.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[]", "[][][][][]"));
		
		JButton btnFotosDelRey = new JButton("Fotos del Rey");
		btnFotosDelRey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarResultado(/*XXX*/ 1,tiposBusqueda.rey);
			}
		});
		panel_1.add(btnFotosDelRey, "cell 0 0,growx");
		
		JButton btnFotosFamiliares = new JButton("Fotos familiares");
		panel_1.add(btnFotosFamiliares, "cell 0 1,growx");
		
		JButton btnFotosDeTrabajo = new JButton("Fotos de trabajo");
		btnFotosDeTrabajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarResultado(/*XXX*/ 1,tiposBusqueda.trabajo);
			}
		});
		panel_1.add(btnFotosDeTrabajo, "cell 0 2,growx");
		
		JButton btnFotosRomnticas = new JButton("Fotos romanticas");
		panel_1.add(btnFotosRomnticas, "cell 0 3,growx");
		
		JButton btnFotosDeHermanos = new JButton("Fotos de hermanos");
		btnFotosDeHermanos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarResultado(/*XXX*/ 1,tiposBusqueda.hermanos);
			}
		});
		panel_1.add(btnFotosDeHermanos, "cell 0 4,growx");
		btnFotosRomnticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarResultado(/*XXX*/ 1,tiposBusqueda.romantica);
			}
		});
		btnFotosFamiliares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarResultado(/*XXX*/ 1,tiposBusqueda.familia);
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
		
		DefaultListModel model = new DefaultListModel();
		listFotos = new JList<ImageIcon>(model);
		listFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFotos.setCellRenderer(new MiCellRenderer());
		scrollPane.setViewportView(listFotos);
		
		
		

	}
	
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
	
	public void cargarResultado(int panel, tiposBusqueda tB){
		if (panel == 0){
			/*
			((DefaultListModel<String>)JList_resultado1.getModel()).removeAllElements();
			List<String> items = conector.getInfoFoto((String)JComboBox_tipoMarca.getSelectedItem(), (String)JComboBox_fotos.getSelectedItem());
			for (int i=0;i<items.size();i++){	
				String nombre = items.get(i);
				((DefaultListModel<String>)JList_resultado1.getModel()).addElement(nombre);
			}
			*/
		}
		else{
			((DefaultListModel<ImageIcon>)listFotos.getModel()).removeAllElements();
			List<String> items = imagenesDe(tB);
			for (int i=0;i<items.size();i++){	
				String nombre = items.get(i);
				ImageIcon icono = new ImageIcon(conector.getUrl(nombre));
				
				((DefaultListModel<ImageIcon>)listFotos.getModel()).addElement(icono);
			}
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
}


/*DefaultListModel model = new DefaultListModel();
listFotos = new JList<ImageIcon>(model);
listFotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
listFotos.setCellRenderer(new MiCellRenderer());
scrollPane.setViewportView(listFotos);*/