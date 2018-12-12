package com.isb.sgs.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PublicationResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-01T17:11:41.081+01:00")

public class AuthorizationResponse {

	public AuthorizationResponse(String message, boolean status, Integer code) {
		super();
		this.message = message;
		this.status = status;
		this.code = code;
	}

	public AuthorizationResponse() {
		super();
	}

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("status")
	private boolean status;

	@JsonProperty("code")
	private Integer code = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName() + ": {\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
