package com.isb.sgs.auth.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Clase para manejar los ficheros de Mensaje que vamos a tratar durante la
 * definicion del servicio y garantizar el multi-idioma
 * 
 * @author xIS05846
 *
 */
public class SGSAuthMessage {

	/**
	 * REQUIRED TOKEN. El token debe venir informado
	 */
	public static final String _MSG_ERROR_TOKEN_REQUIRED = "error.token.required";

	/**
	 * ACTION TOKEN. El token debe venir informado
	 */
	public static final String _MSG_ERROR_ACTION_REQUIRED = "error.action.required";

	/**
	 * No encontramos el Fichero de propiedades en la ruta especificada
	 */
	public static final String _MSG_ERROR_JSON_NOT_FOUND = "error.jsonFile.notFound";

	public static final String _MSG_ERROR_TOKEN_NOT_VALID = "error.token.notFound";

	public static final String _ACTION_NOT_VALID = "error.action.notValid";

	public static final String _MSG_ERROR_PLATFORM_REQUIRED = "error.platform.required";

	public static final String _MSG_ERROR_PRODUCT_REQUIRED = "error.product.required";

	public static final String _MSG_ERROR_VERSION_REQUIRED = "error.version.required";

	public static final String _MSG_CYPHER_STATE_YES = "msg.cypher.yes";

	public static final String _MSG_CYPHER_STATE_NO = "msg.cypher.no";

	public static final String _MSG_ERROR_CLONE_REPOSITORY = "msg.refresh.error";
	
	public static final String _MSG_REFRESH_OK = "msg.refresh.ok";

	public static final String _OK = "succes.token.OK";

	public static final Integer _FORBIDDEN = new Integer(403);

	public static final Integer _SUCCESSFULLY = new Integer(200);

	public static final Integer _NOT_FOUND = new Integer(404);
	/**
	 * No se ha podido borrar el directorio temporal resultado del clonado del
	 * repositorio git {0}: {1}.
	 */
	public static final String _MSG_WARN_DELETE_CLONE_GIT_DIRECTORY = "error.gitCloneDirectory.deleteNotPossible";

	private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");

	public static String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object... params) {
		try {
			return MessageFormat.format(bundle.getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
