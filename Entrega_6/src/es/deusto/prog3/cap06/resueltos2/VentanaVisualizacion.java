package es.deusto.prog3.cap06.resueltos2;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;

import java.util.*;
import java.util.List;

public class VentanaVisualizacion extends JFrame {

	private JPanel pnlBotones;
	private JPanel pnlIzquierda;
	private JPanel pnlDerecha;
	private JPanel pnlCentro;
	private JPanel pnlMensaje;
	private JPanel pnlTabla;
	private JTree arbol;
	private JTable tabla;
	private JScrollPane scrollArbol;
	private JScrollPane scrollTabla;
	private DefaultTableModel modelo;
	private DefaultTableCellRenderer renderer;
	private JLabel lblMensaje;
	private JPanel pnlContenido;
	
	private DataSetMunicipiosCSV dataset;
	
	public VentanaVisualizacion() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(700,650);
		
		//CREAR UN CONTENT PANE 
		
		pnlContenido = new JPanel();
		pnlContenido.setLayout(new BorderLayout());
		
		setContentPane(pnlContenido);
		
		//CREAR LA BOTONERA DE ABAJO
		
		pnlBotones = new JPanel();
		pnlBotones.setPreferredSize(new Dimension(700,125));
		pnlBotones.setBackground(Color.RED);
		
		pnlContenido.add(pnlBotones, BorderLayout.SOUTH);
		
		//CREAR EL PANEL DE LA IZQUIERDA
		
		pnlIzquierda = new JPanel();
		pnlIzquierda.setBackground(Color.YELLOW);
		pnlIzquierda.setPreferredSize(new Dimension(150,600));
		
		pnlContenido.add(pnlIzquierda, BorderLayout.WEST );
		
		//CREAR EL PANEL DE LA DERECHA
		
		pnlDerecha = new JPanel();
		pnlDerecha.setPreferredSize(new Dimension(150,600));
		pnlDerecha.setBackground(Color.BLUE);
		
		pnlContenido.add(pnlDerecha, BorderLayout.EAST);
		
		//CREAR EL PANEL PARA EL MENSAJE
		
		pnlMensaje = new JPanel();
		pnlMensaje.setPreferredSize(new Dimension(150,60));
		pnlMensaje.setBackground(Color.GREEN);
		pnlMensaje.setLayout(new FlowLayout());
		
		lblMensaje = new JLabel("Mensaje");
		pnlMensaje.add(lblMensaje);
		
		pnlContenido.add(pnlMensaje, BorderLayout.NORTH);
		
		//CREAR EL PANEL DE LA TABLA
		
		pnlTabla = new JPanel();
		pnlTabla.setPreferredSize(new Dimension(250, 415));
		pnlTabla.setBackground(Color.GRAY);
		
		pnlContenido.add(pnlTabla);
		
		arbol.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath camino = e.getPath();
				if (camino.getPathCount()== 3) {
					Object prov = arbol.getLastSelectedPathComponent();
					modelo.setRowCount(0);
					crearDatosDeTabla( prov.toString() );
				}
				
			}
			
		});
		
		
		
		
	}
	
	public void setDatos2 ( DataSetMunicipiosCSV datosMunicipios ) {
		
		dataset = datosMunicipios;
		
		arbol = new JTree();
		tabla = new JTable();
		
		arbol.setPreferredSize(new Dimension(100,600));
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Municipios");
		DefaultTreeModel modeloTree = new DefaultTreeModel(root);
		
		
		HashMap<String, ArrayList<String>> map = dataset.mapaProvinciasComunidad();
		
		scrollArbol = new JScrollPane(arbol);
		scrollTabla = new JScrollPane(tabla);
		
		pnlTabla.add(scrollTabla, BorderLayout.CENTER);
		pnlIzquierda.add(scrollArbol, BorderLayout.CENTER);
		
		
		ArrayList<String> lComunidades = new ArrayList<>(map.keySet());
		
		for(String comunidad : lComunidades) {
			DefaultMutableTreeNode nodoComunidad = new DefaultMutableTreeNode(comunidad);
			modeloTree.insertNodeInto(nodoComunidad, root, 0);
			ArrayList<String> provincias = map.get(comunidad);
			for(int i = 0; i<provincias.size(); i++) {
				DefaultMutableTreeNode prov = new DefaultMutableTreeNode(provincias.get(i));
				modeloTree.insertNodeInto(prov, nodoComunidad, i);
			}
			
			
		}
		
		arbol.setModel(modeloTree);
		
		modelo = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				if(column == 1 || column == 2) {
					return true;
				}else {
					return false;
				}
			}
			
		};
		
		tabla.getTableHeader().setReorderingAllowed( false );
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Nombre");
		modelo.addColumn("Nº Habitantes");
		modelo.addColumn("Población");
		modelo.addColumn("Provincia");
		modelo.addColumn("Comunidad");
		
		tabla.setModel(modelo);
		
		
	}
	
	public void crearDatosDeTabla( String provincia ) {
		ArrayList<String> municipios = dataset.mapaProvinciasMunicipio().get(provincia);
		for( String s: municipios) {
			Municipio mun = dataset.mapaMunicipiosPorNombre().get(s);
			Object[] datoTabla = new Object[] {mun.getCodigo(), mun.getNombre(), mun.getHabitantes(),mun.getProvincia(), mun.getClass() };
			modelo.addRow(datoTabla);
		}
		tabla.repaint();
	}
	
	public static void main(String[] args) {
		
		VentanaVisualizacion vent = new VentanaVisualizacion();
		
		vent.setVisible( true );
		
		
	}
	
}
