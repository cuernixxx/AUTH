package com.isb.sgs.cypher;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CypherResponse {
	public CypherResponse(String message, boolean status, Integer code) {
		super();
		this.message = message;
		this.status = status;
		this.code = code;
	}

	public CypherResponse() {
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
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
