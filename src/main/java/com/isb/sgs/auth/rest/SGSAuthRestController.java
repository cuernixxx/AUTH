package com.isb.sgs.auth.rest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isb.sgs.auth.AuthorizationApi;
import com.isb.sgs.auth.AuthorizationResponse;
import com.isb.sgs.auth.GitProperties;
import com.isb.sgs.auth.bean.Token;
import com.isb.sgs.auth.git.GitHelper;
import com.isb.sgs.auth.util.SGSAuthMessage;

/**
 * Esta clase define el microservicio para verificar la autorizacion para
 * realizar acciones gg
 * 
 * @author: CCSW
 * @version: V01R00F00
 * 
 */
@RestController
public class SGSAuthRestController implements AuthorizationApi {

	Logger _logger = LoggerFactory.getLogger(this.getClass());
	// public static final String _JSON_FILE = "sgs-auth.json";
	@Autowired
	GitProperties aProperties;

	/**
	 * Metodo para comprobar la autorización
	 * 
	 * @param user,
	 *            un usuario
	 * @param token
	 *            token a verificar en repositorio en Git
	 * @param accion
	 *            Lo que va a realizar sobre el portal
	 * @return
	 */
	@Override
	public AuthorizationResponse getAuthorization(String token, String action) {

		// recuperamos properties de los git a clonar
		if (!getProperties()) {
			return new AuthorizationResponse(
					SGSAuthMessage.getString("Read properties : " + SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND), false,
					SGSAuthMessage._FORBIDDEN);
		}
		long start = Calendar.getInstance().getTimeInMillis();

		// Validamos los valores recibidos
		if (null == token || token.isEmpty())
			return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_TOKEN_REQUIRED), false,
					SGSAuthMessage._FORBIDDEN);
		if (null == action || action.isEmpty())
			return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_ACTION_REQUIRED), false,
					SGSAuthMessage._FORBIDDEN);

		// Verificamos que exista el fichero JSON en memoria y en caso contrario
		// lo recuperamos.
		byte[] json = null;
		try {
			if (aProperties.getSgsAuth() == null) {
				_logger.debug("No existe el fichero Json en Memoria. Lo recuperamos del repositorio..");
				json = this.getJson();
				aProperties.setSgsAuth(json);
			} else {
				_logger.info("Recuperamos el Fichero de Memoria");
				json = aProperties.getSgsAuth();
			}
			if (json == null)
				return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND),
						false, SGSAuthMessage._FORBIDDEN);
			// Recuperamos el token....
			Token aToken = this.readJson(token, json);
			_logger.debug("Verificacion de token y accion");
			// No hemos encontrado el token o no es valido
			if (aToken == null) {
				_logger.debug("Token no encontrado " + token);
				return new AuthorizationResponse(
						SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_TOKEN_NOT_VALID, token), false,
						SGSAuthMessage._FORBIDDEN);
			}

			// Si el token se ha encontrado y se cumple que la accion es * o
			// contiene la misma que le pasan
			if (aToken.getActions().contains(action) || aToken.getActions().contains("*")) {
				_logger.debug("Permitido realizar accion " + action + " para token : " + token);
				return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._OK), true,
						SGSAuthMessage._SUCCESSFULLY);
			}

			_logger.debug(
					" [FINISH] getAuthorization in " + (Calendar.getInstance().getTimeInMillis() - start) + " mls");

			return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._ACTION_NOT_VALID, token, action),
					false, SGSAuthMessage._FORBIDDEN);

		} catch (Exception e) {
			_logger.error(e.getMessage());
			return new AuthorizationResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND), false,
					SGSAuthMessage._FORBIDDEN);
		}
	}

	/**
	 * Recuperamos los properties
	 * 
	 * 
	 * @return Boolean
	 * 
	 */
	private Boolean getProperties() {
		// if(aProperties)
		// try {
		// aProperties = GitProperties.getInstance();
		// } catch (Exception e1) {
		// _logger.info("Problemas al leer el fichero properties");
		// return false;
		// }
		// TODO: Ver que hacemos aquí..
		return true;
	}

	/**
	 * Realizamos clonado recorriendo todos los host proporcionados
	 * 
	 * 
	 * @return File
	 * 
	 */
	private byte[] getJson() {
		byte[] json = null;
		GitHelper git = new GitHelper();
		List<String> aux = Arrays.asList(aProperties.getHosts().split(","));
		for (int i = 0; i < aux.size(); i++) {
			_logger.debug("Recuperamos el Fichero sgs-auth.json del repositorio " + aux.get(i));
			try {
				json = git.getJsonFile(GitHelper._JSON_FILE_AUTH, aux.get(i), aProperties.getUser(),
						aProperties.getPassword());
				break;
			} catch (Exception e) {
				_logger.debug("No se pudo recuperar json de host : " + aux.get(i) + " ERROR : " + e.getMessage());
			}

		}
		return json;
	}

	/**
	 * Método para leer el fichero JSON de autenticación
	 * 
	 * @param token
	 * @param jsonFile
	 * @return
	 * @throws Exception
	 */
	private synchronized Token readJson(String token, byte[] jsonFile) throws Exception {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.info("[START]  readJson:  " + token, jsonFile);
		Type listType = new TypeToken<ArrayList<Token>>() {
		}.getType();

		String str = new String(jsonFile);
		List<Token> keyPairBoolDataList = new Gson().fromJson(str, listType);

		Token aToken = null;
		for (int i = 0; i < keyPairBoolDataList.size(); i++) {

			if (keyPairBoolDataList.get(i).getName().equals(token)) {
				aToken = keyPairBoolDataList.get(i);
				break;
			}
		}
		_logger.info("[FINISH readJson ] in: " + (Calendar.getInstance().getTimeInMillis() - start) + "mls");
		return aToken;
	}
}