package com.isb.sgs.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-01T17:11:41.081+01:00")

@Api(value = "Authorization", tags= "/auth")
public interface AuthorizationApi {
	
  @ApiOperation(value = "Devuelve si esta autorizado o no", notes = "Devuelve si esta autorizado o no", response = AuthorizationResponse.class, responseContainer = "List", tags={ "/auth", })
  @ApiResponses(value = { 
      @ApiResponse(code = 200, message = "OK", response = AuthorizationResponse.class),
      @ApiResponse(code = 400, message = "Bad request", response = AuthorizationResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = AuthorizationResponse.class),
      @ApiResponse(code = 403, message = "Forbidden", response = AuthorizationResponse.class),
      @ApiResponse(code = 404, message = "Not found", response = AuthorizationResponse.class),
      @ApiResponse(code = 405, message = "Method not allowed", response = AuthorizationResponse.class) })
  @RequestMapping(value = "/auth",
      produces = { "application/json" }, 
      method = RequestMethod.GET)
  AuthorizationResponse getAuthorization(
       @ApiParam(value = "token pasado") @RequestParam(value = "token", required = true) String token,
       @ApiParam(value = "accion que va a realizar") @RequestParam(value = "action", required = true) String action
       );


}
