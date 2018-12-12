package com.isb.sgs.auth.bean;

import java.io.Serializable;
import java.util.List;

public class Token implements Comparable<Token>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<String> actions;

	public Token(String name) {
		super();
		this.name = name;
		this.actions = null;
	}
	
	public Token(String name, List<String> actions) {
		super();
		this.name = name;
		this.actions = actions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName() + ": {\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object token){
		
	    if (((Token) token).getName().equals(this.name)) return true;
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
	public int compareTo(Token token) {
		
		return ((this.name.equals((((Token) token).getName())) ? 1:0)) ;
		

	}
}
