package com.isb.sgs.cypher.rest;

import java.io.FileNotFoundException;
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
import com.isb.sgs.auth.GitProperties;
import com.isb.sgs.auth.git.GitHelper;
import com.isb.sgs.auth.util.SGSAuthMessage;
import com.isb.sgs.cypher.CypherResponse;
import com.isb.sgs.cypher.ISGSCypherApi;
import com.isb.sgs.cypher.bean.Platform;
import com.isb.sgs.cypher.bean.Product;

@RestController
public class SGSCypherRestController implements ISGSCypherApi {

	Logger _logger = LoggerFactory.getLogger(this.getClass());
	public static final String _JSON_FILE = "sgs-cypher.json";
	@Autowired
	GitProperties aProperties;

	@Override
	public CypherResponse cypherPSI(String platform, String product, String version) {

		CypherResponse response = null;
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(
				" [START]  cypherPSI( Platform: " + platform + ". Product: " + product + ". Version: " + version + ")");
		if (platform == null || platform.equals("")) {
			_logger.error(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_PLATFORM_REQUIRED));
			return new CypherResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_PLATFORM_REQUIRED), false,
					SGSAuthMessage._FORBIDDEN);
		}
		if (product == null || product.equals("")) {
			_logger.error(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_PRODUCT_REQUIRED));
			return new CypherResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_PRODUCT_REQUIRED), false,
					SGSAuthMessage._FORBIDDEN);
		}
		if (version == null || version.equals("")) {
			_logger.error(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_VERSION_REQUIRED));
			return new CypherResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_VERSION_REQUIRED), false,
					SGSAuthMessage._FORBIDDEN);
		}

		// try {
		// aProperties = GitProperties.getInstance();
		// } catch (Exception e1) {
		// _logger.warn("Problems reading the properties file: " +
		// e1.getMessage());
		// return new CypherResponse(
		// SGSAuthMessage.getString("Read properties : " +
		// SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND)
		// + e1.getMessage(),
		// false, SGSAuthMessage._FORBIDDEN);
		// }

		// Verificamos que exista el fichero JSON en memoria y en caso contrario
		// lo recuperamos.
		byte[] json = null;
		try {
			if (aProperties.getSgsCypher() == null) {
				_logger.debug("No existe el fichero Json en Memoria. Lo recuperamos del repositorio..");
				json = this.getJson();
				aProperties.setSgsCypher(json);
			} else {
				_logger.info("Recuperamos el Fichero de Memoria");
				json = aProperties.getSgsCypher();
			}
			if (json == null)
				return new CypherResponse(SGSAuthMessage.getString(SGSAuthMessage._MSG_ERROR_JSON_NOT_FOUND), false,
						SGSAuthMessage._FORBIDDEN);
			List<Platform> platforms = this.getPlatformsList(json);
			int cypherState = getCypherState(platform, product, version, platforms);
			if (cypherState == 1) {
				response = new CypherResponse(
						SGSAuthMessage.getString(SGSAuthMessage._MSG_CYPHER_STATE_YES, platform, product, version),
						true, SGSAuthMessage._SUCCESSFULLY);
			} else {
				response = new CypherResponse(
						SGSAuthMessage.getString(SGSAuthMessage._MSG_CYPHER_STATE_NO, platform, product, version),
						false, SGSAuthMessage._SUCCESSFULLY);
			}
			_logger.info("Response: (Message) " + response.getMessage() + ", (Code) " + response.getCode()
					+ ", (Status) " + response.getStatus());
			_logger.debug(" [FINISH] cypherPSI in " + (Calendar.getInstance().getTimeInMillis() - start) + " mls");
		} catch (Exception e) {

			response = new CypherResponse(e.getMessage(), false, SGSAuthMessage._NOT_FOUND);
			_logger.info("Response: (Message) " + response.getMessage() + ", (Code) " + response.getCode()
					+ ", (Status) " + response.getStatus());
		}

		return response;
	}

	/**
	 * Recupera el fichero JSON como un objeto Java para tratar la lógica
	 * Platform-Product-Version
	 * 
	 * @param jsonFile
	 * @return
	 * @throws FileNotFoundException
	 */
	protected synchronized List<Platform> getPlatformsList(byte[] json) throws FileNotFoundException {
		Type listType = new TypeToken<ArrayList<Platform>>() {
		}.getType();

		String str = new String(json);
		List<Platform> platforms = new Gson().fromJson(str, listType);
		for (Platform p : platforms) {
			_logger.debug("Platform: " + p.getPlatform());
			List<Product> products = p.getProducts();
			for (Product product : products) {
				if (product.getProduct() != null) {
					_logger.debug("Product: " + product.getProduct());
					List<String> versionList = product.getVersions();
					for (String version : versionList)
						_logger.debug("Version: " + version);
				}
			}
		}
		return platforms;

	}

	/**
	 * Lógica de Negocio para devolver si se puede o no cifrar una publicación
	 * en función del product-Version-Plataform según la configuración Json en
	 * el repo GIT
	 * 
	 * @param product
	 * @param version
	 * @param platform
	 * @param platforms
	 * @return int, 1 Se debe Cifrar, 0 => No Cifra la publicacion
	 */
	protected int getCypherState(String platform, String product, String version, List<Platform> platforms) {

		int result = 0;
		if (platforms.contains(new Platform("*"))) {
			_logger.info("Cypher allowed for all platforms");
			return 1;
		}
		if (!platforms.contains(new Platform(platform))) {
			_logger.info("Cypher is not allowed for platform: " + platform);
			return 0;
		}
		// Si contiene mi plataforma para todos los productos return 1. Si no ,
		// y tampoco contiene mi producto return 0
		for (Platform p : platforms) {

			List<Product> products = p.getProducts();
			if (p.equals(new Platform(platform))) {

				if (products.contains(new Product("*"))) {
					_logger.info("Platform " + platform + " allows cypher for all its products.");
					return 1;
				}
				if (!products.contains(new Product(product))) {
					_logger.info("Platform " + platform + " doesn't allow cypher for the product " + product);
					return 0;
				}
				// Plataforma y producto coinciden
				for (Product producto : products) {
					if (producto.equals(new Product(product))) {
						if (producto.getVersions().contains("*")) {
							_logger.info("Platform " + platform + " and product " + product
									+ " allow cypher for all versions.");
							return 1;
						}
						if (producto.getVersions().contains(version)) {
							_logger.info("Platform " + platform + " and product " + product
									+ " allow cypher for the version " + version);
							return 1;
						}
						_logger.info("Platform " + platform + " and product " + product
								+ " don't allow cypher for version " + version);
						return 0;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Recuperamos el fichero Json del repositorio Git
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
				json = git.getJsonFile(GitHelper._JSON_FILE_CYPHER, aux.get(i), aProperties.getUser(),
						aProperties.getPassword());
				break;
			} catch (Exception e) {
				_logger.debug("No se pudo recuperar json de host : " + aux.get(i) + " ERROR : " + e.getMessage());
			}

		}
		return json;
	}

}
