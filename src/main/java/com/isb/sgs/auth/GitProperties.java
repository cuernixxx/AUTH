package com.isb.sgs.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @stereotype singleton
 * @author xIS15688
 *
 */
@RefreshScope
@Component
public class GitProperties {

	@Value("${sgs.config.git.host}")
	public String hosts;

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	//
	// private static GitProperties instance;
	// public static final String _PROPERTIES_FILE = "sgs_aux.json";

	private static String user = "CCSW_user_r";
	private static String password = "r_user_CCSW";

	private byte[] sgsAuth = null;
	private byte[] sgsCypher = null;

	private static List<String> lsHost = new ArrayList<String>();

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public byte[] getSgsAuth() {
		return sgsAuth;
	}

	public void setSgsAuth(byte[] sgsAuth) {
		this.sgsAuth = sgsAuth;
	}

	public byte[] getSgsCypher() {
		return sgsCypher;
	}

	public void setSgsCypher(byte[] sgsCypher) {
		this.sgsCypher = sgsCypher;
	}

}
