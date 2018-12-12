package com.isb.sgs.cypher.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.isb.sgs.auth.GitProperties;
import com.isb.sgs.cypher.CypherResponse;
import com.isb.sgs.cypher.bean.Platform;
import com.isb.test.doc.TestInfo;

public class SGSCypherRestControllerTest {

	SGSCypherRestController cypherController;
	String _PLATFORM_DEFAULT = "";
	String _PRODUCT_DEFAULT = "";
	String _VERSION_DEFAULT = "";
	String _CYPHER_JSON = "sgs-cypher.json";
	String _REPO_PRE = "http://ISLJKGSI01.scisb.isban.corp";
	@Autowired
	GitProperties aProperties;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cypherController = new SGSCypherRestController();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO un fichero de configuración con un único repositorio e inválido, cuando se ejecute el servicio REST, este deberá devolver un error controlado informando de que no se ha podido establecer la conexión con los Repositorios Git", jiras = "ADU17-525")
	public void _cypher_test_all_repositories_shutdown_when_only_exist_one() {

		List<String> hostShutdown = new ArrayList<String>();
		hostShutdown.add("NOEXISTO:8080/CCSW/CCSW_CONFIG");
		List<String> origins = new ArrayList<String>();
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts("");
			response = cypherController.cypherPSI("PLATFORM_DEFAULT", "PRODUCT_DEFAULT", "VERSION_DEFAULT");
			assertTrue("El sistema no debe permitir el cifrado. [MESSAGE] ", response.getMessage()
					.contains("No existe el fichero JSON de configuración en la ruta especificada."));
			assertTrue("El sistema no debe permitir el cifrado  [STATUS] ", !response.getStatus());
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO un fichero de configuración con dos o más repositorios y todos inválidos, cuando se ejecute el servicio REST, este deberá devolver un error controlado informando de que no se ha podido establecer la conexión con los Repositorios Git", jiras = "ADU17-525")
	public void _cypher_test_all_repositories_shutdown_when_exist_two_repos_git() {

		List<String> hostShutdown = new ArrayList<String>();
		hostShutdown.add("NOEXISTO:8080/CCSW/CCSW_CONFIG");
		hostShutdown.add("NILOPRETENDO:8080/CCSW/CCSW_CONFIG");
		List<String> origins = new ArrayList<String>();
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostShutdown.toString());
			response = cypherController.cypherPSI("PLATFORM_DEFAULT", "PRODUCT_DEFAULT", "VERSION_DEFAULT");
			assertTrue("El sistema no debe permitir el cifrado. [MESSAGE] ", response.getMessage()
					.contains("No existe el fichero JSON de configuración en la ruta especificada."));
			assertTrue("El sistema no debe permitir el cifrado  [STATUS] ", !response.getStatus());
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO un fichero de configuración con el primer repositorio válido, cuando se ejecute el servicio REST, este deberá devolver un código de ok", jiras = "ADU17-525")
	public void _cypher_test_getJSONFile_when_first_repo_is_ok() {

		List<String> hostList = new ArrayList<>();
		hostList.add(_REPO_PRE);
		hostList.add("http://ISLGITLP01.scisb.isban.corp");
		hostList.add("http://ISLGITLP02.scisb.isban.corp");
		List<String> origins = null;
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostList.toString());
			response = cypherController.cypherPSI("PLATFORM_DEFAULT", "PRODUCT_DEFAULT", "VERSION_DEFAULT");
			assertTrue("El sistema debe poder consultar el cifrado. [CODE] ", response.getCode() == 200);
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO un fichero de configuración con el primer repositorio inválido y el segundo ok, cuando se ejecute el servicio REST, este deberá devolver un código de ok", jiras = "ADU17-525")
	public void _cypher_test_getJSONFile_when_first_repo_is_shutdown_and_repo_is_ok() {

		List<String> hostList = new ArrayList<>();
		hostList.add("http://HOST_INVALIDO/");
		hostList.add("http://ISLGITLP01.scisb.isban.corp");

		List<String> origins = null;
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostList.toString());
			response = cypherController.cypherPSI("PLATFORM_DEFAULT", "PRODUCT_DEFAULT", "VERSION_DEFAULT");
			assertTrue("El sistema debe poder consultar el cifrado. [CODE] ", response.getCode() == 200);
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO una plataforma nula, cuando se ejecute el servicio REST, este deberá devolver un error controlado informando de que no puede ser nula", jiras = "ADU17-525")
	public void _cypher_test_getError_when_platform_parameter_is_null() {

		List<String> hostList = new ArrayList<>();
		hostList.add("http://ISLGITLP01.scisb.isban.corp");

		List<String> origins = null;
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostList.toString());
			response = cypherController.cypherPSI(_PLATFORM_DEFAULT, _PRODUCT_DEFAULT, _VERSION_DEFAULT);
			assertTrue("El sistema no debe permitir el cifrado. [MESSAGE] ",
					response.getMessage().contains("La plataforma es obligatoria"));
			assertTrue("El sistema no debe permitir el cifrado  [STATUS] ", !response.getStatus());
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO un producto nulo, cuando se ejecute el servicio REST, este deberá devolver un error controlado informando de que no puede ser nulo", jiras = "ADU17-525")
	public void _cypher_test_getError_when_product_parameter_is_null() {

		List<String> hostList = new ArrayList<>();
		hostList.add("http://ISLGITLP01.scisb.isban.corp");

		List<String> origins = null;
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostList.toString());
			response = cypherController.cypherPSI("bks", _PRODUCT_DEFAULT, _VERSION_DEFAULT);
			assertTrue("El sistema no debe permitir el cifrado. [MESSAGE] ",
					response.getMessage().contains("El producto es obligatorio"));
			assertTrue("El sistema no debe permitir el cifrado  [STATUS] ", !response.getStatus());
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO una versión nula, cuando se ejecute el servicio REST, este deberá devolver un error controlado informando de que no puede ser nula", jiras = "ADU17-525")
	public void _cypher_test_getError_when_version_parameter_is_null() {

		List<String> hostList = new ArrayList<>();
		hostList.add("http://ISLGITLP01.scisb.isban.corp");

		List<String> origins = null;
		CypherResponse response = null;
		try {
			origins = Arrays.asList(aProperties.getHosts().split(","));
			aProperties.setHosts(hostList.toString());
			response = cypherController.cypherPSI("bks", "SDT", _VERSION_DEFAULT);
			assertTrue("El sistema no debe permitir el cifrado. [MESSAGE] ",
					response.getMessage().contains("La versión es obligatoria"));
			assertTrue("El sistema no debe permitir el cifrado  [STATUS] ", !response.getStatus());
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		} finally {
			try {
				aProperties.setHosts(origins.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO Un fichero Json válido recuperamos la información de Platform-Product-Version", jiras = "ADU17-525")
	public void _getPlatformsList_test_convert_json_to_Java() {
		File json = new File(this.getClass().getClassLoader().getResource(_CYPHER_JSON).getFile());
		Path path = Paths.get(json.getAbsolutePath());

		List<Platform> platforms = null;
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms);
		} catch (Exception e) {
			fail("Uppps :( :( :( .Se ha producido un error durante la petición de cifrado" + e.getMessage());
		}

	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "DADO Un fichero Json válido recuperamos la información de Platform-Product-Version, y la primera plataforma obtenida es igual a bks", jiras = "ADU17-525")
	public void _getPlatformsList_test_convert_json_to_Java_first_platform_is_bks() {
		File json = new File(this.getClass().getClassLoader().getResource(_CYPHER_JSON).getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue("La plataforma controlado es BKS ", platforms.get(0).getPlatform().equals("bks"));
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "Se Cifran todas las plataformas", jiras = "ADU17-525")
	public void _cypher_with_all_platforms() {
		File json = new File(this.getClass().getClassLoader().getResource("_cypher_with_all_platforms.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("PLATFORM_NOT_FOUND", "PRODUCT_NOT_FOUND", "VERSION_NOT_FOUND",
					platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 1);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "Se cifra solo la plataforma bks para todos los productos", jiras = "ADU17-525")
	public void _cypher_with_platform_bks_and_all_products() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_with_platform_bks_and_all_products.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "PRODUCT_NOT_FOUND", "VERSION_NOT_FOUND", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 1);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "No se permite el cifrado si la plataforma no está en la lista de plataformas a cifrar", jiras = "ADU17-525")
	public void _cypher_false_with_platform_not_included() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_false_with_platform_not_included.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "PRODUCT_NOT_FOUND", "VERSION_NOT_FOUND", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 0);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "No se permite el cifrado si el producto no está en la lista de productos a cifrar", jiras = "ADU17-525")
	public void _cypher_false_with_platform_bks_and_product_not_included() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_false_with_platform_bks_and_product_not_included.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "sdt", "VERSION_NOT_FOUND", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 0);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "Cifra si la plataforma y el producto están en la lista para todas las versiones", jiras = "ADU17-525")
	public void _cypher_with_platform_bks_and_product_sgs_and_all_versions() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_with_platform_bks_and_product_sgs_and_all_versions.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "sgs", "VERSION_NOT_FOUND", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 1);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "Cifra si la plataforma, el producto y la versión están en la lista", jiras = "ADU17-525")
	public void _cypher_with_platform_bks_and_product_sgs_and_version_V05R16() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_with_platform_bks_and_product_sgs_and_version_V05R16.json").getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "sgs", "V05R16", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 1);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

	@Test
	@TestInfo(classTested = SGSCypherRestController.class, description = "No Cifra si la plataforma y el producto están en la lista, pero la versión no está", jiras = "ADU17-525")
	public void _cypher_false_with_platform_bks_and_product_sgs_and_version_not_included() {
		File json = new File(this.getClass().getClassLoader()
				.getResource("_cypher_false_with_platform_bks_and_product_sgs_and_version_not_included.json")
				.getFile());
		List<Platform> platforms = null;
		Path path = Paths.get(json.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			platforms = cypherController.getPlatformsList(data);
			int result = cypherController.getCypherState("bks", "sgs", "V05R18", platforms);
			assertNotNull("La lista de plataforma-producto-version No puede ser nula", platforms.get(0));
			assertTrue(result == 0);
		} catch (Exception e) {
			fail("Upps :( :( :( Se ha  producido un error no controlado " + e.getMessage());
		}
	}

}
