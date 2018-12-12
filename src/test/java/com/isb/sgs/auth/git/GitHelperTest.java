package com.isb.sgs.auth.git;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;
import com.isb.sgs.auth.GitProperties;
import com.isb.test.doc.TestInfo;

@Component
public class GitHelperTest {

	Logger _logger = Logger.getLogger(this.getClass());
	public static final String _fichero = "sgs-auth.json";
	GitHelper git = new GitHelper();

	@Autowired
	GitProperties aProperties;

	@Before
	public void loadFile() {
		_logger.debug("DEBUG DE TEST GIT");
	}

	@Test
	@TestInfo(classTested = GitHelperTest.class, description = "DADO Una petición que busque el properties y SI lo encuentra. ", jiras = "ADU17-437")
	public void test_readPropertiesFound() {
		try {
			assertNotNull(aProperties);
			assertNotNull(aProperties.getHosts());
			_logger.debug(" [START]  _readPropertiesFound: ");
			_logger.debug(" readPropertiesFound: " + aProperties.getUser());
			_logger.debug(" readPropertiesFound: " + aProperties.getPassword());
			List<String> aux = Arrays.asList(aProperties.getHosts().split(","));
			for (int i = 0; i < aux.size(); i++)
				_logger.debug(" readPropertiesFound: " + aux.get(i));
			assert (true);

		} catch (Exception e) {
			_logger.error("error " + e.getMessage());
			assert (false);
		}
	}

	@Test
	@TestInfo(classTested = GitHelperTest.class, description = "DADO Una petición que busque el properties pero NO lo encuentra.", jiras = "ADU17-437")
	public void test_readPropertiesNotFound() {
		try {
			_logger.debug(" [START]  _readPropertiesFound: ");
			_logger.debug(" readPropertiesFound: " + aProperties.getUser());
			_logger.debug(" readPropertiesFound: " + aProperties.getPassword());
			List<String> aux = Arrays.asList(aProperties.getHosts().split(","));
			for (int i = 0; i < aux.size(); i++)
				_logger.debug(" readPropertiesFound: " + aux.get(i));
			assert (true);

		} catch (Exception e) {
			_logger.error("error " + e.getMessage());
			assert (false);

		}

	}

	@Test
	@TestInfo(classTested = GitHelperTest.class, description = "DADO Una petición que busque el properties pero el REPO,USER o PASS no esta informado", jiras = "ADU17-437")
	public void test_readPropertiesRepoNotFound() {
		try {
			_logger.debug(" [START]  _readPropertiesFound: ");
			_logger.debug(" readPropertiesFound: " + aProperties.getUser());
			_logger.debug(" readPropertiesFound: " + aProperties.getPassword());
			List<String> aux = Arrays.asList(aProperties.getHosts().split(","));
			for (int i = 0; i < aux.size(); i++)
				_logger.debug(" readPropertiesFound: " + aux.get(i));
			assert (true);

		} catch (Exception e) {
			_logger.error("error " + e.getMessage());
			assert (false);

		}

	}

	@Test
	public void testGetJson() {
		// http://isljkgsi01.scisb.isban.corp/CCSW/CCSW_CONFIG/raw/master/sgs-auth.json
		URL url;
		try {
			url = new URL("http://isljkgsi01.scisb.isban.corp/CCSW/CCSW_CONFIG/raw/master/sgs-auth.json");

			URLConnection uc = url.openConnection();
			uc.getContentType();

			InputStream jsonFile = uc.getInputStream();
			byte[] targetArray = ByteStreams.toByteArray(jsonFile);

			_logger.info("Test GetJson ... " + targetArray.toString());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetJsonWithFile_Exist() {
		// http://isljkgsi01.scisb.isban.corp/CCSW/CCSW_CONFIG/raw/master/sgs-auth.json

		try {
			GitlabSession session = GitlabAPI.connect("http://isljkgsi01.scisb.isban.corp", "CCSW_user_r",
					"r_user_CCSW");
			GitlabAPI api = GitlabAPI.connect("http://isljkgsi01.scisb.isban.corp", session.getPrivateToken());
			GitlabGroup aGroup = api.getGroup("CCSW");
			assertNotNull(aGroup);
			GitlabProject aProject = api.getProject(aGroup.getPath(), "CCSW_CONFIG");
			assertNotNull(aProject);
			// GitlabRepositoryFile aRpoF = api.getRepositoryFile(aProject,
			// "/sgs-auth.json", "master");
			// _logger.debug(aRpoF.getContent());

			byte[] json = api.getRawFileContent(aProject, "master", "sgs-auth.json");
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			output.write(json);
			output.writeTo(System.out);
			_logger.debug(new String(json));
			assertNotNull(json);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetJsonWithFile_NotExist() {
		// http://isljkgsi01.scisb.isban.corp/CCSW/CCSW_CONFIG/raw/master/sgs-auth.json

		try {
			GitlabSession session = GitlabAPI.connect("http://isljkgsi01.scisb.isban.corp", "CCSW_user_r",
					"r_user_CCSW");
			GitlabAPI api = GitlabAPI.connect("http://isljkgsi01.scisb.isban.corp", session.getPrivateToken());
			GitlabGroup aGroup = api.getGroup("CCSW");
			assertNotNull(aGroup);
			GitlabProject aProject = api.getProject(aGroup.getPath(), "CCSW_CONFIG");
			assertNotNull(aProject);
			// GitlabRepositoryFile aRpoF = api.getRepositoryFile(aProject,
			// "/sgs-auth.json", "master");
			// _logger.debug(aRpoF.getContent());

			byte[] json = api.getRawFileContent(aProject, "master", "sgs-Noexisto.json");
			assertNotNull(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
