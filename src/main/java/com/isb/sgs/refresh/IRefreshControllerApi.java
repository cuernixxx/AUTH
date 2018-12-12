package com.isb.sgs.refresh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Refresh", tags = "/reload")
public interface IRefreshControllerApi {
	@ApiOperation(value = "Clona de nuevo el repositorio para tratar los ficheros de configuración", notes = "devuelve 200 en caso de poder clonar el repositorio", response = RefreshResponse.class, tags = {
			"/reload", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RefreshResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = RefreshResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = RefreshResponse.class),
			@ApiResponse(code = 403, message = "Forbidden", response = RefreshResponse.class),
			@ApiResponse(code = 404, message = "Not found", response = RefreshResponse.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = RefreshResponse.class) })
	@RequestMapping(value = "/reload", produces = { "application/json" }, method = RequestMethod.PUT)
	RefreshResponse refresh();

}
