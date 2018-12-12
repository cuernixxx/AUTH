package com.isb.sgs.auth.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class JarExtract {

	
	Logger _logger = LoggerFactory.getLogger(this.getClass());
	public static String _PROPERTIES_FILE_EXTRACT = "";
	
	
	public void extractJar(String jarFile, java.io.File directory) throws IOException {
		java.util.jar.JarInputStream jarInput = new java.util.jar.JarInputStream(new FileInputStream(jarFile));
		java.util.jar.JarEntry jarEntry = null;
		while ((jarEntry = jarInput.getNextJarEntry()) != null) {
			java.io.File file = new java.io.File(directory, jarEntry.getName());
			if (jarEntry.isDirectory()) {
				if (!file.exists())
					file.mkdirs();
			} else {
				java.io.File dir = new java.io.File(file.getParent());
				if (!dir.exists())
					dir.mkdirs();
				byte[] bytes = new byte[1024];
				java.io.InputStream inputStream = new BufferedInputStream(jarInput);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				int read = -1;
				while ((read = inputStream.read(bytes)) != -1) {
					fileOutputStream.write(bytes, 0, read);
				}
				fileOutputStream.close();
			}
		}

	}
	
	
	public String getRutaJar(){
		
		File miDir = new File (".");
		String rutaTemporal = null;
	     try {
	    	 rutaTemporal =  miDir.getCanonicalPath();

		_logger.debug("Directorio actual: " + rutaTemporal);
	       }
	     catch(Exception e) {
	       e.printStackTrace();
	       }
	     
		return rutaTemporal;
		
	}
	
	
	public void rutaPropertiesDescomprimido(String nombreFicheroBuscar,File directorio){
		
		File listFile[] = directorio.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                	rutaPropertiesDescomprimido(nombreFicheroBuscar,listFile[i]);
                } else {
                	if(listFile[i].getPath() != null)
                		if(listFile[i].getPath().contains(nombreFicheroBuscar)){
                			_PROPERTIES_FILE_EXTRACT = listFile[i].getPath().toString();
                			break;
                		}	
                }
            }
        }				
        
	}
	
}
