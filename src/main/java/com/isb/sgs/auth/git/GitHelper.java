package com.isb.sgs.auth.git;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitHelper {

	Logger _logger = LoggerFactory.getLogger(this.getClass());
	private static String _REPO = "CCSW_CONFIG";
	private static String _GROUP = "CCSW";
	public static final String _JSON_FILE_AUTH = "sgs-auth.json";
	public static final String _JSON_FILE_CYPHER = "sgs-cypher.json";

	public static File _fileRepo = new File(System.getProperty("user.home") + File.separator + _REPO);

	public byte[] getJsonFile(String nameJson, String repository, String user, String password) throws Exception {
		GitlabSession session = GitlabAPI.connect(repository, user, password);
		GitlabAPI api = GitlabAPI.connect(repository, session.getPrivateToken());
		GitlabGroup aGroup = api.getGroup(_GROUP);
		GitlabProject aProject = api.getProject(aGroup.getPath(), _REPO);
		return api.getRawFileContent(aProject, "master", nameJson);
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 * @param host
	 * @throws Exception
	 */
	public void refreshRepository(String user, String pass) throws Exception {
		Git repo = new Git(new FileRepository(_fileRepo));
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(user, pass);
		FetchCommand command = repo.fetch();
		command.setCredentialsProvider(cp);
		command.setRemote("refs/heads/master");
		List<RefSpec> specs = new ArrayList<RefSpec>();
		specs.add(new RefSpec("+refs/heads/*:refs/remotes/origin/*"));
		command.setRefSpecs(specs);
		try {
			FetchResult result = command.call();
			_logger.debug(result.getMessages());
		} catch (Throwable e) {
			_logger.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			repo.close();
		}

	}

}
