package es.deusto.prog3.cap06.resueltos2;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class DataSetMunicipiosCSV {

	private List<Municipio> lMunicipios = new ArrayList<>();
	
	public DataSetMunicipiosCSV ( String fichero ) throws IOException {
		
		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
		
		fr = new FileReader( new File( fichero ) );
		br = new BufferedReader ( fr );
		
		String linea;
		
		while( ( linea = br.readLine() ) != null) {
			
			String[] campos = linea.split(";");
			int codigo = Integer.parseInt( campos[0] );
			String nombre = campos[1];
			int habitantes = Integer.parseInt( campos[2] );
			String provincia = campos[3];
			String autonomia = campos[4];
			Municipio muni = new Municipio( codigo, nombre, habitantes, provincia, autonomia );
			lMunicipios.add(muni);
			
		}
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public List<Municipio> getlMunicipios() {
		return lMunicipios;
	}



	public void setlMunicipios(List<Municipio> lMunicipios) {
		this.lMunicipios = lMunicipios;
	}
	
	public HashMap<String, ArrayList<String>> mapaProvinciasComunidad(){
		
		HashMap<String, ArrayList<String>> resultado = new HashMap<>();
		for (Municipio m : lMunicipios) {
			String provincia = m.getProvincia();
			String comunidad = m.getAutonomia();
			
			if(!resultado.containsKey(comunidad)) {
				resultado.put(comunidad, new ArrayList<String>());
				resultado.get(comunidad).add(provincia);
			}else {
				if(!resultado.get(comunidad).contains(provincia)) {
					resultado.get(comunidad).add(provincia);
					Collections.sort(resultado.get(comunidad));
				}
			}
		}
		
		
		return resultado;
		
	}
	

	public HashMap<String, ArrayList<String>> mapaProvinciasMunicipio(){
		
		HashMap<String, ArrayList<String>> resultado = new HashMap<>();
		for (Municipio m : lMunicipios) {
			String provincia = m.getProvincia();
			String municipio = m.getNombre();
			
			if(!resultado.containsKey(provincia)) {
				resultado.put(provincia, new ArrayList<String>());
				resultado.get(provincia).add(municipio);
			}else {
				if(!resultado.get(provincia).contains(municipio)) {
					resultado.get(provincia).add(municipio);
					Collections.sort(resultado.get(provincia));
				}
			}
		}
		
		return resultado;
		
	}
	
public HashMap<String, Municipio> mapaMunicipiosPorNombre(){
		
		HashMap<String, Municipio> resultado = new HashMap<>();
		for (Municipio m : lMunicipios) {
			String municipio = m.getNombre();
			
			if(!resultado.containsKey(municipio)) {
				resultado.put(municipio, m);
			
			}
		}
		
		return resultado;
		
	}
	




//	public static void main(String[] args) throws IOException {
//		DataSetMunicipiosCSV dataMunis = new DataSetMunicipiosCSV("datasetMunicipios50k.csv");
//		System.out.println(dataMunis.mapaMunicipiosPorNombre());
//	}
	
}
