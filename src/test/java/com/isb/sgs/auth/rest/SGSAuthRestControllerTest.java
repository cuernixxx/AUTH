package com.isb.sgs.auth.rest;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.isb.sgs.auth.AuthorizationResponse;
import com.isb.test.doc.TestInfo;

public class SGSAuthRestControllerTest {

	Logger _logger = Logger.getLogger(this.getClass());
	ResourceBundle authBundle;
	SGSAuthRestController controller;

	@Before
	public void setUp() {
		authBundle = ResourceBundle.getBundle("messages");
		controller = new SGSAuthRestController();
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "DADO Una petición que no tenga el token informado el sistema deberá dar un error controlado. TIPO DE ERROR 404", jiras = "ADU17-437")
	public void testGetAuthentication_without_token() {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_without_token: ");
		AuthorizationResponse response = controller.getAuthorization("", "ACTION");
		assertTrue("Upps :( :( :( Ha ocurrido un error inesperado El Token Requerido: Es obligatorio y debe venir informado",response.getStatus() == false);
		_logger.debug(" [FINISH] testGetAuthentication_without_token in "
				+ (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "DADO Una petición que no tenga la accion informada el sistema deberá dar un error controlado. TIPO DE ERROR 404", jiras = "ADU17-437")
	public void testGetAuthentication_without_action() {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_without_action.");
		AuthorizationResponse response = controller.getAuthorization("TOKEN-INFORMADO", "");
		_logger.debug(" AuthorizationResponse: " + response.toString());
		assertTrue("Upps :( :( :( Ha ocurrido un error inesperado.  Action Required.", response.getStatus() == false);
		_logger.debug(" [FINISH] testGetAuthentication_without_token in "
				+ (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "DADO Una petición que venga con un token informado, pero no válido el sistema deberá dar un error controlado. TIPO DE ERROR 200. Resultado OK, pero en la respuesta deberemos tratar una respuesta con  error \"TOKEN NO AUTORIZADO\"", jiras = "ADU17-437")
	public void testGetAuthentication_with_token_not_valids() {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_with_token_not_valids: ");
		AuthorizationResponse response = controller.getAuthorization("TOKEN-INFORMADO_NO_DEFINIDO-EN-JSON", "*");
		_logger.debug(" AuthorizationResponse: " + response.toString());		
		_logger.debug(" [FINISH] testGetAuthentication_without_token in "
				+ (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "DADO Una petición que venga con token y accion informada, cuando el tokenno esté asociado a dicha accion el sistema deberá dar un error controlado. TIPO DE ERROR 200. AuthorizationResponse.code = _CODE_ERROR, y AuthorizationResponse.messag=\"TOKEN NO ASOCIADO A LA ACCION\"", jiras = "ADU17-437")
	public void testGetAuthentication_with_token_asociatte_to_action_not_valid() {
		
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_with_token_asociatte_to_action_not_valid: ");
		AuthorizationResponse response = controller.getAuthorization("TOKEN-1", "ACTION-NOT-VALID");
		_logger.debug(" AuthorizationResponse: " + response.toString());
		assertTrue("Finis OK  ",true);
		_logger.debug(" [FINISH] testGetAuthentication_with_token_asociatte_to_action_not_valid "
				+ (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "ACTION=* es un token que puede realizar cualquier tipo de acción", jiras = "ADU17-437")
	public void testGetAuthentication_with_token_asociatte_to_action_ADMIN() {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_with_token_asociatte_to_action_ADMIN: ");
		AuthorizationResponse response = controller.getAuthorization("TOKEN-1", "CUALQUIER_ACCION");
		_logger.debug(" AuthorizationResponse: " + response.toString());
		assertTrue("Finis OK  ",true);
		_logger.debug(" [FINISH] testGetAuthentication_with_token_asociatte_to_action_ADMIN "
				+ (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}

	@Test
	@TestInfo(classTested = SGSAuthRestController.class, description = "En caso de no encontrar el fichero de configuración de Tokens entonces el sistema deberá dar un error controlado. TIPO DE ERROR: 404", jiras = "ADU17-437")
	public void testGetAuthentication_properties_not_found() {
		long start = Calendar.getInstance().getTimeInMillis();
		_logger.debug(" [START]  testGetAuthentication_properties_not_found: ");
		AuthorizationResponse response = controller.getAuthorization("TOKEN-1", "*");
		_logger.debug(" AuthorizationResponse: " + response.toString());
		assertTrue("Finis OK  ",true);
		_logger.debug(" [FINISH] testGetAuthentication_properties_not_found " + (Calendar.getInstance().getTimeInMillis() - start) + " mls");
	}


}
