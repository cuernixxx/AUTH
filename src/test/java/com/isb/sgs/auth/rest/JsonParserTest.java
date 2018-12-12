package com.isb.sgs.auth.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.isb.sgs.auth.bean.Token;

public class JsonParserTest {
	Logger _logger = Logger.getLogger(this.getClass());
	File jsonFile;
	public static final String _JSON_FILE = "sgs-auth.json";

	@Before
	public void loadFile() {
		jsonFile = new File(this.getClass().getClassLoader().getResource(_JSON_FILE).getPath());
		_logger.debug("JSonFile Path: " + jsonFile.toString());
	}

	@Test
	public void testGetJsonObject() {
		try {
			List<Token> aToken = new Gson().fromJson(new FileReader(jsonFile), ArrayList.class);
			_logger.debug(aToken.toString());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
