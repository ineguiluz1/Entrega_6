package es.deusto.prog3.cap06.resueltos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Ejercicio06_03 {
	
	private static JFrame ventana;
	private static DataSetMunicipiosCSV dataset;

	private static VentanaVisualizacion ventanaDatos;
	
	public static void main(String[] args) {
		ventana = new JFrame( "Ejercicio 6.3" );
		ventana.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		ventana.setLocationRelativeTo( null );
		ventana.setSize( 200, 80 );

		JButton bCargaMunicipios = new JButton( "Carga municipios > 50k " );
		ventana.add( bCargaMunicipios );
		
		bCargaMunicipios.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargaMunicipios();
			}
		});
		
		ventana.setVisible( true );
	}
	
	private static void cargaMunicipios() {
		try {
			dataset = new DataSetMunicipiosCSV ( "datasetMunicipios50k.csv" );
			System.out.println( "Cargados municipios:" );
			for (Municipio m : dataset.getlMunicipios() ) {
				System.out.println( "\t" + m );
			}
			// TODO Resolver el ejercicio 6.3
			ventanaDatos = new VentanaVisualizacion( );
			ventanaDatos.setDatos2 ( dataset );
			ventanaDatos.setVisible( true );
		} catch (IOException e) {
			System.err.println( "Error en carga de municipios" );
		}
	}
	
}
