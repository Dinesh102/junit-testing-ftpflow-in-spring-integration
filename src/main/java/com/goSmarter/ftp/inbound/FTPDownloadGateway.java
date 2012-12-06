package com.goSmarter.ftp.inbound;

/**
 * Message gateway for the FTP File Download Spring Integration Flow
 *
*/
public interface FTPDownloadGateway {
	
	public void downloadFilesFromFTP(String request);	
	
}
