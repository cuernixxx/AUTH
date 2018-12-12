package com.isb.sgs.cypher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Api(value = "SGS Cypher", tags= "/cypher")
public interface ISGSCypherApi {

	@ApiOperation(value = "Devuelve si se debe encriptar una publicación en función de La plataforma, Producto, Version", notes = "Devuelve si esta autorizado o no", response = CypherResponse.class, responseContainer = "List", tags = {
			"/cypher", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = CypherResponse.class),
			@ApiResponse(code = 400, message = "Bad request", response = CypherResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = CypherResponse.class),
			@ApiResponse(code = 403, message = "Forbidden", response = CypherResponse.class),
			@ApiResponse(code = 404, message = "Not found", response = CypherResponse.class),
			@ApiResponse(code = 405, message = "Method not allowed", response = CypherResponse.class) })
	@RequestMapping(value = "/cypher", produces = { "application/json" }, method = RequestMethod.GET)
	CypherResponse cypherPSI(
			@ApiParam(value = "Platform") @RequestParam(value = "platform", required = true) String platform,
			@ApiParam(value = "Product") @RequestParam(value = "product", required = true) String product,
			@ApiParam(value = "Version") @RequestParam(value = "version", required = true) String version);

}
