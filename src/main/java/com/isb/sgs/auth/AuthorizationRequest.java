package com.isb.sgs.auth;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
/**
 * PublicationRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-01T17:11:41.081+01:00")

public class AuthorizationRequest   {
  

  @JsonProperty("token")
  private String token = null;
  
  @JsonProperty("action")
  private String action = null;



  public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

@Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PublicationRequest {\n");
    
    sb.append("    idPublication: ").append(toIndentedString(token)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");

    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

