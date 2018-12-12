package com.isb.sgs.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RefreshScope
@ComponentScan(basePackages = "com.isb.sgs.auth,com.isb.sgs.cypher.rest,com.isb.sgs.auth.rest,com.isb.sgs.refresh.rest")
public class SgsAuthApplication {

	public byte[] authJson;

	public static void main(String[] args) {
		// descomprimir el jar para buscar el properties cada vez que se arranca
		SpringApplication.run(SgsAuthApplication.class, args);
	}

	public byte[] getAuthJson() {
		return authJson;
	}

	public void setAuthJson(byte[] authJson) {
		this.authJson = authJson;
	}
}
