package com.goSmarter.ftp.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "/ftp-inbound-integration-config.xml",  "/test-ftp-beans-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RetrieveFileTest extends FtpTest {

	private static Log logger = LogFactory.getLog(RetrieveFileTest.class);

	@Test
	public void testRetrieveFileStreamApi() throws Exception {

		FTPClient client = new FTPClient();

		client.connect("localhost", 9999);
		client.login("user", "password");

		InputStream is = client.retrieveFileStream("/dir/sample.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;

		while ((line = in.readLine()) != null) {
			assertEquals("abcdef 1234567890", line);
		}
	}

	@Autowired
	private FileDownloadUtil downloadUtil;

	@Test
	public void testFtpDownload() throws Exception {
		File file = new File("src/test/resources/output");
		delete(file);

		FTPClient client = new FTPClient();

		client.connect("localhost", 9999);
		client.login("user", "password");

		String files[] = client.listNames("/dir");
		client.help();
		logger.debug("Before delete" + files[0]);
		assertEquals(1, files.length);

		downloadUtil.downloadFilesFromRemoteDirectory();

		logger.debug("After delete");

		files = client.listNames("/dir");
		client.help();
		assertEquals(0, files.length);

		assertEquals(1, file.list().length);
	}

	private static void delete(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				c.delete();
		}
	}
}