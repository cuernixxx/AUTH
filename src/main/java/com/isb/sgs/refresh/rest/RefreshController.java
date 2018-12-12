package com.isb.sgs.refresh.rest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.isb.sgs.auth.GitProperties;
import com.isb.sgs.auth.git.GitHelper;
import com.isb.sgs.auth.util.SGSAuthMessage;
import com.isb.sgs.refresh.IRefreshControllerApi;
import com.isb.sgs.refresh.RefreshResponse;

@RestController
public class RefreshController implements IRefreshControllerApi {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	GitProperties aProperties;

	@Override
	public RefreshResponse refresh() {
		RefreshResponse response = null;
		long start = Calendar.getInstance().getTimeInMillis();
		logger.info("[START]  refresh:  ");
		try {
			aProperties.setSgsAuth(this.getJson(GitHelper._JSON_FILE_AUTH));
			aProperties.setSgsCypher(this.getJson(GitHelper._JSON_FILE_CYPHER));
		} catch (Exception e1) {
			logger.warn("Problems reading the properties file: " + e1.getMessage());
			return new RefreshResponse(
					SGSAuthMessage.getString("Read properties : " + SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND)
							+ e1.getMessage(),
					false, SGSAuthMessage._FORBIDDEN);
		}
		logger.info("[FINISH refresh ] in: " + (Calendar.getInstance().getTimeInMillis() - start) + "mls");
		response = new RefreshResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_REFRESH_OK), true,
				SGSAuthMessage._SUCCESSFULLY);
		return response;
	}

	/**
	 * Recuperamos los ficheros JSON en función del fichero de Host configurados
	 * 
	 * 
	 * @return File
	 * 
	 */
	private byte[] getJson(String jsonName) {
		byte[] json = null;
		GitHelper git = new GitHelper();
		List<String> aux = Arrays.asList(aProperties.getHosts().split(","));
		for (int i = 0; i < aux.size(); i++) {
			logger.debug("Recuperamos el Fichero sgs-auth.json del repositorio " + aux.get(i));
			try {
				json = git.getJsonFile(jsonName, aux.get(i), aProperties.getUser(), aProperties.getPassword());
				break;
			} catch (Exception e) {
				logger.debug("No se pudo recuperar json de host : " + aux.get(i) + " ERROR : " + e.getMessage());
			}

		}
		return json;
	}

}
