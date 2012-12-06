package com.goSmarter.ftp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

//@ContextConfiguration(locations = { "/sftp-inbound-integration-config.xml",  "/test-sftp-beans-config.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
public class SftpRetrieveFileTest extends SftpTest {

	private static Log logger = LogFactory.getLog(SftpRetrieveFileTest.class);

	@Test
	public void testPutAndGetFile() throws Exception {
		JSch jsch = new JSch();

		Hashtable<String, String> config = new Hashtable<String, String>();
		config.put("StrictHostKeyChecking", "no");
		JSch.setConfig(config);

		Session session = jsch.getSession("remote-username", "localhost", 22999);
		session.setPassword("remote-password");

		session.connect();

		Channel channel = session.openChannel("sftp");
		channel.connect();

		ChannelSftp sftpChannel = (ChannelSftp) channel;

		final String testFileContents = "some file contents";

		String uploadedFileName = "/uploadFile";
		sftpChannel.put(new ByteArrayInputStream(testFileContents.getBytes()), uploadedFileName);

		String downloadedFileName = "downLoadFile";
		sftpChannel.get(uploadedFileName, downloadedFileName);

		File downloadedFile = new File(downloadedFileName);
		assertTrue(downloadedFile.exists());

		String fileData = getFileContents(downloadedFile);

		assertEquals(testFileContents, fileData);

		if (sftpChannel.isConnected()) {
			sftpChannel.exit();
			logger.debug("Disconnected channel");
		}

		if (session.isConnected()) {
			session.disconnect();
			logger.debug("Disconnected session");
		}

	}

	private String getFileContents(File downloadedFile) throws Exception {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(downloadedFile));

		try {
			char[] buf = new char[1024];
			for (int numRead = 0; (numRead = reader.read(buf)) != -1; buf = new char[1024]) {
				fileData.append(String.valueOf(buf, 0, numRead));
			}
		} finally {
			reader.close();
		}

		return fileData.toString();
	}
}