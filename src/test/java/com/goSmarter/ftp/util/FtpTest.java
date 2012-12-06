package com.goSmarter.ftp.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class FtpTest {
	protected static final String HOME_DIR = "/";
	protected static final String FILE = "/dir/sample.txt";
	protected static final String CONTENTS = "abcdef 1234567890";

	protected FakeFtpServer fakeFtpServer;


	@Before
	public void setUp() throws Exception {
		fakeFtpServer = new FakeFtpServer();
		fakeFtpServer.setServerControlPort(9999); // use any free port

		FileSystem fileSystem = new UnixFakeFileSystem();
		fileSystem.add(new FileEntry(FILE, CONTENTS));
		fakeFtpServer.setFileSystem(fileSystem);

		UserAccount userAccount = new UserAccount("user", "password", HOME_DIR);
		fakeFtpServer.addUserAccount(userAccount);

		fakeFtpServer.start();
	}

	@After
	public void tearDown() throws Exception {
		fakeFtpServer.stop();
	}

}
