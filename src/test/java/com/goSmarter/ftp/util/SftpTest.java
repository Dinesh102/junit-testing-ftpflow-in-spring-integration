package com.goSmarter.ftp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SftpTest {
	SshServer sshd;

	@Before
	public void beforeTestSetup() throws Exception {
		sshd = SshServer.setUpDefaultServer();
		sshd.setPort(22999);

		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
		sshd.setPasswordAuthenticator(new PasswordAuthenticator() {

			public boolean authenticate(String username, String password, ServerSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		CommandFactory myCommandFactory = new CommandFactory() {

			public Command createCommand(String command) {
				System.out.println("Command: " + command);
				return null;
			}
		};
		sshd.setCommandFactory(new ScpCommandFactory(myCommandFactory));

		List<NamedFactory<Command>> namedFactoryList = new ArrayList<NamedFactory<Command>>();

		namedFactoryList.add(new SftpSubsystem.Factory());
		sshd.setSubsystemFactories(namedFactoryList);

		sshd.start();
	}

	@After
	public void teardown() throws Exception {
		sshd.stop();
	}

}
