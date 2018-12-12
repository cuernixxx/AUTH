package com.isb.sgs.auth.bean;

import java.io.Serializable;
import java.util.List;

public class Repositorio implements Comparable<Repositorio>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;

	public Repositorio(String host) {
		super();
		this.host = host;

	}
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName() + ": {\n");
		sb.append("    host: ").append(toIndentedString(host)).append("\n");
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object token){
		
	    if (((Repositorio) token).getHost().equals(this.host)) return true;
	    return false;
	}
	

//	public Integer compara(Token token){
//		
//		
//		return ((this.name.equals((((Token) token).getName())) ? 1:0)) ;
//		
////		
////	    if (((Token) token).getName().equals(this.name)) return true;
////	    return false;
//	}
	
	

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

	@Override
	public int compareTo(Repositorio token) {
		
		return ((this.host.equals((((Repositorio) token).getHost())) ? 1:0)) ;
		

	}
}
