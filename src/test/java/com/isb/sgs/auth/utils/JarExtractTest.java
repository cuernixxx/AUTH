/**
 * 
 */
package com.isb.sgs.auth.utils;


import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isb.sgs.auth.util.JarExtract;

/**
 * @author xIS15688
 *
 */
public class JarExtractTest {
	
	public static final String _PROPERTIES_FILE = "sgs_aux.properties";

	Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Test method for {@link com.isb.sgs.auth.util.JarExtract#extractJar(java.lang.String, java.io.File)}.
	 */
	@Test
	public void testExtractJar() throws Exception{
		
		
		JarExtract extract = new JarExtract();
		String rutaJar = extract.getRutaJar();
		
		//buscamos el nombre de nuestro jar dentro de la ruta
		String[] listDirectory = new File(rutaJar).list();
		for(int i=0;i < listDirectory.length;i++){
			
			if(listDirectory[i]!=null)
				if(listDirectory[i].endsWith(".jar"))
					rutaJar = rutaJar + File.separator + listDirectory[i];
		}
			
		if(!rutaJar.endsWith(".jar"))
			throw new Exception("No se encontro el jar");
		
		File directoryExtract = new File(rutaJar.substring(0, rutaJar.lastIndexOf(File.separator))+ File.separator + "Temp");
		if(!directoryExtract.exists())
			directoryExtract.mkdir();
		
		try {
			extract.extractJar(rutaJar,directoryExtract);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extract.rutaPropertiesDescomprimido(_PROPERTIES_FILE,directoryExtract);
		String rutaPropertiesDescomprimido =  extract._PROPERTIES_FILE_EXTRACT;
		
		_logger.debug("rutaPropertiesDescomprimido : " + rutaPropertiesDescomprimido);
	}

}
